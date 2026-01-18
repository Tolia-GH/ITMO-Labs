package se.ifmo.service1ejb.dao.repository;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import se.ifmo.service1ejb.dao.model.Chapter;
import se.ifmo.service1ejb.dao.model.Coordinates;
import se.ifmo.service1ejb.dao.model.SpaceMarine;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class SpaceMarineRepo {

    @PersistenceContext(unitName = "SpaceMarinePU")
    private EntityManager em;

    public List<SpaceMarine> getSpaceMarineList(List<String> sort, String order, List<String> filters, int page, int pageSize) {
        // 构建 Criteria API 查询
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SpaceMarine> cq = cb.createQuery(SpaceMarine.class);
        Root<SpaceMarine> root = cq.from(SpaceMarine.class);

        Join<SpaceMarine, Chapter> chapterJoin = root.join("chapter", JoinType.LEFT); // LEFT JOIN 以包含没有关联的记录
        Join<SpaceMarine, Coordinates> coordinatesJoin = root.join("coordinates", JoinType.LEFT);

        // 处理过滤条件
        List<Predicate> predicates = new ArrayList<>();
        if (filters != null && !filters.isEmpty()) {
            for (String filter : filters) {
                String[] filterParts = filter.split("(?<=[^.=><])(?=[=><])|(?<=[=><])(?=[^.=><])");
                if (filterParts.length == 3) {
                    String field = filterParts[0].trim();
                    String operator = filterParts[1].trim();
                    String value = filterParts[2].trim().replace("'", ""); // 去掉引号

                    Path<Object> path;
                    if (field.startsWith("chapter.")) {
                        path = chapterJoin.get(field.replace("chapter.", ""));  // 访问 `chapter` 关联表字段
                    } else if (field.startsWith("coordinates.")) {
                        path = coordinatesJoin.get(field.replace("coordinates.", ""));  // 访问 `coordinates` 关联表字段
                    } else {
                        path = root.get(field);  // 访问 `space_marine` 自身字段
                    }

                    switch (operator) {
                        case ">":
                            predicates.add(cb.greaterThan(path.as(Integer.class), Integer.parseInt(value)));
                            break;
                        case "<":
                            predicates.add(cb.lessThan(path.as(Integer.class), Integer.parseInt(value)));
                            break;
                        case "=":
                            predicates.add(cb.equal(path, value));
                            break;
                        default:
                            throw new IllegalArgumentException("Unsupported operator: " + operator);
                    }
                }
            }
        }

        // 添加过滤条件到查询
        if (!predicates.isEmpty()) {
            cq.where(predicates.toArray(new Predicate[0]));
        }

        if (sort != null && !sort.isEmpty()) {
            List<Order> orders = new ArrayList<>();
            for (String field : sort) {
                if (field != null && !field.trim().isEmpty()) {
                    boolean ascending = order != null && order.equalsIgnoreCase("ASC");
                    Path<?> path;

                    if (field.startsWith("chapter.")) {
                        path = chapterJoin.get(field.substring(8)); // 访问 chapter 字段
                    } else if (field.startsWith("coordinates.")) {
                        path = coordinatesJoin.get(field.substring(12)); // 访问 coordinates 字段
                    } else {
                        path = root.get(field); // 访问 SpaceMarine 自身的字段
                    }

                    orders.add(ascending ? cb.asc(path) : cb.desc(path));
                }
            }
            if (!orders.isEmpty()) {
                cq.orderBy(orders);
            }
        }

        // 创建查询并分页
        TypedQuery<SpaceMarine> query = em.createQuery(cq);
        query.setFirstResult(page * pageSize);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }
    public SpaceMarine getSpaceMarineById(long id) {
        return em.find(SpaceMarine.class, id);
    }

    public void save(SpaceMarine spaceMarine) {
        if (spaceMarine.getId() == null) {
            em.persist(spaceMarine);
        } else {
            em.merge(spaceMarine);
        }
    }

    public void delete(SpaceMarine spaceMarine) {
        if (em.contains(spaceMarine)) {
            em.remove(spaceMarine);
        } else {
            em.remove(em.merge(spaceMarine));
        }
    }

    public void deleteSpaceMarineByHeartCount(int heartCount) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<SpaceMarine> delete = cb.createCriteriaDelete(SpaceMarine.class);
        Root<SpaceMarine> root = delete.from(SpaceMarine.class);
        delete.where(cb.equal(root.get("heartCount"), heartCount));
        em.createQuery(delete).executeUpdate();
    }

    public List<SpaceMarine> findByMeleeWeapon(String weapon) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SpaceMarine> cq = cb.createQuery(SpaceMarine.class);
        Root<SpaceMarine> root = cq.from(SpaceMarine.class);
        
        // Handle enum conversion if needed, assuming weapon string matches enum name
        try {
            se.ifmo.service1ejb.dao.model.enums.MeleeWeapon mw = se.ifmo.service1ejb.dao.model.enums.MeleeWeapon.valueOf(weapon);
            cq.where(cb.equal(root.get("meleeWeapon"), mw));
            return em.createQuery(cq).getResultList();
        } catch (IllegalArgumentException e) {
            return new ArrayList<>();
        }
    }

    public List<SpaceMarine> findByNamePrefix(String prefix) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SpaceMarine> cq = cb.createQuery(SpaceMarine.class);
        Root<SpaceMarine> root = cq.from(SpaceMarine.class);
        cq.where(cb.like(root.get("name"), prefix + "%"));
        return em.createQuery(cq).getResultList();
    }
}

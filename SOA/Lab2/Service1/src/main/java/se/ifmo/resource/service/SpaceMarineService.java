package se.ifmo.resource.service;

import se.ifmo.resource.model.entity.SpaceMarine;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class SpaceMarineService {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("SpaceMarinePU");
    private EntityManager em = emf.createEntityManager();

    public List<SpaceMarine> getAllSpaceMarine(String sort, String order, List<String> filters, int page, int pageSize) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SpaceMarine> cq = cb.createQuery(SpaceMarine.class);
        Root<SpaceMarine> root = cq.from(SpaceMarine.class);

        // Apply sorting and filtering
        if (sort != null && !sort.isEmpty()) {
            if ("ASC".equals(order)) {
                cq.orderBy(cb.asc(root.get(sort)));
            } else {
                cq.orderBy(cb.desc(root.get(sort)));
            }
        }

        // Pagination
        TypedQuery<SpaceMarine> q = em.createQuery(cq);
        q.setFirstResult((page) * pageSize);
        q.setMaxResults(pageSize);

        return q.getResultList();
    }

    public SpaceMarine addSpaceMarine(SpaceMarine newSpaceMarine) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(newSpaceMarine);
        tx.commit();
        return newSpaceMarine;
    }

    public SpaceMarine getSpaceMarineById(int id) {
        return em.find(SpaceMarine.class, id);
    }
}

package se.ifmo.dao.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import se.ifmo.dao.model.Chapter;
import se.ifmo.dao.model.Coordinates;
import se.ifmo.dao.model.NewSpaceMarine;
import se.ifmo.dao.model.SpaceMarine;
import se.ifmo.dao.model.enums.MeleeWeapon;
import se.ifmo.util.DatabaseUtil;


import jakarta.persistence.EntityGraph;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import java.sql.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class SpaceMarineRepo {

    @PersistenceContext(unitName = "SpaceMarinePU")
    private EntityManager em;

    public void extractSpaceMarine(SpaceMarine spaceMarine, ResultSet rs) throws SQLException {
        spaceMarine.setId(rs.getLong("id"));
        spaceMarine.setName(rs.getString("name"));
        spaceMarine.setHealth(rs.getInt("health"));
        spaceMarine.setHeartCount(rs.getInt("heart_count"));
        spaceMarine.setHeight(rs.getFloat("height"));
        String weapon = rs.getString("melee_weapon");
        spaceMarine.setMeleeWeapon(weapon != null ? MeleeWeapon.valueOf(weapon) : null);

        Coordinates coordinates = new Coordinates();
        coordinates.setId(rs.getLong("c_id"));
        coordinates.setX(rs.getInt("x"));
        coordinates.setY(rs.getDouble("y"));
        spaceMarine.setCoordinates(coordinates);

        Chapter chapter = new Chapter();
        chapter.setId(rs.getLong("ch_id"));
        chapter.setName(rs.getString("ch_name"));
        chapter.setWorld(rs.getString("world"));
        spaceMarine.setChapter(chapter);

        spaceMarine.setCreationDate(
                rs.getTimestamp("creation_date")
                        .toInstant()
                        .atZone(ZonedDateTime.now().getZone()));
    }

    // Helper method to unproxy Hibernate entities
    @SuppressWarnings("unchecked")
    private <T> T unproxy(T entity) {
        if (entity == null) {
            return null;
        }
        if (entity instanceof HibernateProxy) {
            Hibernate.initialize(entity);
            return (T) ((HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();
        }
        return entity;
    }

    public List<SpaceMarine> getSpaceMarineList(List<String> sort, String order, List<String> filters, int page, int pageSize) {
        // 构建 Criteria API 查询
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SpaceMarine> cq = cb.createQuery(SpaceMarine.class);
        Root<SpaceMarine> root = cq.from(SpaceMarine.class);

        Join<SpaceMarine, Chapter> chapterJoin = root.join("chapter", JoinType.LEFT);
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
        cq.where(predicates.toArray(new Predicate[0]));

        if (sort != null && !sort.isEmpty()) {
            List<Order> orders = new ArrayList<>();
            for (String field : sort) {
                if (field != null && !field.trim().isEmpty()) {
                    boolean ascending = order != null && order.equalsIgnoreCase("ASC");
                    Path<?> path;

                    if (field.startsWith("chapter.")) {
                        path = chapterJoin.get(field.substring(8)); // 访问 chapter 字段
                    } else if (field.startsWith("coordinates.")) {
                        path = coordinatesJoin.get(field.substring(11)); // 访问 coordinates 字段
                    } else {
                        path = root.get(field); // 访问 SpaceMarine 自身的字段
                    }

                    orders.add(ascending ? cb.asc(path) : cb.desc(path));
                }
            }
            cq.orderBy(orders);
        }

        // 创建查询并分页
        TypedQuery<SpaceMarine> query = em.createQuery(cq);
        
        // Use EntityGraph for fetching
        EntityGraph<SpaceMarine> graph = em.createEntityGraph(SpaceMarine.class);
        graph.addAttributeNodes("coordinates", "chapter");
        query.setHint("jakarta.persistence.loadgraph", graph);
        
        query.setFirstResult(page * pageSize);
        query.setMaxResults(pageSize);

        // 执行查询
        List<SpaceMarine> results = query.getResultList();
        
        // Unproxy
        List<SpaceMarine> unproxiedResults = new ArrayList<>();
        for (SpaceMarine sm : results) {
            if (sm.getCoordinates() != null) {
                sm.setCoordinates(unproxy(sm.getCoordinates()));
            }
            if (sm.getChapter() != null) {
                sm.setChapter(unproxy(sm.getChapter()));
            }
            unproxiedResults.add(unproxy(sm));
        }
        
        return unproxiedResults;
    }

    // 获取指定ID的SpaceMarine
    public SpaceMarine getSpaceMarineById(long id) throws SQLException {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SpaceMarine> cq = cb.createQuery(SpaceMarine.class);
        Root<SpaceMarine> spaceMarineRoot = cq.from(SpaceMarine.class);

        // Add where condition
        cq.select(spaceMarineRoot)
                .where(cb.equal(spaceMarineRoot.get("id"), id));

        // 执行查询
        TypedQuery<SpaceMarine> query = em.createQuery(cq);
        
        // 使用 EntityGraph 确保关联数据被加载
        EntityGraph<SpaceMarine> graph = em.createEntityGraph(SpaceMarine.class);
        graph.addAttributeNodes("coordinates", "chapter");
        query.setHint("jakarta.persistence.loadgraph", graph);
        
        List<SpaceMarine> results = query.getResultList();

        if (results.isEmpty()) {
            throw new SQLException("Space marine not found");
        }
        
        SpaceMarine sm = results.get(0);
        
        if (sm.getCoordinates() != null) {
            sm.setCoordinates(unproxy(sm.getCoordinates()));
        }
        if (sm.getChapter() != null) {
            sm.setChapter(unproxy(sm.getChapter()));
        }

        return unproxy(sm);
    }

    // 插入坐标表
    public int insertCoordinates(Connection conn, Coordinates coordinates) throws SQLException {
        String sql = "INSERT INTO soa_lab2.coordinates (x, y) VALUES (?, ?) RETURNING id";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, coordinates.getX());
            ps.setDouble(2, coordinates.getY());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                throw new SQLException("Failed to insert coordinates");
            }
        }
    }

    // 插入章节表（如果有章节信息）
    public Integer insertChapter(Connection conn, Chapter chapter) throws SQLException {
        if (chapter != null && chapter.getName() != null) {
            String sql = "INSERT INTO soa_lab2.chapter (name, world) VALUES (?, ?) RETURNING id";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, chapter.getName());
                ps.setString(2, chapter.getWorld());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return null;
    }

    // 插入 SpaceMarine 表
    public SpaceMarine insertSpaceMarine(Connection conn, NewSpaceMarine newSM, int coordinateId, Integer chapterId) throws SQLException {
        String sql = "INSERT INTO soa_lab2.space_marine (name, coordinate_id, health, heart_count, height, melee_weapon, chapter_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id, creation_date";
        SpaceMarine sm = new SpaceMarine();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newSM.getName());
            ps.setInt(2, coordinateId);
            ps.setInt(3, newSM.getHealth());
            ps.setInt(4, newSM.getHeartCount());
            ps.setFloat(5, newSM.getHeight());
            if (newSM.getMeleeWeapon() != null)
                ps.setObject(6, newSM.getMeleeWeapon(), java.sql.Types.OTHER);
            else
                ps.setNull(6, Types.VARCHAR);
            if (chapterId != null)
                ps.setInt(7, chapterId);
            else
                ps.setNull(7, Types.INTEGER);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                sm.setId(rs.getLong("id"));
                sm.setCreationDate(rs.getTimestamp("creation_date")
                        .toInstant()
                        .atZone(ZonedDateTime.now().getZone()));
            }
        }
        return sm;
    }

    // 更新coordinates表
    public void updateCoordinates(Connection conn, Coordinates coordinates, Long id) throws SQLException {
        String sql = "UPDATE soa_lab2.coordinates SET x = ?, y = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, coordinates.getX());
            ps.setDouble(2, coordinates.getY());
            ps.setLong(3, id);
            ps.executeUpdate();
        }
    }

    // 更新chapter表
    public void updateChapter(Connection conn, Chapter chapter, Long id) throws SQLException {
        String sql = "UPDATE soa_lab2.chapter SET name = ?, world = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, chapter.getName());
            ps.setString(2, chapter.getWorld());
            ps.setLong(3, id);
            ps.executeUpdate();
        }
    }

    // 更新space_marine表
    public void updateSpaceMarine(Connection conn, NewSpaceMarine updatedSM, long id) throws SQLException {
        String sql = "UPDATE soa_lab2.space_marine SET name = ?, health = ?, heart_count = ?, height = ?, melee_weapon = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, updatedSM.getName());
            ps.setInt(2, updatedSM.getHealth());
            ps.setInt(3, updatedSM.getHeartCount());
            ps.setFloat(4, updatedSM.getHeight());
            if (updatedSM.getMeleeWeapon() != null)
                ps.setObject(5, updatedSM.getMeleeWeapon(), java.sql.Types.OTHER);
            else
                ps.setNull(5, Types.VARCHAR);
            ps.setLong(6, id);
            ps.executeUpdate();
        }
    }

    public void deleteSpaceMarineById(long id) throws SQLException {
        String sql = "DELETE FROM soa_lab2.space_marine WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    public void deleteSpaceMarineByHeartCount(int heartCount) throws SQLException {
        String sql = "DELETE FROM soa_lab2.space_marine WHERE heart_count = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, heartCount);

            ps.executeUpdate();
        }
    }

    public List<SpaceMarine> findByMeleeWeapon(String weapon) throws SQLException {
        List<SpaceMarine> spaceMarineList = new ArrayList<>();

        String query = "SELECT " +
                "sm.id, " +
                "sm.name, " +
                "sm.creation_date, " +
                "sm.health, " +
                "sm.heart_count, " +
                "sm.height, " +
                "sm.melee_weapon, " +
                "c.id AS c_id, c.x, c.y, " +
                "ch.id AS ch_id, ch.name AS ch_name, ch.world " +
                "FROM soa_lab2.space_marine sm " +
                "JOIN soa_lab2.coordinates c ON c.id = sm.coordinate_id " +
                "JOIN soa_lab2.chapter ch ON ch.id = sm.chapter_id " +
                "WHERE melee_weapon = CAST(? AS soa_lab2.melee_weapon) " +
                "ORDER BY sm.id ";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, weapon);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SpaceMarine spaceMarine = new SpaceMarine();
                extractSpaceMarine(spaceMarine, rs);
                spaceMarineList.add(spaceMarine);
            }
        }

        return spaceMarineList;
    }

    public List<SpaceMarine> findByNamePrefix(String prefix) throws SQLException {
        List<SpaceMarine> spaceMarineList = new ArrayList<>();

        String query = "SELECT " +
                "sm.id, " +
                "sm.name, " +
                "sm.creation_date, " +
                "sm.health, " +
                "sm.heart_count, " +
                "sm.height, " +
                "sm.melee_weapon, " +
                "c.id AS c_id, c.x, c.y, " +
                "ch.id AS ch_id, ch.name AS ch_name, ch.world " +
                "FROM soa_lab2.space_marine sm " +
                "JOIN soa_lab2.coordinates c ON c.id = sm.coordinate_id " +
                "JOIN soa_lab2.chapter ch ON ch.id = sm.chapter_id " +
                "WHERE sm.name LIKE ? " +
                "ORDER BY sm.id ";


        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, prefix + '%');
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SpaceMarine spaceMarine = new SpaceMarine();
                extractSpaceMarine(spaceMarine, rs);

                spaceMarineList.add(spaceMarine);
            }
        }

        return spaceMarineList;
    }
}

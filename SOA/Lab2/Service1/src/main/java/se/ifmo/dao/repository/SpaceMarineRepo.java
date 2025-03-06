package se.ifmo.dao.repository;

import se.ifmo.dao.model.Chapter;
import se.ifmo.dao.model.Coordinates;
import se.ifmo.dao.model.NewSpaceMarine;
import se.ifmo.dao.model.SpaceMarine;
import se.ifmo.dao.model.enums.MeleeWeapon;
import se.ifmo.util.DatabaseUtil;

import java.sql.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class SpaceMarineRepo {

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

    // 插入坐标表
    public int insertCoordinates(Connection conn, Coordinates coordinates) throws SQLException {
        String sql = "INSERT INTO coordinates (x, y) VALUES (?, ?) RETURNING id";
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
            String sql = "INSERT INTO chapter (name, world) VALUES (?, ?) RETURNING id";
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
        String sql = "INSERT INTO space_marine (name, coordinate_id, health, heart_count, height, melee_weapon, chapter_id) " +
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

    public List<SpaceMarine> getSpaceMarineList(List<String> sort, String order, List<String> filters, int page, int pageSize) throws SQLException {
        List<SpaceMarine> spaceMarineList = new ArrayList<>();

        StringBuilder query = new StringBuilder(
                "SELECT " +
                        "sm.id, sm.name, sm.creation_date, sm.health, sm.heart_count, sm.height, sm.melee_weapon, " +
                        "c.id AS c_id, c.x, c.y, " +
                        "ch.id AS ch_id, ch.name AS ch_name, ch.world " +
                        "FROM space_marine sm " +
                        "JOIN coordinates c ON c.id = sm.coordinate_id " +
                        "JOIN chapter ch ON ch.id = sm.chapter_id"
        );

        if (filters != null && !filters.isEmpty()) {
            query.append(" WHERE sm.").append(String.join(" AND sm.", filters));
        }

        if (!sort.isEmpty()) {
            query.append(" ORDER BY sm.")
                    .append(String.join(", sm.", sort))
                    .append(" ")
                    .append(order);
        }

        query.append(" LIMIT ").append(pageSize);
        query.append(" OFFSET ").append(page * pageSize);

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query.toString())) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SpaceMarine spaceMarine = new SpaceMarine();
                extractSpaceMarine(spaceMarine, rs);
                spaceMarineList.add(spaceMarine);
            }
        }

        return spaceMarineList;
    }

    // 获取指定ID的SpaceMarine
    public SpaceMarine getSpaceMarineById(long id) throws SQLException {
        SpaceMarine spaceMarine = new SpaceMarine();

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
                "FROM space_marine sm " +
                "JOIN coordinates c ON c.id = sm.coordinate_id " +
                "JOIN chapter ch ON ch.id = sm.chapter_id " +
                "WHERE sm.id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, id);  // 使用PreparedStatement避免SQL注入
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                extractSpaceMarine(spaceMarine, rs);
            }
        }

        if (spaceMarine.getName() == null) {
            throw new SQLException("Space marine not found");
        }

        return spaceMarine;
    }

    // 更新coordinates表
    public void updateCoordinates(Connection conn, Coordinates coordinates, Long id) throws SQLException {
        String sql = "UPDATE coordinates SET x = ?, y = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, coordinates.getX());
            ps.setDouble(2, coordinates.getY());
            ps.setLong(3, id);
            ps.executeUpdate();
        }
    }

    // 更新chapter表
    public void updateChapter(Connection conn, Chapter chapter, Long id) throws SQLException {
        String sql = "UPDATE chapter SET name = ?, world = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, chapter.getName());
            ps.setString(2, chapter.getWorld());
            ps.setLong(3, id);
            ps.executeUpdate();
        }
    }

    // 更新space_marine表
    public void updateSpaceMarine(Connection conn, NewSpaceMarine updatedSM, long id) throws SQLException {
        String sql = "UPDATE space_marine SET name = ?, health = ?, heart_count = ?, height = ?, melee_weapon = ? WHERE id = ?";
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
        String sql = "DELETE FROM space_marine WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    public void deleteSpaceMarineByHeartCount(int heartCount) throws SQLException {
        String sql = "DELETE FROM space_marine WHERE heart_count = ?";
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
                "FROM space_marine sm " +
                "JOIN coordinates c ON c.id = sm.coordinate_id " +
                "JOIN chapter ch ON ch.id = sm.chapter_id " +
                "WHERE melee_weapon = CAST(? AS melee_weapon) " +
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
                "FROM space_marine sm " +
                "JOIN coordinates c ON c.id = sm.coordinate_id " +
                "JOIN chapter ch ON ch.id = sm.chapter_id " +
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

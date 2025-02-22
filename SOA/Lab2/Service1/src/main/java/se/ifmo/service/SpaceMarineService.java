package se.ifmo.service;

import se.ifmo.model.entity.Chapter;
import se.ifmo.model.entity.Coordinates;
import se.ifmo.model.entity.NewSpaceMarine;
import se.ifmo.model.entity.SpaceMarine;
import se.ifmo.model.enums.MeleeWeapon;
import se.ifmo.util.DatabaseUtil;

import java.sql.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class SpaceMarineService {

    private static void extractSpaceMarine(SpaceMarine spaceMarine, ResultSet rs) throws SQLException {
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

    public List<SpaceMarine> getAllSpaceMarine(
            String sort,
            String order,
            List<String> filters,
            int page,
            int pageSize) throws SQLException {
        List<SpaceMarine> spaceMarineList = new ArrayList<>();

        if (sort == null || sort.isEmpty()) {
            sort = "id";
        }

        if (order == null || order.isEmpty()) {
            order = "ASC";
        } else {
            if (order.equalsIgnoreCase("DESC")) {
                order = "DESC";
            } else {
                order = "ASC";
            }
        }

        if (page < 0) {
            page = 0;
        }

        if (pageSize < 1) {
            pageSize = 10;
        }

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
                "JOIN chapter ch ON ch.id = sm.chapter_id";

        if (filters != null && !filters.isEmpty()) {
            query += " WHERE ";
            // 此处简化处理，每个过滤条件之间使用 AND 拼接
            query += String.join(" AND sm.", filters);
        }

        query += " ORDER BY sm." + sort + " " + order;
        query += " LIMIT " + pageSize;
        query += " OFFSET " + page * pageSize;

        try (Connection conn = DatabaseUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SpaceMarine spaceMarine = new SpaceMarine();
                extractSpaceMarine(spaceMarine, rs);

                spaceMarineList.add(spaceMarine);
            }
        }

        return spaceMarineList;
    }



    public SpaceMarine addSpaceMarine(NewSpaceMarine newSM) throws SQLException {
        try (Connection conn = DatabaseUtil.getConnection()){
            conn.setAutoCommit(false);
            try {
                // 插入 coordinates 表
                int coordinateId;
                String sqlCoordinates = "INSERT INTO coordinates (x, y) VALUES (?, ?) RETURNING id";
                try (PreparedStatement ps = conn.prepareStatement(sqlCoordinates)) {
                    ps.setInt(1, newSM.getCoordinates().getX());
                    ps.setDouble(2, newSM.getCoordinates().getY());
                    ResultSet rs = ps.executeQuery();
                    if(rs.next()){
                        coordinateId = rs.getInt("id");
                    } else {
                        conn.rollback();
                        throw new SQLException("Failed to insert coordinates");
                    }
                }
                // 插入 chapter 表（如果传入了章节信息）
                Integer chapterId = null;
                if(newSM.getChapter() != null && newSM.getChapter().getName() != null){
                    String sqlChapter = "INSERT INTO chapter (name, world) VALUES (?, ?) RETURNING id";
                    try (PreparedStatement ps = conn.prepareStatement(sqlChapter)) {
                        ps.setString(1, newSM.getChapter().getName());
                        ps.setString(2, newSM.getChapter().getWorld());
                        ResultSet rs = ps.executeQuery();
                        if(rs.next()){
                            chapterId = rs.getInt("id");
                        }
                    }
                }
                // 插入 space_marine 表
                String sqlSM = "INSERT INTO space_marine (name, coordinate_id, health, heart_count, height, melee_weapon, chapter_id) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id, creation_date";
                SpaceMarine sm = new SpaceMarine();
                try (PreparedStatement ps = conn.prepareStatement(sqlSM)) {
                    ps.setString(1, newSM.getName());
                    ps.setInt(2, coordinateId);
                    ps.setInt(3, newSM.getHealth());
                    ps.setInt(4, newSM.getHeartCount());
                    ps.setFloat(5, newSM.getHeight());
                    if(newSM.getMeleeWeapon() != null)
                        ps.setObject(6, newSM.getMeleeWeapon(), java.sql.Types.OTHER);
                    else
                        ps.setNull(6, Types.VARCHAR);
                    if(chapterId != null)
                        ps.setInt(7, chapterId);
                    else
                        ps.setNull(7, Types.INTEGER);

                    ResultSet rs = ps.executeQuery();
                    if(rs.next()){
                        sm.setId(rs.getLong("id"));
                        sm.setCreationDate(rs.getTimestamp("creation_date")
                                .toInstant()
                                .atZone(ZonedDateTime.now().getZone()));
                    }
                }
                // 设置其它字段
                sm.setName(newSM.getName());
                sm.setHealth(newSM.getHealth());
                sm.setHeartCount(newSM.getHeartCount());
                sm.setHeight(newSM.getHeight());
                sm.setMeleeWeapon(newSM.getMeleeWeapon());
                sm.setCoordinates(newSM.getCoordinates());
                sm.setChapter(newSM.getChapter());

                conn.commit();
                return sm;
            } catch(SQLException e){
                conn.rollback();
                throw e;
            }
        }
    }

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
                "WHERE sm.id = " + id;

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                extractSpaceMarine(spaceMarine, rs);
            }
        }

        return spaceMarine;
    }

    public void updateSpaceMarine(long id, NewSpaceMarine updatedSM) throws SQLException {

        try (Connection conn = DatabaseUtil.getConnection()) {
            conn.setAutoCommit(false);
            SpaceMarine spaceMarine = getSpaceMarineById(id);
            if (spaceMarine == null) {
                throw new SQLException("Space marine not found");
            }

            // 更新 coordinates 表
            String sqlCoordinates = "UPDATE coordinates SET x = ?, y = ? WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlCoordinates)) {
                ps.setInt(1, updatedSM.getCoordinates().getX());
                ps.setDouble(2, updatedSM.getCoordinates().getY());
                ps.setLong(3, spaceMarine.getCoordinates().getId());
                ps.executeUpdate();
            }

            // 更新 chapter 表（如果传入了新的章节信息）
            if (updatedSM.getChapter() != null && updatedSM.getChapter().getName() != null) {
                String sqlChapter = "UPDATE chapter SET name = ?, world = ? WHERE id = ?";
                try (PreparedStatement ps = conn.prepareStatement(sqlChapter)) {
                    ps.setString(1, updatedSM.getChapter().getName());
                    ps.setString(2, updatedSM.getChapter().getWorld());
                    ps.setLong(3, spaceMarine.getChapter().getId());
                    ps.executeUpdate();
                }
            }

            // 更新 space_marine 表
            String sqlSM = "UPDATE space_marine SET name = ?, health = ?, heart_count = ?, height = ?, melee_weapon = ? WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlSM)) {
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

            conn.commit();
        }
    }

    public void deleteSpaceMarineByID(long id) throws SQLException {
        SpaceMarine spaceMarine = getSpaceMarineById(id);
        if (spaceMarine == null) {
            throw new SQLException("Space marine not found");
        }

        String sql = "DELETE FROM space_marine WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }
}

package se.ifmo.service;

import se.ifmo.model.entity.Chapter;
import se.ifmo.model.entity.Coordinates;
import se.ifmo.model.entity.SpaceMarine;
import se.ifmo.model.enums.MeleeWeapon;
import se.ifmo.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class SpaceMarineService {

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

        String query = "SELECT * FROM space_marine sm";
        query += " JOIN coordinates c ON c.id = sm.coordinate_id";
        query += " JOIN chapter ch ON ch.id = sm.chapter_id";

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
                spaceMarine.setId(rs.getLong("id"));
                spaceMarine.setName(rs.getString("name"));
                spaceMarine.setHealth(rs.getInt("health"));
                spaceMarine.setHeartCount(rs.getInt("heart_count"));
                spaceMarine.setHeight(rs.getFloat("height"));
                String weapon = rs.getString("melee_weapon");
                spaceMarine.setMeleeWeapon(weapon != null ? MeleeWeapon.valueOf(weapon) : null);

                Coordinates coordinates = new Coordinates();
                coordinates.setX(rs.getInt("x"));
                coordinates.setY(rs.getDouble("y"));
                spaceMarine.setCoordinates(coordinates);

                Chapter chapter = new Chapter();
                chapter.setName(rs.getString("name"));
                chapter.setWorld(rs.getString("world"));
                spaceMarine.setChapter(chapter);

                spaceMarine.setCreationDate(
                        rs.getTimestamp("creation_date")
                                .toInstant()
                                .atZone(ZonedDateTime.now().getZone()));

                spaceMarineList.add(spaceMarine);
            }
        }

        return spaceMarineList;
    }

    public SpaceMarine addSpaceMarine(SpaceMarine newSpaceMarine) {
        return newSpaceMarine;
    }

    public SpaceMarine getSpaceMarineById(int id) {
        return null;
    }
}

package se.ifmo.dao.repository;

import se.ifmo.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StarshipRepo {
    public void removeSpaceMarineFromStarship(long starshipId, long smId) throws SQLException {
        String sql = "UPDATE soa_lab2.starship SET space_marine_id_list = array_remove(space_marine_id_list, ?) WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, smId);
            ps.setLong(2, starshipId);
            ps.executeUpdate();
        }
    }

    public void clearSpaceMarinesFromStarship(long starshipId) throws SQLException {
        String sql = "UPDATE soa_lab2.starship SET space_marine_id_list = '{}' WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, starshipId);
            ps.executeUpdate();
        }
    }
}

package se.ifmo.service;

import se.ifmo.model.entity.SpaceMarine;
import se.ifmo.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class StarshipService {

    public void unloadSpaceMarineById(long starshipId, long smId) throws SQLException {
        try (Connection conn = DatabaseUtil.getConnection()) {
            conn.setAutoCommit(false);
            try {
                String sql = "UPDATE starship SET space_marine_id_list = array_remove(space_marine_id_list, ?) WHERE id = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setLong(1, smId);
                    ps.setLong(2, starshipId);
                    ps.executeUpdate();
                }
                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            }
        }
    }

    public void unloadAllSpaceMarine(long starshipId) throws SQLException {
        String sql = "UPDATE starship SET space_marine_id_list = '{}' WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, starshipId);
            ps.executeUpdate();
        }
    }
}

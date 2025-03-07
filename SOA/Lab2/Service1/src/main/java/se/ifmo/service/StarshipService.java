package se.ifmo.service;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import se.ifmo.dao.repository.StarshipRepo;
import se.ifmo.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StarshipService {

    @PersistenceContext(unitName = "SpaceMarinePU")
    private EntityManager em;

    private final StarshipRepo starshipRepo;

    @Inject
    public StarshipService(StarshipRepo starshipRepo) {
        this.starshipRepo = starshipRepo;
    }

    public void unloadSpaceMarineById(long starshipId, long smId) throws SQLException {
        try (Connection conn = DatabaseUtil.getConnection()) {
            conn.setAutoCommit(false);
            try {
                starshipRepo.removeSpaceMarineFromStarship(starshipId, smId);
                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            }
        }
    }

    public void unloadAllSpaceMarine(long starshipId) throws SQLException {
        starshipRepo.clearSpaceMarinesFromStarship(starshipId);
    }
}

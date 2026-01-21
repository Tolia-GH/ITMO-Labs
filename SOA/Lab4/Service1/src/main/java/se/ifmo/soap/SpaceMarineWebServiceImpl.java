package se.ifmo.soap;

import jakarta.inject.Inject;
import jakarta.jws.WebService;
import se.ifmo.dao.model.NewSpaceMarine;
import se.ifmo.dao.model.SpaceMarine;
import se.ifmo.service.SpaceMarineService;

import java.sql.SQLException;
import java.util.List;

@WebService(endpointInterface = "se.ifmo.soap.SpaceMarineWebService", targetNamespace = "http://soap.ifmo.se/", serviceName = "SpaceMarineService")
public class SpaceMarineWebServiceImpl implements SpaceMarineWebService {

    @Inject
    private SpaceMarineService spaceMarineService;

    @Override
    public List<SpaceMarine> getAllSpaceMarine(List<String> sort, String order, List<String> filter, int page, int pageSize) {
        try {
            return spaceMarineService.getAllSpaceMarine(sort, order, filter, page, pageSize);
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Internal error: " + e.getMessage(), e);
        }
    }

    @Override
    public SpaceMarine addSpaceMarine(NewSpaceMarine newSpaceMarine) {
        try {
            return spaceMarineService.addSpaceMarine(newSpaceMarine);
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Internal error: " + e.getMessage(), e);
        }
    }

    @Override
    public SpaceMarine getSpaceMarineById(long id) {
        try {
            return spaceMarineService.getSpaceMarineById(id);
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Internal error: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateSpaceMarineById(long id, NewSpaceMarine newSpaceMarine) {
        try {
            spaceMarineService.updateSpaceMarine(id, newSpaceMarine);
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Internal error: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteSpaceMarineById(long id) {
        try {
            spaceMarineService.deleteSpaceMarineByID(id);
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Internal error: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteSpaceMarineByHeartCount(int heartCount) {
        try {
            spaceMarineService.deleteSpaceMarineByHeartCount(heartCount);
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Internal error: " + e.getMessage(), e);
        }
    }

    @Override
    public List<SpaceMarine> getSpaceMarineListByMeleeWeapon(String meleeWeapon) {
        try {
            return spaceMarineService.getSpaceMarineListByMeleeWeapon(meleeWeapon);
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Internal error: " + e.getMessage(), e);
        }
    }

    @Override
    public List<SpaceMarine> getSpaceMarineListByName(String name) {
        try {
            return spaceMarineService.getSpaceMarineListByName(name);
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Internal error: " + e.getMessage(), e);
        }
    }
}

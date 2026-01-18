package se.ifmo.service;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import se.ifmo.service1ejb.dao.model.NewSpaceMarine;
import se.ifmo.service1ejb.dao.model.SpaceMarine;
import se.ifmo.service1ejb.remote.SpaceMarineServiceRemote;

import java.sql.SQLException;
import java.util.List;

@RequestScoped
public class SpaceMarineService {

    @EJB
    private SpaceMarineServiceRemote spaceMarineServiceRemote;

    public List<SpaceMarine> getAllSpaceMarine(
            List<String> sort,
            String order,
            List<String> filters,
            int page,
            int pageSize) throws SQLException {
        return spaceMarineServiceRemote.getSpaceMarine(sort, order, filters, page, pageSize);
    }

    public SpaceMarine getSpaceMarineById(long id) throws SQLException {
        return spaceMarineServiceRemote.getSpaceMarineById(id);
    }

    public SpaceMarine addSpaceMarine(NewSpaceMarine newSM) throws SQLException {
        return spaceMarineServiceRemote.addSpaceMarine(newSM);
    }

    public void updateSpaceMarine(long id, NewSpaceMarine updatedSM) throws SQLException {
        spaceMarineServiceRemote.updateSpaceMarine(id, updatedSM);
    }

    public void deleteSpaceMarineByID(long id) throws SQLException {
        spaceMarineServiceRemote.deleteSpaceMarineByID(id);
    }

    public void deleteSpaceMarineByHeartCount(int heartCount) throws SQLException {
        spaceMarineServiceRemote.deleteSpaceMarineByHeartCount(heartCount);
    }

    public List<SpaceMarine> getSpaceMarineListByMeleeWeapon(String weapon) throws SQLException {
        return spaceMarineServiceRemote.getSpaceMarineListByMeleeWeapon(weapon);
    }

    public List<SpaceMarine> getSpaceMarineListByName(String prefix) throws SQLException {
        return spaceMarineServiceRemote.getSpaceMarineListByName(prefix);
    }
}

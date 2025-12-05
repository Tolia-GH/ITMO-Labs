package se.ifmo.service1ejb.remote;

import jakarta.ejb.Remote;
import se.ifmo.service1ejb.dao.model.NewSpaceMarine;
import se.ifmo.service1ejb.dao.model.SpaceMarine;


import java.sql.SQLException;
import java.util.List;

@Remote
public interface SpaceMarineServiceRemote {
    List<SpaceMarine> getSpaceMarine(
            List<String> sort,
            String order,
            List<String> filters,
            int page,
            int pageSize) throws SQLException;

    SpaceMarine getSpaceMarineById(long id) throws SQLException;
    SpaceMarine addSpaceMarine(NewSpaceMarine newSM) throws SQLException;
    void updateSpaceMarine(long id, NewSpaceMarine updatedSM) throws SQLException;
    void deleteSpaceMarineByID(long id) throws SQLException;
    void deleteSpaceMarineByHeartCount(int heartCount) throws SQLException;
    List<SpaceMarine> getSpaceMarineListByMeleeWeapon(String weapon) throws SQLException;
    List<SpaceMarine> getSpaceMarineListByName(String prefix) throws SQLException;
}


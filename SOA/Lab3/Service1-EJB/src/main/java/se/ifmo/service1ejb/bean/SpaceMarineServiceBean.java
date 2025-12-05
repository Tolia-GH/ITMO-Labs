package se.ifmo.service1ejb.bean;


import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import se.ifmo.service1ejb.dao.model.NewSpaceMarine;
import se.ifmo.service1ejb.dao.model.SpaceMarine;
import se.ifmo.service1ejb.dao.repository.SpaceMarineRepo;
import se.ifmo.service1ejb.remote.SpaceMarineServiceRemote;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Stateless
public class SpaceMarineServiceBean implements SpaceMarineServiceRemote {

    @Inject
    private SpaceMarineRepo spaceMarineRepo;

    @Override
    public List<SpaceMarine> getSpaceMarine(List<String> sort, String order, List<String> filters, int page, int pageSize) throws SQLException {
        List<SpaceMarine> spaceMarineList;

        if (sort == null || sort.isEmpty()) {
            sort = Collections.singletonList("id");
        }

        if (order == null || order.isEmpty()) {
            order = "ASC";
        } else {
            if (!order.equalsIgnoreCase("DESC")) {
                order = "ASC";
            }
        }

        if (page < 0) {
            page = 0;
        }

        if (pageSize < 1) {
            pageSize = 10;
        }

        spaceMarineList = spaceMarineRepo.getSpaceMarineList(sort, order, filters, page, pageSize);

        return spaceMarineList;
    }

    @Override
    public SpaceMarine getSpaceMarineById(long id) throws SQLException {
        return null;
    }

    @Override
    public SpaceMarine addSpaceMarine(NewSpaceMarine newSM) throws SQLException {
        return null;
    }

    @Override
    public void updateSpaceMarine(long id, NewSpaceMarine updatedSM) throws SQLException {

    }

    @Override
    public void deleteSpaceMarineByID(long id) throws SQLException {

    }

    @Override
    public void deleteSpaceMarineByHeartCount(int heartCount) throws SQLException {

    }

    @Override
    public List<SpaceMarine> getSpaceMarineListByMeleeWeapon(String weapon) throws SQLException {
        return List.of();
    }

    @Override
    public List<SpaceMarine> getSpaceMarineListByName(String prefix) throws SQLException {
        return List.of();
    }
}

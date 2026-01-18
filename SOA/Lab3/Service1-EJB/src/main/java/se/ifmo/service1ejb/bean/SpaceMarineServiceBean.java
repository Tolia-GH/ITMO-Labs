package se.ifmo.service1ejb.bean;

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
        SpaceMarine spaceMarine = spaceMarineRepo.getSpaceMarineById(id);
        if (spaceMarine == null) {
            throw new SQLException("Space marine not found");
        }
        return spaceMarine;
    }

    @Override
    public SpaceMarine addSpaceMarine(NewSpaceMarine newSM) throws SQLException {
        SpaceMarine sm = new SpaceMarine();
        sm.setName(newSM.getName());
        sm.setCoordinates(newSM.getCoordinates());
        sm.setHealth(newSM.getHealth());
        sm.setHeartCount(newSM.getHeartCount());
        sm.setHeight(newSM.getHeight());
        sm.setMeleeWeapon(newSM.getMeleeWeapon());
        sm.setChapter(newSM.getChapter());
        
        spaceMarineRepo.save(sm);
        return sm;
    }

    @Override
    public void updateSpaceMarine(long id, NewSpaceMarine updatedSM) throws SQLException {
        SpaceMarine sm = spaceMarineRepo.getSpaceMarineById(id);
        if (sm == null) {
            throw new SQLException("Space marine not found");
        }
        
        sm.setName(updatedSM.getName());
        // For OneToOne relationships, we might need to handle the update carefully
        // But assuming JPA Merge handles cascading updates if properly configured
        if (updatedSM.getCoordinates() != null) {
            // Update internal values or replace object? 
            // Usually simpler to replace if CascadeType.ALL is set
            // But if ID is missing in input, it might create new one.
            // Ideally we map fields. For simplicity in this lab:
            if (sm.getCoordinates() != null) {
                sm.getCoordinates().setX(updatedSM.getCoordinates().getX());
                sm.getCoordinates().setY(updatedSM.getCoordinates().getY());
            } else {
                sm.setCoordinates(updatedSM.getCoordinates());
            }
        }
        
        sm.setHealth(updatedSM.getHealth());
        sm.setHeartCount(updatedSM.getHeartCount());
        sm.setHeight(updatedSM.getHeight());
        sm.setMeleeWeapon(updatedSM.getMeleeWeapon());
        
        if (updatedSM.getChapter() != null) {
            if (sm.getChapter() != null) {
                sm.getChapter().setName(updatedSM.getChapter().getName());
                sm.getChapter().setWorld(updatedSM.getChapter().getWorld());
            } else {
                sm.setChapter(updatedSM.getChapter());
            }
        } else {
            sm.setChapter(null);
        }

        spaceMarineRepo.save(sm);
    }

    @Override
    public void deleteSpaceMarineByID(long id) throws SQLException {
        SpaceMarine sm = spaceMarineRepo.getSpaceMarineById(id);
        if (sm != null) {
            spaceMarineRepo.delete(sm);
        } else {
             // Or throw exception? Previous implementation didn't seem to throw on delete if not found?
             // Actually previous implementation called repo.deleteSpaceMarineById(id)
        }
    }

    @Override
    public void deleteSpaceMarineByHeartCount(int heartCount) throws SQLException {
        spaceMarineRepo.deleteSpaceMarineByHeartCount(heartCount);
    }

    @Override
    public List<SpaceMarine> getSpaceMarineListByMeleeWeapon(String weapon) throws SQLException {
        return spaceMarineRepo.findByMeleeWeapon(weapon);
    }

    @Override
    public List<SpaceMarine> getSpaceMarineListByName(String prefix) throws SQLException {
        return spaceMarineRepo.findByNamePrefix(prefix);
    }
}

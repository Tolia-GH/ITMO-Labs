package se.ifmo.service;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import se.ifmo.dao.model.NewSpaceMarine;
import se.ifmo.dao.model.SpaceMarine;
import se.ifmo.dao.repository.SpaceMarineRepo;
import se.ifmo.util.DatabaseUtil;
import java.sql.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpaceMarineService {

    @PersistenceContext(unitName = "SpaceMarinePU")
    private EntityManager em;

    private final SpaceMarineRepo spaceMarineRepo;

    @Inject
    public SpaceMarineService(SpaceMarineRepo spaceMarineRepo) {
        this.spaceMarineRepo = spaceMarineRepo;
    }

    public List<SpaceMarine> getAllSpaceMarine(
            List<String> sort,
            String order,
            List<String> filters,
            int page,
            int pageSize) throws SQLException {
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

    public SpaceMarine addSpaceMarine(NewSpaceMarine newSM) throws SQLException {
        try (Connection conn = DatabaseUtil.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // 插入 coordinates 表
                int coordinateId = spaceMarineRepo.insertCoordinates(conn, newSM.getCoordinates());

                // 插入 chapter 表（如果传入了章节信息）
                Integer chapterId = spaceMarineRepo.insertChapter(conn, newSM.getChapter());

                // 插入 space_marine 表
                SpaceMarine sm = spaceMarineRepo.insertSpaceMarine(conn, newSM, coordinateId, chapterId);

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
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    // 根据ID获取SpaceMarine
    public SpaceMarine getSpaceMarineById(long id) throws SQLException {
        SpaceMarine spaceMarine = spaceMarineRepo.getSpaceMarineById(id);

        if (spaceMarine.getName() == null) {
            throw new SQLException("Space marine not found");
        }

        // 这里可以加入其他业务逻辑
        return spaceMarine;
    }

    public void updateSpaceMarine(long id, NewSpaceMarine updatedSM) throws SQLException {
        try (Connection conn = DatabaseUtil.getConnection()) {
            conn.setAutoCommit(false);

            SpaceMarine spaceMarine = getSpaceMarineById(id);
            if (spaceMarine.getName() == null) {
                throw new SQLException("Space marine not found");
            }

            // 更新coordinates表
            spaceMarineRepo.updateCoordinates(conn, updatedSM.getCoordinates(), spaceMarine.getCoordinates().getId());

            // 更新chapter表（如果传入了新的章节信息）
            if (updatedSM.getChapter() != null && updatedSM.getChapter().getName() != null) {
                spaceMarineRepo.updateChapter(conn, updatedSM.getChapter(), spaceMarine.getChapter().getId());
            }

            // 更新space_marine表
            spaceMarineRepo.updateSpaceMarine(conn, updatedSM, id);

            conn.commit();
        }
    }

    public void deleteSpaceMarineByID(long id) throws SQLException {
        spaceMarineRepo.deleteSpaceMarineById(id);
    }

    public void deleteSpaceMarineByHeartCount(int heartCount) throws SQLException {
        spaceMarineRepo.deleteSpaceMarineByHeartCount(heartCount);
    }

    public List<SpaceMarine> getSpaceMarineListByMeleeWeapon(String weapon) throws SQLException {
        return spaceMarineRepo.findByMeleeWeapon(weapon);
    }


    public List<SpaceMarine> getSpaceMarineListByName(String prefix) throws SQLException {
        return spaceMarineRepo.findByNamePrefix(prefix);
    }
}

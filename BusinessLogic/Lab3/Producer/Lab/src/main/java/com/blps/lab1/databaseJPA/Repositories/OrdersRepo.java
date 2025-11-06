package com.blps.lab1.databaseJPA.Repositories;

import com.blps.lab1.databaseJPA.Objects.OrdersJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrdersRepo extends JpaRepository<OrdersJPA, Integer> {

    @Query(value = "select A from OrdersJPA A where A.user_id = ?1")
    List<OrdersJPA> findAllByAccountId(Integer accountID);

    @Query(value = "select A from OrdersJPA A where A.status = 'PENDING' and A.creation_time < :expireTime")
    List<OrdersJPA> findAllUnpaidByExpireTime(LocalDateTime expireTime);
}

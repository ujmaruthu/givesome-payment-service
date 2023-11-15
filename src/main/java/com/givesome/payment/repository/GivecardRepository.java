package com.givesome.payment.repository;

import com.givesome.payment.entity.GivecardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GivecardRepository extends JpaRepository<GivecardEntity,Integer> {

    Optional<GivecardEntity> findByCode(String giveCardCode);

    Optional<GivecardEntity> findById(Integer id);

    Optional<GivecardEntity> findByBatchIdAndUserId(int id, Integer userId);

    List<GivecardEntity> findByBatchId(int id);

    Optional<GivecardEntity> findByCodeAndUserId(String givecardCode, Integer userId);
}

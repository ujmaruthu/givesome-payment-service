package com.givesome.payment.repository;

import com.givesome.payment.entity.GivecardBatchEntity;
import com.givesome.payment.entity.GivecardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GivecardBatchRepository extends JpaRepository<GivecardBatchEntity,Integer> {

    GivecardBatchEntity findByCode(String givecardCode);
}

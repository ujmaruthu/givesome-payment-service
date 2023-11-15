package com.givesome.payment.repository;

import com.givesome.payment.entity.GivecardCampaignEntity;
import com.givesome.payment.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GivecardCampaignRepository extends JpaRepository<GivecardCampaignEntity,Integer> {


    Optional<GivecardCampaignEntity> findBySupplierId(Integer supplierId);

}

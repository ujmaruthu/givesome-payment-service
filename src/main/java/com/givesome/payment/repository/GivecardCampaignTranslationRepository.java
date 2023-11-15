package com.givesome.payment.repository;

import com.givesome.payment.entity.GivecardCampaignEntity;
import com.givesome.payment.entity.GivecardCampaignTranslationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GivecardCampaignTranslationRepository extends JpaRepository<GivecardCampaignTranslationEntity,Integer> {


}

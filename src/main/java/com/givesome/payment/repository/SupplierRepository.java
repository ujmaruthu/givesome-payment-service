package com.givesome.payment.repository;

import com.givesome.payment.entity.GivecardCampaignEntity;
import com.givesome.payment.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<SupplierEntity,Integer> {


}

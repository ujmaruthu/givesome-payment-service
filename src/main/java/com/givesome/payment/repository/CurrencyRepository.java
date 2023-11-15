package com.givesome.payment.repository;

import com.givesome.payment.entity.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurrencyRepository extends JpaRepository<CurrencyEntity,Integer> {

    List<CurrencyEntity> findAllByOrderByIdAsc();
}

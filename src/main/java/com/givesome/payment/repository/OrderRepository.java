package com.givesome.payment.repository;

import com.givesome.payment.entity.CurrencyEntity;
import com.givesome.payment.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity,Integer> {

}

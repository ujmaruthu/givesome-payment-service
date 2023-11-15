package com.givesome.payment.service;

import com.givesome.payment.entity.OrderEntity;
import com.givesome.payment.model.PaymentRequestModel;
import com.givesome.payment.repository.OrderRepository;
import com.stripe.model.Charge;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Log4j2(topic = "Order service")
@Service
public class OrderService {

@Autowired
OrderRepository orderRepository;

    public boolean updateOrderDetails(Charge paymentCreationResponse, PaymentRequestModel paymentRequestModel) {
        try{
        OrderEntity orderEntity=new OrderEntity();
        orderEntity.setCreatedOn(Instant.now());
        orderEntity.setCurrency(paymentCreationResponse.getCurrency());
        orderEntity.setPaymentMethodName("Stripe");
        orderEntity.setLabel("default");
        orderEntity.setDisplayCurrency(paymentCreationResponse.getCurrency());
        orderEntity.setModifiedOn(Instant.now());
        orderEntity.setStatusId(3);
        orderEntity.setShopId(1);
        orderEntity.setDeleted(false);
        orderEntity.setPaymentStatus(2);
        orderEntity.setTaxfulTotalPriceValue(paymentRequestModel.getAmount());
        orderEntity.setTaxlessTotalPriceValue(paymentRequestModel.getAmount());
        var orderResponse=orderRepository.save(orderEntity);
        if(orderResponse!=null){
            orderEntity.setIdentifier(orderResponse.getId());
            orderRepository.save(orderEntity);
            return true;
        }
        else{
            return false;
        }
    }catch (Exception e){
            log.error("Error: Create order - "+e.getMessage());
            return false;
        }
    }
}

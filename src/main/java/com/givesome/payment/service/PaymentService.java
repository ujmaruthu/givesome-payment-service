package com.givesome.payment.service;

import com.givesome.payment.constant.ResponseStatusCode;
import com.givesome.payment.entity.CurrencyEntity;
import com.givesome.payment.entity.PaymentEntity;
import com.givesome.payment.model.PaymentRequestModel;
import com.givesome.payment.model.PaymentResponseModel;
import com.givesome.payment.model.ResponseModel;
import com.givesome.payment.payment_processor.StripePayment;
import com.givesome.payment.repository.CurrencyRepository;
import com.givesome.payment.repository.PaymentRepository;
import com.givesome.payment.repository.ProjectUrlRepository;
import com.givesome.payment.repository.SimpleSupplierStockCountRepository;
import com.stripe.model.Charge;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2(topic = "Payment service")
@Service
public class PaymentService {

    @Autowired
    StripePayment stripePayment;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    SimpleSupplierStockCountRepository simpleSupplierStockCountRepository;

    @Autowired
    GivecardService givecardService;

    @Autowired
    OrderService orderService;

    @Autowired
    ProjectUrlRepository projectUrlRepository;


    public ResponseModel createPayment(PaymentRequestModel paymentRequestModel) {
        ResponseModel responseModel = new ResponseModel();

        try {
            if (paymentRequestModel.getYouGive() != null && paymentRequestModel.getCurrency() != null
                    && !paymentRequestModel.getCurrency().isBlank() && paymentRequestModel.getToken() != null &&
                    !paymentRequestModel.getToken().isBlank() && !isLessThan0_5(paymentRequestModel.getYouGive())
                    && paymentRequestModel.getProjectId() != null && !paymentRequestModel.getProjectId().isBlank()
                    && paymentRequestModel.getSupplierId() != null && !paymentRequestModel.getSupplierId().isBlank()) {
                var paymentCreationResponse = stripePayment.createPayment(paymentRequestModel);
                if (paymentCreationResponse != null && paymentCreationResponse.getStatus().equalsIgnoreCase("succeeded")) {
                    var savePaymentDetailsInDbResponse = savePaymentCreationResponse(paymentCreationResponse, paymentRequestModel);
                    if (savePaymentDetailsInDbResponse != null && reduceLogicalCount(paymentRequestModel) &&
                            givecardService.updateGiveCardBalance(paymentRequestModel)
                            &&orderService.updateOrderDetails(paymentCreationResponse,paymentRequestModel)) {
                        var projectUrl=projectUrlRepository.findByProjectId(Integer.valueOf(paymentRequestModel.getProjectId()));
                        if(projectUrl.isPresent()) {
                            PaymentResponseModel paymentResponseModel = new PaymentResponseModel();
                            paymentResponseModel.setAmount(paymentCreationResponse.getAmount());
                            paymentResponseModel.setStatus(paymentCreationResponse.getStatus());
                            paymentResponseModel.setCreatedTime(Instant.ofEpochSecond(paymentCreationResponse.getCreated()));
                            paymentResponseModel.setPaymentId(paymentCreationResponse.getId());
                            paymentResponseModel.setMoreProjectUrl(projectUrl.get().getMoreProjectUrl());
                            paymentResponseModel.setExclusiveContentUrl(projectUrl.get().getExclusiveContentUrl());
                            responseModel.setStatus(ResponseStatusCode.SUCCESS);
                            responseModel.setMessage("Payment Success");
                            responseModel.setData(paymentResponseModel);
                        }
                        else {
                            responseModel.setStatus(ResponseStatusCode.INTERNAL_SERVER_ERROR);
                            responseModel.setMessage("Payment Failed");
                        }
                    } else {
                        responseModel.setStatus(ResponseStatusCode.INTERNAL_SERVER_ERROR);
                        responseModel.setMessage("Payment Failed");
                    }
                } else {
                    responseModel.setStatus(ResponseStatusCode.INTERNAL_SERVER_ERROR);
                    responseModel.setMessage("Payment Failed");
                }
            } else {
                responseModel.setStatus(ResponseStatusCode.NOT_FOUND_EXCEPTION);
                responseModel.setMessage("Bad Request");
            }
            return responseModel;
        } catch (Exception e) {
            log.error("Error: Create payment  -" + e.getMessage());
            responseModel.setStatus(ResponseStatusCode.INTERNAL_SERVER_ERROR);
            responseModel.setMessage("Payment Failed - " + e.getMessage());
            return responseModel;
        }
    }



    private Boolean reduceLogicalCount(PaymentRequestModel paymentRequestModel) {
        try {
            var simpleSupplierStockCountEntity = simpleSupplierStockCountRepository.findByProductIdAndSupplierId(Integer.parseInt(paymentRequestModel.getProjectId()), Integer.parseInt(paymentRequestModel.getSupplierId()));
            if (simpleSupplierStockCountEntity.isPresent()) {
                var logicalCount = simpleSupplierStockCountEntity.get().getLogicalCount();
                var reducedCount = logicalCount.subtract(paymentRequestModel.getAmount());
                simpleSupplierStockCountEntity.get().setLogicalCount(reducedCount);
                simpleSupplierStockCountEntity.get().setPhysicalCount(reducedCount);
                log.info("REDUCED COUNT - " + reducedCount);
                simpleSupplierStockCountRepository.save(simpleSupplierStockCountEntity.get());
                return true;
            }
            else{
                return false;
            }
        }
        catch (Exception e){
            log.info("Error: Reduce logical count  -"+e.getMessage());
            return false;
        }
    }

    private PaymentEntity savePaymentCreationResponse(Charge chargeCreationResponse, PaymentRequestModel paymentRequestModel) {
        try {
            PaymentEntity paymentEntity=new PaymentEntity();
            paymentEntity.setPaymentMethod(chargeCreationResponse.getPaymentMethod());
            paymentEntity.setPaymentId(chargeCreationResponse.getId());
            paymentEntity.setAmount(chargeCreationResponse.getAmount());
            paymentEntity.setCreatedAt(Instant.ofEpochSecond(chargeCreationResponse.getCreated()));
            paymentEntity.setDescription(chargeCreationResponse.getDescription());
            paymentEntity.setAmountRefunded(chargeCreationResponse.getAmountRefunded());
            paymentEntity.setCurrency(chargeCreationResponse.getCurrency());
            paymentEntity.setStatus(chargeCreationResponse.getStatus());
            paymentEntity.setBalanceTransaction(chargeCreationResponse.getBalanceTransaction());
            paymentEntity.setLivemode(chargeCreationResponse.getLivemode());
            paymentEntity.setAmountRefunded(chargeCreationResponse.getAmountRefunded());
            paymentEntity.setCustomerId(chargeCreationResponse.getCustomer());
            paymentEntity.setCaptured(chargeCreationResponse.getCaptured());
            paymentEntity.setReceiptUrl(chargeCreationResponse.getReceiptUrl());
            paymentEntity.setFailureCode(chargeCreationResponse.getFailureCode());
            paymentEntity.setFailureMessage(chargeCreationResponse.getFailureMessage());
            paymentEntity.setProductId(Integer.valueOf(paymentRequestModel.getProjectId()));
            paymentEntity.setSupplierId(Integer.valueOf(paymentRequestModel.getSupplierId()));
            paymentEntity.setPostalCode(paymentRequestModel.getPostalCode());
            log.info("Supplier_id - "+paymentEntity.getSupplierId());
            log.info("Project_id - "+paymentEntity.getProductId());
            return paymentRepository.save(paymentEntity);
        }catch(Exception e){
            log.error("Error: Save payment creation -"+e.getMessage());
            return null;
        }
    }

    public static boolean isLessThan0_5(BigDecimal value) {
        BigDecimal threshold = new BigDecimal("0.5");
        return value.compareTo(threshold) < 0;
    }

    public ResponseModel getCurrency() {
        ResponseModel responseModel = new ResponseModel();
        try {
            var currencyEntityList = currencyRepository.findAllByOrderByIdAsc();
            List<Map<String, String>> currencyList=new ArrayList<>();
            if (!currencyEntityList.isEmpty()) {
                for (CurrencyEntity currency : currencyEntityList) {
                    Map<String, String> currencyMap = new HashMap<>();
                    currencyMap.put("currencyCode",currency.getCurrencyCode());
                    currencyMap.put("currencyName",currency.getCurrencyName());
                    currencyMap.put("currencySymbol",currency.getCurrencySymbol());
                    currencyList.add(currencyMap);
                }
                responseModel.setStatus(ResponseStatusCode.SUCCESS);
                responseModel.setMessage("Currency List");
                responseModel.setData(currencyList);
            }
        }catch (Exception e){
            log.error("Error: Get Currency List-"+e.getMessage());
            responseModel.setStatus(ResponseStatusCode.INTERNAL_SERVER_ERROR);
            responseModel.setMessage("Get Currency List Failed - "+e.getMessage());
        }
        return responseModel;
    }
}

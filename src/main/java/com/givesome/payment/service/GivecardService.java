package com.givesome.payment.service;

import com.givesome.payment.constant.ResponseStatusCode;
import com.givesome.payment.entity.GivecardBatchEntity;
import com.givesome.payment.entity.GivecardEntity;
import com.givesome.payment.model.*;
import com.givesome.payment.repository.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2(topic = "Givecard service")
@Service
public class GivecardService {


    @Autowired
    GivecardCampaignRepository givecardCampaignRepository;

    @Autowired
    GivecardCampaignImageRepository givecardCampaignImageRepository;
    @Autowired
    GivecardRepository givecardRepository;

    @Autowired
    GivecardBatchRepository givecardBatchRepository;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    GivecardCampaignTranslationRepository givecardCampaignTranslationRepository;


    public ResponseModel getGiveCardResponse(GiveCardRequestModel giveCardRequestModel) {
        ResponseModel responseModel = new ResponseModel();
        try {
            var responseMap=getResponseMap(giveCardRequestModel);
            if (responseMap != null) {
                var giveCardBatch=redeemGivecard(responseMap, giveCardRequestModel);
                if(giveCardBatch!=null){
                    var giveResponseModel=buildGivecardResponse(giveCardRequestModel,responseMap,giveCardBatch);
                    if(giveResponseModel!=null) {
                        responseModel.setStatus(ResponseStatusCode.SUCCESS);
                        responseModel.setMessage("Givecard Response");
                        responseModel.setData(giveResponseModel);
                        log.info("Response " + responseModel.getStatus());
                    }
                    else{
                        responseModel.setStatus(ResponseStatusCode.NOT_FOUND_EXCEPTION);
                        responseModel.setMessage("Givecard campaign not found");
                    }
                }
                else{
                    responseModel.setStatus(ResponseStatusCode.NOT_FOUND_EXCEPTION);
                    responseModel.setMessage("Givecard is not valid");
                }
            }
            else{
                responseModel.setStatus(ResponseStatusCode.NOT_FOUND_EXCEPTION);
                responseModel.setMessage("Givecard is not valid");
            }
            return responseModel;
            } catch (Exception e) {
            log.error("Error: Get Givecard -" + e.getMessage());
            responseModel.setStatus(ResponseStatusCode.INTERNAL_SERVER_ERROR);
            responseModel.setMessage("Get Givecard Failed - " + e.getMessage());
            return responseModel;
        }
    }

    private Map<String,Integer> getResponseMap(GiveCardRequestModel giveCardRequestModel) {
        var giveCardBatchResponse = givecardBatchRepository.findByCode(giveCardRequestModel.getCode());
        if (giveCardBatchResponse != null &&giveCardBatchResponse.getRedemptionEndDate() != null&& !new Date().after(giveCardBatchResponse.getRedemptionEndDate())) {
               return findBalanceFromGivecardBatch(giveCardBatchResponse, giveCardRequestModel.getUserId());
            }
         else {
           return findBalanceFromGivecard(giveCardRequestModel.getCode(), giveCardRequestModel.getUserId());
        }
    }

    private GiveCardResponseModel buildGivecardResponse(GiveCardRequestModel giveCardRequestModel, Map<String, Integer> responseMap, GivecardBatchEntity giveCardBatch) {
        GiveCardResponseModel giveCardResponseModel=new GiveCardResponseModel();
        var campaignResponse=givecardCampaignRepository.findById(giveCardBatch.getCampaignId());
        if(campaignResponse.isPresent()) {
            var campaignImageEntity = givecardCampaignImageRepository.findById(campaignResponse.get().getImageId());
            if (campaignImageEntity.isPresent()) {
                var campaignTranslationEntity = givecardCampaignTranslationRepository.findById(giveCardBatch.getCampaignId());
                if (campaignTranslationEntity.isPresent()) {
                    giveCardResponseModel.setCampaignImage("https://qa.givesome.org//media/" + campaignImageEntity.get().getFile());
                    giveCardResponseModel.setBalance(responseMap.get("balance"));
                    giveCardResponseModel.setGivecardId(responseMap.get("givecardId"));
                    giveCardResponseModel.setCampaignName(campaignTranslationEntity.get().getName());
                    giveCardResponseModel.setCampaignMessage(campaignTranslationEntity.get().getMessage());
                    return giveCardResponseModel;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }

    }

    private Map<String, Integer> findBalanceFromGivecard(String givecardCode, Integer userId) {
        Map<String, Integer> responseMap;
        Integer balance;
        if (userId != null && userId > 0) {
            var giveCardEntityByUserId = givecardRepository.findByCodeAndUserId(givecardCode, userId);
            if (giveCardEntityByUserId.isPresent() && giveCardEntityByUserId.get().getBalance() > 0) {
                var giveCardBatchEntity = givecardBatchRepository.findById(giveCardEntityByUserId.get().getBatchId());
                if (giveCardBatchEntity.isPresent() && !new Date().after(giveCardBatchEntity.get().getRedemptionEndDate())) {
                    balance = giveCardEntityByUserId.get().getBalance();
                    responseMap = getGivecardResponseMap(balance, giveCardEntityByUserId.get());
                } else {
                    return null;
                }
            } else {
                var giveCardEntityByCode = givecardRepository.findByCode(givecardCode);
                if (giveCardEntityByCode.isPresent()) {
                    var giveCardBatchEntity = givecardBatchRepository.findById(giveCardEntityByCode.get().getBatchId());
                    if (giveCardBatchEntity.isPresent() && !new Date().after(giveCardBatchEntity.get().getRedemptionEndDate()) && giveCardEntityByCode.get().getRedeemedOn() == null) {
                        balance = giveCardEntityByCode.get().getBalance();
                        responseMap = getGivecardResponseMap(balance, giveCardEntityByCode.get());
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            }
        } else {
            var giveCardEntityByCode = givecardRepository.findByCode(givecardCode);
            if (giveCardEntityByCode.isPresent()) {
                var giveCardBatchEntity = givecardBatchRepository.findById(giveCardEntityByCode.get().getBatchId());
                if (giveCardBatchEntity.isPresent() && !new Date().after(giveCardBatchEntity.get().getRedemptionEndDate()) && giveCardEntityByCode.get().getRedeemedOn() == null) {
                    balance = giveCardEntityByCode.get().getBalance();
                    responseMap = getGivecardResponseMap(balance, giveCardEntityByCode.get());
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
        return responseMap;
    }

    private Map<String, Integer> findBalanceFromGivecardBatch(GivecardBatchEntity giveCardBatchResponse, Integer userId) {
        Map<String, Integer> responseMap;
        Integer balance;
        Optional<GivecardEntity> giveCardEntity;
        if (userId != null && userId > 0) {
            giveCardEntity = givecardRepository.findByBatchIdAndUserId(giveCardBatchResponse.getId(), userId);
            if (giveCardEntity.isPresent() && giveCardEntity.get().getRedeemedOn() == null) {
                balance = giveCardEntity.get().getBalance();
                responseMap = getGivecardResponseMap(balance, giveCardEntity.get());
            } else if (giveCardEntity.isPresent() && giveCardEntity.get().getRedeemedOn() != null) {
                return null;
            } else {
                var giveCardEntityList = givecardRepository.findByBatchId(giveCardBatchResponse.getId());
                giveCardEntity = giveCardEntityList.stream().filter(s -> s.getRedeemedOn() == null).findFirst();
                if (giveCardEntity.isPresent()) {
                    balance = giveCardEntity.get().getBalance();
                    responseMap = getGivecardResponseMap(balance, giveCardEntity.get());
                } else {
                    return null;
                }
            }
        } else {
            var giveCardEntityList = givecardRepository.findByBatchId(giveCardBatchResponse.getId());
            giveCardEntity = giveCardEntityList.stream().filter(s -> s.getRedeemedOn() == null).findFirst();
            if (giveCardEntity.isPresent()) {
                balance = giveCardEntity.get().getBalance();
                responseMap = getGivecardResponseMap(balance, giveCardEntity.get());
            } else {
                return null;
            }
        }
        return responseMap;
    }

    private Map<String, Integer> getGivecardResponseMap(Integer balance, GivecardEntity givecardEntity) {
        Map<String, Integer> responseMap = new HashMap<>();
        responseMap.put("balance", balance);
        responseMap.put("givecardId", givecardEntity.getId());
        return responseMap;
    }


    public GivecardBatchEntity redeemGivecard(Map<String, Integer> responseMap, GiveCardRequestModel giveCardRequestModel) {
        try {
            var giveCardResponse = givecardRepository.findById(responseMap.get("givecardId"));
            if (giveCardResponse.isPresent()) {
                var givecardBatchEntity=givecardBatchRepository.findById(giveCardResponse.get().getBatchId());
                if(givecardBatchEntity.isPresent()){
                    if(givecardBatchEntity.get().getRestrictionType().equals(2)&&giveCardRequestModel.getSupplierId().equals(givecardBatchEntity.get().getSupplierId())) {
                        giveCardResponse.get().setRedeemedOn(Instant.now());
                        giveCardResponse.get().setUserId(giveCardRequestModel.getUserId());
                        givecardRepository.save(giveCardResponse.get());
                    }
                    else{
                        giveCardResponse.get().setRedeemedOn(Instant.now());
                        giveCardResponse.get().setUserId(giveCardRequestModel.getUserId());
                        givecardRepository.save(giveCardResponse.get());
                    }
                    return givecardBatchEntity.get();
                }
                else{
                    return null;
                }

            }
            else{
                return null;
            }
        } catch (Exception e) {
            log.error("Error: GiveCard Redeem  -" + e.getMessage());
            return null;
        }
    }
    public boolean givecardRedeem(GiveCardRedeemRequestModel giveCardRedeemRequestModel) {
        try {
            if (giveCardRedeemRequestModel.getGivacardBalance() != null ) {
                var giveCardResponse = givecardRepository.findById(giveCardRedeemRequestModel.getGivecardId());
                if (giveCardResponse.isPresent()) {
                    giveCardResponse.get().setBalance(giveCardRedeemRequestModel.getGivacardBalance());
                    giveCardResponse.get().setUserId(giveCardRedeemRequestModel.getUserId());
                    givecardRepository.save(giveCardResponse.get());
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error("Error:  Give Card Redeem  -" + e.getMessage());
            return false;
        }
    }
    public boolean updateGiveCardBalance(PaymentRequestModel paymentRequestModel) {
        try {
            if (paymentRequestModel.getGivacardBalance() != null&&!paymentRequestModel.getGivecardCode().isBlank() ) {
                var giveCardResponse = givecardRepository.findById(paymentRequestModel.getGivecardId());
                if (giveCardResponse.isPresent()) {
                    giveCardResponse.get().setBalance(paymentRequestModel.getGivacardBalance());
                    giveCardResponse.get().setUserId(paymentRequestModel.getUserId());
                    givecardRepository.save(giveCardResponse.get());
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } catch (Exception e) {
            log.error("Error: Update Give Card  -" + e.getMessage());
            return false;
        }
    }
}

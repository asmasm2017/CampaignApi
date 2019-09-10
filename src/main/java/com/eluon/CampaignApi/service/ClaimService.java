package com.eluon.CampaignApi.service;

import com.eluon.CampaignApi.constant.ConstantVar;
import com.eluon.CampaignApi.responseEntity.BaseResponse;
import com.eluon.CampaignApi.entity.ClaimTest;
import com.eluon.CampaignApi.entity.RewardValid;
import com.eluon.CampaignApi.util.MsisdnEncryption;
import com.google.common.hash.Hashing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class ClaimService
{
    ClaimTest claimTest;
    RewardValid rewardValid;
    boolean sspResult;

    private static final Logger LOG = LoggerFactory.getLogger(ClaimService.class);

    public ClaimTest getClaimResponse(String  encryptedMsisdn,String encryptedToken)
    {
        sspResult=true; //DUMMY
        //decrypt msisdn<<<
        MsisdnEncryption me=new MsisdnEncryption(ConstantVar.secretKey);
        String msisdn= me.decrypt(encryptedMsisdn);
        System.out.println("param:"+encryptedMsisdn+ " ,msisdn:"+msisdn);
        //>>>
        //encrypt token and compare it with requester token<<<
        String generatedTokenByService = Hashing.sha256()
                .hashString(ConstantVar.serviceClaimRewardToken, StandardCharsets.UTF_8)
                .toString();
        System.out.println("requester-token:"+encryptedToken+ " ,generated-token:"+generatedTokenByService);
        if(generatedTokenByService.equals(encryptedToken))
        {
            System.out.println("TOKEN MATCH");
        }
        else
        {
            System.out.println("ERROR TOKEN MISMATCH");
            //TODO: response code 130
        }
        //>>>

        LOG.debug("TODO check SSP response for msisdn:"+msisdn);
        claimTest=new ClaimTest();
        if(sspResult==true)
        {
            claimTest.setResponse_code("0");
            claimTest.setResponse_message("ok");
            rewardValid=new RewardValid("500M","7 days");
            claimTest.setData(rewardValid);
        }
        else
        {
            claimTest.setResponse_code("-1");
            claimTest.setResponse_message("failed");
            rewardValid=new RewardValid("500M","7 days");
            claimTest.setData(rewardValid);
        }
        return claimTest;
    }

}

package com.eluon.CampaignApi.service;

import com.eluon.CampaignApi.constant.ConstantVar;
import com.eluon.CampaignApi.dao.DailyCheckinDao;
import com.eluon.CampaignApi.responseEntity.BaseResponse;
import com.eluon.CampaignApi.util.MsisdnEncryption;
import com.google.common.hash.Hashing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class CheckInNowService
{
    private static final Logger LOG = LoggerFactory.getLogger(CheckInNowService.class);
    BaseResponse baseResponse;

    @Autowired
    DailyCheckinDao dailyCheckinDao;

    public BaseResponse getCheckinResponse(String encryptedMsisdn,String encryptedToken)
    {


        boolean sspResult;
        BaseResponse baseResponse;

        //decrypt msisdn<<<
        MsisdnEncryption me=new MsisdnEncryption(ConstantVar.secretKey);
        String msisdn= me.decrypt(encryptedMsisdn);
        System.out.println("param:"+encryptedMsisdn+ " ,msisdn:"+msisdn);
        //>>>

        //encrypt token and compare it with requester token<<<
        String generatedTokenByService = Hashing.sha256()
                .hashString(ConstantVar.serviceCheckInNowToken, StandardCharsets.UTF_8)
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
        /*TODO:  hit module yang ke ssp */
        LOG.debug("TODO hit ssp with msidn:"+msisdn);
        sspResult=true; //DUMMY
        if(sspResult)
        {
            baseResponse=new BaseResponse("0","ok");
        }
        else
        {
            baseResponse=new BaseResponse("-1","failed");
        }

        if(sspResult==true) //insert to DaiylCheckin database
        {
            System.out.println("updating day_30");
            //get day(1-31) from current date
            Date date=new Date();
            SimpleDateFormat formatter=new SimpleDateFormat("dd");
            String todayDate=formatter.format(date);
            System.out.println("date of today:"+todayDate);
            String todayDateChecked;
            if(Integer.parseInt(todayDate)<10)
            {
                todayDateChecked=todayDate.substring(todayDate.length()-1);
            }
            else
            {
                todayDateChecked=todayDate;
            }
            dailyCheckinDao.updateCheckIn(msisdn,'Y',todayDateChecked);
        }
        return baseResponse;

    }
}

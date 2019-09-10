package com.eluon.CampaignApi.service;

import com.eluon.CampaignApi.constant.ConstantVar;
import com.eluon.CampaignApi.dao.DailyCheckinDao;
import com.eluon.CampaignApi.entity.DailyCheckinData;
import com.eluon.CampaignApi.entity.DaysData;
import com.eluon.CampaignApi.entity.MsisdnCheckin;
import com.eluon.CampaignApi.responseEntity.PointRewardResponse;
import com.eluon.CampaignApi.responseEntity.DailyCheckinResponse;
import com.eluon.CampaignApi.util.MsisdnEncryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DailyCheckinService
{
    @Autowired
    DailyCheckinDao dailyCheckinDao;

    MsisdnCheckin msisdnCheckin;
    public MsisdnCheckin getDailyCheckinByMsisdn(String msisdn)
    {
        msisdnCheckin = dailyCheckinDao.getCheckInByMsisdn(msisdn);
        return msisdnCheckin;
    }

    public DailyCheckinResponse getDailyCheckinResponse(String encryptedMsisdn)
    {
        //decrypt msisdn<<<
        MsisdnEncryption me=new MsisdnEncryption(ConstantVar.secretKey);
        String msisdn= me.decrypt(encryptedMsisdn);
        System.out.println("param:"+encryptedMsisdn+ " ,msisdn:"+msisdn);
        //>>>
        DailyCheckinResponse dailyCheckinResponse=new DailyCheckinResponse();
        dailyCheckinResponse.setResponse_code("0");
        dailyCheckinResponse.setResponse_message("sukses");

        MsisdnCheckin msisdnCheckin=new MsisdnCheckin();
        msisdnCheckin=dailyCheckinDao.getCheckInByMsisdn(msisdn);
        DailyCheckinData dailyCheckinData=new DailyCheckinData();
        dailyCheckinData.setAlready_checkin_today(false);
        dailyCheckinData.setCan_claim_reward(true);

        PointRewardResponse pointReward= new PointRewardResponse("10","300MB","15 days");
        PointRewardResponse pointReward2= new PointRewardResponse("20","300MB","30 days");
        PointRewardResponse pointReward3= new PointRewardResponse("30","300MB","60 days");
        List<PointRewardResponse> pointRewardList=new ArrayList<>();
        pointRewardList.add(pointReward);
        pointRewardList.add(pointReward2);
        pointRewardList.add(pointReward3);
        dailyCheckinData.setPointRewardList(pointRewardList);
        dailyCheckinData.setRemaining_point(6);
        dailyCheckinData.setNumber_of_days(30);

        //Days_data
        DaysData daysData2=new DaysData();
        if(0==Character.compare('Y',msisdnCheckin.getDay_1()))
        {
            daysData2.setDay_1("redeemed");
        }
        else if(0==Character.compare('N',msisdnCheckin.getDay_1()))
        {
            daysData2.setDay_1("not_checked");
        }

        daysData2.setDay_2("not_checked");
        dailyCheckinData.setDays_data(daysData2);
        dailyCheckinResponse.setData(dailyCheckinData);


        return dailyCheckinResponse;
    }
}
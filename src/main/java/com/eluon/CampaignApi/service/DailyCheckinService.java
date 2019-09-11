package com.eluon.CampaignApi.service;

import com.eluon.CampaignApi.constant.ConstantVar;
import com.eluon.CampaignApi.dao.DailyCheckinDao;
import com.eluon.CampaignApi.dao.QuotaInfoDao;
import com.eluon.CampaignApi.entity.*;
import com.eluon.CampaignApi.responseEntity.DailyCheckinResponse;
import com.eluon.CampaignApi.responseEntity.MsisdnInfoResponse;
import com.eluon.CampaignApi.util.MsisdnEncryption;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class DailyCheckinService
{
    @Autowired
    DailyCheckinDao dailyCheckinDao;
    @Autowired
    QuotaInfoDao quotaInfoDao;

    public DailyCheckinResponse getDailyCheckinResponse(String encryptedMsisdn, String encryptedToken)
    {

        List<PointReward> pointRewards=new ArrayList<>();

        //decrypt msisdn<<<
        MsisdnEncryption me=new MsisdnEncryption(ConstantVar.secretKey);
        String decryptedMsisdn= me.decrypt(encryptedMsisdn);
        System.out.println("encrypted-msisdn:"+encryptedMsisdn+ " ,decrypted-msisdn:"+decryptedMsisdn);
        //>>>

        //encrypt token to matching requester token<<<
        String generatedTokenByService = Hashing.sha256()
                .hashString(ConstantVar.serviceCheckInToken, StandardCharsets.UTF_8)
                .toString();
        System.out.println("requester-token:"+encryptedToken+ " ,generated-token:"+generatedTokenByService);
        //>>>

        DailyCheckinResponse dailyCheckinResponse=new DailyCheckinResponse();

        if (encryptedMsisdn != null && encryptedMsisdn != "") {

            if (encryptedToken != null && encryptedToken != "") {

                if (encryptedToken.equals(generatedTokenByService)) {



                    dailyCheckinResponse.setResponse_code("0");
                    dailyCheckinResponse.setResponse_message("Success");

                    MsisdnCheckin msisdnCheckin = new MsisdnCheckin();
                    msisdnCheckin = dailyCheckinDao.getCheckInByMsisdn(decryptedMsisdn);
                    System.out.println("msisdn : "+msisdnCheckin.getMsisdn());
                    System.out.println("created-date: "+msisdnCheckin.getCreated_date());
                    System.out.println("updated-date : "+msisdnCheckin.getUpdated_date());
                    System.out.println("day-1 : "+msisdnCheckin.getDay_1());
                    System.out.println("day-2 : "+msisdnCheckin.getDay_2());
                    System.out.println("day-3 : "+msisdnCheckin.getDay_3());
                    System.out.println("day-4 : "+msisdnCheckin.getDay_4());
                    System.out.println("day-5 : "+msisdnCheckin.getDay_5());
                    System.out.println("day-6 : "+msisdnCheckin.getDay_6());
                    System.out.println("day-7 : "+msisdnCheckin.getDay_7());
                    System.out.println("day-8 : "+msisdnCheckin.getDay_8());
                    System.out.println("day-9 : "+msisdnCheckin.getDay_9());
                    System.out.println("day-10 : "+msisdnCheckin.getDay_10());
                    System.out.println("day-11 : "+msisdnCheckin.getDay_11());
                    System.out.println("day-12 : "+msisdnCheckin.getDay_12());
                    System.out.println("day-13 : "+msisdnCheckin.getDay_13());
                    System.out.println("day-14 : "+msisdnCheckin.getDay_14());
                    System.out.println("day-15 : "+msisdnCheckin.getDay_15());
                    System.out.println("day-16 : "+msisdnCheckin.getDay_16());
                    System.out.println("day-17 : "+msisdnCheckin.getDay_17());
                    System.out.println("day-18 : "+msisdnCheckin.getDay_18());
                    System.out.println("day-19 : "+msisdnCheckin.getDay_19());
                    System.out.println("day-20 : "+msisdnCheckin.getDay_20());
                    System.out.println("day-21 : "+msisdnCheckin.getDay_21());
                    System.out.println("day-22 : "+msisdnCheckin.getDay_22());
                    System.out.println("day-23 : "+msisdnCheckin.getDay_23());
                    System.out.println("day-24 : "+msisdnCheckin.getDay_24());
                    System.out.println("day-25 : "+msisdnCheckin.getDay_25());
                    System.out.println("day-26 : "+msisdnCheckin.getDay_26());
                    System.out.println("day-27 : "+msisdnCheckin.getDay_27());
                    System.out.println("day-28 : "+msisdnCheckin.getDay_28());
                    System.out.println("day-29 : "+msisdnCheckin.getDay_29());
                    System.out.println("day-30 : "+msisdnCheckin.getDay_30());
                    System.out.println("day-31 : "+msisdnCheckin.getDay_31());

                    Calendar calendar = Calendar.getInstance();
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int month = calendar.get(Calendar.MONTH)+1;
                    int year = calendar.get(Calendar.YEAR);
                    YearMonth yearMonthObject = YearMonth.of(year, month);
                    int countDaysOnThisMonth = yearMonthObject.lengthOfMonth();

                    int totalCheckinCounter = 0;
                    int totalRedeemedCounter = 0;
                    DailyCheckinData dailyCheckinData = new DailyCheckinData();
                    dailyCheckinData.setAlready_checkin_today(false);
                    dailyCheckinData.setCan_claim_reward(false);

                    List<QuotaInfo> quotaInfoList= quotaInfoDao.getQuotaInfoAll();
                    List<PointReward> pointRewardList=new ArrayList<>();
                    int minimumPointToRedeem = 0;
                    for(QuotaInfo getQuotaInfo:quotaInfoList)
                    {
                        String quotaInfoReward = getQuotaInfo.getReward();
                        String quotaInfoValid = getQuotaInfo.getValid();
                        int quotaInfoRedeemParameter = getQuotaInfo.getRedeem_parameter();
                        int quotaInfoRedeemPoints = getQuotaInfo.getRedeem_points();
                        char quotaInfoStatus = getQuotaInfo.getStatus();
                        String quotaInfoCreatedDate = getQuotaInfo.getCreated_date();
                        String quotaInfoUpdatedDate = getQuotaInfo.getUpdated_date();

                        if(minimumPointToRedeem == 0) {
                            minimumPointToRedeem = quotaInfoRedeemPoints;
                        }

                        PointReward pointReward= new PointReward(quotaInfoReward,quotaInfoValid,quotaInfoRedeemParameter, quotaInfoRedeemPoints, "0 point will expired in 0 days", quotaInfoStatus, quotaInfoCreatedDate, quotaInfoUpdatedDate);
                        pointRewardList.add(pointReward);

                    }
                    dailyCheckinData.setPointRewardList(pointRewardList);
                    dailyCheckinData.setRemaining_point(0);
                    dailyCheckinData.setNumber_of_days(0);

                    DaysData daysData = new DaysData();
                    if(0 == Character.compare('N',msisdnCheckin.getDay_1()))
                    {
                        if (1 < day) {
                            daysData.setDay_1("not_checked");
                        }
                        else {
                            daysData.setDay_1(null);
                        }
                    }
                    else
                    {
                        if(0 == Character.compare('R',msisdnCheckin.getDay_1())) {
                            daysData.setDay_1("redeemed");
                            totalRedeemedCounter++;
                            if (1 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                        else {
                            daysData.setDay_1("checked");
                            totalCheckinCounter++;
                            if (1 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }

                    }

                    if(0 == Character.compare('N',msisdnCheckin.getDay_2()))
                    {
                        if (2 < day) {
                            daysData.setDay_2("not_checked");
                        }
                        else {
                            daysData.setDay_2(null);
                        }
                    }
                    else
                    {
                        if(0 == Character.compare('R',msisdnCheckin.getDay_2())) {
                            daysData.setDay_2("redeemed");
                            totalRedeemedCounter++;
                            if (2 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                        else {
                            daysData.setDay_2("checked");
                            totalCheckinCounter++;
                            if (2 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                    }

                    if(0 == Character.compare('N',msisdnCheckin.getDay_3()))
                    {
                        if (3 < day) {
                            daysData.setDay_3("not_checked");
                        }
                        else {
                            daysData.setDay_3(null);
                        }
                    }
                    else
                    {
                        if(0 == Character.compare('R',msisdnCheckin.getDay_3())) {
                            daysData.setDay_3("redeemed");
                            totalRedeemedCounter++;
                            if (3 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                        else {
                            daysData.setDay_3("checked");
                            totalCheckinCounter++;
                            if (3 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                    }

                    if(0 == Character.compare('N',msisdnCheckin.getDay_4()))
                    {
                        if (4 < day) {
                            daysData.setDay_4("not_checked");
                        }
                        else {
                            daysData.setDay_4(null);
                        }
                    }
                    else
                    {
                        if(0 == Character.compare('R',msisdnCheckin.getDay_4())) {
                            daysData.setDay_4("redeemed");
                            totalRedeemedCounter++;
                            if (4 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                        else {
                            daysData.setDay_4("checked");
                            totalCheckinCounter++;
                            if (4 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                    }

                    if(0 == Character.compare('N',msisdnCheckin.getDay_5()))
                    {
                        if (5 < day) {
                            daysData.setDay_5("not_checked");
                        }
                        else {
                            daysData.setDay_5(null);
                        }
                    }
                    else
                    {
                        if(0 == Character.compare('R',msisdnCheckin.getDay_5())) {
                            daysData.setDay_5("redeemed");
                            totalRedeemedCounter++;
                            if (5 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                        else {
                            daysData.setDay_5("checked");
                            totalCheckinCounter++;
                            if (5 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                    }

                    if(0 == Character.compare('N',msisdnCheckin.getDay_6()))
                    {
                        if (6 < day) {
                            daysData.setDay_6("not_checked");
                        }
                        else {
                            daysData.setDay_6(null);
                        }
                    }
                    else
                    {
                        if(0 == Character.compare('R',msisdnCheckin.getDay_6())) {
                            daysData.setDay_6("redeemed");
                            totalRedeemedCounter++;
                            if (6 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                        else {
                            daysData.setDay_6("checked");
                            totalCheckinCounter++;
                            if (6 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                    }

                    if(0 == Character.compare('N',msisdnCheckin.getDay_7()))
                    {
                        if (7 < day) {
                            daysData.setDay_7("not_checked");
                        }
                        else {
                            daysData.setDay_7(null);
                        }
                    }
                    else
                    {
                        if(0 == Character.compare('R',msisdnCheckin.getDay_7())) {
                            daysData.setDay_7("redeemed");
                            totalRedeemedCounter++;
                            if (7 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }                        }
                        else {
                            daysData.setDay_7("checked");
                            totalCheckinCounter++;
                            if (7 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                    }

                    if(0 == Character.compare('N',msisdnCheckin.getDay_8()))
                    {
                        if (8 < day) {
                            daysData.setDay_8("not_checked");
                        }
                        else {
                            daysData.setDay_8(null);
                        }
                    }
                    else
                    {
                        if(0 == Character.compare('R',msisdnCheckin.getDay_8())) {
                            daysData.setDay_8("redeemed");
                            totalRedeemedCounter++;
                            if (8 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }                        }
                        else {
                            daysData.setDay_8("checked");
                            totalCheckinCounter++;
                            if (8 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                    }

                    if(0 == Character.compare('N',msisdnCheckin.getDay_9()))
                    {
                        if (9 < day) {
                            daysData.setDay_9("not_checked");
                        }
                        else {
                            daysData.setDay_9(null);
                        }
                    }
                    else
                    {
                        if(0 == Character.compare('R',msisdnCheckin.getDay_9())) {
                            daysData.setDay_9("redeemed");
                            totalRedeemedCounter++;
                            if (9 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                        else {
                            daysData.setDay_9("checked");
                            totalCheckinCounter++;
                            if (9 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                    }

                    if(0 == Character.compare('N',msisdnCheckin.getDay_10()))
                    {
                        if (10 < day) {
                            daysData.setDay_10("not_checked");
                        }
                        else {
                            daysData.setDay_10(null);
                        }
                    }
                    else
                    {
                        if(0 == Character.compare('R',msisdnCheckin.getDay_10())) {
                            daysData.setDay_10("redeemed");
                            totalRedeemedCounter++;
                            if (10 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                        else {
                            daysData.setDay_10("checked");
                            totalCheckinCounter++;
                            if (10 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                    }

                    if(0 == Character.compare('N',msisdnCheckin.getDay_11()))
                    {
                        if (11 < day) {
                            daysData.setDay_11("not_checked");
                        }
                        else {
                            daysData.setDay_11(null);
                        }
                    }
                    else
                    {
                        if(0 == Character.compare('R',msisdnCheckin.getDay_11())) {
                            daysData.setDay_11("redeemed");
                            totalRedeemedCounter++;
                            if (11 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }

                        }
                        else {
                            daysData.setDay_11("checked");
                            totalCheckinCounter++;
                            if (11 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                    }

                    if(0 == Character.compare('N',msisdnCheckin.getDay_12()))
                    {
                        if (12 < day) {
                            daysData.setDay_12("not_checked");
                        }
                        else {
                            daysData.setDay_12(null);
                        }
                    }
                    else
                    {
                        if(0 == Character.compare('R',msisdnCheckin.getDay_12())) {
                            daysData.setDay_12("redeemed");
                            totalRedeemedCounter++;
                            if (12 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                        else {
                            daysData.setDay_12("checked");
                            totalCheckinCounter++;
                            if (12 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                    }

                    if(0 == Character.compare('N',msisdnCheckin.getDay_13()))
                    {
                        if (13 < day) {
                            daysData.setDay_13("not_checked");
                        }
                        else {
                            daysData.setDay_13(null);
                        }
                    }
                    else
                    {
                        if(0 == Character.compare('R',msisdnCheckin.getDay_13())) {
                            daysData.setDay_13("redeemed");
                            totalRedeemedCounter++;
                            if (13 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                        else {
                            daysData.setDay_13("checked");
                            totalCheckinCounter++;
                            if (13 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                    }

                    if(0 == Character.compare('N',msisdnCheckin.getDay_14()))
                    {
                        if (14 < day) {
                            daysData.setDay_14("not_checked");
                        }
                        else {
                            daysData.setDay_14(null);
                        }
                    }
                    else
                    {
                        if(0 == Character.compare('R',msisdnCheckin.getDay_14())) {
                            daysData.setDay_14("redeemed");
                            totalRedeemedCounter++;
                            if (14 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                        else {
                            daysData.setDay_14("checked");
                            totalCheckinCounter++;
                            if (14 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                    }

                    if(0 == Character.compare('N',msisdnCheckin.getDay_15()))
                    {
                        if (15 < day) {
                            daysData.setDay_15("not_checked");
                        }
                        else {
                            daysData.setDay_15(null);
                        }
                    }
                    else
                    {
                        if(0 == Character.compare('R',msisdnCheckin.getDay_15())) {
                            daysData.setDay_15("redeemed");
                            totalRedeemedCounter++;
                            if (15 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                        else {
                            daysData.setDay_15("checked");
                            totalCheckinCounter++;
                            if (15 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                    }

                    if(0 == Character.compare('N',msisdnCheckin.getDay_16()))
                    {
                        if (16 < day) {
                            daysData.setDay_16("not_checked");
                        }
                        else {
                            daysData.setDay_16(null);
                        }
                    }
                    else
                    {
                        if(0 == Character.compare('R',msisdnCheckin.getDay_16())) {
                            daysData.setDay_16("redeemed");
                            totalRedeemedCounter++;
                            if (16 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                        else {
                            daysData.setDay_16("checked");
                            totalCheckinCounter++;
                            if (16 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                    }

                    if(0 == Character.compare('N',msisdnCheckin.getDay_17()))
                    {
                        if (17 < day) {
                            daysData.setDay_17("not_checked");
                        }
                        else {
                            daysData.setDay_17(null);
                        }
                    }
                    else
                    {
                        if(0 == Character.compare('R',msisdnCheckin.getDay_17())) {
                            daysData.setDay_17("redeemed");
                            totalRedeemedCounter++;
                            if (17 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                        else {
                            daysData.setDay_17("checked");
                            totalCheckinCounter++;
                            if (17 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                    }

                    if(0 == Character.compare('N',msisdnCheckin.getDay_18()))
                    {
                        if (18 < day) {
                            daysData.setDay_18("not_checked");
                        }
                        else {
                            daysData.setDay_18(null);
                        }
                    }
                    else
                    {
                        if(0 == Character.compare('R',msisdnCheckin.getDay_18())) {
                            daysData.setDay_18("redeemed");
                            totalRedeemedCounter++;
                            if (18 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                        else {
                            daysData.setDay_18("checked");
                            totalCheckinCounter++;
                            if (18 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                    }

                    if(0 == Character.compare('N',msisdnCheckin.getDay_19()))
                    {
                        if (19 < day) {
                            daysData.setDay_19("not_checked");
                        }
                        else {
                            daysData.setDay_19(null);
                        }
                    }
                    else
                    {
                        if(0 == Character.compare('R',msisdnCheckin.getDay_19())) {
                            daysData.setDay_19("redeemed");
                            totalRedeemedCounter++;
                            if (19 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                        else {
                            daysData.setDay_19("checked");
                            totalCheckinCounter++;
                            if (19 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                    }

                    if(0 == Character.compare('N',msisdnCheckin.getDay_20()))
                    {
                        if (20 < day) {
                            daysData.setDay_20("not_checked");
                        }
                        else {
                            daysData.setDay_20(null);
                        }
                    }
                    else
                    {
                        if(0 == Character.compare('R',msisdnCheckin.getDay_20())) {
                            daysData.setDay_20("redeemed");
                            totalRedeemedCounter++;
                            if (20 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                        else {
                            daysData.setDay_20("checked");
                            totalCheckinCounter++;
                            if (20 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                    }

                    if(0 == Character.compare('N',msisdnCheckin.getDay_21()))
                    {
                        if (21 < day) {
                            daysData.setDay_21("not_checked");
                        }
                        else {
                            daysData.setDay_21(null);
                        }
                    }
                    else
                    {
                        if(0 == Character.compare('R',msisdnCheckin.getDay_21())) {
                            daysData.setDay_21("redeemed");
                            totalRedeemedCounter++;
                            if (21 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                        else {
                            daysData.setDay_21("checked");
                            totalCheckinCounter++;
                            if (21 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                    }

                    if(0 == Character.compare('N',msisdnCheckin.getDay_22()))
                    {
                        if (22 < day) {
                            daysData.setDay_22("not_checked");
                        }
                        else {
                            daysData.setDay_22(null);
                        }
                    }
                    else
                    {
                        if(0 == Character.compare('R',msisdnCheckin.getDay_22())) {
                            daysData.setDay_22("redeemed");
                            totalRedeemedCounter++;
                            if (22 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                        else {
                            daysData.setDay_22("checked");
                            totalCheckinCounter++;
                            if (22 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                    }

                    if(0 == Character.compare('N',msisdnCheckin.getDay_23()))
                    {
                        if (23 < day) {
                            daysData.setDay_23("not_checked");
                        }
                        else {
                            daysData.setDay_23(null);
                        }
                    }
                    else
                    {
                        if(0 == Character.compare('R',msisdnCheckin.getDay_23())) {
                            daysData.setDay_23("redeemed");
                            totalRedeemedCounter++;
                            if (23 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                        else {
                            daysData.setDay_23("checked");
                            totalCheckinCounter++;
                            if (23 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                    }

                    if(0 == Character.compare('N',msisdnCheckin.getDay_24()))
                    {
                        if (24 < day) {
                            daysData.setDay_24("not_checked");
                        }
                        else {
                            daysData.setDay_24(null);
                        }
                    }
                    else
                    {
                        if(0 == Character.compare('R',msisdnCheckin.getDay_24())) {
                            daysData.setDay_24("redeemed");
                            totalRedeemedCounter++;
                            if (24 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                        else {
                            daysData.setDay_24("checked");
                            totalCheckinCounter++;
                            if (24 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                    }

                    if(0 == Character.compare('N',msisdnCheckin.getDay_25()))
                    {
                        if (25 < day) {
                            daysData.setDay_25("not_checked");
                        }
                        else {
                            daysData.setDay_25(null);
                        }
                    }
                    else
                    {
                        if(0 == Character.compare('R',msisdnCheckin.getDay_25())) {
                            daysData.setDay_25("redeemed");
                            totalRedeemedCounter++;
                            if (25 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                        else {
                            daysData.setDay_25("checked");
                            totalCheckinCounter++;
                            if (25 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                    }

                    if(0 == Character.compare('N',msisdnCheckin.getDay_26()))
                    {
                        if (26 < day) {
                            daysData.setDay_26("not_checked");
                        }
                        else {
                            daysData.setDay_26(null);
                        }
                    }
                    else
                    {
                        if(0 == Character.compare('R',msisdnCheckin.getDay_26())) {
                            daysData.setDay_26("redeemed");
                            totalRedeemedCounter++;
                            if (26 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                        else {
                            daysData.setDay_26("checked");
                            totalCheckinCounter++;
                            if (26 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                    }

                    if(0 == Character.compare('N',msisdnCheckin.getDay_27()))
                    {
                        if (27 < day) {
                            daysData.setDay_27("not_checked");
                        }
                        else {
                            daysData.setDay_27(null);
                        }
                    }
                    else
                    {
                        if(0 == Character.compare('R',msisdnCheckin.getDay_27())) {
                            daysData.setDay_27("redeemed");
                            totalRedeemedCounter++;
                            if (27 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                        else {
                            daysData.setDay_27("checked");
                            totalCheckinCounter++;
                            if (27 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                    }

                    if(0 == Character.compare('N',msisdnCheckin.getDay_28()))
                    {
                        if (28 < day) {
                            daysData.setDay_28("not_checked");
                        }
                        else {
                            daysData.setDay_28(null);
                        }
                    }
                    else
                    {
                        if(0 == Character.compare('R',msisdnCheckin.getDay_28())) {
                            daysData.setDay_28("redeemed");
                            totalRedeemedCounter++;
                            if (28 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                        else {
                            daysData.setDay_28("checked");
                            totalCheckinCounter++;
                            if (28 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                    }

                    if(0 == Character.compare('N',msisdnCheckin.getDay_29()))
                    {
                        if (29 < day) {
                            daysData.setDay_29("not_checked");
                        }
                        else {
                            daysData.setDay_29(null);
                        }
                    }
                    else
                    {
                        if(0 == Character.compare('R',msisdnCheckin.getDay_29())) {
                            daysData.setDay_29("redeemed");
                            totalRedeemedCounter++;
                            if (29 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                        else {
                            daysData.setDay_29("checked");
                            totalCheckinCounter++;
                            if (29 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                    }

                    if(0 == Character.compare('N',msisdnCheckin.getDay_30()))
                    {
                        if (30 < day) {
                            daysData.setDay_30("not_checked");
                        }
                        else {
                            daysData.setDay_30(null);
                        }
                    }
                    else
                    {
                        if(0 == Character.compare('R',msisdnCheckin.getDay_30())) {
                            daysData.setDay_30("redeemed");
                            totalRedeemedCounter++;
                            if (30 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                        else {
                            daysData.setDay_30("checked");
                            totalCheckinCounter++;
                            if (30 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                    }

                    if(0 == Character.compare('N',msisdnCheckin.getDay_31()))
                    {
                        if (31 < day) {
                            daysData.setDay_31("not_checked");
                        }
                        else {
                            daysData.setDay_31(null);
                        }
                    }
                    else
                    {
                        if(0 == Character.compare('R',msisdnCheckin.getDay_31())) {
                            daysData.setDay_31("redeemed");
                            totalRedeemedCounter++;
                            if (31 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                        else {
                            daysData.setDay_31("checked");
                            totalCheckinCounter++;
                            if (31 == day) {
                                dailyCheckinData.setAlready_checkin_today(true);
                            }
                        }
                    }

                    int totalNotCheckinCounter = day-(totalCheckinCounter+totalRedeemedCounter);
                    int totalNextCheckinCounter = countDaysOnThisMonth-day;
                    String nowDate = year+"-"+month+"-"+day;
                    System.out.println("now-date : "+nowDate);
                    System.out.println("total-checkin : "+totalCheckinCounter);
                    System.out.println("total-redeemed : "+totalRedeemedCounter);
                    System.out.println("total-notcheckin : "+totalNotCheckinCounter);
                    System.out.println("total-nextcheckin : "+totalNextCheckinCounter);
                    System.out.println("total-daysonthismonth : "+countDaysOnThisMonth);

                    System.out.println("minimum-pointtoredeem "+minimumPointToRedeem+" # now-point "+totalCheckinCounter);
                    if (totalCheckinCounter >= minimumPointToRedeem) {
                        System.out.println("setting can redeem flag : true");
                        dailyCheckinData.setCan_claim_reward(true);
                    }
                    else {
                        System.out.println("keep can redeem flag : false");
                    }
                    dailyCheckinResponse.setData(dailyCheckinData);
                    dailyCheckinData.setRemaining_point(totalCheckinCounter);
                    dailyCheckinData.setNumber_of_days(countDaysOnThisMonth);
                    dailyCheckinData.setDays_data(daysData);





                }
                else {
                    dailyCheckinResponse.setResponse_code("130");
                    dailyCheckinResponse.setResponse_message("Error : Header Token mismatch");
                }

            }
            else {
                dailyCheckinResponse.setResponse_code("121");
                dailyCheckinResponse.setResponse_message("Error : No TOKEN Header");
            }
        }
        else {
            dailyCheckinResponse.setResponse_code("120");
            dailyCheckinResponse.setResponse_message("Error : No MSISDN Header");
        }

        return dailyCheckinResponse;
    }

}
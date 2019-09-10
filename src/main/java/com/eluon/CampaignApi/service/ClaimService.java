package com.eluon.CampaignApi.service;

import com.eluon.CampaignApi.constant.ConstantVar;
import com.eluon.CampaignApi.dao.DailyCheckinDao;
import com.eluon.CampaignApi.dao.MsisdnInfoDao;
import com.eluon.CampaignApi.dao.QuotaInfoDao;
import com.eluon.CampaignApi.entity.*;
import com.eluon.CampaignApi.util.MsisdnEncryption;
import com.google.common.hash.Hashing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class ClaimService
{
	@Autowired
    SSPService sspService;
	
    ClaimTest claimTest;
    RewardValid rewardValid;
    SSPClaimResult sspResult;
    @Autowired
    MsisdnInfoDao msisdnInfoDao;
    @Autowired
    MsisdnInfo msisdnInfo;
    @Autowired
    DailyCheckinDao dailyCheckinDao;
    @Autowired
    QuotaInfo quotaInfo;
    @Autowired
    QuotaInfoDao quotaInfoDao;

    @Autowired
	 // SSPClaimResult sspResult;
    private static final Logger LOG = LoggerFactory.getLogger(ClaimService.class);

    public ClaimTest getClaimResponse(String  encryptedMsisdn,String encryptedToken)
    {

        int pointBeforeClaim;
        int pointToClaim;
        //decrypt msisdn<<<
        MsisdnEncryption me=new MsisdnEncryption(ConstantVar.secretKey);
        String msisdn= me.decrypt(encryptedMsisdn);
        System.out.println("param:"+encryptedMsisdn+ " ,msisdn:"+msisdn);
        claimTest=new ClaimTest();
        if(msisdn==null || encryptedMsisdn.equalsIgnoreCase(""))
        {
            claimTest.setResponse_code("120");
            claimTest.setResponse_message("Error : No MSISDN Header");
            return claimTest;
        }
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
            claimTest.setResponse_code("130");
            claimTest.setResponse_message("Error : Header Token mismatch");
            return claimTest;
        }
        //>>>
    // CLAIM with particular point<<<<
        msisdnInfo=msisdnInfoDao.getMsisdnInfoByMsisdn(msisdn);
        pointBeforeClaim=msisdnInfo.getPoint();
        List<QuotaInfo> quotaInfoList= quotaInfoDao.getQuotaInfoAll();
        QuotaInfo selectedQuotaInfo= new QuotaInfo();
        int pointToClaimTemp=0;
        for(QuotaInfo tempQuotaInfo:quotaInfoList)
        {
            if((tempQuotaInfo.getRedeem_points() <= pointBeforeClaim) && (tempQuotaInfo.getRedeem_points()>pointToClaimTemp))
            {
                pointToClaimTemp=tempQuotaInfo.getRedeem_points();
                selectedQuotaInfo=tempQuotaInfo;
            }
        }
        System.out.println("selectedQuotaInfo point:"+selectedQuotaInfo.getRedeem_points()+" ,reward:"+selectedQuotaInfo.getReward());

        pointToClaim=selectedQuotaInfo.getRedeem_points(); //hardcoded TODO: get based on claim reward and point

		sspResult = sspService.claim(msisdn,pointToClaim);
        LOG.debug("hit SSP response with msisdn:"+msisdn+" ,pointToClaim:"+pointToClaim);
		claimTest=new ClaimTest();
        if(sspResult.result)
        {
            claimTest.setResponse_code("0");
            claimTest.setResponse_message("ok");
            rewardValid=new RewardValid(selectedQuotaInfo.getReward(),selectedQuotaInfo.getValid());
            claimTest.setData(rewardValid);
        }
        else
        {
            claimTest.setResponse_code("100");
            claimTest.setResponse_message("internal error : connection to ssp failed");
            rewardValid=new RewardValid("0MB"," ");
            claimTest.setData(rewardValid);
            return claimTest;
        }


        int pointAfterClaim=pointBeforeClaim-pointToClaim;
        //deduct from current point
        msisdnInfoDao.updateMsisdnInfo(pointAfterClaim,msisdn);
        System.out.println("point beforeclaim:"+pointBeforeClaim+" ,toClaim:"+pointToClaim+" ,afterClaim:"+pointAfterClaim);
        System.out.println("update msisdn_info set new point:"+pointAfterClaim+" where msisdn="+msisdn);
    //>>>>
        return claimTest;
    }

}

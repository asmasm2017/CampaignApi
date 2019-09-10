package com.eluon.CampaignApi.controller;

import com.eluon.CampaignApi.constant.ConstantVar;
import com.eluon.CampaignApi.responseEntity.*;
import com.eluon.CampaignApi.entity.*;
import com.eluon.CampaignApi.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping("/im3community")
public class CampaignController
{
    private static final Logger LOG = LoggerFactory.getLogger(CampaignController.class);

    @Autowired
    MsisdnTrackingService msisdnTrackingService;
    @Autowired
    CampaignAdsRequest campaignAdsRequest;
    @Autowired
    CheckInNowService checkInNowService;
    @Autowired
    ClaimService claimService;
    @Autowired
    CampaignAdsService campaignAdsService;
    @Autowired
    MsisdnInfoService msisdnInfoService;
    @Autowired
    DailyCheckinResponse dailyCheckinResponse;
    @Autowired
    DailyCheckinService dailyCheckinService;





    @RequestMapping(value = "/checkheader/{input}", method = RequestMethod.GET)
    public void printHeader(@PathVariable("input") String input)
    {
        LOG.debug("from url , input:" + input);
        System.out.println("input = " + input);
    }



    @RequestMapping(value= "/msisdn_tracking" , method =RequestMethod.POST)
    public List<MsisdnTracking> getAllMsisdnTrackingList()
    {
        return msisdnTrackingService.getAllMsisdnTracking();
    }


    //BELOW API'S as GOOGLEDOCS
//    @RequestMapping(value = "/campaign_ads" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public CampaignAdsResponse campaign_ads(@RequestBody CampaignAdsRequest campaignAdsRequest, @RequestHeader("msisdn") String msisdn)
//    {
//        CampaignAdsResponse campaignAdsResponse;
//        campaignAdsResponse=campaignAdsService.getCampaignAdsResponse(msisdn,campaignAdsRequest);
//        //return new CampaignAdsResponse("http://url.to.content");
//
//        return campaignAdsResponse;
//    }



    @RequestMapping(value = "/campaign_ads" , method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void campaign_ads(@RequestBody CampaignAdsRequest campaignAdsRequest, @RequestHeader("msisdn") String msisdn,HttpServletResponse httpServletResponse,@RequestHeader("token") String encryptedToken)
     {
        String redirect_to_url=null;
        String default_url= ConstantVar.defaultRedirectToUrl;
        redirect_to_url=campaignAdsService.getCampaignAdsResponse(msisdn,campaignAdsRequest,encryptedToken);
        if(redirect_to_url!=null)
        {
            System.out.println("redirect to url_asset:"+redirect_to_url);
            httpServletResponse.setHeader("Location", redirect_to_url);
        }
        else
        {
            System.out.println("redirect to url_asset:"+default_url);
            httpServletResponse.setHeader("Location", default_url);
        }
        httpServletResponse.setStatus(302);

    }

    @RequestMapping(value = "/check_in_now" , method = RequestMethod.POST)
    public BaseResponse checkInNow(@RequestHeader("msisdn") String msisdn,@RequestHeader("token") String encryptedToken)
    {
        BaseResponse baseResponse =checkInNowService.getCheckinResponse(msisdn,encryptedToken);
        return baseResponse;
    }
    @RequestMapping(value = "/claim" , method = RequestMethod.POST)
    public ClaimResponse ClaimResponse(@RequestHeader("msisdn") String msisdn,@RequestHeader("token") String encryptedToken)
    {
       //ClaimTest claimTest = new ClaimTest("0","ok",new RewardValid("500M","7 days"));
        ClaimResponse claimResponse= claimService.getClaimResponse(msisdn,encryptedToken);
        return claimResponse;
    }

    @RequestMapping(value = "/msisdn_info" , method = RequestMethod.POST)
    public MsisdnInfoResponse msisdnInfo(@RequestHeader("msisdn") String msisdn)
    {
        return msisdnInfoService.getMsisdnInfoResponse(msisdn);
    }

    @RequestMapping(value = "/daily_check_in" , method = RequestMethod.POST)
    public DailyCheckinResponse dailyCheckin(@RequestHeader("msisdn") String msisdn)
    {
        return dailyCheckinService.getDailyCheckinResponse(msisdn);
    }









    //BELOW API'S ARE FOR TESTING PURPOSE

//    @RequestMapping(value = "/check_in_now_test" , method = RequestMethod.POST)
//    public BaseResponse checkInNowTest()
//    {
//        BaseResponse baseResponse = new BaseResponse("0","ok");
//        return baseResponse;
//    }
//    @RequestMapping(value = "/daily_checkin_test" , method = RequestMethod.POST)
//    public DailyCheckinTest DailyCheckinTest()
//    {
//        DailyCheckinTest dailyCheckinTest;
//        List<PointReward> pointRewardList=new ArrayList<>();
//        PointReward pointReward1= new PointReward("10","500MB","7 days");
//        PointReward pointReward2= new PointReward("20","1GB","14 days");
//        PointReward pointReward3= new PointReward("30","2GB","30 days");
//        pointRewardList.add(pointReward1);
//        pointRewardList.add(pointReward2);
//        pointRewardList.add(pointReward3);
//        DaysData days_data= new DaysData("redeemed","not_checked","checked","","","","","","","","","","","","","","","","","","","","","","","","","","","","");
//        //public DailyCheckinDataTest(boolean already_checkin_today, boolean can_claim_reward, List<PointReward> pointRewardList, int remaining_point, int number_of_days, DaysData days_data)
//        DailyCheckinData dailyCheckinData =new DailyCheckinData(false,false,pointRewardList,6,30,days_data);
//        dailyCheckinTest=new DailyCheckinTest("0","ok", dailyCheckinData);
//        return dailyCheckinTest;
//    }
//
//    @RequestMapping(value = "/claim_test" , method = RequestMethod.POST)
//    public ClaimTest claimTest()
//    {
//
//        ClaimTest claimTest = new ClaimTest("0","ok",new RewardValid("500M","7 days"));
//        return claimTest;
//    }
//    @RequestMapping(value = "/msisdn_info_test" , method = RequestMethod.POST)
//    public MsisdnInfoTest msisdnInfoTest()
//    {
//        List<ImageTitleUrl> imageTitleUrls;
//        imageTitleUrls=new ArrayList<>();
//
//        ImageTitleUrl imageTitleUrl=new ImageTitleUrl("http://url_to_image1","berita1","http://url_to_berita1");
//        ImageTitleUrl imageTitleUrl2=new ImageTitleUrl("http://url_to_image2","berita2","http://url_to_berita2");
//        ImageTitleUrl imageTitleUrl3=new ImageTitleUrl("http://url_to_image3","berita3","http://url_to_berita3");
//        imageTitleUrls.add(imageTitleUrl);
//        imageTitleUrls.add(imageTitleUrl2);
//        imageTitleUrls.add(imageTitleUrl3);
//        MsisdnInfoTest msisdnInfoTest = new MsisdnInfoTest("-1","failed2",new MsisdnBalanceQuota("0812959590001",3000,12000,imageTitleUrls,imageTitleUrls));
//        return msisdnInfoTest;
//    }

}

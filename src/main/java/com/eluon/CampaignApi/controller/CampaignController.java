package com.eluon.CampaignApi.controller;

import com.eluon.CampaignApi.constant.ConstantVar;
import com.eluon.CampaignApi.responseEntity.BaseResponse;
import com.eluon.CampaignApi.responseEntity.CampaignAdsResponse;
import com.eluon.CampaignApi.entity.*;
import com.eluon.CampaignApi.responseEntity.DailyCheckinResponse;
import com.eluon.CampaignApi.responseEntity.MsisdnInfoResponse;
import com.eluon.CampaignApi.service.*;
import com.sun.syndication.io.FeedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
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

//    @RequestMapping(value = "/msisdn_info_dummy", method = RequestMethod.POST)
//    public Collection<MsisdnInfo> getAllMsisdnInfo()
//    {
//        return msisdnInfoService.getAllMsisdnInfo();
//    }

    @RequestMapping(value= "/msisdn_tracking" , method =RequestMethod.POST)
    public List<MsisdnTracking> getAllMsisdnTrackingList()
    {
        return msisdnTrackingService.getAllMsisdnTracking();
    }


    //BELOW API'S as GOOGLEDOCS


    @RequestMapping(value = "/campaign_ads" , method = RequestMethod.GET)
    public void campaign_ads(@RequestParam("type") String type, @RequestParam("width") String width,@RequestParam("height") String height, @RequestHeader("msisdn") String msisdn,HttpServletResponse httpServletResponse,@RequestHeader("token") String encryptedToken)
    {
        String redirect_to_url=null;
        String default_url= ConstantVar.defaultRedirectToUrl;
        campaignAdsRequest.setHeight(height);
        campaignAdsRequest.setType(type);
        campaignAdsRequest.setWeight(width);
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


//
//    @RequestMapping(value = "/campaign_ads" , method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public void campaign_ads(@RequestBody CampaignAdsRequest campaignAdsRequest, @RequestHeader("msisdn") String msisdn,HttpServletResponse httpServletResponse,@RequestHeader("token") String encryptedToken)
//     {
//        String redirect_to_url=null;
//        String default_url= ConstantVar.defaultRedirectToUrl;
//        redirect_to_url=campaignAdsService.getCampaignAdsResponse(msisdn,campaignAdsRequest,encryptedToken);
//        if(redirect_to_url!=null)
//        {
//            System.out.println("redirect to url_asset:"+redirect_to_url);
//            httpServletResponse.setHeader("Location", redirect_to_url);
//        }
//        else
//        {
//            System.out.println("redirect to url_asset:"+default_url);
//            httpServletResponse.setHeader("Location", default_url);
//        }
//        httpServletResponse.setStatus(302);
//
//    }

    @RequestMapping(value = "/check_in_now" , method = RequestMethod.POST)
    public BaseResponse checkInNow(@RequestHeader("msisdn") String msisdn,@RequestHeader("token") String encryptedToken)
    {
        BaseResponse baseResponse =checkInNowService.getCheckinResponse(msisdn,encryptedToken);
        return baseResponse;
    }
    @RequestMapping(value = "/claim" , method = RequestMethod.POST)
    public ClaimTest claimTest(@RequestHeader("msisdn") String msisdn,@RequestHeader("token") String encryptedToken)
    {
       //ClaimTest claimTest = new ClaimTest("0","ok",new RewardValid("500M","7 days"));
        ClaimTest claimTest= claimService.getClaimResponse(msisdn,encryptedToken);
        return claimTest;
    }

    @RequestMapping(value = "/msisdn_info" , method = RequestMethod.POST)
    public MsisdnInfoResponse msisdnInfo(@RequestHeader("msisdn") String encryptedMsisdn, @RequestHeader("token") String encryptedToken)
   {
        MsisdnInfoResponse msisdnInfoResponse = null;
        try {
            msisdnInfoResponse = msisdnInfoService.getMsisdnInfoResponse(encryptedMsisdn, encryptedToken);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FeedException e) {
            e.printStackTrace();
        }
        return msisdnInfoResponse;

    }

    @RequestMapping(value = "/daily_check_in" , method = RequestMethod.POST)
    public DailyCheckinResponse dailyCheckin(@RequestHeader("msisdn") String encryptedMsisdn, @RequestHeader("token") String encryptedToken)
    {
        return dailyCheckinService.getDailyCheckinResponse(encryptedMsisdn, encryptedToken);
    }









    //BELOW API'S ARE FOR TESTING PURPOSE

    @RequestMapping(value = "/check_in_now_test" , method = RequestMethod.POST)
    public BaseResponse checkInNowTest()
    {
        BaseResponse baseResponse = new BaseResponse("0","ok");
        return baseResponse;
    }
    @RequestMapping(value = "/daily_checkin_test" , method = RequestMethod.POST)
    public DailyCheckinTest DailyCheckinTest()
    {
        DailyCheckinTest dailyCheckinTest;
        List<PointReward> pointRewardList=new ArrayList<>();
        PointReward pointReward1= new PointReward("0MB","0Days",0, 0, (char) 0, "04111989", "04111989");
        PointReward pointReward2= new PointReward("0MB","0Days",0, 0, (char) 0, "04111989", "04111989");
        PointReward pointReward3= new PointReward("0MB","0Days",0, 0, (char) 0, "04111989", "04111989");
        pointRewardList.add(pointReward1);
        pointRewardList.add(pointReward2);
        pointRewardList.add(pointReward3);
        DaysData days_data= new DaysData("redeemed","not_checked","checked","","","","","","","","","","","","","","","","","","","","","","","","","","","","");
        //public DailyCheckinDataTest(boolean already_checkin_today, boolean can_claim_reward, List<PointReward> pointRewardList, int remaining_point, int number_of_days, DaysData days_data)
        DailyCheckinData dailyCheckinData =new DailyCheckinData(false,false,pointRewardList,6,30,days_data);
        dailyCheckinTest=new DailyCheckinTest("0","ok", dailyCheckinData);
        return dailyCheckinTest;
    }

    @RequestMapping(value = "/claim_test" , method = RequestMethod.POST)
    public ClaimTest claimTest()
    {

        ClaimTest claimTest = new ClaimTest("0","ok",new RewardValid("500M","7 days"));
        return claimTest;
    }
    @RequestMapping(value = "/msisdn_info_test" , method = RequestMethod.POST)
    public MsisdnInfoTest msisdnInfoTest()
    {
        List<ImageTitleUrlTrending> imageTitleUrlsTrending;
        imageTitleUrlsTrending=new ArrayList<>();
        List<ImageTitleUrlInteresting> imageTitleUrlsInteresting;
        imageTitleUrlsInteresting=new ArrayList<>();

        ImageTitleUrlTrending imageTitleUrlTrending=new ImageTitleUrlTrending("http://url_to_image1","berita1","http://url_to_berita1");
        ImageTitleUrlTrending imageTitleUrlTrending2=new ImageTitleUrlTrending("http://url_to_image2","berita2","http://url_to_berita2");
        ImageTitleUrlTrending imageTitleUrlTrending3=new ImageTitleUrlTrending("http://url_to_image3","berita3","http://url_to_berita3");
        imageTitleUrlsTrending.add(imageTitleUrlTrending);
        imageTitleUrlsTrending.add(imageTitleUrlTrending2);
        imageTitleUrlsTrending.add(imageTitleUrlTrending3);
        ImageTitleUrlInteresting imageTitleUrlInteresting=new ImageTitleUrlInteresting("http://url_to_image1","berita1","http://url_to_berita1");
        ImageTitleUrlInteresting imageTitleUrlInteresting2=new ImageTitleUrlInteresting("http://url_to_image2","berita2","http://url_to_berita2");
        ImageTitleUrlInteresting imageTitleUrlInteresting3=new ImageTitleUrlInteresting("http://url_to_image3","berita3","http://url_to_berita3");
        imageTitleUrlsInteresting.add(imageTitleUrlInteresting);
        imageTitleUrlsInteresting.add(imageTitleUrlInteresting2);
        imageTitleUrlsInteresting.add(imageTitleUrlInteresting3);
        MsisdnInfoTest msisdnInfoTest = new MsisdnInfoTest("-1","failed2",new MsisdnBalanceQuota("0812959590001",0,0,0,12000,imageTitleUrlsTrending,imageTitleUrlsInteresting));
        return msisdnInfoTest;
    }

}

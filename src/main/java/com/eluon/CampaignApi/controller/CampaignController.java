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


    @RequestMapping("/")
    public String hello() {
    	return "Im3Community is working";
    }


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

}

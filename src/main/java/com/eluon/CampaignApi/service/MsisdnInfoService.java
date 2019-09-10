package com.eluon.CampaignApi.service;

import com.eluon.CampaignApi.constant.ConstantVar;
import com.eluon.CampaignApi.dao.RssDao;
import com.eluon.CampaignApi.entity.ImageTitleUrl;
import com.eluon.CampaignApi.entity.MsisdnBalanceQuota;
import com.eluon.CampaignApi.entity.Rss;
import com.eluon.CampaignApi.responseEntity.MsisdnInfoResponse;
import com.eluon.CampaignApi.util.MsisdnEncryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class MsisdnInfoService
{
    @Autowired
    RssDao rssDao;

    public MsisdnInfoResponse getMsisdnInfoResponse(String encryptedMsisdn)
    {
        List<ImageTitleUrl> imageTitleUrls=new ArrayList<>();

        //decrypt msisdn<<<
        MsisdnEncryption me=new MsisdnEncryption(ConstantVar.secretKey);
        String msisdn= me.decrypt(encryptedMsisdn);
        System.out.println("param:"+encryptedMsisdn+ " ,msisdn:"+msisdn);
        //>>>


        MsisdnInfoResponse msisdnInfoResponse=new MsisdnInfoResponse();
        msisdnInfoResponse.setResponse_code("0");
        msisdnInfoResponse.setResponse_message("this is ok");

        MsisdnBalanceQuota msisdnBalanceQuota=new MsisdnBalanceQuota();
        msisdnBalanceQuota.setBalance(5000);
        msisdnBalanceQuota.setQuota(20000);
        msisdnBalanceQuota.setMsisdn("081295950001");

        ImageTitleUrl imageTitleUrl1 = new ImageTitleUrl("http://image1.jpg", "title1", "http://url1.com");
        ImageTitleUrl imageTitleUrl2=new ImageTitleUrl("http://image2.jpg","title2","http://url2.com");
        imageTitleUrls.add(imageTitleUrl1);
        imageTitleUrls.add(imageTitleUrl2);
        msisdnBalanceQuota.setRss_interesting(imageTitleUrls);
        msisdnBalanceQuota.setRss_trending(imageTitleUrls);

        msisdnInfoResponse.setData(msisdnBalanceQuota);
        //RSS get <<<<
        List<Rss> rssList= rssDao.getAllRssWithType("interesting");
        StringBuilder sb;
        for(Rss testRss:rssList)
        {
            sb=new StringBuilder("\n");
            sb.append("source_url:").append(testRss.getSource_url());
            sb.append(",limit:").append(testRss.getLimit());
            sb.append(",status:").append(testRss.getStatus());
            sb.append(",type:").append(testRss.getType());
            System.out.println(sb.toString());
        }
        //>>>RSS get

        return msisdnInfoResponse;
    }

}

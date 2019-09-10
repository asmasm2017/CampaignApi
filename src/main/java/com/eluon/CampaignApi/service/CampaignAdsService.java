package com.eluon.CampaignApi.service;

import com.eluon.CampaignApi.constant.ConstantVar;
import com.eluon.CampaignApi.dao.PlaceholderTemplateDao;
import com.eluon.CampaignApi.entity.CampaignAdsData;
import com.eluon.CampaignApi.entity.CampaignAdsRequest;
import com.eluon.CampaignApi.entity.PlaceholderTemplate;
import com.eluon.CampaignApi.responseEntity.CampaignAdsResponse;
import com.eluon.CampaignApi.util.MsisdnEncryption;
import com.google.common.hash.Hashing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

@Service
public class CampaignAdsService
{
    private static final Logger LOG = LoggerFactory.getLogger(CampaignAdsService.class);
    CampaignAdsResponse campaignAdsResponse;
    CampaignAdsRequest campaignAdsRequest;
    @Autowired
    PlaceholderTemplate placeholderTemplate;
    @Autowired
    PlaceholderTemplateDao placeholderTemplateDao;
    public String getCampaignAdsResponse(String encryptedMsisdn, CampaignAdsRequest campaignAdsRequest,String encryptedToken)
    {
        int height;
        int width;
        String url_asset=null;
        //decrypt msisdn<<<
        MsisdnEncryption me = new MsisdnEncryption(ConstantVar.secretKey);
        String msisdn = me.decrypt(encryptedMsisdn);
        System.out.println("param:" + encryptedMsisdn + " ,msisdn:" + msisdn);
        //>>>
        //encrypt token and compare it with requester token<<<
        String generatedTokenByService = Hashing.sha256()
                .hashString(ConstantVar.serviceCampaignAds, StandardCharsets.UTF_8)
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

        //GET param from body<<<<
        LOG.debug("body param type:" + campaignAdsRequest.getType());
        LOG.debug("body param Height:" + campaignAdsRequest.getHeight());
        LOG.debug("body param weight:" + campaignAdsRequest.getWeight());
        //>>>>>


        //set response==========================
        campaignAdsResponse = new CampaignAdsResponse();
        campaignAdsResponse.setResponse_code("0");
        campaignAdsResponse.setResponse_message("ok");

        //get placeholderTemplate from DB
        width = Integer.parseInt(campaignAdsRequest.getWeight());
        height = Integer.parseInt(campaignAdsRequest.getHeight());
        placeholderTemplate = placeholderTemplateDao.getPlaceholderTemplateByWidthHeight(width, height);
        System.out.println("placeholder width:" + width + "height:" + height + " ,asset:" + placeholderTemplate.getPath_html_asset());
        campaignAdsResponse.setData(new CampaignAdsData(placeholderTemplate.getPath_html_asset()));

        //go to url asset TODO: return html asset with response 302
        url_asset= placeholderTemplate.getPath_html_asset();

        return url_asset;
    }
}

package com.eluon.CampaignApi.service;

import com.eluon.CampaignApi.constant.ConstantVar;
import com.eluon.CampaignApi.dao.RssDao;
import com.eluon.CampaignApi.entity.*;
import com.eluon.CampaignApi.responseEntity.MsisdnInfoResponse;
import com.eluon.CampaignApi.util.MsisdnEncryption;
import com.eluon.CampaignApi.util.XmlReader;
import com.google.common.hash.Hashing;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.jar.Manifest;

@Service
public class MsisdnInfoService
{
    @Autowired
    RssDao rssDao;
	@Autowired
    SSPService sspService;

    XmlReader xmlReader;
    public MsisdnInfoResponse getMsisdnInfoResponse(String encryptedMsisdn, String encryptedToken) throws IOException, FeedException {
        List<ImageTitleUrlTrending> imageTitleUrlsTrending=new ArrayList<>();
        List<ImageTitleUrlInteresting> imageTitleUrlsInteresting=new ArrayList<>();

        //decrypt msisdn<<<
        MsisdnEncryption me=new MsisdnEncryption(ConstantVar.secretKey);
        String decryptedMsisdn= me.decrypt(encryptedMsisdn);
        System.out.println("encrypted-msisdn:"+encryptedMsisdn+ " ,decrypted-msisdn:"+decryptedMsisdn);
        //>>>

        //encrypt token to matching requester token<<<
        String generatedTokenByService = Hashing.sha256()
                .hashString(ConstantVar.serviceMsisdnInfoToken, StandardCharsets.UTF_8)
                .toString();
        System.out.println("requester-token:"+encryptedToken+ " ,generated-token:"+generatedTokenByService);
        //>>>

        MsisdnInfoResponse msisdnInfoResponse=new MsisdnInfoResponse();

        if (encryptedMsisdn != null && encryptedMsisdn != "") {

            if (encryptedToken != null && encryptedToken != "") {

                if (encryptedToken.equals(generatedTokenByService)) {

                    msisdnInfoResponse.setResponse_code("0");
                    msisdnInfoResponse.setResponse_message("Success");

        SSPInfoResult info =  sspService.getInfo("081295950001");
                    MsisdnBalanceQuota msisdnBalanceQuota = new MsisdnBalanceQuota();
		 if (info.result){
		            long balance = (Long) info.content.get("balance");
		            String quota = (String) info.content.get("packageQuotaUnit");
		            msisdnBalanceQuota.setBalance((int)balance);
		            msisdnBalanceQuota.setQuota(Integer.parseInt(quota));
		        } else {
		            msisdnBalanceQuota.setBalance(0);
		            msisdnBalanceQuota.setQuota(0);
		        }
                    msisdnBalanceQuota.setMsisdn(decryptedMsisdn);

                    /*
                    ImageTitleUrl imageTitleUrl1 = new ImageTitleUrl("http://image1.jpg", "title1", "http://url1.com");
                    ImageTitleUrl imageTitleUrl2 = new ImageTitleUrl("http://image2.jpg","title2","http://url2.com");
                    imageTitleUrlsTrending.add(imageTitleUrl1);
                    imageTitleUrls.add(imageTitleUrl2);
                    msisdnBalanceQuota.setRss_trending(imageTitleUrlsTrending);
                    msisdnBalanceQuota.setRss_interesting(imageTitleUrls);
                    */

                    /*
                    //RSS get old <<<<
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
                    //>>>RSS get old
                    */

                    /* msisdnInfoResponse.setData(msisdnBalanceQuota); */

                    //RSS get <<<<
                    List<Rss> rssListTrending= rssDao.getAllRssWithType("trending");
                    StringBuilder sbTrending;
                    xmlReader = new XmlReader();
                    for(Rss getRssTrending:rssListTrending)
                    {
                        String sourceUrlTrending = getRssTrending.getSource_url();
                        System.out.println(sourceUrlTrending);
                        SyndFeed rssResponseTrending = xmlReader.getSyndFeedForUrl(sourceUrlTrending);
                        // System.out.println(rssResponseTrending);
                        int limitDisplayTrending = getRssTrending.getLimit_display();
                        int nowcounterTrending = 0;
                        System.out.println("");
                        System.out.println("------------------------");
                        System.out.println("Trending");
                        System.out.println("------------------------");
                        for (SyndEntry entryTrending : (List<SyndEntry>) rssResponseTrending.getEntries()) {
                            SyndContent descriptionTrending = entryTrending.getDescription();
                            String imagefullTrending = descriptionTrending.getValue();
                            int startIdxTrending = imagefullTrending.indexOf("\"") + 1;
                            int lastIdxTrending = imagefullTrending.indexOf("\"", startIdxTrending);
                            String imageTrending = imagefullTrending.substring(startIdxTrending, lastIdxTrending);
                            String titleTrending = entryTrending.getTitle();
                            String urlTrending = entryTrending.getUri();
                            System.out.println(imageTrending);
                            System.out.println(titleTrending);
                            System.out.println(urlTrending);
                            System.out.println("------------------------");
                            if (nowcounterTrending < limitDisplayTrending) {
                                System.out.println("MASUKKAN => data ke : " + nowcounterTrending + ", dari limit : " + limitDisplayTrending);
                                System.out.println("------------------------");
                                ImageTitleUrlTrending imageTitleUrlTrending = new ImageTitleUrlTrending(imageTrending, titleTrending, urlTrending);
                                imageTitleUrlsTrending.add(imageTitleUrlTrending);
                                nowcounterTrending ++;
                            }
                            else {
                                System.out.println("SKIP => data ke : " + nowcounterTrending + ", dari limit: " + limitDisplayTrending);
                                System.out.println("------------------------");
                                nowcounterTrending ++;
                            }
                        }
                        msisdnBalanceQuota.setRss_trending(imageTitleUrlsTrending);
                    }
                    //>>>RSS get


                    //RSS get <<<<
                    List<Rss> rssListInteresting= rssDao.getAllRssWithType("interesting");
                    StringBuilder sbInteresting;
                    xmlReader = new XmlReader();
                    for(Rss getRssInteresting:rssListInteresting)
                    {
                        String sourceUrlInteresting = getRssInteresting.getSource_url();
                        System.out.println(sourceUrlInteresting);
                        SyndFeed rssResponseInteresting = xmlReader.getSyndFeedForUrl(sourceUrlInteresting);
                        // System.out.println(rssResponseInteresting);
                        int limitDisplayInteresting = getRssInteresting.getLimit_display();
                        int nowcounterInteresting = 0;
                        System.out.println("");
                        System.out.println("------------------------");
                        System.out.println("Interesting");
                        System.out.println("------------------------");
                        for (SyndEntry entryInteresting : (List<SyndEntry>) rssResponseInteresting.getEntries()) {
                            SyndContent descriptionInteresting = entryInteresting.getDescription();
                            String imagefullInteresting = descriptionInteresting.getValue();
                            int startIdxInteresting = imagefullInteresting.indexOf("\"") + 1;
                            int lastIdxInteresting = imagefullInteresting.indexOf("\"", startIdxInteresting);
                            String imageInteresting = imagefullInteresting.substring(startIdxInteresting, lastIdxInteresting);
                            String titleInteresting = entryInteresting.getTitle();
                            String urlInteresting = entryInteresting.getUri();
                            System.out.println(imageInteresting);
                            System.out.println(titleInteresting);
                            System.out.println(urlInteresting);
                            System.out.println("------------------------");
                            if (nowcounterInteresting < limitDisplayInteresting) {
                                System.out.println("MASUKKAN => data ke : " + nowcounterInteresting + ", dari limit : " + limitDisplayInteresting);
                                System.out.println("------------------------");
                                ImageTitleUrlInteresting imageTitleUrlInteresting = new ImageTitleUrlInteresting(imageInteresting, titleInteresting, urlInteresting);
                                imageTitleUrlsInteresting.add(imageTitleUrlInteresting);
                                nowcounterInteresting ++;
                            }
                            else {
                                System.out.println("SKIP => data ke : " + nowcounterInteresting + ", dari limit: " + limitDisplayInteresting);
                                System.out.println("------------------------");
                                nowcounterInteresting ++;
                            }
                        }
                        msisdnBalanceQuota.setRss_interesting(imageTitleUrlsInteresting);
                    }
                    //>>>RSS get

                    msisdnInfoResponse.setData(msisdnBalanceQuota);
                }
                else {
                    msisdnInfoResponse.setResponse_code("130");
                    msisdnInfoResponse.setResponse_message("Error : Header Token mismatch");
                }

            }
            else {
                msisdnInfoResponse.setResponse_code("121");
                msisdnInfoResponse.setResponse_message("Error : No TOKEN Header");
            }
        }
        else {
            msisdnInfoResponse.setResponse_code("120");
            msisdnInfoResponse.setResponse_message("Error : No MSISDN Header");
        }

        return msisdnInfoResponse;
    }

}

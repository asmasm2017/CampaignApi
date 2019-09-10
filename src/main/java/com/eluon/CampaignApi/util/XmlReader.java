package com.eluon.CampaignApi.util;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import org.xml.sax.InputSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

public class XmlReader {

    SyndFeed feed = null;
    InputStream is = null;

    public SyndFeed getSyndFeedForUrl(String url) throws MalformedURLException, IOException, IllegalArgumentException, FeedException {
        try {

            URLConnection openConnection = new URL(url).openConnection();
            is = new URL(url).openConnection().getInputStream();
            if("gzip".equals(openConnection.getContentEncoding())){
                is = new GZIPInputStream(is);
            }
            InputSource source = new InputSource(is);
            SyndFeedInput input = new SyndFeedInput();
            feed = input.build(source);

        } catch (Exception e){
            System.out.println(e);
            //LOG.error("Exception occured when building the feed object out of the url", e);
        } finally {
            if( is != null)	is.close();
        }

        return feed;
    }


    public SyndFeed getSyndFeedFromLocalFile(String filePath)
            throws MalformedURLException, IOException,
            IllegalArgumentException, FeedException {

        SyndFeed feed = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
            InputSource source = new InputSource(fis);
            SyndFeedInput input = new SyndFeedInput();
            feed = input.build(source);
        } finally {
            fis.close();
        }

        return feed;
    }

}

package com.eluon.CampaignApi.entity;

import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class MsisdnBalanceQuota
{
    String msisdn;
    int balance;
    int quota;
    int point;
    String expired_quota;
    List<ImageTitleUrlTrending> rss_trending;
    List<ImageTitleUrlInteresting> rss_interesting;

    public MsisdnBalanceQuota()
    { }
    public MsisdnBalanceQuota(String msisdn, int balance, int point, String expired_quota, int quota, List<ImageTitleUrlTrending> rss_trending, List<ImageTitleUrlInteresting> rss_interesting)
    {
        this.msisdn = msisdn;
        this.balance = balance;
        this.quota = quota;
        this.point = point;
        this.expired_quota = expired_quota;
        this.rss_trending = rss_trending;
        this.rss_interesting = rss_interesting;
    }

    public String getMsisdn()
    {
        return msisdn;
    }

    public void setMsisdn(String msisdn)
    {
        this.msisdn = msisdn;
    }

    public int getBalance()
    {
        return balance;
    }

    public void setBalance(int balance)
    {
        this.balance = balance;
    }

    public int getQuota()
    {
        return quota;
    }

    public void setQuota(int quota)
    {
        this.quota = quota;
    }

    public int getPoint()
    {
        return point;
    }

    public void setPoint(int point)
    {
        this.point = point;
    }

    public String getExpired_quota() {
        return expired_quota;
    }

    public void setExpired_quota(String expired_quota) {
        this.expired_quota = expired_quota;
    }

    public List<ImageTitleUrlTrending> getRss_trending()
    {
        return rss_trending;
    }

    public void setRss_trending(List<ImageTitleUrlTrending> rss_trending)
    {
        this.rss_trending = rss_trending;
    }

    public List<ImageTitleUrlInteresting> getRss_interesting()
    {
        return rss_interesting;
    }

    public void setRss_interesting(List<ImageTitleUrlInteresting> rss_interesting)
    {
        this.rss_interesting = rss_interesting;
    }
}

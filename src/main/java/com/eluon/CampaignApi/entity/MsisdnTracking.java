package com.eluon.CampaignApi.entity;

import org.springframework.stereotype.Repository;

@Repository
public class MsisdnTracking
{
    String msisdn;
    String calling_date;
    String calling_time;
    int calling_type;
    String data;
    public MsisdnTracking()
    {

    }
    public String getMsisdn()
    {
        return msisdn;
    }

    public void setMsisdn(String msisdn)
    {
        this.msisdn = msisdn;
    }

    public String getCalling_date()
    {
        return calling_date;
    }

    public void setCalling_date(String calling_date)
    {
        this.calling_date = calling_date;
    }

    public String getCalling_time()
    {
        return calling_time;
    }

    public void setCalling_time(String calling_time)
    {
        this.calling_time = calling_time;
    }

    public int getCalling_type()
    {
        return calling_type;
    }

    public void setCalling_type(int calling_type)
    {
        this.calling_type = calling_type;
    }

    public String getData()
    {
        return data;
    }

    public void setData(String data)
    {
        this.data = data;
    }
}

package com.eluon.CampaignApi.entity;

import org.springframework.stereotype.Repository;


@Repository
public class MsisdnInfo
{
    String msisdn;
    int point;
    int days_month;
    String created_date;
    String updated_date;

    public MsisdnInfo()
    {
    }

    public MsisdnInfo(String msisdn, int point, int days_month, String created_date, String updated_date) {
        this.msisdn = msisdn;
        this.point = point;
        this.days_month = days_month;
        this.created_date = created_date;
        this.updated_date = updated_date;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getDays_month() {
        return days_month;
    }

    public void setDays_month(int days_month) {
        this.days_month = days_month;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(String updated_date) {
        this.updated_date = updated_date;
    }
}

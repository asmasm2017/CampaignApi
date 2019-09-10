package com.eluon.CampaignApi.entity;

import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public class Rss
{
   String source_url;
   int limit_display;
   char status;
   Timestamp created_date;
   Timestamp updated_date;
   String type;

    public Rss()
    {
    }

    public Rss(String source_url, int limit_display, char status, Timestamp created_date, Timestamp updated_date, String type)
    {
        this.source_url = source_url;
        this.limit_display = limit_display;
        this.status = status;
        this.created_date = created_date;
        this.updated_date = updated_date;
        this.type = type;
    }

    public String getSource_url()
    {
        return source_url;
    }

    public void setSource_url(String source_url)
    {
        this.source_url = source_url;
    }

    public int getLimit_display()
    {
        return limit_display;
    }

    public void setLimit_display(int limit_display)
    {
        this.limit_display = limit_display;
    }

    public char getStatus()
    {
        return status;
    }

    public void setStatus(char status)
    {
        this.status = status;
    }

    public Timestamp getCreated_date()
    {
        return created_date;
    }

    public void setCreated_date(Timestamp created_date)
    {
        this.created_date = created_date;
    }

    public Timestamp getUpdated_date()
    {
        return updated_date;
    }

    public void setUpdated_date(Timestamp updated_date)
    {
        this.updated_date = updated_date;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}

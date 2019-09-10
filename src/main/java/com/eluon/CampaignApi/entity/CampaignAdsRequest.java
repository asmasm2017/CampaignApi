package com.eluon.CampaignApi.entity;

import org.springframework.stereotype.Repository;

@Repository
public class CampaignAdsRequest
{
    String type;
    String height;
    String weight;

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getHeight()
    {
        return height;
    }

    public void setHeight(String height)
    {
        this.height = height;
    }

    public String getWeight()
    {
        return weight;
    }

    public void setWeight(String weight)
    {
        this.weight = weight;
    }
}

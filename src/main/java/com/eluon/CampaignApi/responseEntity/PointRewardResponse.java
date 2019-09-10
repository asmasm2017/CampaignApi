package com.eluon.CampaignApi.responseEntity;

import org.springframework.stereotype.Repository;


public class PointRewardResponse
{
    String point;
    String reward;
    String valid;

    public PointRewardResponse(String point, String reward, String valid)
    {
        this.point = point;
        this.reward = reward;
        this.valid = valid;
    }

    public String getPoint()
    {
        return point;
    }

    public void setPoint(String point)
    {
        this.point = point;
    }

    public String getReward()
    {
        return reward;
    }

    public void setReward(String reward)
    {
        this.reward = reward;
    }

    public String getValid()
    {
        return valid;
    }

    public void setValid(String valid)
    {
        this.valid = valid;
    }
}

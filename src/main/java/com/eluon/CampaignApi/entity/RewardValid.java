package com.eluon.CampaignApi.entity;

public class RewardValid
{
    String reward;
    String valid;

    public RewardValid(String reward, String valid)
    {
        this.reward = reward;
        this.valid = valid;
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

package com.eluon.CampaignApi.entity;

public class QuotaInfo
{
    String rewards;
    String valid;
    int redeem_parameter;
    int redeem_points;
    char status;
    String created_date;
    String updated_date;

    public String getRewards()
    {
        return rewards;
    }

    public void setRewards(String rewards)
    {
        this.rewards = rewards;
    }

    public String getValid()
    {
        return valid;
    }

    public void setValid(String valid)
    {
        this.valid = valid;
    }

    public int getRedeem_parameter()
    {
        return redeem_parameter;
    }

    public void setRedeem_parameter(int redeem_parameter)
    {
        this.redeem_parameter = redeem_parameter;
    }

    public int getRedeem_points()
    {
        return redeem_points;
    }

    public void setRedeem_points(int redeem_points)
    {
        this.redeem_points = redeem_points;
    }

    public char getStatus()
    {
        return status;
    }

    public void setStatus(char status)
    {
        this.status = status;
    }

    public String getCreated_date()
    {
        return created_date;
    }

    public void setCreated_date(String created_date)
    {
        this.created_date = created_date;
    }

    public String getUpdated_date()
    {
        return updated_date;
    }

    public void setUpdated_date(String updated_date)
    {
        this.updated_date = updated_date;
    }
}

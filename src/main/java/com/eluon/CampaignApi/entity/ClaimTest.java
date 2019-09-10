package com.eluon.CampaignApi.entity;

import com.eluon.CampaignApi.responseEntity.BaseResponse;

public class ClaimTest extends BaseResponse
{
    RewardValid data;

    public ClaimTest()
    {

    }

    public ClaimTest(String response_code, String response_message, RewardValid data)
    {
        super(response_code, response_message);
        this.data = data;
    }

    public ClaimTest(RewardValid data)
    {
        this.data = data;
    }

    public RewardValid getData()
    {
        return data;
    }

    public void setData(RewardValid data)
    {
        this.data = data;
    }
}

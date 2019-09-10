package com.eluon.CampaignApi.responseEntity;

import com.eluon.CampaignApi.entity.RewardValid;
import com.eluon.CampaignApi.responseEntity.BaseResponse;

public class ClaimResponse extends BaseResponse
{
    RewardValid data;

    public ClaimResponse()
    {

    }

    public ClaimResponse(String response_code, String response_message, RewardValid data)
    {
        super(response_code, response_message);
        this.data = data;
    }

    public ClaimResponse(RewardValid data)
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

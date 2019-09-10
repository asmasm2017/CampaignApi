package com.eluon.CampaignApi.responseEntity;

import com.eluon.CampaignApi.entity.CampaignAdsData;

public class CampaignAdsResponse extends BaseResponse
{

    CampaignAdsData data;

    public CampaignAdsResponse()
    {

    }
    public CampaignAdsResponse(String response_code, String response_message, CampaignAdsData data)
    {
        super(response_code, response_message);
        this.data = data;
    }

    public CampaignAdsResponse(CampaignAdsData data)
    {
        this.data = data;
    }

    public CampaignAdsData getData()
    {
        return data;
    }

    public void setData(CampaignAdsData data)
    {
        this.data = data;
    }
}

package com.eluon.CampaignApi.entity;

import com.eluon.CampaignApi.responseEntity.BaseResponse;

public class DailyCheckinTest extends BaseResponse
{
    DailyCheckinData data;

    public DailyCheckinTest(String response_code, String response_message, DailyCheckinData data)
    {
        super(response_code, response_message);
        this.data = data;
    }

    public DailyCheckinTest(DailyCheckinData data)
    {
        this.data = data;
    }

    public DailyCheckinData getData()
    {
        return data;
    }

    public void setData(DailyCheckinData data)
    {
        this.data = data;
    }
}

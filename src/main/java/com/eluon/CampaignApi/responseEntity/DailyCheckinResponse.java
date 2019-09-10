package com.eluon.CampaignApi.responseEntity;

import com.eluon.CampaignApi.entity.DailyCheckinData;
import org.springframework.stereotype.Repository;

@Repository
public class DailyCheckinResponse extends BaseResponse
{
    DailyCheckinData data;

    public DailyCheckinResponse()
    {}
    public DailyCheckinResponse(String response_code, String response_message, DailyCheckinData data)
    {
        super(response_code, response_message);
        this.data = data;
    }

    public DailyCheckinResponse(DailyCheckinData data)
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

package com.eluon.CampaignApi.responseEntity;

import com.eluon.CampaignApi.entity.MsisdnBalanceQuota;

public class MsisdnInfoResponse extends BaseResponse
{
    MsisdnBalanceQuota data=new MsisdnBalanceQuota();

    public MsisdnInfoResponse()
    {

    }
    public MsisdnInfoResponse(String response_code, String response_message, MsisdnBalanceQuota data)
    {
        super(response_code, response_message);
        this.data = data;
    }

    public MsisdnInfoResponse(MsisdnBalanceQuota data)
    {
        this.data = data;
    }

    public MsisdnBalanceQuota getData()
    {
        return data;
    }

    public void setData(MsisdnBalanceQuota data)
    {
        this.data = data;
    }
}

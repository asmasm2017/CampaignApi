package com.eluon.CampaignApi.entity;

import com.eluon.CampaignApi.responseEntity.BaseResponse;

public class MsisdnInfoTest extends BaseResponse
{
    MsisdnBalanceQuota msisdnBalanceQuota;

    public MsisdnInfoTest(String response_code, String response_message, MsisdnBalanceQuota msisdnBalanceQuota)
    {
        super(response_code, response_message);
        this.msisdnBalanceQuota = msisdnBalanceQuota;
    }

    public MsisdnInfoTest(MsisdnBalanceQuota msisdnBalanceQuota)
    {
        this.msisdnBalanceQuota = msisdnBalanceQuota;
    }

    public MsisdnBalanceQuota getMsisdnBalanceQuota()
    {
        return msisdnBalanceQuota;
    }

    public void setMsisdnBalanceQuota(MsisdnBalanceQuota msisdnBalanceQuota)
    {
        this.msisdnBalanceQuota = msisdnBalanceQuota;
    }
}

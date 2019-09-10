package com.eluon.CampaignApi.responseEntity;

public class BaseResponse
{
    String response_code = "0";
    String response_message = "OK";

    public String getResponse_code()
    {
        return response_code;
    }

    public void setResponse_code(String response_code)
    {
        this.response_code = response_code;
    }

    public String getResponse_message()
    {
        return response_message;
    }

    public void setResponse_message(String response_message)
    {
        this.response_message = response_message;
    }

    public BaseResponse(String response_code, String response_message)
    {
        this.response_code = response_code;
        this.response_message = response_message;
    }

    public BaseResponse()
    {
    }
}

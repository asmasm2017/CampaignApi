package com.eluon.CampaignApi.service;

import com.eluon.CampaignApi.dao.MsisdnTrackingDao;
import com.eluon.CampaignApi.entity.MsisdnTracking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MsisdnTrackingService
{
    @Autowired
    MsisdnTrackingDao msisdnTrackingDao;
    public List<MsisdnTracking> getAllMsisdnTracking()
    {
        return msisdnTrackingDao.getAllMsisdnTracking();
    }
}

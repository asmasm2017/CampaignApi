package com.eluon.CampaignApi.dao;

import com.eluon.CampaignApi.entity.MsisdnTracking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MsisdnTrackingDao
{
    @Autowired
    JdbcTemplate jdbcTemplate;

    MsisdnTracking msisdnTracking;
    public List<MsisdnTracking> getAllMsisdnTracking()
    {
        List<MsisdnTracking> msisdnTrackingList=new ArrayList<MsisdnTracking>();
        String sql = "select * from msisdn_tracking ";
        RowMapper<MsisdnTracking> rowMapper = new BeanPropertyRowMapper<MsisdnTracking>(MsisdnTracking.class);
        msisdnTrackingList=jdbcTemplate.query(
                sql,
                rowMapper);
        return msisdnTrackingList;

    }

    public void deleteMsisdnTrackingByMsisdn()
    {

    }
}

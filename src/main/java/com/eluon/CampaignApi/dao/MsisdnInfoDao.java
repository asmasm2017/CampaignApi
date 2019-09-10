package com.eluon.CampaignApi.dao;

import com.eluon.CampaignApi.entity.MsisdnInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MsisdnInfoDao
{
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    MsisdnInfo msisdnInfo;

    public List<MsisdnInfo> getMsisdnInfoByMsisdn(String inputmsisdn)
    {
        List<MsisdnInfo> msisdnInfoList=new ArrayList<>();
        String sql="select * from msisdn_info where msisdn= "+inputmsisdn;
        RowMapper<MsisdnInfo> rowMapper = new BeanPropertyRowMapper<MsisdnInfo>(MsisdnInfo.class);
        msisdnInfoList=jdbcTemplate.query(
                sql,
                rowMapper
        );
        return msisdnInfoList;

    }
}

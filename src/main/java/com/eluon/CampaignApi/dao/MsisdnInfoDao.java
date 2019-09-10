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
    MsisdnInfo msisdnInfo;

    public List<MsisdnInfo> getMsisdnInfoAll()
    {
        List<MsisdnInfo> msisdnInfoList=new ArrayList<>();
        String sql="select * from msisdn_info ";
        RowMapper<MsisdnInfo> rowMapper = new BeanPropertyRowMapper<MsisdnInfo>(MsisdnInfo.class);
        msisdnInfoList=jdbcTemplate.query(
                sql,
                rowMapper
        );
        return msisdnInfoList;

    }
    public MsisdnInfo getMsisdnInfoByMsisdn(String msisdn)
    {
        MsisdnInfo msisdnInfo=new MsisdnInfo();
        System.out.println("query msisdn_info where msisdn: "+msisdn);
        String sql="select * from msisdn_info where msisdn = ? limit 1";
        RowMapper<MsisdnInfo> rowMapper =  new BeanPropertyRowMapper<MsisdnInfo>(MsisdnInfo.class);
        msisdnInfo=jdbcTemplate.queryForObject(
                sql,new Object[]{msisdn},
                rowMapper
        );
        return msisdnInfo;
    }
    public void updateMsisdnInfo(int point, String msisdn)
    {
        String sql = "update msisdn_info set point =? where msisdn=?  ";
        jdbcTemplate.update(sql,point,msisdn);

    }
}

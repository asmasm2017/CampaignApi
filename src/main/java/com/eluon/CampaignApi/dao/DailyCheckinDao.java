package com.eluon.CampaignApi.dao;

import com.eluon.CampaignApi.entity.MsisdnCheckin;
import com.eluon.CampaignApi.entity.Rss;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DailyCheckinDao
{
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    MsisdnCheckin msisdnCheckin;
    public MsisdnCheckin getCheckInByMsisdn(String msisdn)
    {
        String sql="select * from msisdn_checkin where msisdn = ? limit 1";
        RowMapper<MsisdnCheckin> rowMapper = new BeanPropertyRowMapper<MsisdnCheckin>(MsisdnCheckin.class);
        msisdnCheckin=jdbcTemplate.queryForObject(
                sql,new Object[]{msisdn},
                rowMapper
        );

        return msisdnCheckin;
    }
    public void updateCheckIn(String msisdn,char flag, String day)
    {

        String dayOfMonth="day_"+day;
        String sql = "update msisdn_checkin set "+dayOfMonth+"=? where msisdn=?  ";

        String YES="Y";
        jdbcTemplate.update(sql,YES,msisdn);
        System.out.println("update msisdn_checkin set "+dayOfMonth+" Y where msisdn = "+msisdn);

    }
}

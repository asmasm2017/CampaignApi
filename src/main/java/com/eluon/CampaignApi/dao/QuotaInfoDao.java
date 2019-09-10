package com.eluon.CampaignApi.dao;

import com.eluon.CampaignApi.entity.QuotaInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

//reward	varchar(100)
//        valid	varchar(50)
//        redeem_parameter	int
//        redeem_points	int
//        status	char(1)
//        created_date	date
//        updated_date	date
@Repository
public class QuotaInfoDao
{
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    QuotaInfo quotaInfo;
    public List<QuotaInfo> getQuotaInfoAll()
    {
        List<QuotaInfo> quotaInfoList= new ArrayList<>();
        String sql="select * from quota_info ";
        RowMapper<QuotaInfo> rowMapper = new BeanPropertyRowMapper<QuotaInfo>(QuotaInfo.class);
        quotaInfoList=jdbcTemplate.query(
                sql,
                rowMapper
        );
        return quotaInfoList;
    }

}

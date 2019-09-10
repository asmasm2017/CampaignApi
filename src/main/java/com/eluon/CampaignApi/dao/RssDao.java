package com.eluon.CampaignApi.dao;

import com.eluon.CampaignApi.entity.MsisdnTracking;
import com.eluon.CampaignApi.entity.Rss;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RssDao
{
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    Rss rss;

    public List<Rss> getAllRss()
    {
        List<Rss> rssList=new ArrayList<>();
        String sql="select * from rss";
        RowMapper<Rss> rowMapper = new BeanPropertyRowMapper<Rss>(Rss.class);
        rssList=jdbcTemplate.query(
                sql,
                rowMapper
        );
        return rssList;

    }
    public List<Rss> getAllRssWithStatus(char status)
    {
        List<Rss> rssList=new ArrayList<>();
        String sql="select * from rss where status= "+status;
        RowMapper<Rss> rowMapper = new BeanPropertyRowMapper<Rss>(Rss.class);
        rssList=jdbcTemplate.query(
                sql,
                rowMapper
        );
        return rssList;

    }
    public List<Rss> getAllRssWithType(String type)
    {
        List<Rss> rssList=new ArrayList<>();
        String sql="select * from rss where type = ? ";
        RowMapper<Rss> rowMapper = new BeanPropertyRowMapper<Rss>(Rss.class);
        rssList=jdbcTemplate.query(
                sql,new Object[]{"interesting"},
                rowMapper
        );
        return rssList;

    }

}

package com.eluon.CampaignApi.dao;

import com.eluon.CampaignApi.entity.PlaceholderTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PlaceholderTemplateDao
{
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    PlaceholderTemplate placeholderTemplate;
    public List<PlaceholderTemplate> getAllPlaceholderTemplate()
    {
        List<PlaceholderTemplate> placeholderTemplateList=new ArrayList<>();
        String sql="select * from placeholder_template";
        RowMapper<PlaceholderTemplate> rowMapper= new BeanPropertyRowMapper<PlaceholderTemplate>(PlaceholderTemplate.class);
        placeholderTemplateList=jdbcTemplate.query(
                sql,
                rowMapper
         );

        return placeholderTemplateList;
    }
    public PlaceholderTemplate getPlaceholderTemplateByWidthHeight(int width, int height)
    {
        String sql="select * from placeholder_template where width =? and height=? limit 1";
        RowMapper<PlaceholderTemplate> rowMapper =  new BeanPropertyRowMapper<PlaceholderTemplate>(PlaceholderTemplate.class);
        placeholderTemplate=jdbcTemplate.queryForObject(
                sql,new Object[]{width,height},
                rowMapper
        );
        return placeholderTemplate;
    }
}

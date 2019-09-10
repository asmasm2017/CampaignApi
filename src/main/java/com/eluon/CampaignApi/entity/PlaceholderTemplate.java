package com.eluon.CampaignApi.entity;

import org.springframework.stereotype.Repository;

@Repository
public class PlaceholderTemplate
{
    int widht;
    int height;
    String path_html_template;
    String path_html_asset;

    public PlaceholderTemplate()
    {
    }

    public PlaceholderTemplate(int widht, int height, String path_html_template, String path_html_asset)
    {
        this.widht = widht;
        this.height = height;
        this.path_html_template = path_html_template;
        this.path_html_asset = path_html_asset;
    }

    public int getWidht()
    {
        return widht;
    }

    public void setWidht(int widht)
    {
        this.widht = widht;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public String getPath_html_template()
    {
        return path_html_template;
    }

    public void setPath_html_template(String path_html_template)
    {
        this.path_html_template = path_html_template;
    }

    public String getPath_html_asset()
    {
        return path_html_asset;
    }

    public void setPath_html_asset(String path_html_asset)
    {
        this.path_html_asset = path_html_asset;
    }
}

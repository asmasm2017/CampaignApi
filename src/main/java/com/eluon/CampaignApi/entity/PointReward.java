package com.eluon.CampaignApi.entity;

import org.springframework.stereotype.Repository;


public class PointReward
{
    String reward;
    String valid;
    int redeem_parameter;
    int redeem_points;
    String status;
    String created_date;
    String updated_date;


    public PointReward(String reward, String valid, int redeem_parameter, int redeem_points, String status, String created_date, String updated_date) {
        this.reward = reward;
        this.valid = valid;
        this.redeem_parameter = redeem_parameter;
        this.redeem_points = redeem_points;
        this.status = status;
        this.created_date = created_date;
        this.updated_date = updated_date;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public int getRedeem_parameter() {
        return redeem_parameter;
    }

    public void setRedeem_parameter(int redeem_parameter) {
        this.redeem_parameter = redeem_parameter;
    }

    public int getRedeem_points() {
        return redeem_points;
    }

    public void setRedeem_points(int redeem_points) {
        this.redeem_points = redeem_points;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String craeted_date) {
        this.created_date = craeted_date;
    }

    public String getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(String updated_date) {
        this.updated_date = updated_date;
    }
}

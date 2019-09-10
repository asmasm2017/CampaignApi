package com.eluon.CampaignApi.entity;

import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class DailyCheckinData
{
    boolean already_checkin_today;
    boolean can_claim_reward;
    List<PointReward> pointRewardList;
    int remaining_point;
    int number_of_days;
    DaysData days_data;

    public DailyCheckinData()
    {
    }

    public DailyCheckinData(boolean already_checkin_today, boolean can_claim_reward, List<PointReward> pointRewardList, int remaining_point, int number_of_days, DaysData days_data)
    {
        this.already_checkin_today = already_checkin_today;
        this.can_claim_reward = can_claim_reward;
        this.pointRewardList = pointRewardList;
        this.remaining_point = remaining_point;
        this.number_of_days = number_of_days;
        this.days_data = days_data;
    }

    public boolean isAlready_checkin_today()
    {
        return already_checkin_today;
    }

    public void setAlready_checkin_today(boolean already_checkin_today)
    {
        this.already_checkin_today = already_checkin_today;
    }

    public boolean isCan_claim_reward()
    {
        return can_claim_reward;
    }

    public void setCan_claim_reward(boolean can_claim_reward)
    {
        this.can_claim_reward = can_claim_reward;
    }

    public List<PointReward> getPointRewardList()
    {
        return pointRewardList;
    }

    public void setPointRewardList(List<PointReward> pointRewardList)
    {
        this.pointRewardList = pointRewardList;
    }

    public int getRemaining_point()
    {
        return remaining_point;
    }

    public void setRemaining_point(int remaining_point)
    {
        this.remaining_point = remaining_point;
    }

    public int getNumber_of_days()
    {
        return number_of_days;
    }

    public void setNumber_of_days(int number_of_days)
    {
        this.number_of_days = number_of_days;
    }

    public DaysData getDays_data()
    {
        return days_data;
    }

    public void setDays_data(DaysData days_data)
    {
        this.days_data = days_data;
    }
}

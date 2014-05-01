package com.tapadoo.ull.ull2014.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rory on 28/04/14.
 */
public class ScheduleDay {
    @SerializedName("Name")
    public String name;
    @SerializedName("Schedule")
    public List<ScheduleItem> schedule;
}

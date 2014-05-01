package com.tapadoo.ull.ull2014.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rory on 28/04/14.
 */
public class ScheduleItem {

@SerializedName("Title")
    public String title;

    @SerializedName("StartTime")
    public Float startTime;

    @SerializedName("EndTime")
    public Float endTime;
}

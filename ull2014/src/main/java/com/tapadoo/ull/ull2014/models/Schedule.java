package com.tapadoo.ull.ull2014.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Schedule {
    @SerializedName("Days")
    public List<ScheduleDay> days;
}
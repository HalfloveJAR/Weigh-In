package me.kobeplane.data;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

public class DailyCalorieLogData {

    @DatabaseField(canBeNull = false, generatedId = true)
    private int dayId;


    @DatabaseField(canBeNull = false)
    private int totalCalories;

    @DatabaseField(canBeNull = false)
    private Date logDate;

    public DailyCalorieLogData() {}

    public int getDayId() {
        return dayId;
    }

    public int getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(int calories) {
        this.totalCalories = calories;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date date) {
        this.logDate = date;
    }

}
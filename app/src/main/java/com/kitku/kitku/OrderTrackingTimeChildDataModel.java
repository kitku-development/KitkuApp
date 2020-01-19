package com.kitku.kitku;

public class OrderTrackingTimeChildDataModel {

    private String timeChildDate;
    private String timeChildTime;

    public OrderTrackingTimeChildDataModel(String timeChildDate, String timeChildTime) {
        this.timeChildDate = timeChildDate;
        this.timeChildTime = timeChildTime;
    }

    public String getTimeChildDate() {
        return timeChildDate;
    }

    public String getTimeChildTime() {
        return timeChildTime;
    }
}

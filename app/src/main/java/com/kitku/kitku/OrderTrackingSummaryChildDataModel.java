package com.kitku.kitku;

public class OrderTrackingSummaryChildDataModel {

    private String summaryChildName;
    private String summaryChildAmount;
    private String summaryChildItemPrice;
    private String summaryChildDiscountPrice;
    private String summaryChildDeliveryPrice;
    private String summaryChildUniqueCode;
    private String summaryChildTotalPrice;

    public OrderTrackingSummaryChildDataModel(String summaryChildName, String summaryChildAmount, String summaryChildItemPrice,
                                              String summaryChildDiscountPrice, String summaryChildDeliveryPrice,
                                              String summaryChildUniqueCode, String summaryChildTotalPrice) {
        this.summaryChildName = summaryChildName;
        this.summaryChildAmount = summaryChildAmount;
        this.summaryChildItemPrice = summaryChildItemPrice;
        this.summaryChildDiscountPrice = summaryChildDiscountPrice;
        this.summaryChildDeliveryPrice = summaryChildDeliveryPrice;
        this.summaryChildUniqueCode = summaryChildUniqueCode;
        this.summaryChildTotalPrice = summaryChildTotalPrice;
    }

    public String getSummaryChildName() {
        return summaryChildName;
    }

    public String getSummaryChildAmount() {
        return summaryChildAmount;
    }

    public String getSummaryChildItemPrice() {
        return summaryChildItemPrice;
    }

    public String getSummaryChildDiscountPrice() {
        return summaryChildDiscountPrice;
    }

    public String getSummaryChildDeliveryPrice() {
        return summaryChildDeliveryPrice;
    }

    public String getSummaryChildUniqueCode() {
        return summaryChildUniqueCode;
    }

    public String getSummaryChildTotalPrice() {
        return summaryChildTotalPrice;
    }
}

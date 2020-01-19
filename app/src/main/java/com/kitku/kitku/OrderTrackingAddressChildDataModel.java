package com.kitku.kitku;

public class OrderTrackingAddressChildDataModel {

    private String addressChildName;
    private String addressChildPhone;
    private String addressChildAddressHouse;
    private String addressChildAddressCity;
    private String addressChildAddressProvince;

    public OrderTrackingAddressChildDataModel(String addressChildName, String addressChildPhone, String addressChildAddressHouse,
                                              String addressChildAddressCity, String addressChildAddressProvince) {
        this.addressChildName = addressChildName;
        this.addressChildPhone = addressChildPhone;
        this.addressChildAddressHouse = addressChildAddressHouse;
        this.addressChildAddressCity = addressChildAddressCity;
        this.addressChildAddressProvince = addressChildAddressProvince;
    }

    public String getAddressChildName() {
        return addressChildName;
    }

    public String getAddressChildPhone() {
        return addressChildPhone;
    }

    public String getAddressChildAddressHouse() {
        return addressChildAddressHouse;
    }

    public String getAddressChildAddressCity() {
        return addressChildAddressCity;
    }

    public String getAddressChildAddressProvince() {
        return addressChildAddressProvince;
    }
}

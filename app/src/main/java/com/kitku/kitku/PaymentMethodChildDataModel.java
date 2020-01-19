package com.kitku.kitku;

public class PaymentMethodChildDataModel {

    private Integer methodChildImage;
    private String methodChildCode;
    private String methodChildAccount;
    private String methodChildPrice;

    public PaymentMethodChildDataModel(Integer methodChildImage, String methodChildCode,
                                       String methodChildAccount, String methodChildPrice) {
        this.methodChildImage = methodChildImage;
        this.methodChildCode = methodChildCode;
        this.methodChildAccount = methodChildAccount;
        this.methodChildPrice = methodChildPrice;
    }

    public Integer getMethodChildImage() {
        return methodChildImage;
    }

    public String getMethodChildCode() {
        return methodChildCode;
    }

    public String getMethodChildAccount() {
        return methodChildAccount;
    }

    public String getMethodChildPrice() {
        return methodChildPrice;
    }
}

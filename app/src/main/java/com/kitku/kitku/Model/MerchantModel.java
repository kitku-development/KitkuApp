package com.kitku.kitku.Model;

import android.content.Context;
import android.content.SharedPreferences;

public class MerchantModel implements BaseModel{

    private String id;
    private String password;
    private String name;
    private String address;
    private String contact;
    private String email;
    private String registeredDate;

    private SharedPreferences merchantData;
    private SharedPreferences.Editor merchantDataEdit;

    public MerchantModel(Context context) {
    }


    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getContact() {
        return contact;
    }

    @Override
    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getRegisteredDate() {
        return registeredDate;
    }

    @Override
    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }

    @Override
    public String loginJson() throws Exception {
        return null;
    }

    @Override
    public void logout() {
        merchantDataEdit.clear().apply();
    }
}

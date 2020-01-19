package com.kitku.kitku.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.IntDef;
import androidx.preference.PreferenceManager;

import org.json.JSONObject;

public class CustomerModel implements BaseModel {

    private String id;
    private String password;
    private String name;
    private String address;
    private String contact;
    private String email;
    private String registeredDate;

    private SharedPreferences customerData;
    private SharedPreferences.Editor customerDataEdit;

    public CustomerModel(Context context, String password, String email) {
        customerData = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        customerDataEdit = customerData.edit();
        this.password = password;
        this.email = email;
        customerDataEdit.putString("customerEmail", email);
        customerDataEdit.apply();
    }

    public CustomerModel(Context context) {
        customerData = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        id = customerData.getString("customerId", "");
        name = customerData.getString("customerName", "");
        address = customerData.getString("customerAddress", "");
        contact = customerData.getString("customerContact", "");
        registeredDate = customerData.getString("registered", "");
        customerDataEdit = customerData.edit();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
        customerDataEdit.putString("customerId", id);
        customerDataEdit.apply();
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
        customerDataEdit.putString("customerName", name);
        customerDataEdit.apply();
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
        customerDataEdit.putString("customerAddress", address);
        customerDataEdit.apply();
    }

    @Override
    public String getContact() {
        return contact;
    }

    @Override
    public void setContact(String contact) {
        this.contact = contact;
        customerDataEdit.putString("customerContact", contact);
        customerDataEdit.apply();
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
        customerDataEdit.putString("registered", registeredDate);
        customerDataEdit.apply();
    }

    public String loginJson() throws Exception{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", email);
        jsonObject.put("password", password);
        return jsonObject.toString();
    }

    public void logout() {
        customerDataEdit.clear().apply();
    }
}

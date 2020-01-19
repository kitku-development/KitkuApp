package com.kitku.kitku.Model;

public interface BaseModel {

    String getId();

    void setId(String id);

    String getPassword();

    void setPassword(String password);

    String getName();

    void setName(String name);

    String getAddress();

    void setAddress(String address);

    String getContact();

    void setContact(String contact);

    String getEmail();

    void setEmail(String email);

    String getRegisteredDate();

    void setRegisteredDate(String registeredDate);

    String loginJson() throws Exception;

    void logout();
}

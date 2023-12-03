package com.faizanahmed.janabhazir;

import org.json.JSONObject;

public class UserDataSingleton {
    private static UserDataSingleton instance;
    private JSONObject userData;

    private UserDataSingleton() {}

    public static synchronized UserDataSingleton getInstance() {
        if (instance == null) {
            instance = new UserDataSingleton();
        }
        return instance;
    }

    public void setUserData(JSONObject data) {
        this.userData = data;
    }

    public JSONObject getUserData() {
        return this.userData;
    }
}

package com.burkinabe.application;

import android.app.Application;

import io.realm.Realm;

public class MoneyManagementApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}

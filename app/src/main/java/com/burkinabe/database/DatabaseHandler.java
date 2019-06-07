/*
 * Copyright (C) 2016 Glucosio Foundation
 *
 * This file is part of Glucosio.
 *
 * Glucosio is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * Glucosio is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Glucosio.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */

package com.burkinabe.database;

import android.content.Context;

import com.burkinabe.database.entities.Depense;
import com.burkinabe.database.entities.Depot;
import com.burkinabe.database.entities.User;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DatabaseHandler {

    private static RealmConfiguration mRealmConfig;
    private Context mContext;
    private Realm realm;

    public DatabaseHandler(Context context) {
        this.mContext = context;
        Realm.init(context);
        this.realm = getNewRealmInstance();
    }

    public Realm getNewRealmInstance() {
        if (mRealmConfig == null) {
            mRealmConfig = new RealmConfiguration.Builder()
                    .schemaVersion(0)
                    .migration(new Migration())
                    .build();
        }
        return Realm.getInstance(mRealmConfig); // Automatically run migration if needed
    }

    public Realm getRealmInstance() {
        return realm;
    }

    public void addUser(User user) {
        realm.beginTransaction();
        realm.copyToRealm(user);
        realm.commitTransaction();
    }

    public User getUser(long id) {
        return realm.where(User.class)
                .equalTo("id", id)
                .findFirst();
    }

    public User getUser(Realm realm, long id) {
        return realm.where(User.class)
                .equalTo("id", id)
                .findFirst();
    }

    public void updateUser(User user) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();
    }


    public void addDepot(Depot depot) {
        realm.beginTransaction();
        realm.copyToRealm(depot);
        realm.commitTransaction();
    }

    public Depot getDepot(long id) {
        return realm.where(Depot.class)
                .equalTo("id", id)
                .findFirst();
    }

    public Depot getDepot(Realm realm, long id) {
        return realm.where(Depot.class)
                .equalTo("id", id)
                .findFirst();
    }

    public void updateDepot(Depot depot) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(depot);
        realm.commitTransaction();
    }


    public void addDepense(Depense depense) {
        int size = getAllDepenses().size();
        int nextId;
        if( size == 0) {
            nextId = 1;
        } else {
            nextId = size + 1;
        }
        depense.setId(nextId);
        realm.beginTransaction();
        realm.copyToRealm(depense);
        realm.commitTransaction();
    }

    public Depense getDepense(long id) {
        return realm.where(Depense.class)
                .equalTo("id", id)
                .findFirst();
    }

    public Depense getDepense(Realm realm, long id) {
        return realm.where(Depense.class)
                .equalTo("id", id)
                .findFirst();
    }

    public void updateDepense(Depense depense) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(depense);
        realm.commitTransaction();
    }

    public List<Depense> getAllDepenses() {
        return realm.where(Depense.class)
                .findAll();
    }


    private String generateIdFromDate(Date created, long readingId) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(created);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        return "" + year + month + day + hours + minutes + readingId;
    }

}

package com.burkinabe.database;

/**
 * I can not leave it as Object and I can not name it as RealmObject
 */
public enum RObject {
    DEPOT("Depot"),
    DEPENSE("Depense");

    private String key;

    RObject(String key) {
        this.key = key;
    }

    String key() {
        return key;
    }
}

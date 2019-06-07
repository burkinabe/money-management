package com.burkinabe.database;

public enum RealmField {
    ID("id"),
    DATEDEPOT("dateDepot"),
    MONTANTINITIAL("montantInitial"),
    MONTANTRESTANT("montantRestant"),
    DATEDEPENSE("dateDepense"),
    MONTANTDEPENSE("montantDepense"),
    MOTIF("motif");
    private String key;

    RealmField(String key) {
        this.key = key;
    }

    String key() {
        return key;
    }
}

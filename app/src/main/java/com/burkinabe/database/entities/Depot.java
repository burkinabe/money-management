package com.burkinabe.database.entities;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Depot extends RealmObject {

    @PrimaryKey
    private long id;

    private Date dateDepot;

    private double montantInitial;

    private double montantRestant;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDateDepot() {
        return dateDepot;
    }

    public void setDateDepot(Date dateDepot) {
        this.dateDepot = dateDepot;
    }

    public double getMontantInitial() {
        return montantInitial;
    }

    public void setMontantInitial(double montantInitial) {
        this.montantInitial = montantInitial;
    }

    public void setMontantRestant(double montantRestant) {
        this.montantRestant = montantRestant;
    }

    public double getMontantRestant() {
        return montantRestant;
    }
}

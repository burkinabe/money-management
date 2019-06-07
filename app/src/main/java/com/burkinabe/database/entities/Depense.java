package com.burkinabe.database.entities;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Depense extends RealmObject {

    @PrimaryKey
    private long id;

    private String motif;

    private Date dateDepense;

    private double montantDepense;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDateDepense() {
        return dateDepense;
    }

    public void setDateDepense(Date dateDepense) {
        this.dateDepense = dateDepense;
    }

    public double getMontantDepense() {
        return montantDepense;
    }

    public void setMontantDepense(double montantDepense) {
        this.montantDepense = montantDepense;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }
}

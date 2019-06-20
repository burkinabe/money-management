package com.burkinabe.database.entities;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Depense extends RealmObject {

    @PrimaryKey
    private long id;

    private String motif;

    private Date dateDepense;

    private long yearValue;

    private long monthValue;

    private long dayValue;

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

    public long getDayValue() {
        return dayValue;
    }

    public void setDayValue(long dayValue) {
        this.dayValue = dayValue;
    }

    public long getMonthValue() {
        return monthValue;
    }

    public void setMonthValue(long monthValue) {
        this.monthValue = monthValue;
    }

    public long getYearValue() {
        return yearValue;
    }

    public void setYearValue(long yearValue) {
        this.yearValue = yearValue;
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

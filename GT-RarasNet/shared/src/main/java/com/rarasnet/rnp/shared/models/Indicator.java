package com.rarasnet.rnp.shared.models;

/**
 * Created by lucas on 29/08/16.
 */
public class Indicator {
    private String id;
    private String year;
    private String amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getNameIndicatorType() {
        return nameIndicatorType;
    }

    public void setNameIndicatorType(String nameIndicatorType) {
        this.nameIndicatorType = nameIndicatorType;
    }

    public String getGetNameIndicatorSource() {
        return nameIndicatorSource;
    }

    public void setGetNameIndicatorSource(String getNameIndicatorSource) {
        this.nameIndicatorSource = getNameIndicatorSource;
    }

    private String reference;
    private String nameIndicatorType;
    private String nameIndicatorSource;

}

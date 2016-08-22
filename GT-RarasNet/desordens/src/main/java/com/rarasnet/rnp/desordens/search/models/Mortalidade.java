package com.rarasnet.rnp.desordens.search.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ronnyery Barbosa on 14/08/2015.
 */
public class Mortalidade implements Serializable {

    private String id;
    @SerializedName("2002")  private String ano2002;
    @SerializedName("2003")  private String ano2003;
    @SerializedName("2004")  private String ano2004;
    @SerializedName("2005")  private String ano2005;
    @SerializedName("2006")  private String ano2006;
    @SerializedName("2007")  private String ano2007;
    @SerializedName("2008")  private String ano2008;
    @SerializedName("2009")  private String ano2009;
    @SerializedName("2010")  private String ano2010;
    @SerializedName("2011")  private String ano2011;
    @SerializedName("2012")  private String ano2012;

    public Mortalidade(String id, String ano2002, String ano2003, String ano2004,
                       String ano2005, String ano2006, String ano2007, String ano2008,
                       String ano2009, String ano2010, String ano2011, String ano2012) {
        this.id = id;
        this.ano2002 = ano2002;
        this.ano2003 = ano2003;
        this.ano2004 = ano2004;
        this.ano2005 = ano2005;
        this.ano2006 = ano2006;
        this.ano2007 = ano2007;
        this.ano2008 = ano2008;
        this.ano2009 = ano2009;
        this.ano2010 = ano2010;
        this.ano2011 = ano2011;
        this.ano2012 = ano2012;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAno2002() {
        return ano2002;
    }

    public void setAno2002(String ano2002) {
        this.ano2002 = ano2002;
    }

    public String getAno2003() {
        return ano2003;
    }

    public void setAno2003(String ano2003) {
        this.ano2003 = ano2003;
    }

    public String getAno2004() {
        return ano2004;
    }

    public void setAno2004(String ano2004) {
        this.ano2004 = ano2004;
    }

    public String getAno2005() {
        return ano2005;
    }

    public void setAno2005(String ano2005) {
        this.ano2005 = ano2005;
    }

    public String getAno2006() {
        return ano2006;
    }

    public void setAno2006(String ano2006) {
        this.ano2006 = ano2006;
    }

    public String getAno2007() {
        return ano2007;
    }

    public void setAno2007(String ano2007) {
        this.ano2007 = ano2007;
    }

    public String getAno2008() {
        return ano2008;
    }

    public void setAno2008(String ano2008) {
        this.ano2008 = ano2008;
    }

    public String getAno2009() {
        return ano2009;
    }

    public void setAno2009(String ano2009) {
        this.ano2009 = ano2009;
    }

    public String getAno2010() {
        return ano2010;
    }

    public void setAno2010(String ano2010) {
        this.ano2010 = ano2010;
    }

    public String getAno2011() {
        return ano2011;
    }

    public void setAno2011(String ano2011) {
        this.ano2011 = ano2011;
    }

    public String getAno2012() {
        return ano2012;
    }

    public void setAno2012(String ano2012) {
        this.ano2012 = ano2012;
    }
}




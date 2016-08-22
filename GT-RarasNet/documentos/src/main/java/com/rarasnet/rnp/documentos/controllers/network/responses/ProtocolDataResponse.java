package com.rarasnet.rnp.documentos.controllers.network.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Farina on 18/7/2015.
 */
public class ProtocolDataResponse {

    @SerializedName("id") private String id;
    @SerializedName("dn") private String disorderName;
    @SerializedName("pn") private String protocolName;
    @SerializedName("di") private String disorderId;

    public ProtocolDataResponse(String id, String disorderName, String protocolName, String protocolDir, String disorderId) {
        this.id = id;
        this.disorderName = disorderName;
        this.protocolName = protocolName;
        this.disorderId = disorderId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisorderName() {
        return disorderName;
    }

    public void setDisorderName(String disorderName) {
        this.disorderName = disorderName;
    }

    public String getProtocolName() {
        return protocolName;
    }

    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }


    public String getDisorderId() {
        return disorderId;
    }

    public void setDisorderId(String disorderId) {
        this.disorderId = disorderId;
    }

}

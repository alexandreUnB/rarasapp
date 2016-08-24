package com.rarasnet.rnp.shared.protocol;

import java.util.List;

/**
 * Created by lucas on 22/08/16.
 */
public class ProtocolProfile {
    public List<ProtocolModel> getProtocols() {
        return protocols;
    }

    public void setProtocols(List<ProtocolModel> protocols) {
        this.protocols = protocols;
    }

    private List<ProtocolModel> protocols;

    public ProtocolProfile(List<ProtocolModel> protocols) {
        this.protocols = protocols;
    }

}

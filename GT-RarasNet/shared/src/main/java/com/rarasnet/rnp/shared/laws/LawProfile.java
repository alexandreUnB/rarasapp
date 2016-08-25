package com.rarasnet.rnp.shared.laws;

import java.util.List;

/**
 * Created by lucas on 25/08/16.
 */
public class LawProfile {
    public List<LawModel> getProtocols() {
        return laws;
    }

    public void setProtocols(List<LawModel> laws) {
        this.laws = laws;
    }

    private List<LawModel> laws;

    public LawProfile(List<LawModel> laws) {
        this.laws = laws;
    }

}

package com.rarasnet.rnp.shared.util.managers;

import android.content.Context;

/**
 * Created by Farina on 18/7/2015.
 */
public class ProtocolsManager extends PDFManager {

    private static final String folderLocation = "dados_nacionai/protocolo/";

    public ProtocolsManager(Context context) {
        super(context, folderLocation);
    }

}

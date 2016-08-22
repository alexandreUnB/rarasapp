package com.rarasnet.rnp.shared.common;

import android.support.annotation.IntDef;

/**
 * Created by Farina on 21/8/2015.
 *
 * IntDef (Similar a um enum) para o status de um campo de dados na aplicação
 */
public class FieldStatus {

    private int status;

    public static final int OK = 0;
    public static final int EMPTY = 1;
    public static final int INCORRECT_FORMAT = 2;
    public static final int PASSWORD_NOT_MATCHES = 3;

    @IntDef({OK, EMPTY, INCORRECT_FORMAT, PASSWORD_NOT_MATCHES})
    public @interface Status {
    }

    @Status
    public int getStatus() {
        return status;
    }

    public void setStatus(@Status int status) {
        this.status = status;
    }
}

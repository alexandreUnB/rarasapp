package com.rarasnet.rnp.shared.usuario.common;

import android.support.annotation.IntDef;

/**
 * Created by Farina on 21/8/2015.
 */
public class FieldType {

    private int type;

    public static final int NOME = 0;
    public static final int SOBRENOME = 1;
    public static final int LOGIN = 2;
    public static final int SENHA = 3;
    public static final int SENHA_CONF = 4;
    public static final int EMAIL = 5;

    @IntDef({NOME, SOBRENOME, LOGIN, SENHA, SENHA_CONF, EMAIL})
    public @interface Type {
    }

    @Type
    public int getType() {
        return type;
    }

    public void setType(@Type int type) {
        this.type = type;
    }

}

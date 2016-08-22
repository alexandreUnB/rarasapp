package com.rarasnet.rnp.autenticacao.controllers.network.requests.exceptions;

import java.util.Hashtable;

import com.rarasnet.rnp.autenticacao.common.FieldType;
import com.rarasnet.rnp.shared.common.FieldStatus;

/**
 * Created by Farina on 21/8/2015.
 */
public class InvalidRegisterFieldsException extends Exception {

    private Hashtable<FieldType, FieldStatus> validationResult
            = new Hashtable<FieldType, FieldStatus>();

    public InvalidRegisterFieldsException() {

    }

    public FieldStatus getStatus(FieldType fieldType) {
        return validationResult.get(fieldType);
    }

    public void addStatus(FieldType fieldType, FieldStatus fieldStatus) {

        if(validationResult.contains(fieldType))
            validationResult.remove(fieldType);

        validationResult.put(fieldType, fieldStatus);

    }

    public boolean hasValidationResult() {
        return validationResult.isEmpty();
    }
}

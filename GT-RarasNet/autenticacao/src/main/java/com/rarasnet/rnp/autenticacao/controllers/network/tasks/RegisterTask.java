package com.rarasnet.rnp.autenticacao.controllers.network.tasks;

import android.os.AsyncTask;

import com.rarasnet.rnp.autenticacao.controllers.activities.RegisterActivity;
import com.rarasnet.rnp.autenticacao.controllers.network.requests.RegisterRequest;
import com.rarasnet.rnp.shared.network.connections.RarasConnection;
import com.rarasnet.rnp.shared.network.responses.GenericResponse;


/**
 * Created by Farina on 12/8/2015.
 */
public class RegisterTask extends AsyncTask<RegisterRequest, Void, GenericResponse> {

    private static final String TAG = RegisterTask.class.getSimpleName();
    private RegisterActivity.RegisterUserCallback registerUserCallback = null;

    public RegisterTask(RegisterActivity.RegisterUserCallback registerUserCallback) {
        this.registerUserCallback = registerUserCallback;
    }

    protected GenericResponse doInBackground(RegisterRequest... params) {
        RarasConnection rarasConnection = new RarasConnection(RarasConnection.ACTION_REGISTER);
        rarasConnection.sendRequest(params[0], RegisterRequest.class);
        GenericResponse response = rarasConnection.getResponse(GenericResponse.class);
        return response;
    }

    protected void onPostExecute(GenericResponse response) {
        registerUserCallback.onRegisterTaskCallback(response);
    }
}

package com.rarasnet.rnp.autenticacao.controllers.network.tasks;


import android.os.AsyncTask;

import com.rarasnet.rnp.autenticacao.controllers.activities.LoginActivity;
import com.rarasnet.rnp.autenticacao.controllers.network.requests.LoginRequest;
import com.rarasnet.rnp.shared.network.connections.RarasConnection;
import com.rarasnet.rnp.shared.network.responses.GenericResponse;


public class LoginTask extends AsyncTask<String, String, GenericResponse> {

    private LoginActivity.LoginUserCallback loginUserCallback;

    public LoginTask(LoginActivity.LoginUserCallback loginUserCallback) {
        this.loginUserCallback = loginUserCallback;
    }

    @Override
    protected GenericResponse doInBackground(String... params){
        GenericResponse response;
        String login = params[0];
        String password = params[1];
        LoginRequest loginReq = new LoginRequest(login, password);

        RarasConnection requester = new RarasConnection(RarasConnection.ACTION_LOGIN);
        requester.sendRequest(loginReq, LoginRequest.class);
        response = requester.getResponse(GenericResponse.class);

        return response;
    }

    protected void onPostExecute(GenericResponse result){
        loginUserCallback.onLoginTaskCallback(result);
    }
}
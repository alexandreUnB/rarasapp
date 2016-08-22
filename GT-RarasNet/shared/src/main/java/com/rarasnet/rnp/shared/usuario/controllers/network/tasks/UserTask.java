package com.rarasnet.rnp.shared.usuario.controllers.network.tasks;


import android.os.AsyncTask;
import android.util.Log;

import com.rarasnet.rnp.shared.network.connections.RarasConnection;
import com.rarasnet.rnp.shared.network.responses.GenericResponse;
import com.rarasnet.rnp.shared.usuario.controllers.activities.UserActivity;
import com.rarasnet.rnp.shared.usuario.controllers.network.requests.UserRequest;


public class UserTask extends AsyncTask<String, String, GenericResponse> {

    private UserActivity.LoginUserCallback loginUserCallback;

    public UserTask(UserActivity.LoginUserCallback loginUserCallback) {
        this.loginUserCallback = loginUserCallback;
    }

    @Override
    protected GenericResponse doInBackground(String... params){
        GenericResponse response;
        String login = params[0];
        String password = params[1];
        UserRequest loginReq = new UserRequest(login, password);

        RarasConnection requester = new RarasConnection(RarasConnection.ACTION_USER);
        requester.sendRequest(loginReq, UserRequest.class);
        response = requester.getResponse(GenericResponse.class);
        Log.d("aqui response",response.toString());

        return response;
    }

    protected void onPostExecute(GenericResponse result){
        loginUserCallback.onLoginTaskCallback(result);
    }
}
package com.example.utn.investigacion;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by christian on 11/2/2016.
 */
public class AuthenticatorService extends Service {
    private AccountAuthenticator mAuthenticator;
    @Override
    public void onCreate() {
// Create a new authenticator object
        mAuthenticator = new AccountAuthenticator(this);
    }
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
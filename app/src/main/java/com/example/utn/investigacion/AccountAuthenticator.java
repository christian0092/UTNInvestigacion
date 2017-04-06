/*package com.example.utn.investigacion;
import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import static android.app.Activity.RESULT_OK;

//import com.example.andres.myapplication.Activities.AuthenticatorActivity;
/*
Clase que maneja la autenticacion y realiza la gran mayoria de las operaciones importantes de
una cuenta.
*/
/*public class AccountAuthenticator extends AbstractAccountAuthenticator {
    private Context mContext;
    public AccountAuthenticator(Context context) {
        super(context);
        mContext = context;
    }
    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        return null;
    }
    /*
    Llamado cuando el usuario quiere loguearse y anadir un nuevo usuario.
    @return bundle con intent para iniciar AuthenticatorActivity.
    */
/*    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String
            authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
        ...
    }
    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account,
                                     Bundle options) throws NetworkErrorException {
        return null;
    }
    /*
    Obtiene el token de una cuenta. Si falla, se avisa que se debe llamar a
    AuthenticatorActivity.
    @return Si resulta, bundle con informacion de cuenta y token.
    Si falla, bundle con informacion de cuenta y activity.
    19
    */
/*   @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String
            authTokenType, Bundle options) throws NetworkErrorException {
        ...
    }
    @Override
    public String getAuthTokenLabel(String authTokenType) {
        return null;
    }
    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String
            authTokenType, Bundle options) throws NetworkErrorException {
        return null;
    }
    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[]
            features) throws NetworkErrorException {
        return null;
    }

    Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String
            authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
        final Intent intent = new Intent(mContext, AuthenticatorActivity.class);
        intent.putExtra(AuthenticatorActivity.ARG_ACCOUNT_TYPE, accountType);
        intent.putExtra(AuthenticatorActivity.ARG_AUTH_TYPE, authTokenType);
        intent.putExtra(AuthenticatorActivity.ARG_IS_ADDING_NEW_ACCOUNT, true);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }
    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String
            authTokenType, Bundle options) throws NetworkErrorException {
// Extrae username y pass del account manager
        final AccountManager am = AccountManager.get(mContext);
// Pide el authtoken
        String authToken = am.peekAuthToken(account, authTokenType);
// Si authToken esta vacio (no hay token guardado), se intenta autenticar en servidor
        if (TextUtils.isEmpty(authToken)){
            final String password = am.getPassword(account);
            if (password != null) {
// Se autentica en el servidor
                authToken = authenticateInServer(account);
            }
        }
// Si obtenemos un authToken, lo retornamos
        if (!TextUtils.isEmpty(authToken)) {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            21
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
            return result;
        }
// Si llegamos aca aun no podemos obtener el token.
// Necesitamos pedirle de nuevo que se loguee.
        final Intent intent = new Intent(mContext, AuthenticatorActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        intent.putExtra(AuthenticatorActivity.ARG_ACCOUNT_TYPE, account.type);
        intent.putExtra(AuthenticatorActivity.ARG_AUTH_TYPE, authTokenType);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }
    public void submit() {
// Se obtiene el usuario y contrasena ingresados
        final String userName = ((TextView) findViewById(R.id.account_name)).getText().toString();
        final String userPass = ((TextView) findViewById(R.id.account_password)).getText().toString();
// Se loguea de forma asincronica para no entorpecer el UI thread
        new AsyncTask<Void, Void, Intent>() {
            @Override
            protected Intent doInBackground(Void... params) {
// Se loguea en el servidor y retorna token
                String authtoken = logIn(userName, userPass);
// Informacion necesaria para enviar al authenticator
                final Intent res = new Intent();
                res.putExtra(AccountManager.KEY_ACCOUNT_NAME, userName);
                res.putExtra(AccountManager.KEY_ACCOUNT_TYPE, "com.example.andres.myapplication");
                res.putExtra(AccountManager.KEY_AUTHTOKEN, authtoken);
                22
                res.putExtra(PARAM_USER_PASS, userPass);
                return res;
            }
            @Override
            protected void onPostExecute(Intent intent) {
                finishLogin(intent);
            }
        }.execute();
    }
    private void finishLogin(Intent intent) {
        String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String accountPassword = intent.getStringExtra(PARAM_USER_PASS);
        final Account account = new Account(accountName,
                intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));
// Si es que se esta anadiendo una nueva cuenta
        if (getIntent().getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false)) {
            String authtoken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
// Pueden haber muchos tipos de cuenta. En este caso solo tenemos una que llame 'normal'
            String authtokenType = "normal";
// Creando cuenta en el dispositivo y seteando el token que obtuvimos.
            mAccountManager.addAccountExplicitly(account, accountPassword, null);
// Ojo: hay que setear el token explicitamente si la cuenta no existe, no basta con
            mandarlo al authenticator
            mAccountManager.setAuthToken(account, authtokenType, authtoken);
        }
// Si no se esta anadiendo cuenta, el token antiguo estaba invalidado.
// Seteamos contrasena nueva por si la cambio.
        else {
// Solo seteamos contrasena
// Aca no es necesario setear el token explicitamente, basta con enviarlo al Authenticator
            mAccountManager.setPassword(account, accountPassword);
        }
// Setea el resultado para que lo reciba el Authenticator
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
// Cerramos la actividad
        finish();
    }
}

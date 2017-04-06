package com.example.utn.investigacion;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

import java.io.IOException;
import java.util.ArrayList;

public class SyncAdapter extends AbstractThreadedSyncAdapter {
    private ContentResolver mContentResolver;
    private String students;
    private String token;
    private AccountManager mAccountManager;

    public SyncAdapter (Context context, boolean autoInitialize){
        super(context, autoInitialize);
        this.mContentResolver = context.getContentResolver();
        mAccountManager = AccountManager.get(context);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient
            provider, SyncResult syncResult) {
        try {
// No importa que el thread se bloquee ya que es asincronico.
            token = mAccountManager.blockingGetAuthToken(account, "normal" , true);
// Obtiene datos del servidor
            ArrayList<String> results = performRequest(url, "GET");
// Sincroniza los datos
            updateData(results, "GET");
// Manejo de errores
        } catch (IOException e) {
            e.printStackTrace();
        } catch catch (AuthenticatorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OperationCanceledException e) {
            e.printStackTrace();
        }...
        ...
    }


}
package com.example.utn.investigacion;

import android.Manifest;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.plus.Account;
import com.google.android.maps.MapActivity;

import java.io.IOException;

import static android.provider.ContactsContract.Directory.ACCOUNT_TYPE;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int REQUEST_CODE;
    private Cursor cursor; //cursor para las marcas

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //BdManager manager =BdManager.getinstance(this);//crea la bd
        //  manager.insertar("hola","hola"); //inserta datos en la fila
        //cursor=manager.cargarmarcas();//carga las amrcas+
        Toast toast = new Toast(this);
        //cursor.moveToFirst();
        //toast.makeText(this,cursor.getString(2),toast.LENGTH_LONG).show();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;        // Add a marker in Sydney and move the camera


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
        }
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

            @Override
            public void onMapLongClick(LatLng point) {
                double lat = point.latitude;//obtengo latitud del objeto//dos variables float para la latitud y la longitud
                double lng = point.longitude;//obtengo longitud del objeto
                Intent cuestionario = new Intent(MapsActivity.this, FormActivity.class);//creo intent para cambiar de activity
                cuestionario.putExtra("latitud", lat);//pongo lat y lng para enviar como parametros al otro activity
                cuestionario.putExtra("longitud", lng);//
                startActivity(cuestionario);//inicio la activity
            }
        });
        final BdManager manager = new BdManager(this);
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {//activa el listener del infowindow
            @Override
            public void onInfoWindowClick(Marker marker) { //al hacer click en el infowindow
                int id = 0;

                Intent intent = new Intent(MapsActivity.this, DetalleMarcaActivity.class);
                id = manager.buscardidmarca(marker.getPosition().latitude, marker.getPosition().longitude);
                intent.putExtra("idmarca", id);
                startActivity(intent);


            }
        });

        Cursor cursordemarcas = manager.cargarmarcas();//obtengo el cursor con las marcas de la bd
        cursordemarcas.moveToFirst();


        while (!cursordemarcas.isAfterLast()) {
            Marker marca = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(cursordemarcas.getDouble(2), cursordemarcas.getDouble(3)))
                    .title(cursordemarcas.getString(1)));//agrega marca//LatLng sydney = new LatLng(-34, 151);//crea un objeto latlng a partir de las coordenadas

            cursordemarcas.moveToNext();
            marca.showInfoWindow();
        }


        cursordemarcas.close();


        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));//mueve camara
        Toast.makeText(this, "Hasta aca llegue",
                Toast.LENGTH_LONG).show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //do something
                } else {
                    finish();
                }
                break;
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
/*        accountManager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
        Account[] accounts = accountManager.getAccountsByType(ACCOUNT_TYPE);
        if (accounts.length == 0){
// Tambien se puede llamar a metodo accountManager.addAcount(...)
            Intent intent = new Intent(this, AuthenticatorActivity.class);
            intent.putExtra(AuthenticatorActivity.ARG_IS_ADDING_NEW_ACCOUNT, true);
            startActivity(intent);
        }
        else{
            mAccount = accounts[0];
            accountManager.getAuthToken(mAccount, "normal", null, this, mGetAuthTokenCallback, null);

        }
        AccountManagerCallback<Bundle> mGetAuthTokenCallback =
                new AccountManagerCallback<Bundle>() {
                    @Override
                    public void run(final AccountManagerFuture<Bundle> arg0) {
                        try {
                            token = (String) arg0.getResult().get(AccountManager.KEY_AUTHTOKEN);
                        } catch (Exception e) {
// handle error
                        }
                    }
                };*/
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.utn.investigacion/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.utn.investigacion/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


}


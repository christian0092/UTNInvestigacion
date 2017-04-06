package com.example.utn.investigacion;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.GpsStatus;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DetalleMarcaActivity extends AppCompatActivity {
    Bundle recuperador;
    TextView _id,nombre,longitud,latitud;
    Button eliminar,modificar;
    BdManager manager;

    Cursor cursordemarcas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detallesdemarca);
        recuperador=getIntent().getExtras();
        Toast.makeText(this, String.valueOf(recuperador.getInt("idmarca")),
                Toast.LENGTH_LONG).show();

        manager=new BdManager(this);

        _id=(TextView) findViewById(R.id.det_id);
        nombre=(TextView) findViewById(R.id.det_nombre);
        longitud=(TextView) findViewById(R.id.det_longitud);
        latitud=(TextView) findViewById(R.id.det_latitud);

        eliminar=(Button) findViewById(R.id.det_eliminar);
        modificar=(Button) findViewById(R.id.det_mod);
        eliminar.setOnClickListener(Listener);
        modificar.setOnClickListener(Listener);





        cursordemarcas=manager.recuperarmarca(recuperador.getInt("idmarca"));
        cursordemarcas.moveToFirst();
        while (!cursordemarcas.isAfterLast()) {

           _id.setText(String.valueOf(cursordemarcas.getInt(0)));
            nombre.setText(cursordemarcas.getString(1));
            latitud.setText(String.valueOf(cursordemarcas.getDouble(3)));
            longitud.setText(String.valueOf(cursordemarcas.getDouble(2)));

            cursordemarcas.moveToNext();


        }
        cursordemarcas.close();



    }
    private View.OnClickListener Listener = new View.OnClickListener() {
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.det_eliminar:
                { manager.eliminar(recuperador.getInt("idmarca"));
                    Intent perro = new Intent(DetalleMarcaActivity.this, MapsActivity.class);//se supone que tu aplicacion debe ser seria pibe
                    startActivity(perro);
                break;}
                case R.id.det_mod:
                {
                    Intent gato = new Intent(DetalleMarcaActivity.this,FormActivity.class);
                    cursordemarcas=manager.recuperarmarca(recuperador.getInt("idmarca"));
                    cursordemarcas.moveToFirst();
                    while (!cursordemarcas.isAfterLast()) {
                        gato.putExtra("id",cursordemarcas.getInt(0));
                        gato.putExtra("nombre",cursordemarcas.getString(1));
                        gato.putExtra("latitud", cursordemarcas.getDouble(3));
                        gato.putExtra("longitud", cursordemarcas.getDouble(2));
                        cursordemarcas.moveToNext();


                    }
                    cursordemarcas.close();



                    startActivity(gato);
                }


            }

        }
    };


}

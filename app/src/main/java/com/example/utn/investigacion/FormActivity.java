package com.example.utn.investigacion;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class FormActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Spinner spinner;
    EditText nombre,latitud,longitud;
    Button aceptar,cancelar;
    Bundle recuperador;
    BdManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        spinner=(Spinner) findViewById(R.id.FormSpin);
        ArrayAdapter adapter= ArrayAdapter.createFromResource(this, R.array.Type, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        nombre=(EditText) findViewById(R.id.nombre);
        latitud=(EditText) findViewById(R.id.Latitud);
        longitud=(EditText) findViewById(R.id.Longitud);
        aceptar=(Button) findViewById(R.id.FormAcept);
        aceptar.setOnClickListener(Listener);

        cancelar=(Button) findViewById(R.id.Form_canc);
        cancelar.setOnClickListener(Listener);
        recuperador=getIntent().getExtras();//obtengo los parametros del intent
        try{
        if(!recuperador.getString("nombre").equals(null)){
        nombre.setText(recuperador.getString("nombre"));}}
        catch (Exception e)
            {}
        latitud.setText(String.valueOf(recuperador.getDouble("latitud")));//seteo latitud y longituden el formulario
        longitud.setText(String.valueOf(recuperador.getDouble("longitud")));//
        manager =new BdManager(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView textView=(TextView) view;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle("Error al seleccionar item:")
                .setMessage("El Mensaje para el usuario")
                .setPositiveButton("Reintentar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });


        builder.create();
    }

    protected View.OnClickListener Listener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.FormAcept: {
                    int valor=-1;
                    valor=recuperador.getInt("id");
                    if(valor==0){
                    manager.insertar(nombre.getText().toString(), Double.parseDouble(latitud.getText().toString()), Double.parseDouble(longitud.getText().toString()));}
                    else{
                        manager.modificarmarcar(recuperador.getInt("id"),Double.parseDouble(latitud.getText().toString()), Double.parseDouble(longitud.getText().toString()),nombre.getText().toString());
                    }
                    Intent mapas = new Intent(FormActivity.this, MapsActivity.class);
                    startActivity(mapas);
                    break;
                }
                case R.id.Form_canc: {
                    Intent mapas = new Intent(FormActivity.this, MapsActivity.class);
                    startActivity(mapas);
                    break;
                }
            }


        }
    };
    }


package com.example.utn.investigacion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by christian on 3/5/2016.
 */
public class BdManager {
    private static BdManager _inst;
    private Bdhelper bdhelper;
    private SQLiteDatabase db;
    public static final String Table_Name = "Marca";
    public static final String Mk_id = "_id";
    public static final String Mk_Nombre = "Nombre";
    public static final String Mk_LaT = "latitud";
    public static final String Mk_Lng = "longitud";

    //crear tabla marca
    //_id int llave primaria autogenerada
    //Nombre text no nullo
    //Posicion int no nulo
    public static final String CREATE_TABLE = " create table " + Table_Name + " ("
            + Mk_id + " integer primary key autoincrement,"
            + Mk_Nombre + " text no null,"
            + Mk_Lng + " double no null,"
            + Mk_LaT + " double no null);";

    public static BdManager getinstance(Context context){
        if(_inst.equals(null)) {
            _inst=new BdManager(context);
        }
        return _inst;
    }

    public BdManager(Context context) {

        bdhelper = new Bdhelper(context);
        db = bdhelper.getWritableDatabase();

    }

    public ContentValues generarvalores(String nombre, Double latitud, Double longitud)

    { ContentValues valores=new ContentValues();
        valores.put(Mk_Nombre,nombre);
        valores.put(Mk_LaT, latitud);
        valores.put(Mk_Lng, longitud);
    return valores;}

    public void insertar(String nombre,Double latitud,Double longitud)
    {
        db.execSQL("INSERT INTO Marca (Nombre,latitud,longitud) VALUES ('"+nombre+"',"+latitud+","+longitud+")");
                //db.insert(Table_Name, Mk_Nombre, generarvalores(nombre, latitud, longitud));//devuelve -1 si hubo un error
    }
    public void eliminar(int id)
    {db.execSQL("DELETE FROM Marca WHERE _id LIKE " + id);
    }
    public void modificarmarcar(int id, Double latitud, Double longitud, String nombre){
        db.execSQL("UPDATE Marca SET Nombre='"+nombre+"', latitud="+latitud+",longitud="+longitud+" WHERE _id LIKE "+id);
    }
    public Cursor cargarmarcas()  //funcion que devuelve un cursor con las marcas
    {
        String[] columnas=new String[]{Mk_id,Mk_Nombre, Mk_LaT, Mk_Lng};//array de string necesario para el query
        return  db.query(Table_Name,columnas,null,null,null,null,null);//retorna el cursor

    }
    public int buscardidmarca(Double lat, Double lng){
        //String[] columnas=new String[]{Mk_id,Mk_Nombre, Mk_LaT, Mk_Lng};
        //return db.query(Table_Name,columnas,Mk_id + "=?",new Double[]{id},null,null,null);
        Cursor cursordemarcas=db.rawQuery("SELECT * FROM Marca WHERE longitud LIKE "+lng+" AND latitud LIKE "+lat,null);
        cursordemarcas.moveToFirst();
        int id=-1;
        while (!cursordemarcas.isAfterLast()) {
           id=cursordemarcas.getInt(0);
            cursordemarcas.moveToNext();


        }
        cursordemarcas.close();
        return id;

    }
    public Cursor recuperarmarca(int id){
        //String[] columnas=new String[]{Mk_id,Mk_Nombre, Mk_LaT, Mk_Lng};
        //return db.query(Table_Name,columnas,Mk_id + "=?",new Double[]{id},null,null,null);
        return db.rawQuery("SELECT * FROM Marca WHERE _id LIKE "+id,null);


    }
}

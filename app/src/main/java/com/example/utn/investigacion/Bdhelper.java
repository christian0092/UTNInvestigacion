package com.example.utn.investigacion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by christian on 3/5/2016.
 */
public class Bdhelper extends SQLiteOpenHelper {
    private static final String Bd_Name="marcas.sqlite";
    private static final int Bd_version=1;
    private static Bdhelper _inst;

    public static Bdhelper getinstance(MarcaProvider context){
        if(_inst.equals(null)) {
            _inst=new Bdhelper(context);
        }
        return _inst;
    }

    public Bdhelper(Context context)
    {
        super(context, Bd_Name, null, Bd_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BdManager.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

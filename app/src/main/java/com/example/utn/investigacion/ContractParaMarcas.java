/*package com.example.utn.investigacion;

/**
 * Created by christian on 10/21/2016.
 */
/*import android.content.ContentResolver;
import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;
public class ContractParaMarcas {
    public static final String AUTHORITY = "com.example.utn.investigacion.provider";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
    public static final Uri STUDENTS_URI = Uri.withAppendedPath(ContractParaMarcas.BASE_URI,
            "/Marcas");
    /*
    MIME Types
    Para listas se necesita 'vnd.android.cursor.dir/vnd.com.example.andres.provider.students
    Para items se necesita 'vnd.android.cursor.item/vnd.com.example.andres.provider.students'
    La primera parte viene esta definida en constantes de ContentResolver
    */
 /*   public static final String URI_TYPE_STUDENT_DIR = ContentResolver.CURSOR_DIR_BASE_TYPE +
            "/vnd.com.example.utn.investigacion.provider";
    public static final String URI_TYPE_STUDENT_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE +
            "/vnd.com.example.utn.investigacion.provider";
    /*
    Tabla definida en provider. Aca podria ser una distinta a la de la base de datos,
    pero consideramos la misma.
    */
/*    public static final class MarcaColumns implements BaseColumns{
        private MarcaColumns(){}
        public static final String Table_Name = "Marca";
        public static final String Mk_id = "_id";
        public static final String Mk_Nombre = "Nombre";
        public static final String Mk_LaT = "latitud";
        public static final String Mk_Lng = "longitud";
        public static final String DEFAULT_SORT_ORDER = Mk_Nombre + " ASC";
    }
}

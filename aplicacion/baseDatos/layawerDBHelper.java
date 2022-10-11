package com.example.aplicacion.baseDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class layawerDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION =  1;
    public static final String DATABASE_NAME = "db_usuarios";


    public layawerDBHelper(Context context,String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context,name, factory,version);
        //   Toast.makeText(context, "elescccccccccccccccccccccc", Toast.LENGTH_SHORT).show();
    }

    private static final String COLUMNA_ID ="id";
     private static final String TABLA_NOMBRES ="nombres" ;
    private static final String COLUMNA_NOMBRE="nombre" ;
   // final String SQL_CREAR1 ="CREATE TABLE usuarios(id TEXT, nombre TEXT, valor TEXT, cantidad TEXT)";
    final String SQL_CREAR ="CREATE TABLE productos(id TEXT, nombre TEXT, valor TEXT, cantidad TEXT)";
    //  "create table "
      //+ TABLA_NOMBRES + "(" + COLUMNA_ID
    //  + " integer primary key autoincrement, " + COLUMNA_NOMBRE
    // + " text not null);";
    @Override
    public void onCreate(SQLiteDatabase db) {


       // db.execSQL(SQL_CREAR);
        db.execSQL(SQL_CREAR);
    // mockData(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS  productos");
        onCreate(db);
    }







}

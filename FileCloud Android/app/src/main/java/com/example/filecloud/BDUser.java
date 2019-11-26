package com.example.filecloud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDUser extends SQLiteOpenHelper {

    BDUser(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String tabla = "CREATE TABLE Usuario (Nombre TEXT)";
        db.execSQL(tabla);
        String documento = "CREATE TABLE Documentos (Nombre TEXT)";
        db.execSQL(documento);
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){

    }
}

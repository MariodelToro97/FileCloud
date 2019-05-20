package com.example.filecloud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class BDUser extends SQLiteOpenHelper {

    String tabla = "CREATE TABLE Usuario (Nombre TEXT)";
    String documento = "CREATE TABLE Documentos (Nombre TEXT)";

    public BDUser(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(tabla);
        db.execSQL(documento);
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){

    }
}

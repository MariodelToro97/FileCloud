package com.example.filecloud;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Bundle;
import android.widget.Toast;

public class splashFileCloud extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_file_cloud);

        BDUser sql = new BDUser(this, "personasBD", null, 1);
        final SQLiteDatabase db = sql.getReadableDatabase();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Cursor c = db.rawQuery("SELECT * FROM Usuario", null);
                int cantidad = c.getCount();

                if (cantidad > 0){
                    if (c.moveToFirst()) {
                        Toast.makeText(getApplicationContext(), "Bienvenido de nuevo "+c.getString(0), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(splashFileCloud.this, documentosElegir.class);
                        intent.putExtra("USUARIO", c.getString(0));
                        startActivity(intent);
                        finish();
                    }

                } else {
                    Intent intent = new Intent(splashFileCloud.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },4000);
    }
}

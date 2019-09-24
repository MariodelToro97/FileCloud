package com.example.filecloud;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;


import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.UpdateFrom;

public class splashFileCloud extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_file_cloud);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null) {
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
        } else {
            // No hay conexión a Internet en este momento
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage(R.string.noInternet);
            alert.setTitle(R.string.titleNoInternte);
            alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });

            AlertDialog dialog = alert.create();
            dialog.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarApp();
    }

    private void actualizarApp () {
        AppUpdater appUpdater = new AppUpdater(this)
                .setDisplay(Display.DIALOG)
                .setCancelable(false)
                .setUpdateFrom(UpdateFrom.GITHUB)
                .setGitHubUserAndRepo("MariodelToro97", "FileCloud")
                .showEvery(1)
                .setTitleOnUpdateAvailable(R.string.actualizacion)
                .setTitleOnUpdateNotAvailable(R.string.actualizacionNo)
                .setButtonUpdate(R.string.actualizar)
                .setButtonUpdateClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(splashFileCloud.this, R.string.descarga, Toast.LENGTH_LONG).show();
                    }
                })
                .setButtonDismiss(R.string.actualizarDespues)
                .setButtonDismissClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(splashFileCloud.this, R.string.actualizarDesp, Toast.LENGTH_LONG).show();
                    }
                })
                .setButtonDoNotShowAgain(R.string.noInteresado)
                .setButtonDoNotShowAgainClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(splashFileCloud.this, R.string.cancelUpd, Toast.LENGTH_LONG).show();
                    }
                });
        //.setIcon(R.drawable.ic_system_update_black_24dp);
        appUpdater.start();
    }
}

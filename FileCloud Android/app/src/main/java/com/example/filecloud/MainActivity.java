package com.example.filecloud;

import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.UpdateFrom;

public class MainActivity extends AppCompatActivity {

    private Button btnRegistro;
    private Button btnInicioSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Cambia el color de la barra de navegación
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorAbajo));
        //Cambia el color de la barra de notificaciones
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorArriba));

        btnRegistro = findViewById(R.id.Registro);
        btnInicioSesion = findViewById(R.id.inicioSesion);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regitro = new Intent(MainActivity.this, Registro.class);
                startActivity(regitro);
            }
        });

        btnInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sesion = new Intent(MainActivity.this, inicioSesion.class);
                startActivity(sesion);
                finish();
            }
        });
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
                        Toast.makeText(MainActivity.this, R.string.descarga, Toast.LENGTH_LONG).show();

                        Documentos doc = new Documentos();

                        Uri uri = Uri.parse(doc.URL);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                })
                .setButtonDismiss(R.string.actualizarDespues)
                .setButtonDismissClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, R.string.actualizarDesp, Toast.LENGTH_LONG).show();
                    }
                })
                .setButtonDoNotShowAgain(R.string.noInteresado)
                .setButtonDoNotShowAgainClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, R.string.cancelUpd, Toast.LENGTH_LONG).show();
                    }
                });
        appUpdater.start();
    }
}

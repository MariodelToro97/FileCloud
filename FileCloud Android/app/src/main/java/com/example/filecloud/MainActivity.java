package com.example.filecloud;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
}

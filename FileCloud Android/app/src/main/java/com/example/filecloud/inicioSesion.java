package com.example.filecloud;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class inicioSesion extends AppCompatActivity {

    private Button btnCancelar;
    private  Button btnIniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        btnCancelar = findViewById(R.id.Cancelar);
        btnIniciarSesion = findViewById(R.id.iniciarSesionP);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sesion = new Intent(inicioSesion.this, MainActivity.class);
                startActivity(sesion);
                finish();
            }
        });

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iniciar = new Intent(inicioSesion.this, documentosElegir.class);
                startActivity(iniciar);
                finish();
            }
        });
    }
}

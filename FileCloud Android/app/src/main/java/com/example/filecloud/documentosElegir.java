package com.example.filecloud;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class documentosElegir extends AppCompatActivity {

    private Button btnCerrarSesion;
    private Button btnSolicitudes;
    private Button btnCargarDocumento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentos_elegir);

        btnCerrarSesion = findViewById(R.id.CerrarSesion);
        btnSolicitudes = findViewById(R.id.solicitudes);
        btnCargarDocumento = findViewById(R.id.cargarDocumentos);

        btnCargarDocumento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnSolicitudes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent solicitudes = new Intent(documentosElegir.this, solicitudes.class);
                startActivity(solicitudes);
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cerrarSesion = new Intent(documentosElegir.this, MainActivity.class);
                startActivity(cerrarSesion);
                finish();
            }
        });
    }
}

package com.example.filecloud;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class Registro extends AppCompatActivity {

    private Button btnCancelar;
    private Button btnRegistro;
    private RadioButton rbNuevoIngreso;
    private RadioButton rbAlumnoInscrito;
    private EditText ptNumeroMatricula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        btnCancelar = findViewById(R.id.cancelarRegistro);
        btnRegistro = findViewById(R.id.registrarse);
        rbAlumnoInscrito = findViewById(R.id.RB_alumnoInscrito);
        rbNuevoIngreso = findViewById(R.id.RB_nuevoIngreso);
        ptNumeroMatricula = findViewById(R.id.PT_numeroMatricula);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registro = new Intent(Registro.this, inicioSesion.class);
                startActivity(registro);
                finish();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cancelar = new Intent(Registro.this, MainActivity.class);
                startActivity(cancelar);
                finish();
            }
        });

        rbAlumnoInscrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}

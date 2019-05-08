package com.example.filecloud;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class solicitudes extends AppCompatActivity {

    private Button Regresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudes);

        Regresar = findViewById(R.id.Regresar);

        Regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regresar = new Intent(solicitudes.this, documentosElegir.class);
                startActivity(regresar);
                finish();
            }
        });
    }
}

package com.example.filecloud;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class inicioSesion extends AppCompatActivity {

    private Button btnCancelar;
    private  Button btnIniciarSesion;
    private EditText Usuario, Password;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        btnCancelar = findViewById(R.id.Cancelar);
        btnIniciarSesion = findViewById(R.id.iniciarSesionP);

        Usuario = findViewById(R.id.userSesion);
        Password = findViewById(R.id.passwordSesion);

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
                String user = Usuario.getText().toString();
                String contra = Password.getText().toString();

                if (user.isEmpty() || contra.isEmpty()){
                    Toast.makeText(getApplicationContext(), R.string.vacio, Toast.LENGTH_SHORT).show();
                } else {
                    obtenerUsuario(user);
                }
            }
        });
    }

    public void obtenerUsuario(String user){
        myRef = database.getReference("Users/" + user + "/Usuario");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                checarUsuario(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getApplicationContext(), R.string.errorBD, Toast.LENGTH_LONG).show();
            }
        });
    }

    boolean usuario = true;
    boolean contra = true;

    public void checarUsuario(String value){
        String user = Usuario.getText().toString();

        if (value != null){
            if (value.equals(user) && usuario) {

                myRef = database.getReference("Users/" + user + "/Password");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        obtenerContra(value);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Toast.makeText(getApplicationContext(), R.string.errorBD, Toast.LENGTH_LONG).show();
                    }
                });
            }
        } else if (usuario){
            Toast.makeText(getApplicationContext(), R.string.noExisteUsuario, Toast.LENGTH_SHORT).show();
        }
    }

    public void obtenerContra(String value) {
        String pass = Password.getText().toString();

        if (value.equals(pass) && contra){


            Toast.makeText(getApplicationContext(), R.string.InicioCorrecto, Toast.LENGTH_SHORT).show();

            Intent iniciar = new Intent(inicioSesion.this, documentosElegir.class);
            startActivity(iniciar);
            finish();

        } else if (contra){
            Toast.makeText(getApplicationContext(), R.string.contraIncorrecta, Toast.LENGTH_SHORT).show();
        }
    }
}

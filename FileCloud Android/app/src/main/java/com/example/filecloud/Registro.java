package com.example.filecloud;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Registro extends AppCompatActivity {

    private Button btnCancelar;
    private Button btnRegistro;
    private RadioButton rbNuevoIngreso;
    private RadioButton rbAlumnoInscrito;
    private EditText editNumeroMatricula;
    private EditText User;
    private EditText nombre, apellidoPaterno, apelllidoMaterno, contrasena, confirmContrasena;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        btnCancelar = findViewById(R.id.cancelarRegistro);
        btnRegistro = findViewById(R.id.registrarse);
        rbAlumnoInscrito = findViewById(R.id.RB_alumnoInscrito);
        rbNuevoIngreso = findViewById(R.id.RB_nuevoIngreso);

        editNumeroMatricula = findViewById(R.id.PT_numeroMatricula);
        nombre = findViewById(R.id.nombreText);
        apellidoPaterno = findViewById(R.id.paternoText);
        apelllidoMaterno = findViewById(R.id.maternoText);
        contrasena = findViewById(R.id.contrasenaText);
        confirmContrasena = findViewById(R.id.confirmContrasenaText);
        User = findViewById(R.id.userText);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myRef = database.getReference("Users/"+User.getText().toString()+"/Usuario");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        String value = dataSnapshot.getValue(String.class);

                        if (!value.equalsIgnoreCase(User.getText().toString())) {
                            if (contrasena.getText().toString().equals(confirmContrasena.getText().toString())) {
                                // Write a message to the database
                                String reference = "Users/" + User.getText().toString();
                                myRef = database.getReference(reference + "/Nombre");
                                myRef.setValue(nombre.getText().toString());
                                myRef = database.getReference(reference + "/apellidoPaterno");
                                myRef.setValue(apellidoPaterno.getText().toString());
                                myRef = database.getReference(reference + "/apellidoMaterno");
                                myRef.setValue(apelllidoMaterno.getText().toString());
                                myRef = database.getReference(reference + "/Password");
                                myRef.setValue(contrasena.getText().toString());
                                myRef = database.getReference(reference + "/Usuario");
                                myRef.setValue(User.getText().toString());
                                Intent registro = new Intent(Registro.this, inicioSesion.class);
                                startActivity(registro);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), R.string.noCoincidden, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.userExist, Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Toast.makeText(getApplicationContext(),R.string.errorBD,Toast.LENGTH_LONG).show();
                    }
                });
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

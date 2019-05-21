package com.example.filecloud;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Registro extends AppCompatActivity {

    private Button btnCancelar;
    private Button btnRegistro;
    private RadioButton rbNuevoIngreso;
    private RadioButton rbAlumnoInscrito;
    private EditText User, Telefono;
    private RadioGroup rbgrupo;
    private EditText nombre, apellidoPaterno, apelllidoMaterno, contrasena, confirmContrasena, correo;

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
        rbgrupo = findViewById(R.id.radioGroup);

        nombre = findViewById(R.id.nombreText);
        apellidoPaterno = findViewById(R.id.paternoText);
        apelllidoMaterno = findViewById(R.id.maternoText);
        contrasena = findViewById(R.id.contrasenaText);
        confirmContrasena = findViewById(R.id.confirmContrasenaText);
        User = findViewById(R.id.userText);
        correo = findViewById(R.id.correo);
        Telefono = findViewById(R.id.phone);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nombre.getText().toString();
                String materno = apelllidoMaterno.getText().toString();
                String paterno = apellidoPaterno.getText().toString();
                String usuario = User.getText().toString();
                String contra = contrasena.getText().toString();
                String confirmCon = confirmContrasena.getText().toString();
                String email = correo.getText().toString();
                String tel = Telefono.getText().toString();

                if (usuario.isEmpty() || contra.isEmpty() || confirmCon.isEmpty() || name.isEmpty() || materno.isEmpty() || paterno.isEmpty() || email.isEmpty() || tel.isEmpty()){
                    Toast.makeText(getApplicationContext(), R.string.vacio, Toast.LENGTH_LONG).show();
                } else {
                    if (contrasena.getText().toString().equals(confirmContrasena.getText().toString())) {

                        myRef = database.getReference("Users/" + User.getText().toString() + "/Usuario");
                        myRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String value = dataSnapshot.getValue(String.class);
                                insertar(value);
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Failed to read value
                                Toast.makeText(getApplicationContext(), R.string.errorBD, Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.noCoincidden, Toast.LENGTH_LONG).show();
                    }
                }
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
                User.setHint(R.string.numControl);
                User.setVisibility(View.VISIBLE);
                contrasena.setVisibility(View.VISIBLE);
                confirmContrasena.setVisibility(View.VISIBLE);
            }
        });

        rbNuevoIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.setHint(R.string.usuario);
                User.setVisibility(View.VISIBLE);
                contrasena.setVisibility(View.VISIBLE);
                confirmContrasena.setVisibility(View.VISIBLE);
            }
        });
    }

    boolean insertado = true;

    public void insertar(String value){
        int tipoUser = -1;

        if (value == null && insertado) {
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
            myRef = database.getReference(reference + "/Correo");
            myRef.setValue(correo.getText().toString());

            if (rbNuevoIngreso.isChecked() && !rbAlumnoInscrito.isChecked()) {
                tipoUser = 1;
            } else {
                if (!rbNuevoIngreso.isChecked() && rbAlumnoInscrito.isChecked())
                    tipoUser = 2;
            }

            myRef = database.getReference(reference + "/tipoUsuario");
            myRef.setValue(tipoUser);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            Date date = new Date();
            String fecha = dateFormat.format(date);

            myRef = database.getReference(reference + "/fechaRegistro");
            myRef.setValue(fecha);

            myRef = database.getReference(reference + "/Telefono");
            myRef.setValue(Telefono.getText().toString());

            insertado = false;

            Toast.makeText(getApplicationContext(), R.string.insertadoUser, Toast.LENGTH_LONG).show();
            Intent registro = new Intent(Registro.this, inicioSesion.class);
            startActivity(registro);
            finish();
        } else if (insertado){
            Toast.makeText(getApplicationContext(), R.string.userExist, Toast.LENGTH_SHORT).show();
        }
    }
}

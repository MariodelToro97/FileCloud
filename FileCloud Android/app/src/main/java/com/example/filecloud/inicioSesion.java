package com.example.filecloud;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

public class inicioSesion extends AppCompatActivity {

    private Button btnCancelar;
    private Button btnIniciarSesion;
    private EditText Usuario, Password;
    private RadioButton rbRecuerdame;

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

        rbRecuerdame = findViewById(R.id.rememberme);

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
                usuario = false;
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
            contra = true;
            usuario = true;
            Toast.makeText(getApplicationContext(), R.string.noExisteUsuario, Toast.LENGTH_SHORT).show();
            Usuario.setText("");
            Password.setText("");
            rbRecuerdame.setChecked(false);
            Usuario.requestFocus();
        }
    }

    public void obtenerContra(String value) {
        String pass = Password.getText().toString();
        final String user = Usuario.getText().toString();

        BDUser bdUser = new BDUser(this, "personasBD", null, 1);
        final SQLiteDatabase db = bdUser.getWritableDatabase();
        final Intent iniciar = new Intent(inicioSesion.this, documentosElegir.class);

        if (value.equals(pass) && contra){
            contra = false;
            myRef = database.getReference("Users/" + user + "/tipoUsuario");
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int value = dataSnapshot.getValue(Integer.class);

                    if (value == 0){
                        Toast.makeText(getApplicationContext(), R.string.admin, Toast.LENGTH_LONG).show();
                        Usuario.setText("");
                        Password.setText("");
                        rbRecuerdame.setChecked(false);
                        Usuario.requestFocus();
                        contra = true;
                        usuario = true;
                    } else {
                        if (db != null){

                            if (rbRecuerdame.isChecked()) {
                                ContentValues registronuevo = new ContentValues();
                                registronuevo.put("Nombre", user);

                                long i = db.insert("Usuario", null, registronuevo);

                                if (i > 0) {
                                    Toast.makeText(getApplicationContext(), R.string.InicioCorrecto, Toast.LENGTH_SHORT).show();

                                    iniciar.putExtra("USUARIO", user);
                                    startActivity(iniciar);
                                    finish();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), R.string.InicioCorrecto, Toast.LENGTH_SHORT).show();

                                iniciar.putExtra("USUARIO", user);
                                startActivity(iniciar);
                                finish();
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Toast.makeText(getApplicationContext(), R.string.errorBD, Toast.LENGTH_LONG).show();
                }
            });

        } else if (contra){
            contra = true;
            usuario = true;
            Toast.makeText(getApplicationContext(), R.string.contraIncorrecta, Toast.LENGTH_SHORT).show();
            Password.setText("");
            Password.requestFocus();
        }
    }
}

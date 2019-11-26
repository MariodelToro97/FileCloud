package com.example.filecloud;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class inicioSesion extends AppCompatActivity {

    private EditText Usuario, Password;
    private CheckBox rbRecuerdame;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        //Cambia el color de la barra de navegación
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorAbajo));
        //Cambia el color de la barra de notificaciones
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorArriba));

        Button btnRegistrar = findViewById(R.id.registrarseLogin);
        Button btnIniciarSesion = findViewById(R.id.iniciarSesionP);

        Usuario = findViewById(R.id.userSesion);
        Password = findViewById(R.id.passwordSesion);

        final ProgressDialog progressDialog = new ProgressDialog(inicioSesion.this);

        rbRecuerdame = findViewById(R.id.rememberme);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sesion = new Intent(inicioSesion.this, Registro.class);
                startActivity(sesion);
                finish();
            }
        });

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = Usuario.getText().toString();
                String contra = Password.getText().toString();

                progressDialog.setTitle(R.string.datosRegistro);
                progressDialog.setMessage("Los datos están siendo verificados");
                progressDialog.setCancelable(false);
                progressDialog.show();

                if (user.isEmpty() || contra.isEmpty()){
                    Toast.makeText(getApplicationContext(), R.string.vacio, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else {
                    obtenerUsuario(user, progressDialog);
                }
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
                        Toast.makeText(inicioSesion.this, R.string.descarga, Toast.LENGTH_LONG).show();

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
                        Toast.makeText(inicioSesion.this, R.string.actualizarDesp, Toast.LENGTH_LONG).show();
                    }
                })
                .setButtonDoNotShowAgain(R.string.noInteresado)
                .setButtonDoNotShowAgainClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(inicioSesion.this, R.string.cancelUpd, Toast.LENGTH_LONG).show();
                    }
                });
        appUpdater.start();
    }

    private static final int INTERVALO = 2000; //2 segundos para salir
    private long tiempoPrimerClick;

    @Override
    public void onBackPressed() {
        if (tiempoPrimerClick + INTERVALO > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }else {
            Toast.makeText(this, R.string.reply, Toast.LENGTH_SHORT).show();
        }
        tiempoPrimerClick = System.currentTimeMillis();
    }

    public void obtenerUsuario(String user, final ProgressDialog progressDialog){
        myRef = database.getReference("Users/" + user + "/Usuario");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                checarUsuario(value, progressDialog);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getApplicationContext(), R.string.errorBD, Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }

    public void checarPass(final ProgressDialog progressDialog){
        String user = Usuario.getText().toString();
            myRef = database.getReference("Users/" + user + "/Password");
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.getValue(String.class);
                    obtenerContra(Objects.requireNonNull(value), progressDialog);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Toast.makeText(getApplicationContext(), R.string.errorBD, Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            });
    }

    boolean contra = true;

    public void checarUsuario(String value, final ProgressDialog progressDialog){
        String user = Usuario.getText().toString();

        if (value != null){
            myRef = database.getReference("Users/" + user + "/cuentaVerificada");
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int value = dataSnapshot.getValue(Integer.class);

                    if (value == 0) {
                        Toast.makeText(getApplicationContext(), R.string.noVerficada, Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    } else {
                        checarPass(progressDialog);
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Toast.makeText(getApplicationContext(), R.string.errorBD, Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            });
        } else {
            contra = true;
            Toast.makeText(getApplicationContext(), R.string.noExisteUsuario, Toast.LENGTH_SHORT).show();
            Usuario.setText("");
            Password.setText("");
            rbRecuerdame.setChecked(false);
            Usuario.requestFocus();
            progressDialog.dismiss();
        }
    }

    public void obtenerContra(String value, final ProgressDialog progressDialog) {
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
                        progressDialog.dismiss();
                    } else {
                        if (db != null){

                            if (rbRecuerdame.isChecked()) {
                                ContentValues registronuevo = new ContentValues();
                                registronuevo.put("Nombre", user);

                                long i = db.insert("Usuario", null, registronuevo);

                                if (i > 0) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), R.string.InicioCorrecto, Toast.LENGTH_SHORT).show();

                                    iniciar.putExtra("USUARIO", user);
                                    startActivity(iniciar);
                                    finish();
                                }
                            } else {
                                progressDialog.dismiss();
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
                    progressDialog.dismiss();
                }
            });

        } else if (contra){
            Toast.makeText(getApplicationContext(), R.string.contraIncorrecta, Toast.LENGTH_SHORT).show();
            Password.setText("");
            Password.requestFocus();
            progressDialog.dismiss();
        }
    }
}

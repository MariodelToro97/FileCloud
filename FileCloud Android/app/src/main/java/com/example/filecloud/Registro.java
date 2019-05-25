package com.example.filecloud;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Registro extends AppCompatActivity {

    private Button btnCancelar;
    private Button btnRegistro;
    private RadioButton rbNuevoIngreso;
    private RadioButton rbAlumnoInscrito;
    private EditText User, Telefono;
    private EditText nombre, apellidoPaterno, apelllidoMaterno, contrasena, confirmContrasena, correo, curp;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    private StorageReference storageRef;


    private int VALOR_RETORNO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        storageRef = FirebaseStorage.getInstance().getReference();

        btnCancelar = findViewById(R.id.cancelarRegistro);
        btnRegistro = findViewById(R.id.registrarse);
        rbAlumnoInscrito = findViewById(R.id.RB_alumnoInscrito);
        rbNuevoIngreso = findViewById(R.id.RB_nuevoIngreso);

        nombre = findViewById(R.id.nombreText);
        apellidoPaterno = findViewById(R.id.paternoText);
        apelllidoMaterno = findViewById(R.id.maternoText);
        contrasena = findViewById(R.id.contrasenaText);
        confirmContrasena = findViewById(R.id.confirmContrasenaText);
        User = findViewById(R.id.userText);
        correo = findViewById(R.id.correo);
        Telefono = findViewById(R.id.phone);
        curp = findViewById(R.id.CURP);

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
                String CURP = curp.getText().toString();

                if (usuario.isEmpty() || contra.isEmpty() || CURP.isEmpty() || confirmCon.isEmpty() || name.isEmpty() || materno.isEmpty() || paterno.isEmpty() || email.isEmpty() || tel.isEmpty()){
                    Toast.makeText(getApplicationContext(), R.string.vacio, Toast.LENGTH_LONG).show();
                } else {
                    if (contrasena.getText().toString().equals(confirmContrasena.getText().toString())) {

                        myRef = database.getReference("Users/" + User.getText().toString() + "/Usuario");
                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                final String value = dataSnapshot.getValue(String.class);

                                if (value == null) {
                                    AlertDialog.Builder alert = new AlertDialog.Builder(Registro.this);
                                    alert.setMessage(R.string.mensajeVerificacion);
                                    alert.setTitle(R.string.documentoVerificacion);
                                    alert.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                            intent.setType("application/pdf");
                                            startActivityForResult(Intent.createChooser(intent, "Escoge tu archivo"), VALOR_RETORNO);
                                        }
                                    });
                                    alert.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Toast.makeText(getApplicationContext(), R.string.cancelado, Toast.LENGTH_SHORT).show();
                                            dialogInterface.cancel();
                                        }
                                    });

                                    AlertDialog dialog = alert.create();
                                    dialog.show();
                                } else {
                                    Toast.makeText(getApplicationContext(), R.string.userExist, Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
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

    public void insertadoDatos(){
        int tipoUser = -1;

        if (insertado) {
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
            myRef = database.getReference(reference + "/CURP");
            myRef.setValue(curp.getText().toString());
            myRef = database.getReference(reference + "/cuentaVerificada");
            myRef.setValue(0);

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
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            //Cancelado por el usuario
            Toast.makeText(getApplicationContext(), R.string.cancelado, Toast.LENGTH_SHORT).show();
        }
        if ((resultCode == RESULT_OK) && (requestCode == VALOR_RETORNO)) {
            //Procesar el resultado
            Uri file = data.getData(); //obtener el uri content

            final String usuario = User.getText().toString();

            final StorageReference riversRef = storageRef.child("nuevosUsuarios/" + usuario + "/documentoVerificacion");
            final ProgressDialog progressDialog = new ProgressDialog(Registro.this);

            progressDialog.setTitle(R.string.loadFile);
            progressDialog.setMessage("Espere un momento, se está realizando la carga de su documento");
            progressDialog.show();

            riversRef.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageRef.child("nuevosUsuarios/" + usuario + "/documentoVerificacion").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String reference = "newUsers/" + usuario;
                            myRef = database.getReference(reference + "/documentoVerificacion/FechaCarga");

                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                            Date date = new Date();
                            String fecha = dateFormat.format(date);
                            myRef.setValue(fecha);

                            myRef = database.getReference(reference + "/documentoVerificacion/urlDocumento");
                            myRef.setValue(uri.toString());
                            insertadoDatos();
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), R.string.cargaCompleta, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Toast.makeText(getApplicationContext(), R.string.errorDocumento, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

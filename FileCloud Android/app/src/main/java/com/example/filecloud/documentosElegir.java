package com.example.filecloud;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class documentosElegir extends AppCompatActivity {

    private Button btnCerrarSesion;
    private Button btnSolicitudes;
    private Button btnCargarDocumento;

    private int VALOR_RETORNO = 1;
    private StorageReference storageRef;

    String USUARIO;

    public documentosElegir() {
    }

    public String getUSUARIO() {
        return USUARIO;
    }

    public void setUSUARIO(String USUARIO) {
        this.USUARIO = USUARIO;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentos_elegir);

        btnCerrarSesion = findViewById(R.id.CerrarSesion);
        btnSolicitudes = findViewById(R.id.solicitudes);
        btnCargarDocumento = findViewById(R.id.cargarDocumentos);

        storageRef = FirebaseStorage.getInstance().getReference();

        btnCargarDocumento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), getUSUARIO(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(Intent.createChooser(intent, "Escoge tu archivo"), VALOR_RETORNO);
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
                cerrarSesion();
            }
        });
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

            //Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
            StorageReference riversRef = storageRef.child(USUARIO+"/");

            riversRef.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            Toast.makeText(getApplicationContext(), R.string.cargaCompleta, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            Toast.makeText(getApplicationContext(), R.string.errorDocumento, Toast.LENGTH_SHORT).show();
                        }
                    });
            //Toast.makeText(getApplicationContext(), , Toast.LENGTH_SHORT).show();
        }
    }

    public void cerrarSesion(){
        BDUser bdUser = new BDUser(this, "personasBD", null, 1);
        SQLiteDatabase db = bdUser.getWritableDatabase();

        if (db != null) {
            db.execSQL("DELETE FROM Usuario");

            Toast.makeText(getApplicationContext(), R.string.cerrarSesion, Toast.LENGTH_SHORT).show();

            Intent cerrarSesion = new Intent(documentosElegir.this, MainActivity.class);
            startActivity(cerrarSesion);
            finish();
        }
    }
}

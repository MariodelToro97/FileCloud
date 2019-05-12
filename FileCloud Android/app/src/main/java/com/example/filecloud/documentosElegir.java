package com.example.filecloud;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class documentosElegir extends AppCompatActivity {

    private Button btnCerrarSesion;
    private Button btnSolicitudes;
    private Button btnCargarDocumento;


    private documentoAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<Documentos> mDocumentosList = new ArrayList<>();

    private int VALOR_RETORNO = 1;

    private StorageReference storageRef;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    String USUARIO;
    String ARCHIVO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentos_elegir);

        btnCerrarSesion = findViewById(R.id.CerrarSesion);
        btnSolicitudes = findViewById(R.id.solicitudes);
        btnCargarDocumento = findViewById(R.id.cargarDocumentos);

        mRecyclerView = findViewById(R.id.recyclerList);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        storageRef = FirebaseStorage.getInstance().getReference();

        USUARIO = getIntent().getStringExtra("USUARIO");

        btnCargarDocumento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seleccionListaDocumentos();
            }
        });

        btnSolicitudes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent solicitudes = new Intent(documentosElegir.this, solicitudes.class);
                solicitudes.putExtra("USUARIO", USUARIO);
                startActivity(solicitudes);
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrarSesion();
            }
        });

        listDocumentos();
    }

    public void listDocumentos(){
        myRef = database.getReference("DOCUMENTS/"+USUARIO);
        //final ArrayList<String> files = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    mDocumentosList.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        mDocumentosList.add(new Documentos(ds.getKey(), ds.child("FechaCarga").getValue().toString(), USUARIO));
                    }

                    mAdapter = new documentoAdapter(mDocumentosList, R.layout.documentos);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getApplicationContext(), R.string.errorBD, Toast.LENGTH_LONG).show();
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
            Documentos docX = new Documentos(ARCHIVO);
            ARCHIVO = docX.getARCHIVO();

            Toast.makeText(getApplicationContext(), "VALOR NULLL " + ARCHIVO, Toast.LENGTH_SHORT).show();
            //Log.e("VALOR NULL", data.getStringExtra("HOLA"));
            if (ARCHIVO != null) {

                Uri file = data.getData(); //obtener el uri content

                //Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
                StorageReference riversRef = storageRef.child(USUARIO + "/" + ARCHIVO);

        /*riversRef.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
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
                });*/

                String reference = "DOCUMENTS/" + USUARIO;
                myRef = database.getReference(reference + "/" + ARCHIVO);
                myRef.setValue("1");

                myRef = database.getReference(reference + "/" + ARCHIVO + "/FechaCarga");

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                Date date = new Date();
                String fecha = dateFormat.format(date);

                myRef.setValue(fecha);
            } else {

            }

            listDocumentos();
        }
    }

    public void editarDocumento(String documento, Context context){
        Toast.makeText(context, "El documento que subirás es " + documento, Toast.LENGTH_SHORT).show();
        ARCHIVO = documento;

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        ((Activity) context).startActivityForResult(Intent.createChooser(intent, "Escoge tu archivo"), VALOR_RETORNO);
    }

    public void cerrarSesion(){
        final BDUser bdUser = new BDUser(this, "personasBD", null, 1);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(R.string.confirmCeraar);
        alert.setTitle(R.string.cerrarSes);
        alert.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                SQLiteDatabase db = bdUser.getWritableDatabase();

                if (db != null) {
                    db.execSQL("DELETE FROM Usuario");

                    Toast.makeText(getApplicationContext(), R.string.cerrarSesion, Toast.LENGTH_SHORT).show();

                    Intent cerrarSesion = new Intent(documentosElegir.this, MainActivity.class);
                    startActivity(cerrarSesion);
                    finish();
                }
            }
        });
        alert.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void seleccionListaDocumentos() {
        final CharSequence[] items = {"Acta de Nacimiento", "CURP", "Certificado", "Recibo de pago"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.seleccionFile);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "El documento que subirás es " + items[i], Toast.LENGTH_SHORT).show();
                switch (i) {
                    case 0:
                        ARCHIVO = "ACTA";
                        break;

                    case 1:
                        ARCHIVO = "CURP";
                        break;

                    case 2:
                        ARCHIVO = "CERTIFICADO";
                        break;

                    case 3:
                        ARCHIVO = "RECIBO";
                        break;
                }
                dialogInterface.cancel();

                Documentos docX = new Documentos(ARCHIVO);
                docX.setARCHIVO(ARCHIVO);

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(Intent.createChooser(intent, "Escoge tu archivo"), VALOR_RETORNO);
            }
        });
        AlertDialog a = builder.create();
        a.show();
    }
}
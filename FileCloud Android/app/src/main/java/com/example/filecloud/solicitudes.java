package com.example.filecloud;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class solicitudes extends AppCompatActivity {

    private Button Regresar;

    String USUARIO;
    private int VALOR_RETORNO = 1;
    private ArrayList<SolicitudesClass> mDocumentosList = new ArrayList<>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private StorageReference storageRef;
    DatabaseReference myRef;

    private SwipeRefreshLayout refreshLayout;
    private solicitudesAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudes);

        Regresar = findViewById(R.id.Regresar);

        mRecyclerView = findViewById(R.id.recyclerSolicitud);

        USUARIO = getIntent().getStringExtra("USUARIO");
        refreshLayout = findViewById(R.id.swipeRefreshLayout2);

        Regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regresar = new Intent(solicitudes.this, documentosElegir.class);
                regresar.putExtra("USUARIO", USUARIO);
                startActivity(regresar);
                finish();
            }
        });

        refreshLayout.setColorSchemeResources(
                R.color.s1,
                R.color.s2,
                R.color.s3,
                R.color.s4
        );

        // Iniciar la tarea asíncrona al revelar el indicador
        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        listDocumentos();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                refreshLayout.setRefreshing(false);
                            }
                        }, 4000);
                    }
                }
        );

        listDocumentos();
    }

    public void listDocumentos(){
        myRef = database.getReference("Solicitudes/"+USUARIO);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    mDocumentosList.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        mDocumentosList.add(new SolicitudesClass(ds.child("Requisito").getValue().toString(), USUARIO, ds.child("Documento").getValue().toString(), ds.child("Fecha").getValue().toString(), Integer.parseInt(ds.child("Estado").getValue().toString())));
                    }

                    mAdapter = new solicitudesAdapter(R.layout.solicitudes, mDocumentosList);
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

    public void atenderSoli(String documento, Context context){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        ((Activity) context).startActivityForResult(Intent.createChooser(intent, "Escoge tu archivo"), VALOR_RETORNO);
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
            SolicitudesClass docX = new SolicitudesClass(ARCHIVO);
            ARCHIVO = docX.getARCHIVO();

                Uri file = data.getData(); //obtener el uri content

                //Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
                StorageReference riversRef = storageRef.child(USUARIO + "/" + ARCHIVO);

                riversRef.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
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
                        });

            listDocumentos();
        }
    }
}

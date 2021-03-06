package com.example.filecloud;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.*;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class solicitudes extends AppCompatActivity {

    String USUARIO;
    String ARCHIVO;
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

        //Cambia el color de la barra de navegación
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorAbajo));
        //Cambia el color de la barra de notificaciones
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorArriba));

        Button regresar = findViewById(R.id.Regresar);

        mRecyclerView = findViewById(R.id.viewsSol);

        storageRef = FirebaseStorage.getInstance().getReference();

        USUARIO = getIntent().getStringExtra("USUARIO");
        refreshLayout = findViewById(R.id.swipeRefreshLayout2);

        regresar.setOnClickListener(new View.OnClickListener() {
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
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        myRef = database.getReference("Requisiciones/"+USUARIO);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    mDocumentosList.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        mDocumentosList.add(new SolicitudesClass(Objects.requireNonNull(ds.child("usuarioCreador").getValue()).toString(), ds.getKey(), Objects.requireNonNull(ds.child("fecha").getValue()).toString(), Objects.requireNonNull(ds.child("mensaje").getValue()).toString(), Objects.requireNonNull(ds.child("usuario").getValue()).toString()));
                    }

                    mAdapter = new solicitudesAdapter(R.layout.prueba_solicitudes, mDocumentosList);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Toast.makeText(getApplicationContext(), R.string.errorBD, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent regresar = new Intent(solicitudes.this, documentosElegir.class);
        regresar.putExtra("USUARIO", USUARIO);
        startActivity(regresar);
        finish();
    }

    public void atenderSoli(String documento, Context context, String user){
        documentosSubidos(documento, context, user);
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
            BDUser sql = new BDUser(this, "personasBD", null, 1);
            final SQLiteDatabase db = sql.getReadableDatabase();

            if (db != null) {
                Cursor c = db.rawQuery("SELECT * FROM Documentos", null);

                int i = c.getCount();

                if (c.moveToFirst()) {
                    do {
                        ARCHIVO = c.getString(0);
                    } while (c.moveToNext());
                }
                Uri file = data.getData(); //obtener el uri content
                //Uri file = Uri.fromFile(new File(path));
                cargarDocumentoProcess(file, db);
            }
            listDocumentos();
        }
    }

    public void cargarDocumentoProcess(Uri file, final SQLiteDatabase db){
        final StorageReference riversRef = storageRef.child(USUARIO + "/" + ARCHIVO);
        final ProgressDialog progressDialog = new ProgressDialog(solicitudes.this);

        progressDialog.setTitle(R.string.loadFile);
        progressDialog.setMessage("Espere un momento, se está realizando la carga de su documento");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        riversRef.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageRef.child(USUARIO + "/" + ARCHIVO).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                    String reference = "DOCUMENTS/" + USUARIO;
                    myRef = database.getReference(reference + "/" + ARCHIVO + "/FechaCarga");

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    Date date = new Date();
                    String fecha = dateFormat.format(date);
                    myRef.setValue(fecha);

                    myRef = database.getReference(reference + "/" + ARCHIVO + "/urlDocumento");
                    myRef.setValue(uri.toString());
                    eliminarRequisicion();
                    progressDialog.dismiss();
                    listDocumentos();
                    db.execSQL("DELETE FROM Documentos");
                    Toast.makeText(getApplicationContext(), R.string.cargaCompleta, Toast.LENGTH_SHORT).show();

                    Intent regresar = new Intent(solicitudes.this, documentosElegir.class);
                    regresar.putExtra("USUARIO", USUARIO);
                    startActivity(regresar);
                    finish();
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

    public void eliminarRequisicion(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Requisiciones/");
        DatabaseReference currentUserBD = mDatabase.child(USUARIO+"/"+ARCHIVO);
        currentUserBD.removeValue();

        Toast.makeText(getApplicationContext(), R.string.deleteFile, Toast.LENGTH_SHORT).show();
    }

    public void documentosSubidos(final String documento, final Context context, String user){
        myRef = database.getReference("DOCUMENTS/"+user + "/" + documento);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    saltoCarga(documento, context);
                } else {
                    guardadoDocuLocal(documento, context);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Toast.makeText(getApplicationContext(), R.string.errorBD, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void saltoCarga(final String documento, final Context context){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setMessage(R.string.alertEditFile);
        alert.setTitle(R.string.confirmEditFile);
        alert.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                guardadoDocuLocal(documento, context);
            }
        });
        alert.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).setCancelable(false);

        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void guardadoDocuLocal(final String documento, final Context context){
        BDUser bdUser = new BDUser(context, "personasBD", null, 1);
        final SQLiteDatabase db = bdUser.getWritableDatabase();

        if (db != null) {
            db.execSQL("DELETE FROM Documentos");
            Toast.makeText(context, "El documento que subirás es " + documento, Toast.LENGTH_SHORT).show();

            ContentValues editFile = new ContentValues();
            editFile.put("Nombre", documento);

            long h = db.insert("Documentos", null, editFile);

            if (h > 0) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                ((Activity) context).startActivityForResult(Intent.createChooser(intent, "Escoge tu archivo"), VALOR_RETORNO);
            }
        }
    }
}

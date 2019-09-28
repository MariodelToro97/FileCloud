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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;

import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
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

public class documentosElegir extends AppCompatActivity {

    private SwipeRefreshLayout refreshLayout;

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

        Button btnCerrarSesion = findViewById(R.id.CerrarSesion);
        Button btnSolicitudes = findViewById(R.id.solicitudes);
        Button btnCargarDocumento = findViewById(R.id.cargarDocumentos);

        mRecyclerView = findViewById(R.id.recyclerList);
        refreshLayout = findViewById(R.id.swipeRefreshLayout);

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
                finish();
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrarSesion();
            }
        });

        // Seteamos los colores que se usarán a lo largo de la animación
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

        BDUser sql = new BDUser(this, "personasBD", null, 1);
        SQLiteDatabase db = sql.getReadableDatabase();
        db.execSQL("DELETE FROM Documentos");
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
                        Toast.makeText(documentosElegir.this, R.string.descarga, Toast.LENGTH_LONG).show();

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
                        Toast.makeText(documentosElegir.this, R.string.actualizarDesp, Toast.LENGTH_LONG).show();
                    }
                })
                .setButtonDoNotShowAgain(R.string.noInteresado)
                .setButtonDoNotShowAgainClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(documentosElegir.this, R.string.cancelUpd, Toast.LENGTH_LONG).show();
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

    public void listDocumentos(){
        myRef = database.getReference("DOCUMENTS/"+USUARIO);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    mDocumentosList.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        mDocumentosList.add(new Documentos(ds.getKey(), Objects.requireNonNull(ds.child("FechaCarga").getValue()).toString(), USUARIO));
                    }

                    mAdapter = new documentoAdapter(mDocumentosList, R.layout.documentos);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Cancelado por el usuario
        if (resultCode == RESULT_CANCELED)
            Toast.makeText(getApplicationContext(), R.string.cancelado, Toast.LENGTH_SHORT).show();
        if ((resultCode == RESULT_OK) && (requestCode == VALOR_RETORNO)) {
            //Procesar el resultado

            BDUser sql = new BDUser(this, "personasBD", null, 1);
            final SQLiteDatabase db = sql.getReadableDatabase();

            if (db != null) {
                Cursor c = db.rawQuery("SELECT * FROM Documentos", null);

                int i = c.getCount();

                if (i == 0) {
                    Uri file = data.getData(); //obtener el uri content
                    //Uri file = Uri.fromFile(new File(path));
                    cargarDocumentoProcess(file, db);

                } else {
                    if (c.moveToFirst()) {
                        do {
                            ARCHIVO = c.getString(0);
                        } while (c.moveToNext());
                    }
                    Uri file = data.getData(); //obtener el uri content
                    //Uri file = Uri.fromFile(new File(path));
                    cargarDocumentoProcess(file, db);
                }
            }
        }
    }

    public void cargarDocumentoProcess(Uri file, final SQLiteDatabase db){
        final StorageReference riversRef = storageRef.child(USUARIO + "/" + ARCHIVO);
        final ProgressDialog progressDialog = new ProgressDialog(documentosElegir.this);

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
                    progressDialog.dismiss();
                    listDocumentos();
                    db.execSQL("DELETE FROM Documentos");
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

    public void eliminarDocumento(final String usuario, final String documento, final Context context){

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setMessage(R.string.confirmDeleteFile);
        alert.setTitle(R.string.borrarDocumento);
        alert.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                eliminateDocument eliminateDocument = new eliminateDocument();

                Intent splash = new Intent(context, splashFileCloud.class);
                context.startActivity(splash);

                eliminateDocument.eliminar(usuario, documento, context);

                Toast.makeText(context, R.string.deleteFile, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, documentosElegir.class);
                ((Activity) context).finish();
                //context.startActivity(intent);
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

    public void editarDocumento(final String documento, final Context context){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setMessage(R.string.alertEditFile);
        alert.setTitle(R.string.confirmEditFile);
        alert.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
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
        }).setCancelable(false);

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

                documentosSubidos();
            }
        });
        AlertDialog a = builder.create();
        a.show();
    }

    public void documentosSubidos(){
        myRef = database.getReference("DOCUMENTS/"+USUARIO + "/" + ARCHIVO);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    saltoCarga();
                } else {
                    Toast.makeText(getApplicationContext(), "El documento que subirás es " + ARCHIVO, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("application/pdf");
                    startActivityForResult(Intent.createChooser(intent, "Escoge tu archivo"), VALOR_RETORNO);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Toast.makeText(getApplicationContext(), R.string.errorBD, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void saltoCarga(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(R.string.alertEditFile);
        alert.setTitle(R.string.confirmEditFile);
        alert.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "El documento que subirás es " + ARCHIVO, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(Intent.createChooser(intent, "Escoge tu archivo"), VALOR_RETORNO);
            }
        });
        alert.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        alert.setCancelable(false);

        AlertDialog dialog = alert.create();
        dialog.show();
    }
}
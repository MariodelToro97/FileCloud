package com.example.filecloud;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;


public class eliminateDocument extends AppCompatActivity {

    FirebaseStorage storageRef = FirebaseStorage.getInstance();

    public void eliminar(final String user, final String documento, final Context context){
        // Create a reference to the file to delete
        StorageReference desertRef = storageRef.getReference(user + "/" + documento);

        // Delete the file
        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // File deleted successfully
                eliminarDos(user, documento);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NotNull Exception exception) {
                // Uh-oh, an error occurred!
                Toast.makeText(context, R.string.errorDeleteFile, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void eliminarDos(String user, String documento){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("DOCUMENTS/");
        DatabaseReference currentUserBD = mDatabase.child(user+"/"+documento);
        currentUserBD.removeValue();
    }
}

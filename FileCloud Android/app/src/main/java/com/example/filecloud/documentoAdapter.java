package com.example.filecloud;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class documentoAdapter extends RecyclerView.Adapter<documentoAdapter.ViewHolder> {

    private int resource;
    private ArrayList<Documentos> documentoLista;

    documentoAdapter(ArrayList<Documentos> documentoLista, int resource){
        this.documentoLista = documentoLista;
        this.resource = resource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resource, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int index) {
        Documentos documento = documentoLista.get(index);

        viewHolder.nombre.setText(documento.getNombre());
        viewHolder.fecha.setText("Fecha de carga: " + documento.getFecha());
        viewHolder.usuario.setText(documento.getUsuario());

        viewHolder.setOnClickListener();
    }

    @Override
    public int getItemCount() {
        return documentoLista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nombre, fecha, usuario;
        View view;
        private Button btnEditarDocumento;
        private Button btnEliminarDocumento;
        private Context context;

        ViewHolder(View view) {
            super(view);

            this.view = view;
            this.nombre = view.findViewById(R.id.nombreDocumento);
            this.fecha = view.findViewById(R.id.fechaDocumento);
            this.usuario = view.findViewById(R.id.usuarioDocumento);
            this.btnEditarDocumento = view.findViewById(R.id.editarRecycler);
            this.btnEliminarDocumento = view.findViewById(R.id.deleteRecycler);
            this.context = view.getContext();
        }

        void setOnClickListener() {
            btnEditarDocumento.setOnClickListener(this);
            btnEliminarDocumento.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            documentosElegir elegir = new documentosElegir();
            switch (view.getId()){
                case  R.id.editarRecycler:
                    elegir.editarDocumento(nombre.getText().toString(), context);
                    break;

                case R.id.deleteRecycler:
                    elegir.eliminarDocumento(usuario.getText().toString(), nombre.getText().toString(), context);
                    //elegir.listDocumentos();
                    break;
            }
        }
    }
}


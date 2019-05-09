package com.example.filecloud;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class documentoAdapter extends RecyclerView.Adapter<documentoAdapter.ViewHolder> {

    private int resource;
    private ArrayList<Documentos> documentoLista;

    public documentoAdapter(ArrayList<Documentos> documentoLista, int resource){
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
    }

    @Override
    public int getItemCount() {
        return documentoLista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre, fecha;
        public View view;

        public ViewHolder(View view) {
            super(view);

            this.view = view;
            this.nombre = view.findViewById(R.id.nombreDocumento);
            this.fecha = view.findViewById(R.id.fechaDocumento);
        }
    }
}

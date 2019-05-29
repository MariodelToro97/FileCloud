package com.example.filecloud;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class solicitudesAdapter extends RecyclerView.Adapter<solicitudesAdapter.ViewHolder> {

    private int resource;
    private ArrayList<SolicitudesClass> solicitudLista;

    public solicitudesAdapter(int resource, ArrayList<SolicitudesClass> solicitudLista) {
        this.resource = resource;
        this.solicitudLista = solicitudLista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resource, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        SolicitudesClass solicitud = solicitudLista.get(i);

        viewHolder.txtNombre.setText(solicitud.getDocumento());
        viewHolder.txtFecha.setText("Fecha de solicitud: " + solicitud.getFecha());
        viewHolder.txtSolicitante.setText(solicitud.getUsuarioRequisito());
        viewHolder.mensaje.setText(solicitud.getMensaje());
        viewHolder.usuario.setText(solicitud.getUser());

        viewHolder.setOnClickListener();
    }

    @Override
    public int getItemCount() {
        return solicitudLista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public View view;
        private Button btnAtencion;
        private TextView txtNombre, txtFecha, txtSolicitante, mensaje, usuario;
        private Context context;

        public ViewHolder(View view) {
            super(view);

            this.view = view;
            this.btnAtencion = view.findViewById(R.id.btnAtenderSolicitud);
            this.txtFecha = view.findViewById(R.id.txtFechaSolicitud);
            this.txtNombre = view.findViewById(R.id.txtNombreDocumento);
            this.txtSolicitante = view.findViewById(R.id.txtNombreSolicitudes);
            this.mensaje = view.findViewById(R.id.mensa);
            this.context = view.getContext();
            this.usuario = view.findViewById(R.id.usuarioSolicitud);
        }

        void setOnClickListener() {
            btnAtencion.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            solicitudes solicitudes = new solicitudes();
            solicitudes.atenderSoli(txtNombre.getText().toString(), context, usuario.getText().toString());
        }
    }
}

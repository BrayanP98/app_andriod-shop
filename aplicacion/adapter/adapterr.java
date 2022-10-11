package com.example.aplicacion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacion.ItemClickInterface;
import com.example.aplicacion.R;
import com.example.aplicacion.carrito.carritoCop;
import com.example.aplicacion.clickCantidad;
import com.example.aplicacion.productos.productosver;

import java.util.ArrayList;

public class adapterr extends RecyclerView.Adapter <adapterr.ViewHolder> implements View.OnClickListener {
    ArrayList<carritoCop> model;
    LayoutInflater inflater;
    productosver producto;

    public adapterr(Context context, ArrayList< carritoCop> model){
        this.inflater= LayoutInflater.from(context);
        this.model= model;
    }
    public adapterr(ArrayList< carritoCop> model) {
        this.model = model;


    }

    //listener
    private  View.OnClickListener listener;




    @NonNull
    @Override
    public adapterr.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=  LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_productos,null,false);
        v.  setOnClickListener(this);


        return new adapterr.ViewHolder(v);
    }

    public void setOnClickListener(View.OnClickListener listener ){

        this.listener=listener;
    }
    int cantt=0;


    @Override
    public void onBindViewHolder(@NonNull  adapterr.ViewHolder holder, int position) {
        //  holder.asignardatos(model.get(position).getNombre());
        //  holder.asignardatos(model.get(position).getValor());
        holder.nombre.setText(model.get(position).getValor());
        holder.valor.setText(model.get(position).getNombre());
        holder.cant.setText(model.get(position).getCant());
        String cant12= holder.cant.getText().toString().trim();
        //cantt=Integer.parseInt(cant12);


        holder.mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cann=Integer.toString(cantt);
                cantt=cantt+1;
                holder.cant.setText(cann);
            }
        });

        holder.menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String cann=Integer.toString(cantt);
                cantt=cantt-1;
                holder.cant.setText(cann);
            }
        });



    }

    @Override
    public int getItemCount() {
        return model.size();
    }


    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombre, valor,id,cant,mas,menos;
        ImageView imagen;
        Button carrito;
        public ViewHolder( View itemView) {

            super(itemView);

            nombre=(TextView) itemView.findViewById(R.id.IDnombrcar);
            valor=(TextView)itemView.findViewById(R.id.IDvalorcar);
            // imagen=(ImageView)itemView.findViewById(R.id.IDfoto);
            cant=(TextView) itemView.findViewById(R.id.IDid);
            mas=(TextView) itemView.findViewById(R.id.IDmasCAR);
            menos=(TextView) itemView.findViewById(R.id.IdmenosCAR);

        }

        public void asignardatos(ArrayList<productosver> modell) {

            //   nombre.setText(modell);

        }


    }
}

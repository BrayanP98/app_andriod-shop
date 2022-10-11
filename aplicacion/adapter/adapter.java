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

import com.example.aplicacion.R;
import com.example.aplicacion.carrito.carritoCop;
import com.example.aplicacion.carrito.scroll_img;
import com.example.aplicacion.productos.productosver;

import java.util.ArrayList;

public class adapter extends RecyclerView.Adapter <adapter.ViewHolder> implements View.OnClickListener {
    ArrayList<scroll_img> model;
    LayoutInflater inflater;
    productosver producto;

    public adapter(Context context, ArrayList<scroll_img> model){
        this.inflater= LayoutInflater.from(context);
        this.model= model;
    }
    public adapter(ArrayList<scroll_img> model) {
        this.model = model;


    }

    //listener
    private  View.OnClickListener listener;




    @NonNull
    @Override
    public adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=  LayoutInflater.from(parent.getContext()).inflate(R.layout.image_scroll,null,false);
        v.  setOnClickListener(this);


        return new adapter.ViewHolder(v);
    }

    public void setOnClickListener(View.OnClickListener listener ){

        this.listener=listener;
    }


    @Override
    public void onBindViewHolder(@NonNull  adapter.ViewHolder holder, int position) {
        //  holder.asignardatos(model.get(position).getNombre());
        //  holder.asignardatos(model.get(position).getValor());
        holder.imagen.setImageResource(model.get(position).getImg());

        // holder.imagen.setImageResource(model.get(position).getImagen());


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
        TextView nombre, valor,id;
        ImageView imagen;
        Button carrito;
        public ViewHolder( View itemView) {

            super(itemView);


             imagen=(ImageView)itemView.findViewById(R.id.IDImg);


        }

        public void asignardatos(ArrayList<productosver> modell) {

            //   nombre.setText(modell);

        }


    }
}

package com.example.aplicacion.adapter;


import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacion.Fragment_product_buy;
import com.example.aplicacion.ItemClickInterface;
import com.example.aplicacion.R;
import com.example.aplicacion.carrito.carritoCop;
import com.example.aplicacion.cuerpo;
import com.example.aplicacion.productos.productosver;
import com.example.aplicacion.ui.gallery.GalleryFragment;

import java.util.ArrayList;


    public class adapterProductos extends RecyclerView.Adapter <adapterProductos.ViewHolder> implements View.OnClickListener{
        ArrayList<productosver> model;
        LayoutInflater inflater;
        productosver producto;
     private ItemClickInterface itemClickInterface;

       public int id;
        public adapterProductos(Context context, ArrayList<productosver> model, ItemClickInterface itemClickInterface){
          this.inflater= LayoutInflater.from(context);
            this.model= model;
            this.itemClickInterface=itemClickInterface;
        }
        public adapterProductos(ArrayList<productosver> model) {
            this.model = model;


        }

        //listener
        private  View.OnClickListener listener;




        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v=  LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_lista,null,false);
          v.  setOnClickListener(this);


            return new ViewHolder(v);
        }

        public void setOnClickListener(View.OnClickListener listener ){

            this.listener=listener;
        }


        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
          //  holder.asignardatos(model.get(position).getNombre());
          //  holder.asignardatos(model.get(position).getValor());
            holder.nombre.setText(model.get(position).getNombre());
            holder.valor.setText(model.get(position).getValor());
            holder.imagen.setImageResource(model.get(position).getImagen());


        }

        @Override
        public int getItemViewType(int position) {


            return super.getItemViewType(position);
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


        GalleryFragment galleryFragment= new GalleryFragment();
        public class ViewHolder extends RecyclerView.ViewHolder{
            TextView nombre, valor;

            ImageView imagen;
            Button carrito;
            public ViewHolder( View itemView) {

                super(itemView);
                id=getAdapterPosition();
                nombre=(TextView) itemView.findViewById(R.id.IDnombre);
                valor=(TextView)itemView.findViewById(R.id.IDvalor);
                imagen=(ImageView)itemView.findViewById(R.id.IDfoto);
               // carrito.setOnClickListener((View.OnClickListener) this);
      itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
          itemClickInterface.ItemClick(getAdapterPosition());

          }
      });

      itemView.setOnLongClickListener(new View.OnLongClickListener() {
          @Override
          public boolean onLongClick(View view) {
              model.remove(getAdapterPosition());
              itemClickInterface.OnLongClick(getAdapterPosition());
              return false;
          }
      });
            }

            public void asignardatos(ArrayList<productosver> modell) {

             //   nombre.setText(modell);

            }


        }

    }


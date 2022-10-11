package com.example.aplicacion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.aplicacion.adapter.adapterProductos;
import com.example.aplicacion.productos.productosver;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;


public class FragmentProductosVer extends Fragment {
 adapterProductos adapterProducto;
 RecyclerView  recyclerViewProductos;
 ArrayList<productosver> listaProducto;


 //   producto producto;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_productos_ver, container, false);


        listaProducto= new ArrayList<>();
        cargarLisrta();
        recyclerViewProductos= view.findViewById(R.id.recyclerviewPro);
        recyclerViewProductos.setLayoutManager(new LinearLayoutManager(getContext()));
         //cargar  lista

         adapterProductos adapterProductos= new adapterProductos(listaProducto);
         recyclerViewProductos.setAdapter(adapterProductos);
        //cargar  lista
        //mostrarData();

        return inflater.inflate(R.layout.fragment_productos_ver, container, false);
    }
    public void cargarLisrta(){

        listaProducto.add(new productosver("ooll","12222",R.drawable.pol));
        listaProducto.add(new productosver("pgf","12222",R.drawable.discotk));
        listaProducto.add(new productosver("pgfg","12222",R.drawable.pol));
        listaProducto.add(new productosver("pgfg","12222",R.drawable.pol));
        listaProducto.add(new productosver("pgfg","12222",R.drawable.pol));

    }
   //public void mostrarData(){
     // Toast.makeText(getContext()," USUARIO"+listaProducto, Toast.LENGTH_SHORT).show();
     //  recyclerViewProductos.setLayoutManager(new LinearLayoutManager(getContext()));
     //  adapterProducto=new adapterProductos(getContext(),listaProducto);
     // /recyclerViewProductos.setAdapter(adapterProducto);
    //}

}
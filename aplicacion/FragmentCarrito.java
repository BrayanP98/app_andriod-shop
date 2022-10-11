package com.example.aplicacion;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicacion.adapter.adapter;
import com.example.aplicacion.adapter.adapterProductos;
import com.example.aplicacion.adapter.adapterr;
import com.example.aplicacion.baseDatos.Utilidades;
import com.example.aplicacion.baseDatos.layawerDBHelper;
import com.example.aplicacion.carrito.carritoCop;
import com.example.aplicacion.ui.gallery.GalleryFragment;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.

 * Use the {@link FragmentCarrito#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCarrito extends Fragment {
    private RecyclerView layoutManager;
    ArrayList<carritoCop> listaProducto1;
   adapter adapterprod;
    private RecyclerView recyclerViewCar;
    adapterProductos adapterProductos;
    ArrayList< carritoCop> miArreglo ;
    TextView masCar, menosCar,cantCar;
    String cantr="";
    BadgeDrawable badgeDrawable;
    int cont;
   ImageView x_salir;
    MenuItem Item;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Object ArrayList;

    public FragmentCarrito() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentCarrito.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCarrito newInstance(String param1, String param2) {
        FragmentCarrito fragment = new FragmentCarrito();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment,new GalleryFragment()).hide(new FragmentCarrito()).commit();

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //    cargarlista();
        cargarLisrta1();
        cantTotal();
        cuerpo cuerpo=new cuerpo();
        Bundle bundle = new Bundle();


        View view = inflater.inflate(R.layout.fragment_carrito, container, false);
        recyclerViewCar = (RecyclerView) view.findViewById(R.id.recycler_viewCarrito);
        mostrarData();
        masCar=(TextView) view.findViewById(R.id.IDmasCAR);
        menosCar=(TextView) view.findViewById(R.id.IdmenosCAR);
        cantCar=(TextView) view.findViewById(R.id.IDid);


        x_salir=(ImageView) view.findViewById(R.id.IDsalirCar) ;
        x_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((cuerpo)getActivity()).verNavs();

            }
        });
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);

        // reemplazando fragmentos
        transaction.remove(new Fragment_product_buy());
        transaction.addToBackStack(null);
        transaction.commit();
        return  view;
    }
    String [] arrData;
    private void cargarLisrta1(){



        // db.execSQL("CREATE TABLE cont(cantidad INTEGER)");
        // db.execSQL("DELETE  FROM productos WHERE 1 ");
        listaProducto1=new ArrayList<>();
        layawerDBHelper conn = new layawerDBHelper(this.getContext(), "db_usuario", null, 2);

        SQLiteDatabase db = conn.getReadableDatabase();
        String[] parametros = {"24"};
        String[] campos = {"nombre", "valor"};
        //  try {
     //   Cursor cursor = db.query( Utilidades.CAMPO_NOMBRE, campos, null,null,null,null,null);

        //Cursor cursor = db.rawQuery("select " + Utilidades.CAMPO_NOMBRE+"," + Utilidades.CAMPO_VALOR+" from " + Utilidades.TABLA_USUARIO+" where " + Utilidades.CAMPO_ID+"  = '" + "100" +"'", null);
        Cursor cursor = db.rawQuery("select *  from " + Utilidades.TABLA_PRODCAR, null);
        if (!cursor.moveToFirst()) {
            cursor.close();
            db.close();
        }

        do {
            String nombre = cursor.getString(1);
            String valor = cursor.getString(2);
            String id = cursor.getString(0);
            String cant = cursor.getString(3);


            carritoCop cant2 = new carritoCop(null, null, null,cant);

            carritoCop persona = new carritoCop(nombre, valor, id,cant);
            listaProducto1.add(persona);
          //  miArreglo.add(cant2);
        } while (cursor.moveToNext());
        db.close();
    }
    public void cargarLisrta() {
        carritoCop carritoCop1 = new carritoCop();
        layawerDBHelper conn = new layawerDBHelper(this.getContext(), "db_usuario", null, 1);

        SQLiteDatabase db = conn.getReadableDatabase();

        String[] parametros = {"24"};
        String[] campos = {"nombre", "valor"};
      //  try {
             //Cursor cursor = db.rawQuery("select " + Utilidades.CAMPO_NOMBRE+"," + Utilidades.CAMPO_VALOR+" from " + Utilidades.TABLA_USUARIO+" where " + Utilidades.CAMPO_ID+"  = '" + "100" +"'", null);
            Cursor cursor = db.rawQuery("select *  from " + Utilidades.TABLA_USUARIO, null);
            //while (cursor.moveToNext()) {
            while (cursor.moveToNext()){
                carritoCop persona = new carritoCop();
                String noma=cursor.getString(2);
               Toast.makeText(this.getContext(), " "+noma, Toast.LENGTH_SHORT).show();
                String nombre = cursor.getString(1);
                String valor = cursor.getString(2);
                String id = cursor.getString(0);
                String cant = cursor.getString(3);
              //  Toast.makeText(this.getContext(), " cantidad  es "+cant, Toast.LENGTH_SHORT).show();
                 persona = new carritoCop(nombre, valor, id, cant);
                listaProducto1.add(persona);
                //listaProducto1.add(carritoCop1);


            }
            cursor.close();
            db.close();
      //  } catch (Exception e) {
            Toast.makeText(this.getContext(), " nose ", Toast.LENGTH_SHORT).show();


       // }
        do {
            String nombre = cursor.getString(1);
            String valor = cursor.getString(2);
            String id = cursor.getString(0);
            //carritoCop persona = new carritoCop(nombre, valor, id);
          //  listaProducto1.add(persona);
        } while (cursor.moveToNext());

    }
    public void  ocultarTolbarmen(){
        View view= new View(getContext());
        badgeDrawable = BadgeDrawable.create(this.getContext());
        badgeDrawable.setNumber(5);
        BadgeUtils.attachBadgeDrawable(badgeDrawable,view.findViewById(R.id.toolbar),R.id.IDCarrito);


    }
    public void cargarlista() {

        // listaProducto.add(new productosver("pgf","12222",R.drawable.discotk));
    }
        @SuppressLint("UnsafeOptInUsageError")
        public void mostrarData(){
            int size=  listaProducto1.size();
            View view= new View(getContext());


         Toast.makeText(this.getContext(), " cantidad  es "+size, Toast.LENGTH_SHORT).show();
            adapterr adapter= new adapterr(listaProducto1);
           // recyclerViewCar.setItemAnimator(new DefaultItemAnimator());

            recyclerViewCar.setLayoutManager(new LinearLayoutManager(getContext()));
            // =new adapterProductos(getContext(),listaProducto);

            recyclerViewCar.setAdapter(adapter);

        }
    int cat=0;
    String  cat1="";
   public void cantTotal(){


       for (int i = 0; i < listaProducto1.size(); i++) {
          cat1=listaProducto1.get(i).getCant();

         cat=cat+cat;

           }




   }


}

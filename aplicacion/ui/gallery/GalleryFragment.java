package com.example.aplicacion.ui.gallery;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.aplicacion.Fragment_product_buy;
import com.example.aplicacion.ItemClickInterface;
import com.example.aplicacion.R;
import com.example.aplicacion.adapter.PagerAdapter;
import com.example.aplicacion.adapter.adapterProductos;
import com.example.aplicacion.baseDatos.Utilidades;
import com.example.aplicacion.baseDatos.layawerDBHelper;
import com.example.aplicacion.carrito.carritoCop;
import com.example.aplicacion.cuerpo;
import com.example.aplicacion.productos.productosver;
import com.example.aplicacion.productos.spacecraft;
import com.example.aplicacion.registrar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class GalleryFragment extends Fragment implements ItemClickInterface {
    cuerpo cuerpo;
    Button btncarrito;
    public  final int ids=0;
    FirebaseDatabase firebase=FirebaseDatabase.getInstance();
    DatabaseReference mDatabase;
    DatabaseReference db = firebase.getReference();
    productosver productosver1;
    FirebaseFirestore db1 = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    String nombre;
    private  RecyclerView.LayoutManager layoutManager;
    ArrayList<productosver> listaProducto;
    ArrayList<spacecraft> modelList;
    ArrayList<String> miArreglo ;
    TextView cajanombre;



    private GalleryViewModel galleryViewModel;
    private void inicioFireBase1(){
        FirebaseApp.initializeApp(getContext());
        firebase= firebase.getInstance();
        mDatabase=firebase.getReference()
        ;
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        inicioFireBase1();
        //consulta2();
          new cuerpo();


        //  consultaFirebase();
     cargarLisrta();
        consulta2();
       // inicioFirebase();
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        //final TextView textView = root.findViewById(R.id.text_gallery);
        recyclerView =(RecyclerView) root.findViewById(R.id.recycler_viewId);

        mostrarData();
recyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener());
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }

    public void consultaFirebase() {
        StorageReference folder= FirebaseStorage.getInstance().getReference().child("productos");
        // mDatabase.child("users").child("id"+":"+cajauser.getText().toString())
        mDatabase.child("productos").orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                         productosver comida = snapshot.getValue(productosver.class);
                        comida.setCod(snapshot.getKey());
                            listaProducto.add(comida);

                    }
                   String  nombre1= snapshot.child("cod").getValue().toString();

                 //   String contraseña=snapshot.child("Valor").getValue().toString();

                        Toast.makeText(getContext(), "Bienvenido"+"  "+nombre, Toast.LENGTH_SHORT).show();




                        //Toast.makeText(getContext(), "CONTRASEÑA INCORRECTA", Toast.LENGTH_SHORT).show();




                }else{
                    Toast.makeText(getContext(), "USUARIO NO EXISTENTE", Toast.LENGTH_SHORT).show();
                }

                //  String id= snapshot.child("id").getValue().toString();

                //   if(cajauser.equals(id)){
                // String nombre= snapshot.child(id).child("nombre").getValue().toString();
                //    Toast.makeText(getContext(), "si se pudo", Toast.LENGTH_SHORT).show();
                // }
                //  Toast.makeText(getContext(), "si se pudo"+, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "USUARIO NO ENCONTRADO", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private ArrayList<productosver> mMemos = new ArrayList<>();

    public void  consulta2(){
boolean  band= true;
        //ArrayList<spacecraft> modelList= new ArrayList<>();

        StorageReference folder= FirebaseStorage.getInstance().getReference().child("productos");
        // mDatabase.child("users").child("id"+":"+cajauser.getText().toString())

        mDatabase.child("productos").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
               // GenericTypeIndicator<spacecraft> t = new GenericTypeIndicator<spacecraft>() {};
               // spacecraft Spacecraft = snapshot.getValue(t);
               // for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    for (int i =0;i<1;i++) {
                    String  Key= snapshot.getKey();
                        mDatabase.child("productos").child(Key).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot1) {
                               // do{

                                       // for (int i = 0; i < 1; i++) {
                                            String nombre = snapshot1.child("nombre").getValue().toString();
                                            String valor = snapshot1.child("valor").getValue().toString();
                                            String imagen = snapshot1.child("imagen").getValue().toString();
                                          //  productosver persona = new productosver(nombre, valor, R.drawable.discotk);
                                        //    listaProducto.add(persona);
                                            //Toast.makeText(getContext(), "Bienvenido" + nombre+valor+imagen, Toast.LENGTH_SHORT).show();


                                  //  }while ()
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                   // comida.setCod(snapshot.getKey());

                     //   listaProducto.add(comida);



                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
    public void datos(String nombre1){

     productosver1.setNombre(nombre1);
        for(int i=0;i<4;i++) {
          //  Toast.makeText(getContext(), "Bienvenidoee" + i+"  " +nombre1, Toast.LENGTH_SHORT).show();
        }
    }

    public void cargarLisrta(){

       // Toast.makeText(getContext(), "Bienvenidoolee" + productosver1.getNombre(), Toast.LENGTH_SHORT).show();

        listaProducto=new ArrayList<productosver>();
        listaProducto.add(new productosver("Papael hijienico familia mega roolo ","$12222",R.drawable.www));
       listaProducto.add(new productosver("pgfg","$12222",R.drawable.pol));
        listaProducto.add(new productosver("pgfg","12222",R.drawable.pol));
        listaProducto.add(new productosver("pgfg","12222",R.drawable.www));
        listaProducto.add(new productosver("pgfg","12222",R.drawable.goku));
        listaProducto.add(new productosver("pgfg","12222",R.drawable.goku));
        listaProducto.add(new productosver("pgfg","12222",R.drawable.pol));
        listaProducto.add(new productosver("pgfg","12222",R.drawable.pol));
        listaProducto.add(new productosver("pgfg","12222",R.drawable.pol));
        listaProducto.add(new productosver("pgfg","12222",R.drawable.pol));

    }
    public void cargarLista1(){

        layawerDBHelper conn= new layawerDBHelper(this.getContext(),"db_usuario",null,1);

        SQLiteDatabase db=conn.getReadableDatabase();
        String[] parametros={"24"};
        String[] campos={"nombre","valor"};
        try {
            Cursor cursor = db.rawQuery("select " + Utilidades.CAMPO_NOMBRE+"," + Utilidades.CAMPO_VALOR+" from " + Utilidades.TABLA_USUARIO+" where " + Utilidades.CAMPO_ID+"  = '" + "100" +"'", null);
            //  db.delete("usuarios","245",null);
            //   Cursor cursor=db.query("usuarios",null,null,null,null,null,null);
            //ursor cursor=db.query("usuarios",campos,"id"+"=?",parametros,null,null,null);
            cursor.moveToNext();
            for(int i=0;i <3;i++){
                Toast.makeText(getContext()," el"+cursor.getString(i), Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(getContext()," el"+cursor.getString(2), Toast.LENGTH_SHORT).show();
            cursor.close();
            db.close();
        }catch (Exception e){
            Toast.makeText(getContext()," nose pudo", Toast.LENGTH_SHORT).show();


        }

    }
    adapterProductos adapter;
    public void mostrarData(){

        GridLayoutManager gridLayoutManager=new GridLayoutManager(this.getContext(),2,GridLayoutManager.VERTICAL,false);
        miArreglo = new ArrayList<String>();
        recyclerView.setLayoutManager(gridLayoutManager);
       // =new adapterProductos(getContext(),listaProducto);
        adapter= new adapterProductos(getContext(),listaProducto,this);
        recyclerView.setAdapter(adapter);




    }
    public void pasaABuy(int pos){





    }

    ItemTouchHelper.SimpleCallback simpleCallback= new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|
            ItemTouchHelper.DOWN | ItemTouchHelper.START| ItemTouchHelper.END,0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            Toast.makeText(getContext()," numero", Toast.LENGTH_SHORT).show();
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };

    Boolean saved=null;
    ArrayList<String> spacecrafts=new ArrayList<>();

    public void FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }

    //SAVE
    public Boolean save(spacecraft Spacecraft)
    {
        if(Spacecraft==null)
        {
            saved=false;
        }else {

            try
            {
                db.child("productos").push().setValue(Spacecraft);
                saved=true;
            }catch (DatabaseException e)
            {
                e.printStackTrace();
                saved=false;
            }

        }

        return saved;
    }

    //READ
    public ArrayList<String> retrieve()
    {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return spacecrafts;
    }

    private void fetchData(DataSnapshot dataSnapshot)
    {
        spacecrafts.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            String name=ds.getValue(spacecraft.class).getNombre();
            spacecrafts.add(name);
        }
    }


    @Override
    public void ItemClick(int position) {
      String nombreProd= listaProducto.get(position).getNombre();
        Fragment_product_buy fragment = new Fragment_product_buy(); // replace your custom fragment class
        Bundle bundle = new Bundle();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        bundle.putString("key",nombreProd); // use as per your need
        fragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.nav_host_fragment,fragment);
        fragmentTransaction.commit();


    }

    @Override
    public void OnLongClick(int position) {

        listaProducto.remove(position);
        adapter.notifyItemRemoved(position);

    }
}
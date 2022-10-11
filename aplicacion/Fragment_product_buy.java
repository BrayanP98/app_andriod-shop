package com.example.aplicacion;

import static android.app.ProgressDialog.show;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Icon;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicacion.adapter.adapter;
import com.example.aplicacion.baseDatos.Utilidades;
import com.example.aplicacion.baseDatos.layawerDBHelper;
import com.example.aplicacion.carrito.scroll_img;
import com.example.aplicacion.productos.productosver;
import com.example.aplicacion.ui.gallery.GalleryFragment;
import com.example.aplicacion.ui.slideshow.SlideshowFragment;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nex3z.notificationbadge.NotificationBadge;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_product_buy#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_product_buy extends Fragment {
    public static final  String nombres="Papel Higienico";
    int cont,cnt_not;
    NotificationBadge notificationBadge;
    private final int TIEMPO = 20000;
    private final int TIEMPO1 = 5000;
    Handler handler = new Handler();
    cuerpo cuerpo= new cuerpo();
      Icon carro;
    Button comprar;
    String nombres1="names";
    productosver productver= new productosver();
    ImageView fotoProd;
    ArrayList<productosver> model;
    TextView nombreProd2, cantidad,mas, menos, valor,descripcion;
    ArrayList<scroll_img> listaProducto1;
    GalleryFragment galleryFragment= new GalleryFragment();
    private AppBarConfiguration mAppBarConfiguration;
    private Menu menus;
    private MenuItem item2;
    ExtendedFloatingActionButton fab;
   Interpolator interpolador;
    BadgeDrawable badgeDrawable;
    ArrayList<String> miArreglo ;
   private HorizontalScrollView scroll;
    FirebaseDatabase firebase=FirebaseDatabase.getInstance();
    DatabaseReference mDatabase;
    DatabaseReference ref = firebase.getReference("productos");
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<productosver> listaProducto;
    private RecyclerView recyclerViewCar;
    String nombreProd1;
    int mum1 = 1;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_product_buy() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_product_buy.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_product_buy newInstance(String param1, String param2) {
        Fragment_product_buy fragment = new Fragment_product_buy();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {

        super.onStart();
    }
    private void inicioFireBase(){
        FirebaseApp.initializeApp(getContext());
        firebase= firebase.getInstance();
        mDatabase=firebase.getReference()
        ;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        View view = null;
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            nombreProd1 = bundle.getString("key", "2");
            Toast.makeText(getContext()," USUARIO"+nombreProd1, Toast.LENGTH_SHORT).show();

        }
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @SuppressLint("NewApi")
            @Override
            public void handleOnBackPressed() {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setReorderingAllowed(true);
                transaction.replace(R.id.nav_host_fragment, new SlideshowFragment());

                transaction.commit();
                fab.animate().cancel();
                handler.removeCallbacksAndMessages(null);

               ;
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }

    }
    @Override
    public void onHiddenChanged(boolean hidden) {
      //  Toast.makeText(getContext()," USUARIO", Toast.LENGTH_SHORT).show();
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onStop() {
        super.onStop();
        cerrarHandrler();
      //  transaction.remove(this).commit();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);
        inicioFireBase();
        lisFirebase();
       // ejecutarTaread ();
        View view=inflater.inflate(R.layout.fragment_product_buy, container, false);
      fab = view.findViewById(R.id.ADCar);
       interpolador = AnimationUtils.loadInterpolator(getContext(),
                android.R.interpolator.fast_out_slow_in) ;

        ejecutar ();
        recyclerViewCar = (RecyclerView) view.findViewById(R.id.recycler_IMg);
        //Bundle bundle=getArguments();
       // bundle = getActivity().getIntent().getExtras();
     //  String name=bundle.getString("nombre");
        //Toast.makeText(getContext()," USUARIO"+name, Toast.LENGTH_SHORT).show();
        badgeDrawable= BadgeDrawable.create(this.getContext());
        cargarlista();
        fotoProd=(ImageView) view.findViewById(R.id.IDfoto) ;
        nombreProd2=(TextView) view.findViewById(R.id.IDnombre1);
        cantidad=(TextView) view.findViewById(R.id.IDcantidad) ;
        mas=(TextView) view.findViewById(R.id.IDmas);
       descripcion=(TextView) view.findViewById(R.id. txtDescripcion) ;
        menos=(TextView) view.findViewById(R.id.Idmenos);
        valor=(TextView) view.findViewById(R.id.IDvalor1);
        cantidad.setText(" "+"0");
        Picasso.get().load(  "https://firebasestorage.googleapis.com/v0/b/pruebafirebase-79e9f.appspot.com/o/img_user%2Fgoku.jpg?alt=media&token=c21a01f3-3580-4975-b88d-459c3ce632ba").into(fotoProd);
         mostrarData();


       nombreProd2.setText(nombreProd1);


        menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                cont=cont-1;
                String cont1= Integer.toString(cont);
                cantidad.setText(" "+cont1);
                if(cont<=0){
                    cantidad.setEnabled(false);
                    menos.setEnabled(false);
                }else {
                    cantidad.setEnabled(true);
                    menos.setEnabled(true);
                }
            }
        });
        mas.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
               // mDatabase.child("productos").child("cod"+":"+"04455").child("descripcion").setValue("Dan un buen sonido, pero nada espectacular la verdad. Por el precio están bien, aunque esperaba más claridad y mejores bajos según las reseñas que leí; en todo caso, con un buen ecualizador se arregla. Lo que más incomoda es la presión de la diadema, no puedo usarlos por más de una hora porque");

                menos.setEnabled(true);
                cont=cont+1;
                String cont1= Integer.toString(cont);
                cantidad.setText(" "+cont1);
            }
        });
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Se presionó el FAB", Snackbar.LENGTH_LONG).show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Snackbar.make(view, "de nuevo en linea", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                cnt_not=cnt_not;
                final int[] mum = {1};

                alertDialogBuilder.setTitle("Confirmar!");

// Configura el mensaje.
                alertDialogBuilder
                        .setMessage("Hola Alex, Deseas agregar esete item al carro?")
                        .setCancelable(false)
                        .setPositiveButton("Si",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                cnt_not=cnt_not+1;
                                mum1 =badgeDrawable.getNumber();
                                registrarProd();
                                badgeDrawable.setNumber(cnt_not);
                                BadgeUtils.attachBadgeDrawable(badgeDrawable,getActivity().findViewById(R.id.toolbar),R.id.IDCarrito);

                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }

                        }).create().show();


            }




        });
        return view;
    }
public void lisFirebase(){
    StorageReference folder= FirebaseStorage.getInstance().getReference().child("productos");
    mDatabase.child("productos").child("cod"+":"+"04455").addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

                String nombre= snapshot.child("descripcion").getValue().toString();
            descripcion.setText(nombre);
               // Toast.makeText(getContext(), "Bienvenido"+"  "+nombre, Toast.LENGTH_SHORT).show();


        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
}
    public void cargarlista() {
        listaProducto1 = new ArrayList<scroll_img>();
        listaProducto1.add(new scroll_img(R.drawable.www));
        listaProducto1.add(new scroll_img(R.drawable.discotk));
        listaProducto1.add(new scroll_img(R.drawable.www));
        listaProducto1.add(new scroll_img(R.drawable.goku));

    }
    public void mostrarData(){

        adapter adapter= new adapter(listaProducto1);
       LinearLayoutManager gridLayoutManager=new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false);
        gridLayoutManager.scrollHorizontallyBy(2,  null,null);
        miArreglo = new ArrayList<String>();
        recyclerViewCar.setLayoutManager(gridLayoutManager);
        recyclerViewCar.setAdapter(adapter);

    }

    @SuppressLint("NewApi")
    public void imprimir(){
      View view=new View(this.getContext());
        fab.setScaleX(1);
        fab.setScaleY(1);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            final Interpolator interpolador = AnimationUtils.loadInterpolator(getContext(),
                    android.R.interpolator.fast_out_slow_in) ;

            fab.animate()
                    .scaleX(0)
                    .scaleY(0)
                    .setInterpolator(interpolador)
                    .setDuration(600)
                    .setStartDelay(1000)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            fab.animate()
                                    .scaleY(1)
                                    .scaleX(1)
                                    .setInterpolator(interpolador)
                                    .setDuration(1000)
                                    .start();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });

        }


    }

    public void cantCarrito(MenuItem item){
        item2=  item;
      // setBadgeCount((LayerDrawable) item2.getIcon(), "6");
        cantCarrito1("7");
    }

    public void cantCarrito2(){
        cantCarrito1("3");

       // setBadgeCount((LayerDrawable) item2.getIcon(),count);

    }
    public void cantCarrito1(String count){
       // item1=  item;
     setBadgeCount((LayerDrawable) item2.getIcon(),count);

    }
    Circle circle=new Circle();
    public   void setBadgeCount(LayerDrawable icon, String count){

        circle=new Circle();
//Llamamos nuestro método setCount y enciamos el numero de notificaciones
        circle.setCount(count);
        /*Mutable no compartirá su estado con ningún otro sorteo.*/
        icon.mutate();
        /*como les mencione anteriormente creamos dos capas una para la notificación y otra para agregar el circulo rojo en esta línea llamamos la capa y agregamos el circulo*/
        icon.setDrawableByLayerId(R.id.ic_badge, circle);
    }


    public void ejecutarTarea () {
        handler.postDelayed(new Runnable() {
            public void run() {


                handler.postDelayed(this, TIEMPO);
                Toast.makeText(getContext(), "Necesaria conexión a internet ", Toast.LENGTH_SHORT).show();
                cuerpo.setBadgeCount((LayerDrawable) item2,"7");
                handler.removeCallbacks(this);}


        }, TIEMPO);
        //handler.removeCallbacks(this,TIEMPO);

    }
    public void ejecutarTaread () {

        handler.postDelayed(new Runnable() {
            public void run() {

                imprimir();

                handler.postDelayed(this, TIEMPO);

            }

        }, TIEMPO);

    }
    int contk;
    public void ejecutar () {

        handler.postDelayed(new Runnable() {
            public void run() {
        contk=contk+1;
        if(contk==listaProducto1.size() ){
            contk=0;
        }
             recyclerViewCar.smoothScrollToPosition(contk);

                Toast.makeText(getContext(), "es "+ contk, Toast.LENGTH_SHORT).show();

                handler.postDelayed(this, TIEMPO1);

            }

        }, TIEMPO);

    }
    public void cerrarHandrler(){

        handler.removeCallbacksAndMessages(null);
    }
    int conr=0;
    public  void registrarProd(){
   conr=mum1;
   String connd=Integer.toString(conr);
        layawerDBHelper conn= new layawerDBHelper(this.getContext(),"db_usuario",null,2);

       // Toast.makeText(getContext()," numero", Toast.LENGTH_SHORT).show();
        SQLiteDatabase db=conn.getWritableDatabase();
        // conn.onCreate(db);
        //db.execSQL("DELETE FROM usuarios WHERE id='01'");
        ContentValues values= new ContentValues();
        values.put(Utilidades.CAMPO_ID,nombreProd2.getText().toString());
        values.put(Utilidades.CAMPO_NOMBRE,"5");
        values.put(Utilidades.CAMPO_VALOR,valor.getText().toString());
        values.put(Utilidades.CAMPO_CANT,cantidad.getText().toString());

        long idres= db.insert(Utilidades.TABLA_PRODCAR,Utilidades.CAMPO_ID,values);
       // values.put((Utilidades.CAMPO_CANTCAR),"2");

        db.close();

    }

}




package com.example.aplicacion;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.LayerDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewParent;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicacion.adapter.adapterProductos;
import com.example.aplicacion.carrito.carritoCop;
import com.example.aplicacion.productos.productosver;
import com.example.aplicacion.ui.home.HomeFragment;
import com.example.aplicacion.ui.juegos.JuegosFragment;
import com.example.aplicacion.ui.slideshow.SlideshowFragment;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class cuerpo extends AppCompatActivity {
   public static final  String nombres="names";
    public static final  String imagen="imagen";
    public static  boolean secionStart=false;

    private AppBarConfiguration mAppBarConfiguration;
    ImageView cara;
    private RecyclerView recyclerViewCar;
    FloatingActionButton fab;
    private  RecyclerView.LayoutManager layoutManager;
    ArrayList< productosver > listaProducto1;
    boolean band;
    TextView bienvenido, textView ;
    ArrayList<carritoCop> listaProducto;
    RecyclerView recyclerViewProductos;
    Toolbar toolbar;
    adapterProductos adapterProductos;
    ArrayList<String> miArreglo ;
    DrawerLayout drawer;
    NavController navController;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    DatabaseReference mDatabase;
    String usuario2="names";
    View bottomSheetBehavior;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener firebaseAuthListener;

    private DrawerLayout mDrawerLayout;
    private ExpandableListView expListView;
    private ActionBarDrawerToggle mDrawerToggle;
    ExpandableListAdapter listAdapterExpandable;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    FirebaseDatabase firebase = FirebaseDatabase.getInstance();
    @Override
    public void onStart() {
        inicioFireBase();
        checkCurrentUser();

        super.onStart();
    }

    private void inicioFireBase() {

        FirebaseApp.initializeApp(this);
        firebase = firebase.getInstance();
        mDatabase = firebase.getReference();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = null;
        setContentView(R.layout.activity_cuerpo);
       toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
   //   fab = (FloatingActionButton) findViewById(R.id.fab);
        //inicioFireBase();
       // setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(true);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      //  mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // obtiene el listview.

        // prepara datos para Header y Listado en ExpandableListView.
       // prepareListData();
        // configura Adapter.
       // listAdapterExpandable = new com.example.aplicacion.ExpandableListAdapter(this,listDataHeader, listDataChild);
        // configura Adapter en ExpandableListView.
      //  expListView.setAdapter(listAdapterExpandable);
        // Puedes expandir los grupos por default.
       // int count = listAdapterExpandable.getGroupCount();
      //  for ( int i = 0; i < count; i++ )
          //  expListView.expandGroup(i);

        FirebaseAuth.AuthStateListener firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    String name = user.getDisplayName().toString();
                    user.getPhotoUrl();

                }
            }
        };

        drawer = findViewById(R.id.drawer_layout);
         navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_secion)
                .setDrawerLayout(drawer)
                .build();
      //  getActionBar().setDisplayHomeAsUpEnabled(true);
       navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
      bottomSheetBehavior = findViewById(R.id.nav_home);
   //headerFfoto
     View headView= navigationView.getHeaderView(0);
        cara=(ImageView)headView.findViewById(R.id.IDcara);
        bienvenido=(TextView)headView.findViewById(R.id.textView_bienvenido);
        textView =findViewById(R.id.text_home);
        View n= findViewById(R.id.nav_host_fragmentC);
        n.setVisibility(View.INVISIBLE);
      //  Boolean secionI= getIntent().getBooleanExtra("session");
        loversf= getIntent().getBooleanExtra("perfect",false);
        String usuario2= getIntent().getStringExtra("names");
       String img= getIntent().getStringExtra("imagen");
        Picasso.get().load( img).into(cara);

        Boolean   loversf = getIntent().getBooleanExtra("perfect",true);
        //Toast.makeText(this, "hola"+usuario2, Toast.LENGTH_SHORT).show();
        bienvenido.setText(usuario2);
       // Picasso.get().load(  "https://firebasestorage.googleapis.com/v0/b/pruebafirebase-79e9f.appspot.com/o/img_user%2Fgoku.jpg?alt=media&token=c21a01f3-3580-4975-b88d-459c3ce632ba").into(cara);
       // Picasso.get().load("http://192.168.0.110:/login/uploads/goku.jpg").into(cara);

       //mostrarData();
      //  listaProducto= new ArrayList<>();
      //  recyclerViewProductos= findViewById(R.id.recyclerviewProductos);
      // recyclerViewProductos.setLayoutManager(new LinearLayoutManager(this));
      //  cargarLisrta();
      // mostrarData();
       // adapterProductos adapterProductoss= new adapterProductos(listaProducto);
       // recyclerViewProductos.setAdapter(adapterProductoss);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
              ViewParent i=  navigationView.getParent();

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                int id= item.getItemId();
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                switch (id){

                    case R.id.nav_home:
                        toolbar.setTitle(item.getTitle().toString());
                        drawer.closeDrawer(GravityCompat.START);
                        fragmentTransaction.replace(R.id.nav_host_fragment,new HomeFragment());
                        fragmentTransaction.commit();
                        item.setChecked(true);

                        break;
                    case R.id.nav_slideshow:
                        toolbar.setTitle(item.getTitle().toString());
                         drawer.closeDrawer(GravityCompat.START);
                        fragmentTransaction.replace(R.id.nav_host_fragment,new SlideshowFragment());
                        fragmentTransaction.commit();
                        item.setChecked(true);


                        break;
                    case R.id.nav_juegos:
                        toolbar.setTitle(item.getTitle().toString());
                        drawer.closeDrawer(GravityCompat.START);
                        fragmentTransaction.replace(R.id.nav_host_fragment,new JuegosFragment());
                        fragmentTransaction.commit();
                        item.setChecked(true);
                       //Toast.makeText(getBaseContext(),"vamos a jugar",Toast.LENGTH_SHORT).show();



                        break;
                }

                return false;
            }
        });
    }


   private void checkCurrentUser() {
        // [START check_current_user]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseAuth firebaseAuth;
        firebaseAuth = FirebaseAuth.getInstance();

          View root=new View(this);
        if (user != null) {
            String uid=user.getUid();
            user.getProviderId();
            firebaseAuth.getApp();
            loversf=true;
           // Toast.makeText(this," hay"+  firebaseAuth.getAccessToken(true),Toast.LENGTH_SHORT).show();

           consultaData(uid);

        } else {
            loversf=false;
            Toast.makeText(this,"no hay",Toast.LENGTH_SHORT).show();
        }
        }
        // [END check_current_user]


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        View view= new View(this);
        Fragment_product_buy product_buy= new Fragment_product_buy();
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cuerpo, menu);
        MenuItem item = menu.findItem(R.id.item_logout);
        item.setEnabled(loversf);
      //  setBadgeCount((LayerDrawable) item.getIcon(),"1"

        recyclerViewCar =(RecyclerView) view.findViewById(R.id.recycler_viewCarrito);

        return true;

    }
    public void consultaData(String uid){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
      ;
        String iud= user.getUid();
        mDatabase.child("users").child(iud).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String  nombres = snapshot.child("nombre").getValue().toString();
                    Toast.makeText(getBaseContext(),"bienvenido"+ nombres,Toast.LENGTH_SHORT).show();
                bienvenido.setText(nombres);
                    String uri= snapshot.child("imagen").getValue().toString();
                    Picasso.get().load( uri).into(cara);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    public void verNavs(){

        View n= findViewById(R.id.nav_host_fragmentC);
        n.setVisibility(View.INVISIBLE);
        View na= findViewById(R.id.nav_host_fragment);
        na.setVisibility(View.VISIBLE);
        View nau=findViewById(R.id.item_logout);
        nau.setVisibility(View.VISIBLE);

       // Toast.makeText(getBaseContext(), "es"+y, Toast.LENGTH_SHORT).show();

    }
    public void  ocultarTolbarmen(){
       // badgeDrawable = BadgeDrawable.create(this);
       // badgeDrawable.setNumber(5);
      //  BadgeUtils.attachBadgeDrawable(badgeDrawable,findViewById(R.id.toolbar),R.id.IDCarrito);


    }
    public   void setBadgeCount(LayerDrawable icon, String count){

        Circle circle=new Circle();
//Llamamos nuestro método setCount y enciamos el numero de notificaciones
        circle.setCount(count);
        /*Mutable no compartirá su estado con ningún otro sorteo.*/
        icon.mutate();
        /*como les mencione anteriormente creamos dos capas una para la notificación y otra para agregar el circulo rojo en esta línea llamamos la capa y agregamos el circulo*/
        icon.setDrawableByLayerId(R.id.ic_badge, circle);
    }
    Boolean  loversf;
    public boolean onOptionsItemSelected(MenuItem item ) {


        usuario2= getIntent().getStringExtra("names");
       int id= item.getItemId();
       if(id==R.id.item_logout){
        FirebaseAuth.getInstance().signOut();
         Intent intent = new Intent(this, MainActivitySecion.class);
         //intent.putExtra(cuerpo.nombres, user1.getName());
           startActivity(intent);
           loversf=true  ;

       finish();
          // Random aleatorio = new Random(System.currentTimeMillis());
         //  i/nt intAletorio = aleatorio.nextInt(100);
         //  aleatorio.setSeed(System.currentTimeMillis());
        //   Toast.makeText(this,"inicie secion por favor"+intAletorio,Toast.LENGTH_SHORT).show();
       }else if(id==R.id.IDCarrito){
           if(loversf==false){

               Toast.makeText(this,"inicie secion por favor",Toast.LENGTH_SHORT).show();
            // MainActivitySecion mainActivity2= new MainActivitySecion();
            // mainActivity2.boton();
               Intent intent = new Intent(this, MainActivitySecion.class);
               // intent.putExtra(cuerpo.nombres, user1.getName());
               startActivity(intent);


           }else{
               View n= findViewById(R.id.nav_host_fragmentC);
               n.setVisibility(View.VISIBLE);
               View na= findViewById(R.id.nav_host_fragment);
               na.setVisibility(View.INVISIBLE);


               FragmentManager fragmentManager=getSupportFragmentManager();
               FragmentTransaction transaction = fragmentManager.beginTransaction();
               transaction.setReorderingAllowed(true);

               // reemplazando fragmentos
               transaction.replace(R.id.nav_host_fragmentC, FragmentCarrito.class, null);

               // inicio de la transaction
               transaction.commit();

            //

           }


       }

        return super.onOptionsItemSelected(item);
    }
    public void   carrito(){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);

        // reemplazando fragmentos
        transaction.replace(R.id.escenario, registrar.class, null);

        // inicio de la transaction
        transaction.commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();

    }

    public   void  cambiar(){
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        BadgeDrawable badgeDrawable= BadgeDrawable.create(this.getBaseContext());
        badgeDrawable.setNumber(3);
       // BadgeUtils.attachBadgeDrawable(badgeDrawable,this.findViewById(R.id.toolbar),R.id.IDagregarCarro);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,  R.id.nav_slideshow,R.id.nav_secion)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private BroadcastReceiver networkStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = manager.getActiveNetworkInfo();
            onNetworkChange(ni);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onPause() {
        unregisterReceiver(networkStateReceiver);
        super.onPause();
    }

    private void onNetworkChange(NetworkInfo networkInfo) {
        View view=new View(this.getBaseContext());
        band=true;
        ProgressDialog loading= new ProgressDialog(cuerpo.this);
        if (networkInfo != null) {
            if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                //   Toast.makeText(getBaseContext(), "Necesaria conexión a internet ", Toast.LENGTH_SHORT).cancel();
               // loading = ProgressDialog.show(this,"Conectese a la red por favor...","Buscando red...",true,true);
             loading.setCancelable(band);
               loading.setCanceledOnTouchOutside(band);
               // Toast.makeText(getBaseContext(), "de nuevo en red ", Toast.LENGTH_SHORT).show();

            } else {
                // final ProgressDialog loading = ProgressDialog.show(this,"Conectese a la red por favor...","Espere por favor...",false,true);


            }
        }else {

            loading = ProgressDialog.show(this,"Conectese a la red por favor...","Buscando red...",true,band);

            //Toast.makeText(getBaseContext(), "Necesaria conexión a internet ", Toast.LENGTH_SHORT).show();

        }

    }







}
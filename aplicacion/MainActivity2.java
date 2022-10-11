package com.example.aplicacion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.aplicacion.baseDatos.Utilidades;
import com.example.aplicacion.baseDatos.layawerDBHelper;
import com.example.aplicacion.carrito.carritoCop;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private final int TIEMPO = 5000;
    //ProgressDialog loading=new ProgressDialog(MainActivity2.this);
    private  RecyclerView.LayoutManager layoutManager;
    Handler handler = new Handler();
    ArrayList<String> miArreglo ;
   // adapterProductos adapterProducto;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


         ProgressDialog loading=new ProgressDialog(MainActivity2.this);
        setContentView(R.layout.activity_main2);
            if (!compruebaConexion(this)) {

                // Toast.makeText(getBaseContext(),"Necesaria conexión a internet ", Toast.LENGTH_SHORT).show();
           // loading = ProgressDialog.show(this,"Conectese a la red por favor...","Espere por favor...",true,true);
            }else {
                //   Toast.makeText(getBaseContext()," De nuevo en red ", Toast.LENGTH_SHORT).show();
                boton();
                loading.dismiss();
            }
        //Getting the instance of Spinner and applying OnItemSelectedListener on it

   //   boton();

       //pasar() ;
    }



    public void boton(){

        int contUniv=0;
        layawerDBHelper conn= new layawerDBHelper(this,"db_usuario",null,2);
       SQLiteDatabase db=conn.getWritableDatabase();
         ContentValues values= new ContentValues();
        // db.execSQL("CREATE TABLE cont(cantidad INTEGER+)");
        //db.execSQL("DELETE FROM productos WHERE id='0'");


         contUniv=contUniv+1;
       if(contUniv>=1){
           values.put(Utilidades.CAMPO_ID,"0");
           values.put(Utilidades.CAMPO_NOMBRE,"");
           values.put(Utilidades.CAMPO_VALOR,"");
           values.put(Utilidades.CAMPO_CANT,"");
           values.put("cantidad",contUniv);

           long idres= db.insert(Utilidades.TABLA_PRODCAR,Utilidades.CAMPO_ID,values);
       // long cont= db.insert("cont",Utilidades.CAMPO_ID,values);
           db.execSQL("DELETE FROM productos WHERE id='0'");
             db.close();
            Toast.makeText(getBaseContext(), "creado "+idres, Toast.LENGTH_SHORT).show();
      }

       // Toast.makeText(getBaseContext(), "elmcontador es"+contUniv, Toast.LENGTH_SHORT).show();
       //   FragmentManager fm = getSupportFragmentManager();
      //  if(db!=null){
       //     Toast.makeText(this, "creada", Toast.LENGTH_SHORT).show();

       ///   }else{


          //    }
    //    values.put(Utilidades.CAMPO_ID,"");
       //  values.put(Utilidades.CAMPO_NOMBRE,"");
        // values.put(Utilidades.CAMPO_VALOR,"");
        // values.put(Utilidades.CAMPO_CANT,"");

       //  long idres= db.insert(Utilidades.TABLA_PRODCAR,Utilidades.CAMPO_ID,values);
        // db.close();
     /// // FragmentTransaction ft = fm.beginTransaction();

      ///  secion1Fragment llf = new secion1Fragment(); ft.add(R.id.escenario, llf); ft.commit();
        //secion1Fragment llf = new secion1Fragment(); ft.add(R.id.escenario, llf); ft.commit();

       Intent intent = new Intent(this, cuerpo.class);
        // intent.putExtra(cuerpo.nombres, user1.getName());
        startActivity(intent);

    }
    public void pasar(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        //  FragmentProductosVer  llf = new  FragmentProductosVer(); ft.add(R.id.escenario, llf); ft.commit();
        secion1Fragment llf = new secion1Fragment(); ft.add(R.id.escenario, llf); ft.commit();
    }
    public static boolean compruebaConexion(Context context) {

        boolean connected = false;

        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Recupera todas las redes (tanto móviles como wifi)
        NetworkInfo[] redes = connec.getAllNetworkInfo();

        for (int i = 0; i < redes.length; i++) {
            // Si alguna red tiene conexión, se devuelve true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                connected = true;

            }

        }
        return connected;

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
        ProgressDialog loading= new ProgressDialog(MainActivity2.this);
        if (networkInfo != null) {
            if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
             //   Toast.makeText(getBaseContext(), "Necesaria conexión a internet ", Toast.LENGTH_SHORT).cancel();
                loading.dismiss();

                Toast.makeText(getBaseContext(), "de nuevo en red ", Toast.LENGTH_SHORT).show();
            } else {
                // final ProgressDialog loading = ProgressDialog.show(this,"Conectese a la red por favor...","Espere por favor...",false,true);


            }
        }else {

            loading = ProgressDialog.show(this,"Conectese a la red por favor...","Buscando red...",true,true);

            //Toast.makeText(getBaseContext(), "Necesaria conexión a internet ", Toast.LENGTH_SHORT).show();

        }

    }
    public void conexion(){
        ProgressDialog loading= new ProgressDialog(MainActivity2.this);
        if (!compruebaConexion(this)) {
            // Toast.makeText(getBaseContext(),"Necesaria conexión a internet ", Toast.LENGTH_SHORT).show();

             loading = ProgressDialog.show(this,"Conectese a la red por favor...","Buscando red...",true,true);

        }else {

            loading.dismiss();
            //   Toast.makeText(getBaseContext()," De nuevo en red ", Toast.LENGTH_SHORT).show();


        }
    }
        public void ejecutarTarea () {

            handler.postDelayed(new Runnable() {
                public void run() {
                  //  Toast.makeText(getBaseContext()," hla", Toast.LENGTH_SHORT).show();
                   //conexion();

                    handler.postDelayed(this, TIEMPO);
                }

            }, TIEMPO);

        }

}
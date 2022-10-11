package com.example.aplicacion;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.content.Context.INPUT_SERVICE;
import static android.util.Patterns.*;
import static androidx.core.content.ContextCompat.getSystemService;
import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.util.PatternsCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.aplicacion.databinding.FragmentFirstBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link registrar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class registrar extends Fragment {
    String Username;
     String Email;
    AwesomeValidation awesomeValidation;
     FirebaseAuth  firebaseAuth;
Button botonr;
    boolean b;
   FirebaseDatabase firebase=FirebaseDatabase.getInstance();
  DatabaseReference mDatabase;
    DatabaseReference ref = firebase.getReference("server/saving-data/fireblog/posts");
    FirebaseFirestore db = FirebaseFirestore.getInstance();
TextView nombre, contraseña, usuarioR, celular,user_nDis;
usuario usuarios= new usuario();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public registrar() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment otro.
     */
    // TODO: Rename and change types and number of parameters
    public static registrar newInstance(String param1, String param2) {
        registrar fragment = new registrar();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            inicioFireBase();
        }

      firebaseAuth= FirebaseAuth.getInstance();
        awesomeValidation= new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation((Activity) getContext(),R.id.txt_contraseñaReg2,".6,}",R.string.invalid_user);

    }
    private void inicioFireBase(){
        FirebaseApp.initializeApp(getContext());
        firebase= firebase.getInstance();
        mDatabase=firebase.getReference()
                ;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =inflater.inflate(R.layout.fragment_otro, container, false);
        nombre= vista.findViewById(R.id.txt_nombreReg2);
       usuarioR= vista.findViewById(R.id.txt_usuarioReg2);
      botonr= vista.findViewById(R.id.button2);
        contraseña= vista.findViewById(R.id.txt_contraseñaReg2);
        celular= vista.findViewById(R.id.txtCel);
       botonr.setEnabled(false);
       user_nDis=(TextView)vista.findViewById(R.id.  LblToastCustom);

       Drawable c= usuarioR.getBackground();
        usuarioR.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @SuppressLint("NewApi")
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    Drawable c= usuarioR.getBackground();
                    c= DrawableCompat.wrap(c);
                    DrawableCompat.setTint(c, ContextCompat.getColor(getContext(),R.color.cardview_dark_background));
                   consultaUser();
                    Toast.makeText(getContext(), "si se pudo", Toast.LENGTH_SHORT).show();
                    awesomeValidation.addValidation((Activity) getContext(),R.id.txt_usuarioReg2, Patterns.EMAIL_ADDRESS,R.string.invalid_user);

                }else{
                    Drawable c= usuarioR.getBackground();
                    c= DrawableCompat.wrap(c);
                    DrawableCompat.setTint(c, ContextCompat.getColor(getContext(),R.color.purple_500));
                    botonr.setEnabled(true);


                }
            }
        });



      botonr.setOnClickListener(new View.OnClickListener() {

          @Override
          public void onClick(View view) {
              String name,email;
            //ejecutarServicio();

              insertarAuth();
           //

          }


      });
        return vista;
    }


    public  void errore(String error ){

        switch (error){
            case "ERROR_EMAIL_ALREADY_IN_USE":
                Toast.makeText(getContext(), "USUARIO NO DISPONIBLE", Toast.LENGTH_SHORT).show();
            break;
            case "ERROR_WEAK_PASSWORD":
                Toast.makeText(getContext(), "CONTRASEÑA INSEGURA", Toast.LENGTH_SHORT).show();
                  break;
        }
    }
    public void insertarAuth2(){


        firebaseAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                   // Log.d(TAG, "signInAnonymously:success");
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    Toast.makeText(getContext(), "Authentication."+user,
                            Toast.LENGTH_SHORT).show();

                } else {
                    // If sign in fails, display a message to the user.
                   // Log.w(TAG, "signInAnonymously:failure", task.getException());
                    Toast.makeText(getContext(), "Authentication failed.",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    public void insertarAuth(){

        String mail=usuarioR.getText().toString();
        String pass=contraseña.getText().toString();

        if(awesomeValidation.validate()){
            firebaseAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        String uid= firebaseAuth.getUid();
                        Toast.makeText(getContext(), "usuario creado", Toast.LENGTH_SHORT).show();

                        // mDatabase.child("users").child("mail").child("nombre").setValue(usuarioR.getText().toString());
                         registroFirebase(uid);
                    }else{
                        String error=((FirebaseAuthException) task.getException()).getErrorCode();
                        //     Toast.makeText(getContext(), "no"+error, Toast.LENGTH_SHORT).show();

                        errore(error);

                    }
                }
            });

        }else{
            Toast.makeText(getContext(), "verifique datods", Toast.LENGTH_SHORT).show();
        }
    }
boolean band;
    public void consultaUser(){
        View v = new View(this.getContext());
        String useraqui="id"+":"+usuarioR.getText().toString();
        mDatabase.child("users").orderByValue().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String name = snapshot.getKey();

                 for (int i=0;i<=0;i++) {


                if(useraqui.equalsIgnoreCase(name)) {

                    LayoutInflater Inflater = getLayoutInflater();
                    View Layout = Inflater.inflate(R.layout.notif_incorrecto, (ViewGroup) v.findViewById(R.id.ToastCustom2));
                    Toast toast = new Toast(getContext());
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(Layout);
                    toast.show();


                  //  Toast.makeText(getContext(), "usuario exixtente", Toast.LENGTH_SHORT).show();
                    band = false;
                    botonr.setEnabled(false);
                    break;
                }
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


    public void registroFirebase(String uid){

        mDatabase.child("users").child(uid).child("nombre").setValue(nombre.getText().toString());
        mDatabase.child("users").child(uid).child("celular").setValue(celular.getText().toString());
        mDatabase.child("users").child(uid).child("imagen").setValue(255);
    }
    public void ejecutarServicio() {
        // String url= "http://192.168.0.110:/login/prueba.php";
        // String usurio="111";
        String url = "http://192.168.0.110:/login/prueba.php";   //+'user'=+usurio+'pwd=' +usurio;


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), "Operacion exitosa", Toast.LENGTH_SHORT).show();
                //try {
                //JSONArray json = response.optJSONArray("datos");
                //json = new JSONArray(response);
                //JSONObject c = ja.getJSONObject();
                //} catch (JSONException e) {
                //  e.printStackTrace();
                //}
                JSONObject jsonObject = null;
                Intent intent = new Intent(getContext(), cuerpo.class);
                intent.putExtra(cuerpo.nombres, usuarios.getName());
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                //String namme = "popor";
              //  String user= "popor";
                String napwd = "555";

                parametros.put("name", nombre.getText().toString());
               parametros.put("user", usuarioR.getText().toString());
                parametros.put("pwd", contraseña.getText().toString());

                return parametros;

            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }



}
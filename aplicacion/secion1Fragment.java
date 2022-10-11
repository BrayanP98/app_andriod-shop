package com.example.aplicacion;


import android.annotation.SuppressLint;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.aplicacion.baseDatos.Utilidades;
import com.example.aplicacion.baseDatos.layawerDBHelper;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.auth.AuthResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link secion1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class  secion1Fragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener, GoogleApiClient.OnConnectionFailedListener {
    StorageReference storageReference;
    FirebaseDatabase firebase = FirebaseDatabase.getInstance();
    FirebaseAuth.AuthStateListener firebaseAuthListener;
    DatabaseReference mDatabase;
    DatabaseReference ref = firebase.getReference("server/saving-data/fireblog/posts");
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button boton, btn_googleReg;
    EditText cajauser, cajapwd;
    ImageView visiblePass;
    TextView crear_cuenta, recup_pass;
    RequestQueue rq;
    JsonRequest jrq;
    JSONArray ja;
    View v;

    AwesomeValidation awesomeValidation;
    FirebaseAuth firebaseAuth;
    CardView cardView;
    usuario user1 = new usuario();
    boolean dark;
    //private static final String TAG = "AnonymousAuth";
    private FirebaseAuth mAuth;
    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;
    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
    private boolean showOneTapUI = true;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private static final int SIGN_IN_CODE = 777;
    GoogleSignInClient mGoogleSignInClient;


    cuerpo cuerpo1;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int requestCode;
    private int resultCode;
    private Intent data;

    public secion1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment secion1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static secion1Fragment newInstance(String param1, String param2) {
        secion1Fragment fragment = new secion1Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onStart() {
        String uid = "some-uid";
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
         FirebaseUser currentUser = mAuth.getCurrentUser();
         updateUI(currentUser);
        super.onStart();
    }




    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this.getContext())
                .enableAutoManage((FragmentActivity) this.getContext(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();



        mGoogleSignInClient = GoogleSignIn.getClient(this.getContext(), gso);
        // [END config_signin]

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    //Intent intent = new Intent(getContext(), cuerpo.class);
                  //  intent.putExtra("perfect",true);
                   // startActivity(intent);
                }
            }
        };

        inicioFireBase();

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation((Activity) getContext(), R.id.txt_contraseñaReg2, "{.6,}", R.string.invalid_user);
        awesomeValidation.addValidation((Activity) getContext(), R.id.txt_usuarioReg2, Patterns.EMAIL_ADDRESS, R.string.invalid_user);
    }

    private void inicioFireBase() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        FirebaseApp.initializeApp(getContext());
        firebase = firebase.getInstance();
        mDatabase = firebase.getReference();
    }

    @SuppressLint("NewApi")
    public void validation() {
        firebaseAuth = FirebaseAuth.getInstance();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation((Activity) getContext(), R.id.txt_contraseñaReg2, "{.6,}", R.string.invalid_user);
        awesomeValidation.addValidation((Activity) getContext(), R.id.txt_usuarioReg2, Patterns.EMAIL_ADDRESS, R.string.invalid_user);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_secion1, container, false);
        visiblePass = vista.findViewById(R.id.ImgVisbPass);
        cajauser = vista.findViewById(R.id.txtUser);
        cajapwd = vista.findViewById(R.id.txtPwd);
        boton = vista.findViewById(R.id.button_ses);
        btn_googleReg = vista.findViewById(R.id.button_Google);
        //   boton.setVisibility(vista.VISIBLE);
        boton.setEnabled(false);
        recup_pass = vista.findViewById(R.id.txt_RecupPass);
        crear_cuenta = vista.findViewById(R.id.txt_crearCu);
        rq = Volley.newRequestQueue(getContext());
        btn_googleReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
             signIn();



            }
        });
        recup_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (awesomeValidation.validate()) {
                    alertDialogBuilder.setTitle("Confirmar!");

                    // Configura el mensaje.
                    alertDialogBuilder
                            .setMessage(" Deseas enviar correo de recuperacion?")
                            .setCancelable(false)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    reg_anonimus();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }

                            }).create().show();
                } else {

                }

            }

        });
        cajapwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                validation();
                if (!b) {
                    Drawable c = cajapwd.getBackground();
                    c = DrawableCompat.wrap(c);
                    DrawableCompat.setTint(c, ContextCompat.getColor(getContext(), R.color.red));
                } else {
                    Drawable c = cajapwd.getBackground();
                    c = DrawableCompat.wrap(c);
                    DrawableCompat.setTint(c, ContextCompat.getColor(getContext(), R.color.purple_500));
                }

            }
        });
        visiblePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Drawable drawable = cajapwd.getCompoundDrawables()[2];
                ;
                if (drawable.getConstantState().equals(getResources().getDrawable(R.drawable.ic_baseline_visibility_24).getConstantState())) {
                    cajapwd.setCompoundDrawablesWithIntrinsicBounds(null,
                            null, getResources().getDrawable(R.drawable.ic_baseline_visibility_off_24), null);

                    cajapwd.setTransformationMethod(PasswordTransformationMethod.getInstance());

                } else {
                    cajapwd.setCompoundDrawablesWithIntrinsicBounds(null,
                            null, getResources().getDrawable(R.drawable.ic_baseline_visibility_24), null);

                    cajapwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }

            }


            // }


        });


        cajapwd.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                Toast.makeText(getContext(), "Remplazar por tu codigo", Toast.LENGTH_LONG);

                if (cajapwd.getText().toString().length() > 1) {
                    cajapwd.setCompoundDrawablesWithIntrinsicBounds(null,
                            null, getResources().getDrawable(R.drawable.ic_baseline_visibility_off_24), null);
                    boton.setEnabled(true);
                } else {
                    boton.setEnabled(false);
                }
                return false;
            }
        });
        crear_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                //  reg_anonimus();
                //pasar();


                //  mDatabase.child("users").child("mail").setValue("ddddd");
                //  mDatabase.child("users").child("perafanbrayan1998").child("nombre").setValue("pedro");
                // mDatabase.child("users").child("id"+":"+usuarioR.getText().toString()).child("contraseña").setValue(contraseña.getText().toString());
                //   mDatabase.child("users").child("id"+":"+usuarioR.getText().toString()).child("celular").setValue(celular.getText().toString());


            }
        });
        cuerpo1 = new cuerpo();
        boton.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                cajapwd.setTextColor(R.color.purple_500);
                dark = true;
                // registrarProd();

                //  ((cuerpo) getActivity()).dayNight(1);

                // layawerDBHelper conn= new layawerDBHelper(getActivity().getBaseContext(),"db_usuario",null,2);
                //  SQLiteDatabase db=conn.getWritableDatabase();
                ///  ContentValues values= new ContentValues();



              VerificarEmail();

                //currentUser.getDisplayName();

                //if(db!=null){


                //  }

                //  values.put(Utilidades.CAMPO_ID,"");
                // values.put(Utilidades.CAMPO_NOMBRE,"");
                // values.put(Utilidades.CAMPO_VALOR,"");
                // values.put(Utilidades.CAMPO_CANT,"");

                // long idres= db.insert(Utilidades.TABLA_PRODCAR,Utilidades.CAMPO_ID,values);
                // db.close();
                //   consulta();
                //consulta();
                // Toast.makeText(getContext(), "eles"+idres, Toast.LENGTH_SHORT).show();
                // db.close();
                // Toast.makeText(getContext(), "SIN REGISTROS PARA ACTUALIZAR", Toast.LENGTH_SHORT).show();
                //  final ProgressDialog loading = ProgressDialog.show(getContext(),"Verificando...","Espere por favor...",false,true);
                //inicioFirebase();

                //   iniciarsecion();
                //    ejecutarServicio();
            }
        });


        return vista;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       GoogleSignInResult result= Auth.GoogleSignInApi.getSignInResultFromIntent(data);
     Status nn= result.getStatus();
        Toast.makeText(getContext(), "bienvenido"+nn, Toast.LENGTH_SHORT).show();

        if (result.isSuccess()){
            ;
            firebaseAuthWithGoogle(result.getSignInAccount());
        }
        GoogleSignInAccount account = null;
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);


        try {
            account = task.getResult(ApiException.class);


        } catch (ApiException e) {

            e.printStackTrace();
        }


    }



    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    AuthCredential credential;
    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser currentUser = mAuth.getCurrentUser ( ) ;
                         String uid= currentUser.getUid();
                    //Toast.makeText(getContext(), "bienvenido"+firebaseAuth.getCurrentUser().getDisplayName(), Toast.LENGTH_SHORT).show();
                    mDatabase.child("users").child(uid).child("nombre").setValue(currentUser.getDisplayName());
                    mDatabase.child("users").child(uid).child("celular").setValue(currentUser.getPhoneNumber());
                    mDatabase.child("users").child(uid).child("imagen").setValue(currentUser.getPhotoUrl().toString());
                    inicioFirebase(uid);
                }
            }
        });

    }



    private void updateUI(FirebaseUser user) {

        FirebaseUser currentUser = mAuth.getCurrentUser ( ) ;

        if ( currentUser!= null ) {
           String name= currentUser.getDisplayName().toString();

         // Intent intent = new Intent(getContext(), cuerpo.class);
          // intent.putExtra("perfect",true);
          //  intent.putExtra(cuerpo.nombres, name);
         // startActivity(intent);

        }

        }
     public void VerificarEmail(){
        //verificacion e-mail registrado en firebase

         String mail=cajauser.getText().toString();
         String pass=cajapwd.getText().toString();

         if(awesomeValidation.validate()){
             firebaseAuth.signInWithEmailAndPassword(mail,pass). addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                     if(task.isSuccessful()){
                        String uid= firebaseAuth.getUid();
                        // Intent intent = new Intent(getContext(), cuerpo.class);
                       //  intent.putExtra("perfect",true);
                         // intent.putExtra(cuerpo.nombres,"ya");
                        // startActivity(intent);
                        // getActivity().finish();

                       inicioFirebase(uid);

                     }else{
                         String error=((FirebaseAuthException) task.getException()).getErrorCode();
                        // Toast.makeText(getContext(), "no"+error, Toast.LENGTH_SHORT).show();

                         errore(error);

                     }

                 }
             });


         }else{
             Toast.makeText(getContext(), "usverifique datods", Toast.LENGTH_SHORT).show();
         }

     }
    public  void errore(String error ){

        switch (error){

            case "ERROR_INVALID_EMAIL":
                Toast.makeText(getContext(), "USUARIO INVALIDO", Toast.LENGTH_SHORT).show();

                break;
            case "ERROR_EMAIL_ALREADY_IN_USE":
                Toast.makeText(getContext(), "USUARIO NO DISPONIBLE", Toast.LENGTH_SHORT).show();
                break;
            case "ERROR_WEAK_PASSWORD":
                Toast.makeText(getContext(), "CONTRASEÑA INSEGURA", Toast.LENGTH_SHORT).show();
                break;
            case "ERROR_WRONG_PASSWORD":
                Toast.makeText(getContext(), "CONTRASEÑA INCORRECTA", Toast.LENGTH_SHORT).show();
                break;
            case "ERROR_USER_NOT_FOUND":
                Toast.makeText(getContext(), "USUARIO NO ENCONTRADO", Toast.LENGTH_SHORT).show();

        }
    }
  public  void reg_anonimus(){
      String mail=cajauser.getText().toString();
        firebaseAuth.sendPasswordResetEmail(mail,null).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getContext(), "correo enviado", Toast.LENGTH_SHORT).show();
                }else{
                    String error=((FirebaseAuthException) task.getException()).getErrorCode();
                    // Toast.makeText(getContext(), "no"+error, Toast.LENGTH_SHORT).show();

                    errore(error);

                }
            }
        });
  }
    public void validate(View view) {
        String mandato="pollo";
        String mailError = null;
        if (mandato.equalsIgnoreCase(cajauser.getText().toString())){
            mailError = getString(R.string.mandatory);
        }
        //toggleTextInputLayoutError(textInputEmail, mailError);

        String passError = null;
        if (TextUtils.isEmpty(cajapwd.getText())) {
            passError = getString(R.string.mandatory);
        }
       // toggleTextInputLayoutError(textInputPassword, passError);

        view.clearFocus();
    }

    /**
     * Display/hides TextInputLayout error.
     *
     * @param msg the message, or null to hide
     */
    private static void toggleTextInputLayoutError(@NonNull TextInputLayout textInputLayout,
                                                   String msg) {
        textInputLayout.setError(msg);
        textInputLayout.setErrorEnabled(msg != null);
    }
    public void setPlaceholderTextColor (ColorStateList placeholderTextColor){

    }
    public void inicioFirebase(String uid) {
       //mDatabase.child("productos").child("cod"+":"+"04455").child("imagen").setValue(235);
       // mDatabase.child("productos").child("cod"+":"+"00321").child("imagen").setValue(123);
       // mDatabase.child("productos").child("cod"+":"+"1234").child("imagen").setValue(555);
        StorageReference folder= FirebaseStorage.getInstance().getReference().child("users");
       // mDatabase.child("users").child("id"+":"+cajauser.getText().toString())
        mDatabase.child("users").child(uid).orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
             if(snapshot.exists()){
                 String nombre= snapshot.child("nombre").getValue().toString();
                 String img= snapshot.child("imagen").getValue().toString();
               //  String contraseña=snapshot.child("contraseña").getValue().toString();
               //  if(cajapwd.getText().toString().equals(contraseña)){
                     //Toast.makeText(getContext(), "Bienvenido"+"  "+img, Toast.LENGTH_SHORT).show();

                 Intent intent = new Intent(getContext(), cuerpo.class);
                     intent.putExtra("perfect",true);
                    intent.putExtra(cuerpo.nombres, nombre);
                    intent.putExtra(cuerpo.imagen, img);
                    startActivity(intent);
               //  }else {

                 //  Toast.makeText(getContext(), "CONTRASEÑA INCORRECTA", Toast.LENGTH_SHORT).show();
              //   }



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
    public void pasar(){
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);

        // reemplazando fragmentos
        transaction.replace(R.id.escenario2, registrar.class, null);

        // inicio de la transaction
        transaction.commit();
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        String user11=""; String name1="" ;String PWD="";
        Toast.makeText(getContext(), "operacion exitosa", Toast.LENGTH_SHORT).show();
        JSONObject jsonObject = response;
        //Toast.makeText(getContext(), "Operacion  exitosa", Toast.LENGTH_SHORT).show();

        try {
            JSONArray jarray = jsonObject.getJSONArray("datos");
            if (jarray.length() <= 0) {
                Toast.makeText(getContext(), "SIN REGISTROS PARA ACTUALIZAR", Toast.LENGTH_SHORT).show();
            } else {
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject object1 = jarray.getJSONObject(i);
                    name1 = object1.getString("name");
                    user11 = object1.getString("user");
                    PWD= object1.getString("pwd");
                    user1.setName(object1.getString("name"));
                    user1.setUser(object1.getString("user"));
                    user1.setPwd(object1.getString("pwd"));
                    // String passw_e = object1.getString("password");
                    //String servidor_e = object1.getString("servidor");
                    //MCrypt mcrypt = new MCrypt();

                    try {
                        //String empresa = new String(mcrypt.decrypt(empresa_e));
                        // String sucursal = new String(mcrypt.decrypt(sucursal_e));
                        // String passw = new String(mcrypt.decrypt(passw_e));
                        //String servidor = new String(mcrypt.decrypt(servidor_e));
                        //  Toast toast2 = Toast.makeText(getApplicationContext(), "DATOS: " + empresa + " | " + sucursal + " | " + passw + " | " + servidor, Toast.LENGTH_SHORT);
                        //toast2.show();

                    } catch(Exception e){
                        e.printStackTrace();
                    }
                    // Toast.makeText(getContext(), "HOLA"+user11, Toast.LENGTH_SHORT).show();
                }          //Toast.makeText(getContext(), "HOLA"+user1.getUser(), Toast.LENGTH_SHORT).show();


            }
        } catch(JSONException e){
            e.printStackTrace();
        }//dialog.dismiss();

        if (user11.equalsIgnoreCase(cajauser.getText().toString())&&PWD.equalsIgnoreCase(cajapwd.getText().toString())){
            Toast.makeText(getContext(), "HOLA"+user1.getName(), Toast.LENGTH_SHORT).show();

             Intent intent = new Intent(getContext(), cuerpo.class);
  intent.putExtra(cuerpo.nombres, user1.getName());
             startActivity(intent);
        }
        else{
            Toast.makeText(getContext()," USUARIO Y/O CONTRASEÑA INCORRECTOS", Toast.LENGTH_SHORT).show();
        }

    }
    private void iniciarsecion() {
        String user111="user"; String pwd="222";
       // String urvl = "http://192.168.0.110:/login/secion.php?user="+user111+"&"+"pwd="+pwd;
        String urvl = "http://192.168.0.110:/login/secion.php?user="+cajauser.getText().toString()+"&"+"pwd="+cajapwd.getText().toString();
       // String urvl = "http://192.168.0.110:/login/prueba.php";
        jrq = new JsonObjectRequest(Request.Method.GET, urvl, null, this, this);
        rq.add(jrq);
    }

    public void setForceDarkAllowed (boolean allow){
        allow=true;
    }

    public void ejecutarServicio() {
        // String url= "http://192.168.0.110:/login/prueba.php";
        // String usurio="111";
        String url = "http://192.168.0.110:/login/secion.php";   //+'user'=+usurio+'pwd=' +usurio;


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
                String namme = "popor";
                parametros.put("name", namme);
                parametros.put("user", cajauser.getText().toString());
                parametros.put("pwd", cajapwd.getText().toString());

                return parametros;

            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public  void registrarProd(){

        layawerDBHelper conn= new layawerDBHelper(this.getContext(),"db_usuario",null,1);

        SQLiteDatabase db=conn.getWritableDatabase();
        //db.execSQL("DELETE FROM usuarios WHERE id='01'");
        ContentValues values= new ContentValues();
        values.put(Utilidades.CAMPO_ID,"001");
        values.put(Utilidades.CAMPO_NOMBRE,"brayan");
        values.put(Utilidades.CAMPO_VALOR,"150");

       //db.delete("usuarios","120",null);

        long idres= db.insert(Utilidades.TABLA_USUARIO,Utilidades.CAMPO_ID,values);
        Toast.makeText(getContext()," si es"+idres, Toast.LENGTH_SHORT).show();

        db.close();

    }

    public  void consulta(){
        layawerDBHelper conn= new layawerDBHelper(this.getContext(),"db_usuario",null,1);

        SQLiteDatabase db=conn.getReadableDatabase();
        String[] parametros={"24"};
        String[] campos={"nombre","valor"};
        try {
            Cursor cursor = db.rawQuery("select *  from " + Utilidades.TABLA_USUARIO,null);

         //   Cursor cursor = db.rawQuery("select " + Utilidades.CAMPO_NOMBRE+"," + Utilidades.CAMPO_VALOR+" from " + Utilidades.TABLA_USUARIO+" where " + Utilidades.CAMPO_ID+"  = '" + "100" +"'", null);
          /// db.delete("usuarios","245",null);
    //   Cursor cursor=db.query("usuarios",null,null,null,null,null,null);
           //ursor cursor=db.query("usuarios",campos,"id"+"=?",parametros,null,null,null);
            cursor.moveToNext();
            while (cursor.moveToNext()) {

           // for(int i=0;i <3;i++){
                Toast.makeText(getContext()," el"+cursor.getString(0), Toast.LENGTH_SHORT).show();

           // }
            }
          //  Toast.makeText(getContext()," el"+cursor.getString(2), Toast.LENGTH_SHORT).show();
          cursor.close();
          db.close();
        }catch (Exception e){
            Toast.makeText(getContext()," nose pudo", Toast.LENGTH_SHORT).show();


        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @Override
    public void onStop() {
        super.onStop();
        if (firebaseAuthListener != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }
}
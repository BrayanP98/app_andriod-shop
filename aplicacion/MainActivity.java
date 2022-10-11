package com.example.aplicacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button btnBuscar;
    private Button btnSubir;

    private ImageView imageView;

    private EditText editTextName;

    private Bitmap bitmap;

    private int PICK_IMAGE_REQUEST = 1;
    StorageReference storageReference;
    //  private String UPLOAD_URL ="http://192.168.0.110:/login/subirImg.php";
    FirebaseDatabase firebase = FirebaseDatabase.getInstance();
    DatabaseReference mDatabase;
    DatabaseReference ref = firebase.getReference("server/saving-data/fireblog/posts");
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String KEY_IMAGEN = "imagen";
    private String KEY_NOMBRE = "nombre";
    String nombre ;
    FirebaseStorage storage;
    StorageReference StorageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inicioFireBase();
        setContentView(R.layout.activity_main);
        btnBuscar = (Button) findViewById(R.id.btnBuscar);
        btnSubir = (Button) findViewById(R.id.btnSubir);

        editTextName = (EditText) findViewById(R.id.editText);
        storage = FirebaseStorage.getInstance();
        StorageReference = storage.getReference();
        imageView = (ImageView) findViewById(R.id.imageView);
        nombre =editTextName.getText().toString();
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });
        btnSubir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               // mDatabase.child("users").child("id"+":"+"Brayan1").child("foto").setPriority(map);
            }
        });

    }

    private void inicioFireBase() {
        FirebaseApp.initializeApp(this);
        firebase = firebase.getInstance();
        mDatabase = firebase.getReference()
        ;
    }

    public String getStringImagen(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        imageView.setImageBitmap(bmp);
        return encodedImage;
    }


    private void uploadImage() {
        Uri file = Uri.fromFile(new File("http://192.168.0.110:/login/uploads/goku.jpg"));
        StorageReference riversRef = storageReference.child("img_user/" + file.getLastPathSegment());
        // final StorageReference ref = storageReference.child("img_user/mountains.jpg"); // Referencia a donde queres subir el archivo
        UploadTask uploadTask = riversRef.putFile(file);
        //StorageReference folder= FirebaseStorage.getInstance().getReference().child("users");
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });
        String url = "http://192.168.0.110:/login/enviar2.php";
        //Mostrar el diálogo de progreso
        //  final ProgressDialog loading = ProgressDialog.show(this,"Subiendo...","Espere por favor...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Descartar el diálogo de progreso
                Toast.makeText(MainActivity.this, "Operacion exitosa", Toast.LENGTH_SHORT).show();

                // loading.dismiss();
                //Mostrando el mensaje de la respuesta
                Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Descartar el diálogo de progreso
                        //   loading.dismiss();
                        Toast.makeText(MainActivity.this, "Operacion no exitosa", Toast.LENGTH_SHORT).show();

                        //Showing toast
                        Toast.makeText(MainActivity.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Convertir bits a cadena
                Map<String, String> params = new HashMap<String, String>();
                String imagen = getStringImagen(bitmap);
                //  String imagen="10";
                //Obtener el nombre de la imagen
                String nombre = editTextName.getText().toString();
                //  String nombre ="pepe";
                //Creación de parámetros


                //Agregando de parámetros
                params.put("foto", getStringImagen(bitmap));
                params.put("nombre", nombre);

                //Parámetros de retorno
                return params;
            }
        };

        //Creación de una cola de solicitudes
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
        ;

    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Imagen"), PICK_IMAGE_REQUEST);
    }
    Uri map= Uri.parse("ff");
    String nombre1 ="";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        nombre1 =editTextName.getText().toString();
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            StorageReference Folder = FirebaseStorage.getInstance().getReference().child("img_user");
            final StorageReference file_name = Folder.child( nombre1+ filePath.getLastPathSegment());
            file_name.putFile(filePath).addOnSuccessListener(taskSnapshot -> file_name.getDownloadUrl().addOnSuccessListener(uri -> {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("link", String.valueOf(uri));
                Toast.makeText(MainActivity.this, "Operacion "+uri.toString(), Toast.LENGTH_SHORT).show();

              mDatabase.child("users").child("id"+":"+"Brayan1").child("foto").setValue(uri.toString());
               // mDatabase.child("users2").setValue(hashMap);
                //mDatabase.child("users").child("id"+":"+"1").child("apellido").setValue("pedro33");
            }));


            try {
            //Cómo obtener el mapa de bits de la Galería
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
            //Configuración del mapa de bits en ImageView
            imageView.setImageBitmap(bitmap);
             } catch (IOException e) {
                 e.printStackTrace();
             }
        }
    }


}

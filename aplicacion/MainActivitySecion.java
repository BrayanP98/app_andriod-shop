package com.example.aplicacion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

public class MainActivitySecion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_secion);
        boton();

    }
    public void boton(){

         FragmentManager fm = getSupportFragmentManager();
         FragmentTransaction ft = fm.beginTransaction();

       secion1Fragment llf = new secion1Fragment(); ft.add(R.id.escenario2, llf); ft.commit();
        //secion1Fragment llf = new secion1Fragment(); ft.add(R.id.escenario, llf); ft.commit();

        //ntent intent = new Intent(this, cuerpo.class);
        // intent.putExtra(cuerpo.nombres, user1.getName());
       // startActivity(intent);

    }

}
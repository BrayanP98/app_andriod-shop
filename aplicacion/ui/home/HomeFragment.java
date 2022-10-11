package com.example.aplicacion.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.aplicacion.FragmentCarrito;
import com.example.aplicacion.Fragment_product_buy;
import com.example.aplicacion.MainActivity2;
import com.example.aplicacion.R;
import com.example.aplicacion.cuerpo;
import com.example.aplicacion.ui.gallery.GalleryFragment;
import com.example.aplicacion.ui.slideshow.SlideshowViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment {
    ExtendedFloatingActionButton fab;
    Fragment_product_buy fragmentProductBuy= new Fragment_product_buy();
    private HomeViewModel homeViewModel;
    cuerpo cuerpo1;
        Fragment_product_buy buy;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       homeViewModel= new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        final TextView textView = root.findViewById(R.id.text_home);

         final Switch modo= root.findViewById(R.id.switModo);
         modo.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if(modo.isChecked()){

                   //  new cuerpo().dayNight(0);
                    //((cuerpo) getActivity()).dayNight(0);
                 }else{
                 //  new  cuerpo().dayNight(1);
                   //((cuerpo) getActivity()).dayNight(1);
                 }
             }
         });
       homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {

            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;

    }



}
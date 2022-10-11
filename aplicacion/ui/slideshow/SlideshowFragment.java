package com.example.aplicacion.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.aplicacion.FragmentCarrito;
import com.example.aplicacion.Fragment_product_buy;
import com.example.aplicacion.R;
import com.example.aplicacion.adapter.PagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    ViewPager viewPager;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
       // final TextView textView = root.findViewById(R.id.text_slideshow);
        TabLayout tabLayout = (TabLayout) root.findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Todos").setIcon(R.drawable.salida_36));
        tabLayout.addTab(tabLayout.newTab().setText("ASEO"));
        tabLayout.addTab(tabLayout.newTab().setText("COCINA"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        PagerAdapter adapter = new PagerAdapter(getActivity().getSupportFragmentManager(),
                tabLayout.getTabCount());

       viewPager =  root.findViewById(R.id.viewPager2);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




        return root;
    }
}
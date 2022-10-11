package com.example.aplicacion.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.ListFragment;

import com.example.aplicacion.Aseoragment;
import com.example.aplicacion.FragmentCarrito;
import com.example.aplicacion.FragmentProductosVer;
import com.example.aplicacion.RuletaFragment;
import com.example.aplicacion.crearUsuarioFragment;
import com.example.aplicacion.ui.gallery.GalleryFragment;
import com.example.aplicacion.ui.home.HomeFragment;
import com.example.aplicacion.ui.slideshow.SlideshowFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private int numberOfTabs;


    public PagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return  new GalleryFragment();
            case 1:
                return  new RuletaFragment();
            case 2:
                return  new HomeFragment();
            default:
                return null;
        }

    }
}

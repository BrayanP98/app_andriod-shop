package com.example.aplicacion;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import android.content.Context;

import android.widget.Spinner;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link crearUsuarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class crearUsuarioFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener{
    Button botonREG;
    EditText cajauserREG, cajapwdREG,celularREG,nombreREG,contraseña2;
    private Context cont=getContext();
    private Spinner spinnert;
    private ArrayList<String> lista = new ArrayList<String>();
    String[] country = { "India", "USA", "China", "Japan", "Other"};
    Spinner  spiner;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public crearUsuarioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment crearUsuarioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static crearUsuarioFragment newInstance(String param1, String param2) {
        crearUsuarioFragment fragment = new crearUsuarioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crear_usuario, container, false);

    }


    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

    }


}
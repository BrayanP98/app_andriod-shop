package com.example.aplicacion;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicacion.ui.home.HomeFragment;
import com.example.aplicacion.ui.slideshow.SlideshowFragment;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RuletaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RuletaFragment extends Fragment {
ImageView ruleta;
Button mover_rul;
Random r;
private final int TIEMPO1 = 4300;
TextView num_rul, txt_prediccon,txt_felicidades,color;
int degree=0, degree_old=0;
MediaPlayer mp,mp1;
Animation animation1;
ConstraintLayout constraintLayout;
Handler handler = new Handler();
public static String colors="negro";
int cont1=0,contClick=0;

public ArrayList<Integer> miArreglo = new ArrayList<Integer>();


private  static  final  float FACTOR=4.86f;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RuletaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RuletaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RuletaFragment newInstance(String param1, String param2) {
        RuletaFragment fragment = new RuletaFragment();
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
        View view =inflater.inflate(R.layout.fragment_ruleta, container, false);

        ruleta=(ImageView) view.findViewById(R.id.imageRuleta);
        mover_rul=(Button) view.findViewById(R.id.btn_girarRt);
        num_rul=(TextView) view.findViewById(R.id.text_rulNum);
        txt_prediccon=(TextView) view.findViewById(R.id.txt_prediccion);
        constraintLayout=(ConstraintLayout)view.findViewById(R.id.containerRul);
        txt_felicidades=(TextView) view.findViewById(R.id.text_felizRul);
        txt_felicidades.setVisibility(View.INVISIBLE);
        color=view.findViewById(R.id.txt_color);


        ////////

        Calendar cal = Calendar.getInstance();
        Calendar dt = Calendar.getInstance();
        dt.clear();
        int hora= (cal.get(cal.HOUR));
        int minutos= (cal.get(cal.MINUTE));
        String dia= String.valueOf(cal.get(cal.DATE));
        String mes= String.valueOf(cal.get(cal.MONTH));
        String año= String.valueOf(cal.get(cal.YEAR));
       dt.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),cal.get(Calendar.DATE));
        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);
        Toast.makeText(getContext(), "es"+dia+"/"+mes+"/"+año+"__"+"a las"+hora+":"+minutos, Toast.LENGTH_SHORT).show();

       // return dt.getTime();
        r= new Random();
        txt_prediccon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint({"ResourceAsColor", "NewApi"})
            @Override
            public void afterTextChanged(Editable editable) {
                if(txt_prediccon.getText().length()==0){
                    Resources resources = getContext().getResources();
                    Drawable HippoDrawable = resources.getDrawable(R.drawable.color_white);
                    color.setBackground(HippoDrawable);
                }else {
                    verificarColor();
                }
               //

            }
        });
        mover_rul.setOnClickListener(new View.OnClickListener() {


            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {


                if(txt_prediccon.getText().length()==0){
                    Toast.makeText(getContext(), "Ingrese Su Prediccion  \n"+"  "+"  "+" por favor", Toast.LENGTH_SHORT).show();

                }else{
                    contClick = contClick + 1;
                    txt_prediccon.setEnabled(false);
                    if (contClick <= 6 ) {
                        String pred = txt_prediccon.getText().toString()+" "+colors;
                     //   Toast.makeText(getContext(), "Ingrese Su Prediccion  \n"+pred, Toast.LENGTH_SHORT).show();


                        cont1 = 0;
                        degree_old = degree % 360;
                        degree = r.nextInt(3600) + 720;
                        RotateAnimation rotate = new RotateAnimation(degree_old, degree, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                        rotate.setDuration(3600);
                        rotate.setFillAfter(true);
                        rotate.setInterpolator(new DecelerateInterpolator());
                        rotate.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                mp = MediaPlayer.create(getContext(), R.raw.girar_rul);
                                mp.start();
                            }

                            @SuppressLint("NewApi")
                            @Override
                            public void onAnimationEnd(Animation animation) {
                                num_rul.setText(curretNumber(360 - (degree % 360)));
                                String numRul = curretNumber(360 - (degree % 360));
                                String pred = txt_prediccon.getText().toString()+""+colors;

                                if (numRul.equalsIgnoreCase(pred)) {
                                    mp.stop();
                                    mp1 = MediaPlayer.create(getContext(), R.raw.ganartono);
                                    mp1.start();
                                    Toast.makeText(getContext(), "GANASTE", Toast.LENGTH_SHORT).show();
                                    Resources resources = getContext().getResources();
                                    Drawable HippoDrawable = resources.getDrawable(R.drawable.color_rul_suss);
                                    txt_prediccon.setBackground(HippoDrawable);
                                    txt_felicidades.setVisibility(View.VISIBLE);
                                    txt_felicidades.setTextColor(R.color.red);
                                    animation1 = AnimationUtils.loadAnimation(getContext(), R.anim.zoom);
                                    animation1.setDuration(2000);
                                    animation1.setInterpolator(new DecelerateInterpolator());
                                    txt_felicidades.startAnimation(animation1);
                                    //////////////////////////////////
                                    handler.postDelayed(new Runnable() {

                                        public void run() {
                                            cont1 = cont1 + 1;
                                            bucle();
                                            handler.postDelayed(this, TIEMPO1);
                                            Toast.makeText(getContext(), "cont es" + cont1, Toast.LENGTH_SHORT).show();

                                            if (cont1 == 3) {
                                                animation1.cancel();
                                                handler.removeCallbacksAndMessages(null);
                                                cont1 = 0;
                                            }
                                        }

                                    }, TIEMPO1);
                                } else {
                                    mp.stop();

                                    // Toast.makeText(getContext(), "cont es" + cont1, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getContext(), "Intenta de nuevo \n "+"  por favor", Toast.LENGTH_SHORT).show();


                                    //   Toast.makeText(getContext(),"Sigue Intentando"+numRul,Toast.LENGTH_SHORT).show();
                                    //bucle();

                                }

                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        ruleta.startAnimation(rotate);
                    }else{

                        mover_rul.setEnabled(false);

                    }
                }

            }
        });
        return view;
    }

  @SuppressLint({"NewApi", "ResourceAsColor"})
  public void verificarColor(){
      int[] arreglo; arreglo = new int[19];

    int colorUser= Integer.parseInt( txt_prediccon.getText().toString());
     // int[] rojos = new int {32,19,21,25,34,27,36,30,23,5,16,1,14,9,18,7,12,3};
      arreglo[0]=32;
      arreglo[1]=19;
      arreglo[2]=21;
      arreglo[3]=25;
      arreglo[4]=34;
      arreglo[5]=27;
      arreglo[6]=36;
      arreglo[7]=30;
      arreglo[8]=23;
      arreglo[9]=5;
      arreglo[10]=16;
      arreglo[11]=1;
      arreglo[12]=14;
      arreglo[13]=9;
      arreglo[14]=18;
      arreglo[15]=7;
      arreglo[16]=12;
      arreglo[17]=3;
      Resources resources = getContext().getResources();
      for(int pivote : arreglo)
      {
     if(pivote==colorUser){
         Drawable HippoDrawable = resources.getDrawable(R.drawable.color_red);
         color.setBackground(HippoDrawable);
         colors="Rojo";
         if(colorUser==0) {
             Drawable HippoDrawable1 = resources.getDrawable(R.drawable.color_rul_suss);
             color.setBackground(HippoDrawable1);
         }

     break;
     }else{
         colors="Negro";
         color.setBackgroundColor(R.color.black);
     }


      }

  }
    @SuppressLint("NewApi")
    public void bucle(){

        int cont=0;
        cont =cont+1;

        mp1  = MediaPlayer.create(getContext(), R.raw.ganartono);
        mp1.start();
        txt_felicidades.setVisibility(View.VISIBLE);
        animation1 = AnimationUtils.loadAnimation(getContext(), R.anim.zoom);
        animation1.setDuration(2000);
        animation1.setInterpolator(new DecelerateInterpolator());
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                txt_felicidades.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        txt_felicidades.startAnimation(animation1);

    }

    @SuppressLint("ResourceAsColor")
    public  String curretNumber(int degrees){
        String pred= txt_prediccon.getText().toString();
        //Toast.makeText(getContext(),"GANASTE" +pred,Toast.LENGTH_SHORT).show();
       // num_rul.setHintTextColor(R.color.design_default_color_error);
        String text="";
        if(degrees>=(FACTOR*1)&&degrees<(FACTOR*3)){
            text="32 Rojo";

        }
        if(degrees>=(FACTOR*3)&&degrees<(FACTOR*5)){
            text="15 Negro";

        }
        if(degrees>=(FACTOR*5)&&degrees<(FACTOR*7)){
            text="19 Rojo";

        }
        if(degrees>=(FACTOR*7)&&degrees<(FACTOR*9)){
            text="4 Negro";

        }
        if(degrees>=(FACTOR*9)&&degrees<(FACTOR*11)){
            text="21 Rojo";


        }
        if(degrees>=(FACTOR*11)&&degrees<(FACTOR*13)){
            text="2 Negro";

        }
        if(degrees>=(FACTOR*13)&&degrees<(FACTOR*15)){
            text="25 Rojo";


        }
        if(degrees>=(FACTOR*15)&&degrees<(FACTOR*17)){
            text="17 Negro";

        }
        if(degrees>=(FACTOR*17)&&degrees<(FACTOR*19)){
            text="34 Rojo";


        }
        if(degrees>=(FACTOR*19)&&degrees<(FACTOR*21)){
            text="6 Negro";

        }
        if(degrees>=(FACTOR*21)&&degrees<(FACTOR*23)){
            text="27 Rojo";

        }
        if(degrees>=(FACTOR*23)&&degrees<(FACTOR*25)){
            text="13 Negro";

        }
        if(degrees>=(FACTOR*25)&&degrees<(FACTOR*27)){
            text="36 Rojo";

        }
        if(degrees>=(FACTOR*27)&&degrees<(FACTOR*29)){
            text="11 Negro";

        }
        if(degrees>=(FACTOR*29)&&degrees<(FACTOR*31)){
            text="30 Rojo";

        }
        if(degrees>=(FACTOR*31)&&degrees<(FACTOR*33)){
            text="8 Negro";
        }
        if(degrees>=(FACTOR*33)&&degrees<(FACTOR*35)){
            text="23 Rojo";
        }
        if(degrees>=(FACTOR*35)&&degrees<(FACTOR*37)){
            text="10 Negro";
        }
        if(degrees>=(FACTOR*37)&&degrees<(FACTOR*39)){
            text="5 Rojo";

        }
        if(degrees>=(FACTOR*39)&&degrees<(FACTOR*41)){
            text="24 Negro";
        }
        if(degrees>=(FACTOR*41)&&degrees<(FACTOR*43)){
            text="16 Rojo";

        }
        if(degrees>=(FACTOR*43)&&degrees<(FACTOR*45)){
            text="33 Negro"; num_rul.setTextColor(R.color.black);


        }
        if(degrees>=(FACTOR*45)&&degrees<(FACTOR*47)){
            text="1 Rojo";

        }
        if(degrees>=(FACTOR*47)&&degrees<(FACTOR*49)){
            text="20 Negro"; num_rul.setTextColor(R.color.black);

        }
        if(degrees>=(FACTOR*49)&&degrees<(FACTOR*51)){
            text="14 Rojo";

        }
        if(degrees>=(FACTOR*51)&&degrees<(FACTOR*53)){
            text="31 Negro"; num_rul.setTextColor(R.color.black);

        }
        if(degrees>=(FACTOR*53)&&degrees<(FACTOR*55)){
            text="9 Rojo";

        }
        if(degrees>=(FACTOR*55)&&degrees<(FACTOR*57)){
            text="22 Negro";

        }
        if(degrees>=(FACTOR*57)&&degrees<(FACTOR*59)){
            text="18 Rojo";

        }
        if(degrees>=(FACTOR*59)&&degrees<(FACTOR*61)){
            text="29 Negro"; num_rul.setTextColor(R.color.black);

        }
        if(degrees>=(FACTOR*61)&&degrees<(FACTOR*63)){
            text="7 Rojo";

        }
        if(degrees>=(FACTOR*63)&&degrees<(FACTOR*65)){
            text="28 Negro";
        }
        if(degrees>=(FACTOR*65)&&degrees<(FACTOR*67)){
            text="12 Rojo";
        }
        if(degrees>=(FACTOR*67)&&degrees<(FACTOR*69)){
            text="35 Negro";
        }

        if(degrees>=(FACTOR*71)&&degrees<(FACTOR*73)){
            text="26 Negro";

        }
        if((degrees>=(FACTOR*73)&&degrees<360)||(degrees >= 0 && degrees< (FACTOR*1))){
            text="0";
        }

       // num_rul.setTextColor(R.color.black);


        return text;
    }
}
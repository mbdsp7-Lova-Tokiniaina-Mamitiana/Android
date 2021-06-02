package com.example.etu000603_android.ui.pari.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.etu000603_android.R;
import com.example.etu000603_android.data.model.Pari;
import com.example.etu000603_android.ui.pari.PariActvity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


public class PagerFragment extends Fragment {
    private Pari pari=new Pari();
    private int position=0;
    private int pageCount=0;
    private PariActvity activity;

    public PagerFragment(){

    }

    public PagerFragment(Pari pari, PariActvity activity, int position, int page) {
        this.pari = pari;
        this.position=position;
        this.pageCount=page;
        this.activity=activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view= (ViewGroup) inflater.inflate(
                R.layout.pari_item, container, false);
        View content= activity.findViewById(R.id.content);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float width = displayMetrics.widthPixels;
        float height= displayMetrics.heightPixels;

      //  System.out.println("height screen:"+height+" vs "+content.getHeight()) ;
        TextView textEquipe1=view.findViewById(R.id.text_equipe1);
        TextView textEquipe2=view.findViewById(R.id.text_equipe2);
        TextView textDescription = view.findViewById(R.id.description_pari);

        textEquipe1.setText(pari.getMatch().getDomicile().getName());
        textEquipe2.setText(pari.getMatch().getExterieur().getName());
        textDescription.setText(pari.getDescription());


        Button pariButton =view.findViewById(R.id.btn_pari);
        pariButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.parier(pari);
            }
        });

        TextView textPage=view.findViewById(R.id.pagination);


        CardView cardcomp1=view.findViewById(R.id.card_equipe_1);
        CardView cardcomp2=view.findViewById(R.id.card_equipe_2);
        Float ratio = width/height;
        Log.d("Ratio",width + ":" + height);
        Log.d("Ratio",ratio + "");
        if(ratio < 0.48 || ratio > 0.525) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) cardcomp1.getLayoutParams();
            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) cardcomp2.getLayoutParams();
            int topMargin = (int) (height * 4 / 100);
            layoutParams.topMargin = (int) (topMargin);
            layoutParams2.topMargin = (int) (topMargin);
            cardcomp1.setLayoutParams(layoutParams);
            cardcomp2.setLayoutParams(layoutParams);
        }

        //CardView cardnext=view.findViewById(R.id.card_company_shadow);

        //CardView cardnext2=view.findViewById(R.id.card_company_shadow2);
        // cardcomp.setBackgroundResource(R.drawable.shadow);
        int page=position+1;
        textPage.setText((page)+"");

//        if(page==pageCount){
//            cardnext.setVisibility(View.GONE);
//        }
//        if(page==1){
//            cardnext2.setVisibility(View.GONE);
//        }
        TextView textPage2=view.findViewById(R.id.pagination2);


        textPage2.setText(" / "+pageCount);
        if(page<10){
            textPage.setText("0"+(page));


        }
        if(pageCount<10){
            textPage2.setText(" / 0"+pageCount);
        }
        CardView card1=view.findViewById(R.id.card_equipe_1);
        card1.setBackgroundResource(R.drawable.circle_cardview);
        CardView card2=view.findViewById(R.id.card_equipe_2);
        card2.setBackgroundResource(R.drawable.circle_cardview);
        ImageButton eyeButton=view.findViewById(R.id.eye_detail);
        eyeButton.setOnClickListener(new ImageButton.OnClickListener(){

            @Override
            public void onClick(View v) {

                activity.redirectToInfo(pari);
            }
        });
        final ImageView imageView1=view.findViewById(R.id.image_equipe1);
        if(pari.getMatch().getDomicile().getUrl_image()!=null) {
            if(!pari.getMatch().getDomicile().getUrl_image().isEmpty()&&!pari.getMatch().getDomicile().getUrl_image().equals("null")) {
                Glide.with(view.getContext())
                        .asBitmap().centerCrop()
                        .load(pari.getMatch().getDomicile().getUrl_image())
                        .centerCrop()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                Drawable drawable = new BitmapDrawable(getResources(), resource);


                                imageView1.setAdjustViewBounds(true);
                                imageView1.setImageDrawable(drawable);

                                imageView1.setPadding(28, 28, 28, 28);
                                pari.getMatch().getDomicile().setLogo_drawable(drawable);


                            }
                        });
            }
        }
        final ImageView imageView2=view.findViewById(R.id.image_equipe2);
        if(pari.getMatch().getExterieur().getUrl_image()!=null) {
            if(!pari.getMatch().getExterieur().getUrl_image().isEmpty()&&!pari.getMatch().getExterieur().getUrl_image().equals("null")) {
                Glide.with(view.getContext())
                        .asBitmap().centerCrop()
                        .load(pari.getMatch().getExterieur().getUrl_image())
                        .centerCrop()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                Drawable drawable = new BitmapDrawable(getResources(), resource);


                                imageView2.setAdjustViewBounds(true);
                                imageView2.setImageDrawable(drawable);

                                imageView2.setPadding(28, 28, 28, 28);
                                pari.getMatch().getExterieur().setLogo_drawable(drawable);


                            }
                        });
            }
        }
        LinearLayout linearLayout=view.findViewById(R.id.content_horizontal);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity.redirectToInfo(pari);

            }
        });
        return view;
    }
}

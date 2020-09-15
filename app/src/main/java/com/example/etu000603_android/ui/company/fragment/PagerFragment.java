package com.example.etu000603_android.ui.company.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.etu000603_android.R;
import com.example.etu000603_android.data.model.Address;
import com.example.etu000603_android.data.model.Company;
import com.example.etu000603_android.ui.company.SearchCompany;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


public class PagerFragment extends Fragment {
    private Company company=new Company("",new Address(),"");
    private int position=0;
    private int pageCount=0;
    private SearchCompany activity;
    public PagerFragment(){

    }

    public PagerFragment(Company company,SearchCompany activity,int position,int page) {
        this.company = company;
        this.position=position;
        this.pageCount=page;
        this.activity=activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view= (ViewGroup) inflater.inflate(
                R.layout.company_item, container, false);
        View content= activity.findViewById(R.id.content);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float width = displayMetrics.widthPixels;
        float height= displayMetrics.heightPixels;

        System.out.println("height screen:"+height+" vs "+content.getHeight()) ;
        TextView textCompany=view.findViewById(R.id.text_company);
        textCompany.setText(company.getName());

        TextView textAdresse=view.findViewById(R.id.company_adress);
        textAdresse.setText(company.getAddress().toString());

        TextView textPage=view.findViewById(R.id.pagination);


        CardView cardcomp=view.findViewById(R.id.card_company);
        Float ratio = width/height;
        Log.d("Ratio",width + ":" + height);
        Log.d("Ratio",ratio + "");
        if(ratio < 0.48 || ratio > 0.525) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) cardcomp.getLayoutParams();
            int topMargin = (int) (height * 4 / 100);
            layoutParams.topMargin = (int) (topMargin);
            cardcomp.setLayoutParams(layoutParams);
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
            textPage2.setText(" / 0"+pageCount);
        }
        CardView card=view.findViewById(R.id.card_company_img);
        card.setBackgroundResource(R.drawable.circle_cardview);

        ImageButton eyeButton=view.findViewById(R.id.eye_detail);
        eyeButton.setOnClickListener(new ImageButton.OnClickListener(){

            @Override
            public void onClick(View v) {

                activity.redirectToInfo(company);
            }
        });
        final ImageView imageView=view.findViewById(R.id.image_tiers);
        if(company.getUrl_logo()!=null) {
            if(!company.getUrl_logo().isEmpty()&&!company.getUrl_logo().equals("null")) {
                Glide.with(view.getContext())
                        .asBitmap().centerCrop()
                        .load(company.getUrl_logo())
                        .centerCrop()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                Drawable drawable = new BitmapDrawable(getResources(), resource);


                                imageView.setAdjustViewBounds(true);
                                imageView.setImageDrawable(drawable);

                                imageView.setPadding(55, 55, 55, 55);
                                company.setLogo_drawable(drawable);


                            }
                        });
            }
        }
        LinearLayout linearLayout=view.findViewById(R.id.content_horizontal);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity.redirectToInfo(company);

            }
        });
        return view;
    }
}

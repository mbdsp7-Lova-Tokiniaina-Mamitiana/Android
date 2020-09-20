package com.example.etu000603_android.ui.company.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import com.example.etu000603_android.data.model.Company;
import com.example.etu000603_android.ui.company.SearchCompany;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;


public class VerticalCompanyFragment extends Fragment {

    private Company company;
    private SearchCompany activity;
    private int heigth;
    private int width;
    public VerticalCompanyFragment(){

    }

    public VerticalCompanyFragment(Company company, SearchCompany activity) {

        this.company = company;
        this.activity=activity;
        View content=activity.findViewById(R.id.content);
        heigth=content.getHeight();
        width=content.getWidth();

    }
    public View createView(){
        int scale=heigth/970;
        final int heigthRoot=(heigth/5)-15;
      //  System.out.println("heightRoot:"+heigthRoot+" widthRoot:"+width);
        RelativeLayout relativeLayout=new RelativeLayout(activity.getBaseContext());
        RelativeLayout.LayoutParams lpRelative=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,heigthRoot );
        relativeLayout.setBackgroundColor(relativeLayout.getResources().getColor(android.R.color.transparent));
        lpRelative.leftMargin=width*60/720;
        lpRelative.rightMargin=width*95/720;
        lpRelative.bottomMargin=heigthRoot*2/144;
        CardView cardRoot=new CardView(activity.getBaseContext());

        cardRoot.setElevation(10);
        cardRoot.setBackgroundResource(R.drawable.round_white_cardview);
        cardRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.redirectToInfo(company);
            }
        });
        cardRoot.setId(R.id.card_company2);
        relativeLayout.addView(cardRoot,lpRelative);

        RelativeLayout relativeLayoutRoot=new RelativeLayout(activity.getBaseContext());
        relativeLayoutRoot.setBackgroundColor(relativeLayout.getResources().getColor(android.R.color.transparent));
        //relativeLayoutRoot.setBackgroundResource(R.drawable.round_white_cardview);
        cardRoot.addView(relativeLayoutRoot,new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        CardView cardCompany=new CardView(activity.getBaseContext());
        RelativeLayout.LayoutParams relativeCardCompany=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeCardCompany.setMargins(25,0,15,10);
        relativeCardCompany.addRule(RelativeLayout.CENTER_VERTICAL);
        cardCompany.setId(R.id.card_company);
        cardCompany.setBackgroundColor(relativeLayout.getResources().getColor(android.R.color.transparent));

        relativeLayoutRoot.addView(cardCompany,relativeCardCompany);

        RelativeLayout relativeLayoutCard=new RelativeLayout(activity.getBaseContext());
        RelativeLayout.LayoutParams rlpCardCompany=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlpCardCompany.addRule(RelativeLayout.CENTER_VERTICAL);

        cardCompany.addView(relativeLayoutCard,rlpCardCompany);
        cardCompany.setBackgroundResource(R.drawable.circle_cardview);
        //cardCompany.setPadding(15,15,15,15);
        final ImageView imageView=new ImageView(activity.getBaseContext());
        final int scaleHeight=20*heigthRoot/144;
        if(company.getLogo_drawable()==null){
          //  System.out.println("glide");
            Glide.with(activity.getBaseContext())
                    .asBitmap()
                    .centerCrop()
                    .load(company.getUrl_logo())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            Drawable drawable = new BitmapDrawable(activity.getResources(), resource);

                            imageView.setAdjustViewBounds(true);

                            imageView.setPadding(scaleHeight,scaleHeight,scaleHeight,scaleHeight);
                            imageView.setImageDrawable(drawable);

                            company.setLogo_drawable(drawable);


                        }
                    });
        }else{
           // System.out.println("not glide");
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);imageView.setPadding(scaleHeight,scaleHeight,scaleHeight,scaleHeight);

            imageView.setImageDrawable(company.getLogo_drawable());
        }

        float scaleImage=heigthRoot*90/144;
        RelativeLayout.LayoutParams lpImg=new RelativeLayout.LayoutParams((int)scaleImage,(int)scaleImage);
        lpImg.setMargins(5,5,5,5);

        //imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setBackgroundResource(R.drawable.img_rounded);
        relativeLayoutCard.setPadding(7,7,7,7);
        relativeLayoutCard.addView(imageView,lpImg);

        LinearLayout linearLayout=new LinearLayout(activity.getBaseContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        RelativeLayout.LayoutParams RLP=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RLP.leftMargin=10;
        RLP.addRule(RelativeLayout.RIGHT_OF,cardCompany.getId());
        RLP.addRule(RelativeLayout.CENTER_VERTICAL);
        relativeLayoutRoot.addView(linearLayout,RLP);

        LinearLayout.LayoutParams lptext=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lptext.rightMargin=37;
        LinearLayout.LayoutParams lptext2=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lptext2.rightMargin=38;
        TextView textComp=new TextView(activity.getBaseContext());
        textComp.setText(company.getName());
        textComp.setTypeface(ResourcesCompat.getFont(relativeLayout.getContext(), R.font.mosk_bold_700));
        int textSize1=16;
        int textSize2=13;
        if(width>1024){

            textSize1=18;
            textSize2=15;
        }
        textComp.setTextSize(textSize1);

        textComp.setTextColor(relativeLayout.getResources().getColor(R.color.colorBlack));
        TextView textAddress=new TextView(activity.getBaseContext());
        textAddress.setText(company.getAddress().toString());
        textAddress.setTextSize(textSize2);
        textAddress.setTypeface(ResourcesCompat.getFont(relativeLayout.getContext(), R.font.open_sans));
        textAddress.setTextColor(relativeLayout.getResources().getColor(R.color.colorBlack));
        linearLayout.addView(textComp,lptext);
        linearLayout.addView(textAddress,lptext2);

        ImageButton imageButton=new ImageButton(activity.getBaseContext());
        imageButton.setElevation(150);
        imageButton.setVisibility(View.VISIBLE);
        float scaleImageButton=heigthRoot*120/144;
        RelativeLayout.LayoutParams rlImageButton=new RelativeLayout.LayoutParams((int)scaleImageButton,(int)scaleImageButton);
        imageButton.setImageResource(R.drawable.eye_detail);
        imageButton.setBackgroundColor(relativeLayout.getResources().getColor(android.R.color.transparent));
        imageButton.setOnClickListener(new ImageButton.OnClickListener(){

            @Override
            public void onClick(View v) {
                activity.redirectToInfo(company);
            }
        });
        //rlImageButton.addRule(RelativeLayout.LEFT_OF,R.id.card_company2);
        rlImageButton.rightMargin=-60*heigthRoot/144;


        rlImageButton.addRule(RelativeLayout.CENTER_VERTICAL);
        rlImageButton.addRule(RelativeLayout.ALIGN_END,cardRoot.getId());
        rlImageButton.addRule(RelativeLayout.ALIGN_RIGHT,cardRoot.getId());
        relativeLayout.addView(imageButton,rlImageButton);
        imageButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return relativeLayout;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view= (ViewGroup) inflater.inflate(
                R.layout.company_item2, container, false);
        float scale=heigth/970;
        TextView textCompany=view.findViewById(R.id.company_name);
        textCompany.setText(company.getName());


        TextView textAdresse=view.findViewById(R.id.company_adress);
        textAdresse.setText(company.getAddress().getStreet());

        CardView card=view.findViewById(R.id.card_company_img);
        card.setBackgroundResource(R.drawable.circle_cardview);
        card.requestLayout();
        ImageButton eyeButton=view.findViewById(R.id.eye_button);
        eyeButton.setOnClickListener(new ImageButton.OnClickListener(){

            @Override
            public void onClick(View v) {
               activity.redirectToInfo(company);
            }
        });

        final ImageView imageView=view.findViewById(R.id.logo_company);





        if(company.getLogo_drawable()==null){
            if(company.getUrl_logo()!=null ) {
                if( !company.getUrl_logo().isEmpty()&&!company.getUrl_logo().equals("null")) {
                    Glide.with(view.getContext())
                            .asBitmap()
                            .load(company.getUrl_logo())
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    Drawable drawable = new BitmapDrawable(view.getResources(), resource);


                                    imageView.setImageDrawable(drawable);
                                    company.setLogo_drawable(drawable);


                                }
                            });
                }
            }
        }else{
            imageView.setImageDrawable(company.getLogo_drawable());
        }

      //  view.setScaleX(scale);
     //   view.setScaleY(scale);

        return view;
    }
}

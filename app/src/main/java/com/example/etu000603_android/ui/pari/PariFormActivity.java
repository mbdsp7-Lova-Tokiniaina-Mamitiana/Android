package com.example.etu000603_android.ui.pari;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.etu000603_android.R;
import com.example.etu000603_android.data.model.Match;
import com.example.etu000603_android.data.model.Pari;
import com.example.etu000603_android.data.model.User;
import com.example.etu000603_android.data.repository.ProfilRepository;
import com.example.etu000603_android.ui.language.ActivityWithLanguage;
import com.example.etu000603_android.ui.navigation.ActivityWithNavigation;
import com.example.etu000603_android.ui.pari.fragment.PariMatchFragment;
import com.example.etu000603_android.utils.Session;
import com.google.zxing.WriterException;

import java.text.SimpleDateFormat;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import jp.wasabeef.blurry.Blurry;

public class PariFormActivity extends ActivityWithNavigation {
    private TextView textDate,textEquipe1,textEquipe2 = null;
    private Button btn_pari =null;
    private ImageView imageView1,img_qr,imageView2 = null;
    private CardView cardView1,cardView2 = null;
    private Match match =null;
    private QRGEncoder qrgEncoder;
    private ProfilRepository profilRepository =new ProfilRepository();
    private LinearLayout view =null;
    private ProgressBar progressBar =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pari_form);
        final  PariFormActivity activity =this;
        this.match =Session.selected_match;
        textDate = findViewById(R.id.text_date);
        textEquipe1 = findViewById(R.id.text_equipe1);
        textEquipe2 = findViewById(R.id.text_equipe2);
        imageView1 = findViewById(R.id.image_equipe1);
        imageView2 = findViewById(R.id.image_equipe2);
        img_qr = findViewById(R.id.img_qr);
        this.progressBar = findViewById(R.id.progressBar);
        this.view = findViewById(R.id.content_scroll);
        this.cardView1= findViewById(R.id.card_equipe_1);
        this.cardView2= findViewById(R.id.card_equipe_2);
        this.cardView1.setBackgroundResource(R.drawable.circle_cardview);
        this.cardView2.setBackgroundResource(R.drawable.circle_cardview);
        SimpleDateFormat dateFormat =new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        textEquipe1.setText(this.match.getDomicile().getName());
        textEquipe2.setText(this.match.getExterieur().getName());
        if(match.getDomicile().getUrl_image()!=null) {
            if(!match.getDomicile().getUrl_image().isEmpty()&&!match.getDomicile().getUrl_image().equals("null")) {
                Glide.with(activity.getBaseContext())
                        .asBitmap().centerCrop()
                        .load(match.getDomicile().getUrl_image())
                        .centerCrop()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                Drawable drawable = new BitmapDrawable(getResources(), resource);


                                imageView1.setAdjustViewBounds(true);
                                imageView1.setImageDrawable(drawable);

                                imageView1.setPadding(28, 28, 28, 28);
                                match.getDomicile().setLogo_drawable(drawable);


                            }
                        });
            }
        }

        if(match.getExterieur().getUrl_image()!=null) {
            if(!match.getExterieur().getUrl_image().isEmpty()&&!match.getExterieur().getUrl_image().equals("null")) {
                Glide.with(activity.getBaseContext())
                        .asBitmap().centerCrop()
                        .load(match.getExterieur().getUrl_image())
                        .centerCrop()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                Drawable drawable = new BitmapDrawable(getResources(), resource);


                                imageView2.setAdjustViewBounds(true);
                                imageView2.setImageDrawable(drawable);

                                imageView2.setPadding(28, 28, 28, 28);
                                match.getExterieur().setLogo_drawable(drawable);


                            }
                        });
            }
        }
        textDate.setText(dateFormat.format(this.match.getDate()));
         qrgEncoder =new QRGEncoder(
                 match.getId(), null,
                 QRGContents.Type.TEXT,
                 250);  ;
        try {
            Bitmap bitmap = qrgEncoder.encodeAsBitmap();
            img_qr.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        final  LinearLayout listePari = findViewById(R.id.pari_list);
        for(Pari p:match.getListPari()){
            PariMatchFragment fragment =new PariMatchFragment(p,activity);
            View view =fragment.onCreateView(getLayoutInflater(),listePari,savedInstanceState);
            listePari.addView(view);
        }


    }
    public void loading(){
        final View back = getWindow().getDecorView().getRootView();
        view.setAlpha(0.8F);
        Blurry.with(back.getContext()).onto( (ViewGroup) back);
        progressBar.setVisibility(View.VISIBLE);
    }
    public void stop(){
        final View back = getWindow().getDecorView().getRootView();
        back.setAlpha(1F);
        Blurry.delete((ViewGroup) back);
        progressBar.setVisibility(View.GONE);
    }
    public void parier(Pari pari,double montant) throws Exception {
        checkAuthstate();
        User profil = Session.profil;

        if(profil!=null){
            final  PariFormActivity activity =this;
            if(montant>profil.getSolde()){
                throw  new Exception("Solde insuffisant");
            }
            loading();
            profilRepository.parier(profil.getId(),Session.selected_match,pari,montant,activity);
        }

    }
}
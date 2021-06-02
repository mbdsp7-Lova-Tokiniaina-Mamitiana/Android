package com.example.etu000603_android.ui.pari;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.etu000603_android.R;
import com.example.etu000603_android.data.model.Pari;
import com.example.etu000603_android.utils.Session;

public class PariFormActivity extends AppCompatActivity {
    private TextView textCote,description,textEquipe1,textEquipe2 = null;
    private Button btn_pari =null;
    private ImageView imageView1,imageView2 = null;
    private CardView cardView1,cardView2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pari_form);
        final Pari pari = Session.selected_pari;
        textCote = findViewById(R.id.cote);
        description = findViewById(R.id.description_pari);
        textEquipe1 = findViewById(R.id.text_equipe1);
        textEquipe2 = findViewById(R.id.text_equipe2);
        btn_pari = findViewById(R.id.btn_pari);
        imageView1 = findViewById(R.id.image_equipe1);
        imageView2 = findViewById(R.id.image_equipe2);
        cardView1 = findViewById(R.id.card_equipe_1);
        cardView2 = findViewById(R.id.card_equipe_2);
        textCote.setText("("+pari.getCote()+")");
        textEquipe1.setText(pari.getMatch().getDomicile().getName());
        textEquipe2.setText(pari.getMatch().getExterieur().getName());
        description.setText(pari.getDescription());
        cardView1.setBackgroundResource(R.drawable.circle_cardview);
        cardView2.setBackgroundResource(R.drawable.circle_cardview);
        if(pari.getMatch().getDomicile().getUrl_image()!=null) {
            if(!pari.getMatch().getDomicile().getUrl_image().isEmpty()&&!pari.getMatch().getDomicile().getUrl_image().equals("null")) {
                Glide.with(this.getApplicationContext())
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
        ;
        if(pari.getMatch().getExterieur().getUrl_image()!=null) {
            if(!pari.getMatch().getExterieur().getUrl_image().isEmpty()&&!pari.getMatch().getExterieur().getUrl_image().equals("null")) {
                Glide.with(this.getApplicationContext())
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
    }
}
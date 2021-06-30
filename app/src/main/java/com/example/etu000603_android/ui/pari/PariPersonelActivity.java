package com.example.etu000603_android.ui.pari;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Build;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.example.etu000603_android.R;
import com.example.etu000603_android.data.model.PariPersonnel;
import com.example.etu000603_android.data.model.PariStatut;
import com.example.etu000603_android.data.repository.PariRepository;
import com.example.etu000603_android.ui.navigation.ActivityWithNavigation;
import com.example.etu000603_android.ui.pari.fragment.VerticalPagerFragmentPersonnel;
import com.example.etu000603_android.ui.pari.fragment.VerticalPariFragment;
import com.example.etu000603_android.ui.pari.fragment.VerticalPariPersonelFragment;
import com.example.etu000603_android.utils.Session;

import java.util.List;

public class PariPersonelActivity extends ActivityWithNavigation {

    private Button buttonProgressing,buttonFinished;
    private int type =0;
    private PariRepository repository =null;
    private ProgressBar progressBar =null;
    private PariPersonelActivity activity =this;
    private  CardView content = null;
    private List<PariPersonnel> liste=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setIdPage(R.id.action_perso);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pari_personnel_drawer);
        buttonProgressing =findViewById(R.id.btn_progressing);
        buttonFinished = findViewById(R.id.btn_finished);
        content=this.findViewById(R.id.content);
        progressBar = findViewById(R.id.loading);
        buttonProgressing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonProgressing.setBackgroundResource(R.drawable.radius);
                buttonFinished.setBackgroundResource(R.drawable.border_help);
                type = 0;
                content.post(new Runnable() {
                    @Override
                    public void run() {

                        if(Session.profil!=null){
                            progressBar.setVisibility(View.VISIBLE);
                            repository.getPariPersonnel(Session.profil.getId(),PariStatut.ENCOURS,1,15,activity);
                        }


                    }
                });
            }
        });
        buttonFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonFinished.setBackgroundResource(R.drawable.radius);
                buttonProgressing.setBackgroundResource(R.drawable.border_help);
                type =1;
                content.post(new Runnable() {
                    @Override
                    public void run() {
                        if(Session.profil!=null){
                            progressBar.setVisibility(View.VISIBLE);
                            repository.getPariPersonnel(Session.profil.getId(),PariStatut.TERMINEE,1,15,activity);
                        }
                    }
                });
            }
        });
        CardView cardTrame=findViewById(R.id.card_trame);
        cardTrame.setBackgroundResource(R.drawable.trame);
        final String uid=getUid();
        repository=new PariRepository();
        progressBar.setVisibility(View.VISIBLE);
        content.post(new Runnable() {
            @Override
            public void run() {
                if(Session.profil!=null){
                    repository.getPariPersonnel(Session.profil.getId(),PariStatut.ENCOURS,1,15,activity);
                }
            }
        });
    }

    public void getPariDisponibles(final List<PariPersonnel> list,PariStatut type) {
        this.liste=list;
        getParis(list,type);

    }
    private void getParis(List<PariPersonnel> list, PariStatut type){

        content.removeAllViews();


        LinearLayout linearLayout=new LinearLayout(getBaseContext());
        ScrollView scrollView = new ScrollView(getBaseContext());
        RelativeLayout.LayoutParams lpRelative=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        content.addView(scrollView,lpRelative);
        // System.out.println("Content width:"+content.getWidth()+"Content height:"+content.getHeight());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lp1 =new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp1.topMargin = 50;
        scrollView.addView(linearLayout,lp1);
        //ScreenVerticalPagerAdapter adapter =new ScreenVerticalPagerAdapter(activity,liste,type);
        int n = liste.size();
        for(int i=0;i<n;i++){

            RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.bottomMargin = 50;
            VerticalPariPersonelFragment pariFragment =new VerticalPariPersonelFragment(liste.get(i),activity,type);
            linearLayout.addView(pariFragment.onCreateView(getLayoutInflater(),linearLayout,null),lp);
        }




        progressBar.setVisibility(View.GONE);

    }


    private class ScreenVerticalPagerAdapter extends FragmentStateAdapter {
        private List<PariPersonnel> liste=null;
        private  PariStatut type = null;
        public ScreenVerticalPagerAdapter(PariPersonelActivity fa, List<PariPersonnel> liste,PariStatut type) {
            super(fa);
            this.liste=liste;
            this.type =type;
        }

        @Override
        public Fragment createFragment(int position) {
            return new VerticalPagerFragmentPersonnel(liste,position,activity,type);
        }

        @Override
        public int getItemCount() {
            int n=liste.size();
            return ((n/5)+1);
        }
    }
}

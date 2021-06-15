package com.example.etu000603_android.ui.pari.fragment;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.etu000603_android.data.model.PariPersonnel;
import com.example.etu000603_android.data.model.PariStatut;
import com.example.etu000603_android.ui.pari.PariPersonelActivity;

import java.util.List;

import androidx.fragment.app.Fragment;

public class VerticalPagerFragmentPersonnel  extends Fragment {
    private List<PariPersonnel> liste=null;
    private int position=0;
    private PariPersonelActivity activity;
    private  PariStatut type;

    public VerticalPagerFragmentPersonnel(){

    }
    public VerticalPagerFragmentPersonnel(List<PariPersonnel> list, int position, PariPersonelActivity act, PariStatut type){
        liste=list;
        this.position=position;
        activity=act;
        this.type =type;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RelativeLayout relativeLayout=new RelativeLayout(activity.getBaseContext());
        RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeLayout.setGravity(RelativeLayout.CENTER_IN_PARENT);
        LinearLayout linearLayout = new LinearLayout(activity.getBaseContext());
        int n=liste.size();
        TextView textPage=new TextView(activity.getBaseContext());
        String html="<b> "+(position+1)+" </b> / "+((n/5)+1);
        RelativeLayout.LayoutParams linepa=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linepa.rightMargin=35;
        linepa.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        linepa.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        linepa.bottomMargin=10;

        textPage.setTextSize(16);
        textPage.setText(Html.fromHtml(html));
        // linearLayout.addView(textPage,linepa);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        textPage.setVisibility(View.GONE);
        relativeLayout.addView(textPage,linepa);
        //  scrollView.addView(linearLayout, lpScrol);

        for(int i=position;i<position+5;i++){
            if(i>=n){
                break;
            }
            PariPersonnel c=liste.get(i);
            VerticalPariPersonelFragment fragment=new VerticalPariPersonelFragment(c,activity,type);
            //  View view=fragment.onCreateView(getLayoutInflater(),linearLayout,activity.instance);
            LinearLayout.LayoutParams lp2=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            View view=fragment.onCreateView(getLayoutInflater(),linearLayout,savedInstanceState);

            lp2.bottomMargin=15;
            linearLayout.addView(view,lp2);
        }


        relativeLayout.addView(linearLayout,lp);
        return relativeLayout;
    }
}
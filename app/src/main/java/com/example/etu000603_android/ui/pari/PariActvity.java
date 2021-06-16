package com.example.etu000603_android.ui.pari;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import com.example.etu000603_android.R;
import com.example.etu000603_android.data.model.Pari;
import com.example.etu000603_android.data.repository.PariRepository;
import com.example.etu000603_android.ui.pari.fragment.PagerFragment;
import com.example.etu000603_android.ui.pari.fragment.VerticalPariFragment;
import com.example.etu000603_android.ui.pari.fragment.VerticalPagerFragment;
import com.example.etu000603_android.ui.navigation.ActivityWithNavigation;
import com.example.etu000603_android.utils.Session;

import java.util.ArrayList;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;




public class PariActvity extends ActivityWithNavigation {

    private CardView content=null;
    private EditText searchView=null;
    private PariRepository repository=null;
    public Bundle instance=null;
    PariActvity activity=null;
    private List<Pari> liste=null;
    private ProgressBar progressBar=null;
    private  ImageButton search_button;



    @Override
    protected void onResume() {
        super.onResume();

        enableSearchView();
       // progressBar.setVisibility(View.GONE);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setIdPage(R.id.action_home);
        super.onCreate(savedInstanceState);
        this.instance=savedInstanceState;
        setContentView(R.layout.activity_search_pari_drawer);
        activity=this;
        searchView=this.findViewById(R.id.searchView);
        search_button=findViewById(R.id.search_button);
        content=this.findViewById(R.id.content);
        progressBar=findViewById(R.id.loading);





        CardView cardTrame=findViewById(R.id.card_trame);
        cardTrame.setBackgroundResource(R.drawable.trame);

        repository=new PariRepository();
        progressBar.setVisibility(View.VISIBLE);
        content.post(new Runnable() {
            @Override
            public void run() {
                repository.getPariDisponibles(activity);

            }
        });

        this.configureSearchView();

    }


    public void parier(Pari pari){
        Session.selected_pari=pari;
        Intent intent=new Intent(getBaseContext(), PariFormActivity.class);

        startActivity(intent);
    }
    public void logout() {


    }



    public void redirectToInfo(Pari pari){
        parier(pari);
        //finish();
    }
    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }
    public void getPariDisponibles(final List<Pari> list) {
        this.liste=list;
        getParis(list,true);

    }



    private void getParis(List<Pari> list, boolean horizontal){

        content.removeAllViews();
        if(horizontal){

            RelativeLayout relativeLayout=new RelativeLayout(getBaseContext());
            RelativeLayout.LayoutParams lpRelative=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            content.addView(relativeLayout,lpRelative);
            int width = content.getWidth();
            int padding = width*15/100;
          //  System.out.println("padding:"+padding);
            final ViewPager2 viewPager=new ViewPager2(getBaseContext());

            viewPager.setClipToPadding(false);
            viewPager.setClipChildren(false);
            viewPager.setOffscreenPageLimit(3);
            viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
            viewPager.setPadding(padding,0,padding,0);
            // viewPager.setPageTransformer(new PageTrans(viewPager,list.size()));

            RelativeLayout.LayoutParams lpViewPager=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            lpViewPager.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

            FragmentStateAdapter  pagerAdapter = new ScreenSlidePagerAdapter(this,list);
            viewPager.setAdapter(pagerAdapter);

            CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
            compositePageTransformer.addTransformer(new MarginPageTransformer(padding/2));

            viewPager.setPageTransformer(compositePageTransformer);

            relativeLayout.addView(viewPager,lpViewPager);


        }else {


            //Ajout du scrollView
                /*ScrollView scrollView = new ScrollView(content.getContext());
                ViewGroup.LayoutParams lpScrol = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                content.addView(scrollView, lpScrol);

                //Ajout du contenu

                LinearLayout linearLayout = new LinearLayout(getBaseContext());
                LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                linearLayout.setOrientation(LinearLayout.VERTICAL);
                scrollView.addView(linearLayout, lpScrol);
                for(Company c:list){
                    VerticalCompanyFragment fragment=new VerticalCompanyFragment(c,this);
                    View view=fragment.onCreateView(getLayoutInflater(),linearLayout,this.instance);
                    linearLayout.addView(view);
                }*/
            LinearLayout linearLayout=new LinearLayout(getBaseContext());
            ScrollView scrollView = new ScrollView(getBaseContext());
            RelativeLayout.LayoutParams lpRelative=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            content.addView(scrollView,lpRelative);
           // System.out.println("Content width:"+content.getWidth()+"Content height:"+content.getHeight());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            scrollView.addView(linearLayout,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ScreenSlidePagerAdapter adapter =new ScreenSlidePagerAdapter(activity,liste);
            int n = adapter.getItemCount();
            for(int i=0;i<n;i++){

                RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.bottomMargin = 50;
                VerticalPariFragment pariFragment =new VerticalPariFragment(liste.get(i),activity);
                linearLayout.addView(pariFragment.onCreateView(getLayoutInflater(),linearLayout,null),lp);
            }



        }


        progressBar.setVisibility(View.GONE);

    }


    private List<Pari> getCompanyList(String companyName){
        companyName=companyName.trim();
        if(companyName.isEmpty()){
            return liste;
        }
        List<Pari> newList=new ArrayList<>();
        for(Pari c:liste){

                newList.add(c);

        }
        return  newList;
    }
    private void makeSearch(String query){
       // progressBar.setVisibility(View.VISIBLE);


      //  progressBar.setVisibility(View.VISIBLE);
        List<Pari> list=getCompanyList(query);

        if(query.trim().isEmpty()){

            getParis(list,true);
        }else{

            getParis(list,false);
        }
    }
    private void configureSearchView(){
        this.searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length()>=2||s.length()==0){
                    makeSearch(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        searchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String query=searchView.getText().toString();

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {



                    makeSearch(query);




                    hideKeybaord(v);
                    return true;
                }
                return false;
            }
        });


        search_button.setOnClickListener(new ImageButton.OnClickListener(){

            @Override
            public void onClick(View v) {

                String query=searchView.getText().toString();
                makeSearch(query);

                hideKeybaord(v);
            }
        });
        searchView.setEnabled(false);
        search_button.setEnabled(false);

    }
    public void sendEmail(String email){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"+email));

        startActivity(intent);
    }
    @Override
    protected void onDestroy() {
        try {

        }catch (Exception exc){

        }
        super.onDestroy();
    }
    private void enableSearchView(){
        searchView.setEnabled(true);
        search_button.setEnabled(true);
    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        private List<Pari> liste=null;
        private PariActvity activity;
        public ScreenSlidePagerAdapter(PariActvity fa, List<Pari> liste) {
            super(fa);
            activity=fa;
            this.liste=liste;
        }

        @Override
        public Fragment createFragment(int position) {
            return new PagerFragment(liste.get(position),activity,position,getItemCount());
        }

        @Override
        public int getItemCount() {
            return liste.size();
        }
    }
    private class ScreenVerticalPagerAdapter extends FragmentStateAdapter {
        private List<Pari> liste=null;
        public ScreenVerticalPagerAdapter(PariActvity fa, List<Pari> liste) {
            super(fa);
            this.liste=liste;
        }

        @Override
        public Fragment createFragment(int position) {
            return new VerticalPagerFragment(liste,position,activity);
        }

        @Override
        public int getItemCount() {
            int n=liste.size();
            return (n);
        }
    }
    private class HorizontalAdapter extends FragmentStateAdapter {
        private List<Pari> liste=null;
        public HorizontalAdapter(PariActvity fa, List<Pari> liste) {
            super(fa);
            this.liste=liste;
        }

        @Override
        public Fragment createFragment(int position) {
            return new VerticalPariFragment(liste.get(position),activity);
        }

        @Override
        public int getItemCount() {
            return liste.size();
        }
    }
}

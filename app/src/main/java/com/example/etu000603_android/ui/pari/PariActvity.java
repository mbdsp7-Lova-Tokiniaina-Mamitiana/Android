package com.example.etu000603_android.ui.pari;

import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import com.example.etu000603_android.R;
import com.example.etu000603_android.data.model.Match;
import com.example.etu000603_android.data.model.Pari;
import com.example.etu000603_android.data.repository.PariRepository;
import com.example.etu000603_android.ui.pari.fragment.PagerFragment;
import com.example.etu000603_android.ui.pari.fragment.VerticalPariFragment;
import com.example.etu000603_android.ui.pari.fragment.VerticalPagerFragment;
import com.example.etu000603_android.ui.navigation.ActivityWithNavigation;
import com.example.etu000603_android.utils.Session;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;




public class PariActvity extends ActivityWithNavigation {

    private CardView content=null;
    private EditText searchView,datedebut,datefin;
    private PariRepository repository=null;
    public Bundle instance=null;
    PariActvity activity=null;
    private List<Match> liste=null;
    private ProgressBar progressBar=null;
    private  ImageButton search_button;
    private Button previousButton,nextButton;
    private AlertDialog dialog1,dialog2;
    private int page =1;
    private int max =10;
    private int nbpages=1;
    private int totals=0;

    private CheckBox term_checkbox,today_checkbox;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setNbpages(int nbpages) {
        this.nbpages = nbpages;
    }

    public void setTotals(int totals) {
        this.totals = totals;
    }

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
        this.datedebut = findViewById(R.id.date_debut);
        this.datefin = findViewById(R.id.date_fin);
        this.term_checkbox = findViewById(R.id.finished_checkbox);
        this.today_checkbox = findViewById(R.id.today_checkbox);
        previousButton  = findViewById(R.id.previous_button);
        nextButton = findViewById(R.id.next_button);

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.setPage(activity.getPage()-1);
                activity.rechercher();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.setPage(activity.getPage()+1);
                activity.rechercher();
            }
        });



        setupDialog();

        CardView cardTrame=findViewById(R.id.card_trame);
        cardTrame.setBackgroundResource(R.drawable.trame);

        repository=new PariRepository();
        progressBar.setVisibility(View.VISIBLE);
        content.post(new Runnable() {
            @Override
            public void run() {
                repository.getMatchs2(activity,1,100);
                rechercher();

            }
        });

        this.configureSearchView();

    }
    private void setupDialog(){
        final View dialogView = View.inflate(activity, R.layout.datetimepicker, null);

        dialog1 = new AlertDialog.Builder(activity).create();

        dialogView.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);

                Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                        datePicker.getMonth(),
                        datePicker.getDayOfMonth(),
                        0,
                        0);

                long time = calendar.getTimeInMillis();
                datedebut.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date(time)));
                dialog1.dismiss();
            }});
        dialogView.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                datedebut.setText("dd/MM/yyyy");
                dialog1.dismiss();
            }});
        dialog1.setView(dialogView);
        this.datedebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Focusable");
                dialog1.show();
            }
        });
        final View dialogView2 = View.inflate(activity, R.layout.datetimepicker, null);
        dialog2 = new AlertDialog.Builder(activity).create();

        dialogView2.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePicker datePicker = (DatePicker) dialogView2.findViewById(R.id.date_picker);

                Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                        datePicker.getMonth(),
                        datePicker.getDayOfMonth(),
                        0,
                        0);

                long time = calendar.getTimeInMillis();
                datefin.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date(time)));
                dialog2.dismiss();
            }});
        dialogView2.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                datefin.setText("dd/MM/yyyy");
                dialog2.dismiss();
            }});
        dialog2.setView(dialogView2);
        this.datefin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Focusable1");
                dialog2.show();
            }
        });

    }
    private void rechercher(){
        progressBar.setVisibility(View.VISIBLE);
        repository.getMatchs(activity,page,max,searchView.getText().toString(),this.term_checkbox.isChecked(),this.today_checkbox.isChecked(),this.datedebut.getText().toString(),this.datefin.getText().toString());
    }

    public void infomatch(Match match){
        Session.selected_match=match;
        Intent intent=new Intent(getBaseContext(), PariFormActivity.class);

        startActivity(intent);
    }
    public void logout() {


    }



    public void redirectToInfo(Match match){
        infomatch(match);
        //finish();
    }
    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }
    public void getPariDisponibles(final List<Match> list) {
        this.liste=list;
        previousButton.setEnabled(true);
        nextButton.setEnabled(true);
        nextButton.setVisibility(View.VISIBLE);
        previousButton.setVisibility(View.VISIBLE);
        if(page == 1){
            previousButton.setEnabled(false);
            previousButton.setVisibility(View.INVISIBLE);
        }
        if(page == nbpages){
            nextButton.setEnabled(false);
            nextButton.setVisibility(View.INVISIBLE);
        }
        if(!Session.isOnline){
            totals = list.size();
            previousButton.setVisibility(View.INVISIBLE);
            nextButton.setVisibility(View.INVISIBLE);
            showMessage("Vous êtest actuellement déconnété.L'application utilisera le mode offline",true);
        }
        getParis(list,true);

    }



    private void getParis(List<Match> list, boolean horizontal){

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

            FragmentStateAdapter  pagerAdapter = new ScreenSlidePagerAdapter(this,list,page,max,totals);
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
            ScreenSlidePagerAdapter adapter =new ScreenSlidePagerAdapter(activity,list,page,max,totals);
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

    private List<Match> getMatchsByDates(List<Match> l){
        List<Match> newList=new ArrayList<>();
        String search = searchView.getText().toString();

        for(Match c:l){
             long dateavant =0;
             long dateapres=Long.MAX_VALUE;
             if(today_checkbox.isChecked()){
                Date avant =new Date(System.currentTimeMillis());
                avant.setHours(0);
                avant.setMinutes(0);
                dateavant =avant.getTime();
                Date apres =new Date(System.currentTimeMillis());
                avant.setHours(23);
                avant.setMinutes(59);
                dateapres =avant.getTime();

             }else{
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                 try{
                     Date avant = dateFormat.parse(this.datedebut.getText().toString());
                     dateavant =avant.getTime();
                 }catch (Exception exc){

                 }
                 try{
                     Date fin = dateFormat.parse(this.datefin.getText().toString());
                     dateapres =fin.getTime();
                 }catch (Exception exc){

                 }
             }
             if(c.getDate().getTime()<=dateapres && c.getDate().getTime()>=dateavant){
                 newList.add(c);
             }


        }


        return  newList;
    }

    private List<Match> getMatchsBySearch(List<Match> l){
        List<Match> newList=new ArrayList<>();
        String search = searchView.getText().toString();
        if(search!=null && search!=""){
            for(Match c:l){
                if(c.getDomicile().getName().toLowerCase().contains(search.toLowerCase())){
                    newList.add(c);
                    continue;
                }
                if(c.getExterieur().getName().toLowerCase().contains(search.toLowerCase())){
                    newList.add(c);
                    continue;
                }

                for(Pari p : c.getListPari()){
                        if(p.getDescription().toLowerCase().contains(search.toLowerCase())){
                            newList.add(c);

                            break;
                        }
                }


            }
        }else{
            return  l;
        }

        return  newList;
    }
    private List<Match> getCompanyList(String companyName){

        List<Match> newList=new ArrayList<>();
        List<Match> matchesSearch = getMatchsBySearch(liste);
        List<Match> matchesDates =getMatchsByDates(matchesSearch);

        return  matchesDates;
    }
    private void makeSearch(String query){
       // progressBar.setVisibility(View.VISIBLE);

        rechercher();
      //  progressBar.setVisibility(View.VISIBLE);
        if(!Session.isOnline){
            List<Match> list=getCompanyList(query);



            getParis(list,true);
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
                    //makeSearch(s.toString());
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
        private List<Match> liste=null;
        private PariActvity activity;
        private int results=0;
        private int page = 1;
        private int max =0;
        public ScreenSlidePagerAdapter(PariActvity fa, List<Match> liste,int page,int max,int results) {
            super(fa);
            activity=fa;
            this.liste=liste;
            this.results =results;
            this.page = page;
            this.max =max;
        }

        @Override
        public Fragment createFragment(int position) {
            int offset = (page-1)*max+position;
            return new PagerFragment(liste.get(position),activity,offset,results);
        }

        @Override
        public int getItemCount() {
            return liste.size();
        }
    }
    private class ScreenVerticalPagerAdapter extends FragmentStateAdapter {
        private List<Match> liste=null;
        public ScreenVerticalPagerAdapter(PariActvity fa, List<Match> liste) {
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
        private List<Match> liste=null;
        public HorizontalAdapter(PariActvity fa, List<Match> liste) {
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

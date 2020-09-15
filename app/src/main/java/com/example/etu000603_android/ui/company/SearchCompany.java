package com.example.etu000603_android.ui.company;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import com.example.etu000603_android.R;
import com.example.etu000603_android.data.model.Company;
import com.example.etu000603_android.data.repository.CompanyRepository;
import com.example.etu000603_android.ui.company.fragment.PagerFragment;
import com.example.etu000603_android.ui.company.fragment.VerticalCompanyFragment;
import com.example.etu000603_android.ui.company.fragment.VerticalPagerFragment;
import com.example.etu000603_android.ui.navigation.ActivityWithNavigation;
import com.example.etu000603_android.utils.Session;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;




public class SearchCompany extends ActivityWithNavigation {

    private CardView content=null;
    private EditText searchView=null;
    private CompanyRepository repository=null;
    public Bundle instance=null;
    private PopupWindow popUpEditus=null;
    SearchCompany activity=null;
    private List<Company> liste=null;
    private ProgressBar progressBar=null;
    private  ImageButton search_button;


    @Override
    protected void onResume() {
        super.onResume();

        enableSearchView();
        progressBar.setVisibility(View.GONE);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.instance=savedInstanceState;
        setContentView(R.layout.activity_search_society_drawer);
        activity=this;
        searchView=this.findViewById(R.id.searchView);
        search_button=findViewById(R.id.search_button);
        content=this.findViewById(R.id.content);
        progressBar=findViewById(R.id.loading);

        TextView textView=findViewById(R.id.company_not_found);
        String companynotfound=getResources().getString(R.string.company_not_found);
        String contact=getResources().getString(R.string.contact_us);
        String text=companynotfound+" "+contact;
        SpannableString spannable=new SpannableString(text);
        spannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorGreen)),companynotfound.length(),text.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {

                sendEmail("tokiniainaherve.andrianarison@gmail.com");
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        spannable.setSpan(clickableSpan,companynotfound.length(),text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(spannable,TextView.BufferType.SPANNABLE);



        CardView cardTrame=findViewById(R.id.card_trame);
        cardTrame.setBackgroundResource(R.drawable.trame);
        repository=new CompanyRepository();

        this.configureSearchView();
        configureDrawer();
        repository.getCompanies(activity);

    }



    public void logout() {


    }



    public void redirectToInfo(Company company){
        Session.selected_company=company;
       Intent intent=new Intent(getBaseContext(), CompanyDetails.class);

        startActivity(intent);
        //finish();
    }
    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }
    public void getCompaniesWebservice( List<Company> list) {
        this.liste=list;
        getCompanies(list,true);

    }
    private void logoutWait(int duration){
        try {
            Thread.sleep(duration);
            logout();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private int getIminList(int i ,List<Company> liste){
        int imin=i;
        for(int j=i;j<liste.size();j++){
            Company min=liste.get(imin);
            Company c=liste.get(j);
            if(c.lesserThan(min)){
                imin=j;

            }
        }
        return imin;
    }
    private List<Company> sortList(List<Company> liste){
        List<Company> newList=new ArrayList<>();
        for(Company c:liste){
            newList.add(c);
         //   newList.add(c);
        }
        int n=newList.size();
        if(n>0){

            for(int i=0;i<n;i++){
                Company c=newList.get(i);
                int imin=getIminList(i,newList);
                Company min=newList.get(imin);
                newList.set(i,min);
                newList.set(imin,c);

            }
        }
        return newList;
    }
    private class PageTrans implements ViewPager2.PageTransformer {
        private ViewPager2 viewPager2=null;
        private int n=0;

        public PageTrans(ViewPager2 viewPager2, int n) {
            this.viewPager2 = viewPager2;
            this.n = n;
        }

        @Override
        public void transformPage(@NonNull View page, float position) {
//            View viewShadow=page.findViewById(R.id.card_company_shadow);
//            View viewShadow2=page.findViewById(R.id.card_company_shadow2);
//           // System.out.println("position:"+position);
//            if(position==0){
//                int i=viewPager2.getCurrentItem()+1;
//                if(i!=n)
//                viewShadow.setVisibility(View.VISIBLE);
//                if(i!=1)
//                viewShadow2.setVisibility(View.VISIBLE);
//            }else {
//
//
//                    viewShadow.setVisibility(View.GONE);
//                    viewShadow2.setVisibility(View.GONE);
//
//            }
        }
    }
    private void getCompanies(List<Company> listNotSorted,boolean horizontal){

            content.removeAllViews();
            List<Company> list=sortList(listNotSorted);

            if(horizontal){

                RelativeLayout relativeLayout=new RelativeLayout(getBaseContext());
                RelativeLayout.LayoutParams lpRelative=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
                content.addView(relativeLayout,lpRelative);
                int width = content.getWidth();
                int padding = width*15/100;
                final ViewPager2 viewPager=new ViewPager2(getBaseContext());

                viewPager.setClipToPadding(false);
                viewPager.setClipChildren(false);
                viewPager.setOffscreenPageLimit(3);
                viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
                viewPager.setPadding(padding,0,padding,0);
               // viewPager.setPageTransformer(new PageTrans(viewPager,list.size()));

                RelativeLayout.LayoutParams lpViewPager=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
                lpViewPager.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

                FragmentStateAdapter pagerAdapter = new ScreenSlidePagerAdapter(this,list);
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
                RelativeLayout relativeLayout=new RelativeLayout(getBaseContext());
                RelativeLayout.LayoutParams lpRelative=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
                content.addView(relativeLayout,lpRelative);
                System.out.println("Content width:"+content.getWidth()+"Content height:"+content.getHeight());
                ViewPager2 viewPager=new ViewPager2(getBaseContext());
                viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
                viewPager.setClipToPadding(false);
                viewPager.setOnDragListener(new View.OnDragListener() {
                    @Override
                    public boolean onDrag(View v, DragEvent event) {
                        System.out.println("drag");
                        return true;
                    }
                });

                RelativeLayout.LayoutParams lpViewPager=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    viewPager.setForegroundGravity(Gravity.CENTER);
                }
                lpViewPager.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
                FragmentStateAdapter pagerAdapter = new ScreenVerticalPagerAdapter(this,list);
                viewPager.setAdapter(pagerAdapter);
                relativeLayout.addView(viewPager,lpViewPager);

            }




    }

    private List<Company> getCompanyList(String companyName){
        companyName=companyName.trim();
        if(companyName.isEmpty()){
            return liste;
        }
        List<Company> newList=new ArrayList<>();
        for(Company c:liste){
            if(c.contains(companyName)){
                newList.add(c);
            }
        }
        return  newList;
    }
    private void makeSearch(String query){
       // progressBar.setVisibility(View.VISIBLE);


      //  progressBar.setVisibility(View.VISIBLE);
        List<Company> list=getCompanyList(query);

        if(query.trim().isEmpty()){

            getCompanies(list,true);
        }else{

            getCompanies(list,false);
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
        private List<Company> liste=null;
        private SearchCompany activity;
        public ScreenSlidePagerAdapter(SearchCompany fa, List<Company> liste) {
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
        private List<Company> liste=null;
        public ScreenVerticalPagerAdapter(SearchCompany fa, List<Company> liste) {
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
            return ((n/5)+1);
        }
    }
    private class HorizontalAdapter extends FragmentStateAdapter {
        private List<Company> liste=null;
        public HorizontalAdapter(SearchCompany fa, List<Company> liste) {
            super(fa);
            this.liste=liste;
        }

        @Override
        public Fragment createFragment(int position) {
            return new VerticalCompanyFragment(liste.get(position),activity);
        }

        @Override
        public int getItemCount() {
            return liste.size();
        }
    }
}

package com.example.etu000603_android.ui.navigation;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;

import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.etu000603_android.R;
import com.example.etu000603_android.data.model.User;
import com.example.etu000603_android.data.repository.ProfilRepository;
import com.example.etu000603_android.ui.authstate.AuthState;
import com.example.etu000603_android.ui.authstate.LocalAuthState;
import com.example.etu000603_android.ui.language.ActivityWithLanguage;
import com.example.etu000603_android.ui.pari.PariActvity;
import com.example.etu000603_android.ui.language.LanguageItem;
import com.example.etu000603_android.ui.language.fragment.LanguageFragment;
import com.example.etu000603_android.ui.pari.PariPersonelActivity;
import com.example.etu000603_android.utils.ActivityFunction;

import com.example.etu000603_android.utils.Session;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import jp.wasabeef.blurry.Blurry;

import java.util.ArrayList;
import java.util.Locale;




public class ActivityWithNavigation extends LocalAuthState {

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private int idselected=0;
    private  boolean stop=false;
    private LanguageItem selected_language;
    private Bundle instance=null;
    private View languageSpinner;
    private PopupWindow popUp;
    private static boolean ondissmissclick =false;
    private int idPage =0;
    private static final int PERMISSION_REQUEST_CODE = 205;

    public void setIdPage(int idPage) {
        this.idPage = idPage;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance=savedInstanceState;


    }

    @Override
    public void onStart() {
        super.onStart();
        configurationGlobal();
    }

    public void configurationGlobal(){
        this.configureBottomNavigationView(idPage);
        this.configureDrawer();
        configureDrawer();
        configureDrawerInformation();
        configureSpinnerLanguage();
        setOnclickListener();
    }
    @Override
    public void configureDrawerInformation(){
        super.configureDrawerInformation();
        User profil = Session.profil;
        TextView name = findViewById(R.id.profil_name);
        TextView firstname = findViewById(R.id.profil_firstname);
        TextView solde = findViewById(R.id.profil_name);
        TextView recharger = findViewById(R.id.btn_recharge);
        if(profil!=null){
            View logoutView =findViewById(R.id.logout_view);
            if(logoutView!=null){
                TextView textDeco = logoutView.findViewById(R.id.textdeco);
                if(textDeco!=null){
                    textDeco.setText(R.string.logout);
                }
            }
            if(name!=null)
            name.setText(profil.getNom());
            if(firstname!=null)
            firstname.setText(profil.getPrenom());
            if(name!=null)
            name.setVisibility(View.VISIBLE);
            if(firstname!=null)

                firstname.setVisibility(View.VISIBLE);
            if(solde!=null)
                solde.setVisibility(View.VISIBLE);


        }else{
            View logoutView =findViewById(R.id.logout_view);
            if(logoutView!=null){
                TextView textDeco = logoutView.findViewById(R.id.textdeco);
                if(textDeco!=null){
                    textDeco.setText(R.string.action_sign_in_short);
                }
            }
            if(name!=null)
            name.setVisibility(View.INVISIBLE);
            if(firstname!=null)
            firstname.setVisibility(View.INVISIBLE);
            if(solde!=null)
            solde.setVisibility(View.INVISIBLE);
            if(recharger!=null){

                recharger.setText(R.string.action_sign_in_short);
            }
        }

    }
    public void updateSolde(double solde){
        if(Session.profil!=null){
            Session.profil.setSolde(Session.profil.getSolde()+solde);
            TextView textSolde =findViewById(R.id.solde);
            if(popUp!=null){
                popUp.dismiss();
                final Button boutonRecharge = popUp.getContentView().findViewById(R.id.btn_recharge);
                if(boutonRecharge!=null){
                    boutonRecharge.setEnabled(true);
                }
            }

            ActivityFunction.startCountAnimation(textSolde,0,Session.profil.getSolde(),1500);
        }
    }
    public void configureBottomNavigationView(final int id){

        final BottomNavigationView navigationView = findViewById(R.id.bottomNavigationView);
        if(navigationView!=null) {
            navigationView.setPadding(0, 0, 0, 40);
            idselected = id;

            // navigationView.setBackgroundResource(android.R.color.transparent);

            //navigationView.setBackground(navViewBackground);
            // checkColor();
            navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {


                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    //  item.setIcon(R.drawable.circle);

                    // checkColor();
                    if (item.getItemId() == id) {
                        return true;
                    }

                    switch (item.getItemId()) {




                        case R.id.action_home:

                            Intent intent = new Intent(getBaseContext(), PariActvity.class);

                            startActivity(intent);
                            break;

                        case R.id.action_perso:

                             intent = new Intent(getBaseContext(), PariPersonelActivity.class);

                            startActivity(intent);
                            break;

                        case R.id.action_menu:


                            //navigationView.setSelectedItemId(R.id.action_home);
                            showDrawerLayout();

                            break;

                        default:
                            break;
                    }

                    return true;
                }
            });
            if (id == R.id.action_home) {
                navigationView.setSelectedItemId(R.id.action_home);
            }
            if (id == R.id.action_perso) {
                navigationView.setSelectedItemId(R.id.action_perso);
            }
        }

    }
    public void configureDrawer(){
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        if(drawerLayout!=null) {
            final RelativeLayout content2 = findViewById(R.id.shadow_slide);
            content2.setElevation(0);
            content2.setBackgroundResource(R.drawable.layout_radius_gray);
            content2.setAlpha(0.5F);
            final RelativeLayout content = (RelativeLayout) findViewById(R.id.side_content);
            stop = false;
            final CardView decoview = findViewById(R.id.logout_button2);
            final View decoview2 = findViewById(R.id.logout_view);
            //ActivityFunction.configureDrawerLayout(this,drawerLayout,decoview,decoview2,content);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

            actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
                private float scaleFactorX = 3f;
                private float scaleFactorY = 3f;

                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {

                    //  System.out.println("SlideOffset:"+slideOffset);
                    //  System.out.println(drawerView.getId()+"  d:"+drawerLayout.getId()+" c:"+content.getId());
                    super.onDrawerSlide(drawerView, slideOffset);
                    if (stop) {
                        return;
                    }
                    float slideX = drawerView.getWidth() * slideOffset;
                    //  System.out.println("SlideX:"+slideX);
                    content.setTranslationX(slideX + 51);
                    content2.setVisibility(View.GONE);
                    // System.out.println("scale:"+(1 - (slideOffset / scaleFactor)));
                    content.setScaleX(1 - (slideOffset / scaleFactorX));
                    content.setScaleY(1 - (slideOffset / scaleFactorY));

                    decoview2.setVisibility(CardView.INVISIBLE);
                    decoview.setVisibility(CardView.INVISIBLE);
                    content.setBackgroundResource(R.drawable.layout_radius);

                    if (slideOffset == 0) {
                        content.setBackgroundResource(R.color.colorWhiteOpaque);
                        content.setTranslationX(0);
                        configureBottomNavigationView(idselected);
                        showBottomNavigationView();
                    } else {

                        hideBottomNavigationView();
                    }
                    content.setElevation(0);

                    //  System.out.println("drawer:"+content.getWidth());
                    if (slideOffset == 1) {

                        content2.setScaleX(0.35F);
                        content2.setTranslationX((slideX / (400 / 185)) + 50);
                        content2.setScaleY(0.55F);
                        content2.setVisibility(View.VISIBLE);
                        //deco.setX(200);
                        decoview2.setVisibility(CardView.VISIBLE);
                        // decoview2.setElevation(50);
                        //  System.out.println("elevation : "+decoview2.getElevation()+" vs "+ drawerLayout.getElevation()+" vs" +content.getElevation());
                        decoview.setVisibility(CardView.INVISIBLE);

                        configureOnTouchLister();


                    }

                }


            };

            drawerLayout.setScrimColor(Color.TRANSPARENT);
            drawerLayout.setDrawerElevation(0f);
            drawerLayout.addDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();
        }


    }

    public void logout2(){
        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("AuthSharedPref", Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        System.out.println("Auth Token:"+getAuthToken());
        redirectToAccueil();
    }
    public void goToLogin(){

            redirectToLogin();

    }
    public void configureOnTouchLister(){
        final DrawerLayout  drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        final View decoview=findViewById(R.id.logout_view);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final float decoX=decoview.getX();
        final float decoY=decoview.getY();
        final ActivityWithNavigation activity = this;

        drawerLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int)event.getX();
                int y = (int)event.getY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // System.out.println("("+x+","+""+y+")");

                        if(x>=decoX &&x<=decoX+decoview.getWidth()&& y>=decoY && y<=decoY+decoview.getHeight() ){
                           // System.out.println("deco");
                            stop=true;
                            System.out.println("Logout");
                            if(Session.profil!=null) {
                                activity.logout2();
                            }else{
                                activity.goToLogin();
                            }


                        }else{
                         //   System.out.println("click not good");
                        }

                        break;
                    case MotionEvent.ACTION_MOVE:
                        // System.out.println("move key");
                        break;
                    case MotionEvent.ACTION_UP:
                        //System.out.println("up key");
                        break;

                }

                return false;
            }
        });
    }
    public void showDrawerLayout(){

        if(this.actionBarDrawerToggle!=null){
            DrawerLayout drawerLayout =  findViewById(R.id.drawerLayout);
            if(drawerLayout!=null){

                drawerLayout.openDrawer(GravityCompat.START,true);
                TextView textSolde = findViewById(R.id.solde);
                double solde = 0;
                if(Session.profil!=null){
                    solde =Session.profil.getSolde();
                }
                ActivityFunction.startCountAnimation(textSolde,0,solde,1500);


            }


        }
    }

    private void setSelectedItem(LanguageItem languageItem,boolean init){
        String language=languageItem.getLanguage();
        //System.out.println("Language selected:"+language);
        ImageView imageView=findViewById(R.id.img_flag);
        TextView languageSpinner2=findViewById(R.id.language_spinner_text);
        languageSpinner2.setText(languageItem.getLanguage());
        imageView.setImageResource(languageItem.getIdLogo());

        if(selected_language.equals(languageItem)){
            return;
        }
        if(language.equals("Français")){

            setLocale("fr", init);
        }
        if(language.equals("English")){


            setLocale("en", init);
        }
        if(language.equals("Deutsche")){


            setLocale("de", init);
        }


        selected_language=languageItem;
    }
    private  void setOnclickListener(){
        Button condition=findViewById(R.id.recharger);
        if(condition!=null) {
            popUp = new PopupWindow(this.getBaseContext());
            final ActivityWithNavigation activity = this;
            condition.setText(getBaseContext().getResources().getString(R.string.recharger_compte));
            View customView = getLayoutInflater().inflate(R.layout.popup_account, null);
            final ImageButton bouton = customView.findViewById(R.id.close_button);
            final Button boutonRecharge = customView.findViewById(R.id.btn_recharge);
            final ProgressBar progressBar = customView.findViewById(R.id.progressBar);
            final EditText value = customView.findViewById(R.id.value);
            boutonRecharge.setEnabled(true);


            boutonRecharge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.checkAuthstate();
                    progressBar.setVisibility(View.VISIBLE);
                   boutonRecharge.setEnabled(false);
                    ProfilRepository repository =new ProfilRepository();
                    User user = Session.profil;
                    if(user!=null){
                        try {
                            double solde = 0;
                            try{

                                solde = Double.parseDouble(value.getText().toString());
                            }catch (Exception exc){
                               ;
                                throw new Exception("Montant invalide");

                            }
                            repository.ajouterSolde(user.getId(),solde,activity);
                        }catch (Exception exc){
                            showMessage(exc.getMessage(),true);
                            boutonRecharge.setEnabled(true);
                            progressBar.setVisibility(View.GONE);
                        }

                    }


                }
            });


            final View back = activity.getWindow().getDecorView().getRootView();

            bouton.setVisibility(View.GONE);
            bouton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popUp.dismiss();

                    // activity.enable(true);

                }
            });

            condition.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (popUp.isShowing()) {
                        back.setBackgroundColor(activity.getResources().getColor(R.color.colorWhite));

                        popUp.dismiss();

                        //activity.enable(true);

                    } else {

                        popUp.showAtLocation(back, Gravity.CENTER, 0, 0);
                        progressBar.setVisibility(View.GONE);
                        //back.setBackgroundColor(view.getResources().getColor(R.color.colorBlack));
                        back.setAlpha(0.8F);
                        Blurry.with(back.getContext()).onto((ViewGroup) back);

                        // activity.enable(false);


                    }
                }
            });
            popUp.setElevation(0F);
            popUp.setContentView(customView);
            popUp.setOutsideTouchable(true);


            popUp.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {

                    // back.setBackgroundColor(view.getResources().getColor(R.color.colorWhite));
                    Blurry.delete((ViewGroup) back);

                    back.setAlpha(1F);


                    ondissmissclick = true;
                    System.out.println("dismiss");
                }
            });
            popUp.setBackgroundDrawable(null);
            popUp.setFocusable(true);
            popUp.update();
        }
        final ImageButton button = findViewById(R.id.qr);
        final ActivityWithNavigation activity =this;
        if(button!=null){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkPermission()){
                        Intent intent = new Intent(getBaseContext(), QrActivity.class);

                        startActivity(intent);
                    }else{
                        requestPermission();
                    }

                }
            });
        }

    }
    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   Intent intent = new Intent(getBaseContext(), QrActivity.class);

                    startActivity(intent);
                    // main logic
                } else {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("Vous devez autoriser l'acces à la camera",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(ActivityWithNavigation.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Annuler", null)
                .create()
                .show();
    }


    public   void changeLogo(){
        final BottomNavigationView navigationView = findViewById(R.id.bottomNavigationView);

        ImageView imageView=findViewById(R.id.image_tiers);



    }
    public void configureSpinnerLanguage(){
        languageSpinner=findViewById(R.id.language_spinner);
        if(languageSpinner!=null) {
            ArrayList<String> listeLanguages = new ArrayList<>();
            listeLanguages.add("Français");
            listeLanguages.add("English");
            listeLanguages.add("Deutsche");
            listeLanguages.add("Lëtzebuergesch");
            final ArrayList<LanguageItem> liste = new ArrayList<LanguageItem>();
            Locale locale = getResources().getConfiguration().locale;
            liste.add(new LanguageItem("Français", R.drawable.france_rounded));
            liste.add(new LanguageItem("English", R.drawable.uk_rounded));
            liste.add(new LanguageItem("Deutsche", R.drawable.germany_flag));

            final Boolean[] init = {true};
            //languageSpinner.setAdapter(new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_1,listeLanguages));
            // languageSpinner.setAdapter(new LanguageAdapter(getBaseContext(),liste));
            final ImageView flag_img = findViewById(R.id.img_flag);
            if (locale.getLanguage().equals(new Locale("fr").getLanguage())) {
                selected_language = liste.get(0);
                liste.get(0).setSelected(true);
                setSelectedItem(liste.get(0), true);

            }
            if (locale.getLanguage().equals(new Locale("en").getLanguage())) {
                selected_language = liste.get(1);
                setSelectedItem(liste.get(1), true);

                liste.get(1).setSelected(true);
            }
            if (locale.getLanguage().equals(new Locale("de").getLanguage())) {


                selected_language = liste.get(2);
                setSelectedItem(liste.get(2), true);
                liste.get(2).setSelected(true);
            }

            final LinearLayout layout = new LinearLayout(getBaseContext());
            layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setBackgroundResource(R.drawable.border_help_white);

            final PopupWindow popUp = new PopupWindow(getBaseContext());
            final LinearLayout linearLayout = new LinearLayout(getBaseContext());
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setBackgroundResource(R.drawable.border_white_popup);
            for (final LanguageItem languageItem : liste) {
                LanguageFragment fragment = new LanguageFragment(languageItem);
                View view = fragment.onCreateView(getLayoutInflater(), layout, instance);
                //ImageView img=view.findViewById(R.id.img_flag);

                linearLayout.addView(view);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        setSelectedItem(languageItem, false);

                        popUp.dismiss();
                    }
                });

            }
            layout.setPadding(0, 50, 0, 50);
            layout.addView(linearLayout);
            Display display = getWindowManager().getDefaultDisplay();
            final View back = getWindow().getDecorView();
            final Animation anim = new ScaleAnimation(
                    1f, 1f, // Start and end values for the X axis scaling
                    0F, 1F, // Start and end values for the Y axis scaling
                    Animation.RELATIVE_TO_SELF, 1f, // Pivot point of X scaling
                    Animation.RELATIVE_TO_SELF, 1f); // Pivot point of Y scaling
            //  anim.setFillAfter(true); // Needed to keep the result of the animation
            anim.setDuration(300);
            anim.setInterpolator(new AccelerateInterpolator());
            final LinearLayout content = findViewById(R.id.drawer_menu);
            languageSpinner.setVisibility(View.INVISIBLE);
            languageSpinner.setOnClickListener(new ImageButton.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (popUp.isShowing()) {

                        back.setBackgroundColor(getResources().getColor(R.color.colorWhite));

                        popUp.dismiss();

                    } else {
                        popUp.showAtLocation(content, Gravity.BOTTOM, 0, 0);
                        layout.startAnimation(anim);
                        // back.setBackgroundColor(getResources().getColor(R.color.colorBlack));
                        try {
                            Blurry.with(back.getContext()).onto((ViewGroup) back);
                        } catch (Exception exc) {

                        }
                        back.setAlpha(0.8F);

                    }


                }
            });
            popUp.setElevation(0F);

            popUp.setWidth(display.getWidth());
            popUp.setContentView(layout);
            popUp.setOutsideTouchable(true);
            popUp.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {

                    // back.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    try {
                        Blurry.delete((ViewGroup) back);
                        back.setAlpha(1F);
                    } catch (Exception exc) {

                    }
                }
            });
            popUp.setBackgroundDrawable(null);

       /* languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LanguageItem languageItem=(LanguageItem) parent.getItemAtPosition(position);
                String language=languageItem.getLanguage();
                System.out.println("Language selected:"+language);

                if(language.equals("Français")){
                    flag_img.setImageResource(R.drawable.france_rounded);
                    setLocale("fr", init[0]);
                }
                if(language.equals("English")){

                    flag_img.setImageResource(R.drawable.uk_rounded);
                    setLocale("en", init[0]);
                }
                if(language.equals("Deutsche")){

                    flag_img.setImageResource(R.drawable.germany_flag);
                    setLocale("de", init[0]);
                }
                if(language.equals("Lëtzebuergesch")){

                    flag_img.setImageResource(R.drawable.luxembourg_rounded);
                    setLocale("lb", init[0]);
                }
                init[0] =false;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        */
        }
    }
    private void showBottomNavigationView(){
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        final ImageView tiers=findViewById(R.id.image_tiers);
        if(bottomNavigationView!=null){
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
        if(tiers!=null){
            tiers.setVisibility(View.VISIBLE);
        }
    }
    private void hideBottomNavigationView(){
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        final ImageView tiers=findViewById(R.id.image_tiers);
        if(bottomNavigationView!=null){
            bottomNavigationView.setVisibility(View.GONE);
        }
        if(tiers!=null){
            tiers.setVisibility(View.GONE);
        }
    }

}

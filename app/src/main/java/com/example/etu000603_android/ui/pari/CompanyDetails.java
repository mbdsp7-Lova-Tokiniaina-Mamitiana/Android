package com.example.etu000603_android.ui.pari;

import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.example.etu000603_android.R;
import com.example.etu000603_android.data.model.Company;
import com.example.etu000603_android.data.model.Contact;
import com.example.etu000603_android.data.model.Pari;
import com.example.etu000603_android.ui.pari.fragment.ContactFragment;
import com.example.etu000603_android.ui.navigation.ActivityWithNavigation;
import com.example.etu000603_android.utils.Session;

import java.util.ArrayList;
import java.util.List;

public class CompanyDetails extends ActivityWithNavigation {

    private WebView videoWeb;
    private LinearLayout content=null;
    private  int REQUEST_CALL=1;
    private Bundle saveInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details_drawer);
        this.saveInstance=savedInstanceState;
        this.configureBottomNavigationView(R.id.action_home);
        this.configureDrawer();
        videoWeb =  findViewById(R.id.video);

        content=findViewById(R.id.content);

        initYoutubeVideo();
        initContact();
        changeLogo();
        configureSpinnerLanguage();
        configureDrawerInformation();

    }
    private void initContact(){
        Pari selected= Session.selected_pari;
        CompanyDetails activity=this;
        checkLanguage();
        if(selected!=null){
            List<Contact> listContact=new ArrayList<>();
            int n=0;
            if(listContact!=null){
                n=listContact.size();
            }
            for(int i=0;i<n;i++){
                ContactFragment contactFragment=new ContactFragment(listContact.get(i),activity);
                View view=contactFragment.onCreateView(getLayoutInflater(),content,null);
                content.addView(view);
            }

        }
    }
    private void initYoutubeVideo(){
        checkLanguage();
        videoWeb.getSettings().setJavaScriptEnabled(true);
        videoWeb.setWebChromeClient(new WebChromeClient() {

        } );
       // String videoUrl="<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/eWEF1Zrmdow\" frameborder=\"0\" allowfullscreen></iframe>";
        String videoUrl="<iframe width=\"100%\" height=\"100%\" src=\""+getBaseContext().getResources().getString(R.string.url_video)+"\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>";

        videoWeb.loadData( videoUrl, "text/html" , "utf-8" );

    }
    public void sendEmail(String email){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"+email));

        startActivity(intent);
    }
    public void callPhone(String phone) {
        String uri = "tel:" + phone.trim();
        final Intent intent = new Intent(Intent.ACTION_CALL);

        intent.setData(Uri.parse(uri));
        final CompanyDetails activity=this;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.text_phone_permisson)+"   "+phone.trim()+"?")
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
                            }
                        }else{
                            startActivity(intent);
                        }
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        Dialog dialog= builder.create();
        dialog.show();
        /**/

    }
}
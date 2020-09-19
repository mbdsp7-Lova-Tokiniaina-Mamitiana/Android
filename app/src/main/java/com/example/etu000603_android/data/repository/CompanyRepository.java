package com.example.etu000603_android.data.repository;

import android.util.Log;


import com.example.etu000603_android.data.model.Address;
import com.example.etu000603_android.data.model.Company;
import com.example.etu000603_android.data.model.Contact;
import com.example.etu000603_android.ui.company.SearchCompany;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.solver.widgets.Snapshot;


public class CompanyRepository {


public void getCompanies(SearchCompany activity){
    List<Company> companies=new ArrayList<Company>();
    for(int i=0;i<8;i++){
        Company c=new Company("company"+i,new Address("",1,1,"",""),"");
        Contact c1=new Contact();
        c1.setName("ANDRIANARISON");
        c1.setFirstName("Tokiniaina Hervé");
        c1.setRole(Contact.ContactType.ACCOUNT_MANAGER);
        c1.setEmail("tokiniaina12@yahoo.fr");
        c1.setNumero("+261 34 96 733 69");
        c.addContact(c1);
        Contact c2=new Contact();
        c2.setName("ANDRIANARISON");
        c2.setFirstName("Tokiniaina Hervé");
        c2.setRole(Contact.ContactType.COMMERCIAL);
        c2.setEmail("tokiniaina12@yahoo.fr");
        c2.setNumero("+261 34 96 733 69");
        c.addContact(c2);
        companies.add(c);
    }

    activity.getCompaniesWebservice(companies);
}
    public void getCompanies(final SearchCompany activity, String uid){

        System.out.println("uid:"+uid);
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference(uid);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Company> list=new ArrayList<Company>();
                DataSnapshot snapshotcompanies=snapshot.child("companies");
                for(DataSnapshot snapshotC:snapshotcompanies.getChildren()){
                    Company c=new Company();
                    String name=(String)snapshotC.child("name").getValue();
                    String logo=(String)snapshotC.child("logo").getValue();
                    DataSnapshot snapshotAdress=snapshotC.child("address");
                    String city=(String)snapshotAdress.child("city").getValue();
                    String country=(String)snapshotAdress.child("country").getValue();
                    String number=(String)snapshotAdress.child("number").getValue();
                    String postalCode=(String)snapshotAdress.child("postalCode").getValue();
                    String street=(String)snapshotAdress.child("street").getValue();
                    Address address=new Address(street,Integer.parseInt(number),Integer.parseInt(postalCode),city,country);
                    c.setAddress(address);
                    c.setName(name);
                    c.setUrl_logo(logo);
                    List<Contact> listeContacts=new ArrayList<Contact>();
                    DataSnapshot contacts=snapshotAdress.child("contacts");
                    if(contacts.hasChild("commercial")){
                        DataSnapshot contact=contacts.child("commercial");
                        Contact c1=new Contact();
                        c1.setEmail(contact.child("email").getValue()+"");
                        c1.setFirstName(contact.child("firstname").getValue()+"");
                        c1.setNumero(contact.child("tel").getValue()+"");
                        c1.setName(contact.child("lastname").getValue()+"");
                        c1.setRole(Contact.ContactType.COMMERCIAL);
                        listeContacts.add(c1);
                    }
                    if(contacts.hasChild("accountManager")){
                        DataSnapshot contact=contacts.child("accountManager");
                        Contact c1=new Contact();
                        c1.setEmail(contact.child("email").getValue()+"");
                        c1.setFirstName(contact.child("firstname").getValue()+"");
                        c1.setNumero(contact.child("tel").getValue()+"");
                        c1.setName(contact.child("lastname").getValue()+"");
                        c1.setRole(Contact.ContactType.ACCOUNT_MANAGER);
                        listeContacts.add(c1);
                    }
                    c.setListeDesContacts(listeContacts);
                    list.add(c);
                    activity.getCompaniesWebservice(list);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }





}

package com.example.etu000603_android.data.repository;

import android.util.Log;


import com.example.etu000603_android.data.model.Address;
import com.example.etu000603_android.data.model.Company;
import com.example.etu000603_android.data.model.Contact;
import com.example.etu000603_android.ui.company.SearchCompany;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;


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






}

package com.example.etu000603_android.ui.pari.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.etu000603_android.R;
import com.example.etu000603_android.data.model.Contact;
import com.example.etu000603_android.ui.pari.CompanyDetails;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class ContactFragment extends Fragment {
    private Contact contact;
    private CompanyDetails activity;
    public ContactFragment() {

    }

    ;

    public ContactFragment(Contact contact, CompanyDetails activity) {
        this.contact = contact;
        this.activity = activity;
    }

    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = (ViewGroup) inflater.inflate(
                R.layout.contact_fragment, container, false);



        TextView fullname = view.findViewById(R.id.text_fullname);
        fullname.setText(contact.getFirstName() + " " + contact.getName());

        final TextView phoneNumber = view.findViewById(R.id.phone_number);
        View panelNumber=view.findViewById(R.id.phone_panel);
        if(contact.getNumero()==null || contact.getNumero().isEmpty()||contact.getNumero().equals("null")){
            panelNumber.setVisibility(View.GONE);
        }
        phoneNumber.setText(contact.getNumero());
        phoneNumber.setTextIsSelectable(true);
       /* CardView card = view.findViewById(R.id.card_company_img);
        card.setBackgroundResource(R.drawable.circle_cardview);*/
       View panelEmail=view.findViewById(R.id.email_panel);
        final TextView email = view.findViewById(R.id.email);
        if(contact.getEmail()==null || contact.getEmail().isEmpty()||contact.getEmail().equals("null")){
            panelEmail.setVisibility(View.GONE);
        }
        email.setText(contact.getEmail());
       email.setTextIsSelectable(true);
        
        TextView textrole = view.findViewById(R.id.text_role);
        ImageView imageView = view.findViewById(R.id.contact_icon);
        CardView cardView=view.findViewById(R.id.card_logo);
        cardView.setBackgroundResource(R.drawable.circle_cardview);

        Button btn_msg=view.findViewById(R.id.btn_msg);

        Button btn_call = view.findViewById(R.id.btn_call);
        String type="CEM";
        if (contact.getRole() == Contact.ContactType.ACCOUNT_MANAGER) {
            textrole.setText(activity.getResources().getString(R.string.account_manager));
            imageView.setImageResource(R.drawable.ico_role2);


        } else if (contact.getRole() == Contact.ContactType.COMMERCIAL) {
            type="CM";
            imageView.setImageResource(R.drawable.ico_role);
            textrole.setText(view.getResources().getString(R.string.commercial));
        }

        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               activity.callPhone(contact.getNumero());
            }
        });
        btn_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.sendEmail(contact.getEmail());
            }
        });

        return view;
    }
}

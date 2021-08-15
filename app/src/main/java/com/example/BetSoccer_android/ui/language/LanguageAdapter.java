package com.example.BetSoccer_android.ui.language;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.BetSoccer_android.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;


public class LanguageAdapter extends ArrayAdapter<LanguageItem> {
    List<LanguageItem> listeLanguage;
    public LanguageAdapter( Context context, ArrayList<LanguageItem> languageItems) {
        super(context, 0,languageItems);
        this.listeLanguage=languageItems;
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.language_item,parent,false);
            TextView textView=convertView.findViewById(R.id.text_language);
            ImageView imageView =convertView.findViewById(R.id.img_flag);

            LanguageItem item=getItem(position);
            imageView.setImageResource(item.getIdLogo());
            textView.setText(item.getLanguage());
            if(item.isSelected()){
                imageView.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);
                convertView.setVisibility(View.GONE);
            }

        }
        return convertView;
    }

    public View initView(int position,  View convertView,  ViewGroup parent) {
       if(convertView==null){
           convertView= LayoutInflater.from(getContext()).inflate(R.layout.language_item,parent,false);
            TextView textView=convertView.findViewById(R.id.text_language);
           ImageView imageView =convertView.findViewById(R.id.img_flag);

           LanguageItem item=getItem(position);
            imageView.setImageResource(item.getIdLogo());
            textView.setText(item.getLanguage());


       }
       return convertView;
    }
}

package com.example.BetSoccer_android.ui.language.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.BetSoccer_android.R;
import com.example.BetSoccer_android.ui.language.LanguageItem;

import androidx.fragment.app.Fragment;

public class LanguageFragment extends Fragment {
    private LanguageItem languageItem=null;

    public LanguageFragment() {

    }

    public LanguageFragment(LanguageItem item) {
        this.languageItem=item;
    }
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        final View convertView= (ViewGroup) inflater.inflate(
                R.layout.language_item, container, false);
        TextView textView=convertView.findViewById(R.id.text_language);
        ImageView imageView =convertView.findViewById(R.id.img_flag);

        LanguageItem item=languageItem;
        imageView.setImageResource(item.getIdLogo());
        textView.setText(item.getLanguage());
        if(item.isSelected()){
            imageView.setImageResource(R.drawable.green_mark);
        }
        imageView.setElevation(100);
        return convertView;
    }

}

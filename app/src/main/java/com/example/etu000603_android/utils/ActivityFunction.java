package com.example.etu000603_android.utils;

import android.animation.ValueAnimator;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class ActivityFunction extends AppCompatActivity {


    public static void startCountAnimation(final TextView textView, double initialValue, double finalValue, long duration) {
        final ValueAnimator animator = ValueAnimator.ofFloat((float)initialValue, (float)finalValue);
        animator.setDuration(duration);
        final DecimalFormat decim = new DecimalFormat("#,###.##");

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                Float val=(Float)animator.getAnimatedValue();
                textView.setText(decim.format(val.longValue()));
            }
        });
        animator.start();
    }
    public static void startCountAnimationWithPlus(final TextView textView, double initialValue, double finalValue, long duration) {
        final ValueAnimator animator = ValueAnimator.ofFloat((float)initialValue, (float)finalValue);
        animator.setDuration(duration);
        final DecimalFormat decim = new DecimalFormat("#,###.##");
        String symbole = "-";
        if(finalValue>0){
            symbole = "+";
        }
        final String finalSymbole = symbole;
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                Float val=(Float)animator.getAnimatedValue();

                textView.setText(finalSymbole +" "+decim.format(Math.abs(val.longValue())));
            }
        });
        animator.start();
    }
   

}

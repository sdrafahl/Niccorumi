package com.shanedrafahl.www.niccorumi;

import android.app.Fragment;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.Comparator;
import java.util.TreeMap;

/**
 * Created by shanedrafahl on 7/21/16.
 */
public class Card extends Fragment implements Comparable<Card>  {
    public boolean validSelection=true;
    public boolean valid=true;
    public boolean isTopCard=false;
    public int height=0;
    public int width=0;
    protected int value = 0;
    boolean selected = false;


    public void setValue(int val) {
        this.value = val;
    }
    public String suite=null;
    public int getValue() {
        return this.value;
    }


    public int compareTo(Card c) {
        int num=0;
        if(this.suite.equals("hearts")){
            num=50;
        }
        if(this.suite.equals("spades")){
            num=40;
        }
        if(this.suite.equals("clubs")){
            num=30;
        }
        if(this.suite.equals("diamonds")){
            num=20;
        }
        num=num+this.getValue();
        //hearts,spades,clubs,diamonds
        int num1=0;
        if(c.suite.equals("hearts")){
            num1=50;
        }
        if(c.suite.equals("spades")){
            num1=40;
        }
        if(c.suite.equals("clubs")){
            num1=30;
        }
        if(c.suite.equals("diamonds")){
            num1=20;
        }
        num= num + this.value;
        num1= num1+ c.getValue();
        return num-num1;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v1 = inflater.inflate(R.layout.card, container, false);
        if(!isTopCard){
            v1.setY(100f);
        }
        ViewGroup.LayoutParams par = v1.getLayoutParams();
        par.width = this.width;
        par.height = this.height;
        v1.setLayoutParams(par);
       // v1.setY(150f);
        ImageView img = (ImageView) v1.findViewById(R.id.cardI);
        if(suite.equals("hearts")){
            switch(this.value){

                case 1:
                    img.setImageResource(R.drawable.ace_of_hearts);
                    break;
                case 2:
                    img.setImageResource(R.drawable.f2_of_hearts);
                    break;
                case 3:
                    img.setImageResource(R.drawable.f3_of_hearts);
                    break;
                case 4:
                    img.setImageResource(R.drawable.f4_of_hearts);
                    break;
                case 5:
                    img.setImageResource(R.drawable.f5_of_hearts);
                    break;
                case 6:
                    img.setImageResource(R.drawable.f6_of_hearts);
                    break;
                case 7:
                    img.setImageResource(R.drawable.f7_of_hearts);
                    break;
                case 8:
                    img.setImageResource(R.drawable.f8_of_hearts);
                    break;
                case 9:
                    img.setImageResource(R.drawable.f9_of_hearts);
                    break;
                case 10:
                    img.setImageResource(R.drawable.f10_of_hearts);
                    break;
                case 11:
                    img.setImageResource(R.drawable.jack_of_hearts);
                    break;
                case 12:
                    img.setImageResource(R.drawable.queen_of_hearts);
                    break;
                case 13:
                    img.setImageResource(R.drawable.king_of_hearts);
                    break;
            }
        }
        if(suite.equals("clubs")){
            switch(this.value){

                case 1:
                    img.setImageResource(R.drawable.ace_of_clubs);
                    break;
                case 2:
                    img.setImageResource(R.drawable.f2_of_clubs);
                    break;
                case 3:
                    img.setImageResource(R.drawable.f3_of_clubs);
                    break;
                case 4:
                    img.setImageResource(R.drawable.f4_of_clubs);
                    break;
                case 5:
                    img.setImageResource(R.drawable.f5_of_clubs);
                    break;
                case 6:
                    img.setImageResource(R.drawable.f6_of_clubs);
                    break;
                case 7:
                    img.setImageResource(R.drawable.f7_of_clubs);
                    break;
                case 8:
                    img.setImageResource(R.drawable.f8_of_clubs);
                    break;
                case 9:
                    img.setImageResource(R.drawable.f9_of_clubs);
                    break;
                case 10:
                    img.setImageResource(R.drawable.f10_of_clubs);
                    break;
                case 11:
                    img.setImageResource(R.drawable.jack_of_clubs);
                    break;
                case 12:
                    img.setImageResource(R.drawable.queen_of_clubs);
                    break;
                case 13:
                    img.setImageResource(R.drawable.king_of_clubs);
                    break;
            }
        }
        if(suite.equals("spades")){
            switch(this.value){

                case 1:
                    img.setImageResource(R.drawable.ace_of_spades);
                    break;
                case 2:
                    img.setImageResource(R.drawable.f2_of_spades);
                    break;
                case 3:
                    img.setImageResource(R.drawable.f3_of_spades);
                    break;
                case 4:
                    img.setImageResource(R.drawable.f4_of_spades);
                    break;
                case 5:
                    img.setImageResource(R.drawable.f5_of_spades);
                    break;
                case 6:
                    img.setImageResource(R.drawable.f6_of_spades);
                    break;
                case 7:
                    img.setImageResource(R.drawable.f7_of_spades);
                    break;
                case 8:
                    img.setImageResource(R.drawable.f8_of_spades);
                    break;
                case 9:
                    img.setImageResource(R.drawable.f9_of_spades);
                    break;
                case 10:
                    img.setImageResource(R.drawable.f10_of_spades);
                    break;
                case 11:
                    img.setImageResource(R.drawable.jack_of_spades);
                    break;
                case 12:
                    img.setImageResource(R.drawable.queen_of_spades);
                    break;
                case 13:
                    img.setImageResource(R.drawable.king_of_spades);
                    break;
            }
        }
        if(suite.equals("diamonds")){
            switch(this.value){

                case 1:
                    img.setImageResource(R.drawable.ace_of_diamonds);
                    break;
                case 2:
                    img.setImageResource(R.drawable.f2_of_diamonds);
                    break;
                case 3:
                    img.setImageResource(R.drawable.f3_of_diamonds);
                    break;
                case 4:
                    img.setImageResource(R.drawable.f4_of_diamonds);
                    break;
                case 5:
                    img.setImageResource(R.drawable.f5_of_diamonds);
                    break;
                case 6:
                    img.setImageResource(R.drawable.f6_of_diamonds);
                    break;
                case 7:
                    img.setImageResource(R.drawable.f7_of_diamonds);
                    break;
                case 8:
                    img.setImageResource(R.drawable.f8_of_diamonds);
                    break;
                case 9:
                    img.setImageResource(R.drawable.f9_of_diamonds);
                    break;
                case 10:
                    img.setImageResource(R.drawable.f10_of_diamonds);
                    break;
                case 11:
                    img.setImageResource(R.drawable.jack_of_diamonds);
                    break;
                case 12:
                    img.setImageResource(R.drawable.queen_of_diamonds);
                    break;
                case 13:
                    img.setImageResource(R.drawable.king_of_diamonds);
                    break;
            }
        }
        return v1;
   }


}

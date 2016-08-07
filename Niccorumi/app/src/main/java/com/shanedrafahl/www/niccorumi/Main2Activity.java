package com.shanedrafahl.www.niccorumi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {
private int dif=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        Button but = (Button) findViewById(R.id.start);
        if (but != null) {
            but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String message = "" + dif;
                    Intent intent = new Intent(Main2Activity.this, Game.class);
                    intent.setAction(message);
                    Main2Activity.this.startActivity(intent);

                }
            });
        }


    }
    public void easy(View v){
        this.dif=1;
        Button but =(Button) findViewById(R.id.easy);
        if(but!=null){
            but.setBackgroundColor(0xff000000);
        }
        Button but1 = (Button) findViewById(R.id.medium);
        if(but1!=null){
            but1.setBackgroundColor(0xffffffff);
        }
        Button but2 =(Button) findViewById(R.id.hard);
        if(but2!=null){
            but2.setBackgroundColor(0xffffffff);
        }

    }
    public void medium(View v){
        this.dif=2;
        Button but =(Button) findViewById(R.id.medium);
        if(but!=null){
            but.setBackgroundColor(0xff000000);
        }
        Button but1 = (Button) findViewById(R.id.hard);
        if(but1!=null){
            but1.setBackgroundColor(0xffffffff);
        }
        Button but2 =(Button) findViewById(R.id.easy);
        if(but2!=null){
            but2.setBackgroundColor(0xffffffff);
        }

    }
    public void hard(View v){
        this.dif=3;
        Button but =(Button) findViewById(R.id.hard);
        if(but!=null){
            but.setBackgroundColor(0xff000000);
        }
        Button but1 = (Button) findViewById(R.id.medium);
        if(but1!=null){
            but1.setBackgroundColor(0xffffffff);
        }
        Button but2 =(Button) findViewById(R.id.easy);
        if(but2!=null){
            but2.setBackgroundColor(0xffffffff);
        }

    }
}

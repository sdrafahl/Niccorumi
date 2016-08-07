package com.shanedrafahl.www.niccorumi;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class scoreScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_screen);
        Intent ints = getIntent();
        TextView vi = (TextView) findViewById(R.id.score);
        String send = ints.getAction();
        vi.setText(send);
    }
    public void reset(View v){
        //Intent inte = new Intent(scoreScreen.this,Main2Activity.class);
        //scoreScreen.this.startActivity(inte);
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

}

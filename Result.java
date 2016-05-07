package com.example.ankit.game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String no = intent.getStringExtra("value");
        int num = Integer.parseInt(no);

        TextView tv = (TextView) findViewById(R.id.win);
        if(num == 0)
            tv.setText("Match Draw Play next level");
        else
            tv.setText("Player "+num+" wins");
    }

    public void StartAgain (View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}

package com.example.ankit.game;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class play extends AppCompatActivity {

    int count=0,pressed=0,Max=1,p1=0,p2=0;
    ArrayList<Integer> Set = new ArrayList<Integer>();                //Array having indices for random selection
    final String zero = "0",one = "1",two = "2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        int index = 0;
        final int size;
        final ArrayList<Integer> exclude = new ArrayList<Integer>();    //Array having the nos. to exclude which ones are selected randomly
        final TextView value = (TextView) findViewById(R.id.fingers);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);//Getting the width of the screen to set the matrix
        int width = displaymetrics.widthPixels;

        Intent intent = getIntent();
        String no = intent.getStringExtra("number");        //Get the value from previous activity

        //Checking if 5 point touch is allowed or not
        PackageManager pm = this.getPackageManager();
        boolean hasMultitouch =
                pm.hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_JAZZHAND);
        if (hasMultitouch) {
            Max=2;
            value.setText("Max allowed fingers to each player is "+Max);
        }
        else{
            value.setText("Max allowed fingers to each player is "+Max);
        }

        //Creating the matrix dynamically
        TableLayout matrix = (TableLayout) findViewById(R.id.matrix);
        size = Integer.parseInt(no);
        for(int i=1; i<=size*size; i++)
            Set.add(i);
        final GradientDrawable gd = new GradientDrawable();
        gd.setColor(0);
        gd.setStroke(1, 0xFF000000);

        matrix.removeAllViews();
        for (int i = 0; i < size; i++) {
            TableRow Tr = new TableRow(this);   //Inserting new rows and Buttons
            Tr.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

            for (int j = 0; j < size; j++) {
                index++;
                Button b = new Button(this);
                TableRow.LayoutParams Tp = new TableRow.LayoutParams(new TableRow.LayoutParams((width / size)-size, TableRow.LayoutParams.WRAP_CONTENT));
                b.setLayoutParams(Tp);
                b.setId(index);
                b.setBackground(gd);
                Tr.addView(b);
                int btn_size = b.getLayoutParams().width;
                b.setLayoutParams(new TableRow.LayoutParams(btn_size, btn_size));
                int h = b.getId();
                Log.d("tr",""+h);
            }
            matrix.addView(Tr);
        }

        //Timer for conitnuous play
        final Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                final int i1 = generateRandom(size, exclude);
                Log.d("cnt", "" + i1);
                exclude.add(i1);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        p1 = p2 = 0;
                        pressed = 0;
                        final Button c = (Button) findViewById(i1);
                        if (count % 2 != 0)              //Highlighting the button to be pressed
                            c.setBackgroundColor(Color.parseColor("#ff1a1a"));
                        else
                            c.setBackgroundColor(Color.parseColor("#00b300"));
                        c.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                switch (event.getAction()) {
                                    case MotionEvent.ACTION_DOWN:
                                        pressed++;                      //if pressed
                                        Log.d("fst", "touched" + i1);
                                        return true;
                                    case MotionEvent.ACTION_UP:
                                        pressed--;                      //if released
                                        c.setOnTouchListener(null);
                                        ColorDrawable drawable = (ColorDrawable) v.getBackground();
                                        int col = drawable.getColor();
                                        Log.d("we", "" + col);
                                        if (col == -58854)
                                            p2++;
                                        else
                                            p1++;
                                        //Log.d("mn", p1 + " " + p2);      //Cancel the timer function as button release is detected
                                        t.cancel();
                                        return true;
                                }
                                return false;
                            }
                        });
                        //Disabling the buttons which are older than the max numbers
                        if (count >= 2 * Max) {
                            int i2 = exclude.get(count - 2 * Max);
                            Button c1 = (Button) findViewById(i2);
                            c1.setBackground(gd);
                            c1.setOnTouchListener(null);
                        }
                        //Checking if the button is still pressed
                        new CountDownTimer(1950, 1000) {
                            public void onFinish() {
                                Log.d("fin", "ished");
                                if (pressed <= 0) {         //not pressed
                                    t.cancel();
                                    c.setOnTouchListener(null);
                                    if (count % 2 != 0) {
                                        Intent i = new Intent(getApplicationContext(), Result.class);
                                        i.putExtra("value", two);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(i);
                                        return;
                                    } else {
                                        Intent i = new Intent(getApplicationContext(), Result.class);
                                        i.putExtra("value", one);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(i);
                                        return;
                                    }
                                }
                                if (p1 > 0) {       //if p1 releases one of the active button
                                    Intent i = new Intent(getApplicationContext(), Result.class);
                                    i.putExtra("value", two);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);
                                    Log.d("tch", "2 wins");
                                    return;
                                }
                                if (p2 > 0) {       //if p2 releases one of the active buttons
                                    Intent i = new Intent(getApplicationContext(), Result.class);
                                    i.putExtra("value", one);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);
                                    Log.d("tch", "1 wins");
                                }

                            }

                            public void onTick(long milsec) {
                            }
                        }.start();

                        count++;
                        //if no mistakes then match is declared drawn at max
                        if (count >= size * size) {
                            t.cancel();
                            new CountDownTimer(2000, 1000) {
                                public void onFinish() {
                                    Intent i = new Intent(getApplicationContext(), Result.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    i.putExtra("value", zero);
                                    startActivity(i);
                                }

                                public void onTick(long milsec) {
                                }
                            }.start();
                        }
                    }
                });
            }
        },500,2000);
    }

    //Generating random nos.
    public int generateRandom(int size, ArrayList<Integer> excludeRows) {
        Random rand = new Random();
        int range = Set.size();
        Log.d("rt",""+range);
        int random = rand.nextInt(range);
        int num = Set.get(random);
        Set.remove(random);
        return num;
    }


}

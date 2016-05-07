package com.example.ankit.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Spinner;

public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {

    String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Integer[] nums = {3, 4, 5, 6};                        //Options for Matrix Size
        Spinner spinner = (Spinner) findViewById(R.id.spin1);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<Integer> numadapter = new ArrayAdapter<Integer> (this, android.R.layout.simple_spinner_item, nums);
        numadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(numadapter);
    }
    //Start playing area
    public void StartPlay(View view){
        Intent intent = new Intent(this,play.class);
        intent.putExtra("number",item);
        startActivity(intent);
    }
    //Passing the selected value from spinner to next activity
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        item = "3";
    }
}

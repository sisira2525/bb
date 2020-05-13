package com.example.brainboggle3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class home_selection extends AppCompatActivity {

    private Button gotoquiz;
    private  Button qotogradebook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_selection);
        gotoquiz=findViewById(R.id.gotoquiz);
        qotogradebook=findViewById(R.id.qotogradebook);
        gotoquiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intt23= new Intent(home_selection.this, home.class);
                startActivity(intt23);
            }
        });
        qotogradebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intt33= new Intent(home_selection.this, course_selection_list.class);
                startActivity(intt33);
            }
        });

    }
}

package com.example.brainboggle3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class choose_s_r extends AppCompatActivity {
    private Button see_score;
    private  Button see_sol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_s_r);
        see_score=findViewById(R.id.see_score);
       see_sol=findViewById(R.id.see_sol);
        see_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intt23= new Intent(choose_s_r.this, see_score.class);
                startActivity(intt23);
            }
        });
        see_sol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intt33= new Intent(choose_s_r.this, sol_or_reports.class);
                startActivity(intt33);
            }
        });
    }
}

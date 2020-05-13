package com.example.brainboggle3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class see_score extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    String UserId11 ="";
    String email11="";
    String sem11="";
    String v="";
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences2;
    TextView display_score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_score);
        mAuth = FirebaseAuth.getInstance();
        UserId11 = mAuth.getCurrentUser().getUid();
        email11=mAuth.getCurrentUser().getEmail();
        display_score=findViewById(R.id.display_score);
        sharedPreferences=getSharedPreferences("course-clicked", Context.MODE_PRIVATE);
        String p = sharedPreferences.getString("course","NA");
        sharedPreferences2=getSharedPreferences("quiz-clicked",Context.MODE_PRIVATE);
        String o=sharedPreferences2.getString("quiz","NA");
        display_score=findViewById(R.id.display_score);


        db = FirebaseFirestore.getInstance();
        db.collection("student-registration").document(UserId11).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
        {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {

                sem11 = documentSnapshot.getString("semester");



            }
        });
        db.collection("course-by-sem").document("6").collection("courselist").document("15CSE311").collection("quizzes").document("quiz1").collection("student_marks").document(UserId11)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot)
                    {
                       v=documentSnapshot.getString("score");
                        Toast.makeText(see_score.this," scccccc"+v,Toast.LENGTH_LONG).show();
                        display_score.setText(""+v);

                    }
                });



    }
}

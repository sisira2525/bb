package com.example.brainboggle3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class score extends AppCompatActivity {

    TextView score1;
    SharedPreferences sharedPreferences4;
    SharedPreferences sharedPreferences7;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences2;
    SharedPreferences sharedPreferences55;
    Button done;
    String n="";
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    String UserId11 ="";
    String email11="";
    String sem11="";
    String batch11="";
    String mm="";
    TextView max;
    Map<String, Object> results = new HashMap<>();
    List<String> ite;
    private static final String TAG = "home";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        score1=findViewById(R.id.score11);
        sharedPreferences4=getSharedPreferences("score-cal",MODE_PRIVATE);
        n= Integer.toString(sharedPreferences4.getInt("scoree",0));
        score1.setText(n);
        sharedPreferences=getSharedPreferences("course-clicked", Context.MODE_PRIVATE);
        String p = sharedPreferences.getString("course","NA");
        sharedPreferences2=getSharedPreferences("quiz-clicked",Context.MODE_PRIVATE);
        String o=sharedPreferences2.getString("quiz","NA");
        //sharedPreferences7=getSharedPreferences("options-selected",MODE_PRIVATE);
        //String abcdString =sharedPreferences7.getString("abcd","");
        //String[] itemsabcd =abcdString.split(",");
        //ite =new ArrayList<String>();
        //for(int i=0;i<itemsabcd.length;i++)
        //{
            //ite.add(itemsabcd[i]);
        //}
       max=findViewById(R.id.max);
        sharedPreferences7=getSharedPreferences("options-selected",MODE_PRIVATE);

        String abcdString =sharedPreferences7.getString("abcd","");
        String[] itemsabcd =abcdString.split(",");
        ite =new ArrayList<String>();


        for(int j=0;j<itemsabcd.length;j++)
        {
            //results.put("Option_selected_Question"+(j+1),ite.get(j).toString());
            ite.add(itemsabcd[j]);
            results.put("Selected_option_"+(j+1),ite.get(j));
           // Toast.makeText(score.this," "+ite.get(j),Toast.LENGTH_LONG).show();
        }
        sharedPreferences55=getSharedPreferences("max-marks", Context.MODE_PRIVATE);
        mm=sharedPreferences55.getString("maxmarks","");
        //max.setText(mm);

        mAuth = FirebaseAuth.getInstance();
        UserId11 = mAuth.getCurrentUser().getUid();
        email11=mAuth.getCurrentUser().getEmail();


        db = FirebaseFirestore.getInstance();
        db.collection("student-registration").document(UserId11).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
        {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {

                sem11 = documentSnapshot.getString("semester");
                batch11=documentSnapshot.getString("Batch");


            }
        });
        results.put("score",n);
        results.put("student",email11);

        db.collection("course-by-sem").document("6").collection("courselist").document("15CSE311").collection("quizzes").document("quiz1").collection("student_marks").document(UserId11)
                .set(results, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });




        done=findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cc=new Intent(score.this,home_selection.class);
                startActivity(cc);
            }
        });


    }
}

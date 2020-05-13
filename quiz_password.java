package com.example.brainboggle3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class quiz_password extends AppCompatActivity
{
    private  EditText password2;
    public Button sign_in2;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    String UserId11 ="";
    String email11="";
    String sem11 ="";
    String y="";
    long timestampcurr=0;
    long tss=0;
    long tse=0;
    long td=0;
    String z=" ";
    boolean a=false;
    boolean sd;


    //Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    private static final String TAG = "home";
    SharedPreferences sharedPreferences1;
    SharedPreferences sharedPreferences2;
    SharedPreferences sharedPreferences3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quiz_password);
    password2=(EditText)findViewById(R.id.password2);
    sign_in2=(Button)findViewById(R.id.sign_in2);

    mAuth = FirebaseAuth.getInstance();
    UserId11 = mAuth.getCurrentUser().getUid();
    email11=mAuth.getCurrentUser().getEmail();
    sharedPreferences1=getSharedPreferences("course-clicked", Context.MODE_PRIVATE);
    String d = sharedPreferences1.getString("course","NA");

    sharedPreferences2=getSharedPreferences("quiz-clicked",Context.MODE_PRIVATE);
    String e=sharedPreferences2.getString("quiz","NA");
    db = FirebaseFirestore.getInstance();


        db.collection("student-registration").document(UserId11).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
    {
        @Override
        public void onSuccess(DocumentSnapshot documentSnapshot)
        {
            sem11 = documentSnapshot.getString("semester");

        }
    });

        DocumentReference docRef=db.collection("course-by-sem").document("6").collection("courselist").document("15CSE311").collection("quizzes").document("quiz1").collection("student_marks").document(UserId11);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists())
                {
                    Toast.makeText(quiz_password.this,"already attempted",Toast.LENGTH_SHORT).show();
                    sign_in2.setEnabled(false);
                } else
                {

                }
            }


        });
        db.collection("course-by-sem").document("6").collection("courselist").document(d).collection("quizzes").document(e).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
    {

            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {
                y=documentSnapshot.getString("Password");
                Timestamp ts =documentSnapshot.getTimestamp("StartTime");
                Timestamp ts1=documentSnapshot.getTimestamp("EndTime");
                //sd=documentSnapshot.getBoolean("Attempted");
                tss=ts.getSeconds();
                tse=ts1.getSeconds();
                td=(tse-tss)*1000;

                    Toast.makeText(quiz_password.this, "" + td, Toast.LENGTH_LONG).show();
                    sharedPreferences3 = getSharedPreferences("time-duration", MODE_PRIVATE);
                    SharedPreferences.Editor edit = sharedPreferences3.edit();
                    edit.putLong("tid", td);
                    edit.apply();




            }
    });

        sign_in2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                z=password2.getText().toString();
                a=z.equals(y);



                    if (a == true)//ADD TIMESTAMP CONSTRAINT
                    {
                        timestampcurr = System.currentTimeMillis() * 1000;
                        // if((timestampcurr>=tss))
                        //{
                        Intent intt00 = new Intent(quiz_password.this, attempt_quiz.class);
                        startActivity(intt00);
                        //}
                        //else
                        //  {

                        // Toast.makeText(quiz_password.this, "Quiz has not yet started", Toast.LENGTH_LONG).show();
                        //}


                    } else {
                        Toast.makeText(quiz_password.this, "incorrect password", Toast.LENGTH_LONG).show();
                    }


            }
        });





}
}

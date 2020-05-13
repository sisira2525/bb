package com.example.brainboggle3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
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
import java.util.List;

public class sol_or_reports extends AppCompatActivity {

    private RecyclerView recyclerView55;
    private RecyclerView.Adapter  adapter55;
    private List<ListItem_sol> listItems1;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    String UserId11 ="";
    String email11="";
    String sem11 ="";
    String gh="";
    SharedPreferences sharedPreferences;
    private static final String TAG = "home";
    SharedPreferences sharedPreferences7;
    List<String> ops;
    int u=0;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sol_or_reports);
        recyclerView55=(RecyclerView)findViewById(R.id.recyclerview55);
        recyclerView55.setHasFixedSize(true);
        recyclerView55.setLayoutManager(new LinearLayoutManager(this));
        mAuth = FirebaseAuth.getInstance();
        listItems1 = new ArrayList<ListItem_sol>();
        UserId11 = mAuth.getCurrentUser().getUid();
        email11=mAuth.getCurrentUser().getEmail();
        sharedPreferences=getSharedPreferences("course-clicked", Context.MODE_PRIVATE);
        String c = sharedPreferences.getString("course","NA");
        ops=new ArrayList<>();

        //Toast.makeText(quizlist.this, "reached quizlist class", Toast.LENGTH_LONG).show();
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
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        gh=documentSnapshot.getString("score");
                    }
                });





                        db.collection("course-by-sem").document("6").collection("courselist").document("15CSE311").collection("quizzes").document("quiz1").collection("questions")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            int i=0;
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Log.d(TAG, document.getId() + " => " + document.getData());

                                                String q = document.getString("Question");
                                                String co = document.getString("Correct");
                                                String aop = document.getString("OptionA");
                                                String bop = document.getString("OptionB");
                                                String cop = document.getString("OptionC");
                                                String dop = document.getString("OptionD");
                                                //String w =ops.get(i);
                                                //i=i+1;

                                                listItems1.add(new ListItem_sol(q,aop,bop,cop,dop,co,gh));


                                                Log.d(TAG,"size noo");

                                            }
                                            //Toast.makeText(attempt_quiz.this, "yess quizz"+list.size(), Toast.LENGTH_LONG).show();
                                            adapter55= new MyAdapter_sol(listItems1,getApplicationContext());
                                            recyclerView55.setAdapter(adapter55);

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });


                    }





    }



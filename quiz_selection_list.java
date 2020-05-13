package com.example.brainboggle3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class quiz_selection_list extends AppCompatActivity {
    private RecyclerView recyclerView99;
    private RecyclerView.Adapter  adapter11;
    private List<ListItem1> listItems1;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    String UserId11 ="";
    String email11="";
    String sem11 ="";
    private static final String TAG = "home";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_selection_list);
        recyclerView99 = (RecyclerView) findViewById(R.id.recyclerview99);
        recyclerView99.setHasFixedSize(true);
        recyclerView99.setLayoutManager(new LinearLayoutManager(this));
        mAuth = FirebaseAuth.getInstance();
        listItems1 = new ArrayList<ListItem1>();
        UserId11 = mAuth.getCurrentUser().getUid();
        email11 = mAuth.getCurrentUser().getEmail();
        sharedPreferences = getSharedPreferences("course-clicked", Context.MODE_PRIVATE);
        String c = sharedPreferences.getString("course", "NA");

        //Toast.makeText(quizlist.this, "reached quizlist class", Toast.LENGTH_LONG).show();
        db = FirebaseFirestore.getInstance();
        db.collection("student-registration").document(UserId11).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                sem11 = documentSnapshot.getString("semester");

            }
        });


        //MAKE 6 SEM11 LATER.
        db.collection("course-by-sem").document("6").collection("courselist").document(c).collection("quizzes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                String id1 = document.getId();
                                //Boolean h=document.getBoolean("Attempted");


                                    ListItem1 item1 = new ListItem1(id1);
                                    listItems1.add(item1);

                                //Toast.makeText(quizlist.this, "yess quizz"+id1, Toast.LENGTH_LONG).show();

                            }
                            adapter11 = new MyAdapter1_selection(listItems1, getApplicationContext());
                            recyclerView99.setAdapter(adapter11);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    }


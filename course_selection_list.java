package com.example.brainboggle3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class course_selection_list extends AppCompatActivity {


    private RecyclerView recyclerView98;
    private RecyclerView.Adapter  adapter;
    private List<ListItem> listItems;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    String UserId ="";
    String email1="";
    String sem ="";
    private static final String TAG = "home";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_selection_list);
        recyclerView98=(RecyclerView)findViewById(R.id.recyclerview98);
        recyclerView98.setHasFixedSize(true);
        recyclerView98.setLayoutManager(new LinearLayoutManager(this));
        listItems = new ArrayList<ListItem>();
        //email = (TextView)findViewById(R.id.email_txt);
        mAuth = FirebaseAuth.getInstance();
        UserId = mAuth.getCurrentUser().getUid();
        email1=mAuth.getCurrentUser().getEmail();

        db = FirebaseFirestore.getInstance();
        db.collection("student-registration").document(UserId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
        {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {
                sem = documentSnapshot.getString("semester");

            }
        });

        db.collection("course-by-sem").document("6").collection("courselist")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if (task.isSuccessful())
                        {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                String id=document.getId();
                                ListItem item = new ListItem(id);
                                listItems.add(item);
                                //Toast.makeText(home.this, "yess"+id, Toast.LENGTH_SHORT).show();



                            }
                            adapter= new MyAdapter_selection(listItems,getApplicationContext());
                            recyclerView98.setAdapter(adapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });



    }


}

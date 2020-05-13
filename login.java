package com.example.brainboggle3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    private EditText email1;
    private EditText password1;
    private Button   sign_in1;
    private FirebaseAuth firebaseAuth1;
    private static final String TAG = "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email1=findViewById(R.id.email1);
        password1=findViewById(R.id.password1);
        sign_in1=findViewById(R.id.sign_in1);
        firebaseAuth1=FirebaseAuth.getInstance();

        sign_in1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogin();
                Intent i1 = new Intent(login.this, home_selection.class);
                startActivity(i1);
            }
        });

    }

    private void performLogin() {
        final String m1 = email1.getText().toString().trim();
        final String p1 = password1.getText().toString().trim();
        if (!TextUtils.isEmpty(m1) && !TextUtils.isEmpty(p1) ) {

            firebaseAuth1.signInWithEmailAndPassword(m1, p1)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "login success");
                            } else {
                                Log.e(TAG, "Login fail", task.getException());
                                Toast.makeText(login.this,
                                        "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                    });

        }
    }
}

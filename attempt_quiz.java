package com.example.brainboggle3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

public class attempt_quiz extends AppCompatActivity {

    private Button opa,opb,opc,opd,next,submit;
    private LinearLayout llq,option_container;
    private TextView question,question_index,timer;

    List<QuestionModel> list1;
    private  int count=0;
    private  int position=0;
    private  int score=0;
    private  int scorefinal=0;

    List<QuestionModel> l;
    List<String> abcd ;

    private FirebaseUser user;
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    String UserId11 ="";
    String email11="";
    String sem11 ="";
    String mm11="";
    private static final String TAG = "home";
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences2;
    SharedPreferences sharedPreferences3;
    SharedPreferences sharedPreferences4;
    SharedPreferences sharedPreferences7;
    SharedPreferences sharedPreferences55;




    private CountDownTimer mCountDownTimer;
    private static  long START_TIME_IN_MILLIS = 0;
    private long mTimeLeftInMillis = 0;





    @Override
    protected void onCreate(Bundle savedInstanceState)
    {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attempt_quiz);
        opa=(Button)findViewById(R.id.opa);
        opb=(Button)findViewById(R.id.opb);
        opc=(Button)findViewById(R.id.opc);
        opd=(Button)findViewById(R.id.opd);
        next=(Button)findViewById(R.id.next);
        submit=(Button)findViewById(R.id.submit);
        question=(TextView)findViewById(R.id.question);
        question_index=(TextView)findViewById(R.id.question_index);
        timer=(TextView)findViewById(R.id.timer);


        option_container=findViewById(R.id.option_container);
        llq=findViewById(R.id.llq);
        list1=new ArrayList<>();
        l=new ArrayList<>();
        abcd=new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        UserId11 = mAuth.getCurrentUser().getUid();
        email11=mAuth.getCurrentUser().getEmail();
        sharedPreferences=getSharedPreferences("course-clicked", Context.MODE_PRIVATE);
        String p = sharedPreferences.getString("course","NA");
        sharedPreferences2=getSharedPreferences("quiz-clicked",Context.MODE_PRIVATE);
        String o=sharedPreferences2.getString("quiz","NA");
        sharedPreferences3=getSharedPreferences("time-duration",Context.MODE_PRIVATE);
        long e=sharedPreferences3.getLong("tid",0);
        START_TIME_IN_MILLIS=e;
        mTimeLeftInMillis =START_TIME_IN_MILLIS;
        Toast.makeText(attempt_quiz.this, ""+e, Toast.LENGTH_LONG).show();



        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished)
            {

                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();

            }

            private void updateCountDownText()
            {
                int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
                int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

                String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

                timer.setText("time left:"+timeLeftFormatted);
            }

            @Override
            public void onFinish()
            {
                Submit();
            }
        }.start();






        db = FirebaseFirestore.getInstance();
        db.collection("student-registration").document(UserId11).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
        {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {

                sem11 = documentSnapshot.getString("semester");


            }
        });





        {

            db.collection("course-by-sem").document("6").collection("courselist").document("15CSE311").collection("quizzes").document("quiz1").collection("questions")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());

                                    String q = document.getString("Question");
                                    String co = document.getString("Correct");
                                    String aop = document.getString("OptionA");
                                    String bop = document.getString("OptionB");
                                    String cop = document.getString("OptionC");
                                    String dop = document.getString("OptionD");

                                    int   po =Integer.parseInt(document.getString("Points"));

                                    list1.add(new QuestionModel(q, aop, bop, cop, dop, co,po));


                                    Log.d(TAG,"size noo");
                                    playAnim(question, 0, list1.get(position).getQuestion());
                                }
                                //Toast.makeText(attempt_quiz.this, "yess quizz"+list.size(), Toast.LENGTH_LONG).show();
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
        db.collection("course-by-sem").document("6").collection("courselist").document("15CSE311").collection("quizzes").document("quiz1")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        mm11=documentSnapshot.getString("Maxmarks");
                    }
                });

        sharedPreferences55=getSharedPreferences("max-marks", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences3.edit();
        edit.putString("maxmarks", mm11);
        edit.apply();



        for( int i=0;i<4;i++)//setting listner for all option buttons and calling check answer function
        {
            option_container.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                   abcd.add(((Button)v).getText().toString());
                    checkAnswer(((Button)v).getText().toString());
                }
            });


        }
        //Toast.makeText(attempt_quiz.this,"bbb"+scorefinal, LENGTH_LONG).show();










        next.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                enableoption(true);
                next.setEnabled(false);
                next.setAlpha(0.7f);
                //Toast.makeText(attempt_quiz.this, "qqposition"+position, Toast.LENGTH_LONG).show();
                position++;


                count=0;
                    //Toast.makeText(attempt_quiz.this, "wwposition"+position, Toast.LENGTH_LONG).show();
                    playAnim(question, 0, list1.get(position).getQuestion());


            }
        });


    submit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


                Submit();



        }
    });
    }




    private void playAnim(final View view, final int value, final String data)
    {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {


            @Override
            public void onAnimationStart(Animator animation)
            {



                if ((value == 0)&&(count<4))
                {
                    String option=" ";
                    if (count == 0)
                    {
                        option = list1.get(position).getOptionA();
                    } else if (count == 1)
                    {
                        option = list1.get(position).getOptionB();
                    } else if (count == 2)
                    {
                        option = list1.get(position).getOptionC();
                    } else if (count == 3)
                    {
                        option = list1.get(position).getOptionD();
                    }

                    playAnim(option_container.getChildAt(count), 0, option);
                    count++;
                }
            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                if((value==0))
                {
                    try{
                        ((TextView)view).setText(data);
                    }
                    catch (ClassCastException ex)
                    {
                        ((Button)view).setText(data);
                    }
                    playAnim(view,1,data);

                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }




    private void checkAnswer(String selectedoption)
    {

         enableoption(false);




        if(position==(list1.size()-1))
        {
            next.setVisibility(View.INVISIBLE);
            submit.setVisibility(View.VISIBLE);
            submit.setEnabled(true);
            submit.setAlpha(1);
        }

        else
        {
            next.setEnabled(true);
            next.setAlpha(1);

        }










       if(selectedoption.equals(list1.get(position).getCorrectAns()))      /////do mark calculation
      {

         int h=list1.get(position).getPoints();
         score =score+h;

             Toast.makeText(attempt_quiz.this,"scc"+score,LENGTH_LONG).show();
             sharedPreferences4=getSharedPreferences("score-cal",MODE_PRIVATE);
             SharedPreferences.Editor edit1 = sharedPreferences4.edit();
             edit1.putInt("scoree",score);
             edit1.apply();



      }
     else
         {
             Toast.makeText(attempt_quiz.this,"nooo correct"+list1.get(position).getCorrectAns(),LENGTH_SHORT).show();

         }

    }



    private void enableoption(boolean enable) //disables other options after one is clicked
    {
        for(int i=0;i<4;i++)
        {
            option_container.getChildAt(i).setEnabled(enable);

        }
    }


    private void Submit()
    {
        StringBuilder stringBuilder =new StringBuilder();
        for(String s:abcd)
       {
            stringBuilder.append(s);
            stringBuilder.append(",");
        }
        sharedPreferences7=getSharedPreferences("options-selected",MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences7.edit();
        edit.putString("abcd",stringBuilder.toString() );
        edit.apply();

        //db.collection("cities").document("DC").update("Attempted",true);
        Intent ghh= new Intent(attempt_quiz.this,score.class);
        startActivity(ghh);

    }

}

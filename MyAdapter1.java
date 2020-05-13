package com.example.brainboggle3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class MyAdapter1  extends RecyclerView.Adapter<MyAdapter1.ViewHolder >
{
    private List<ListItem1> listItems1;
    private Context context;

    public MyAdapter1(List<ListItem1> listItems1, Context context)
    {
        this.listItems1 = listItems1;
        this.context=context;
    }


    @NonNull
    @Override
    public MyAdapter1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate((R.layout.list_item1), parent,false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull MyAdapter1.ViewHolder holder, int position) {
        final ListItem1 listItem1 =listItems1.get(position);
        holder.quizzes.setText(listItem1.getQuiz());
        holder.linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SharedPreferences sp;
                sp=context.getSharedPreferences("quiz-clicked",context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("quiz",listItem1.getQuiz());
                edit.apply();
                //Toast.makeText(context,"you clicked11111",Toast.LENGTH_SHORT).show();
                Intent in16 = new Intent(context.getApplicationContext(),quiz_password.class);
                in16.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(in16);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems1.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView quizzes;
        public LinearLayout linearLayout1;

        public ViewHolder(@NonNull View itemView)
        {  super(itemView);

            quizzes=(TextView)itemView. findViewById(R.id.quizzes);
            linearLayout1=(LinearLayout)itemView.findViewById(R.id.linearLayout1);

        }
    }
}


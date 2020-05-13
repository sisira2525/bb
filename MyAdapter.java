package com.example.brainboggle3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class MyAdapter  extends RecyclerView.Adapter<MyAdapter.ViewHolder >
{
     private List<ListItem> listItems;
     private Context  context;



    public MyAdapter(List<ListItem> listItems, Context context)
    {
        this.listItems = listItems;
        this.context=context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v= LayoutInflater.from(parent.getContext())
                .inflate((R.layout.list_item), parent,false);

        return new ViewHolder(v);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
       final ListItem listItem =listItems.get(position);

      holder.info_text.setText(listItem.getCourse());
      holder.linearLayout.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v)
          {
              SharedPreferences sp;
              sp=context.getSharedPreferences("course-clicked",context.MODE_PRIVATE);
              SharedPreferences.Editor edit = sp.edit();
              edit.putString("course",listItem.getCourse());
              edit.apply();
              //Toast.makeText(context,"you clicked course",Toast.LENGTH_LONG).show();
              Intent in78 = new Intent(context.getApplicationContext(),quizlist.class);
              in78.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              context.startActivity(in78);

          }
      });
    }

    @Override
    public int getItemCount() {
        return listItems.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView info_text;
        public LinearLayout linearLayout;


        public ViewHolder(@NonNull View itemView)
         {  super(itemView);
             info_text=(TextView)itemView.findViewById(R.id.info_text);
             linearLayout=(LinearLayout)itemView.findViewById(R.id.linearLayout);


        }
    }
}

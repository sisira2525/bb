package com.example.brainboggle3;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter_selection  extends RecyclerView.Adapter<MyAdapter_selection.ViewHolder >
{
    private List<ListItem> listItems;
    private Context  context;



    public MyAdapter_selection(List<ListItem> listItems, Context context)
    {
        this.listItems = listItems;
        this.context=context;

    }

    @NonNull
    @Override
    public MyAdapter_selection.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v= LayoutInflater.from(parent.getContext())
                .inflate((R.layout.selection_item), parent,false);

       return new MyAdapter_selection.ViewHolder(v);


    }


    public void onBindViewHolder(@NonNull MyAdapter_selection.ViewHolder holder, int position) {
        final ListItem listItem =listItems.get(position);

        holder.selec_text.setText(listItem.getCourse());
        holder.linearLayout98.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SharedPreferences sp11;
                sp11=context.getSharedPreferences("course-selected",context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sp11.edit();
                edit.putString("course",listItem.getCourse());
                edit.apply();
                //Toast.makeText(context,"you clicked course",Toast.LENGTH_LONG).show();
                Intent in88 = new Intent(context.getApplicationContext(),quiz_selection_list.class);

                in88.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(in88);

            }
        });
    }



    @Override
    public int getItemCount() {
        return listItems.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView selec_text;
        public LinearLayout linearLayout98;


        public ViewHolder(@NonNull View itemView)
        {  super(itemView);
            selec_text=(TextView)itemView.findViewById(R.id.selec_text);
            linearLayout98=(LinearLayout)itemView.findViewById(R.id.linearLayout98);


        }
    }
}


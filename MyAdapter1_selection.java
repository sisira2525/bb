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

public class MyAdapter1_selection  extends RecyclerView.Adapter<MyAdapter1_selection.ViewHolder >
{
    private List<ListItem1> listItems1;
    private Context context;

    public MyAdapter1_selection(List<ListItem1> listItems1, Context context)
    {
        this.listItems1 = listItems1;
        this.context=context;
    }


    @NonNull
    @Override
    public MyAdapter1_selection.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate((R.layout.selection_item1), parent,false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull MyAdapter1_selection.ViewHolder holder, int position) {
        final ListItem1 listItem1 =listItems1.get(position);
        holder.selec_qu_text.setText(listItem1.getQuiz());
        holder.linearLayout99.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SharedPreferences sp67;
                sp67=context.getSharedPreferences("quiz-clicked",context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sp67.edit();
                edit.putString("quiz",listItem1.getQuiz());
                edit.apply();
                //Toast.makeText(context,"you clicked11111",Toast.LENGTH_SHORT).show();
                Intent in1 = new Intent(context.getApplicationContext(),choose_s_r.class);
                in1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(in1);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems1.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView selec_qu_text;
        public LinearLayout linearLayout99

                ;

        public ViewHolder(@NonNull View itemView)
        {  super(itemView);

            selec_qu_text=(TextView)itemView. findViewById(R.id.selec_qu_text);
            linearLayout99=(LinearLayout)itemView.findViewById(R.id.linearLayout99);

        }
    }
}


package com.aryomtech.plasmaz.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aryomtech.plasmaz.Model.AssignmentData;
import com.aryomtech.plasmaz.R;

import java.util.ArrayList;


public class ExamsAdapterClass extends RecyclerView.Adapter<ExamsAdapterClass.MyViewHolderblood>{

    ArrayList<AssignmentData> list;
    Context context;

    public ExamsAdapterClass(ArrayList<AssignmentData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolderblood onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.content_of_exams,parent,false);
        return new MyViewHolderblood(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderblood holder, int position) {

        holder.subject.setText(list.get(position).getSubject());
        holder.chapter.setText(list.get(position).getChapter());
        holder.date.setText(list.get(position).getDate());
        holder.teacher.setText(list.get(position).getTeacher());

        holder.mainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = list.get(position).link;
                Uri uriUrl = Uri.parse(url);
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                context.startActivity(launchBrowser);
            }
        });

        int rand=position;

        if(rand==8){
            holder.mainlayout.setBackgroundResource(R.drawable.as_one);
        }
        if(rand==7){
            holder.mainlayout.setBackgroundResource(R.drawable.as_two);
        }
        if(rand==6){
            holder.mainlayout.setBackgroundResource(R.drawable.as_three);
        }
        if(rand==5){
            holder.mainlayout.setBackgroundResource(R.drawable.as_four);
        }
        if(rand==4){
            holder.mainlayout.setBackgroundResource(R.drawable.as_five);
        }
        if(rand==3){
            holder.mainlayout.setBackgroundResource(R.drawable.as_six);
        }
        if(rand==2){
            holder.mainlayout.setBackgroundResource(R.drawable.as_seven);
        }
        if(rand==1){
            holder.mainlayout.setBackgroundResource(R.drawable.as_eight);
        }
        if(rand>=9 && position%2==0){
            holder.mainlayout.setBackgroundResource(R.drawable.as_six);
        }
        if(rand>=9 && position%3==0){
            holder.mainlayout.setBackgroundResource(R.drawable.as_four);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolderblood extends RecyclerView.ViewHolder {

        TextView subject;
        TextView chapter;
        TextView date;
        TextView teacher;
        LinearLayout mainlayout;

        public MyViewHolderblood(@NonNull View itemView) {
            super(itemView);

            subject=itemView.findViewById(R.id.textView9);
            chapter=itemView.findViewById(R.id.textView3);
            date=itemView.findViewById(R.id.textView6);
            teacher=itemView.findViewById(R.id.textView10);
            mainlayout=itemView.findViewById(R.id.mainlayout);

        }
    }
}

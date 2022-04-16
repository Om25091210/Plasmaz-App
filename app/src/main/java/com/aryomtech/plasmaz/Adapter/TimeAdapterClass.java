package com.aryomtech.plasmaz.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aryomtech.plasmaz.Model.TimeData;
import com.aryomtech.plasmaz.R;
import com.aryomtech.plasmaz.WriteTimeline;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class TimeAdapterClass extends RecyclerView.Adapter<TimeAdapterClass.MyViewHolderblood>{

    ArrayList<TimeData> list;
    Context context;
    private FirebaseAuth mAuth;
    static FirebaseUser useruuid;
    ArrayList<String> teachers_list;

    public TimeAdapterClass(ArrayList<TimeData> list, Context context ,ArrayList<String> teachers_list) {
        this.list = list;
        this.teachers_list=teachers_list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolderblood onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.content_time_line,parent,false);
        return new MyViewHolderblood(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderblood holder, int position) {

        mAuth = FirebaseAuth.getInstance();
        useruuid = mAuth.getCurrentUser();

        holder.chapter.setText(list.get(position).getText());
        holder.date.setText(list.get(position).getDate());

        holder.mainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(teachers_list.contains(useruuid.getUid())) {
                    Intent launchBrowser = new Intent(context, WriteTimeline.class);
                    launchBrowser.putExtra("key_push",list.get(position).getKey());
                    launchBrowser.putExtra("text_push",list.get(position).getText());
                    context.startActivity(launchBrowser);

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolderblood extends RecyclerView.ViewHolder {

        TextView chapter;
        TextView date;
        LinearLayout mainlayout;


        public MyViewHolderblood(@NonNull View itemView) {
            super(itemView);


            chapter=itemView.findViewById(R.id.textView3);
            date=itemView.findViewById(R.id.textView6);
            mainlayout=itemView.findViewById(R.id.mainlayout);


        }
    }
}

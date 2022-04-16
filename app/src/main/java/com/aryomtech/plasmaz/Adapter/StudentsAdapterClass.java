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
import com.aryomtech.plasmaz.Model.studData;
import com.aryomtech.plasmaz.PdfSend;
import com.aryomtech.plasmaz.Profilesec;
import com.aryomtech.plasmaz.R;
import com.aryomtech.plasmaz.Students;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.ArrayList;


public class StudentsAdapterClass extends RecyclerView.Adapter<StudentsAdapterClass.MyViewHolderblood>{

    ArrayList<studData> list;
    Context context;

    public StudentsAdapterClass(ArrayList<studData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolderblood onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.content_of_student,parent,false);
        return new MyViewHolderblood(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderblood holder, int position) {

        holder.subject.setText(list.get(position).getName());
        holder.chapter.setText(list.get(position).getClassroom());


        holder.mainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent send=new Intent(context, PdfSend.class);
                send.putExtra("sending_UId",list.get(position).getUid());
                context.startActivity(send);
                Animatoo.animateZoom(context);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolderblood extends RecyclerView.ViewHolder {

        TextView subject;
        TextView chapter;
        LinearLayout mainlayout;

        public MyViewHolderblood(@NonNull View itemView) {
            super(itemView);

            subject=itemView.findViewById(R.id.textView9);
            chapter=itemView.findViewById(R.id.textView3);
            mainlayout=itemView.findViewById(R.id.mainlayout);

        }
    }
}


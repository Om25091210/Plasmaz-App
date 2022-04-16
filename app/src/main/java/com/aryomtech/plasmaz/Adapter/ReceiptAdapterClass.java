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
import com.aryomtech.plasmaz.Model.feesData;
import com.aryomtech.plasmaz.R;

import java.util.ArrayList;

public class ReceiptAdapterClass extends RecyclerView.Adapter<ReceiptAdapterClass.MyViewHolderblood>{

    ArrayList<feesData> list;
    Context context;

    public ReceiptAdapterClass(ArrayList<feesData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolderblood onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.content_of_rceipt,parent,false);
        return new MyViewHolderblood(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderblood holder, int position) {

        holder.subject.setText(list.get(position).getDate());
        holder.chapter.setText(list.get(position).getNote());

        holder.mainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = list.get(position).getLink();
                Uri uriUrl = Uri.parse(url);
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                context.startActivity(launchBrowser);
            }
        });

        int rand=position;

        if(rand==1){
            holder.mainlayout.setBackgroundResource(R.drawable.as_one);
        }
        if(rand==2){
            holder.mainlayout.setBackgroundResource(R.drawable.as_two);
        }
        if(rand==3){
            holder.mainlayout.setBackgroundResource(R.drawable.as_three);
        }
        if(rand==4){
            holder.mainlayout.setBackgroundResource(R.drawable.as_four);
        }
        if(rand==5){
            holder.mainlayout.setBackgroundResource(R.drawable.as_five);
        }
        if(rand==6){
            holder.mainlayout.setBackgroundResource(R.drawable.as_six);
        }
        if(rand==7){
            holder.mainlayout.setBackgroundResource(R.drawable.as_seven);
        }
        if(rand==8){
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
        LinearLayout mainlayout;

        public MyViewHolderblood(@NonNull View itemView) {
            super(itemView);

            subject=itemView.findViewById(R.id.textView9);
            chapter=itemView.findViewById(R.id.textView3);
            mainlayout=itemView.findViewById(R.id.mainlayout);

        }
    }
}

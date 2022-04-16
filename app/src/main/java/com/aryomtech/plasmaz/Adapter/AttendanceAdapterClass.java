package com.aryomtech.plasmaz.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.aryomtech.plasmaz.Model.five_am_data;
import com.aryomtech.plasmaz.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AttendanceAdapterClass extends RecyclerView.Adapter<AttendanceAdapterClass.MyViewHolderblood> {

    ArrayList<five_am_data> list;
    ArrayList<String> labels;
    ArrayList<String> students_list;
    ArrayList<String> teachers_list;
    Context context;
    String date;
    private FirebaseAuth mAuth;
    static FirebaseUser useruuid;
    DatabaseReference attendance_ref;



    public AttendanceAdapterClass(ArrayList<five_am_data> list, Context context, String date_on_longpress, ArrayList<String> teachers_list, ArrayList<String> students_list) {
        this.list = list;
        this.context = context;
        this.labels=labels;
        this.date=date_on_longpress;
        this.teachers_list=teachers_list;
        this.students_list=students_list;
    }

    @NonNull
    @Override
    public AttendanceAdapterClass.MyViewHolderblood onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_attendance, parent, false);
        return new AttendanceAdapterClass.MyViewHolderblood(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AttendanceAdapterClass.MyViewHolderblood holder, int position) {

        mAuth = FirebaseAuth.getInstance();
        useruuid = mAuth.getCurrentUser();

        holder.textView21.setVisibility(View.GONE);

        holder.name.setText(list.get(position).getName());
        attendance_ref=FirebaseDatabase.getInstance().getReference().child("Attendance");




        if(teachers_list.contains(useruuid.getUid())) {

            if(students_list.contains(list.get(position).getUid())){

                holder.mark.setText("ABSENT");
                holder.mark.setTextColor(Color.parseColor("#FFFFFF"));
                holder.mark.setBackgroundResource(R.drawable.grad_five);
            }
            else {

                holder.mark.setText("MARK");
                holder.mark.setTextColor(Color.parseColor("#000000"));
                holder.mark.setBackgroundResource(R.drawable.grad_twenty_one);
            }
        }

        try{
            Glide.with(context).load(list.get(position).getDp()).placeholder(R.drawable.ic_plasmaz_app_5_am__2_)
                    .error(R.drawable.ic_plasmaz_app_5_am__2_)
                    .dontAnimate()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .thumbnail(0.5f)
                    .into(holder.circleImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(teachers_list.contains(useruuid.getUid())) {

            holder.mark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(holder.mark.getText().toString().equals("ABSENT")){
                        holder.mark.setText("MARK");
                        holder.mark.setTextColor(Color.parseColor("#000000"));
                        holder.mark.setBackgroundResource(R.drawable.grad_twenty_one);
                        students_list.clear();
                        attendance_ref.child(date).child(list.get(position).getUid()).removeValue();
                    }
                    else if (holder.mark.getText().toString().equals("MARK")){

                        holder.mark.setText("ABSENT");
                        holder.mark.setTextColor(Color.parseColor("#FFFFFF"));
                        students_list.clear();
                        holder.mark.setBackgroundResource(R.drawable.grad_five);
                        attendance_ref.child(date).child(list.get(position).getUid()).setValue(list.get(position).getUid());
                    }
                }
            });
        }
        else{
            holder.textView21.setVisibility(View.VISIBLE);
            holder.mark.setVisibility(View.GONE);

            if(students_list.contains(list.get(position).getUid())){
                Log.e("verfor","verfor");
                holder.textView21.setText("Absent");
                holder.textView21.setBackgroundResource(R.drawable.absent_bg);
            }
            else {
                Log.e("verelse","verelse");
                holder.textView21.setText("Present");
                holder.textView21.setBackgroundResource(R.drawable.present_bg);
            }

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolderblood extends RecyclerView.ViewHolder {

        TextView name,textView21;
        CircleImageView circleImageView;
        Button mark;


        public MyViewHolderblood(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.textView9);
            textView21=itemView.findViewById(R.id.textView21);
            circleImageView=itemView.findViewById(R.id.circleImageView);
            mark=itemView.findViewById(R.id.button7);


        }
    }
}

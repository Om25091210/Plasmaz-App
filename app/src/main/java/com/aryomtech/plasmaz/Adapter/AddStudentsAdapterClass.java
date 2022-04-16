package com.aryomtech.plasmaz.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.Base64;
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
import com.bernaferrari.emojislider.EmojiSlider;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public class AddStudentsAdapterClass extends RecyclerView.Adapter<AddStudentsAdapterClass.MyViewHolderblood> {

    ArrayList<five_am_data> list;
    ArrayList<String> pic;
    Context context;

    public AddStudentsAdapterClass(ArrayList<five_am_data> list, Context context, ArrayList<String> pic) {
        this.list = list;
        this.context = context;
        this.pic=pic;
    }

    @NonNull
    @Override
    public AddStudentsAdapterClass.MyViewHolderblood onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_edit_attendance, parent, false);
        return new AddStudentsAdapterClass.MyViewHolderblood(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AddStudentsAdapterClass.MyViewHolderblood holder, int position) {

        holder.name.setText(list.get(position).getName());
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


        if(!holder.add.getText().equals("Added")) {
            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference sheet_ref = FirebaseDatabase.getInstance().getReference().child("sheet");
                    sheet_ref.child(list.get(position).getUid()).child("name").setValue(list.get(position).getName());
                    sheet_ref.child(list.get(position).getUid()).child("dp").setValue(list.get(position).getDp());
                    sheet_ref.child(list.get(position).getUid()).child("uid").setValue(list.get(position).getUid());
                    holder.add.setText("Added");
                    holder.add.setBackgroundResource(R.drawable.grad_nineteen);
                    holder.add.setTextColor(Color.parseColor("#FFFFFF"));
                }
            });
        }
        else{
            Log.e("Already added","Already added");
        }
        if(pic.contains(list.get(position).getDp())){
            holder.add.setText("Added");
            holder.add.setBackgroundResource(R.drawable.grad_nineteen);
            holder.add.setTextColor(Color.parseColor("#FFFFFF"));
        }else {
            holder.add.setText("Add");
            holder.add.setBackgroundResource(R.drawable.button_bg);
            holder.add.setTextColor(Color.parseColor("#000000"));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class MyViewHolderblood extends RecyclerView.ViewHolder {

        TextView name;
        CircleImageView circleImageView;
        Button add;


        public MyViewHolderblood(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.textView9);
            circleImageView=itemView.findViewById(R.id.circleImageView);
            add=itemView.findViewById(R.id.button7);

        }
    }
}

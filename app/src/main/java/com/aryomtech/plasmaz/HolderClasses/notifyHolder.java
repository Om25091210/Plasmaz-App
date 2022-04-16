package com.aryomtech.plasmaz.HolderClasses;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.aryomtech.plasmaz.R;
import com.bumptech.glide.Glide;


public class notifyHolder extends RecyclerView.ViewHolder {

    View view;
    TextView text,date,timetxt;
    CardView click;
    ImageView photo;
    public notifyHolder(@NonNull View itemView) {
        super(itemView);

        view=itemView;
    }

    public void setView(final Context context, String textdata, String schdule, String link,String time,String image){

        text=view.findViewById(R.id.textView9);
        text.setText(textdata);

        date=view.findViewById(R.id.textView22);
        date.setText(schdule);

        timetxt=view.findViewById(R.id.textView8);
        timetxt.setText(time);

        photo=view.findViewById(R.id.imageView27);
        try{
            Glide.with(context).load(image).into(photo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        click=view.findViewById(R.id.noti);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(link!=null){
                    String url = link;
                    Uri uriUrl = Uri.parse(url);
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                    context.startActivity(launchBrowser);
                }
            }
        });
    }
}


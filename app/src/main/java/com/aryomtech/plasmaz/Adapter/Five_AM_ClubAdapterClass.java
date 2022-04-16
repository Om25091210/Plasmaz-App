package com.aryomtech.plasmaz.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.util.StringUtil;

import com.aryomtech.plasmaz.Model.AssignmentData;
import com.aryomtech.plasmaz.Model.five_am_data;
import com.aryomtech.plasmaz.R;
import com.bernaferrari.emojislider.EmojiSlider;
import com.bernaferrari.emojislider.FloatingEmoji;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public class Five_AM_ClubAdapterClass extends RecyclerView.Adapter<Five_AM_ClubAdapterClass.MyViewHolderblood>{

    private FirebaseAuth mAuth;
    static FirebaseUser useruuid;
    ArrayList<five_am_data> list;
    Context context;
    String dp_from_string="";
    String sun="🌞";
    View view,v;
    String sleeping="\uD83D\uDE34";
    private MediaPlayer mediaPlayer;
    DatabaseReference legend;
    String is_time_ok_to_hack="";
    ArrayList<Long> score=new ArrayList<Long>();

    //EMOJI =====================================================
    String eyes_with_heart="😍";
    String eyes_with_tears_laugh="😂";
    String smile_with_mouth_open_eyes_open="😀";
    String on_fire="🔥";
    String red_face_with_angry="😡";
    String face_with_fear_hooooo_wala="😱";
    String about_to_crying_face="😢";
    String hands_off="🙌️";
    String heart="❤";
    String birthday_cracker="🎉";
    String thumbs_up="👍";
    String humans_natural_fertilizer="💩";
    String monkey="🐒";
    String hunderd="💯";
    String hands_together_for_pranam="🙏";
    String wow="😮";
    String crying="😭";
    String ghurne_wala_face="😒";
    String black_specs_hero_face="😎";
    String aaa_with_mouth_telling_nhi_naa="😩";
    String speechless="😐";
    String head_blue_ring="😇";
    String clap="👏";
    String looking="👀";
    String dog="🐶";
    String cat="🐱";
    String pig="🐷";
    String monkey_with_eyes_closed="🙈";
    String monkey_with_ears_closed="🙉";
    String monkey_with_mouth_closed="🙊";
    String heart_broke="💔";
    String smiling="🌝";
    String black_moon="🌚";
    String crown="👑";
    String rainbow="🌈";
    String money_bag="💰";
    String ash="👻";






    public Five_AM_ClubAdapterClass(ArrayList<five_am_data> list, Context context,View v,String is_time_ok_to_hack,ArrayList<Long> score) {
        this.list = list;
        this.score=score;
        this.context = context;
        this.is_time_ok_to_hack=is_time_ok_to_hack;
        this.v=v;
    }

    @NonNull
    @Override
    public Five_AM_ClubAdapterClass.MyViewHolderblood onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.design_of_five_am_club,parent,false);
        return new Five_AM_ClubAdapterClass.MyViewHolderblood(view);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Five_AM_ClubAdapterClass.MyViewHolderblood holder, int position) {

        mAuth = FirebaseAuth.getInstance();
        useruuid = mAuth.getCurrentUser();

        Date date = new Date() ;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm") ;
        dateFormat.format(date);

        holder.liner.setVisibility(View.GONE);
        if(list.get(position).getLiner()!=null){
            if(!list.get(position).getLiner().trim().equals("")) {
                holder.liner.setVisibility(View.VISIBLE);
                holder.liner.setText("Woke Up At = " + list.get(position).getLiner()+" am");
            }
        }


        mediaPlayer = MediaPlayer.create(context,R.raw.wake_up_club);

        legend= FirebaseDatabase.getInstance().getReference().child("five_am_club");

        dp_from_string=context.getSharedPreferences("User_image_as_string",Context.MODE_PRIVATE)
                .getString("bitmap_to_string_of_user","");

        decodeSampledBitmapFromResource(context.getResources(), R.id.circleImageView, 100, 100);
        decodeSampledBitmapFromResource(context.getResources(), R.id.circleImageView2, 100, 100);

        byte[] imageAsBytes = Base64.decode(dp_from_string.getBytes(), Base64.DEFAULT);
        Bitmap bitmap =BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);

        holder.name.setText(list.get(position).getName());
        holder.points.setText("= "+list.get(position).getPoints()+"");
        holder.circleImageView2.setVisibility(View.GONE);
        try {
            Glide.with(context).load(list.get(position).getDp())
                    .placeholder(R.drawable.ic_plasmaz_app_5_am__2_)
                    .error(R.drawable.ic_plasmaz_app_5_am__2_)
                    .dontAnimate()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .thumbnail(0.5f)
                    .into(holder.circleImageView);

            Glide.with(context).load(list.get(position).getDp()).placeholder(R.drawable.ic_plasmaz_app_5_am__2_)
                    .dontAnimate()
                    .error(R.drawable.ic_plasmaz_app_5_am__2_)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .thumbnail(0.5f)
                    .into(holder.circleImageView2);

        } catch (Exception e) {
            e.printStackTrace();
        }


            holder.slider.setStartTrackingListener(new Function0<Unit>() {
                @Override
                public Unit invoke() {
                    Log.d("D", "setBeginTrackingListener");
                    return Unit.INSTANCE;
                }
            });


            holder.slider.setStopTrackingListener(new Function0<Unit>() {
                @Override
                public Unit invoke() {
                    Log.d("D", "setEndTrackingListener");

                    holder.slider.setProgress(90);
                    legend.child(useruuid.getUid()).child("legend").setValue("yes");
                    holder.name.setGravity(Gravity.END);
                    Log.e("test1", "test1");

                    holder.circleImageView.setVisibility(View.GONE);
                    holder.circleImageView2.setVisibility(View.VISIBLE);

                    if (useruuid.getUid().equals(list.get(position).getUid())) {

                        Long wake_point = list.get(position).getPoints();
                        wake_point = wake_point + 1;

                        legend.child(useruuid.getUid()).child("points").setValue(wake_point);

                        legend.child(useruuid.getUid()).child("liner").setValue(dateFormat.format(date));
                        mediaPlayer.start();

                        Snackbar snackbar = Snackbar.make(v, "1 Point added,Reopen this section to see \uD83D\uDE00", Snackbar.LENGTH_LONG);
                        snackbar.setDuration(10000);
                        snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                        snackbar.setAction("OKAY", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Do Something here
                            }
                        });
                        snackbar.show();


                    }
                    return Unit.INSTANCE;
                }
            });


        holder.slider.setPositionListener(pos -> {
            Log.d("D", "setPositionListener");
            return Unit.INSTANCE;
        });

        holder.slider.setProgress(0);
        holder.slider.setUserSeekable(false);
        holder.slider.setEmoji(sleeping);


        try {

            if(dateFormat.parse(dateFormat.format(date)).after(dateFormat.parse("03:59")) && Objects.requireNonNull(dateFormat.parse(dateFormat.format(date))).before(dateFormat.parse("05:01")) && is_time_ok_to_hack.equals("yes")) {
                if (list.get(position).getLegend().equals("no")) {
                    if (useruuid.getUid().equals(list.get(position).getUid())) {
                        holder.slider.setUserSeekable(true);

                        if (bitmap != null) {

                            holder.slider.setResultDrawable(bitmap);
                        } else {
                            Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                                    R.drawable.ic_plasmaz_app_5_am__2_);
                            holder.slider.setResultDrawable(icon);
                        }

                    }
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(list.get(position).getLegend().equals("yes")){
            holder.slider.setProgress(90);
            holder.name.setGravity(Gravity.END);
            Log.e("test2","test2");
            holder.slider.setEmoji(sun);
            holder.circleImageView.setVisibility(View.GONE);
            holder.circleImageView2.setVisibility(View.VISIBLE);
        }
        else{
            holder.slider.setProgress(0);
            holder.name.setGravity(Gravity.START);
            Log.e("test3","test3");
            holder.slider.setEmoji(sleeping);
            holder.circleImageView.setVisibility(View.VISIBLE);
            holder.circleImageView2.setVisibility(View.GONE);
        }



    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolderblood extends RecyclerView.ViewHolder {

        TextView name;
        final EmojiSlider slider;
        TextView points,liner;
        CircleImageView circleImageView,circleImageView2;


        public MyViewHolderblood(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.textView9);
            slider = itemView.findViewById(R.id.slider);
            points=itemView.findViewById(R.id.textView13);
            circleImageView=itemView.findViewById(R.id.circleImageView);
            circleImageView2=itemView.findViewById(R.id.circleImageView2);
            liner=itemView.findViewById(R.id.textView19);

        }
    }
}

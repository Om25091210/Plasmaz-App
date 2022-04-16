package com.aryomtech.plasmaz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profilesec extends AppCompatActivity {

    private FirebaseAuth mAuth;
    static FirebaseUser useruuid;
    CircleImageView ProfileImage;

    Bitmap bitmap;
    String profilepath;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    DatabaseReference Users,Teacher;
    String user_name ="";
    String displayname="";
    TextView Namedp,email,name;
    ImageView save;
    EditText classroom,school;

    Button certify,receipts,sendRec,secret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilesec);

        Intent get=getIntent();

        Window window = Profilesec.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(Profilesec.this, R.color.followersBg));
        window.setNavigationBarColor(ContextCompat.getColor(Profilesec.this, R.color.white));

        ProfileImage=findViewById(R.id.profile_image);

        Users=FirebaseDatabase.getInstance().getReference().child("Users");
        Teacher=FirebaseDatabase.getInstance().getReference().child("Teachers");

        mAuth = FirebaseAuth.getInstance();
        useruuid = mAuth.getCurrentUser();

        assert useruuid != null;
        user_name = useruuid.getDisplayName();
        assert user_name != null;
        displayname = user_name.replaceAll("[^a-zA-Z0-9]","");

        secret=findViewById(R.id.secret);
        secret.setVisibility(View.GONE);

        if(useruuid.getUid().equals("WzjVfwYBp1Njj0dQ2i3Yx5aFC3o2") || useruuid.getUid().equals("aKzdW2tQoDRRrSX3RotKOj6Wyqh1")){
            secret.setVisibility(View.VISIBLE);
        }
        secret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent push=new Intent(Profilesec.this,Push.class);
                startActivity(push);
            }
        });

        profilepath="picture/"+useruuid.getUid()+".png";

        Namedp=findViewById(R.id.tv_name);
        Namedp.setText(useruuid.getDisplayName());

        email=findViewById(R.id.email);
        email.setText(useruuid.getEmail());

        certify=findViewById(R.id.button3);
        receipts=findViewById(R.id.button4);
        sendRec=findViewById(R.id.button5);
        sendRec.setVisibility(View.GONE);

        Teacher.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot key:snapshot.getChildren()){
                    if(useruuid.getUid().equals(key.getKey())){
                        sendRec.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        certify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent certificate=new Intent(Profilesec.this,Certify.class);
                startActivity(certificate);
                Animatoo.animateZoom(Profilesec.this);
            }
        });

        receipts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent rec=new Intent(Profilesec.this,Receipt.class);
                startActivity(rec);
                Animatoo.animateZoom(Profilesec.this);

            }
        });

        sendRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sendrec=new Intent(Profilesec.this,Students.class);
                startActivity(sendrec);
                Animatoo.animateZoom(Profilesec.this);

            }
        });

        classroom=findViewById(R.id.editTextTextMultiLine2);
        school=findViewById(R.id.editTextTextMultiLine);

        Users.child(useruuid.getUid()).child(displayname).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String classstr=snapshot.child("Class").getValue(String.class);
                String schoolstr=snapshot.child("School").getValue(String.class);
                classroom.setText(classstr);
                school.setText(schoolstr);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        save=findViewById(R.id.imageView29);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!classroom.getText().toString().trim().equals("")){
                    if(!school.getText().toString().trim().equals("")){

                        Users.child(useruuid.getUid()).child(displayname).child("Class").setValue(classroom.getText().toString().trim());
                        Users.child(useruuid.getUid()).child(displayname).child("School").setValue(school.getText().toString().trim());
                        Snackbar.make(findViewById(android.R.id.content),"Plasmaz: Saved Successfully.",Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });

        name=findViewById(R.id.name);
        name.setText(useruuid.getDisplayName());

        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        storageReference=FirebaseStorage.getInstance().getReference().child(profilepath);


        try{
            final File localfile=File.createTempFile(useruuid.getUid(),"png");
            storageReference.getFile(localfile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap= BitmapFactory.decodeFile(localfile.getAbsolutePath());
                            ((ImageView)findViewById(R.id.profile_image)).setImageBitmap(bitmap);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setActivityTitle("Plasmaz")
                        .setAspectRatio(1,1) //You can skip this for free form aspect ratio)
                        .start(Profilesec.this);

            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                // Set uri as Image in the ImageView:
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver() , Uri.parse(String.valueOf(resultUri)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ProfileImage.setImageBitmap(bitmap);
                ProfileImage.invalidate();
                BitmapDrawable drawable6 = (BitmapDrawable) ProfileImage.getDrawable();
                Bitmap bitmap6 = drawable6.getBitmap();

                getImageUri(getApplicationContext(), bitmap6);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        final String randomKey= UUID.randomUUID().toString();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        byte[] b = bytes.toByteArray();
        String encoded = Base64.encodeToString(b, Base64.DEFAULT);

        getSharedPreferences("User_image_as_string",MODE_PRIVATE).edit()
                .putString("bitmap_to_string_of_user",encoded).apply();

        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, ""+randomKey, null);
        uploadPicture(Uri.parse(path));

        return Uri.parse(path);
    }

    private void uploadPicture(Uri parse) {

        final ProgressDialog pd=new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.setCancelable(false);
        pd.show();
        final String randomKey= UUID.randomUUID().toString();
        StorageReference riversRef = FirebaseStorage.getInstance().getReference().child(profilepath);

        riversRef.putFile(parse)
                .addOnSuccessListener(taskSnapshot -> {
                    taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(
                            task -> {
                                String fileLink = task.getResult().toString();

                                FirebaseDatabase.getInstance().getReference().child("five_am_club").child(useruuid.getUid()).child("dp").setValue(fileLink);
                                Users.child(useruuid.getUid()).child(displayname).child("imageurl").setValue(fileLink);

                                long delayInMillis = 1000;
                                Timer timer = new Timer();
                                timer.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        pd.dismiss();
                                    }
                                }, delayInMillis);
                            });
                    Snackbar.make(findViewById(android.R.id.content),"Image Uploaded.",Snackbar.LENGTH_LONG).show();
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Failed To Upload", Toast.LENGTH_LONG).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot tasksnapshot) {
                double progressPercent=(100.00 * tasksnapshot.getBytesTransferred() / tasksnapshot.getTotalByteCount());
                pd.setMessage("Progress: "+(int) progressPercent+"%");
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent main_home=new Intent(Profilesec.this,MainActivity.class);
        startActivity(main_home);
        finish();
        super.onBackPressed();

    }

}
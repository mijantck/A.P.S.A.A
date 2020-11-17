package com.mrsoftit.apsaa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfile extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    CircleImageView profile_image;
    TextView profile_name,profile_email,profile_phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        profile_image = findViewById(R.id.profile_image);
        profile_name = findViewById(R.id.profile_name);
        profile_email = findViewById(R.id.profile_email);
        profile_phone = findViewById(R.id.profile_phone);
        Picasso.get().load(currentUser.getPhotoUrl().toString()).into(profile_image);
        profile_name.setText(currentUser.getDisplayName());
        profile_email.setText(currentUser.getEmail());
        profile_phone.setText(currentUser.getPhoneNumber()+"\n");

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName("Jane Q. User")
                .setPhotoUri(Uri.parse("https://i.imgur.com/n3z7c02.jpeg"))
                .build();

        currentUser.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MyProfile.this, "Sucsesfull", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
}
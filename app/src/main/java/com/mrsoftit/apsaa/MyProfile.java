package com.mrsoftit.apsaa;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mrsoftit.apsaa.product.ProductAddInStore;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MyProfile extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{

    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    CircleImageView profile_image,profile_image_edite,profile_edite_pan;
    TextView profile_name,profile_email,profile_phone;
    UserProfileChangeRequest profileUpdates;

    private static final int PICK_IMAGE_REQUEST = 1;
    public Uri mImageUri;

    LinearLayout my_info_View,my_info_View_edite;

    MaterialButton btn_MY_Profile_Update;
    TextInputEditText profile_Name_Edite,profile_Adress_Edite;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        profile_image = findViewById(R.id.profile_image);
        profile_name = findViewById(R.id.profile_name);
        profile_email = findViewById(R.id.profile_email);
        profile_phone = findViewById(R.id.profile_phone);
        profile_image_edite = findViewById(R.id.profile_image_edite);

        profile_edite_pan = findViewById(R.id.profile_edite_pan);

        my_info_View = findViewById(R.id.my_info_View);
        my_info_View_edite = findViewById(R.id.my_info_View_edite);

        btn_MY_Profile_Update = findViewById(R.id.btn_MY_Profile_Update);

        profile_Name_Edite = findViewById(R.id.profile_Name_Edite);
        profile_Adress_Edite = findViewById(R.id.profile_Adress_Edite);

        Picasso.get().load(currentUser.getPhotoUrl().toString()).into(profile_image);
        profile_name.setText(currentUser.getDisplayName());
        profile_email.setText(currentUser.getEmail());
        profile_phone.setText(currentUser.getPhoneNumber()+"\n");




        profile_edite_pan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyProfile.this, "click", Toast.LENGTH_SHORT).show();
                my_info_View.setVisibility(View.GONE);
                profile_image.setVisibility(View.GONE);
                my_info_View_edite.setVisibility(View.VISIBLE);
                profile_image_edite.setVisibility(View.VISIBLE);
            }
        });
        profile_image_edite.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                getimge_profile();

            }
        });

        btn_MY_Profile_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadImageUri(mImageUri );
            }
        });
    }


    private void uploadImageUri(Uri imageUri ){
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "profilePicTemp");

            InputStream in = getContentResolver().openInputStream(imageUri);
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();

            upload(file, new UploadCallback() {
                @Override
                public void onSuccess(final String downloadLink) {

                    String Name = profile_Name_Edite.getText().toString();
                    String Address = profile_Adress_Edite.getText().toString();
                    profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(Name)
                            .setPhotoUri(Uri.parse(downloadLink))
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


                @Override
                public void onFailed(String message) {
                }
            });

        } catch (Exception e){
            e.printStackTrace();

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @AfterPermissionGranted(PICK_IMAGE_REQUEST)
    private void getimge_profile() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            List<Integer> lPermission = new ArrayList<>();
            List<String> stringPermissionList1 = getPermissionList();
            for (int i = 0; i < stringPermissionList1.size(); i++) {
                lPermission.add(ContextCompat.checkSelfPermission(getApplicationContext(), stringPermissionList1.get(i)));
            }
            boolean bPermissionDenied = false;
            for (int i = 0; i < lPermission.size(); i++) {
                int a = lPermission.get(i);
                if (PackageManager.PERMISSION_DENIED == a) {
                    bPermissionDenied = true;
                    break;
                }
            }


            if (bPermissionDenied) {
                String sMessage = "Please allow all permissions shown in upcoming dialog boxes, so that app functions properly";
                //make request to the user
                List<String> stringPermissionList = getPermissionList();
                String[] sPermissions = stringPermissionList.toArray(new String[stringPermissionList.size()]);

                //request the permissions
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(sPermissions, PICK_IMAGE_REQUEST);
                }
            } else {


            }


        } else {

        }

        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE};

        if (EasyPermissions.hasPermissions(this, perms)) {

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Image Add "), PICK_IMAGE_REQUEST);

        } else {
           /* EasyPermissions.requestPermissions(this, "Permission  ",
                    PICK_IMAGE_REQUEST , perms);*/
        }
    }

    private List<String> getPermissionList(){
        List<String> stringPermissionList=new ArrayList<>();

        stringPermissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        stringPermissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return  stringPermissionList;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        boolean isAllPermissionGranted = true;

        for (int i = 0; i < grantResults.length; i++) {
            int iPermission = grantResults[i];
            if (iPermission == PackageManager.PERMISSION_DENIED) {
                isAllPermissionGranted = false;
                break;
            }
        }
        if (isAllPermissionGranted) {

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Image Add "), PICK_IMAGE_REQUEST);
        } else {
            // Prompt the user to grant all permissions
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (data.getData() != null) {
                    mImageUri = data.getData();
                    Picasso.get().load(mImageUri).into(profile_image_edite);

            } else {
                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }}

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    private void upload(File file, final UploadCallback uploadCallback) {

        //this is for log message
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        //create file to request body and request
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), saveBitmapToFile(file));
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("image", "filename.png", requestBody)
                .build();

        //request create for imgur
        final Request request = new Request.Builder()
                .url("https://api.imgur.com/3/image")
                .method("POST", body)
                .addHeader("Authorization", "Client-ID 2f4dd94e6dbf1f1")
                .build();

        //okhttp client create
        final OkHttpClient client = new OkHttpClient().newBuilder()
                .addInterceptor(loggingInterceptor)
                .build();

        //network request so we need to run on new thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Response response = client.newCall(request).execute();

                    if(response.isSuccessful()){
                        JSONObject jsonObject = new JSONObject(response.body().string());

                        final String link =  jsonObject.getJSONObject("data").getString("link");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                uploadCallback.onSuccess(link);
                            }
                        });


                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                uploadCallback.onFailed("Error message: " + response.message());
                            }
                        });

                    }

                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            uploadCallback.onFailed("Io Exception");
                        }
                    });

                    e.printStackTrace();
                }
            }
        }).start();

    }

    interface UploadCallback{
        void onSuccess(String downloadLink);
        void onFailed(String message);
    }
    public File saveBitmapToFile(File file){
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE=75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , outputStream);

            return file;
        } catch (Exception e) {
            return null;
        }
    }



}
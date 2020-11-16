package com.mrsoftit.apsaa.product;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mrsoftit.apsaa.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class ProductAddInStore extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{

    //databse
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db;
    CollectionReference citiesRef;
    CollectionReference userRef;





    //input
    ImageView pruductImage,ImageAdded,ImageAdded1,ImageAdded2,ImageAdded3,ImageAdded4;
    private TextInputEditText productName, productPrice,pruductDiscount;
    private MaterialButton addProduct;

    //String
    private static final int PICK_IMAGE_REQUEST = 1;
    public Uri mImageUri;

    public String mImageUriS;
    public Uri mImageUri1;

    public String mImageUri1S;
    public Uri mImageUri2;
    public String mImageUri2S;

    public Uri mImageUri3;
    public String mImageUri3S;

    public Uri mImageUri4;
    public String mImageUri4S;

    public  int SilrctorNumber;
    public  int uploadtnumber = 0;
    boolean image = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add_in_store);

        db = FirebaseFirestore.getInstance();

        citiesRef = db.collection("Product");
        userRef = db.collection("Users").document(currentUser.getUid()).collection("myProduct");

        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.pruductPrice);
        pruductDiscount = findViewById(R.id.pruductDiscount);
        addProduct = findViewById(R.id.addpruduct);


        pruductImage = findViewById(R.id.pruductPic);
        ImageAdded = findViewById(R.id.ImageAdded);
        ImageAdded1 = findViewById(R.id.ImageAdded1);
        ImageAdded2 = findViewById(R.id.ImageAdded2);
        ImageAdded3 = findViewById(R.id.ImageAdded3);
        ImageAdded4 = findViewById(R.id.ImageAdded4);

        final Bundle bundle = getIntent().getExtras();

        ImageAdded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SilrctorNumber = 1;
                if (bundle!=null){
                    image = true;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        getIMEGE();
                    }
                }else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        getIMEGE();
                    }
                }

            }
        });
        ImageAdded1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SilrctorNumber = 2;
                if (bundle!=null){
                    image = true;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        getIMEGE();
                    }
                }else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        getIMEGE();
                    }
                }

            }
        });
        ImageAdded2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SilrctorNumber = 3;
                if (bundle!=null){
                    image = true;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        getIMEGE();
                    }
                }else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        getIMEGE();
                    }
                }

            }
        });
        ImageAdded3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SilrctorNumber = 4;
                if (bundle!=null){
                    image = true;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        getIMEGE();
                    }
                }else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        getIMEGE();
                    }
                }

            }
        });
        ImageAdded4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SilrctorNumber = 5;
                if (bundle!=null){
                    image = true;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        getIMEGE();

                    }
                }else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        getIMEGE();
                    }
                }

            }
        });

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mImageUri != null && uploadtnumber == 0 ){

                    uploadImageUri( mImageUri);
                }

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
                    Toast.makeText(ProductAddInStore.this, "in 1", Toast.LENGTH_SHORT).show();
                    mImageUriS = downloadLink;

                    uploadtnumber = 1;

                    if (mImageUri1 !=null && uploadtnumber == 1 ){

                        uploadImageUri1( mImageUri1);

                    } else {
                        PicturUpLoad(mImageUriS,null,null,null,null);
                    }


                }


                @Override
                public void onFailed(String message) {
                }
            });

        } catch (Exception e){
            e.printStackTrace();

        }

    }
    private void uploadImageUri1(Uri imageUri ){

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

                    Toast.makeText(ProductAddInStore.this, "in 2", Toast.LENGTH_SHORT).show();

                    mImageUri1S = downloadLink;
                    uploadtnumber = 2;

                    if (mImageUri2 != null && uploadtnumber == 2 ){
                        uploadImageUri2( mImageUri2 );
                    }else {
                        PicturUpLoad(mImageUriS,mImageUri1S,null,null,null);
                    }



                }

                @Override
                public void onFailed(String message) {
                }
            });

        } catch (Exception e){
            e.printStackTrace();

        }
    }
    private void uploadImageUri2(Uri imageUri ){


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

                    Toast.makeText(ProductAddInStore.this, "in 3", Toast.LENGTH_SHORT).show();

                    mImageUri2S = downloadLink;
                    uploadtnumber = 3;

                    if (mImageUri3 != null && uploadtnumber == 3 ){
                        uploadImageUri3( mImageUri3 );
                    }else {
                        PicturUpLoad(mImageUriS,mImageUri1S,mImageUri2S,null,null);                        }



                }

                @Override
                public void onFailed(String message) {
                }
            });

        } catch (Exception e){
            e.printStackTrace();

        }


    }
    private void uploadImageUri3(Uri imageUri ){


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

                    Toast.makeText(ProductAddInStore.this, "in 4", Toast.LENGTH_SHORT).show();


                    mImageUri3S = downloadLink;
                    uploadtnumber = 4;

                    if (mImageUri4 != null && uploadtnumber == 4 ){
                        uploadImageUri4( mImageUri4 );
                    }else {
                        PicturUpLoad(mImageUriS,mImageUri1S,mImageUri2S,mImageUri3S,null);                        }

                }



                @Override
                public void onFailed(String message) {
                }
            });

        } catch (Exception e){
            e.printStackTrace();

        }


    }
    private void uploadImageUri4(Uri imageUri ){


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

                    Toast.makeText(ProductAddInStore.this, "in 5", Toast.LENGTH_SHORT).show();

                    mImageUri4S = downloadLink;
                    uploadtnumber = 5;




                    PicturUpLoad(mImageUriS,mImageUri1S,mImageUri2S,mImageUri3S,mImageUri4S);


                }

                @Override
                public void onFailed(String message) {
                }
            });

        } catch (Exception e){
            e.printStackTrace();

        }


    }

    public  void  PicturUpLoad(final String downloadLink1, final String downloadLink2,
                               final String downloadLink3, final String downloadLink4,
                               final String downloadLink5){

        Toast.makeText(this, "inooooo", Toast.LENGTH_SHORT).show();
        String name = productName.getText().toString();
        String price  = productPrice.getText().toString();
        double priceD = Double.parseDouble(price);

        String discrition = pruductDiscount.getText().toString();


        Map<String, Object> city = new HashMap<>();
        city.put("name", name);
        city.put("thisDucID", null);
        city.put("price", priceD);
        city.put("discription", discrition);
        city.put("search", name.toLowerCase());

        city.put("i1", downloadLink1 );
        city.put("i2", downloadLink2 );
        city.put("i3", downloadLink3 );
        city.put("i4", downloadLink4 );
        city.put("i15", downloadLink5 );


        citiesRef.add(city)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        String id = task.getResult().getId();
                        citiesRef.document(id).update("thisDucID",id)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        userRef.document(id).set(city)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        userRef.document(id).update("thisDucID",id)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                Toast.makeText(ProductAddInStore.this, " Succecfully ", Toast.LENGTH_SHORT).show();

                                                            }
                                                        });
                                                    }
                                                });
                                    }
                                });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProductAddInStore.this, e+"", Toast.LENGTH_SHORT).show();
            }
        });

    }





    @RequiresApi(api = Build.VERSION_CODES.M)
    @AfterPermissionGranted(PICK_IMAGE_REQUEST)
    private void getIMEGE() {

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
                if ( SilrctorNumber == 1 ){
                    mImageUri = data.getData();
                    Picasso.get().load(mImageUri).into(pruductImage);
                    Picasso.get().load(mImageUri).into(ImageAdded);
                }
                else if (SilrctorNumber == 2 ){
                    mImageUri1 = data.getData();
                    Picasso.get().load(mImageUri1).into(pruductImage);
                    Picasso.get().load(mImageUri1).into(ImageAdded1);
                }
                else if (SilrctorNumber == 3){
                    mImageUri2 = data.getData();
                    Picasso.get().load(mImageUri2).into(pruductImage);
                    Picasso.get().load(mImageUri2).into(ImageAdded2);
                }
                else if (SilrctorNumber == 4){
                    mImageUri3 = data.getData();
                    Picasso.get().load(mImageUri3).into(pruductImage);
                    Picasso.get().load(mImageUri3).into(ImageAdded3);
                }
                else if (SilrctorNumber == 5){
                    mImageUri4 = data.getData();
                    Picasso.get().load(mImageUri4).into(pruductImage);
                    Picasso.get().load(mImageUri4).into(ImageAdded4);
                }


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
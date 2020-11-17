package com.mrsoftit.apsaa.product;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mrsoftit.apsaa.MainActivity;
import com.mrsoftit.apsaa.R;
import com.mrsoftit.apsaa.SliderAdapterExamplepro;
import com.mrsoftit.apsaa.SliderItem;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FullProductView extends AppCompatActivity {

    FirebaseUser currentUser;
    String globlecutouser_id ;
    private FirebaseAuth mAuth;

    private Uri dynamicLink1 = null;

    ImageView mainimage,imageClick1,imageClick2,imageClick3,imageClick4,imageClick5;




    ProgressDialog progressDialog;


    Button orderButton,ChartButton;
    private ImageView productImageDetail;
    private TextView ProductNameDetails,shareButton,inStockDetails,productPriceDetails,
            shopDetailName,shopDetailPhone,shopDetailAddress,ProductCode,sellPrice,LigalPrice,
            tetViewDiescunt,ProductDescriptionView,ProductcolorView,productType,ProductSizeView;

    private EditText productQuantidyfromCustomer,size,colorForOrder,typeforeOrder;

    private  double proPrice,proQua;
    private  int date,pruductDiscount;

    MaterialButton favorite,add_shopping;
    TextView reviewID;

    String name,price,descripton,i1,i2,i3,i4,i5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_product_view);

        mAuth = FirebaseAuth.getInstance();

        if (currentUser!=null){
            globlecutouser_id = currentUser.getUid();
        }

        progressDialog = new ProgressDialog(FullProductView.this);
        // Setting Message
        progressDialog.setMessage("Loading..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();



        androidx.appcompat.widget.Toolbar toolbar =  findViewById(R.id.toolbar_support);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setSubtitleTextColor(getResources().getColor(R.color.grey));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FullProductView.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        productImageDetail = findViewById(R.id.productImageDetail);
        ProductNameDetails  = findViewById(R.id.ProductNameDetails);
        inStockDetails = findViewById(R.id.inStockDetails);
        productPriceDetails = findViewById(R.id.productPriceDetails);
        shopDetailName = findViewById(R.id.shopDetailName);
        shopDetailPhone = findViewById(R.id.shopDetailPhone);
        mainimage = findViewById(R.id.mainimage);

        imageClick1 = findViewById(R.id.imageClick1);
        imageClick2 = findViewById(R.id.imageClick2);
        imageClick3 = findViewById(R.id.imageClick3);
        imageClick4 = findViewById(R.id.imageClick4);
        imageClick5 = findViewById(R.id.imageClick5);



        favorite = findViewById(R.id.favorite);
        add_shopping = findViewById(R.id.add_shopping);

        shopDetailAddress = findViewById(R.id.shopDetailAddress);
        ProductCode =findViewById(R.id.ProductCode);
        shareButton =findViewById(R.id.shareButton);
        reviewID =findViewById(R.id.reviewID);
        size =findViewById(R.id.size);

        LigalPrice =findViewById(R.id.LigalPrice);
        tetViewDiescunt =findViewById(R.id.tetViewDiescunt);
        sellPrice =findViewById(R.id.sellPrice);
        ProductDescriptionView =findViewById(R.id.ProductDescriptionView);

        productQuantidyfromCustomer = findViewById(R.id.productQuantidyfromCustomer);
        orderButton =findViewById(R.id.orderButton);

        ProductcolorView =findViewById(R.id.ProductcolorView);
        productType =findViewById(R.id.productType);
        ProductSizeView =findViewById(R.id.ProductSizeView);


        colorForOrder =findViewById(R.id.colorForOrder);
        typeforeOrder =findViewById(R.id.typeforeOrder);


        final Bundle bundle = getIntent().getExtras();

        if (bundle != null){
            name = bundle.getString("productName");
            ProductNameDetails.setText(name);
            price = bundle.getString("productPrice");
            sellPrice.setText(price);
            descripton = bundle.getString("productDescription");
            ProductDescriptionView.setText(descripton);

            if (bundle.getString("i1") != null){
                i1 = bundle.getString("i1");
                Picasso.get().load(i1).into(mainimage);
                Picasso.get().load(i1).into(imageClick1);

            }
            if (bundle.getString("i2") != null){
                i2 = bundle.getString("i2");
                Picasso.get().load(i2).into(imageClick2);

            }
            if (bundle.getString("i3") != null){
                i3 = bundle.getString("i3");
                Picasso.get().load(i3).into(imageClick3);

            }
            if (bundle.getString("i4") != null){
                i4 = bundle.getString("i4");
                Picasso.get().load(i4).into(imageClick4);

            }
            if (bundle.getString("i5") != null){
                i5 = bundle.getString("i5");
                Picasso.get().load(i5).into(imageClick5);

            }
        }

        imageClick1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.get().load(i1).into(mainimage);
            }
        });
        imageClick2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.get().load(i2).into(mainimage);
            }
        });
        imageClick3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.get().load(i3).into(mainimage);
            }
        });
        imageClick4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.get().load(i4).into(mainimage);
            }
        });
        imageClick5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.get().load(i5).into(mainimage);
            }
        });




        progressDialog.dismiss();
    }
}
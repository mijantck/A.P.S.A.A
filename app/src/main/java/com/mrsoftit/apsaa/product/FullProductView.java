package com.mrsoftit.apsaa.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.mrsoftit.apsaa.AddCard;
import com.mrsoftit.apsaa.BuyNow;
import com.mrsoftit.apsaa.MainActivity;
import com.mrsoftit.apsaa.R;
import com.mrsoftit.apsaa.SliderAdapterExamplepro;
import com.mrsoftit.apsaa.SliderItem;
import com.mrsoftit.apsaa.WatchList;
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

    CollectionReference GlobleSoplist = FirebaseFirestore.getInstance()
            .collection("Product");

    AllProductListAdapter allProductListAdapter;

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

    MaterialButton btn_buy_now,btn_Add_to_cart,btn_add_to_watchlist;



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
        shopDetailName = findViewById(R.id.shopDetailName);
        shopDetailPhone = findViewById(R.id.shopDetailPhone);
        mainimage = findViewById(R.id.mainimage);

        imageClick1 = findViewById(R.id.imageClick1);
        imageClick2 = findViewById(R.id.imageClick2);
        imageClick3 = findViewById(R.id.imageClick3);
        imageClick4 = findViewById(R.id.imageClick4);
        imageClick5 = findViewById(R.id.imageClick5);

        shopDetailAddress = findViewById(R.id.shopDetailAddress);
        ProductCode =findViewById(R.id.ProductCode);


        LigalPrice =findViewById(R.id.LigalPrice);


        ProductDescriptionView =findViewById(R.id.ProductDescriptionView);



        ProductcolorView =findViewById(R.id.ProductcolorView);
        productType =findViewById(R.id.productType);
        ProductSizeView =findViewById(R.id.ProductSizeView);

        btn_buy_now =findViewById(R.id.btn_buy_now);
        btn_Add_to_cart =findViewById(R.id.btn_Add_to_cart);
        btn_add_to_watchlist =findViewById(R.id.btn_add_to_watchlist);

        final Bundle bundle = getIntent().getExtras();

        if (bundle != null){
            name = bundle.getString("productName");
            ProductNameDetails.setText(name);
            price = bundle.getString("productPrice");
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


        btn_buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FullProductView.this, BuyNow.class);
                startActivity(intent);
            }
        });
        btn_Add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FullProductView.this, "Add to card", Toast.LENGTH_SHORT).show();
            }
        });
        btn_add_to_watchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FullProductView.this, "Add to watch list", Toast.LENGTH_SHORT).show();

            }
        });

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

        ShopSearch();

    }

    private void ShopSearch() {


      //  Query query1 = GlobleSoplist.orderBy("search").startAt(query.toLowerCase()).endAt(query.toLowerCase()+ "\uf8ff");

        FirestoreRecyclerOptions<ProductNote> options = new FirestoreRecyclerOptions.Builder<ProductNote>()
                .setQuery(GlobleSoplist, ProductNote.class)
                .build();

        allProductListAdapter = new AllProductListAdapter(options);


        RecyclerView recyclerView = findViewById(R.id.fullProductViewReletetProuct);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        recyclerView.setAdapter(allProductListAdapter);
        allProductListAdapter.startListening();

        allProductListAdapter.setOnItemClickListener(new AllProductListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                ProductNote productNote = documentSnapshot.toObject(ProductNote.class);
                Intent intent = new Intent(FullProductView.this, FullProductView.class);
                intent.putExtra("productName",productNote.getName());
                intent.putExtra("productPrice",productNote.getPrice()+"");
                intent.putExtra("productDescription",productNote.getDiscription());
                intent.putExtra("i1",productNote.getI1());
                intent.putExtra("i2",productNote.getI2());
                intent.putExtra("i3",productNote.getI3());
                intent.putExtra("i4",productNote.getI4());
                intent.putExtra("i15",productNote.getI15());
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        allProductListAdapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();

        allProductListAdapter.stopListening();

    }
}
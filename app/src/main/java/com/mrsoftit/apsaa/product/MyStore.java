package com.mrsoftit.apsaa.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mrsoftit.apsaa.MainActivity;
import com.mrsoftit.apsaa.R;

public class MyStore extends AppCompatActivity {

    //databse
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db;
    CollectionReference citiesRef;
    CollectionReference userRef;

    MaterialButton productadd;
    CollectionReference GlobleSoplist = FirebaseFirestore.getInstance()
            .collection("Product");

    AllProductListAdapter allProductListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_store);
        productadd = findViewById(R.id.productadd);

        db = FirebaseFirestore.getInstance();

        userRef = db.collection("Users").document(currentUser.getUid()).collection("myProduct");
        productadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyStore.this,ProductAddInStore.class);
                startActivity(intent);

            }
        });


        FirestoreRecyclerOptions<ProductNote> options = new FirestoreRecyclerOptions.Builder<ProductNote>()
                .setQuery(userRef, ProductNote.class)
                .build();

        allProductListAdapter = new AllProductListAdapter(options);


        RecyclerView recyclerView = findViewById(R.id.product_info_recyclerView);
        recyclerView.setHasFixedSize(true);
        // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GlobleProductListActivity.this,RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        //  recyclerView.setLayoutManager(ne4 LinearLayoutManager(this));
        // recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(allProductListAdapter);
        allProductListAdapter.setOnItemClickListener(new AllProductListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                ProductNote productNote = documentSnapshot.toObject(ProductNote.class);
                Intent intent = new Intent(MyStore.this, FullProductView.class);
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
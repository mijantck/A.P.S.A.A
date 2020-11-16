package com.mrsoftit.apsaa.product;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.mrsoftit.apsaa.R;

public class MyStore extends AppCompatActivity {

    MaterialButton productadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_store);
        productadd = findViewById(R.id.productadd);

        productadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyStore.this,ProductAddInStore.class);
                startActivity(intent);

            }
        });
    }
}
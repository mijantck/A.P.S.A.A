package com.mrsoftit.apsaa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.mrsoftit.apsaa.product.AllProductListAdapter;
import com.mrsoftit.apsaa.product.FullProductView;
import com.mrsoftit.apsaa.product.MyStore;
import com.mrsoftit.apsaa.product.ProductNote;
import com.squareup.picasso.Picasso;

import java.util.Objects;
import java.util.Queue;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;

    boolean  wholProductList = false ;

    boolean doubleBackToExitPressedOnce = false;
    private FirebaseAuth mAuth;
    GoogleSignInClient googleSignInClient;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    String Userid;

    CollectionReference GlobleSoplist = FirebaseFirestore.getInstance()
            .collection("Product");

    AllProductListAdapter allProductListAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar =  findViewById(R.id.toolbar_support);
        setSupportActionBar(toolbar);


        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

// Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();

        if (currentUser!=null){
            Userid = currentUser.getUid();
        }

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        FirestoreRecyclerOptions<ProductNote> options = new FirestoreRecyclerOptions.Builder<ProductNote>()
                .setQuery(GlobleSoplist, ProductNote.class)
                .build();

        allProductListAdapter = new AllProductListAdapter(options);


        RecyclerView recyclerView = findViewById(R.id.shopListRecyrview);
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
                Intent intent = new Intent(MainActivity.this, FullProductView.class);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        MenuItem Add_to_cart = menu.findItem(R.id.Add_to_cart);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                ShopSearch(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ShopSearch(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Add_to_cart:
                startActivity(new Intent(MainActivity.this,AddCard.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




    private void ShopSearch(String query) {
        Query query1 = GlobleSoplist.orderBy("search").startAt(query.toLowerCase()).endAt(query.toLowerCase()+ "\uf8ff");
        FirestoreRecyclerOptions<ProductNote> options = new FirestoreRecyclerOptions.Builder<ProductNote>()
                .setQuery(query1, ProductNote.class)
                .build();

        allProductListAdapter = new AllProductListAdapter(options);


        RecyclerView recyclerView = findViewById(R.id.shopListRecyrview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(allProductListAdapter);
        allProductListAdapter.startListening();

    }

    @Override
    protected void onStart() {
        super.onStart();
        NavigationView navigationView = findViewById(R.id.navigationView);
        View headeView = navigationView.getHeaderView(0);

        final CircleImageView appCompatImageView = headeView.findViewById(R.id.appCompatImageView);
        final TextView name = headeView.findViewById(R.id.dukanname);
        final TextView Email = headeView.findViewById(R.id.dukanEmail);

        Picasso.get().load(currentUser.getPhotoUrl()).into(appCompatImageView);
        name.setText(currentUser.getDisplayName());
        Email.setText(currentUser.getEmail());

        allProductListAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        allProductListAdapter.stopListening();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.my_info:
                startActivity(new Intent(MainActivity.this,MyProfile.class));
                break;

                case R.id.my_store:
                startActivity(new Intent(MainActivity.this, MyStore.class));
                break;
            case R.id.auctions:
                startActivity(new Intent(MainActivity.this, Auctions.class));
                break;
            case R.id.watchlist:
                startActivity(new Intent(MainActivity.this, WatchList.class));
                break;
                case R.id.Notification:
                startActivity(new Intent(MainActivity.this, Notification.class));
                break;

            case R.id.Logout:
                signOut();
                revokeAccess();
                finish();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Google Sign In failed, update UI appropriately
                    }
                });
    }

    private void revokeAccess() {
        // Firebase sign out
        mAuth.signOut();

        // Google revoke access
        googleSignInClient.revokeAccess().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Google Sign In failed, update UI appropriately

                    }
                });

    }

}
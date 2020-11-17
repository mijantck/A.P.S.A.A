package com.mrsoftit.apsaa.product;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.mrsoftit.apsaa.R;
import com.squareup.picasso.Picasso;

public class AllProductListAdapter extends FirestoreRecyclerAdapter<ProductNote, AllProductListAdapter.ViewHolde> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    private OnItemClickListener listener;

    public AllProductListAdapter(@NonNull FirestoreRecyclerOptions<ProductNote> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolde holder, int position, @NonNull ProductNote model) {
        holder.ProductName.setText(model.getName());
        holder.inStock.setText("in stock");
        holder.productPrice.setText(model.getPrice()+"");
        Picasso.get().load(model.getI1()).into(holder.productImage);

    }

    @NonNull
    @Override
    public ViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sale_product_sing_item,
                parent, false);
        return new ViewHolde(v);
    }

    public class ViewHolde extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView ProductName,inStock,productPrice;


        public ViewHolde(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            ProductName = itemView.findViewById(R.id.ProductName);
            inStock = itemView.findViewById(R.id.inStock);
            productPrice = itemView.findViewById(R.id.productPrice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }

    }
    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

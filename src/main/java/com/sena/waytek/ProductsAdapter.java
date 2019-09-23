package com.sena.waytek;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>{
    private Context mCtx;
    private List<Producto> productList;
    private onProductListener mOnProductListener;

    public ProductsAdapter(Context mCtx, List<Producto> productList, onProductListener onProductListener) {
        this.mCtx = mCtx;
        this.productList = productList;
        this.mOnProductListener  = onProductListener;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.product_list, parent, false);
        return  new ProductViewHolder(view, mOnProductListener);

    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Producto producto = productList.get(position);
        String message;
        //loading the image
        Glide.with(mCtx).load("http://waytek.000webhostapp.com/" + producto.getFoto_producto()).apply(new RequestOptions()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                Toast.makeText(mCtx, e.getMessage(), Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
      public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).timeout(10).into(holder.imageView);

        switch (producto.getTipo_producto()) {
            case "portátil":
                holder.textViewTitle.setText(producto.getNombre_portatil());
                break;
            case "pc escritorio":
                holder.textViewTitle.setText(producto.getNombre_pc_escritorio());
                break;
            case "presupuesto":
                holder.textViewTitle.setText(producto.getNombre_presupuesto());
                break;
            default:
                holder.textViewTitle.setText(producto.getNombre_accesorio());
                break;
        }
        holder.textViewStoreName.setText(String.valueOf(producto.getNombre_tienda()));
        holder.textViewPrice.setText("$ " + String.format("%,d", producto.getPrecio_producto()));
        if (producto.getDescuento_producto() > 0) {
            holder.textViewDiscount.setText("-"+String.valueOf(producto.getDescuento_producto())+"%");
        }
        else{
            holder.textViewDiscount.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }



    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewTitle, textViewStoreName, textViewTypeProduct, textViewPrice, textViewId, textViewDiscount;
        ImageView imageView;
        onProductListener onProductListener;

        public ProductViewHolder(View itemView, onProductListener onProductListener) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewStoreName = itemView.findViewById(R.id.textViewStoreName);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewDiscount = itemView.findViewById(R.id.textViewDiscount);
            imageView = itemView.findViewById(R.id.imageView);
            this.onProductListener = onProductListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onProductListener.onProductClick(getAdapterPosition());
        }
    }
    public interface onProductListener{
        void onProductClick(int position);
        }
}
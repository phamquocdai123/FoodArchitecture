package com.example.lenovo.demofoodarchitecture;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<OrderModel> listData = new ArrayList<>();
    private Context context;
    private double myDiscount;

    public CartAdapter(List<OrderModel> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    public CartAdapter(List<OrderModel> listData, Context context, double myDiscount) {
        this.listData = listData;
        this.context = context;
        this.myDiscount = myDiscount;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        TextDrawable drawable = TextDrawable.builder().buildRound(
                "" + listData.get(position).getQuantity(), Color.RED);
        holder.imv_cart_quantity.setImageDrawable(drawable);
        double discount = (listData.get(position).foodModel.getPrice()*
                (100 - listData.get(position).foodModel.getDiscount())) / 100;
        double price = (discount) *(Double.parseDouble(listData.get(position).getQuantity()));

        holder.txt_price.setText("VND: " +discount+" x "+listData.get(position).getQuantity()+" = "
                + price);

        double myDiscount2 = myDiscount + ((double)(listData.get(position).foodModel.getDiscount())/100.0);
        holder.txt_cart_discount.setText(""+myDiscount2*100+ "%");
        holder.txt_cart_name.setText(listData.get(position).getFoodModel().getName());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


    class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener{
        public TextView txt_cart_name,txt_price,txt_cart_discount;
        public ImageView imv_cart_quantity;
        private ItemClickListener itemClickListener;
        public void setTxt_cart_name(TextView txt_cart_name) {
            this.txt_cart_name = txt_cart_name;
        }


        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public CartViewHolder(View itemView) {
            super(itemView);
            txt_cart_name = itemView.findViewById(R.id.txt_cart_item_name);
            txt_price = itemView.findViewById(R.id.txt_cart_item_price);
            imv_cart_quantity = itemView.findViewById(R.id.imv_cart_item_count);
            txt_cart_discount = itemView.findViewById(R.id.txtDiscountCart);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View view) {

        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Chọn hành động");
            contextMenu.add(0,0,getAdapterPosition(), Common.DELETE);
        }
    }

}

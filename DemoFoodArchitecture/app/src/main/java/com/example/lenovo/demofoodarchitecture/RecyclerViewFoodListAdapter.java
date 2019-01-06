package com.example.lenovo.demofoodarchitecture;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;

public class RecyclerViewFoodListAdapter extends RecyclerView.Adapter<RecyclerViewFoodListAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<FoodModel> foodModelArrayList = new ArrayList<>();

    public RecyclerViewFoodListAdapter() {
    }
    public RecyclerViewFoodListAdapter(Context mContext, ArrayList<FoodModel> foodModelArrayList) {
        this.mContext = mContext;
        this.foodModelArrayList = foodModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final FoodModel model = foodModelArrayList.get(i);
        ViewHolder messageViewHolder = (ViewHolder) viewHolder;
        Glide.with(mContext)
                    .asBitmap()
                    .load(model.getImage())
                    .into(messageViewHolder.img);

        //messageViewHolder.img.setImageResource(model.getImage());
        messageViewHolder.txt.setText(model.getName());
        String strDouble = String.format("%.0f", model.getPrice());
        messageViewHolder.txtPrice.setText(strDouble + " đ");

        messageViewHolder.txtdiscount.setText("off: "+ String.valueOf(model.getDiscount())+ "%");
        messageViewHolder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(mContext, "Test !", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, FoodDetailActivity.class);
                intent.putExtra("activityModel", (Parcelable) model);
                mContext.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return foodModelArrayList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txt;
        TextView txtPrice,txtdiscount;
        CardView parent_layout;

        public ViewHolder( View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imvFood);
            txt = itemView.findViewById(R.id.txtName);
            txtPrice =itemView.findViewById(R.id.txtprice);
            txtdiscount = itemView.findViewById(R.id.txtdiscount);
            parent_layout = itemView.findViewById(R.id.cardviewItem);

        }
    }




}

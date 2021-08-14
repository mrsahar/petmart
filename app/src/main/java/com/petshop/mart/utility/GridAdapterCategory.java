package com.petshop.mart.utility;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.petshop.mart.R;
import com.petshop.mart.activities.ProductDetailActivity;
import com.petshop.mart.localdata.PostModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GridAdapterCategory extends RecyclerView.Adapter<GridAdapterCategory.VHMainCategory>{

    List<PostModel> postModel;
    Context context;
    Bundle b = new Bundle();
    public GridAdapterCategory() {
    }

    public GridAdapterCategory(List<PostModel> postModel, Context context) {
        this.postModel = postModel;
        this.context = context;
    }

    @NonNull
    @Override
    public VHMainCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.custom_grid_layout,parent,false);
        return new VHMainCategory(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VHMainCategory holder, int position) {


        Picasso.get().load(postModel.get(position).getPostImage()).into(holder.postImg);
        String name = postModel.get(position).getPostName();
        holder.postName.setText(name);
        holder.postPrice.setText( "Rs "+postModel.get(position).getPostPrice());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ProductDetailActivity.class);
                b.putString("ads_id", postModel.get(position).getPostId());
                i.putExtras(b);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postModel.size();
    }


    public static class VHMainCategory extends RecyclerView.ViewHolder{
        ImageView postImg;
        TextView postName,postPrice;
        CardView constraintLayout;
        public VHMainCategory(@NonNull View itemView) {
            super(itemView);
            postImg = itemView.findViewById(R.id.post_img);
            postName = itemView.findViewById(R.id.txt_post_name);
            postPrice = itemView.findViewById(R.id.txt_post_price);
            constraintLayout = itemView.findViewById(R.id.post_id);
        }
    }


}

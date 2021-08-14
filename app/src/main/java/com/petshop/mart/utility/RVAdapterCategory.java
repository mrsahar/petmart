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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.petshop.mart.R;
import com.petshop.mart.activities.CategoryActivity;
import com.petshop.mart.activities.ProductDetailActivity;

import java.util.List;

public class RVAdapterCategory extends RecyclerView.Adapter<RVAdapterCategory.VHMainCategory>{

    List<String> category_name;
    Context context;
    Bundle b = new Bundle();

    public RVAdapterCategory() {
    }

    public RVAdapterCategory(List<String> category_name, Context context) {
        this.category_name = category_name;
        this.context = context;
    }

    @NonNull
    @Override
    public VHMainCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.layout_category_hor_main,parent,false);

        return new VHMainCategory(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VHMainCategory holder, int position) {
        String name = category_name.get(position);
        if (position == 0){

            holder.categoryImg.setImageDrawable( context.getResources().getDrawable(R.drawable.ic_lion));

        }if (position ==1){
            holder.categoryImg.setImageDrawable( context.getResources().getDrawable(R.drawable.ic_bird));

        }if (position==2){
            holder.categoryImg.setImageDrawable( context.getResources().getDrawable(R.drawable.ic_fish));
        }
        holder.categoryName.setText(name);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, CategoryActivity.class);
                b.putString("category", name);
                i.putExtras(b);
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return category_name.size();
    }


    public static class VHMainCategory extends RecyclerView.ViewHolder{

        ImageView categoryImg;
        TextView categoryName;
        ConstraintLayout constraintLayout;

        public VHMainCategory(@NonNull View itemView) {
            super(itemView);
            categoryImg = itemView.findViewById(R.id.img_category);
            categoryName = itemView.findViewById(R.id.txt_category_main);
            constraintLayout = itemView.findViewById(R.id.category_link);
        }
    }
}

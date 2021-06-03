package com.petshop.mart.utility;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.petshop.mart.R;

import java.util.List;

public class RVAdapterCategory extends RecyclerView.Adapter<RVAdapterCategory.VHMainCategory>{

    List<String> category_name;


    public RVAdapterCategory() {
    }

    public RVAdapterCategory(List<String> category_name) {
        this.category_name = category_name;
        Log.d("kiki", "AdapterCategory: "+category_name.toString());
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

        holder.categoryName.setText(name);

    }

    @Override
    public int getItemCount() {
        return category_name.size();
    }


    public static class VHMainCategory extends RecyclerView.ViewHolder{

        ImageView categoryImg;
        TextView categoryName;

        public VHMainCategory(@NonNull View itemView) {
            super(itemView);
            categoryImg = itemView.findViewById(R.id.img_category);
            categoryName = itemView.findViewById(R.id.txt_category_main);
        }
    }
}

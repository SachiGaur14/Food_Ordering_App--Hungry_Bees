package com.example.foodorderingapp.Adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorderingapp.R;

import java.util.ArrayList;

import com.example.foodorderingapp.Domain.CategoryDomain;

public class CategoryAdaptor extends RecyclerView.Adapter<CategoryAdaptor.ViewHolder> {

    ArrayList<CategoryDomain> categoryDomains;

    public CategoryAdaptor(ArrayList<CategoryDomain> categoryDomains) {
        this.categoryDomains = categoryDomains;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      holder.categoryName.setText(categoryDomains.get(position).getTitle());
      String picUrl="";
      switch (position){
          case 0:{
              picUrl="pizza";
              holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.banner));
              break;
          }
          case 1:{
              picUrl="burger";
              holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.banner));
              break;
          }
          case 2:{
              picUrl="donut";
              holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.banner));
              break;
          }
          case 3:{
              picUrl="drink";
              holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.banner));
              break;
          }
          case 4:{
              picUrl="hotdog";
              holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.banner));
              break;
          }
      }
      int drawableResourceId=holder.itemView.getContext().getResources().getIdentifier(picUrl,"drawable",holder.itemView.getContext().getPackageName());


        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.categoryPic);
    }

    @Override
    public int getItemCount() {

        return categoryDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView categoryName;
        ImageView categoryPic;
        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName=itemView.findViewById(R.id.categoryName);
            categoryPic=itemView.findViewById(R.id.categoryPic);
            mainLayout=itemView.findViewById(R.id.mainLayout);
        }
    }
}

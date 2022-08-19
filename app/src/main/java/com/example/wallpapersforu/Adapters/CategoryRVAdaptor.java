package com.example.wallpapersforu.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wallpapersforu.Models.CategoryRVModel;
import com.example.wallpapersforu.R;

import java.util.ArrayList;

public class CategoryRVAdaptor extends RecyclerView.Adapter<CategoryRVAdaptor.ViewHolder> {
    private final ArrayList<CategoryRVModel> categoryRVModelArrayList;
    private final Context context;
    private final CategoryClickInterface categoryClickInterface;

    public CategoryRVAdaptor(ArrayList<CategoryRVModel> categoryRVModelArrayList, Context context, CategoryClickInterface categoryClickInterface) {
        this.categoryRVModelArrayList = categoryRVModelArrayList;
        this.context = context;
        this.categoryClickInterface = categoryClickInterface;
    }

    @NonNull
    @Override
    public CategoryRVAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.category_rv_item,parent,false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRVAdaptor.ViewHolder holder, int position) {
        CategoryRVModel categoryRVModel= categoryRVModelArrayList.get(position);
        holder.categoryTV.setText(categoryRVModel.getCategory());
        Glide.with(context).load(categoryRVModel.getCategoryIVUrl()).into(holder.categoryIV);
        holder.itemView.setOnClickListener(view -> categoryClickInterface.onCategoryClick(position));
    }

    @Override
    public int getItemCount() {
        return categoryRVModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView  categoryTV;
        private final ImageView  categoryIV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryIV = itemView.findViewById(R.id.idIVcategory);
            categoryTV =itemView.findViewById(R.id.idTVcategory);

        }
    }
    public interface CategoryClickInterface{
        void onCategoryClick(int position);
    }
}

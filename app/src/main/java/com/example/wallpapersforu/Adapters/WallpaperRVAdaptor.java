package com.example.wallpapersforu.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wallpapersforu.Activities.WallpaperActivity;
import com.example.wallpapersforu.R;

import java.util.ArrayList;

public class WallpaperRVAdaptor extends RecyclerView.Adapter<WallpaperRVAdaptor.ViewHolder> {
    private final ArrayList<String> wallpaperRVList;
    private final Context context;

    public WallpaperRVAdaptor(ArrayList<String> wallpaperRVList, Context context) {
        this.wallpaperRVList = wallpaperRVList;
        this.context = context;
    }

    @NonNull
    @Override
    public WallpaperRVAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wallpaper_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WallpaperRVAdaptor.ViewHolder holder,int position) {
        Glide.with(context).load(wallpaperRVList.get(position)).into(holder.wallpaperIV);
        holder.itemView.setOnClickListener(view -> {
            try{
            Intent i = new Intent(context, WallpaperActivity.class);
            i.putExtra("imgUrl",wallpaperRVList.get(position));
            context.startActivity(i);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    @Override
    public int getItemCount() {
        return wallpaperRVList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageView wallpaperIV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wallpaperIV = itemView.findViewById(R.id.idIVwallpaper);
        }
    }
}

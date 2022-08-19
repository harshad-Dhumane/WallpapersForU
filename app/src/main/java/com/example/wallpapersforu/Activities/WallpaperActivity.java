package com.example.wallpapersforu.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.wallpapersforu.AndroidContants.PunchZoomInOut;
import com.example.wallpapersforu.R;

import java.io.File;
import java.io.IOException;

public class WallpaperActivity extends AppCompatActivity {
    private String imgUrl;
    WallpaperManager wallpaperManager;
    DownloadManager downloadManager;
    private ImageView imageView;
    private PunchZoomInOut punchZoomInOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);
        imageView= findViewById(R.id.imageView);
        Button setWallpaperBtn = findViewById(R.id.idBtnSetWallpaper);
        Button downloadWallpaperBtn = findViewById(R.id.idBtnDownloadWallpaper);
        imgUrl = getIntent().getStringExtra("imgUrl");

        punchZoomInOut = new PunchZoomInOut(imageView,this);

        try {
            Glide
                    .with(this)
                    .load(imgUrl)
                    .apply(new RequestOptions())
                    .into(imageView);

        }catch (Exception e){
            String error = e.getMessage();
            Toast.makeText(this,error,Toast.LENGTH_LONG).show();

        }
        String filename ="Wallpaper Image";
        downloadWallpaperBtn.setOnClickListener(view -> downloadImage(filename,imgUrl));
        wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        setWallpaperBtn.setOnClickListener(view -> {
            Glide.with(WallpaperActivity.this).asBitmap().load(imgUrl).listener(new RequestListener<Bitmap>() {
                @SuppressLint("ShowToast")
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                    Toast.makeText(WallpaperActivity.this, "Failed to load", Toast.LENGTH_SHORT).show();
                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                    try {
                        wallpaperManager.setBitmap(resource);

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(WallpaperActivity.this, "Fail to set Wallpaper", Toast.LENGTH_SHORT).show();

                    }
                    return false;
                }
            }).submit();
            Toast.makeText(WallpaperActivity.this,"Wallpaper set To Home Screen",Toast.LENGTH_LONG).show();
        });
    }
    public void downloadImage(String filename, String imgUrl){
        try {
            downloadManager =null;
            downloadManager =(DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
            Uri downloadUri = Uri.parse(imgUrl);

            DownloadManager.Request request= new DownloadManager.Request(downloadUri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI)
                    .setAllowedOverRoaming(false)
                    .setTitle(filename)
                    .setMimeType("image/jpg")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, File.separator+filename+"jpg");

            downloadManager.enqueue(request);

        }catch (Exception e){
            String error = e.getMessage();
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        punchZoomInOut.myOnTouchEvent(event);
        return true;
    }
}
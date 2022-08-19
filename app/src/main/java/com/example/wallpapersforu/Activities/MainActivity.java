package com.example.wallpapersforu.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.wallpapersforu.Adapters.CategoryRVAdaptor;
import com.example.wallpapersforu.Models.CategoryRVModel;
import com.example.wallpapersforu.R;
import com.example.wallpapersforu.Adapters.WallpaperRVAdaptor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements CategoryRVAdaptor.CategoryClickInterface {
    private EditText searchEdt;
    private ProgressBar loadingPB;
    private ArrayList<String> wallpaperList;
    private ArrayList<CategoryRVModel> categoryRVModelArrayList;
    private CategoryRVAdaptor categoryRVAdaptor;
    private WallpaperRVAdaptor wallpaperRVAdaptor;
    SwipeRefreshLayout swipeRefreshLayout;



    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchEdt = findViewById(R.id.idEDTsearch);
        ImageView searchIV = findViewById(R.id.idIVsearch);
        loadingPB = findViewById(R.id.idPBloading);
        RecyclerView categoryRV = findViewById(R.id.idRVcategory);
        RecyclerView wallpaperRV = findViewById(R.id.idRVwallpaper);

        swipeRefreshLayout = findViewById(R.id.idSrl);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getwallpaper();
            wallpaperRVAdaptor.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing (false);
        });
        wallpaperList = new ArrayList<>();
        categoryRVModelArrayList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this,RecyclerView.HORIZONTAL,false);
        categoryRV.setLayoutManager(linearLayoutManager);
        categoryRVAdaptor = new CategoryRVAdaptor(categoryRVModelArrayList,this, this);
        categoryRV.setAdapter(categoryRVAdaptor);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        wallpaperRV.setLayoutManager(gridLayoutManager);
        wallpaperRVAdaptor = new WallpaperRVAdaptor(wallpaperList,this);
        wallpaperRV.setAdapter(wallpaperRVAdaptor);

        getCategory();
        getwallpaper();

        searchIV.setOnClickListener(view -> {
            String searchText = searchEdt.getText().toString();
            if(searchText.isEmpty()){
                Toast.makeText(MainActivity.this, "Please Enter Search query", Toast.LENGTH_SHORT).show();
            }else{
                getWallpaperByCategory(searchText);
            }
        });

    }
    private void getWallpaperByCategory(String category){
        wallpaperList.clear();
        loadingPB.setVisibility(View.VISIBLE);
        String url = "https://api.pexels.com/v1/search?query="+category+"&per_page=50&page";
        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            JSONArray photoArray;
            loadingPB.setVisibility(View.GONE);
            try {
                photoArray = response.getJSONArray("photos");
                for(int i =0 ; i<photoArray.length();i++){
                    JSONObject photoObj = photoArray.getJSONObject(i);
                    String imgUrl =   photoObj.getJSONObject("src").getString("portrait");
                    wallpaperList.add(imgUrl);
                }
                wallpaperRVAdaptor.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }, error -> Toast.makeText(MainActivity.this, "Fail to Load Wallpaper Try Again..", Toast.LENGTH_SHORT).show()){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String,String> headers= new HashMap<>();
                headers.put("Authorization","563492ad6f91700001000001066bca93649f4b4b89c7c09e442e3f9d");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);


    }

    @SuppressLint("NotifyDataSetChanged")
    private void getCategory() {
        categoryRVModelArrayList.add(new CategoryRVModel("Nature","https://images.pexels.com/photos/3571551/pexels-photo-3571551.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryRVModelArrayList.add(new CategoryRVModel("Animal","https://images.pexels.com/photos/1741205/pexels-photo-1741205.jpeg?auto=compress&cs=tinysrgb&dpr=3&h=750&w=126"));
        categoryRVModelArrayList.add(new CategoryRVModel("Programming","https://images.pexels.com/photos/102061/pexels-photo-102061.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260"));
        categoryRVModelArrayList.add(new CategoryRVModel("Technology","https://images.pexels.com/photos/2582937/pexels-photo-2582937.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryRVModelArrayList.add(new CategoryRVModel("Love","https://images.pexels.com/photos/873083/pexels-photo-873083.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940"));
        categoryRVModelArrayList.add(new CategoryRVModel("Travel","https://images.pexels.com/photos/4553618/pexels-photo-4553618.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryRVModelArrayList.add(new CategoryRVModel("Architecture","https://images.pexels.com/photos/4937197/pexels-photo-4937197.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryRVModelArrayList.add(new CategoryRVModel("Cars","https://images.pexels.com/photos/112460/pexels-photo-112460.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryRVModelArrayList.add(new CategoryRVModel("Flowers","https://images.pexels.com/photos/736230/pexels-photo-736230.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryRVAdaptor.notifyDataSetChanged();
    }

    private void getwallpaper() {
        wallpaperList.clear();
        loadingPB.setVisibility(View.VISIBLE);
        String url = "https://api.pexels.com/v1/curated?per_page=50&page=page";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            loadingPB.setVisibility(View.GONE);
            try {
                JSONArray photoArray = response.getJSONArray("photos");
                for (int i = 0; i < photoArray.length(); i++) {
                    JSONObject photoObj = photoArray.getJSONObject(i);
                    String imgUrl = photoObj.getJSONObject("src").getString("portrait");
                    wallpaperList.add(imgUrl);
                }
                wallpaperRVAdaptor.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(MainActivity.this,"Fail to Load Wallpapers Try Again",Toast.LENGTH_SHORT).show())
        {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String,String> headers= new HashMap<>();
                headers.put("Authorization","563492ad6f91700001000001066bca93649f4b4b89c7c09e442e3f9d");
                return headers;

            }
        };
        requestQueue.add(jsonObjectRequest);
        }



    public void onCategoryClick(int position) {
        String category = categoryRVModelArrayList.get(position).getCategory();
        getWallpaperByCategory(category);

    }


}
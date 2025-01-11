package com.adsprovider.combineadinventory;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adsprovider.combineadinventory.ad.AdmobAds;
import com.adsprovider.combineadinventory.adapter.NewsAdapter;
import com.adsprovider.combineadinventory.model.News;
import com.adsprovider.combineadinventory.util.AdLoadListener;
import com.adsprovider.combineadinventory.util.AppStart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AppStart.NewsFeedUpdateListener, AdLoadListener {

    private NewsAdapter newsAdapter;
    private ArrayList<News> newsArrayList;
    private RecyclerView newsRV;
    private FrameLayout adPlaceHolder;

    @SuppressLint({"NotifyDataSetChanged", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adPlaceHolder = findViewById(R.id.adPlaceHolder);

        newsRV = findViewById(R.id.newsRV);
        newsRV.setLayoutManager(new LinearLayoutManager(this));
        newsRV.setHasFixedSize(true);
        // Register listener to get updates when background data changes
        AppStart.setUpdateListener(this);
        // Initialize the devices list and adapter
        newsArrayList = new ArrayList<>();

        List<News> deviceList = AppStart.getNews();
        if (!deviceList.isEmpty()) {
            newsAdapter = new NewsAdapter(this, deviceList);
            newsRV.setAdapter(newsAdapter);
            newsAdapter.notifyDataSetChanged();
        }

        AdmobAds ads = new AdmobAds();
        ads.loadBannerAd(getLocalClassName(), MainActivity.this, adPlaceHolder);
    }

    @Override
    public void onNewsFeedListUpdated(List<News> updatedList) {
        if (newsAdapter != null) {
            runOnUiThread(new Runnable() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void run() {
                    newsAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public void onAdLoad(View adView) {
        // Replace ad container with new ad view.
        adPlaceHolder.removeAllViews();
        adPlaceHolder.addView(adView);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
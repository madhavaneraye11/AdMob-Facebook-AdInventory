package com.adsprovider.combineadinventory;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.adsprovider.combineadinventory.util.AppStart;
import com.bumptech.glide.Glide;

public class ArticleActivity extends AppCompatActivity {

    private TextView titleTextView;
    private TextView articleAuthor;
    private TextView descriptionTextView;
    private ImageView articleImageView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        // Find views
        titleTextView = findViewById(R.id.articleTitle);
        articleAuthor = findViewById(R.id.articleAuthor);
        descriptionTextView = findViewById(R.id.articleDescription);
        articleImageView = findViewById(R.id.articleImageView);

        // Get data from Intent
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        String author = intent.getStringExtra("author");
        String imageUrl = intent.getStringExtra("imageUrl");

        // Set data to views
        titleTextView.setText(title);
        descriptionTextView.setText(description);
        articleAuthor.setText(author);

        // Load image using Glide or Picasso
        Glide.with(this)
                .load(imageUrl)
                .into(articleImageView);

        AppStart.admobAds.loadBannerAd(getLocalClassName(),ArticleActivity.this,findViewById(R.id.bannerAdContainer));
        AppStart.admobAds.loadInterstitialAd(getLocalClassName(),ArticleActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
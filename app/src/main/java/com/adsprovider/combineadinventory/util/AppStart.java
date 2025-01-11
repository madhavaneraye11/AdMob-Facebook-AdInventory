package com.adsprovider.combineadinventory.util;

import android.app.Application;
import android.content.Context;

import com.adsprovider.combineadinventory.ad.AdmobAds;
import com.adsprovider.combineadinventory.model.News;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.FirebaseApp;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AppStart extends Application {

    private static final List<News> news = new ArrayList<>();
    private static NewsFeedUpdateListener updateListener; // Listener for updates
    public static SharedData sharedData;
    public static AdmobAds admobAds;
    public static AdConfig adConfig;
    public static AppStart context;
    public static FirebaseAnalyticsTracker firebaseAnalyticsTracker;

    @Override
    public void onCreate() {
        super.onCreate();
        context = new AppStart();

        FirebaseApp.initializeApp(this);

        new Thread(
                () -> {
                    // Initialize the Google Mobile Ads SDK on a background thread.
                    MobileAds.initialize(this, initializationStatus -> {
                    });
                    AudienceNetworkAds.initialize(this);
                    AdSettings.setTestMode(true);
                }).start();

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    getJSON();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }.start();

        initializeRemoteConfig();
        firebaseAnalyticsTracker = new FirebaseAnalyticsTracker();
        firebaseAnalyticsTracker.initFA(this);

        sharedData = new SharedData();
        adConfig = new AdConfig();
        admobAds = new AdmobAds();
    }

    private void initializeRemoteConfig() {
        FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)  // Adjust interval as needed
                .build();
        remoteConfig.setConfigSettingsAsync(configSettings);

        AdConfig.fetchAdUnitConfig();
    }

    // load JSON data
    public void getJSON() throws IOException, JSONException {
        String pubUrl = "https://saurav.tech/NewsAPI/everything/cnn.json";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(pubUrl)
                .build();
        Response response = client.newCall(request).execute();

//        try (Response response = client.newCall(request).execute()) {
        if (response.isSuccessful() && response.body() != null) {
            String jsonResponse = response.body().string();

            // Debug print to check the raw JSON response
            System.out.println("Response Body: " + jsonResponse);

            // Parse the JSON using JSONObject and JSONArray
            JSONObject jsonObject = new JSONObject(jsonResponse);

            if (jsonObject.has("articles")) {
                JSONArray articles = jsonObject.getJSONArray("articles");
                System.out.println("Articles count: " + articles.length());  // Debug print

                // Loop through each article in the articles array
                for (int i = 0; i < articles.length(); i++) {
                    JSONObject article = articles.getJSONObject(i);

                    // Extract fields: author, title, and urlToImage
                    String author = article.optString("author", "N/A");
                    String title = article.optString("title", "N/A");
                    String imageUrl = article.optString("urlToImage", "N/A");
                    String description = article.optString("description", "N/A");

                    // Debug print to check the extracted data
                    System.out.println("Extracted author: " + author);
                    System.out.println("Extracted title: " + title);
                    System.out.println("Extracted imageUrl: " + imageUrl);

                    // Add to the news list
                    news.add(new News(title, author, imageUrl,description));

                }
            } else {
            }
        } else {
        }
        if (updateListener != null) {
            updateListener.onNewsFeedListUpdated(news);
        }
    }

    public static List<News> getNews() {
        return news;
    }

    public static void setUpdateListener(NewsFeedUpdateListener listener) {
        updateListener = listener;
    }

    // Interface to notify data updates
    public interface NewsFeedUpdateListener {
        void onNewsFeedListUpdated(List<News> updatedList);
    }

    public static Context getContext() {
        return context;
    }

}


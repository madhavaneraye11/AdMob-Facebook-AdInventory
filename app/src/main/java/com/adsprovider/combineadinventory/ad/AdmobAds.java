package com.adsprovider.combineadinventory.ad;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.adsprovider.combineadinventory.util.AdConfig;
import com.adsprovider.combineadinventory.util.AppStart;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.Collections;
import java.util.List;

public class AdmobAds {

    public InterstitialAd mInterstitialAd;
    private com.facebook.ads.InterstitialAd facebookInterstitialAd;

    public void loadBannerAd(String TAG, Context context, FrameLayout adContainer) {
        String adProvider = decideAdProvider("BANNER");
        switch (adProvider) {
            case "ADMOB":
                if (AdConfig.loadBannerAd("AdMob") != null) {
                    loadAdMobBannerAd(TAG, context, adContainer);
                } else {
                    loadFallbackBannerAd(TAG, context, adContainer, "FACEBOOK");
                }
                break;
            case "FACEBOOK":
                if (AdConfig.loadBannerAd("Facebook") != null) {
                    loadBannerFacebookAd(TAG, context, adContainer);
                } else {
                    loadFallbackBannerAd(TAG, context, adContainer, "IRON_SOURCE");
                }
                break;
        }
    }

    private void loadFallbackBannerAd(String TAG, Context context, FrameLayout adContainer, String fallbackProvider) {
        switch (fallbackProvider) {
            case "ADMOB":
                loadAdMobBannerAd(TAG, context, adContainer);
                break;
            case "FACEBOOK":
                loadBannerFacebookAd(TAG, context, adContainer);
                break;
        }
    }

    public void loadInterstitialAd(String TAG, Activity activity) {
        String adProvider = decideAdProvider("INTERSTITIAL");
        switch (adProvider) {
            case "ADMOB":
                if (AdConfig.loadIndustrialAd("AdMob") != null) {
                    loadAdMobInterstitialAd(TAG, activity);
                } else {
                    loadFallbackInterstitialAd(TAG, activity, "FACEBOOK");
                }
                break;
            case "FACEBOOK":
                if (AdConfig.loadIndustrialAd("Facebook") != null) {
                    loadFacebookInterstitialAd(TAG, activity);
                } else {
                    loadFallbackInterstitialAd(TAG, activity, "IRON_SOURCE");
                }
                break;
        }
    }

    private void loadFallbackInterstitialAd(String TAG, Activity activity, String fallbackProvider) {
        switch (fallbackProvider) {
            case "ADMOB":
                loadAdMobInterstitialAd(TAG, activity);
                break;
            case "FACEBOOK":
                loadFacebookInterstitialAd(TAG, activity);
                break;
        }
    }


    private String decideAdProvider(String adType) {
        int randomValue = (int) (Math.random() * 100);
        if (randomValue < 50) {
            return "ADMOB";
        } else {
            return "FACEBOOK";
        }
    }


    // admob banner ad loader
    public void loadAdMobBannerAd(String TAG, Context context, FrameLayout adContainer) {
//        if (AppStart.sharedData.getLasAdShowed(context) != null && AppStart.sharedData.getLasAdShowed(context).equals("AdMobAd")) {
//            loadBannerFacebookAd(TAG, context, adContainer);
//            return;
//        }
        Log.d("Maddy","admobBannerADID: "+AdConfig.loadBannerAd("AdMob"));
        String admobBannerADID = AdConfig.loadBannerAd("AdMob");
        Log.d("Maddy","admobBannerADID: "+admobBannerADID);
        if (admobBannerADID == null) {
            return;
        }


        adEventSent(admobBannerADID, "AdMob", "Banner", "Ad call");
        AdView adView = new AdView(context);
        adView.setAdUnitId(admobBannerADID);
        adView.setAdSize(AdSize.BANNER);
        adContainer.removeAllViews();
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                Log.d(TAG, "Admob clicked on ad");
                adEventSent(admobBannerADID, "AdMob", "Banner", "clicked");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                Log.d(TAG, "Admob closed ad");
                adEventSent(admobBannerADID, "AdMob", "Banner", "closed");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                // Code to be executed when an ad request fails.
                Log.d(TAG, "Admob ad failed - calling facing ad");
                adEventSent(admobBannerADID, "AdMob", "Banner", "failed - request to facebook ad");
                loadBannerFacebookAd(TAG, context, adContainer);
            }

            @Override
            public void onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
                adEventSent(admobBannerADID, "AdMob", "Banner", "impression done");
                Log.d(TAG, "Admob ad impression");
            }

            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.d(TAG, "Admob ad loaded");
                adEventSent(admobBannerADID, "AdMob", "Banner", "loaded");
                adContainer.addView(adView);
                if (AppStart.sharedData.getLasAdShowed(context) == null ||
                        (AppStart.sharedData.getLasAdShowed(context) != null &&
                                AppStart.sharedData.getLasAdShowed(context).equals("FacebookAd"))) {
                    AppStart.sharedData.setLastAdShowed(context, "AdMobAd");
                }
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                adEventSent(admobBannerADID, "AdMob", "Banner", "opened");
                Log.d(TAG, "Admob ad opened");
            }
        });
    }

    // facebook banner ad loader
    public void loadBannerFacebookAd(String TAG, Context context, FrameLayout adContainer) {

        String facebookBannerADID = AdConfig.loadBannerAd("Facebook");

        if (facebookBannerADID == null) {
            return;
        }

        adEventSent(facebookBannerADID, "Facebook", "Banner", "Call");

        AdSettings.setTestMode(true);
        com.facebook.ads.AdView adView = new com.facebook.ads.AdView(context, facebookBannerADID, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
        // Request an ad
        adView.loadAd();
        adContainer.addView(adView);
        com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.d(TAG, "Facebook ad error");
                adEventSent(facebookBannerADID, "Facebook", "Banner", "failed request send to admob");
                loadAdMobBannerAd(TAG, context, adContainer);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Ad loaded callback
                Log.d(TAG, "Facebook ad loaded");
                adEventSent(facebookBannerADID, "Facebook", "Banner", "load");

                if (AppStart.sharedData.getLasAdShowed(context) != null && AppStart.sharedData.getLasAdShowed(context).equals("AdMobAd")) {
                    AppStart.sharedData.setLastAdShowed(context, "FacebookAd");
                }
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                adEventSent(facebookBannerADID, "Facebook", "Banner", "Clicked");
                Log.d(TAG, "Facebook ad clicked");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                // Please refer to Monetization Manager or Reporting API for final impression numbers
                adEventSent(facebookBannerADID, "Facebook", "Banner", "impression");
                Log.d(TAG, "Facebook ad impression");
            }
        };

        // Request an ad
        adView.loadAd(adView.buildLoadAdConfig().withAdListener(adListener).build());
    }


    public void loadAdMobInterstitialAd(String TAG, Activity activity) {

        String loadAdMobInterstitialAd = AdConfig.loadIndustrialAd("AdMob");
        if (loadAdMobInterstitialAd == null) {
            return;
        }

        adEventSent(loadAdMobInterstitialAd, "AdMob", "Interstitial", "Call");

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(activity, loadAdMobInterstitialAd, adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        adEventSent(loadAdMobInterstitialAd, "AdMob", "Interstitial", "Loaded");
                        mInterstitialAd = interstitialAd;
                        mInterstitialAd.show(activity);
                        Log.i(TAG, "onAdLoaded");
                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdClicked() {
                                super.onAdClicked();
                                adEventSent(loadAdMobInterstitialAd, "AdMob", "Interstitial", "Clicked");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                adEventSent(loadAdMobInterstitialAd, "AdMob", "Interstitial", "onAdDismissedFullScreenContent");
                                super.onAdDismissedFullScreenContent();
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                                adEventSent(loadAdMobInterstitialAd, "AdMob", "Interstitial", "onAdFailedToShowFullScreenContent");
                                super.onAdFailedToShowFullScreenContent(adError);

                            }

                            @Override
                            public void onAdImpression() {
                                adEventSent(loadAdMobInterstitialAd, "AdMob", "Interstitial", "onAdImpression");
                                super.onAdImpression();
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                adEventSent(loadAdMobInterstitialAd, "AdMob", "Interstitial", "onAdShowedFullScreenContent");
                                super.onAdShowedFullScreenContent();
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        adEventSent(loadAdMobInterstitialAd, "AdMob", "Interstitial", "onAdFailedToLoad");
                        Log.d(TAG, loadAdError.toString());
                        mInterstitialAd = null;
                        loadFacebookInterstitialAd(TAG, activity);
                    }
                });
    }

    public void loadFacebookInterstitialAd(String TAG, Activity activity) {

        String facebookInterstitialAdID = AdConfig.loadIndustrialAd("Facebook");

        facebookInterstitialAd = new com.facebook.ads.InterstitialAd(activity, facebookInterstitialAdID);
        // Create listeners for the Interstitial Ad
        com.facebook.ads.InterstitialAdListener interstitialAdListener = new com.facebook.ads.InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e(TAG, "Interstitial ad displayed.");
                adEventSent(facebookInterstitialAdID, "Facebook", "Interstitial", "onInterstitialDisplayed");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e(TAG, "Interstitial ad dismissed.");
                adEventSent(facebookInterstitialAdID, "Facebook", "Interstitial", "onInterstitialDismissed");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
                adEventSent(facebookInterstitialAdID, "Facebook", "Interstitial", "onError");
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
                facebookInterstitialAd.show();
                adEventSent(facebookInterstitialAdID, "Facebook", "Interstitial", "onAdLoaded");
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d(TAG, "Interstitial ad clicked!");
                adEventSent(facebookInterstitialAdID, "Facebook", "Interstitial", "onAdClicked");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                // Please refer to Monetization Manager or Reporting API for final impression numbers
                Log.d(TAG, "Interstitial ad impression logged!");
                adEventSent(facebookInterstitialAdID, "Facebook", "Interstitial", "onLoggingImpression");
            }
        };

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        facebookInterstitialAd.loadAd(
                facebookInterstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());
    }

    public void adEventSent(String adUnitID, String adProvider, String adType, String operation) {
        AppStart.firebaseAnalyticsTracker.updateAdOperationPerformEvent(adUnitID, adProvider, adType, operation);
    }
}

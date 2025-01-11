package com.adsprovider.combineadinventory.util;

import android.util.Log;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AdConfig {

    private static final String AD_UNIT_CONFIG_KEY = "ad_unit_config";
    private static Map<String, Map<String, String>> adUnitMap;

    public static void fetchAdUnitConfig() {
        FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        remoteConfig.fetchAndActivate()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String jsonConfig = remoteConfig.getString(AD_UNIT_CONFIG_KEY);
                        Log.e("AdConfigManager", "jsonConfig : "+jsonConfig);

                        parseAdUnitConfig(jsonConfig);
                    } else {
                        // Handle fetch failure
                        Log.e("AdConfigManager", "Failed to fetch ad unit config");
                    }
                });
    }

    private static void parseAdUnitConfig(String jsonConfig) {
        try {
            JSONObject jsonObject = new JSONObject(jsonConfig);
            adUnitMap = new HashMap<>();
            for (Iterator<String> it = jsonObject.keys(); it.hasNext();) {
                String network = it.next();
                Log.e("AdConfigManager", "network"+network);
                JSONObject networkConfig = jsonObject.getJSONObject(network);
                Log.e("AdConfigManager", "networkConfig"+networkConfig);
                Map<String, String> placements = new HashMap<>();
                for (Iterator<String> keys = networkConfig.keys(); keys.hasNext();) {
                    String placement = keys.next();
                    placements.put(placement, networkConfig.getString(placement));
                }
                adUnitMap.put(network, placements);
            }
        } catch (JSONException e) {
            Log.e("AdConfigManager", "Error parsing ad unit config", e);
        }
    }

    public static Map<String, String> getAdUnitsForNetwork(String network) {
        return adUnitMap != null ? adUnitMap.get(network) : null;
    }

    public static String loadBannerAd(String adNetwork) {
        Map<String, String> adUnits = getAdUnitsForNetwork(adNetwork);
        if (adUnits != null && adUnits.containsKey("banner")) {
            // Initialize the ad using the adUnitId
            return adUnits.get("banner");
        } else {
            Log.e("maddy", "Banner ad unit not found for " + adNetwork);
            return null;
        }
    }

    public static String loadIndustrialAd(String adNetwork) {
        Map<String, String> adUnits = getAdUnitsForNetwork(adNetwork);
        if (adUnits != null && adUnits.containsKey("interstitial")) {
            // Initialize the ad using the adUnitId
            return adUnits.get("interstitial");
        } else {
            Log.e("maddy", "Banner ad unit not found for " + adNetwork);
            return null;
        }
    }
}

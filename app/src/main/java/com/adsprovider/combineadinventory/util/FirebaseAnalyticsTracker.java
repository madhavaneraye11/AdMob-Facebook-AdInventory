package com.adsprovider.combineadinventory.util;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;

public class FirebaseAnalyticsTracker {


    public FirebaseAnalytics mFirebaseAnalytics;

    public void initFA(Context context){
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    public void updateAdOperationPerformEvent(String adUnitID, String adProviderName, String adType, String adOperation){
        Bundle bundle = new Bundle();
        bundle.putString("AD_UNIT_ID", adUnitID);
        bundle.putString("AD_PROVIDER_NAME", adProviderName);
        bundle.putString("AD_TYPE", adType);
        bundle.putString("AD_OPERATION", adOperation);
        Log.d("FirebaseAnalyticsEvent",bundle.toString());
        FirebaseAnalytics.getInstance(AppStart.context).logEvent(adProviderName, bundle);
    }
}

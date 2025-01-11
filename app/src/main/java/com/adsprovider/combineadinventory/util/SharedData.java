package com.adsprovider.combineadinventory.util;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedData {

    public void setLastAdShowed(Context context,String ad){
        // Creating a shared pref object with a file name "MySharedPref" in private mode
        SharedPreferences sharedPreferences = context.getSharedPreferences("AdInventorySP", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        // write all the data entered by the user in SharedPreference and apply
        myEdit.putString("lastAdProvider", ad);
        myEdit.apply();
    }

    public String getLasAdShowed(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("AdInventorySP", MODE_PRIVATE);
        return sharedPreferences.getString("lastAdProvider", null);
    }
}

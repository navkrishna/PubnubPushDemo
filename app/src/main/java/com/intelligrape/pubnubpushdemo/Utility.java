package com.intelligrape.pubnubpushdemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Navkrishna on November 20, 2014
 */
public class Utility {
    public static final String APP_PREF = "Pubnub Demo Pref";

    public static int getAppVersion(Context mContext) {
        try {
            PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getInt(Context context, String key) {
        SharedPreferences preference = context.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        return preference.getInt(key, 0);
    }

    public static void setInt(Context context, String key,
                              int value) {
        SharedPreferences preference = context.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static String getString(Context context, String key) {
        SharedPreferences preference = context.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        return preference.getString(key, "");
    }

    public static void setString(Context context, String key, String value) {
        SharedPreferences preference = context.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(key, value);
        editor.commit();
    }
}

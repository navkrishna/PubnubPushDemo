package com.intelligrape.pubnubpushdemo;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;

/**
 * Created by Navkrishna on November 18, 2014
 */
public class PubnubUtils {
    private static Pubnub pubnub;

    public static Pubnub getPubnubInstance() {
        if (pubnub == null)
            pubnub = new Pubnub(AppConstants.PUBLISH_KEY, AppConstants.SUBSCRIBER_KEY);
        return pubnub;
    }

    public static void removePushFromAllChannel(Context mContext) {
        String gcmRegistrationId = Utility.getString(mContext, AppConstants.KEY_GCM_REG_ID);
        getPubnubInstance().removeAllPushNotificationsForDeviceRegistrationId(gcmRegistrationId);
    }

    public static void gcmAddChannel(Context mContext, String channel) {
        String gcmRegistrationId = Utility.getString(mContext, AppConstants.KEY_GCM_REG_ID);
        if (TextUtils.isEmpty(gcmRegistrationId)) {
            Toast.makeText(mContext, "GCM Registration id not set. Register to GCM and try again.", Toast.LENGTH_LONG).show();
            return;
        }
        getPubnubInstance().enablePushNotificationsOnChannel(channel, gcmRegistrationId, new Callback() {
        });
    }
}

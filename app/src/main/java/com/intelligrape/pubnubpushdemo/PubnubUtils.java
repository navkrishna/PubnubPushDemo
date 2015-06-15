package com.intelligrape.pubnubpushdemo;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.widget.Toast;

import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;

/**
 * Created by Navkrishna on November 18, 2014
 */
public class PubnubUtils {
    private static Pubnub pubnub;

    public static Pubnub getPubnubInstance(Context context) {
        if (pubnub == null) {
            Resources resources = context.getResources();
            pubnub = new Pubnub(resources.getString(R.string.pubnub_key_publish), resources.getString(R.string.pubnub_key_subscribe));
        }
        return pubnub;
    }

    public static void removePushFromAllChannel(Context context) {
        String gcmRegistrationId = Utility.getString(context, AppConstants.KEY_GCM_REG_ID);
        getPubnubInstance(context).removeAllPushNotificationsForDeviceRegistrationId(gcmRegistrationId);
    }

    public static void gcmAddChannel(Context context, String channel) {
        String gcmRegistrationId = Utility.getString(context, AppConstants.KEY_GCM_REG_ID);
        if (TextUtils.isEmpty(gcmRegistrationId)) {
            Toast.makeText(context, "GCM Registration id not set. Register to GCM and try again.", Toast.LENGTH_LONG).show();
            return;
        }
        getPubnubInstance(context).enablePushNotificationsOnChannel(channel, gcmRegistrationId, new Callback() {
        });
    }
}

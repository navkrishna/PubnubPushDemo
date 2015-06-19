package com.intelligrape.pubnubpushdemo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class MainActivity extends Activity {
    private Activity mActivity = MainActivity.this;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        checkGCMStatus();
    }

    private void initViews() {
        final EditText editTextChannel = (EditText) findViewById(R.id.edittext_channel);
        Button buttonSubscribe = (Button) findViewById(R.id.button_subscribe);

        buttonSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String channelName = editTextChannel.getText().toString().trim();
                PubnubUtils.gcmAddChannel(mActivity, channelName);
                Utility.setString(mActivity, AppConstants.KEY_CHANNEL_NAME, channelName);
            }
        });
    }

    private void checkGCMStatus() {
        String registrationId = getRegistrationId();
        if (TextUtils.isEmpty(registrationId)) {
            new GCMRegisterAsyncTask().execute();
            Log.d(TAG, "successfully registered with GCM server - regId: " + registrationId);
        } else {
            Log.i(TAG, "RegId already available. RegId: " + registrationId);
        }
    }

    private String getRegistrationId() {
        String registrationId = Utility.getString(mActivity, AppConstants.KEY_GCM_REG_ID);
        if (registrationId.length() <= 0) {
            Log.i(TAG, "Registration not found.");
            registrationId = "";
        }
        int registeredVersion = Utility.getInt(mActivity, AppConstants.KEY_APP_VERSION);
        int currentVersion = Utility.getAppVersion(mActivity);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            registrationId = "";
        }
        return registrationId;
    }

    public class GCMRegisterAsyncTask extends AsyncTask<Void, Void, String> {
        private String TAG = "GCMRegisterAsyncTask";
        private GoogleCloudMessaging gcm;

        @Override
        protected String doInBackground(Void... params) {
            String msg;
            try {
                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(mActivity);
                }
                String gcmRegistrationId = gcm.register(getResources().getString(R.string.gcm_sender_id));
                Log.d(TAG, "registerInBackground - regId: " + gcmRegistrationId);
                msg = "Device registered, registration ID=" + gcmRegistrationId;
                storeRegistrationId(gcmRegistrationId);
            } catch (Exception ex) {
                msg = "Error :" + ex.getMessage();
                Log.d(TAG, "Error: " + msg);
            }
            Log.d(TAG, "AsyncTask completed: " + msg);
            return msg;
        }

        @Override
        protected void onPostExecute(String msg) {
            PubnubUtils.removePushFromAllChannel(mActivity);
        }

        private void storeRegistrationId(String regId) {
            int appVersion = Utility.getAppVersion(mActivity);
            Log.i(TAG, "Saving regId on app version " + appVersion);
            Utility.setString(mActivity, AppConstants.KEY_GCM_REG_ID, regId);
            Utility.setInt(mActivity, AppConstants.KEY_APP_VERSION, appVersion);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

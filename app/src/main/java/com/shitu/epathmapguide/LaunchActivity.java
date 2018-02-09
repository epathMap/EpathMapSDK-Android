package com.shitu.epathmapguide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.shitu.epathmapguide.common.GlobalShare;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skipActivity();
    }

    private void skipActivity() {
        new Handler().postDelayed(() -> {
            SharedPreferences sharedPreferences = getSharedPreferences("epathMap", Context.MODE_PRIVATE);
            int alreadyOpen = sharedPreferences.getInt(GlobalShare.ALREADY_OPEN, 0);
            Intent intent = new Intent();
            if (alreadyOpen == 0) {
                intent.setClass(this, GuideActivity.class);
            } else {
                intent.setClass(this, MainActivity.class);
            }
            startActivity(intent);
            this.finish();
        }, 2000);
    }
}

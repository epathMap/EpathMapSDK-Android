package com.shitu.epathmapguide;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import android.view.KeyEvent;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shitu.epathmap.EpathMapSDK;
import com.shitu.epathmapguide.adapter.CustomerMapAdapter;
import com.shitu.epathmapguide.app.CustomerApplication;
import com.shitu.epathmapguide.common.ViewUtils;
import com.shitu.epathmapguide.data.MapData;
import com.shitu.location.epathmap.EpathClient;
import com.shitu.location.epathmap.EpathLocation;
import com.shitu.location.epathmap.EpathLocationListener;
import com.shitu.location.epathmap.GetCallBack;
import com.shitu.location.epathmap.utils.T;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private long firstime;
    private CustomerMapAdapter customerMapAdapter;
    private List<MapData> customerMapData;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    protected static final int MY_PERMISSIONS_REQUEST_WRITE_SD_AND_RECORD_AUDIO = 0x03;
    protected static final int MY_PERMISSIONS_REQUEST_FINE_LCOATION = 0x04;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        swipeRefreshLayout = findViewById(R.id.swipe_layout);
        recyclerView = findViewById(R.id.recyclerView);
        ViewUtils.initRecycleView(recyclerView, this, 30);
        ViewUtils.initRefreshLayout(swipeRefreshLayout, this);
        initAdapter();
        onRefresh();
    }

    private void initAdapter() {
        if (customerMapAdapter == null) {
            customerMapData = new ArrayList<>();
            customerMapAdapter = new CustomerMapAdapter(R.layout.item_customer_data, customerMapData);
            customerMapAdapter.openLoadAnimation();
            customerMapAdapter.setOnItemClickListener((adapter1, view, position) -> {


                        String mapId = customerMapData.get(position).getObjectId();
                        EpathClient epathClient = new EpathClient(MainActivity.this, mapId);

                        epathClient.registerLocationListener(new EpathLocationListener() {
                            @Override
                            public void onReceiveLocation(EpathLocation ipsLocation) {
                                if (ipsLocation.isInThisMap()) {
                                    //获取本地定位成功，去获取是否在目的建筑物范围内
                                    epathClient.startCheckLocationRange(mapId, "yinshuiji");
                                }
                            }
                            @Override
                            public void onReceiveRange(boolean isInclude) {
                                if (isInclude) {
                                    Toast.makeText(MainActivity.this, "打卡成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "打卡失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        epathClient.start();

//                        EpathMapSDK.openEpathMapActivity(MainActivity.this, customerMapData.get(position).getObjectId());
                    }

            );


            recyclerView.setAdapter(customerMapAdapter);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        EpathMapSDK.shareLinkToMapView(intent);
    }

    private boolean isFirstRefresh = true;

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        EpathMapSDK.getMapList(new GetCallBack() {
            @Override
            public void onSuccess(String jsonString) {
                if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
//                list.clear();
//                list.addAll(new Gson().fromJson(jsonString, new TypeToken<List<MapData>>() {
//                }.getType()));
//                adapter.notifyDataSetChanged();
                if (customerMapData.size() != 0) customerMapData.clear();
                customerMapData.addAll(new Gson().fromJson(jsonString, new TypeToken<List<MapData>>() {
                }.getType()));
                customerMapAdapter.notifyDataSetChanged();
                if (isFirstRefresh) {
                    EpathMapSDK.shareLinkToMapView(getIntent());
                    isFirstRefresh = false;
                }
            }

            @Override
            public void onError(Exception e) {
                swipeRefreshLayout.setRefreshing(false);
                e.printStackTrace();
            }
        });
    }

    /**
     * 多权限判断
     *
     * @param permissons
     * @return
     */
    public boolean hasPermission(String... permissons) {
        for (String permisson : permissons) {
            if ((ContextCompat.checkSelfPermission(this,
                    permisson) != PackageManager.PERMISSION_GRANTED)) {
                return false;
            }
        }
        return true;
    }

    // 权限申请
    public void requestPermission(int requestCode, String... permissions) {
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(permissions, requestCode);
        }
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_SD_AND_RECORD_AUDIO:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    gotoNextActivity();
                    //doSDCardAndRecordAudio();
                } else {
                    T.showShort(R.string.please_grant_permission);
                    Intent appDetailSettingIntent = getAppDetailSettingIntent();
                    startActivity(appDetailSettingIntent);
                }
                break;
            case MY_PERMISSIONS_REQUEST_FINE_LCOATION:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        if (grantResult == PackageManager.PERMISSION_DENIED) {
                            T.showShort(R.string.please_grant_permission);
                            Intent appDetailSettingIntent = getAppDetailSettingIntent();
                            startActivity(appDetailSettingIntent);
                            finish();
                        }
                    }
                    startUploadLocationService();

                }

        }
    }

    public void startUploadLocationService() {

    }


    protected void gotoNextActivity() {

    }

    private Intent getAppDetailSettingIntent() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        return localIntent;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            long secondtime = System.currentTimeMillis();
            if (secondtime - firstime > 3000) {
                Toast.makeText(MainActivity.this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
                firstime = System.currentTimeMillis();
                return true;
            } else {
                System.exit(0);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }
        return true;
    }
}

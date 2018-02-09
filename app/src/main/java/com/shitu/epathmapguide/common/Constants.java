package com.shitu.epathmapguide.common;

import android.content.Context;

/**
 * Created by liberty on 2017/3/31.
 */

public class Constants {
    //    public static final String IPSMAP_APP_KEY = "DgsknuQTh0";
    //    public static final String IPSMAP_APP_KEY_DEBUG = "PseARAgTYF";
    //    public static final String IPSMAP_MAP_ID = "lGaWCUtqoj";
    //    public static final String WECHAT_APP_ID = "wxfb94585e6d4df00c";

    public static final String EPATHMAP_APP_KEY = "usKE9GFeLp";
    public static final String EPATHMAP_APP_KEY_DEBUG = "usKE9GFeLp";
    public static final String EPATHMAP_MAP_ID = "0HDaWonRnA";
    public static final String WECHAT_APP_ID = "wx7c84e117117c977b";

//    public static final String IPSMAP_APP_KEY = "DgsknuQTh0";
//    public static final String IPSMAP_APP_KEY_DEBUG = "PseARAgTYF";
//    public static final String WECHAT_APP_ID = "wxfb94585e6d4df00c";
//    public static final String IPSMAP_MAP_ID = "lGaWCUtqoj";

    public static int getVersionCode(Context context) {
        try {
            String pkName = context.getPackageName();
            int versionCode = context.getPackageManager().getPackageInfo(
                    pkName, 0).versionCode;
            return versionCode;
        } catch (Exception e) {
        }
        return 0;
    }

    public static String getVersionName(Context context) {
        try {
            String pkName = context.getPackageName();
            String versionName = context.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            return versionName;
        } catch (Exception e) {
        }
        return "0";
    }

}

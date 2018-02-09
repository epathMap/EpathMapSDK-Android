package com.shitu.epathmapguide.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.bugtags.library.Bugtags;
import com.bugtags.library.BugtagsOptions;

import com.shitu.epathmap.EpathMapSDK;
import com.shitu.epathmap.ShareToWechatListener;
import com.shitu.epathmapguide.BuildConfig;
import com.shitu.epathmapguide.common.Constants;
import com.shitu.location.epathmap.utils.T;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * author:lfei
 */
public class CustomerApplication extends Application implements ShareToWechatListener {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        if (BuildConfig.DEBUG) {
            EpathMapSDK.init(new EpathMapSDK.Configuration.Builder(context)
                    .appKey(Constants.EPATHMAP_APP_KEY_DEBUG)
                    .shareToWechatListener(this)
                    .debug(BuildConfig.DEBUG)
                    .build());
        } else {
            EpathMapSDK.init(new EpathMapSDK.Configuration.Builder(context)
                    .appKey(Constants.EPATHMAP_APP_KEY)
                    .shareToWechatListener(this)
                    .debug(BuildConfig.DEBUG)
                    .build());
        }

    }

    @Override
    public void shareToWechat(String url, String title, String description, Bitmap bitmap) {
        try {
            IWXAPI wxApi = WXAPIFactory.createWXAPI(context, Constants.WECHAT_APP_ID);
            wxApi.registerApp(Constants.WECHAT_APP_ID);
            if (!wxApi.isWXAppInstalled()) {
                T.showShort("未安装微信");
                return;
            }
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = url;
            WXMediaMessage msg = new WXMediaMessage(webpage);
            msg.title = title;
            msg.description = description;
            msg.setThumbImage(bitmap);
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("webpage");
            req.message = msg;
            req.scene = SendMessageToWX.Req.WXSceneSession;
            wxApi.sendReq(req);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}

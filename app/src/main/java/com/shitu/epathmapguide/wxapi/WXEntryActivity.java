package com.shitu.epathmapguide.wxapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;


import com.shitu.epathmapguide.R;
import com.shitu.epathmapguide.common.Constants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler, View.OnClickListener {

    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transparent);
        findViewById(R.id.ll_root).setOnClickListener(this);
        api = WXAPIFactory.createWXAPI(this,
                Constants.WECHAT_APP_ID, false);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        //微信发送请求到你的应用，将通过IWXAPIEventHandler接口的onReq方法进行回调
    }

    @Override
    public void onResp(BaseResp baseResp) {
        //应用请求微信的响应结果将通过onResp回调。
        if (baseResp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {
            switch (baseResp.errCode) {
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    Toast.makeText(this,"取消分享",Toast.LENGTH_SHORT);
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_OK:
                    Toast.makeText(this,"分享成功",Toast.LENGTH_SHORT);
                    finish();
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_root) {
            finish();
        }
    }
}

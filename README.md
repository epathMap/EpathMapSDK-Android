# EpathMapGuide
# EpathmapSDK-Android


EpathmapSDK-Android 是一套基于 Android 4.3 及以上版本的室内地图应用程序开发接口，供开发者在自己的Android应用中加入室内地图相关的功能，包括：地图显示（多楼层、多栋楼）、室内导航、模拟导航、语音播报等功能。

## 获取AppKey和MapId
请联系 [service@e-path.cn](service@e-path.cn)

## 添加依赖

compile ('com.shitu.location:epathmap:1.5.1', {
        exclude group: 'com.android.support'
    })


## 目前支持的cpu 架构 arm,暂时不支持其他架构,请配置下面的cpu架构
ndk {
            // 设置支持的 SO 库构架
            abiFilters 'armeabi'
}

## 加入权限
导入EpathmapSDK后需要


     <!-- 注意新增的权限,注意添加-->
        <uses-permission android:name="android.permission.VIBRATE" />
         <!-- sdk 使用需要的权限 -->
         <!-- if use wifi indoor positioning -->
         <uses-permission android:name="android.permission.INTERNET" />
         <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
         <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
         <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
         <!-- if use ble indoor positioning -->
         <uses-permission android:name="android.permission.BLUETOOTH" />
         <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
         <!-- general permission -->
         <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
         <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
         <!-- 连接网络权限，用于执行云端语音能力 -->
         <!-- 获取手机录音机使用权限，听写、识别、语义理解需要用到此权限 -->
         <uses-permission android:name="android.permission.RECORD_AUDIO" />
         <!-- 读取网络信息状态 -->
         <!-- 允许程序改变网络连接状态 -->
         <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" />
         <!-- 读取手机信息权限 -->
         <uses-permission android:name="android.permission.READ_PHONE_STATE" />
         <!-- 读取联系人权限，上传联系人需要用到此权限 -->
         <uses-permission android:name="android.permission.READ_CONTACTS" />
         <!-- 外存储写权限，构建语法需要用到此权限 -->
         <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
         <!-- 外存储读权限，构建语法需要用到此权限 -->
         <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
         <!-- 配置权限，用来记录应用配置信息 -->
         <uses-permission android:name="android.permission.WRITE_SETTINGS" />
         <uses-permission android:name="android.permission.READ_LOGS" />
         <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
         <uses-permission android:name="android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS" />
         <!-- 判断程序是否在前台运行,必须 -->
         <uses-permission android:name="android.permission.GET_TASKS" />
         <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS" />
         <uses-permission android:name="android.permission.VIBRATE" />
```

## 使用
初始化

在Application 的onCreate 方法中进行初始化
``` 
    使用默认配置信息
    EpathMapSDK.init(context, EPATHMAP_APP_KEY);
    或
    定制配置信息 ,使用微信分享功能请实现相关的接口
    EpathMapSDK.init(new EpathMapSDK.Configuration.Builder(context)
                .appKey(Constants.EPATHMAP_APP_KEY)
                .shareToWechatListener(this)
                //正式版请关闭 默认是关闭的
                .debug(false)
                .build());
                

```
SDK内部实现了分享功能，使用的前提是需要申请微信的appkey，并且需要实现接口ShareToWechatListener接口
参考代码如下：
```

    参考代码
   @Override
    public void shareToWechat(String url, String title, String description, Bitmap bitmap) {
        try {
            IWXAPI wxApi = WXAPIFactory.createWXAPI(this, "YOUR WECHAT APP_ID");
            wxApi.registerApp("YOUR WECHAT APP_ID");
            if (!wxApi.isWXAppInstalled()) {
                Toast.makeText(this, "未安装微信", Toast.LENGTH_SHORT).show();
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
                

```

```
将微信分享通过浏览器打开的acitivty 中加入配置 ,建议新建一个界面,不要现有的逻辑冲突.
这个界面的功能一个中转的功能,是通过浏览器唤起这个界面,这个界面打开地图.
<!--微信分享-->
    <intent-filter>
        <action android:name="android.intent.action.VIEW" />
        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />
        <data
            android:host="share"
            android:scheme=你的scheme></data>
    </intent-filter>
<!--微信分享结束-->

重写以下两个方法
  @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        //如果不是新建的页面判断一下scheme
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                EpathMapSDK.shareLinkToMapView(getIntent());
                finish();
            }
        }, 500);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        EpathMapSDK.shareLinkToMapView(intent);
        finish();
    }


启动地图
EpathMapSDK.openEpathMapActivity(context, map_id);
或
EpathMapSDK.openEpathMapActivity(context, map_id, target_id);


定位监听,获取当前的位置,可以参考ipslocation demo ,需要提前获取定位和蓝牙权限
```
epathClient = new EpathClient(context, map_id);
epathClient.registerLocationListener(new EpathLocationListener() {
    @Override
    public void onReceiveLocation(EpathLocation ipsLocation){
    if(epathLocation == null){
        //定位失败;
        return;
    }
    //是否在Map内
    epathLocation.isInThisMap()

    }
});
epathClient.start();


activity 结束时调用

@Override
protected void onDestroy() {
    super.onDestroy();
    epathClient.stop();
}


## 混淆

-dontwarn com.baidu.**
-keep class com.baidu.** {*;}
-dontwarn com.iflytek.**
-keep class com.iflytek.**{*;}
-keep public class com.sails.engine.patterns.IconPatterns
```


微信分享以及复制跳转请参考demo
## FAQ
1.0

出现上面的类似xml资源文件缺失的情况:
两种解决方案:
1. 在通过gradle 引用是加入exclude group: 'com.android.support' ,并且自己加入compile 'com.android.support:appcompat-v7:版本号'
建议方式.建议版本号25.3.1
2. 修改项目的support 支持和  compile 'com.android.support:appcompat-v7:25.3.1' 版本号一致

2.0 

app如果使用了okhttp ,glide ...出现第三发开源库 冲突
两种解决方案:
1.通过  exclude group: "com.squareup.okhttp3" 方式处理
然后保留项目的okhttp和glide 
2.保持和sdk的一致引入的第三方库版本号一致.否则有可能出现冲突

"glide"             : "com.github.bumptech.glide:glide:3.7.0",
"okhttp"            : "com.squareup.okhttp3:okhttp:3.8.0",
"gson"              : "com.google.code.gson:gson:2.8.2",


 3.0

    allprojects {
        repositories {
            jcenter()
            maven { url "https://jitpack.io" }
            flatDir {
                dirs 'libs'
            }
        }
    }
    
    compileOptions {
         sourceCompatibility JavaVersion.VERSION_1_8
         targetCompatibility JavaVersion.VERSION_1_8
     }



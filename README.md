[![Maven Central](https://maven-badges.herokuapp.com/maven-central/net.bingosoft.oss/linksdk/badge.svg)](https://maven-badges.herokuapp.com/maven-central/net.bingosoft.oss/linksdk/badge.svg) [![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

# 品高聆客(Link)OpenSDK

为第三方Android应用接入Link平台提供便捷的方式，默认读者已经熟悉IDE的基本使用方法（Android Studio），以及对Link平台有一定了解，并且已经申请了对应的 ClientId 和 ClientSecret 等。

## 安装

在Android Studio中新建工程。在Module的`build.gradle`文件中，添加如下依赖：

```
dependencies {
    compile 'net.bingosoft.oss:linksdk:1.0.0'
}
```

耐心等待gradle同步完成。

## 使用
[1] AndroidManifest.xml 设置

添加必要的权限支持:

```
<!-- 网络请求权限 -->
<uses-permission android:name="android.permission.INTERNET" />
```

[2] 注册到Link平台

要使程序启动后Link终端能响应你的程序，必须在代码中向Link终端注册你的ClientId。这里推荐在`Application`级别注册（或者其他Activity级别注册），示例参考如下：

```java
public class BaseApplication extends Application {
	//ILinkAPI是第三方app与Link通讯的openapi接口
	public ILinkAPI ilinkapi;
	publicString clientId = "linkclent66666";
    @Override
    public void onCreate() {
        super.onCreate();
		 //获取ILinkAPI实例并注册
        ilinkapi = LinkAPIFactory.createLinkAPI(this);
        ilinkapi.registerClient(clientId);
    }
}


```

[3] 发送请求到Link终端 

现在，你的程序想要发送请求到Link终端，可以通过`ILinkAPI`里面的`sendReq`方法来实现。

```java
void sendReq(Context context,BaseReq req);
```
sendReq是第三方app主动发送请求给Link终端，发送完成之后会切回到第三方app界面。

[4] 接收Link的响应结果

如果你的程序需要接收Link发送的响应结果（回调），那么需要下面3步操作：

A. 新增一个`LinkEntryActivity`类，该类实现`ILinkAPIEventHandler`接口。
图片

在`AndroidManifest.xml`文件中配置如下，注意action是固定不变的，用于Link终端回调找回第三方app：

```
<activity android:name=".LinkEntryActivity">
    <intent-filter>
       <action android:name="net.bingosoft.oss.linkauth" />
         <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
</activity>
```

B. 实现`ILinkAPIEventHandler`接口，发送到Link请求的响应结果将回调到onResp方法，Link发送的请求将回调到onReq方法(暂不支持，无需实现)。

```java
@Override
public void onReq(BaseReq req) {
	//todo 暂时不支持，无需实现
}

@Override
public void onResp(BaseResp resp) {
	//接收来自Link的回调结果
	//例如 AuthResp(继承自BaseResp)里面可以获取Link授权的结果
}
```

C. 在`LinkEntryActivity`中将接收到的intent及实现了`ILinkAPIEventHandler`接口的对象传递给ILinkAPI接口的handleIntent方法。需要在 `onCreate`和`onNewIntent`中实现。参考如下：

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
   super.onCreate(savedInstanceState);
   setContentView(R.layout.activity_link_entry);

   app = (BaseApplication) getApplication();
   app.ilinkapi.handleIntent(getIntent(), this);
}

@Override
protected void onNewIntent(Intent intent) {
   super.onNewIntent(intent);
   app.ilinkapi.handleIntent(getIntent(), this);
}
```

至此，你已经能使用`LinkOpenSDK`的API内容了。如果想要更详细了解每个API函数的用法，请下载源码，查看里面的示例。

更多接入场景：[开发者平台](http://dev.bingocc.com/guide/)
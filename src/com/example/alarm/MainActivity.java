package com.example.alarm;

import android.annotation.SuppressLint;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressWarnings("unused")
@SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
public class MainActivity extends Activity {
	private WebView mWebView;
	@SuppressWarnings("deprecation")
	@SuppressLint({ "SetJavaScriptEnabled", "NewApi" })
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		mWebView = (WebView) findViewById(R.id.Toweb);
		WebSettings settings = mWebView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setUseWideViewPort(true);// 设置此属性，可任意比例缩放
		settings.setLoadWithOverviewMode(true);
		settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		settings.setAppCacheEnabled(true);		
		settings.setLoadsImagesAutomatically(true);				
		settings.setDatabaseEnabled(true);  
		settings.setDomStorageEnabled(true);  
		settings.setSupportZoom(false);
		settings.setBuiltInZoomControls(true);
		settings.setDefaultFontSize(18);
		settings.setGeolocationEnabled(true);
		//js和activity交互，使用WebView的方法
        mWebView.addJavascriptInterface(this,"javatojs");
		mWebView.loadUrl("file:///android_asset/index.html");
		//解除用户必须使用手势才能播放音频
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			mWebView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        }
		
		mWebView.setWebChromeClient(new WebChromeClient(){
			public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback){
				callback.invoke(origin, true, false);
			}
		});
			
		
		
		mWebView.setWebViewClient(new WebViewClient() {		
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				
				// 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
				view.loadUrl(url);
				
				return false;
			}		
		});				
	}
		
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			showTips();

			return false;
		}

		return super.onKeyDown(keyCode, event);
	}
	
	
	private void exit() {
		// handlerre.removeCallbacksAndMessages(null);
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(0);
	}
	
	// 按返回键的时候提示退出
	private void showTips() {		
		try {
			AlertDialog alertDialog = new AlertDialog.Builder(this)
					.setTitle("退出程序")
					.setMessage("确定退出吗？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// 资源释放干净
									exit();
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									return;
								}
							}).create(); // 创建对话框
			alertDialog.show(); // 显示对话框
			
		} catch (Exception e) {
			
		}
	}
}

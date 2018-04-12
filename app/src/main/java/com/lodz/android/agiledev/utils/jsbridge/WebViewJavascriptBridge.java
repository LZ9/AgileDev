package com.lodz.android.agiledev.utils.jsbridge;


public interface WebViewJavascriptBridge {
	
	void send(String data);
	void send(String data, CallBackFunction responseCallback);
	
	

}

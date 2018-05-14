package com.example.dou.myretrofitdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.example.dou.myretrofitdemo.MyApplication;
import com.example.dou.myretrofitdemo.url.Urls;
import com.orhanobut.logger.Logger;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * This example demonstrates how to create a websocket connection to a server.
 * Only the most important callbacks are overloaded.
 */
public class PushService extends Service {

	static PushService instance;

	private int retry = 0;

	/* 加上synchronized，但是每次调用实例时都会加载* */
	public static PushService getInstance() {
		synchronized (PushService.class) {
			if (instance == null) {
				instance = new PushService();
			}
		}
		return instance;
	}

	static WebSocket mWebSocket;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Logger.i("start");
		connect();
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			String info = msg.getData().getString("info");
			Logger.i(""+info);
		}
	};


	private final class EchoWebSocketListener extends WebSocketListener {

		@Override
		public void onOpen(WebSocket webSocket, Response response) {
			mWebSocket = webSocket;
			retry = 0;
		}

		@Override
		public void onMessage(WebSocket webSocket, String text) {
						Bundle bundle = new Bundle();
						bundle.putString("info", text);
						Message message = new Message();
						message.setData(bundle);
						handler.sendMessage(message);
		}

		@Override
		public void onMessage(WebSocket webSocket, ByteString bytes) {
		}

		@Override
		public void onClosing(WebSocket webSocket, int code, String reason) {
			webSocket.close(1000, null);
			Logger.i("onClosing: " + code + "/" + reason);
		}

		@Override
		public void onClosed(WebSocket webSocket, int code, String reason) {
			Logger.i( "onClosed: " + code + "/" + reason);
		}

		@Override
		public void onFailure(WebSocket webSocket, Throwable t, Response response) {
			Logger.i("onFailure: " + t.getMessage());
			retry++;
			if (MyApplication.wsConnect && retry < 5){
				connect();      //  异常断线重连
			}else {
				mWebSocket.cancel();   //  关闭长连接
			}
		}
	}

	public static WebSocket getWebSocketWorker() {
		return mWebSocket;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private void connect() {

		EchoWebSocketListener listener = new EchoWebSocketListener();
		Request request = new Request.Builder()
				.url(Urls.WebSocketUrl)
				.build();
		OkHttpClient client = new OkHttpClient();
		client.newWebSocket(request, listener);

		client.dispatcher().executorService().shutdown();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Logger.d("push:ondestory");
		mWebSocket.cancel();   //  关闭长连接
	}
}

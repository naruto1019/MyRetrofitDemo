package com.example.dou.myretrofitdemo;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.caimuhao.rxpicker.RxPicker;
import com.example.dou.myretrofitdemo.Bean.UserInfoEntity;
import com.example.dou.myretrofitdemo.imageloader.GlideImageLoader;
import com.example.dou.myretrofitdemo.url.Urls;
import com.example.retrofit.CacheInterceptor;
import com.example.retrofit.RetrofitUtils;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.util.concurrent.TimeUnit;

import me.jessyan.progressmanager.ProgressManager;
import okhttp3.Cache;
import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class MyApplication extends Application {

	public static UserInfoEntity user; // 登录用户信息

	public static Context context;

	public static boolean wsConnect = true;

	@Override
	public void onCreate() {
		super.onCreate();

//		setupLeakCanary();   // 内存泄露检测工具

		context = this;
		initRetrofit();
		RxPicker.init(new GlideImageLoader());
		initLogger();
	}

	private void initLogger() {

		FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
				.showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
//				.methodCount(0)         // (Optional) How many method line to show. Default 2
//				.methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
//				.logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
				.tag("dd")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
				.build();

		Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
			@Override public boolean isLoggable(int priority, String tag) {
				return BuildConfig.DEBUG;
			}
		});
	}

	private void initRetrofit() {
		CookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
		HttpLoggingInterceptor LoginInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
			@Override
			public void log(String message) {
				Logger.e(""+message);
			}
		});
		LoginInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

		Cache cache = new Cache(context.getCacheDir(), 10240*1024);

		OkHttpClient.Builder builder = new OkHttpClient.Builder();

		builder.addNetworkInterceptor(new CacheInterceptor(context));//添加自定义缓存拦截器
		builder.addInterceptor(new CacheInterceptor(context));
		builder.cache(cache);//把缓存添加进来（只缓存get请求）

		builder.cookieJar(cookieJar);            // 持久化cookie
		builder.addInterceptor(LoginInterceptor); //添加retrofit日志打印
		builder.connectTimeout(15, TimeUnit.SECONDS);
		builder.readTimeout(20, TimeUnit.SECONDS);
		builder.writeTimeout(20, TimeUnit.SECONDS);
		builder.retryOnConnectionFailure(true);

//		OkHttpClient client = builder.build();

		OkHttpClient client = ProgressManager.getInstance().with(builder).build();

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(Urls.HOST)
				.addConverterFactory(SimpleXmlConverterFactory.create())
				.addConverterFactory(GsonConverterFactory.create(new Gson()))
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.client(client)
				.build();

		RetrofitUtils.initClient(retrofit);
	}

	//	protected void setupLeakCanary() {
//		if (LeakCanary.isInAnalyzerProcess(this)) {
//			// This process is dedicated to LeakCanary for heap analysis.
//			// You should not init your app in this process.
//			return;
//		}
////		enabledStrictMode();    // 严格模式
//		LeakCanary.install(this);
//	}

	private static void enabledStrictMode() {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() //
				.detectAll() //
				.penaltyLog() //
//				.penaltyDeath() //
				.build());
	}

	public static Context getContext() {
		return context;
	}
}

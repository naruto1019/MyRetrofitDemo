package com.example.dou.myretrofitdemo.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 用来操作偏好设置文件的工具类
 *
 */
public class SPUtil {

	private Editor editor;
	private SharedPreferences sp;
	private static SPUtil instance;

	private SPUtil(Context context){
		sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
		editor = sp.edit();
	
	}

	public static SPUtil getInstance(Context context){
		if (instance==null){
			synchronized (SPUtil.class){
				if (instance == null){
					instance = new SPUtil(context);
				}
			}
		}
		return instance;
	}

	/**
	 * @return
	 */

	public String getInfo(String key){
		return sp.getString(key, "");
	}

	/**
	 * @return
	 */
	public void setInfo(String key, String value){
		editor.putString(key, value);
		editor.commit();
	}

	public void clear(){
		editor.clear().commit();
	}

}

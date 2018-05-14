package com.example.dou.myretrofitdemo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;

import java.util.Stack;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 * 
 * @author
 * @version 1.0
 * @created 2013-3-26
 */
public class AppManager {

	private static Stack<Activity> activityStack;
	private static AppManager instance;

	private AppManager() {
	}

	/**
	 * 单一实例
	 */
	public static AppManager getAppManager() {
		if (instance == null) {
			instance = new AppManager();
		}
		return instance;
	}

	/**
	 * 添加Activity到堆栈
	 */
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	/**
	 * 获取当前Activity（堆栈中最后一个压入的）
	 */
	public Activity currentActivity() {
		Activity activity = activityStack.lastElement();
		return activity;
	}

	/**
	 * 结束当前Activity（堆栈中最后一个压入的）
	 */
	public void finishActivity() {
		Activity activity = activityStack.lastElement();
		finishActivity(activity);
	}

	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls) {
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				finishActivity(activity);
			}
		}
	}

	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity() {
		if (activityStack==null) return;
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			if (null != activityStack.get(i)) {
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
//		android.os.Process.killProcess(android.os.Process.myPid());
	}

	/**
	 * 退出应用程序
	 */
	public void AppExit(Context context) {
		try {
			finishAllActivity();
//			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断一个Activity 是否存在
	 *
	 * @param clz
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	public static <T extends Activity> boolean isActivityExist(Class<T> clz) {
		boolean res;
		Activity activity = getActivity(clz);
		if (activity == null) {
			res = false;
		} else {
			if (activity.isFinishing() || activity.isDestroyed()) {
				res = false;
			} else {
				res = true;
			}
		}

		return res;
	}

	/**
	 * 获得指定activity实例
	 *
	 * @param clazz Activity 的类对象
	 * @return
	 */
	public static <T extends Activity> T getActivity(Class<T> clazz) {
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(clazz)) {
				return (T)activity;
			}
		}
		return null;
	}
}
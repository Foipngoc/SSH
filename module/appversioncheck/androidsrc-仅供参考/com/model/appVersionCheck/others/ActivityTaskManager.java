package com.yidong_app.common.util;

import java.util.HashMap;
import java.util.Set;

import android.app.Activity;
import android.util.Log;

/**
 * 
 * Activity堆栈管理
 *
 */
public class ActivityTaskManager {
	private static ActivityTaskManager activityManager = null;
	private HashMap<String, Activity> activityMap = null;

	private ActivityTaskManager() {
		activityMap = new HashMap<String, Activity>();
	}

	/**
	 * 单实例获取
	 * @return
	 */
	public static synchronized ActivityTaskManager getInstance() {
		if (activityManager == null) {
			activityManager = new ActivityTaskManager();
		}
		return activityManager;
	}

	/**
	 * 添加新的activity
	 * @param name
	 * @param activity
	 * @return
	 */
	public Activity putActivity(String name, Activity activity) {
		Log.i("ActivityTaskManager","putActivity:"+name);
		return activityMap.put(name, activity);
	}

	/**
	 * 获得activity
	 * @param name
	 * @return
	 */
	public Activity getActivity(String name) {
		return activityMap.get(name);
	}

	/**
	 * 当前管理器中activity是否为空
	 * @return
	 */
	public boolean isEmpty() {
		return activityMap.isEmpty();
	}

	/**
	 * activity的数量
	 * @return
	 */
	public int size() {
		return activityMap.size();
	}

	/**
	 * 判断是否存在名为name的activity
	 * @param name
	 * @return
	 */
	public boolean containsName(String name) {
		return activityMap.containsKey(name);
	}

	/**
	 * 判断是否存在activity
	 * @param activity
	 * @return
	 */
	public boolean containsActivity(Activity activity) {
		return activityMap.containsValue(activity);
	}

	/**
	 * 关闭所有的activity
	 */
	public void closeAllActivity() {
		Set<String> activityNames = activityMap.keySet();
		for (String string : activityNames) {
			Log.i("ActivityTaskManager","closeActivity:"+string);
			finisActivity(activityMap.get(string));
		}
		activityMap.clear();
	}

	/**
	 * 关闭所有除nameSpecified外的activity
	 * @param nameSpecified
	 */
	public void closeAllActivityExceptOne(String nameSpecified) {
		Set<String> activityNames = activityMap.keySet();
		Activity activitySpecified = activityMap.get(nameSpecified);
		for (String name : activityNames) {
			if (name.equals(nameSpecified)) {
				continue;
			}
			finisActivity(activityMap.get(name));
			Log.i("ActivityTaskManager","closeActivity:"+name);
		}
		activityMap.clear();
		activityMap.put(nameSpecified, activitySpecified);
	}

	/**
	 * 关闭名为name的activity
	 * @param name
	 */
	public void closeActivity(String name) {
		Activity activity = activityMap.remove(name);

		Log.i("ActivityTaskManager","closeActivity:"+name);
		finisActivity(activity);
	}

	private final void finisActivity(Activity activity) {
		if (activity != null) {
			if (!activity.isFinishing()) {
				activity.finish();
			}
		}
	}
}

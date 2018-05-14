package com.example.dou.myretrofitdemo.util;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class HttpNetUtil {

  /**
   * 判断网络是否连接
   *
   * @param context
   * @return
   */
  public static boolean isConnected(Context context)
  {

    ConnectivityManager connectivity = (ConnectivityManager) context
            .getSystemService(Context.CONNECTIVITY_SERVICE);

    if (null != connectivity)
    {

      NetworkInfo info = connectivity.getActiveNetworkInfo();
      if (null != info && info.isConnected())
      {
        if (info.getState() == NetworkInfo.State.CONNECTED)
        {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * 判断是否是wifi连接
   */
  public static boolean isWifi(Context context)
  {
    ConnectivityManager cm = (ConnectivityManager) context
            .getSystemService(Context.CONNECTIVITY_SERVICE);

    if (cm == null)
      return false;
    return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

  }

  /**
   * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
   *
   * @param context
   * @return true 表示开启
   */
  public static final boolean isOPen(final Context context) {
    LocationManager locationManager = (LocationManager) context
            .getSystemService(Context.LOCATION_SERVICE);
    // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
    boolean gps = locationManager
            .isProviderEnabled(LocationManager.GPS_PROVIDER);
    // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
    boolean network = locationManager
            .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    if (gps || network) {
      return true;
    }

    return false;
  }
}

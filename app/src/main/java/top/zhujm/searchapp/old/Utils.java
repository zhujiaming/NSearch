package top.zhujm.searchapp.old;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import top.zhujm.searchapp.AppInfo;


public class Utils {
    public static Map<String, List<AppInfo>> getAllApps(Context context) {
        long startTime = System.currentTimeMillis();
        Map<String, List<AppInfo>> mapDatas = new HashMap<>();
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> list2 = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (PackageInfo packageInfo : list2) {
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                //系统应用
                continue;
            }
            String appName = packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
            Drawable drawable = packageInfo.applicationInfo.loadIcon(context.getPackageManager());
            String packageName = packageInfo.packageName;
            AppInfo appInfo = new AppInfo();
            if (TextUtils.isEmpty(appName)) {
                continue;
            }
            String keys = appInfo.setAppName(appName);
            appInfo.setAppIcon(drawable);
            appInfo.setPkgName(packageName);

            if (mapDatas.containsKey(keys)) {
                List<AppInfo> appInfos = mapDatas.get(keys);
                appInfos.add(appInfo);
            } else {
                List<AppInfo> appInfos = new ArrayList<>();
                appInfos.add(appInfo);
                mapDatas.put(keys, appInfos);
            }
        }
        Log.i("zhujm", "prepare cost:" + (System.currentTimeMillis() - startTime) + "ms");
        return mapDatas;
    }

    public static final void openApp(Context context, String pName) {
        try {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(pName);
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "启动失败，可能已经被卸载", Toast.LENGTH_SHORT).show();
        }
    }

    public AppInfo getAppInfo(Context context, ApplicationInfo app) {
        //创建要返回的集合对象
        AppInfo appInfo = new AppInfo();
        String packageName = app.packageName;

        //获取包名
        String pkgName = app.packageName;
        appInfo.setPkgName(pkgName);

        //获取应用图片
        Drawable appIcon = app.loadIcon(context.getPackageManager());
        appInfo.setAppIcon(appIcon);

        //获取应用名称
        String appName = (String) app.loadLabel(context.getPackageManager());
        appInfo.setAppName(appName);
        try {
            //获取应用的版本号
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            String versionName = packageInfo.versionName;
            appInfo.setVersionName(versionName);

            //应用第一次安装的时间
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            long appDate1 = packageInfo.firstInstallTime;
            String appDate = String.valueOf(dateFormat.format(appDate1));
            appInfo.setAppDate(appDate);

            //获取应用的大小
            String dir = app.publicSourceDir;
            String cs = String.valueOf(new File(dir).length());
            long size = Long.parseLong(cs);
//            String codeSize = convertStorage(size);
//            appInfo.setCodeSize(codeSize);
            //获取APK文件的路径
            String publicSourceDir = app.publicSourceDir;
            appInfo.setPkgPath(publicSourceDir);
            Log.i("TAG", "ssss=" + publicSourceDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appInfo;
    }

    public static void search() {

    }
}

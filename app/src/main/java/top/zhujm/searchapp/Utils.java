package top.zhujm.searchapp;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<AppInfo> getAllAppNames(Context context) {
        List<AppInfo> datas = new ArrayList<>();
        PackageManager pm = context.getPackageManager();
        ////获取到所有安装了的应用程序的信息，包括那些卸载了的，但没有清除数据的应用程序
        List<PackageInfo> list2 = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        //PackageManager.GET_SHARED_LIBRARY_FILES==1024
//        List<PackageInfo> list2=pm.getInstalledPackages(PackageManager.GET_SHARED_LIBRARY_FILES);
        //PackageManager.GET_META_DATA==128
//        List<PackageInfo> list2=pm.getInstalledPackages(PackageManager.GET_META_DATA);
//        List<PackageInfo> list2=pm.getInstalledPackages(0);
        //List<PackageInfo> list2=pm.getInstalledPackages(-10);
        //List<PackageInfo> list2=pm.getInstalledPackages(10000);
        int j = 0;

        for (PackageInfo packageInfo : list2) {
            //得到手机上已经安装的应用的名字,即在AndriodMainfest.xml中的app_name。
            String appName = packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
            //得到手机上已经安装的应用的图标,即在AndriodMainfest.xml中的icon。
            Drawable drawable = packageInfo.applicationInfo.loadIcon(context.getPackageManager());
            //得到应用所在包的名字,即在AndriodMainfest.xml中的package的值。
            String packageName = packageInfo.packageName;
            Log.e("=======aaa", "应用的名字:" + appName);
            Log.e("=======bbbb", "应用的包名字:" + packageName);

            j++;
            AppInfo appInfo = new AppInfo();
            appInfo.setAppName(appName);
            appInfo.setAppIcon(drawable);
            appInfo.setPkgName(packageName);
            datas.add(appInfo);
        }
        Log.e("========cccccc", "应用的总个数:" + j);
        return datas;
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
}

package top.zhujm.searchapp;

import android.graphics.drawable.Drawable;

class AppInfo {
    private String pkgName;
    private Drawable appIcon;
    private String appName;
    private String versionName;
    private String appDate;
    private String pkgPath;

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public void setPkgPath(String publicSourceDir) {
        this.pkgPath = publicSourceDir;
    }
}

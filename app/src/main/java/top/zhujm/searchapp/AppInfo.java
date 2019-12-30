package top.zhujm.searchapp;

import android.graphics.drawable.Drawable;

import com.github.promeg.pinyinhelper.Pinyin;

import java.util.HashSet;
import java.util.Set;

public class AppInfo {

    public static final String TAG = "AppInfo";

    public String appName;
    public String pkgName;
    public String pkgPath;
    public Drawable appIcon;
    public String versionName;
    public String appDate;
    public Set<Integer> keys = new HashSet<>();
    public String keysStr = "";

    public AppInfo() {
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String setAppName(String appName) {
        this.appName = appName;
        StringBuilder sb2 = new StringBuilder();
        for (Character part : this.appName.trim().toCharArray()) {
            String f = Pinyin.toPinyin(part).toUpperCase().charAt(0) + "";
            keys.add(parseToKey(f));
            sb2.append(parseToKey(f) + "");
        }
        keysStr = sb2.toString();
        return keysStr;
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

    @Override
    public String toString() {
        return "AppInfo{" +
                "appName='" + appName + '\'' +
                ", pkgName='" + pkgName + '\'' +
                ", pkgPath='" + pkgPath + '\'' +
                ", appIcon=" + appIcon +
                ", versionName='" + versionName + '\'' +
                ", appDate='" + appDate + '\'' +
                ", keys=" + keys +
                '}';
    }

    private int parseToKey(String f) {
        if ("ABC2".contains(f)) {
            return (2);
        } else if ("DEF3".contains(f)) {
            return (3);
        } else if ("GHI4".contains(f)) {
            return (4);
        } else if ("JKL5".contains(f)) {
            return (5);
        } else if ("MNO6".contains(f)) {
            return (6);
        } else if ("PQRS7".contains(f)) {
            return (7);
        } else if ("TUV8".contains(f)) {
            return (8);
        } else if ("WXYZ9".contains(f)) {
            return (9);
        } else if ("0".equals(f)) {
            return (0);
        } else if ("1".equals(f)) {
            return (1);
        } else {
            return -1;
        }
    }
}

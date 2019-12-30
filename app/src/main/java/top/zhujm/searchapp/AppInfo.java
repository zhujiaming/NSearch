package top.zhujm.searchapp;

import android.graphics.drawable.Drawable;

import com.dbflow5.annotation.Column;
import com.dbflow5.annotation.PrimaryKey;
import com.dbflow5.annotation.Table;
import com.github.promeg.pinyinhelper.Pinyin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import top.zhujm.searchapp.data.AppDatabase;

@Table(database = AppDatabase.class)
public class AppInfo {
    public static final String TAG = "AppInfo";
    @PrimaryKey
    UUID id;
    @Column
    public String appName;
    @Column
    public String pkgName;
    @Column
    public String pkgPath;
    public Drawable appIcon;
    @Column
    public String versionName;
    @Column
    public String appDate;

    public Set<Integer> keys = new HashSet<>();


    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String setAppName(String appName) {
        this.appName = appName.trim();
        StringBuilder sb2 = new StringBuilder();
        for (Character part : this.appName.toCharArray()) {
            String f = Pinyin.toPinyin(part).toUpperCase().charAt(0) + "";
            keys.add(parseToKey(f));
            sb2.append(parseToKey(f) + "");
        }
        return sb2.toString();
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

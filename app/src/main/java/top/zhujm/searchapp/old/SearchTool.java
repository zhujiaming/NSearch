package top.zhujm.searchapp.old;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import top.zhujm.searchapp.AppInfo;

public class SearchTool {

    private static final String TAG = "SearchTool";

//    public static final SparseArray<String[]> KEYS = new SparseArray<>();
//    static {
//        KEYS.append(-2, new String[]{"#"});
//        KEYS.append(-1, new String[]{"*"});
//        KEYS.append(0, new String[]{"0"});
//        KEYS.append(1, new String[]{"1"});
//        KEYS.append(2, new String[]{"2", "A", "B", "C"});
//        KEYS.append(3, new String[]{"3", "D", "E", "F"});
//        KEYS.append(4, new String[]{"4", "G", "H", "I"});
//        KEYS.append(5, new String[]{"5", "J", "K", "L"});
//        KEYS.append(6, new String[]{"6", "M", "N", "O"});
//        KEYS.append(7, new String[]{"7", "P", "Q", "R", "S"});
//        KEYS.append(8, new String[]{"8", "T", "U", "V"});
//        KEYS.append(9, new String[]{"9", "W", "X", "Y", "Z"});
//    }

    public static Map<String, List<AppInfo>> KEYAPPS = new HashMap<>();

//    private Map<String, List<AppInfo>> cacheApps = new HashMap<>();

    public String perKey = "";
    int length;
    private OnResultListener listener;

    public SearchTool(OnResultListener listener) {
        this.listener = listener;
    }

    public void prepareApps(Context context) {
        KEYAPPS = Utils.getAllApps(context);
    }

    public void reset() {
        perKey = "";
        length = 0;
        listener.onResult(Collections.EMPTY_LIST);
    }

    public void rollbackSearch() {
        if (!TextUtils.isEmpty(perKey)) {
            perKey = perKey.substring(0, perKey.length() - 1);
            if (TextUtils.isEmpty(perKey)) {
                listener.onResult(Collections.<AppInfo>emptyList());
            } else {
                searchByKey();
            }
        }
    }

    public void enterToSearch(int key) {
        if (length == 0 && !TextUtils.isEmpty(perKey)) {
            return;
        }
        perKey += key;
        searchByKey();
    }

    private void searchByKey() {
        long start = SystemClock.currentThreadTimeMillis();
        List<AppInfo> datas = new ArrayList<>();
        Log.i(TAG, "searchByKey|" + perKey);
        Map<String, List<AppInfo>> tempAppList = KEYAPPS;
//        if (cacheApps.size() > 0) {
//            tempAppList = new HashMap<>();
//            tempAppList.putAll(cacheApps);
//        }
        Set<Map.Entry<String, List<AppInfo>>> entries = tempAppList.entrySet();
        Iterator<Map.Entry<String, List<AppInfo>>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<AppInfo>> next = iterator.next();
            String key = next.getKey();
            List<AppInfo> appInfos = next.getValue();
            if (key.startsWith(perKey)) {
                datas.addAll(appInfos);
//                if (cacheApps.containsKey(key)) {
//                    cacheApps.get(key).addAll(appInfos);
//                }else{
//                    cacheApps.put(key, appInfos);
//                }
            }
        }
        length = datas.size();
        Log.i("zhujm", "searchByKey|" + perKey + "|cost:" + (SystemClock.currentThreadTimeMillis() - start) + "ms");
        listener.onResult(datas);
    }

    public interface OnResultListener {
        void onResult(List<AppInfo> datas);
    }
}

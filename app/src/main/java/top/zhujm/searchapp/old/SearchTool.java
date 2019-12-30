package top.zhujm.searchapp.old;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import top.zhujm.searchapp.AppInfo;

public class SearchTool {

    public static final SparseArray<String[]> KEYS = new SparseArray<>();
    private static final String TAG = "SearchTool";

    static {
        KEYS.append(-2, new String[]{"#"});
        KEYS.append(-1, new String[]{"*"});
        KEYS.append(0, new String[]{"0"});
        KEYS.append(1, new String[]{"1"});
        KEYS.append(2, new String[]{"2", "A", "B", "C"});
        KEYS.append(3, new String[]{"3", "D", "E", "F"});
        KEYS.append(4, new String[]{"4", "G", "H", "I"});
        KEYS.append(5, new String[]{"5", "J", "K", "L"});
        KEYS.append(6, new String[]{"6", "M", "N", "O"});
        KEYS.append(7, new String[]{"7", "P", "Q", "R", "S"});
        KEYS.append(8, new String[]{"8", "T", "U", "V"});
        KEYS.append(9, new String[]{"9", "W", "X", "Y", "Z"});
    }


    public static Map<String, List<AppInfo>> KEYAPPS = new HashMap<>();

    public List<AppInfo> APPS;

//    private Set<Integer> perKeys = new HashSet<>();


    private String perKey = "";
    OnResultListener listener;

    public SearchTool(OnResultListener listener) {
        this.listener = listener;
    }

    public void prepareApps(Context context) {
        KEYAPPS = Utils.getAllApps(context);
        Log.i(TAG, "KEY_APPS==>" + KEYAPPS.toString());
    }

    public void reset() {
//        perKeys.clear();
        perKey = null;
    }

    public List<AppInfo> searchByKey(int key) {
        List<AppInfo> datas = new ArrayList<>();
        perKey += key;
        Log.i(TAG, "searchByKey|" + perKey);
        Set<Map.Entry<String, List<AppInfo>>> entries = KEYAPPS.entrySet();
        Iterator<Map.Entry<String, List<AppInfo>>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<AppInfo>> next = iterator.next();
            if (next.getKey().startsWith(perKey)) {
                datas.addAll(next.getValue());
            }
        }

        listener.onResult(datas);
        return datas;
    }

    public interface OnResultListener {
        void onResult(List<AppInfo> datas);
    }
}

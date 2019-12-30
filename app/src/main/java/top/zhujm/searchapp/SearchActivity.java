package top.zhujm.searchapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import top.zhujm.searchapp.old.SearchTool;

public class SearchActivity extends AppCompatActivity implements SearchTool.OnResultListener {
    private RecyclerView mListView;
    private AppAdapter mAdapter;
    private HandlerThread mHandlerThread;
    private Handler mSeachHandler;

    static final int MSG_PREPARE_APP = 1;
    static final int MSG_SEARCH_APP = 2;
    static final int MSG_ROLLBACK_SEARCH = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_main);
        mListView = findViewById(R.id.rc_list);
        initRecyclerView();
        initSearchTask();
    }

    public void initRecyclerView() {
        mAdapter = new AppAdapter();
        GridLayoutManager layoutManager = new GridLayoutManager(this, 5, GridLayoutManager.VERTICAL, false);
//        layoutManager.setStackFromEnd(true);//列表再底部开始展示，反转后由上面开始展示
//        layoutManager.setReverseLayout(true);//列表翻转
        mListView.setLayoutManager(layoutManager);
        mListView.setAdapter(mAdapter);
        mListView.setItemAnimator(new DefaultItemAnimator());
    }

    SearchTool mSeachTool = new SearchTool(this);

    private void initSearchTask() {
        mHandlerThread = new HandlerThread("search_app_task");
        mHandlerThread.start();
        mSeachHandler = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_PREPARE_APP:
                        mSeachTool.prepareApps(SearchActivity.this);
                        break;
                    case MSG_SEARCH_APP:
                        mSeachTool.enterToSearch(msg.arg1);
                        break;
                    case MSG_ROLLBACK_SEARCH:
                        mSeachTool.rollbackSearch();
                        break;
                }
            }
        };
        mSeachHandler.sendEmptyMessage(MSG_PREPARE_APP);
    }

    public void onKeyClick(final View view) {
        Message message = Message.obtain();
        message.what = MSG_SEARCH_APP;

        switch (view.getId()) {
            case R.id.key_0:
                message.arg1 = 0;
                break;
            case R.id.key_1:
                message.arg1 = 1;
                break;
            case R.id.key_2:
                message.arg1 = 2;
                break;
            case R.id.key_3:
                message.arg1 = 3;
                break;
            case R.id.key_4:
                message.arg1 = 4;
                break;
            case R.id.key_5:
                message.arg1 = 5;
                break;
            case R.id.key_6:
                message.arg1 = 6;
                break;
            case R.id.key_7:
                message.arg1 = 7;
                break;
            case R.id.key_8:
                message.arg1 = 8;
                break;
            case R.id.key_9:
                message.arg1 = 9;
                break;
            case R.id.key_star:
                return;
            case R.id.key_well:
                message.what = MSG_ROLLBACK_SEARCH;
                mSeachHandler.sendMessage(message);
                return;
            default:
                return;
        }
        mSeachHandler.sendMessage(message);
    }


    @Override
    public void onResult(final List<AppInfo> datas) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.setDatas(datas);
            }
        });
    }
}

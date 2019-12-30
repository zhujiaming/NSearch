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
import top.zhujm.searchapp.old.Utils;

public class SearchActivity extends AppCompatActivity implements SearchTool.OnResultListener {
    private RecyclerView mListView;
    private AppAdapter mAdapter;
    private HandlerThread mHandlerThread;
    private Handler mSeachHandler;
    private SearchTool mSeachTool = new SearchTool(this);

    static final int MSG_PREPARE_APP = 1;
    static final int MSG_SEARCH_APP = 2;
    static final int MSG_ROLLBACK_SEARCH = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_main);
        initViews();
        initSearchTask();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void initViews() {
        mListView = findViewById(R.id.rc_list);
        findViewById(R.id.container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onKeyClick(R.id.key_star);
            }
        });

        mAdapter = new AppAdapter();
        GridLayoutManager layoutManager = new GridLayoutManager(this, 5, GridLayoutManager.VERTICAL, true);
        mListView.setLayoutManager(layoutManager);
        mListView.setAdapter(mAdapter);
        mListView.setItemAnimator(new DefaultItemAnimator());
    }

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

    public void onKeyClick(int keyId) {
        Message message = Message.obtain();
        message.what = MSG_SEARCH_APP;

        switch (keyId) {
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
                Utils.goHome(this);
                mSeachTool.reset();
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

    public void onKeyClick(final View view) {
        onKeyClick(view.getId());
    }


    @Override
    public void onBackPressed() {
        Utils.goHome(this);
        mSeachTool.reset();
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

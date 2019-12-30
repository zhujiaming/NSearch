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
                    case 1:
                        mSeachTool.prepareApps(SearchActivity.this);
                        break;
                    case 2:
                        mSeachTool.searchByKey(msg.arg1);
                        break;
                }
            }
        };
        mSeachHandler.sendEmptyMessage(1);
    }

    public void onKeyClick(final View view) {
        int keyNumber = -999;
        switch (view.getId()) {
            case R.id.key_0:
                keyNumber = 0;
                break;
            case R.id.key_1:
                keyNumber = 1;
                break;
            case R.id.key_2:
                keyNumber = 2;
                break;
            case R.id.key_3:
                keyNumber = 3;
                break;
            case R.id.key_4:
                keyNumber = 4;
                break;
            case R.id.key_5:
                keyNumber = 5;
                break;
            case R.id.key_6:
                keyNumber = 6;
                break;
            case R.id.key_7:
                keyNumber = 7;
                break;
            case R.id.key_8:
                keyNumber = 8;
                break;
            case R.id.key_9:
                keyNumber = 9;
                break;
            case R.id.key_star:
                break;
            case R.id.key_well:
                break;
        }
        Message message = Message.obtain();
        message.what = 2;
        message.arg1 = keyNumber;
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

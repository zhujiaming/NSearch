package top.zhujm.searchapp;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_main);
        Utils.getAllAppNames(this);
    }

    public void onKeyClick(View view) {
        switch (view.getId()) {
            case R.id.key_0:
                break;
            case R.id.key_1:
                break;
            case R.id.key_2:
                break;
            case R.id.key_3:
                break;
            case R.id.key_4:
                break;
            case R.id.key_5:
                break;
            case R.id.key_6:
                break;
            case R.id.key_7:
                break;
            case R.id.key_8:
                break;
            case R.id.key_9:
                break;
            case R.id.key_star:
                break;
            case R.id.key_well:
                break;
        }
    }
}

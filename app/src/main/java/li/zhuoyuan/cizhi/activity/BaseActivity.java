package li.zhuoyuan.cizhi.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BaseActivity extends AppCompatActivity {

    private LinearLayout leftbtn, rightbtn;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
        initEvent();
    }

    private void initEvent() {
    }

    private void initData() {
    }

    private void initView() {
    }
}

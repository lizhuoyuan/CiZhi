package li.zhuoyuan.cizhi.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import li.zhuoyuan.cizhi.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvtitle;
    private LinearLayout leftbtn, rightbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();
    }

    private void initEvent() {
        leftbtn.setOnClickListener(this);
        rightbtn.setOnClickListener(this);
    }

    private void initView() {
        leftbtn = (LinearLayout) findViewById(R.id.leftbtn);
        rightbtn = (LinearLayout) findViewById(R.id.rightbtn);
        tvtitle = (TextView) findViewById(R.id.title);
        tvtitle.setText(R.string.app_name);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.leftbtn:
                finish();
                break;
            case R.id.rightbtn:
                Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}

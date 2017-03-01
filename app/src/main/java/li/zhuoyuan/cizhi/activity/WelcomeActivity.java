package li.zhuoyuan.cizhi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

import li.zhuoyuan.cizhi.R;
import li.zhuoyuan.cizhi.adapter.WelcomePageAdapter;
import me.relex.circleindicator.CircleIndicator;

public class WelcomeActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private ViewPager viewPager;
    private ArrayList<View> views = new ArrayList<>();
    private WelcomePageAdapter adapter;
    private CircleIndicator indicator;
    private View v1, v2, v3, v4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initview();
        initData();
        initEvent();
    }

    private void initview() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        indicator = (CircleIndicator) findViewById(R.id.indicator);
        v1 = LayoutInflater.from(this).inflate(R.layout.view1, null);
        v2 = LayoutInflater.from(this).inflate(R.layout.view2, null);
        v3 = LayoutInflater.from(this).inflate(R.layout.view3, null);
        views.add(v1);
        views.add(v2);
        views.add(v3);
        adapter = new WelcomePageAdapter(views);
    }

    private void initData() {
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
    }

    private void initEvent() {
        v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMain();
            }
        });
    }

    private void startMain() {
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        finish();
    }
}

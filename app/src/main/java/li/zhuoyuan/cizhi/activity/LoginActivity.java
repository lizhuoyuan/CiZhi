package li.zhuoyuan.cizhi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;
import li.zhuoyuan.cizhi.R;

/**
 * Created by 帅裂苍穹的卓原 on 2017/3/1 14:22 .
 * email: zhuoyuan93@gmail.com
 */


public class LoginActivity extends AppCompatActivity implements OnSendMessageHandler {

    private boolean ismobile;
    private TextView tvlogin, tvsendtel, tvtitle;
    private EditText edtel, etCaptcha;
    private Button btnnext;
    private TimeCount mTimeCount;//计时器
    public EventHandler eh; //事件接收器
    private LinearLayout leftbtn;
    private String tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initview();
        initevent();
    }

    private void initevent() {

        leftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        Log.i("huou", "afterEvent:获取验证码 ");
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtel.getText().toString().trim().equals("")) {
                    if (checkTel(tel.trim())) {
                        if (!tvsendtel.getText().toString().trim().equals("")) {
                            SMSSDK.submitVerificationCode("+86", tel, etCaptcha.getText().toString().trim());//提交验证
                        } else {
                            Toast.makeText(LoginActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvsendtel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tel.trim().equals("")) {
                    if (checkTel(tel.trim())) {
                        SMSSDK.getVerificationCode("+86", tel);//获取验证码
                        mTimeCount.start();
                    } else {
                        Toast.makeText(LoginActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                }
            }
        });

        edtel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s == null || s.length() == 0) return;
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < s.length(); i++) {
                    if (i != 3 && i != 8 && s.charAt(i) == ' ') {
                        continue;
                    } else {
                        sb.append(s.charAt(i));
                        if ((sb.length() == 4 || sb.length() == 9) && sb.charAt(sb.length() - 1) != ' ') {
                            sb.insert(sb.length() - 1, ' ');
                        }
                    }
                }
                if (!sb.toString().equals(s.toString())) {
                    int index = start + 1;
                    if (sb.charAt(start) == ' ') {
                        if (before == 0) {
                            index++;
                        } else {
                            index--;
                        }
                    } else {
                        if (before == 1) {
                            index--;
                        }
                    }
                    edtel.setText(sb.toString());
                    edtel.setSelection(index);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 13) {
                    tel = s.toString().replace(" ", "");
                    ismobile = checkTel(tel);
                    Log.e("电话号判断", ismobile + "");
                }
            }
        });
    }

    private void initview() {
        tvsendtel = (TextView) findViewById(R.id.tvsendtel);
        tvlogin = (TextView) findViewById(R.id.tvlogin);
        edtel = (EditText) findViewById(R.id.edittel);
        btnnext = (Button) findViewById(R.id.btnnext);
        etCaptcha = (EditText) findViewById(R.id.Captcha);
        btnnext = (Button) findViewById(R.id.btnnext);
        mTimeCount = new TimeCount(60000, 1000);
        tvtitle = (TextView) findViewById(R.id.title);
        tvtitle.setText("注册");
        leftbtn = (LinearLayout) findViewById(R.id.leftbtn);
    }

    /**
     * 正则匹配手机号码
     *
     * @param tel
     * @return
     */
    public boolean checkTel(String tel) {
        Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
        Matcher matcher = p.matcher(tel);
        return matcher.matches();
    }

    @Override
    public boolean onSendMessage(String s, String s1) {
        return false;
    }


    /**
     * 计时器
     */
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            tvsendtel.setClickable(false);
            tvsendtel.setText(l / 1000 + "秒后重新获取");
        }

        @Override
        public void onFinish() {
            tvsendtel.setClickable(true);
            tvsendtel.setText("获取验证码");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh);
    }


}


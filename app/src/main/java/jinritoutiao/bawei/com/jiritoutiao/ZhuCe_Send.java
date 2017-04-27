package jinritoutiao.bawei.com.jiritoutiao;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static android.os.Build.VERSION_CODES.M;

/**
 * date: 2017/4/13.
 * author: 王艺凯 (lenovo )
 * function:
 */

public class ZhuCe_Send extends AppCompatActivity implements View.OnClickListener {

    private ImageView mIv;
    private TextView mNum;
    private TextView mSend;
    //key values
    private static final String APPKEY = "1cfac242ed098";
    private static final String APPSECRECT = "42c6385ed8251719de735c4123321080";
    private String mNumber;
    private EditText mCode;
    private Button mLogin;
    private EditText mSetpsd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.zc_send_verify_layout);
        initView();
        Intent intent1 = getIntent();
        mNumber = intent1.getStringExtra("tel");
        mNum.setText(mNumber);
        initSMSS();
    }

    private void initSMSS() {

        mCode.requestFocus();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    Toast.makeText(ZhuCe_Send.this, "回调完成", Toast.LENGTH_SHORT).show();
                } else if (msg.what == 2) {
                    Toast.makeText(ZhuCe_Send.this, "提交验证码成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ZhuCe_Send.this, SheZhi.class);
                    startActivity(intent);
                } else if (msg.what == 3) {
                    Toast.makeText(ZhuCe_Send.this, "获取验证码成功", Toast.LENGTH_SHORT).show();
                } else if (msg.what == 4) {
                    Toast.makeText(ZhuCe_Send.this, "返回支持发送国家验证码", Toast.LENGTH_SHORT).show();
                }
            }
        };

        EventHandler eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    handler.sendEmptyMessage(1);
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        handler.sendEmptyMessage(2);
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        handler.sendEmptyMessage(3);
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                        handler.sendEmptyMessage(4);
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                }
            }
        };
        SMSSDK.initSDK(this, APPKEY, APPSECRECT);//初始化
        SMSSDK.registerEventHandler(eh); //注册短信回调
        //启动获取验证码 86是中国
        SMSSDK.getVerificationCode("86", mNumber);//发送短信验证码到手机号
        timer.start();//使用计时器 设置验证码的时间限制
    }

    private void initView() {

        mIv = (ImageView) findViewById(R.id.left_back);
        mNum = (TextView) findViewById(R.id.textnum);
        mSend = (TextView) findViewById(R.id.agin_send);
        mCode = (EditText) findViewById(R.id.edit_verify);
        mLogin = (Button) findViewById(R.id.zc_login);
        mSetpsd = (EditText) findViewById(R.id.set_password);

        mIv.setOnClickListener(this);
        mNum.setOnClickListener(this);
        mSend.setOnClickListener(this);
        mLogin.setOnClickListener(this);

    }

    /**
     * 验证用户的其他信息
     * 这里验证两次密码是否一致 以及验证码判断
     */
    private void submitInfo() {
        //密码验证
        String code = mCode.getText().toString().trim();
        SMSSDK.submitVerificationCode("86", mNumber, code);//提交验证码  在eventHandler里面查看验证结果
    }

    /**
     * 使用计时器来限定验证码
     * 在发送验证码的过程 不可以再次申请获取验证码 在指定时间之后没有获取到验证码才能重新进行发送
     * 这里限定的时间是60s
     */
    private CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mSend.setText((millisUntilFinished / 1000) + "秒后可重发");
        }

        @Override
        public void onFinish() {
            mSend.setEnabled(true);
            mSend.setText("获取验证码");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //防止使用短信验证 产生内存溢出问题
        SMSSDK.unregisterAllEventHandler();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_back:
                ZhuCe_Send.this.finish();
                break;
            case R.id.agin_send:
                break;
            case R.id.zc_login:
                String code = mCode.getText().toString().trim();
                String psd = mSetpsd.getText().toString().trim();
                if (!TextUtils.isEmpty(mCode.getText().toString())) {
                    if (psd.isEmpty()) {
                        Toast.makeText(this, "请设置密码", Toast.LENGTH_LONG).show();
                    } else if (psd.length() < 6 || psd.length() > 20) {
                        Toast.makeText(this, "请设置正确格式的密码", Toast.LENGTH_LONG).show();
                    } else {
                        submitInfo();
                    }
                } else {
                    Toast.makeText(this, "验证码不能为空", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
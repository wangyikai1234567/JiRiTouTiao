package jinritoutiao.bawei.com.jiritoutiao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

public class MoreLogin extends AppCompatActivity implements View.OnClickListener {

    private ImageView mQq;
    private ImageView mWb;
    private ImageView mTx;
    private ImageView mRr;
    private Button mAccount;
    private Button mLogin;
    private ImageView mImageView;
    private static final String APP_ID = "1106033879";//官方获取的APPID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_login);
        initView();
    }

    private void initView() {

        mQq = (ImageView) findViewById(R.id.qq);
        mWb = (ImageView) findViewById(R.id.wb);
        mTx = (ImageView) findViewById(R.id.tx);
        mRr = (ImageView) findViewById(R.id.rr);
        mLogin = (Button) findViewById(R.id.tel_login);
        mAccount = (Button) findViewById(R.id.register);
        mImageView = (ImageView) findViewById(R.id.login_back);
        mImageView.setOnClickListener(this);
        mQq.setOnClickListener(this);
        mWb.setOnClickListener(this);
        mRr.setOnClickListener(this);
        mTx.setOnClickListener(this);
        mLogin.setOnClickListener(this);
        mAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.qq:
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.QQ, umAuthListener);
                break;
            case R.id.wb:

                break;
            case R.id.tx:


                break;
            case R.id.rr:

                Intent intents = new Intent(MoreLogin.this, YS.class);
                startActivity(intents);


                break;
            //返回
            case R.id.login_back:
                MoreLogin.this.finish();
                break;
            case R.id.tel_login:
                Intent intent = new Intent(MoreLogin.this, Tel_Login.class);
                startActivity(intent);
                break;
            case R.id.register:
                Intent intent1 = new Intent(MoreLogin.this, ZhuCe.class);
                startActivity(intent1);
                break;
        }
    }

    //登录qq
    UMAuthListener umAuthListener = new UMAuthListener() {
        //        授权开始回调方法
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        //        授权成功回调方法
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            String tou = data.get("iconurl");
            Intent intent = new Intent(MoreLogin.this, MainActivity.class);
            intent.putExtra("tou", tou);
            intent.putExtra("code", 1);
            startActivity(intent);
        }

        //        授权错误回调方法
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
        }

        //        授权取消回调方法
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}


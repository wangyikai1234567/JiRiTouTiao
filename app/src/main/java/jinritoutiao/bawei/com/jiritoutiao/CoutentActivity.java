package jinritoutiao.bawei.com.jiritoutiao;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RadioButton;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;
/**
 * date: 2017/4/19.
 * author: 王艺凯 (lenovo )
 * function:
 */
public class CoutentActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBack;
    private ImageView mSetAA;
    private RadioButton mShare;
    private WebView mWv;
    private String mWebView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        initView();
        Intent intent = getIntent();
        //uri
        mWebView = intent.getStringExtra("webView");

        WebSettings webSettings = mWv.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mWv.loadUrl(mWebView);
    }

    private void initView() {
        mWv = (WebView) findViewById(R.id.webView);
        mBack = (ImageView) findViewById(R.id.back);
        mSetAA = (ImageView) findViewById(R.id.setAa);
        mShare = (RadioButton) findViewById(R.id.share);

        mShare.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.share:
                new ShareAction(CoutentActivity.this)
                        .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
                        .setCallback(mUmShareListener)
                        .withMedia(new UMWeb(mWebView))
                        .withText("展示的文字内容")
                        .open();
                break;
        }
    }
    //多个分享
    private UMShareListener mUmShareListener = new UMShareListener() {

        //分享开始监听
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        //   分享成功监听
        @Override
        public void onResult(SHARE_MEDIA share_media) {

        }

        //            分享失败监听
        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {

        }

        //            分享取消监听
        @Override
        public void onCancel(SHARE_MEDIA share_media) {

        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}

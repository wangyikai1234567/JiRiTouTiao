package jinritoutiao.bawei.com.jiritoutiao;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import adapter.SlidMenuAdapter;
import bean.MySMBean;
import util.NetUtil.NetworkUtils;
import util.TitleXutil;

@ContentView(value = R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private List<MySMBean> lms = new ArrayList<>();
    @ViewInject(value = R.id.tab)
    private TabLayout tab;
    @ViewInject(R.id.vp)
    private ViewPager mVp;
    @ViewInject(R.id.xia)
    private TextView xia;
    private TextView mBut;
    private TextView mTextView;
    private int theme = R.style.AppTheme;
    private ImageView mImageView;
    private ImageView mAn;
    private SlidingMenu mMenu;
    private ImageView mQq;
    private static final String APP_ID = "1106033879";//官方获取的APPID
    private ImageView mCity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// 判断是否有主题存储
        if (savedInstanceState != null) {
            theme = savedInstanceState.getInt("theme");
            setTheme(theme);
        }



        // 注入绑定事件
        x.view().inject(this);

        initView();
        setUm();
        tab.setTabTextColors(Color.BLACK, Color.RED);

        initData();

        setNet();//设置网络

        setLinstener();
        mAn.setOnClickListener(this);
        //设置侧滑
        setSlidingMenu();
        //跳转回传
        getIntents();
    }

    private void getIntents() {
        Intent intent = getIntent();
        String touxiang = intent.getStringExtra("tou");
        int i = intent.getIntExtra("code", 0);
        if (i == 1) {
            //设置图片
            ImageOptions options = new ImageOptions.Builder()
                    //设置加载过程中的图片
                    .setLoadingDrawableId(R.mipmap.ic_launcher)
                    //设置加载失败后的图片
                    .setFailureDrawableId(R.mipmap.ic_launcher)
                    //设置使用缓存
                    .setUseMemCache(true)
                    //设置显示圆形图片
                    .setCircular(true)
                    //设置支持gif
                    .setIgnoreGif(false)
                    .build();
//            x.image().bind(需要改变的控件名, 图片路径, options);
                                //view,uri,options
            x.image().bind(mAn, touxiang, options);
        }
    }

    //设置友盟
    private void setUm() {
        UMShareConfig config = new UMShareConfig();
//        是否需求重复授权用户信息
        config.isNeedAuthOnGetUserInfo(true);
//        是否打开分享编辑页面
        config.isOpenShareEditActivity(true);
        UMShareAPI.get(this).setShareConfig(config);
        config.setSinaAuthType(UMShareConfig.AUTH_TYPE_SSO);
//        Web授权：
        config.setSinaAuthType(UMShareConfig.AUTH_TYPE_WEBVIEW);
    }

    private void initView() {

        mAn = (ImageView) findViewById(R.id.sm_an);
        mCity = (ImageView) findViewById(R.id.fdj);
    }

    private void initData() {
        lms.add(new MySMBean(R.mipmap.dynamicicon_leftdrawer, "好友动态"));
        lms.add(new MySMBean(R.mipmap.topicicon_leftdrawer, "我的话题"));
        lms.add(new MySMBean(R.mipmap.ic_action_favor_on_pressed, "收藏"));
        lms.add(new MySMBean(R.mipmap.activityicon_leftdrawer, "活动"));
        lms.add(new MySMBean(R.mipmap.sellicon_leftdrawer, "商城"));
        lms.add(new MySMBean(R.mipmap.feedbackicon_leftdrawer, "反馈"));
        lms.add(new MySMBean(R.mipmap.wiper_switch_slipper_btn_profile_night, "爆料"));
    }

    private void setNet() {

        boolean b = NetworkUtils.isConnected();
        if (b) {
            new TitleXutil(tab, mVp, getSupportFragmentManager(), MainActivity.this).getData();//请求网络数据
        } else {

            AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("提示")
                    .setMessage("是否打开Wifi？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            NetworkUtils.setWifiEnabled(true);//设置wifi网络

                            new TitleXutil(tab, mVp, getSupportFragmentManager(), MainActivity.this).getData();//请求网络数据
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).create();
            dialog.show();
        }
    }

    private void setSlidingMenu() {
        mMenu = new SlidingMenu(this);
        //设置侧划方向
        mMenu.setMode(SlidingMenu.LEFT);
//        mMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        mMenu.setShadowDrawable(R.mipmap.ic_launcher);//设置阴影图片
        // 设置滑动菜单视图的宽度
        int i = getWindowManager().getDefaultDisplay().getWidth() / 5;
        mMenu.setBehindOffset(i);
        //设置渐入渐出效果值
        mMenu.setFadeDegree(0.35f);
        mMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //为侧滑菜单设置布局
        mMenu.setMenu(R.layout.left_layout);
        ListView listView = (ListView) findViewById(R.id.lv);
        listView.setAdapter(new SlidMenuAdapter(lms, this));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 4) {
                    Intent intent = new Intent(MainActivity.this, Shopping.class);

                    startActivity(intent);
                }
            }
        });
        mBut = (TextView) findViewById(R.id.mlogin);
        mTextView = (TextView) findViewById(R.id.don);
        mImageView = (ImageView) findViewById(R.id.phone_login);
        mQq = (ImageView) findViewById(R.id.imageView);
        mQq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UMShareAPI.get(MainActivity.this).getPlatformInfo(MainActivity.this, SHARE_MEDIA.QQ, umAuthListener);
            }
        });
        mBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MoreLogin.class);
                startActivity(intent);
            }
        });
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theme = (theme == R.style.AppTheme) ? R.style.NightAppTheme : R.style.AppTheme;
                MainActivity.this.recreate();
            }
        });
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Tel_Login.class);
                startActivity(intent);
            }
        });
    }

    private void setLinstener() {
        xia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChannelActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_anim, R.anim.exit_anima);
            }
        });


        /**
         * 城市了列表
         */
        mCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });







    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sm_an:
                mMenu.toggle();
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("theme", theme);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        theme = savedInstanceState.getInt("theme");
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
            String qq_Name = data.get("screen_name");
            String tou = data.get("iconurl");

            ImageOptions options = new ImageOptions.Builder()
                    //设置加载过程中的图片
                    .setLoadingDrawableId(R.mipmap.ic_launcher)
                    //设置加载失败后的图片
                    .setFailureDrawableId(R.mipmap.ic_launcher)
                    //设置使用缓存
                    .setUseMemCache(true)
                    //设置显示圆形图片
                    .setCircular(true)
                    //设置支持gif
                    .setIgnoreGif(false)
                    .build();

            x.image().bind(mAn, tou, options);
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

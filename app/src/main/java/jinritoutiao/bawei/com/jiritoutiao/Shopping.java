package jinritoutiao.bawei.com.jiritoutiao;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import com.google.gson.Gson;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import adapter.MyXListViewAdapter;
import bean.Bean;
import util.Urls;
import xlistview.bawei.com.xlistviewlibrary.XListView;

public class Shopping extends AppCompatActivity implements XListView.IXListViewListener {
    private int channe1Id = 0;
    private int startNum = 0;
    private XListView mLxv;
    private Handler handler = new Handler() {
    };
    private Bean mB;
    private List<Bean.DataBean> lbd = new ArrayList<>();
    private int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        initView();
        mLxv.setPullLoadEnable(true);
        mLxv.setPullRefreshEnable(true);
        mLxv.setXListViewListener(this);
        wljx(channe1Id, startNum);
    }

    private void wljx(int channe1Id, int startNum) {
        RequestParams params = new RequestParams(Urls.FIRST);
        params.addQueryStringParameter("channelId", channe1Id + "");
        params.addQueryStringParameter("startNum", startNum + "");

        x.http().get(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                mB = gson.fromJson(result, Bean.class);
                for (int i = 0; i < mB.getData().size(); i++) {
                    lbd.add(mB.getData().get(i));
                }

                mLxv.setAdapter(new MyXListViewAdapter(lbd, Shopping.this));
                //修改xlistview的起始位置
                mLxv.setSelection(num);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });
    }


    private void initView() {

        mLxv = (XListView) findViewById(R.id.shopping_xlv);

        mLxv.setOnScrollListener(new XListView.OnXScrollListener() {
            @Override
            public void onXScrolling(View view) {

            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //如果是停止滑动
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) ;
                //获取当前xlistview所显示的第一条item的position
                num = mLxv.getFirstVisiblePosition();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });


    }

    public void onLoade() {
        mLxv.stopRefresh();
        mLxv.stopLoadMore();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 获取当前系统时间
        String nowTime = df.format(new Date(System.currentTimeMillis()));
        // 释放时提示正在刷新时的当前时间
        mLxv.setRefreshTime(nowTime);
    }

    //刷新
    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startNum = 0;
                wljx(channe1Id, startNum);
                onLoade();
            }
        }, 1000);
    }

    //加载
    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startNum++;
                wljx(channe1Id, startNum);
                onLoade();
            }
        }, 1000);
    }
}
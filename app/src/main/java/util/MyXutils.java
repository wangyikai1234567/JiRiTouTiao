package util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import adapter.MyAdapter;
import bean.DataBeans;
import jinritoutiao.bawei.com.jiritoutiao.CoutentActivity;
import xlistview.bawei.com.xlistviewlibrary.XListView;

/**
 * 用途：
 * author：王艺凯
 * date:2017/4/11.
 */
public class MyXutils {
    private final Context context;
    private final XListView xlistview;
    private final Handler mHandler;

    public MyXutils(Context context, XListView xlistview, Handler mHandler) {
        this.context = context;
        this.xlistview = xlistview;
        this.mHandler = mHandler;
    }

    public void getData(String str) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("正在加载...");
        progressDialog.show();
        //内容请求
        RequestParams params = new RequestParams(Urls.uri);
        params.addQueryStringParameter("uri", str);
        x.http().get(params, new Callback.CacheCallback<String>() {
            public String result;

            @Override
            public void onSuccess(String result) {
                progressDialog.dismiss();

                //如果服务返回304或onCache选择了信任缓存,这时result为null
                DataBeans bean = GsonUtil.getBean(result, DataBeans.class);
                final List<DataBeans.ResultBean.DataBean> data = bean.getResult().getData();
                final MyAdapter adapter = new MyAdapter(context, data);
                xlistview.setAdapter(adapter);
                xlistview.setPullRefreshEnable(true);//下拉刷新
                xlistview.setPullLoadEnable(true);//上拉加载更多

                xlistview.setXListViewListener(new XListView.IXListViewListener() {
                    @Override
                    public void onRefresh() {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                onLoad();
                            }
                        }, 2000);
                    }

                    @Override
                    public void onLoadMore() {

                    }
                });
                //点击按钮分享************************************
                xlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent mIntent = new Intent(context, CoutentActivity.class);

                        mIntent.putExtra("webView", data.get(position).getUrl());
                        context.startActivity(mIntent);

                    }
                });
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
            public boolean onCache(String result) {//得到缓存数据，过期后不会进入
                this.result = result;
                return false;//true: 信任缓存数据, 不再发起网络请求; false不信任缓存数据
            }
        });
    }

    public void onLoad() {
        xlistview.stopRefresh();
        xlistview.stopLoadMore();
        xlistview.setRefreshTime(setXlistTime());
    }

    public String setXlistTime() {
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd HH_mm_ss");//设置时间格式
        String format = sdf.format(date);
        return format;
    }

}

package util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import adapter.ViewPagerAdapter;
import bean.TitleBean;
import bean.MyTitle;

public class TitleXutil {

    private final TabLayout tabLayout;
    private final ViewPager viewPager;
    private final FragmentManager manager;
    private DbManager mDb;
    private List<MyTitle> mLmt;
    private Context mContext;
    private SharedPreferences mSp;
    private boolean mB;
    private List<MyTitle> mFindAll;

    public TitleXutil(TabLayout tabLayout, ViewPager viewPager, FragmentManager manager, Context mContext) {

        this.tabLayout = tabLayout;
        this.viewPager = viewPager;
        this.manager = manager;
        this.mContext = mContext;
    }

    public void getData() {

        mSp = mContext.getSharedPreferences("ceshi", 0);
        mB = mSp.getBoolean("flag", true);

        //获取数据库示例
        DbManager.DaoConfig daoConfig = MyApplication.getDaoConfig();

        mDb = x.getDb(daoConfig);

//        params.addQueryStringParameter("要拼接的字段名", "要拼接的字段内容");

        RequestParams params = new RequestParams(Urls.urititle);
        params.addBodyParameter("uri", "news");
        x.http().post(params, new Callback.CommonCallback<String>() {
            //成功
            @Override
            public void onSuccess(String result) {
                List<TitleBean.ResultBean.DateBean> list = GsonUtil.getBean(result, TitleBean.class).getResult().getDate();

                //存入到数据库
                if (mB) {

                    cun(list);
                    SharedPreferences.Editor editor = mSp.edit();
                    editor.putBoolean("flag", false);
                    editor.commit();
                }
                //查询数据库
                try {
                    mFindAll = mDb.selector(MyTitle.class).expr("codes =1").findAll();

                } catch (DbException e) {
                    e.printStackTrace();
                }

                tabLayout.setupWithViewPager(viewPager);
                viewPager.setAdapter(new ViewPagerAdapter(manager, mFindAll));
                tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                tabLayout.setTabTextColors(Color.BLACK, Color.RED);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });


    }

    private void cun(List<TitleBean.ResultBean.DateBean> list) {
        try {
            //存入数据库
            mLmt = new ArrayList<MyTitle>();
            for (int i = 0; i < list.size(); i++) {
                MyTitle title = new MyTitle();
                title.setTitle(list.get(i).getTitle());
                title.setUri(list.get(i).getUri());
                title.setCodes(1);
                mLmt.add(title);
            }
            mLmt.add(new MyTitle(0, "汽车", 2, null));
            mLmt.add(new MyTitle(0, "情感", 2, null));
            mLmt.add(new MyTitle(0, "女人", 2, null));
            mLmt.add(new MyTitle(0, "健康", 2, null));
            mLmt.add(new MyTitle(0, "美女", 2, null));
            mLmt.add(new MyTitle(0, "游戏", 2, null));
            mLmt.add(new MyTitle(0, "数码", 2, null));
            mLmt.add(new MyTitle(0, "探索", 2, null));
            //保存实体类或实体类的List到数据库
            mDb.save(mLmt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

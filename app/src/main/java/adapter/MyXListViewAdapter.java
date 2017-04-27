package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

import bean.Bean;
import jinritoutiao.bawei.com.jiritoutiao.R;

/**
 * date: 2017/4/24.
 * author: 王艺凯 (lenovo )
 * function:
 */

public class MyXListViewAdapter extends BaseAdapter {
    private List<Bean.DataBean> lbd;
    private Context mContext;

    public MyXListViewAdapter(List<Bean.DataBean> lbd, Context context) {
        this.lbd = lbd;
        mContext = context;
    }

    @Override
    public int getViewTypeCount() {
        return 2;//种类有两种
    }

    //指定索引指向的条目的类型（0代表复用系统）
    @Override
    public int getItemViewType(int position) {
        if (lbd.get(position).getIMAGEURL() != null) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getCount() {
        return lbd.size();
    }

    @Override
    public Object getItem(int position) {
        return lbd.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int type = getItemViewType(position);
        if (type == 0) {
            ViewHolder vh;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.xlv_n, null);
                vh = new ViewHolder();
                vh.iv = (ImageView) convertView.findViewById(R.id.xlv_iv);
                vh.title = (TextView) convertView.findViewById(R.id.xlv_title);
                vh.connect = (TextView) convertView.findViewById(R.id.xlv_nr);
                vh.time = (TextView) convertView.findViewById(R.id.time);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            vh.title.setText(lbd.get(position).getTITLE());
            vh.connect.setText(lbd.get(position).getSUBTITLE());
            vh.time.setText(lbd.get(position).getSHOWTIME());

            ImageOptions options = new ImageOptions.Builder()
                    //设置加载过程中的图片
                    .setLoadingDrawableId(R.mipmap.ic_launcher)
                    //设置加载失败后的图片
                    .setFailureDrawableId(R.mipmap.ic_launcher)
                    //设置使用缓存
                    .setUseMemCache(true)
                    //设置支持gif
                    .setIgnoreGif(false)
                    .build();

            x.image().bind(vh.iv, (String) lbd.get(position).getIMAGEURL(), options);

        } else {
            //正常条目
            ViewHoler1 vh1;
            if (convertView == null) {
                vh1 = new ViewHoler1();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.xlv_n0, null);
                vh1.title = (TextView) convertView.findViewById(R.id.title);
                vh1.connect = (TextView) convertView.findViewById(R.id.nr);
                vh1.time = (TextView) convertView.findViewById(R.id.xlv_time0);
                convertView.setTag(vh1);
            } else {
                vh1 = (ViewHoler1) convertView.getTag();
            }
            vh1.title.setText(lbd.get(position).getTITLE());
            vh1.connect.setText(lbd.get(position).getSUBTITLE());
            vh1.time.setText(lbd.get(position).getSHOWTIME());
        }
        return convertView;
    }

    class ViewHolder {

        TextView title, connect, time;
        ImageView iv;
    }

    class ViewHoler1 {
        TextView title, connect, time;
    }
}

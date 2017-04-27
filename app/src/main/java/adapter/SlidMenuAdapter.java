package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import bean.MySMBean;
import jinritoutiao.bawei.com.jiritoutiao.R;

/**
 * date: 2017/4/14.
 * author: 王艺凯 (lenovo )
 * function:
 */

public class SlidMenuAdapter extends BaseAdapter {
    private List<MySMBean> lms;
    private Context mContext;

    public SlidMenuAdapter(List<MySMBean> lms, Context context) {
        this.lms = lms;
        mContext = context;
    }

    @Override
    public int getCount() {
        return lms.size();
    }

    @Override
    public Object getItem(int position) {
        return lms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_layout, null);
            vh = new ViewHolder();

            vh.iv = (ImageView) convertView.findViewById(R.id.sm_iv);
            vh.tv = (TextView) convertView.findViewById(R.id.sm_tv);

            convertView.setTag(vh);


        } else {
            vh = (ViewHolder) convertView.getTag();

        }
        vh.tv.setText(lms.get(position).getTitle());
        vh.iv.setImageResource(lms.get(position).getImage());

        return convertView;
    }

    class ViewHolder {
        ImageView iv;
        TextView tv;

    }


}

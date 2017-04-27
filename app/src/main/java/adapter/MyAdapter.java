package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.x;

import java.util.List;

import bean.DataBeans;
import jinritoutiao.bawei.com.jiritoutiao.R;

/**
 * 用途：
 * author：
 * date:2017/4/10.
 */

public class MyAdapter extends BaseAdapter {

    private  Context context;
    private  List<DataBeans.ResultBean.DataBean> list;
    private final int ITEM_TYPE1=0;
    private final int ITEM_TYPE2=1;
    private final int ITEM_TYPE3=2;
    public MyAdapter(Context context, List<DataBeans.ResultBean.DataBean> list) {

        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        int type = getItemViewType(i);
        if(type == ITEM_TYPE1){
            //展示灰色条目
            ViewHolder1 viewHolder1;
            if(convertView == null){
                viewHolder1 = new ViewHolder1();
                convertView = View.inflate(context, R.layout.xlist_item, null);
                viewHolder1.one_tv = (TextView) convertView.findViewById(R.id.one_title);
                viewHolder1.one_tv_n = (TextView) convertView.findViewById(R.id.one_n);
                viewHolder1.one_iv = (ImageView) convertView.findViewById(R.id.one_image);
                convertView.setTag(viewHolder1);
            }else{
                viewHolder1 = (ViewHolder1) convertView.getTag();
            }
            viewHolder1.one_tv.setText(list.get(i).getTitle());
            viewHolder1.one_tv_n.setText(list.get(i).getAuthor_name());
            x.image().bind(viewHolder1.one_iv, list.get(i).getThumbnail_pic_s());
            return convertView;
        }else if (type == ITEM_TYPE1){
            //2张图片
            ViewHolder2 viewHolder2;
            if(convertView == null){
                viewHolder2  = new ViewHolder2();
                convertView = View.inflate(context, R.layout.xlist_item2, null);
                viewHolder2.two_tv_title = (TextView) convertView.findViewById(R.id.two_title);
                viewHolder2.two_tv_n = (TextView) convertView.findViewById(R.id.two_n);
                viewHolder2.two_iv1 = (ImageView) convertView.findViewById(R.id.two_iv1);
                viewHolder2.two_iv2 = (ImageView) convertView.findViewById(R.id.two_iv2);
                convertView.setTag(viewHolder2);
            }else{
                viewHolder2 = (ViewHolder2) convertView.getTag();
            }
            viewHolder2.two_tv_title.setText(list.get(i).getTitle());
            viewHolder2.two_tv_n.setText(list.get(i).getAuthor_name());
            x.image().bind(viewHolder2.two_iv1, list.get(i).getThumbnail_pic_s());
            x.image().bind(viewHolder2.two_iv2, list.get(i).getThumbnail_pic_s02());

            return convertView;
        }else {
            ViewHolder3 viewHolder3;
            if (convertView == null) {
                convertView  = View.inflate(context, R.layout.xlist_item3, null);

                viewHolder3 = new ViewHolder3();

                viewHolder3.three_tv_title = (TextView) convertView.findViewById(R.id.three_title);
                viewHolder3.three_tv_n = (TextView) convertView.findViewById(R.id.three_n);
                viewHolder3.three_iv1 = (ImageView) convertView.findViewById(R.id.three_iv1);
                viewHolder3.three_iv2 = (ImageView) convertView.findViewById(R.id.three_iv2);
                viewHolder3.three_iv3 = (ImageView) convertView.findViewById(R.id.three_iv3);



                convertView .setTag(viewHolder3);

            } else {
                viewHolder3 = (ViewHolder3) convertView.getTag();
            }

            viewHolder3.three_tv_title.setText(list.get(i).getTitle());
            viewHolder3.three_tv_n.setText(list.get(i).getAuthor_name());
            x.image().bind(viewHolder3.three_iv1, list.get(i).getThumbnail_pic_s());
            x.image().bind(viewHolder3.three_iv2, list.get(i).getThumbnail_pic_s02());
            x.image().bind(viewHolder3.three_iv3, list.get(i).getThumbnail_pic_s03());
            return convertView ;
        }

    }

    //一张图片
class ViewHolder1{
    ImageView one_iv;
    TextView one_tv,one_tv_n;
}

    //二张图片
    class ViewHolder2{
        ImageView two_iv1,two_iv2;
        TextView two_tv_title,two_tv_n;

    }
    //三张图片
    class ViewHolder3{
        ImageView three_iv1,three_iv2,three_iv3;
        TextView three_tv_title,three_tv_n;
    }
    public int getViewTypeCount() {
        return 3;//种类有三种
    }



    //指定索引指向的条目的类型（0代表复用系统）
    @Override
    public int getItemViewType(int position) {
        //有三张图
        if (list.get(position).getThumbnail_pic_s() != null
                && list.get(position).getThumbnail_pic_s02() != null
                && list.get(position).getThumbnail_pic_s03() != null) {

            //0代表纯文本条目
            return ITEM_TYPE3;
            //只有两张图
        } else if (list.get(position).getThumbnail_pic_s() != null
                && list.get(position).getThumbnail_pic_s02() != null
                && list.get(position).getThumbnail_pic_s03() == null) {
            //1代表文本条目+图片
            return ITEM_TYPE2;
            //只有一张图
        }
            return ITEM_TYPE1;
    }
}

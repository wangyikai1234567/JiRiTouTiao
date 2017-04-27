package fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import jinritoutiao.bawei.com.jiritoutiao.R;
import util.MyXutils;
import xlistview.bawei.com.xlistviewlibrary.XListView;

@ContentView(value = R.layout.fragment_my)
public
class MyFragment extends Fragment{

    @ViewInject(R.id.xlist)
    private XListView xlist;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    public static MyFragment getFragment(String uri){
        MyFragment myFragment = new MyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("uri", uri);
        myFragment.setArguments(bundle);
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this,inflater,container);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
    }
    private void initData() {
        Bundle bundle = getArguments();
        String str = bundle.getString("uri");

        new MyXutils(getActivity(),xlist,mHandler).getData(str);

    }
}

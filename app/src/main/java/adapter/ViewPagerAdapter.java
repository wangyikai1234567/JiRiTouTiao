package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.List;
import fragment.MyFragment;
import bean.MyTitle;

/**
 * 用途：
 * author
 * date:2017/4/10.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<MyTitle> list;
    public ViewPagerAdapter(FragmentManager fm, List<MyTitle> list) {
        super(fm);
        this.list = list;

    }
    @Override
    public Fragment getItem(int position) {
        MyFragment myFragment = MyFragment.getFragment(list.get(position).getUri());
        return myFragment;
    }
    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getTitle();
    }
}

package jinritoutiao.bawei.com.jiritoutiao;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

/**
 * date: 2017/4/14.
 * author: 王艺凯 (lenovo )
 * function:
 */

public class FindPsd extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findpwd_layout);

        initView();

    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.pwd_back);
        mBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pwd_back:
                FindPsd.this.finish();
                break;
        }
    }
}

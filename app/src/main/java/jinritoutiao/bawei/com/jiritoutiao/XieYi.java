package jinritoutiao.bawei.com.jiritoutiao;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import static android.R.attr.id;

/**
 * date: 2017/4/13.
 * author: 王艺凯 (lenovo )
 * function:
 */

public class XieYi extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_protocol_layout);
        initView();
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.left_back);
        mBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_back:
                XieYi.this.finish();
                break;
        }
    }
}

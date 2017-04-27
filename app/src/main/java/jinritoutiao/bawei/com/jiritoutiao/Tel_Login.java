package jinritoutiao.bawei.com.jiritoutiao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;
import util.Urls;
/**
 * date: 2017/4/13.
 * author: 王艺凯 (lenovo )
 * function:
 */

public class Tel_Login extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Tel_Login";
    private EditText mPhone;
    private EditText mPassword;
    private TextView mWjmm;
    private TextView mZc;
    private Button mLogin;
    private ImageView mBack;
    private String mPsd;
    private String mTel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_phone_layout);
        initView();

    }

    private void initView() {

        mPhone = (EditText) findViewById(R.id.edit_phone);
        mPassword = (EditText) findViewById(R.id.edit_password);
        mBack = (ImageView) findViewById(R.id.yj);
        mZc = (TextView) findViewById(R.id.zc_zh);
        mWjmm = (TextView) findViewById(R.id.wjmm);
        mLogin = (Button) findViewById(R.id.login);

        mZc.setOnClickListener(this);
        mWjmm.setOnClickListener(this);
        mLogin.setOnClickListener(this);
        mBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zc_zh:
                Intent intent = new Intent(Tel_Login.this, ZhuCe.class);
                startActivity(intent);

                break;
            case R.id.wjmm:
                Intent intent1 = new Intent(Tel_Login.this, FindPsd.class);
                startActivity(intent1);

                break;
            case R.id.login:

                volleypost();

                break;
            case R.id.yj:
                Tel_Login.this.finish();
                break;
        }
    }

    /**
     * 网络请求
     */
    //volley发送post请
    private void volleypost() {
        //获取到一个requestQueue 对象
        RequestQueue queue = Volley.newRequestQueue(Tel_Login.this);
        String url = Urls.LINK_MOBILE_LOGIN;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                    Intent intent2 = new Intent(Tel_Login.this, MainActivity.class);
                    startActivity(intent2);
                    Log.i(TAG, "post请求成功" + s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i(TAG, "post请求失败" + volleyError.toString());
                Toast.makeText(Tel_Login.this, volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        })
                //这个方法用来拼接uri；
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                mTel = mPhone.getText().toString().trim();
//        Log.i(TAG, "post请求成功" + s);.
                mPsd = mPassword.getText().toString().trim();
                Log.i(TAG, "psd==" + mPsd);
                HashMap<String, String> map = new HashMap<>();
                map.put("username", mTel);
                map.put("password", mPsd);

                map.put("client", Urls.SYSTEM_TYPE);
                Log.i(TAG, map + "post");
                return map;
            }
        };
        queue.add(request);

    }
}

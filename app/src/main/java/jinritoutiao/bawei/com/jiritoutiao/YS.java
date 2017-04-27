package jinritoutiao.bawei.com.jiritoutiao;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import bean.CGBean;
import util.Urls;

public class YS extends AppCompatActivity {

    private static final String TAG = "YS";
    private EditText tel;
    private EditText mPsd;
    private EditText mAgainPsd;
    private EditText mEmail;
    private Button mZc;
    private SharedPreferences mSp;
    private String mTelNumber;
    private String mPsd1;
    private String mAgainPsd1;
    private String mEmail1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ys);
        initView();
        mSp = getSharedPreferences("config", 0);
    }

    private void initView() {

        tel = (EditText) findViewById(R.id.ys_tel);
        mPsd = (EditText) findViewById(R.id.ys_psd);
        mAgainPsd = (EditText) findViewById(R.id.ys_psd_again);
        mEmail = (EditText) findViewById(R.id.ys_email);

        mZc = (Button) findViewById(R.id.ys_login);

        mZc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTelNumber = tel.getText().toString().trim();
                mPsd1 = mPsd.getText().toString().trim();
                mAgainPsd1 = mAgainPsd.getText().toString().trim();
                mEmail1 = mEmail.getText().toString().trim();
                if (mTelNumber.isEmpty() || mPsd1.isEmpty() || mAgainPsd1.isEmpty() || mEmail1.isEmpty()) {
                    Toast.makeText(YS.this, "请完善您的信息", Toast.LENGTH_SHORT).show();
                } else {
                    //注册成功
                    if (mTelNumber.length() == 11 && mPsd1.equals(mAgainPsd1) && mEmail1.length() >= 7 && mEmail1.length() <= 20 && mPsd1.length() >= 6 && mPsd1.length() <= 20) {
                        Toast.makeText(YS.this, "注册成功", Toast.LENGTH_SHORT).show();
                        //网络请求
                        volleypost();
                    } else if (mTelNumber.length() != 11) {
                        Toast.makeText(YS.this, "请输入正确手机号", Toast.LENGTH_SHORT).show();
                    } else if (mPsd1.length() < 6 || mPsd1.length() > 20) {
                        Toast.makeText(YS.this, "密码在6~20位之间，请重新输入", Toast.LENGTH_SHORT).show();
                    } else if (!mPsd1.equals(mAgainPsd1)) {
                        Toast.makeText(YS.this, "两次密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
                    } else if (mEmail1.length() < 7 || mEmail1.length() > 20) {
                        Toast.makeText(YS.this, "请输入正确格式的邮箱地址", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    /**
     * 网络请求
     */
    //volley发送post请
    private void volleypost() {
        //获取到一个requestQueue 对象
        RequestQueue queue = Volley.newRequestQueue(YS.this);
        String url = Urls.LINK_MOBILE_REG;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                CGBean cgb = gson.fromJson(s, CGBean.class);
                int code = cgb.getCode();
                SharedPreferences.Editor editor = mSp.edit();
                editor.putInt("code", code);
                editor.commit();
                Intent intent = new Intent(YS.this, Tel_Login.class);
                startActivity(intent);
                Log.i(TAG, "post请求成功" + s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i(TAG, "post请求失败" + volleyError.toString());
                Toast.makeText(YS.this, volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        })
                //这个方法用来拼接uri；
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("username", mTelNumber);
                map.put("password", mPsd1);
                map.put("password_confirm", mAgainPsd1);
                map.put("email", mEmail1);
                map.put("client", Urls.SYSTEM_TYPE);
                return map;
            }
        };
        queue.add(request);
    }
}

package jinritoutiao.bawei.com.jiritoutiao;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

/**
 * date: 2017/4/13.
 * author: 王艺凯 (lenovo )
 * function:
 */

public class ZhuCe extends AppCompatActivity implements View.OnClickListener {

    private EditText mZhucephone;
    private RadioButton mRb;
    private TextView mTextView;
    private ImageView mImageView;
    private Button mButton;
    private String mPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_zhu_ce);

        initView();

    }

    private void initView() {
        mZhucephone = (EditText) findViewById(R.id.zhuce_phone);
        mRb = (RadioButton) findViewById(R.id.radioButton);
        mTextView = (TextView) findViewById(R.id.xy);
        mImageView = (ImageView) findViewById(R.id.zhuce_im_fanhui);
        mButton = (Button) findViewById(R.id.zc_next);
        mTextView.setOnClickListener(this);
        mImageView.setOnClickListener(this);
        mButton.setOnClickListener(this);
    }

    //点击监听
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zhuce_im_fanhui:
                ZhuCe.this.finish();

                break;
            case R.id.xy:
                Intent intent = new Intent(ZhuCe.this, XieYi.class);
                startActivity(intent);
                break;
            case R.id.zc_next:

                mPhoneNumber = mZhucephone.getText().toString().trim();
                if (mPhoneNumber.length() == 11) {
                    if (mRb.isChecked()) {
                        AlertDialog.Builder ab = new AlertDialog.Builder(this);
                        ab.setTitle("我们将要向向您的手机发送短信，是否同意");
                        ab.setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent1 = new Intent(ZhuCe.this, ZhuCe_Send.class);
                                intent1.putExtra("tel", mPhoneNumber);
                                startActivity(intent1);
                            }
                        });
                        ab.setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        ab.show();
                    }else {
                        Toast.makeText(ZhuCe.this, "请同意今日头条协议", Toast.LENGTH_SHORT).show();

                    }


                } else {
                    Toast.makeText(ZhuCe.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                }

                break;
        }

    }
}

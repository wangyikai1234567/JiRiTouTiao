package jinritoutiao.bawei.com.jiritoutiao;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * date: 2017/4/14.
 * author: 王艺凯 (lenovo )
 * function:
 */

public class SheZhi extends AppCompatActivity implements View.OnClickListener {

    private TextView mWancheng;
    private ImageView mPhoto;
    private ImageView mCha;
    private EditText mEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.myself_info_layout);
        initView();
    }

    private void initView() {
        mWancheng = (TextView) findViewById(R.id.info_wancheng);
        mPhoto = (ImageView) findViewById(R.id.info_photo);
        mCha = (ImageView) findViewById(R.id.iv_default2);

        mEdit = (EditText) findViewById(R.id.info_edit);


        mWancheng.setOnClickListener(this);
        mPhoto.setOnClickListener(this);
        mCha.setOnClickListener(this);
        mEdit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击头像上传图片
            case R.id.info_photo:
                final AlertDialog.Builder builder = new AlertDialog.Builder(SheZhi.this);
                //    指定下拉列表的显示数据
                final String[] choose = {"从相册选择", "拍照", "取消"};
                //    设置一个下拉的列表选择项
                builder.setItems(choose, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intent2 = new Intent();
                                intent2.setAction(intent2.ACTION_PICK);
                                intent2.setType("image/*");
                                startActivityForResult(intent2, 2);

                                break;
                            case 1:
                                Intent intent1 = new Intent();
                                intent1.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent1, 1);
                                break;
                            case 2:
                                break;

                        }
                    }
                });
                builder.show();
                break;
            //点击完成跳转界面
            case R.id.info_wancheng:
                String nc = mEdit.getText().toString().trim();
                if (nc.isEmpty()) {
                    Toast.makeText(SheZhi.this, "请输入昵称", Toast.LENGTH_SHORT).show();

                } else {
                    Intent intent = new Intent(SheZhi.this, MainActivity.class);
                    startActivity(intent);
                }
                break;
            //点击×取消ed中的内容
            case R.id.iv_default:
                mEdit.setText(null);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            //得到照片
            Bitmap bitmap = data.getParcelableExtra("data");

            mPhoto.setImageBitmap(bitmap);
        }
        if (requestCode == 2) {
            //得到像册中图片的地址
            Uri uri = data.getData();
            //image_login.setImageURI(uri);
            crop(uri);
        } else if (requestCode == 9999) {
            //得到裁剪后的照片
            Bitmap bimap = data.getParcelableExtra("data");
            mPhoto.setImageBitmap(bimap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //是否裁剪
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", false);// 取消人脸识别

        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 9999);
    }

}

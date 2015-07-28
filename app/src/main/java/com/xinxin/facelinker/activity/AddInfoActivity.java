package com.xinxin.facelinker.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.easemob.chatuidemo.R;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xinxin.facelinker.Config;
import com.xinxin.facelinker.domain.User;

public class AddInfoActivity extends Activity {

    private EditText etEmail;
    private EditText etNickname;
    private EditText etGender;
    private EditText etBirthday;
    private Button btnOk;
    private ImageView ivPhoto;
    private String photo;
    private String phone_num;
    private EditText etJob;
    HttpUtils httpUtils = new HttpUtils();
    private static int requestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etNickname = (EditText) findViewById(R.id.etNickname);
        etGender = (EditText) findViewById(R.id.etGender);
        etBirthday = (EditText) findViewById(R.id.etBirthday);
        btnOk = (Button) findViewById(R.id.btnOk);
        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        phone_num = getIntent().getStringExtra(Config.KEY_PHONE_NUM);
        etJob = (EditText) findViewById(R.id.etJob);
        ivPhoto.setImageResource(R.drawable.photo);

        //头像设置
        ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, requestCode);
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (etBirthday.getText().toString().isEmpty() == true || etGender.getText().toString().isEmpty() == true || etEmail.getText().toString().isEmpty() == true || etNickname.getText().toString().isEmpty() == true) {
                    Toast.makeText(AddInfoActivity.this, R.string.information_is_not_enough, Toast.LENGTH_SHORT).show();
                    return;
                }

                final ProgressDialog pd = ProgressDialog.show(AddInfoActivity.this, getResources().getString(R.string.information_connecting), getResources().getString(R.string.information_connecting_to_server));
                RequestParams params = new RequestParams();
                params.addBodyParameter(Config.ACTION, Config.ACTION_ADD_INFO);
                params.addBodyParameter(Config.KEY_PHONE_NUM, phone_num);
                params.addBodyParameter(Config.KEY_GENDER, etGender.getText().toString());
                params.addBodyParameter(Config.KEY_EMAIL, etEmail.getText().toString());
                params.addBodyParameter(Config.KEY_BIRTHDAY, etBirthday.getText().toString());
                params.addBodyParameter(Config.KEY_NICKNAME, etNickname.getText().toString());
                params.addBodyParameter(Config.KEY_PHOTO, photo);
                params.addBodyParameter(Config.KEY_JOB, etJob.getText().toString());
                httpUtils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        pd.dismiss();

                        try {
                            //创建数据库，保存用户数据
                            DbUtils db= DbUtils.create(AddInfoActivity.this);
                            User user=new User();
                            db.saveBindingId(user);
                        } catch (DbException e) {
                            e.printStackTrace();
                        }

                        Intent i = new Intent(AddInfoActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        pd.dismiss();
                        Toast.makeText(AddInfoActivity.this, R.string.information_fail, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (data == null)
                return;
            else {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap bm = extras.getParcelable("data");
                    ivPhoto.setImageBitmap(bm);
                    photo = bm.toString();
                }
            }
        }

    }
}

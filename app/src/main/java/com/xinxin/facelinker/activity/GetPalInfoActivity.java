package com.xinxin.facelinker.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.chatuidemo.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xinxin.facelinker.Config;
import com.xinxin.facelinker.domain.GetPalInfo;
import com.xinxin.facelinker.utils.NetHelper;

public class GetPalInfoActivity extends Activity {

    private TextView tvAccountNum;
    private TextView tvNickname;
    private TextView tvGender;
    private TextView tvBirthday;
    private TextView tvMood;
    private TextView tvHonour;
    private ImageView ivPhoto;
    private Button btnAddPal;
    private Button btnSendMessage;
    private Button btnAddBlackList;
    private String my_account_num;
    private String other_account_num;
    HttpUtils httpUtils = new HttpUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_pal_info);

        tvAccountNum = (TextView) findViewById(R.id.tvAccountNum);
        tvNickname = (TextView) findViewById(R.id.tvNickname);
        tvGender = (TextView) findViewById(R.id.tvGender);
        tvBirthday = (TextView) findViewById(R.id.tvBirthday);
        tvMood = (TextView) findViewById(R.id.tvMood);
        tvHonour = (TextView) findViewById(R.id.tvHonour);
        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        btnAddPal = (Button) findViewById(R.id.btnAddPals);
        btnSendMessage = (Button) findViewById(R.id.btnSendMessage);
        btnAddBlackList = (Button) findViewById(R.id.btnAddBlackList);

        ivPhoto.setImageResource(R.drawable.photo);

        my_account_num = Config.getCachedAccountNum(GetPalInfoActivity.this);
        other_account_num = getIntent().getStringExtra(Config.KEY_OTHER_ACCOUNT_NUM);

        final ProgressDialog pd = ProgressDialog.show(GetPalInfoActivity.this, getResources().getString(R.string.get_contacts_connecting), getResources().getString(R.string.get_pal_info_connecting_to_server));
        //new GetPalInfoNet(my_account_num,other_account_num ,new GetPalInfoNet.SuccessCallback() {
        RequestParams params = new RequestParams();
        params.addBodyParameter(Config.ACTION, Config.ACTION_GET_PAL_INFO);
        params.addBodyParameter(Config.KEY_MY_ACCOUNT_NUM, my_account_num);
        params.addBodyParameter(Config.KEY_OTHER_ACCOUNT_NUM, other_account_num);
        httpUtils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                pd.dismiss();
                GetPalInfo getPalInfo =
                        (GetPalInfo) NetHelper.parseJsonData(responseInfo.result, GetPalInfo.class);

//                    InputStream is = new ByteArrayInputStream(jsonObject.getString(Config.KEY_PHOTO).getBytes());
//                    Bitmap bitmap;
//                    bitmap = BitmapFactory.decodeStream(is);
//                    ivPhoto.setImageBitmap(bitmap);

                tvAccountNum.setText(other_account_num);
                tvNickname.setText(getPalInfo.getNickname());
                tvGender.setText(getPalInfo.getGender());
                tvBirthday.setText(getPalInfo.getBirthday());
                tvMood.setText(getPalInfo.getMood());
                tvHonour.setText(getPalInfo.getHonour());

                //添加好友按钮
                btnAddPal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                            final EditText input = new EditText(GetPalInfoActivity.this);    //定义一个EditText
//
//                            final AlertDialog.Builder builder = new AlertDialog.Builder(GetPalInfoActivity.this);
//                            builder.setTitle("你要说的话");
//                            // builder.setIcon(android.R.drawable.ic_menu_info_details);
//                            builder.setView(input);

                        RequestParams params = new RequestParams();
                        params.addBodyParameter(Config.ACTION, Config.ACTION_ACCEPT_ADD_NEW_PAL);
                        params.addBodyParameter(Config.KEY_MY_ACCOUNT_NUM, my_account_num);
                        params.addBodyParameter(Config.KEY_OTHER_ACCOUNT_NUM, other_account_num);
                        httpUtils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params, new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                Toast.makeText(GetPalInfoActivity.this, R.string.add_new_pal_success, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(HttpException e, String s) {
                                Toast.makeText(GetPalInfoActivity.this, R.string.add_new_pal_fail, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                //发送消息按钮
                btnSendMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                btnAddBlackList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RequestParams params = new RequestParams();
                        params.addBodyParameter(Config.ACTION, Config.ACTION_ADD_BLACKLIST);
                        params.addBodyParameter(Config.KEY_MY_ACCOUNT_NUM, my_account_num);
                        params.addBodyParameter(Config.KEY_OTHER_ACCOUNT_NUM, other_account_num);
                        httpUtils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params, new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                Toast.makeText(GetPalInfoActivity.this, R.string.add_blacklist_success, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(HttpException e, String s) {
                                Toast.makeText(GetPalInfoActivity.this, R.string.add_blacklist_fail, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }

            @Override
            public void onFailure(HttpException e, String s) {
                pd.dismiss();
                Toast.makeText(GetPalInfoActivity.this, R.string.get_contacts_fail, Toast.LENGTH_SHORT).show();
            }
        });

    }
}

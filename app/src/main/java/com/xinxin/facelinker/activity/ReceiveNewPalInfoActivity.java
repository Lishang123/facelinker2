

/*
 * User: xinxin
 * Date:
 * Describe:检查联系人申请
 *
 */

package com.xinxin.facelinker.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.xinxin.facelinker.domain.ReceiveNewPalInfo;
import com.xinxin.facelinker.utils.NetHelper;

public class ReceiveNewPalInfoActivity extends Activity {

    private TextView tvAccountNum;
    private TextView tvNickname;
    private TextView tvMessage;
    private String my_account_num;
    private String other_account_num;
    private Button btnAccept;
    private Button btnRefuse;
    private HttpUtils httpUtils=new HttpUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_new_pal_info);

        tvAccountNum= (TextView) findViewById(R.id.tvAccountNum);
        tvNickname= (TextView) findViewById(R.id.tvNickname);
        tvMessage= (TextView) findViewById(R.id.tvMessage);
        my_account_num= getIntent().getStringExtra(Config.KEY_MY_ACCOUNT_NUM);
        other_account_num=getIntent().getStringExtra(Config.KEY_OTHER_ACCOUNT_NUM);
        btnAccept= (Button) findViewById(R.id.btnAccept);
        btnRefuse= (Button) findViewById(R.id.btnRefuse);

        RequestParams params = new RequestParams();
        params.addBodyParameter(Config.ACTION, Config.ACTION_RECEIVE_NEW_PAL_INFO);
        params.addBodyParameter(Config.KEY_MY_ACCOUNT_NUM, my_account_num);
        params.addBodyParameter(Config.KEY_OTHER_ACCOUNT_NUM, other_account_num);
        final ProgressDialog pd = ProgressDialog.show(ReceiveNewPalInfoActivity.this, getResources().getString(R.string.receive_new_pal_info_connecting), getResources().getString(R.string.receive_new_pal_info_connecting_to_server));
        httpUtils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ReceiveNewPalInfo receiveNewPalInfo =
                        (ReceiveNewPalInfo) NetHelper.parseJsonData(responseInfo.result, ReceiveNewPalInfo.class);

                tvAccountNum.setText(receiveNewPalInfo.getOther_account_num());
                tvNickname.setText(receiveNewPalInfo.getNickname());
                tvMessage.setText(receiveNewPalInfo.getMessage());

                //接受按钮
                btnAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RequestParams params = new RequestParams();
                        params.addBodyParameter(Config.ACTION, Config.ACTION_ACCEPT_ADD_NEW_PAL);
                        params.addBodyParameter(Config.KEY_MY_ACCOUNT_NUM, my_account_num);
                        params.addBodyParameter(Config.KEY_OTHER_ACCOUNT_NUM, other_account_num);
                        httpUtils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params, new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                Toast.makeText(ReceiveNewPalInfoActivity.this, R.string.answer_new_pal_success,Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(HttpException e, String s) {
                                Toast.makeText(ReceiveNewPalInfoActivity.this, R.string.answer_new_pal_fail,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                //拒绝按钮
                btnRefuse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RequestParams params = new RequestParams();
                        params.addBodyParameter(Config.ACTION, Config.ACTION_REFUSE_ADD_NEW_PAL);
                        params.addBodyParameter(Config.KEY_MY_ACCOUNT_NUM, my_account_num);
                        params.addBodyParameter(Config.KEY_OTHER_ACCOUNT_NUM, other_account_num);
                        httpUtils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params, new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                Toast.makeText(ReceiveNewPalInfoActivity.this, R.string.answer_new_pal_success,Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(HttpException e, String s) {
                                Toast.makeText(ReceiveNewPalInfoActivity.this, R.string.answer_new_pal_fail,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }

            @Override
            public void onFailure(HttpException e, String s) {
                pd.dismiss();
                Toast.makeText(ReceiveNewPalInfoActivity.this, R.string.receive_new_pals_fail, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

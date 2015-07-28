package com.xinxin.facelinker.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.easemob.chatuidemo.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xinxin.facelinker.Config;

public class SendNewPalMessageActivity extends Activity {

    private EditText etSendNewPalMessage;
    private Button btnSendNewPalMessage;
    private HttpUtils httpUtils=new HttpUtils();
    private String my_account_num;
    private String other_account_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_new_pal_message);

        etSendNewPalMessage= (EditText) findViewById(R.id.etSendNewPalMessage);
        btnSendNewPalMessage= (Button) findViewById(R.id.btnSendNewPalMessage);
        my_account_num=getIntent().getStringExtra(Config.KEY_MY_ACCOUNT_NUM);
        other_account_num=getIntent().getStringExtra(Config.KEY_OTHER_ACCOUNT_NUM);

        btnSendNewPalMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestParams params = new RequestParams();
                params.addBodyParameter(Config.ACTION, Config.ACTION_SEND_NEW_PAL_MESSAGE);
                params.addBodyParameter(Config.KEY_MY_ACCOUNT_NUM, my_account_num);
                params.addBodyParameter(Config.KEY_OTHER_ACCOUNT_NUM, other_account_num);
                params.addBodyParameter(Config.KEY_SEND_NEW_PAL_MESSAGE, etSendNewPalMessage.getText().toString());
                httpUtils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Toast.makeText(SendNewPalMessageActivity.this, R.string.send_new_pal_message_success,Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Toast.makeText(SendNewPalMessageActivity.this, R.string.send_new_pal_message_fail,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}

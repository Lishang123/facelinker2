package com.xinxin.facelinker.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.easemob.chatuidemo.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xinxin.facelinker.Config;
import com.xinxin.facelinker.domain.ReceiveNewPals;
import com.xinxin.facelinker.utils.NetHelper;

public class ReceiveNewPalsActivity extends Activity {

    private ListView lvReceiveNewPals;
    private String my_account_num;
    private String other_account_num;
    private ArrayAdapter adapter;
    private String receive_new_pal_message;
    private HttpUtils httpUtils = new HttpUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_new_pals);

        lvReceiveNewPals = (ListView) findViewById(R.id.lvReceiveNewPals);
        adapter = new ArrayAdapter<String>(ReceiveNewPalsActivity.this, android.R.layout.simple_list_item_1);
        lvReceiveNewPals.setAdapter(adapter);
        my_account_num = Config.getCachedAccountNum(ReceiveNewPalsActivity.this);

        RequestParams params = new RequestParams();
        params.addBodyParameter(Config.ACTION, Config.ACTION_RECEIVE_NEW_PALS);
        params.addBodyParameter(Config.KEY_MY_ACCOUNT_NUM, my_account_num);
        final ProgressDialog pd = ProgressDialog.show(ReceiveNewPalsActivity.this, getResources().getString(R.string.receive_new_pals_connecting), getResources().getString(R.string.receive_new_pals_connecting_to_server));
        httpUtils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                final ReceiveNewPals receiveNewPals =
                        (ReceiveNewPals) NetHelper.parseJsonData(responseInfo.result, ReceiveNewPals.class);

                for (int i = 0; i < receiveNewPals.getOther_account_num().length; i++) {
                    adapter.add(receiveNewPals.getOther_account_num()[i]);
                    adapter.notifyDataSetChanged();
                }

                lvReceiveNewPals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        other_account_num = receiveNewPals.getOther_account_num()[position];
                        Intent intent=new Intent(ReceiveNewPalsActivity.this,ReceiveNewPalInfoActivity.class);
                        intent.putExtra(Config.KEY_MY_ACCOUNT_NUM,my_account_num);
                        intent.putExtra(Config.KEY_OTHER_ACCOUNT_NUM,other_account_num);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onFailure(HttpException e, String s) {
                pd.dismiss();
                Toast.makeText(ReceiveNewPalsActivity.this,R.string.receive_new_pals_fail,Toast.LENGTH_SHORT).show();
            }
        });
    }
}

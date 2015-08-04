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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xinxin.facelinker.Config;
import com.xinxin.facelinker.adapter.ShowToplistAdapter;
import com.xinxin.facelinker.domain.ShowToplist;

import java.util.ArrayList;
import java.util.List;

public class ShowFemaleToplistActivity extends Activity {

    private ListView lvShowFemaleToplist;
    private ArrayAdapter adapter;
    private HttpUtils httpUtils = new HttpUtils();
    private List<ShowToplist> list = new ArrayList<ShowToplist>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_male_toplist);

        lvShowFemaleToplist = (ListView) findViewById(R.id.lvShowMaleToplist);
        adapter = new ArrayAdapter<ShowToplistAdapter>(ShowFemaleToplistActivity.this, android.R.layout.simple_list_item_1);
        lvShowFemaleToplist.setAdapter(adapter);

        final ProgressDialog pd = ProgressDialog.show(ShowFemaleToplistActivity.this, getResources().getString(R.string.show_female_toplist_connecting), getResources().getString(R.string.show_female_toplist_connecting_to_server));
        RequestParams params = new RequestParams();
        params.addBodyParameter(Config.ACTION, Config.ACTION_SHOW_FEMALE_TOPLIST);
        httpUtils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                pd.dismiss();
                Gson gson = new Gson();
                list = gson.fromJson(responseInfo.result, new TypeToken<List<ShowToplist>>() {
                }.getType());
                for (int i = 0; i < list.size(); i++) {
                    adapter.add(new ShowToplistAdapter(list.get(i).getAccount_num(), list.get(i).getPhotoUri()));
                }

                lvShowFemaleToplist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String account_num = list.get(position).getAccount_num();
                        Intent intent = new Intent(ShowFemaleToplistActivity.this, GetPalInfoActivity.class);
                        intent.putExtra(Config.KEY_OTHER_ACCOUNT_NUM, account_num);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(HttpException e, String s) {
                pd.dismiss();
                Toast.makeText(ShowFemaleToplistActivity.this, R.string.show_female_toplist_fail, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

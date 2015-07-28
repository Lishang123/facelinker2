package com.xinxin.facelinker.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.xinxin.facelinker.adapter.MyMotionAdapter;
import com.xinxin.facelinker.domain.ShowMyMotion;

import java.util.ArrayList;
import java.util.List;

public class ShowMyMotionActivity extends Activity {

    private ListView lvMyMotion;
    private Button btnAddMyMotion;
    private String my_account_num;
    private ArrayAdapter adapter;
    private String motion_id;
    private String comment;
    private List<ShowMyMotion> list = new ArrayList<ShowMyMotion>();
    private HttpUtils httpUtils = new HttpUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_my_motion);

        lvMyMotion = (ListView) findViewById(R.id.lvMyMotion);
        btnAddMyMotion = (Button) findViewById(R.id.btnAddMyMotion);
        my_account_num = getIntent().getStringExtra(Config.KEY_MY_ACCOUNT_NUM);
        adapter = new ArrayAdapter<MyMotionAdapter>(this, android.R.layout.simple_list_item_1);

        //添加动态
        btnAddMyMotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowMyMotionActivity.this, AddMyMotionActivity.class);
                intent.putExtra(Config.KEY_MY_ACCOUNT_NUM, my_account_num);
                startActivity(intent);
            }
        });

        final ProgressDialog pd = ProgressDialog.show(ShowMyMotionActivity.this, getResources().getString(R.string.show_my_motion_connecting), getResources().getString(R.string.show_my_motion_connecting_to_server));
        //new ShowMyMotionNet(my_account_num, new ShowMyMotionNet.SuccessCallback() {
        RequestParams params = new RequestParams();
        params.addBodyParameter(Config.ACTION, Config.ACTION_SHOW_MY_MOTION);
        params.addBodyParameter(Config.KEY_MY_ACCOUNT_NUM, my_account_num);
        httpUtils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                pd.dismiss();
                Gson gson = new Gson();
                list = gson.fromJson(responseInfo.result, new TypeToken<List<ShowMyMotion>>() {
                }.getType());
                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j < list.get(i).getComment().length; j++) {

                        comment = comment + list.get(i).getComment()[j] + '\n';
                    }
                    adapter.add(new MyMotionAdapter(list.get(i).getMotion(), comment, list.get(i).getDate()));
                    adapter.notifyDataSetChanged();
                }

                lvMyMotion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        motion_id = list.get(position).getMotion_id();
                        Intent intent = new Intent(ShowMyMotionActivity.this, ShowMotionActivity.class);
                        intent.putExtra(Config.KEY_MY_ACCOUNT_NUM, my_account_num);
                        intent.putExtra(Config.KEY_MOTION_ID, motion_id);
                        startActivity(intent);
                    }
                });

                lvMyMotion.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {

                        motion_id = list.get(position).getMotion_id();
                        RequestParams params = new RequestParams();
                        params.addBodyParameter(Config.ACTION, Config.ACTION_DELETE_MY_MOTION);
                        params.addBodyParameter(Config.KEY_MY_ACCOUNT_NUM, my_account_num);
                        params.addBodyParameter(Config.KEY_MOTION_ID, motion_id);
                        httpUtils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params, new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                adapter.remove(lvMyMotion.getItemAtPosition(position));
                                adapter.notifyDataSetChanged();
                                lvMyMotion.invalidate();
                                Toast.makeText(ShowMyMotionActivity.this, R.string.delete_my_motion_fail, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(HttpException e, String s) {
                                Toast.makeText(ShowMyMotionActivity.this, R.string.delete_my_motion_success, Toast.LENGTH_SHORT).show();
                            }
                        });

                        return false;
                    }
                });
            }

            @Override
            public void onFailure(HttpException e, String s) {
                pd.dismiss();
                Toast.makeText(ShowMyMotionActivity.this, R.string.show_my_motion_fail, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

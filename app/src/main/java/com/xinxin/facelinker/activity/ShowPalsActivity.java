package com.xinxin.facelinker.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.xinxin.facelinker.domain.ShowPals;

import java.util.ArrayList;
import java.util.List;

public class ShowPalsActivity extends Activity {

    private Button btnSearch;
    private ListView lvShowPals;
    private String my_account_num;
    private String other_account_num;
    private ArrayAdapter adapter;
    private EditText etSearch;
    private HttpUtils httpUtils = new HttpUtils();
    private List<ShowPals> list = new ArrayList<ShowPals>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pals);

        btnSearch = (Button) findViewById(R.id.btnSearch);
        lvShowPals = (ListView) findViewById(R.id.lvShowPals);
        etSearch = (EditText) findViewById(R.id.etSearch);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        lvShowPals.setAdapter(adapter);
        my_account_num = Config.getCachedAccountNum(ShowPalsActivity.this);

        //测试

        final ProgressDialog pd = ProgressDialog.show(ShowPalsActivity.this, getResources().getString(R.string.get_contacts_connecting), getResources().getString(R.string.get_contacts_connecting_to_server));
//        new ShowPalsNet(my_account_num, new ShowPalsNet.SuccessCallback() {
        RequestParams params = new RequestParams();
        params.addBodyParameter(Config.ACTION, Config.ACTION_SHOW_PALS);
        params.addBodyParameter(Config.KEY_MY_ACCOUNT_NUM, my_account_num);
        httpUtils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        pd.dismiss();

//                        Gson gson = new Gson();
//                        list = gson.fromJson(responseInfo.result, new TypeToken<List<ShowPals>>() {
//                        }.getType());
//                        System.out.println(list.toArray().length);
//                        for (int i = 0; i < list.size(); i++) {
//                            adapter.add(list.get(i).getNickname());
//                            adapter.notifyDataSetChanged();
//                        }

                        lvShowPals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                other_account_num = list.get(position).getOther_account_num();
                                Intent intent = new Intent(ShowPalsActivity.this, GetPalInfoActivity.class);
                                intent.putExtra(Config.KEY_OTHER_ACCOUNT_NUM, other_account_num);
                                startActivity(intent);
                            }
                        });
                    }
                    @Override
                    public void onFailure(HttpException e, String s) {
                        pd.dismiss();
                        Toast.makeText(ShowPalsActivity.this, R.string.get_contacts_fail, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search_user_account_num=etSearch.getText().toString();

                if(etSearch.getText().toString().isEmpty()==true){
                    Toast.makeText(ShowPalsActivity.this, R.string.search_can_not_be_empty, Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent=new Intent(ShowPalsActivity.this,SearchUserActivity.class);
                intent.putExtra(Config.KEY_SEARCH_USER_ACCOUNT_NUM,search_user_account_num);
                startActivity(intent);
            }
        });
    }
}

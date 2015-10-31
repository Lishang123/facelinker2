/*
 * User: xinxin
 * Date:
 * Describe:搜索好友
 *
 */

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
import com.xinxin.facelinker.domain.SearchUser;
import com.xinxin.facelinker.utils.NetHelper;

public class SearchUserActivity extends Activity {

    private ListView lvSearchUsers;
    private ArrayAdapter adapter;
    private String search_user_account_num;
    private String my_account_num;
    private HttpUtils httpUtils = new HttpUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        search_user_account_num = getIntent().getStringExtra(Config.KEY_SEARCH_USER_ACCOUNT_NUM);
        my_account_num = Config.getCachedAccountNum(SearchUserActivity.this);
        lvSearchUsers = (ListView) findViewById(R.id.lvSearchUsers);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        lvSearchUsers.setAdapter(adapter);

        final ProgressDialog pd = ProgressDialog.show(SearchUserActivity.this, getResources().getString(R.string.search_users_connecting), getResources().getString(R.string.search_users_connecting_to_server));
        //new SearchUserNet(search_user_account_num, my_account_num,new SearchUserNet.SuccessCallback() {
        RequestParams params = new RequestParams();
        params.addBodyParameter(Config.ACTION, Config.ACTION_SEARCH_USER);
        params.addBodyParameter(Config.KEY_MY_ACCOUNT_NUM, my_account_num);
        params.addBodyParameter(Config.KEY_SEARCH_USER_ACCOUNT_NUM, search_user_account_num);
        httpUtils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                final SearchUser searchUser =
                        (SearchUser) NetHelper.parseJsonData(responseInfo.result, SearchUser.class);
                if (searchUser.getOther_account_num() == null) {
                    Toast.makeText(SearchUserActivity.this, R.string.search_users_is_empty, Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < searchUser.getOther_account_num().length; i++) {
                        adapter.add(searchUser.getOther_account_num()[i]);
                        adapter.notifyDataSetChanged();
                    }
                }
                //列表点击弹出好友信息
                lvSearchUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(SearchUserActivity.this, GetPalInfoActivity.class);
                        intent.putExtra(Config.KEY_OTHER_ACCOUNT_NUM, searchUser.getOther_account_num()[position]);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(HttpException e, String s) {
                pd.dismiss();
                Toast.makeText(SearchUserActivity.this, R.string.search_users_fail, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
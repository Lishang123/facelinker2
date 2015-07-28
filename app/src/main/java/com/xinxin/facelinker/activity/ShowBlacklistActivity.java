package com.xinxin.facelinker.activity;

import android.app.Activity;
import android.app.ProgressDialog;
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
import com.xinxin.facelinker.domain.ShowBlacklist;
import com.xinxin.facelinker.utils.NetHelper;

/**
 * 黑名单列表页面
 */
public class ShowBlacklistActivity extends Activity {
    private ListView lvBlacklist;
    //	private BlacklistAdapater adapter;
    private ArrayAdapter adapter;
    private String my_account_num;
    private String other_account_num;
    HttpUtils httpUtils = new HttpUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_list);

        lvBlacklist = (ListView) findViewById(R.id.lvBlacklist);

        lvBlacklist = (ListView) findViewById(R.id.lvBlacklist);
        my_account_num = getIntent().getStringExtra(Config.KEY_MY_ACCOUNT_NUM);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        lvBlacklist.setAdapter(adapter);

        final ProgressDialog pd = ProgressDialog.show(ShowBlacklistActivity.this, getResources().getString(R.string.blacklist_connecting), getResources().getString(R.string.blacklist_connecting_to_server));
        //new ShowBlacklistNet(my_account_num, new ShowBlacklistNet.SuccessCallback()
        RequestParams params = new RequestParams();
        params.addBodyParameter(Config.ACTION, Config.ACTION_SHOW_BLACKLIST);
        params.addBodyParameter(Config.KEY_MY_ACCOUNT_NUM, my_account_num);
        httpUtils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                final ShowBlacklist showBlacklist = (ShowBlacklist) NetHelper.parseJsonData(responseInfo.result, ShowBlacklist.class);
                for (int i = 0; i < showBlacklist.getOther_account_num().length; i++) {
                    adapter.add(showBlacklist.getOther_account_num()[i]);
                }

                lvBlacklist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
                        other_account_num = showBlacklist.getOther_account_num()[position];

                        RequestParams params = new RequestParams();
                        params.addBodyParameter(Config.ACTION, Config.ACTION_DELETE_BLACKLIST);
                        params.addBodyParameter(Config.KEY_MY_ACCOUNT_NUM, my_account_num);
                        params.addBodyParameter(Config.KEY_OTHER_ACCOUNT_NUM, other_account_num);
                        httpUtils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params, new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {

                                adapter.remove(lvBlacklist.getItemAtPosition(position));
                                adapter.notifyDataSetChanged();
                                lvBlacklist.invalidate();
                                Toast.makeText(ShowBlacklistActivity.this, R.string.delete_blacklist_success,Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onFailure(HttpException e, String s) {
                                Toast.makeText(ShowBlacklistActivity.this, R.string.delete_blacklist_fail, Toast.LENGTH_SHORT).show();

                            }
                        });
                        return false;
                    }
                });
            }

            @Override
            public void onFailure(HttpException e, String s) {
                pd.dismiss();
                Toast.makeText(ShowBlacklistActivity.this, R.string.blacklist_fail, Toast.LENGTH_SHORT).show();
            }
        });
    }


//		// 从本地获取黑名单
//		 List<String> blacklist = EMContactManager.getInstance().getBlackListUsernames();
//
//		// 显示黑名单列表
//		if (blacklist != null) {
//			Collections.sort(blacklist);
//			adapter = new BlacklistAdapater(this, 1, blacklist);
//			listView.setAdapter(adapter);
//		}
//
//		// 注册上下文菜单
//		registerForContextMenu(listView);



//	@Override
//	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
//		super.onCreateContextMenu(menu, v, menuInfo);
//		getMenuInflater().inflate(R.menu.remove_from_blacklist, menu);
//	}
//
//	@Override
//	public boolean onContextItemSelected(MenuItem item) {
//		if (item.getItemId() == R.id.remove) {
//			final String tobeRemoveUser = adapter.getItem(((AdapterContextMenuInfo) item.getMenuInfo()).position);
//			// 把目标user移出黑名单
//			removeOutBlacklist(tobeRemoveUser);
//			return true;
//		}
//		return super.onContextItemSelected(item);
//	}
//
//	/**
//	 * 移出黑民单
//	 *
//	 * @param tobeRemoveUser
//	 */
//	void removeOutBlacklist(final String tobeRemoveUser) {
//	    final ProgressDialog pd = new ProgressDialog(this);
//	    pd.setMessage(getString(R.string.be_removing));
//	    pd.setCanceledOnTouchOutside(false);
//	    pd.show();
//	    new Thread(new Runnable() {
//            public void run() {
//                try {
//                    // 移出黑民单
//                    EMContactManager.getInstance().deleteUserFromBlackList(tobeRemoveUser);
//                    runOnUiThread(new Runnable() {
//                        public void run() {
//                            pd.dismiss();
//                            adapter.remove(tobeRemoveUser);
//                        }
//                    });
//                } catch (EaseMobException e) {
//                    e.printStackTrace();
//                    runOnUiThread(new Runnable() {
//                        public void run() {
//                            pd.dismiss();
//                            Toast.makeText(getApplicationContext(), R.string.Removed_from_the_failure, 0).show();
//                        }
//                    });
//                }
//            }
//        }).start();
//	}
//
//	/**
//	 * adapter
//	 *
//	 */
//	private class BlacklistAdapater extends ArrayAdapter<String> {
//
//		public BlacklistAdapater(Context context, int textViewResourceId, List<String> objects) {
//			super(context, textViewResourceId, objects);
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			if (convertView == null) {
//				convertView = View.inflate(getContext(), R.layout.row_contact, null);
//			}
//
//			TextView name = (TextView) convertView.findViewById(R.id.name);
//			name.setText(getItem(position));
//
//			return convertView;
//		}
//
//	}
//
//	/**
//	 * 返回
//	 *
//	 * @param view
//	 */
//	public void back(View view) {
//		finish();
//	}
}

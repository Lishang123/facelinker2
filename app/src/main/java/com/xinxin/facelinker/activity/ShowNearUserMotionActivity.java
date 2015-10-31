package com.xinxin.facelinker.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
import com.xinxin.facelinker.adapter.NearUserMotionAdapter;
import com.xinxin.facelinker.domain.ShowNearUserMotion;

import java.util.ArrayList;
import java.util.List;

public class ShowNearUserMotionActivity extends Activity {

    private ListView lvShowNearUserMotion;
    private ArrayAdapter adapter;
    private LocationManager locationManager;
    private Location location;
    private StringBuilder longitude;
    private StringBuilder latitude;
    private String my_account_num;
    private HttpUtils httpUtils = new HttpUtils();
    private List<ShowNearUserMotion> list = new ArrayList<ShowNearUserMotion>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_near_user_motion);

        lvShowNearUserMotion = (ListView) findViewById(R.id.lvShowNearUserMotion);
        my_account_num = getIntent().getStringExtra(Config.KEY_MY_ACCOUNT_NUM);
        adapter = new ArrayAdapter<NearUserMotionAdapter>(ShowNearUserMotionActivity.this, android.R.layout.simple_list_item_1);
        lvShowNearUserMotion.setAdapter(adapter);
        longitude = new StringBuilder();
        latitude = new StringBuilder();

        //获取地理位置
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

//            updateLocation(location);

        //System.out.println(location.getLatitude() + "hdahsdhasdhkasjd");

//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 8, new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                longitude.delete(0, 1);
//                latitude.delete(0, 1);
//
//                updateLocation(location);
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//
//            }
//        });
    }

    public void pointChanged(String lat, String lng) {

        latitude.delete(0, 1);
        longitude.delete(0, 1);
        latitude.append((Double.valueOf(lat) - 0.00555199));
        longitude.append((Double.valueOf(lng) - 0.006836000));
    }

//    public void updateLocation(Location location) {
//
//        longitude.append(location.getLongitude());
//        latitude.append(location.getLatitude());
//
//        pointChanged(latitude.toString(), longitude.toString());
//
//        final ProgressDialog pd = ProgressDialog.show(ShowNearUserMotionActivity.this, getResources().getString(R.string.show_near_user_motion_connecting), getResources().getString(R.string.show_near_user_motion_connecting_to_server));
//
//        lvShowNearUserMotion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                RequestParams params = new RequestParams();
//                params.addBodyParameter(Config.ACTION, Config.ACTION_SHOW_NEAR_USER_MOTION);
//                params.addBodyParameter(Config.KEY_MY_ACCOUNT_NUM, my_account_num);
//                params.addBodyParameter(Config.KEY_LATITUDE, latitude.toString());
//                params.addBodyParameter(Config.KEY_LONGTITUDE, longitude.toString());
//                httpUtils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params, new RequestCallBack<String>() {
//                    @Override
//                    public void onSuccess(ResponseInfo<String> responseInfo) {
//                        pd.dismiss();
//                        Gson gson = new Gson();
//                        list = gson.fromJson(responseInfo.result, new TypeToken<List<ShowNearUserMotion>>() {
//                        }.getType());
//                        for (int i = 0; i < list.size(); i++) {
//                            adapter.add(new NearUserMotionAdapter(list.get(i).getOther_account_num(), list.get(i).getNickname(), list.get(i).getMotion(), list.get(i).getDate()));
//                        }
//
//                        lvShowNearUserMotion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                                String motion_id = list.get(position).getMotion_id();
//                                Intent intent = new Intent(ShowNearUserMotionActivity.this, ShowMotionActivity.class);
//                                intent.putExtra(Config.KEY_MY_ACCOUNT_NUM, my_account_num);
//                                intent.putExtra(Config.KEY_MOTION_ID, motion_id);
//                                startActivity(intent);
//
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onFailure(HttpException e, String s) {
//                        pd.dismiss();
//                        Toast.makeText(ShowNearUserMotionActivity.this, R.string.show_near_user_motion_fail, Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//
//            }
//        });
//    }
}

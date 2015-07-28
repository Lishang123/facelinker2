package com.xinxin.facelinker.activity;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
import com.xinxin.facelinker.adapter.PalsAdapter;
import com.xinxin.facelinker.domain.ShowPalsMotion;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ShowPalsMotionActivityFragment extends Fragment {

    private String my_account_num;
    private ListView lvShowPalsMotion;
    private ArrayAdapter adapter;
    private ImageView ivPhoto;
    private Button btnShowNearUserMotion;
    private HttpUtils httpUtils = new HttpUtils();
    private List<ShowPalsMotion> list = new ArrayList<ShowPalsMotion>();
    private String comment;

    public ShowPalsMotionActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_show_pals_motion, container, false);

        my_account_num = getActivity().getIntent().getStringExtra(Config.KEY_MY_ACCOUNT_NUM);
        lvShowPalsMotion = (ListView) root.findViewById(R.id.lvShowPalsMotion);
        ivPhoto = (ImageView) root.findViewById(R.id.ivPhoto);
        btnShowNearUserMotion = (Button) root.findViewById(R.id.btnShowNearUserMotion);
        ivPhoto.setImageResource(R.drawable.photo);
        adapter = new ArrayAdapter<PalsAdapter>(getActivity(), android.R.layout.simple_list_item_1);
        lvShowPalsMotion.setAdapter(adapter);

        ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShowMyMotionActivity.class);
                intent.putExtra(Config.KEY_MY_ACCOUNT_NUM, my_account_num);
                startActivity(intent);
            }
        });

        btnShowNearUserMotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShowNearUserMotionActivity.class);
                intent.putExtra(Config.KEY_MY_ACCOUNT_NUM, my_account_num);
                startActivity(intent);
            }
        });

        final ProgressDialog pd = ProgressDialog.show(getActivity(), getResources().getString(R.string.show_pals_motion_connecting), getResources().getString(R.string.show_pals_motion_connecting_to_server));
//        new ShowPalsMotionNet(my_account_num, new ShowPalsMotionNet.SuccessCallback() {
        RequestParams params = new RequestParams();
        params.addBodyParameter(Config.ACTION, Config.ACTION_SHOW_PALS_MOTION);
        params.addBodyParameter(Config.KEY_MY_ACCOUNT_NUM, my_account_num);
        httpUtils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                pd.dismiss();
                Gson gson = new Gson();
                list = gson.fromJson(responseInfo.result, new TypeToken<List<ShowPalsMotion>>() {
                }.getType());
                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j < list.get(i).getComment().length; j++) {

                        comment = comment + list.get(i).getComment()[j] + '\n';
                    }
                    adapter.add(new PalsAdapter(list.get(i).getMotion(), list.get(i).getNickname(), comment));
                    adapter.notifyDataSetChanged();
                }

                lvShowPalsMotion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String motion_id = list.get(position).getMotion_id();
                        Intent intent = new Intent(getActivity(), ShowMotionActivity.class);
                        intent.putExtra(Config.KEY_MY_ACCOUNT_NUM, my_account_num);
                        intent.putExtra(Config.KEY_MOTION_ID, motion_id);
                        startActivity(intent);

                    }
                });
            }

            @Override
            public void onFailure(HttpException e, String s) {
                pd.dismiss();
                Toast.makeText(getActivity(), R.string.show_pals_motion_fail, Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}

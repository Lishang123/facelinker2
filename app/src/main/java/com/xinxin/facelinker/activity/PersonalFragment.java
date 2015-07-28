package com.xinxin.facelinker.activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chatuidemo.R;
import com.xinxin.facelinker.Constant;
import com.xinxin.facelinker.DemoApplication;

/**
 * 我的fragment
 */
public class PersonalFragment extends Fragment implements OnClickListener {

    /**
     * 头像
     */
    private ImageView iv_personal_head;
    /**
     * 个人信息界面
     */
    private LinearLayout ll_personal_head;

    /**
     * 好友数量
     */
    private TextView tv_pals_num;
    /**
     * 群组数量
     */
    private TextView tv_groups_num;
    /**
     * 黑名单数量
     */
    private TextView tv_blacklist_num;
    /**
     * 群组
     */
    private RelativeLayout rl_groups;
    /**
     * 黑名单
     */
    private RelativeLayout rl_blacklist;

    /**
     * 聊天设置
     */
    private LinearLayout ll_chatsettings;
    /**
     * 账号设置
     */
    private LinearLayout ll_accountsettings;
    /**
     * 其他设置，例如清除缓存等
     */
    private LinearLayout ll_othersettings;
    /**
     * 评分
     */
    private LinearLayout ll_rating;
    /**
     * 反馈
     */
    private LinearLayout ll_feedback;
    /**
     * 关于
     */
    private LinearLayout ll_about;

    private Button bt_personal_exit;

    public PersonalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_personal, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false))
            return;
        init();
        iv_personal_head.setOnClickListener(this);
        ll_personal_head.setOnClickListener(this);
        rl_groups.setOnClickListener(this);
        rl_blacklist.setOnClickListener(this);
        ll_chatsettings.setOnClickListener(this);
        ll_accountsettings.setOnClickListener(this);
        ll_othersettings.setOnClickListener(this);
        ll_rating.setOnClickListener(this);
        ll_feedback.setOnClickListener(this);
        ll_about.setOnClickListener(this);
        bt_personal_exit.setOnClickListener(this);
        if (!TextUtils.isEmpty(EMChatManager.getInstance().getCurrentUser())) {
            bt_personal_exit.setText(getString(R.string.button_logout) + "(" + EMChatManager.getInstance().getCurrentUser() + ")");
        }
        bt_personal_exit.setOnClickListener(this);
    }

    //初始化相关变量
    private void init() {
        iv_personal_head = (ImageView) getView().findViewById(R.id.iv_personal_head);
        ll_personal_head = (LinearLayout) getView().findViewById(R.id.ll_personal_head);
        rl_groups = (RelativeLayout) getView().findViewById(R.id.rl_groups);
        rl_blacklist = (RelativeLayout) getView().findViewById(R.id.rl_blacklist);
        ll_chatsettings = (LinearLayout) getView().findViewById(R.id.ll_chatsettings);
        ll_accountsettings = (LinearLayout) getView().findViewById(R.id.ll_accountsettings);
        ll_othersettings = (LinearLayout) getView().findViewById(R.id.ll_othersettings);
        ll_rating = (LinearLayout) getView().findViewById(R.id.ll_rating);
        ll_feedback = (LinearLayout) getView().findViewById(R.id.ll_feedback);
        ll_about = (LinearLayout) getView().findViewById(R.id.ll_about);
        tv_pals_num = (TextView) getView().findViewById(R.id.tv_pals_num);
        tv_groups_num = (TextView) getView().findViewById(R.id.tv_groups_num);
        tv_blacklist_num = (TextView) getView().findViewById(R.id.tv_blacklist_num);
//        初始化登陆按钮并设置其显示名字
        bt_personal_exit = (Button) getView().findViewById(R.id.bt_personal_exit);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_personal_head:
                changeAvater();
                break;
            case R.id.ll_personal_head:
                ChangePersonInfo();
                break;
            case R.id.rv_pals:
                //                startActivity(new Intent(getActivity(),));
                break;
            case R.id.rl_groups:
                //                startActivity(new Intent(getActivity(),));
                break;
            case R.id.rl_blacklist:
//                startActivity(new Intent(getActivity(),));
                break;
            case R.id.ll_chatsettings:
                startActivity(new Intent(getActivity(), SettingChatActivity.class));
                break;
            case R.id.ll_accountsettings:
                startActivity(new Intent(getActivity(), SettingAccountActivity.class));
                break;
            case R.id.ll_othersettings:
                startActivity(new Intent(getActivity(), SettingOthersActivity.class));
                break;
            case R.id.ll_rating:
                rating(getActivity().getApplicationContext());
                break;
            case R.id.ll_feedback:
                startActivity(new Intent(getActivity(), FeedbackActivity.class));
                break;
            case R.id.ll_about:
                startActivity(new Intent(getActivity(), AboutFaceLinkerActivity.class));
                break;
            case R.id.bt_personal_exit:
                logout();
                break;

        }
    }

    private void ChangePersonInfo() {

    }

    private void changeAvater() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (((MainActivity) getActivity()).isConflict) {
            outState.putBoolean("isConflict", true);
        } else if (((MainActivity) getActivity()).getCurrentAccountRemoved()) {
            outState.putBoolean(Constant.ACCOUNT_REMOVED, true);
        }
    }

    /**
     * 获取手机中所有的应用商店
     *
     * @param context
     */
    private void rating(Context context) {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    private void share(Context context) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setType("text/*");
        sendIntent.putExtra(Intent.EXTRA_TEXT, "233333");
        startActivity(sendIntent);
    }

    void logout() {
        final ProgressDialog pd = new ProgressDialog(getActivity());
        String st = getResources().getString(R.string.Are_logged_out);
        pd.setMessage(st);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        DemoApplication.getInstance().logout(new EMCallBack() {

            @Override
            public void onSuccess() {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        pd.dismiss();
                        // 重新显示登陆页面
                        ((MainActivity) getActivity()).finish();
                        startActivity(new Intent(getActivity(), LoginActivity.class));

                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {

            }
        });
    }

}

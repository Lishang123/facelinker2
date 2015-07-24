package com.xinxin.facelinker.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ToggleButton;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMChatOptions;
import com.easemob.chat.EMGroup;
import com.easemob.chatuidemo.R;
import com.xinxin.applib.controller.HXSDKHelper;

import java.util.ArrayList;
import java.util.List;

public class SettingChatActivity extends Activity {
    private static final int REQUEST_CODE_SELECT_AUDIO = 10000;
    ToggleButton sbt_pushmessage_onoff;
    ToggleButton sbt_personalized_voice_onoff;
    ToggleButton sbt_chatvoice_onoff;
    ToggleButton sbt_chatshake_onoff;
    ToggleButton sbt_groupmsgremind_onoff;
    ToggleButton sbt_ignorenotview_onoff;
    Button right_title;
    Button left_title;
    Uri ringUri;
    private EMChatOptions chatOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting_chat);

        initialView();
        right_title.setVisibility(View.GONE);
        left_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initialView() {
        sbt_pushmessage_onoff = (ToggleButton) findViewById(R.id.sbt_pushmessage_onoff);
        sbt_personalized_voice_onoff = (ToggleButton) findViewById(R.id.sbt_personalized_voice_onoff);
        sbt_chatvoice_onoff = (ToggleButton) findViewById(R.id.sbt_chatvoice_onoff);
        sbt_chatshake_onoff = (ToggleButton) findViewById(R.id.sbt_chatshake_onoff);
        sbt_groupmsgremind_onoff = (ToggleButton) findViewById(R.id.sbt_groupmsgremind_onoff);
        sbt_ignorenotview_onoff = (ToggleButton) findViewById(R.id.sbt_ignorenotview_onoff);
        right_title = (Button) findViewById(R.id.right_title);
        left_title = (Button) findViewById(R.id.left_title);

       chatOptions = EMChatManager.getInstance().getChatOptions();
        ringUri = chatOptions.getNotifyRingUri();
        initalToggleButtons();
    }

    private void initalToggleButtons() {
        if (chatOptions.getNotificationEnable()) {
            sbt_pushmessage_onoff.setChecked(true);
        } else {
            sbt_pushmessage_onoff.setChecked(false);
        }
        if (chatOptions.getNotifyRingUri()!=null) {
            sbt_personalized_voice_onoff.setChecked(true);
        } else {
            sbt_personalized_voice_onoff.setChecked(false);
        }
        if (chatOptions.getNoticedBySound()) {
            sbt_chatvoice_onoff.setChecked(true);
        } else {
            sbt_chatvoice_onoff.setChecked(false);
        }
        if (chatOptions.getNoticedByVibrate()) {
            sbt_chatshake_onoff.setChecked(true);
        } else {
            sbt_chatshake_onoff.setChecked(false);
        }
        if (chatOptions.getReceiveNoNotifyGroup()!=null) {
            sbt_groupmsgremind_onoff.setChecked(true);
        } else {
            sbt_groupmsgremind_onoff.setChecked(false);
        }
        if (chatOptions.getUseSpeaker()) {
            sbt_ignorenotview_onoff.setChecked(true);
        } else {
            sbt_ignorenotview_onoff.setChecked(false);
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_push_message:
                if (sbt_pushmessage_onoff.isChecked()) {
                    sbt_pushmessage_onoff.setChecked(false);
                    chatOptions.setNotificationEnable(false);
                    EMChatManager.getInstance().setChatOptions(chatOptions);

                    HXSDKHelper.getInstance().getModel().setSettingMsgNotification(false);
                } else {
                    sbt_pushmessage_onoff.setChecked(true);
                    chatOptions.setNotificationEnable(true);
                    EMChatManager.getInstance().setChatOptions(chatOptions);
                    HXSDKHelper.getInstance().getModel().setSettingMsgNotification(true);
                }
                break;

            case R.id.ll_use_pro_tone:
                if (sbt_personalized_voice_onoff.isChecked()) {
                    sbt_personalized_voice_onoff.setChecked(false);

//                    选择音乐
                    selectRingTone();


                } else {
                    sbt_personalized_voice_onoff.setChecked(true);
                }
                break;

            case R.id.ll_person_withvoice:
                if (sbt_chatvoice_onoff.isChecked()) {
                    sbt_chatvoice_onoff.setChecked(false);
                    sbt_personalized_voice_onoff.setChecked(false);
                    chatOptions.setNoticeBySound(false);
                    EMChatManager.getInstance().setChatOptions(chatOptions);
                    HXSDKHelper.getInstance().getModel().setSettingMsgSound(false);
                } else {
                    sbt_chatvoice_onoff.setChecked(true);
                    chatOptions.setNoticeBySound(true);
                    EMChatManager.getInstance().setChatOptions(chatOptions);
                    HXSDKHelper.getInstance().getModel().setSettingMsgSound(true);
                }
                break;

            case R.id.ll_person_withvibration:
                if (sbt_chatshake_onoff.isChecked()) {
                    sbt_chatshake_onoff.setChecked(false);
                    chatOptions.setNoticedByVibrate(false);
                    EMChatManager.getInstance().setChatOptions(chatOptions);
                    HXSDKHelper.getInstance().getModel().setSettingMsgVibrate(false);
                } else {
                    sbt_chatshake_onoff.setChecked(true);
                    chatOptions.setNoticedByVibrate(true);
                    EMChatManager.getInstance().setChatOptions(chatOptions);
                    HXSDKHelper.getInstance().getModel().setSettingMsgVibrate(true);
                }
                break;

            case R.id.ll_all_group_remind:
                if (sbt_groupmsgremind_onoff.isChecked()) {
                    sbt_groupmsgremind_onoff.setChecked(false);
                    chatOptions.setGroupsOfNotificationDisabled(null);
                    EMChatManager.getInstance().setChatOptions(chatOptions);

                } else {
                    sbt_groupmsgremind_onoff.setChecked(true);
                    List<EMGroup> allGroups = EMChatManager.getInstance().getAllGroups();
                    List<String> grougIds = new ArrayList<String>();
                    for (EMGroup group : allGroups) {
                        grougIds.add(group.getGroupId());
                    }
                    chatOptions.setGroupsOfNotificationDisabled(grougIds);
                    EMChatManager.getInstance().setChatOptions(chatOptions);
                }
                break;

            case R.id.ll_use_peaker:
                if (sbt_ignorenotview_onoff.isChecked()) {
                    sbt_ignorenotview_onoff.setChecked(false);
                    chatOptions.setUseSpeaker(false);
                    EMChatManager.getInstance().setChatOptions(chatOptions);
                    HXSDKHelper.getInstance().getModel().setSettingMsgSpeaker(false);
                } else {
                    sbt_ignorenotview_onoff.setChecked(true);
                    chatOptions.setUseSpeaker(true);
                    EMChatManager.getInstance().setChatOptions(chatOptions);
                    HXSDKHelper.getInstance().getModel().setSettingMsgVibrate(true);
                }
                break;


        }

    }


    public void selectRingTone() {
        Intent intent = null;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);

        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, REQUEST_CODE_SELECT_AUDIO);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == REQUEST_CODE_SELECT_AUDIO) {
            if (data != null) {
                ringUri = data.getData();
                chatOptions.setNotifyRingUri(ringUri);
                EMChatManager.getInstance().setChatOptions(chatOptions);
            }

        }
    }
}

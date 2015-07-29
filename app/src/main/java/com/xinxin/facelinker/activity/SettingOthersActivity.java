package com.xinxin.facelinker.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.easemob.chatuidemo.R;

public class SettingOthersActivity extends Activity {
    View left_title;
    LinearLayout ll_clear_recent_chat_list;
    LinearLayout ll_clear_chat_log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_others);
        left_title=findViewById(R.id.left_title);
        ll_clear_recent_chat_list= (LinearLayout) findViewById(R.id.ll_clear_recent_chat_list);
        ll_clear_chat_log= (LinearLayout) findViewById(R.id.ll_clear_chat_log);
        left_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }


}

/*
 * User: xinxin
 * Date:
 * Describe:反馈
 *
 */

package com.xinxin.facelinker.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.easemob.chatuidemo.R;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xinxin.facelinker.Config;
import com.xinxin.facelinker.domain.Feedback;

import java.util.Date;

public class FeedbackActivity extends Activity {
    EditText help_feedback = null;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);


        Button but_help_feedback = (Button) findViewById(R.id.but_help_feedback);
        help_feedback = (EditText) findViewById(R.id.help_feedback);


        but_help_feedback.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (TextUtils.isEmpty(help_feedback.getText())) {
                    Toast.makeText(FeedbackActivity.this, getString(R.string.feedback_text_empty), Toast.LENGTH_SHORT).show();
                } else {
                    String context = help_feedback.getText().toString();
                    HttpUtils httpUtils = new HttpUtils();
                    RequestParams params = new RequestParams();
                    params.addBodyParameter(Config.ACTION,"Feedback");

                    Feedback feedback = new Feedback();
                    feedback.setUserId(getApplication().getPackageName());
                    Date date=new Date();
                    feedback.setTimeDate(date);
                    feedback.setFeedMessage(context);
                    String content = new Gson().toJson(feedback);
                    params.addBodyParameter(Config.CONTENT, "content");
                    httpUtils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, new RequestCallBack<String>() {
                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {

                        }

                        @Override
                        public void onFailure(HttpException e, String s) {

                        }
                    });


                }
            }
        });
    }


/*                try {

                    Boolean flags = UserDataServiceHelper.SendFeedBack(new UserDataReadHelper(Help.this).GetUserNiceName(), Context);

                    Toast.makeText(Help.this, "��л���ķ���,���ǻᾡ�촦�����������", Toast.LENGTH_SHORT).show();
                    ViewUtility.NavigateActivate((Activity) Help.this, Main.class);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                }
            }
        });*/


}
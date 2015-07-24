package com.xinxin.facelinker.activity;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.easemob.chatuidemo.R;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.xinxin.facelinker.Config;
import com.xinxin.facelinker.DemoHXSDKHelper;
import com.xinxin.facelinker.domain.Version;
import com.xinxin.facelinker.utils.CommonUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.xinxin.facelinker.utils.NetHelper.getDataFromServer;
import static com.xinxin.facelinker.utils.NetHelper.parseJsonData;

/**
 * 闪屏页
 */
public class SplashActivity extends BaseActivity {
    private static final int REQUEST_CODE_UPDATE = 2;

    private RelativeLayout rootLayout;
    private TextView versionText;
    long m_newVerCode; //最新版的版本号
    //    String m_newVerName; //最新版的版本名
    String m_appNameStr; //下载到本地要给这个APP命的名字
    private static final int sleepTime = 2000;

    Handler m_mainHandler;
    ProgressDialog m_progressDlg;

    @Override
    protected void onCreate(Bundle arg0) {
        setContentView(R.layout.activity_splash);
        super.onCreate(arg0);

        rootLayout = (RelativeLayout) findViewById(R.id.splash_root);
        versionText = (TextView) findViewById(R.id.tv_version);

        versionText.setText(CommonUtils.getVersionName(this));

        m_progressDlg =  new ProgressDialog(this);
        m_progressDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // 设置ProgressDialog 的进度条是否不明确 false 就是不设置为不明确
        m_progressDlg.setIndeterminate(false);

        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(1500);
        rootLayout.startAnimation(animation);
        new checkNewestVersionAsyncTask().execute();
    }



//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == REQUEST_CODE_UPDATE) {
//
//            System.out.println("更新");
//        }
//    }

    class checkNewestVersionAsyncTask extends AsyncTask<Void, Void, Boolean> {
        RequestParams requestParams = new RequestParams();
        boolean status = true;
        @Override
        protected Boolean doInBackground(Void... params) {
            requestParams.addBodyParameter(Config.ACTION, "check_update");
            getDataFromServer(requestParams, new RequestCallBack<String>() {

                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    String result = (String) responseInfo.result;
                    System.out.println(result);

                    Version version = (Version) parseJsonData(result, Version.class);
                    System.out.println(version.toString());
                    m_newVerCode = version.getVersion();
                    int vercode = Integer.parseInt(CommonUtils.getVersionCode(getApplicationContext()));
                    if (m_newVerCode > vercode) {
                        status = true;
                    } else {
                        status = false;
                    }
                }

                @Override
                public void onFailure(HttpException e, String s) {

                    System.out.println("网络连接失败");
                    status = false;
                }
            });

            return status;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            if (result) {//如果有最新版本
                doNewVersionUpdate(); // 更新新版本
            } else {
                enterMain(); // 进入主界面
            }
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

        }

        /**
         * 提示当前为最新版本
         */
        private void notNewVersionDlgShow() {
            int verCode = Integer.parseInt(CommonUtils.getVersionCode(getApplicationContext()));
            String verName = CommonUtils.getVersionName(getApplicationContext());
            String str = "当前版本:" + verName + " Code:" + verCode + ",/n已是最新版,无需更新!";
            Dialog dialog = new AlertDialog.Builder(SplashActivity.this).setTitle("软件更新")
                    .setMessage(str)// 设置内容
                    .setPositiveButton("确定",// 设置确定按钮
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    finish();
                                }
                            }).create();// 创建
            // 显示对话框
            dialog.show();
        }

        //        进入主界面
        private void enterMain() {
            new Thread(new Runnable() {
                public void run() {
                    if (DemoHXSDKHelper.getInstance().isLogined()) {
                        // ** 免登陆情况加载主页面有本地群和会话
                        //不是必须的，不加sdk也会自动异步去加载(不会重复加载)
                        //加上的话保证进了主页面会话和群组都已经load完毕
                        long start = System.currentTimeMillis();
                        EMGroupManager.getInstance().loadAllGroups();
                        EMChatManager.getInstance().loadAllConversations();
                        long costTime = System.currentTimeMillis() - start;
                        //等待sleeptime时长
                        if (sleepTime - costTime > 0) {
                            try {
                                Thread.sleep(sleepTime - costTime);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        //进入主页
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    } else {
                        try {
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {
                        }
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();
                    }
                }
            }).start();
        }

        private void doNewVersionUpdate() {
            int verCode = Integer.parseInt(CommonUtils.getVersionCode(getApplicationContext()));
            String verName = CommonUtils.getVersionName(getApplicationContext());

            String str = "当前版本：" + verName + " Code:" + verCode + " ,发现新版本：" +
                    " Code:" + m_newVerCode + " ,是否更新？";

            Dialog dialog = new AlertDialog.Builder(SplashActivity.this).setTitle("软件更新").setMessage(str)
                    // 设置内容
                    .setPositiveButton("更新",// 设置确定按钮
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    m_progressDlg.setTitle("正在下载");
                                    m_progressDlg.setMessage("请稍候...");
                                    downFile(Config.UPDATESOFTADDRESS);  //开始下载
                                }
                            })
                    .setNegativeButton("暂不更新",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    // 点击"取消"按钮之后退出程序
                                    enterMain();
//                                    finish();
                                }
                            }).create();// 创建
            // 显示对话框
            dialog.show();
        }

        private void downFile(final String url) {
            m_progressDlg.show();
            new Thread() {
                public void run() {
                    HttpClient client = new DefaultHttpClient();
                    HttpGet get = new HttpGet(url);
                    HttpResponse response;
                    try {
                        response = client.execute(get);
                        HttpEntity entity = response.getEntity();
                        long length = entity.getContentLength();

                        m_progressDlg.setMax((int) length);//设置进度条的最大值

                        InputStream is = entity.getContent();
                        FileOutputStream fileOutputStream = null;
                        if (is != null) {
                            File file = new File(
                                    Environment.getExternalStorageDirectory(),
                                    m_appNameStr);
                            fileOutputStream = new FileOutputStream(file);
                            byte[] buf = new byte[1024];
                            int ch = -1;
                            int count = 0;
                            while ((ch = is.read(buf)) != -1) {
                                fileOutputStream.write(buf, 0, ch);
                                count += ch;
                                if (length > 0) {
                                    m_progressDlg.setProgress(count);//设置当前进度
                                }
                            }
                        }
                        fileOutputStream.flush();
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        down();  //告诉HANDER已经下载完成了，可以安装了
                    } catch (ClientProtocolException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

        /**
         * 告诉HANDER已经下载完成了，可以安装了
         */
        private void down() {
            m_mainHandler.post(new Runnable() {
                public void run() {
                    m_progressDlg.cancel();
                    update();
                }
            });
        }

        /**
         * 安装程序
         */
        void update() {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(Environment
                            .getExternalStorageDirectory(), m_appNameStr)),
                    "application/vnd.android.package-archive");
            startActivity(intent);
        }
    }


}

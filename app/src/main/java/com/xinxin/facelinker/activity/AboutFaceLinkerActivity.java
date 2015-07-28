package com.xinxin.facelinker.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.easemob.chatuidemo.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xinxin.facelinker.Config;
import com.xinxin.facelinker.activity.util.SystemUiHider;
import com.xinxin.facelinker.domain.CreatorInfo;
import com.xinxin.facelinker.domain.UserProvision;
import com.xinxin.facelinker.domain.Version;
import com.xinxin.facelinker.utils.CommonUtils;
import com.xinxin.facelinker.utils.NetHelper;

import java.io.File;

import static com.xinxin.facelinker.utils.NetHelper.parseJsonData;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class AboutFaceLinkerActivity extends Activity {
    LinearLayout ll_version_update;
    LinearLayout ll_version_info;
    LinearLayout ll_provision;
    LinearLayout ll_composer_info;
    Handler m_mainHandler;
    Button left_title;
    ProgressDialog m_progressDlg;
    HttpUtils httpUtils = new HttpUtils();

    int m_newVerCode; //最新版的版本号
    String m_newVerName; //最新版的版本名
    String url = null;//下载的地址
    String m_appNameStr = "newversion"; //下载到本地要给这个APP命的名字


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_about_facelinker);
        ll_version_update = (LinearLayout) findViewById(R.id.ll_version_update);
        ll_version_info = (LinearLayout) findViewById(R.id.ll_version_info);
        ll_provision = (LinearLayout) findViewById(R.id.ll_provision);
        ll_composer_info = (LinearLayout) findViewById(R.id.ll_composer_info);

        left_title = (Button) findViewById(R.id.left_title);
        m_progressDlg = new ProgressDialog(this);
        m_progressDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // 设置ProgressDialog 的进度条是否不明确 false 就是不设置为不明确
        m_progressDlg.setIndeterminate(false);
        left_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_version_update:
                checkUpdate();
                break;
            case R.id.ll_version_info:
                getVsersionInfo();
                break;
            case R.id.ll_provision:
                getUserProvision();
                break;
            case R.id.ll_composer_info:
                getCreatorInfo();
                break;


        }


    }

    private void checkUpdate() {
        RequestParams params = new RequestParams();
        params.addBodyParameter(Config.ACTION, "check_update2");
        httpUtils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = (String) responseInfo.result;
                Version version = (Version) parseJsonData(result, Version.class);
                m_newVerCode = version.getVersion();
                m_newVerName = version.getVersionName();
                url = version.getUrl();
                int vercode = CommonUtils.getVersionCode(getApplicationContext());
                System.out.println("+++++++++++++" + vercode);
                if (version.getVersion() > vercode) {
                    System.out.println("执行版本更新");
                    doNewVersionUpdate(); // 更新新版本
                } else {
                    notNewVersionDlgShow();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(getApplicationContext(), getString(R.string.check_update_fail), Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 提示当前为最新版本
     */
    private void notNewVersionDlgShow() {
        int verCode = CommonUtils.getVersionCode(getApplicationContext());
        String verName = CommonUtils.getVersionName(getApplicationContext());
        String str = "当前版本:" + verName + " Code:" + verCode + ",/n已是最新版,无需更新!";
        Dialog dialog = new AlertDialog.Builder(AboutFaceLinkerActivity.this).setTitle("软件更新")
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

    private void doNewVersionUpdate() {
        int verCode = CommonUtils.getVersionCode(getApplicationContext());
        String verName = CommonUtils.getVersionName(getApplicationContext());

        String str = "当前版本：" + verName + " Code:" + verCode + " ,发现新版本：" +
                " Code:" + m_newVerCode + " ,是否更新？";

        Dialog dialog = new AlertDialog.Builder(AboutFaceLinkerActivity.this).setTitle("软件更新").setMessage(str)
                // 设置内容
                .setPositiveButton("更新",// 设置确定按钮
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                m_progressDlg.setTitle("正在下载");
                                m_progressDlg.setMessage("请稍候...");
                                downFile(url);  //开始下载
                            }
                        })
                .setNegativeButton("暂不更新",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {

                            }
                        }).create();// 创建
        // 显示对话框
        dialog.show();
    }

    private void downFile(final String url) {

        new Thread() {
            public void run() {
                File targetFile = new File(Environment.getExternalStorageDirectory() + m_appNameStr);
                HttpUtils httpUtils = new HttpUtils();
                HttpHandler httpHandler = httpUtils.download(url, targetFile.getAbsolutePath(), true, false, new RequestCallBack<File>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        m_progressDlg.show();
                    }

                    @Override
                    public void onSuccess(ResponseInfo<File> responseInfo) {

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {

                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        super.onLoading(total, current, isUploading);
                        m_progressDlg.setMax((int) total);//设置进度条的最大值
                        m_progressDlg.setProgress((int) current);//设置当前进度
                        if (total == current) {
                            down();
                        }
                    }

                });
            }
//               {
//                    HttpClient client = new DefaultHttpClient();
//                    HttpGet get = new HttpGet(url);
//                    HttpResponse response;
//                    try {
//                        response = client.execute(get);
//                        HttpEntity entity = response.getEntity();
//                        long length = entity.getContentLength();
//
//                        m_progressDlg.setMax((int) length);//设置进度条的最大值
//
//                        InputStream is = entity.getContent();
//                        FileOutputStream fileOutputStream = null;
//                        if (is != null) {
//                            File file = new File(
//                                    Environment.getExternalStorageDirectory(),
//                                    m_appNameStr);
//                            fileOutputStream = new FileOutputStream(file);
//                            byte[] buf = new byte[1024];
//                            int ch = -1;
//                            int count = 0;
//                            while ((ch = is.read(buf)) != -1) {
//                                fileOutputStream.write(buf, 0, ch);
//                                count += ch;
//                                if (length > 0) {
//                                    m_progressDlg.setProgress(count);//设置当前进度
//                                }
//                            }
//                        }
//                        fileOutputStream.flush();
//                        if (fileOutputStream != null) {
//                            fileOutputStream.close();
//                        }
//                        down();  //告诉HANDER已经下载完成了，可以安装了
//                    } catch (ClientProtocolException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
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


    private void getCreatorInfo() {
        RequestParams params = new RequestParams();
        params.addBodyParameter(Config.ACTION, "getCreatorInfo");
        httpUtils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                CreatorInfo creatorInfo = (CreatorInfo) NetHelper.parseJsonData(responseInfo.result, CreatorInfo.class);
                Dialog dialog = new AlertDialog.Builder(AboutFaceLinkerActivity.this).setTitle("作者信息").setMessage(creatorInfo.toString()).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();
                dialog.show();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(getApplicationContext(), "获取作者信息失败", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getUserProvision() {
        RequestParams params = new RequestParams();
        params.addBodyParameter(Config.ACTION, "getUserProvision");
        httpUtils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                UserProvision userProvision =
                        (UserProvision) NetHelper.parseJsonData(responseInfo.result, UserProvision.class);
                Dialog dialog = new AlertDialog.Builder(AboutFaceLinkerActivity.this).setTitle("用户条款").setMessage(userProvision.toString()).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();
                dialog.show();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(getApplicationContext(), "获取用户条款失败", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getVsersionInfo() {
        RequestParams params = new RequestParams();
        params.addBodyParameter(Config.ACTION, "check_update2");
        httpUtils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Version version =
                        (Version) NetHelper.parseJsonData(responseInfo.result, Version.class);
                Dialog dialog = new AlertDialog.Builder(AboutFaceLinkerActivity.this).setTitle("用户条款").setMessage(version.toString()).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();
                dialog.show();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(getApplicationContext(), "获取用户条款失败", Toast.LENGTH_LONG).show();
            }
        });
    }


}

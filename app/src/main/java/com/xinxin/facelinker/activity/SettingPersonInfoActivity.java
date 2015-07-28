
package com.xinxin.facelinker.activity;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.method.NumberKeyListener;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.chatuidemo.R;
import com.easemob.util.DeviceUuidFactory;
import com.easemob.util.PathUtil;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinxin.facelinker.Config;
import com.xinxin.facelinker.DemoApplication;
import com.xinxin.facelinker.domain.FLUser;
import com.xinxin.facelinker.domain.User;
import com.xinxin.facelinker.utils.CommonUtils;
import com.xinxin.facelinker.utils.ImageOptions;
import com.xinxin.facelinker.utils.NetHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 设置界面
 *
 * @author Administrator
 */
public class SettingPersonInfoActivity extends Activity implements OnClickListener {
    DbUtils utils;
    FLUser user;
    /*	private String  = "shared_key_setting_user_pic";
        private String  = "shared_key_setting_user_sex";
        private String  = "shared_key_setting_user_age";
        private String  = "shared_key_setting_user_area";
        private String  = "shared_key_setting_user_zaina";*/
    private static String LOG = "log";
    /**********************************新增用户信息*****************************************/
    /**
     * 设置用户头像
     */
    private RelativeLayout rl_user_pic;
    /**
     * 显示用户账号
     */
    private RelativeLayout rl_user_zhanghao;

    /**
     * 设置用户昵称
     */
    private RelativeLayout rl_user_nicheng;
    /**
     * 设置用户性别
     */
    private RelativeLayout rl_user_xingbie;
    /**
     * 设置用户年龄
     */
    private RelativeLayout rl_user_nianling;
    /**
     * 设置用户城市
     */
    private RelativeLayout rl_user_chengshi;
    /**
     * 设置用户职业
     */
    private RelativeLayout rl_user_job;
    /**
     * 设置用户签名
     */
    private RelativeLayout rl_user_qianming;
    /**
     * 设置用户在哪的动态
     */
    private RelativeLayout rl_user_zainadongtai;

    /**
     * 用户头像imageView
     */
    private ImageView iv_user_photo;

    /**
     * 用户昵称
     */
    private TextView tv_user_nicheng;
    /**
     * 用户账号，只能显示，不能更改
     */
    private TextView tv_user_zhanghao;
    /**
     * 用户性别
     */
    private TextView tv_user_xingbie;
    /**
     * 用户年龄
     */
    private TextView tv_user_nianling;
    /**
     * 用户地区
     */
    private TextView tv_user_chengshi;
    /**
     * 用户职业
     */
    private TextView tv_user_job;
    /**
     * 用户签名
     */
    private TextView tv_user_qianming;


    private String UserPic = null;
    private String UserNickName = null;
    private String UserAccount = null;
    private String UserSex = null;
    private String UserAge = null;
    private String UserArea = null;
    private String UserZaina = null;
    private String Userjob = null;
    private String UserQianming = null;


    /**
     * **********
     * 新加逻辑变量
     */
    private File cameraFile;//照相机拍照的图片
    //	private File cutFile;//剪切后的图片
    //头像uri
    private static String IMAGE_FILE_LOCATION = null;
    private Uri imageUri = null;
    private static final String IMAGE_FILE_LOCATION_TEAST = "file:///sdcard/lehu/temp.jpg";//temp file
    Uri imageUritest = Uri.parse(IMAGE_FILE_LOCATION_TEAST);//The Uri to store the big bitmap
    //private Uri imageUri = null;//Uri.parse(IMAGE_FILE_LOCATION);//The Uri to store the big bitmap
    public static final int USERPIC_REQUEST_CODE_LOCAL = 101;
    public static final int USERPIC_REQUEST_CODE_LOCAL_19 = 101;
    public static final int USERPIC_REQUEST_CODE_CAMERA = 102;
    public static final int USERPIC_REQUEST_CODE_CUT = 103;

    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_person_info_settings);
        DeviceUuidFactory uuid = new DeviceUuidFactory(this);
        pd = new ProgressDialog(this);
        pd.setMessage("正在提交请求...");
        init();

        //		联网更新用户数据
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addBodyParameter(Config.ACTION, "getUserInfo");
        httpUtils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.d(LOG, "setting_qes" + responseInfo.result);
                pd.dismiss();
                FLUser user = (FLUser) NetHelper.parseJsonData(responseInfo.result, FLUser.class);
//				将取得的信息存储在本地数据库
                utils = DbUtils.create(getApplicationContext(), Environment.getExternalStorageDirectory() + "/", "Fluser.db");
                try {
                    utils.update(user, WhereBuilder.b("account", "=", user.getAccount()));
                } catch (DbException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(HttpException e, String s) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "网络请求失败,请检查网络！", Toast.LENGTH_SHORT).show();
            }
        });

        //联网完毕，从本地数据库获取用户信息
        utils = DbUtils.create(getApplicationContext(), Environment.getExternalStorageDirectory() + "/", "Fluser.db");
        try {
            user = utils.findFirst(Selector.from(User.class));
        } catch (DbException e) {
            e.printStackTrace();
        }
        UserPic = user.getPhotourl();
        UserNickName = user.getName();
        UserAccount = user.getAccount();
        UserSex = user.getGender();
        UserAge = user.getAge();
        UserArea = user.getLocation();
        Userjob = user.getJob();
        UserQianming = user.getMood();


        //设置用户信息
        //iv_user_photo
        ImageLoader.getInstance().displayImage(UserPic, iv_user_photo, ImageOptions.getOptions());
        tv_user_nicheng.setText(UserNickName);
        tv_user_zhanghao.setText(UserAccount);
        tv_user_xingbie.setText(UserSex);
        tv_user_nianling.setText(UserAge);
        tv_user_chengshi.setText(UserArea);
        tv_user_job.setText(Userjob);
        tv_user_qianming.setText(UserQianming);
//绑定事件
        rl_user_pic.setOnClickListener(this);
        rl_user_nicheng.setOnClickListener(this);
        rl_user_xingbie.setOnClickListener(this);
        rl_user_nianling.setOnClickListener(this);
        rl_user_chengshi.setOnClickListener(this);
        rl_user_zainadongtai.setOnClickListener(this);
        rl_user_job.setOnClickListener(this);
        rl_user_qianming.setOnClickListener(this);


    }

    /**
     * 将activity与xml关联
     */
    private void init() {
        /***************用户信息设置**************/
        rl_user_pic = (RelativeLayout) findViewById(R.id.rl_user_pic);
        rl_user_nicheng = (RelativeLayout) findViewById(R.id.rl_user_nicheng);
        rl_user_xingbie = (RelativeLayout) findViewById(R.id.rl_user_xingbie);
        rl_user_nianling = (RelativeLayout) findViewById(R.id.rl_user_nianling);
        rl_user_chengshi = (RelativeLayout) findViewById(R.id.rl_user_chengshi);
        rl_user_qianming = (RelativeLayout) findViewById(R.id.rl_user_qianming);

        //实例化用户信息组件
        iv_user_photo = (ImageView) findViewById(R.id.iv_user_photo);
        tv_user_nicheng = (TextView) findViewById(R.id.tv_user_nicheng);
        tv_user_zhanghao = (TextView) findViewById(R.id.tv_user_zhanghao);
        tv_user_xingbie = (TextView) findViewById(R.id.tv_user_xingbie);
        tv_user_nianling = (TextView) findViewById(R.id.tv_user_nianling);
        tv_user_chengshi = (TextView) findViewById(R.id.tv_user_chengshi);
        tv_user_job = (TextView) findViewById(R.id.tv_user_job);
        tv_user_qianming = (TextView) findViewById(R.id.tv_user_qianming);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_user_pic:
                //startActivity(new Intent(getActivity(), BlacklistActivity.class));
                //更改头像图片
                change_headurl();
                break;
            case R.id.rl_user_nicheng:
                //startActivity(new Intent(getActivity(), BlacklistActivity.class));
                //修改昵称
                change_nickname("" + tv_user_nicheng.getText().toString().trim());
                break;
            case R.id.rl_user_xingbie:
                //修改性别
                change_sex();
                break;
            case R.id.rl_user_nianling:
                //修改年龄
                change_age("" + tv_user_nianling.getText().toString().trim());
            case R.id.rl_user_chengshi:
                //修改城市
                break;
            case R.id.rl_user_job:
                //修改职业
                change_job();
                break;
            case R.id.rl_user_qianming:
                //修改心情
                change_qianming("" + tv_user_qianming.getText().toString().trim());
                break;

        }

    }

    /**
     * 更改职业
     */
    private void change_job() {
        // TODO Auto-generated method stub
        Builder builder = new Builder(getApplicationContext());
        String[] strarr = {"计算机/互联网/通信", "生产/工艺/制造", "商业/服务业/个体经营"
                , "金融/银行/投资/保险", "文化/广告/传媒", "娱乐/艺术/表演", "医疗/护理/制药"
                , "律师/法务", "教育/培训", "公务员/事业单位", "学生", "无"};
        builder.setItems(strarr, new DialogInterface.OnClickListener() {
            String job = "无";

            public void onClick(DialogInterface arg0, int arg1) {
                switch (arg1) {
                    case 0:
                        job = "计算机/互联网/通信";
                        break;
                    case 1:
                        job = "生产/工艺/制造";
                        break;
                    case 2:
                        job = "商业/服务业/个体经营";
                        break;
                    case 3:
                        job = "金融/银行/投资/保险";
                        break;
                    case 4:
                        job = "文化/广告/传媒";
                        break;
                    case 5:
                        job = "娱乐/艺术/表演";
                        break;
                    case 6:
                        job = "医疗/护理/制药";
                        break;
                    case 7:
                        job = "律师/法务";
                        break;
                    case 8:
                        job = "教育/培训";
                        break;
                    case 9:
                        job = "公务员/事业单位";
                        break;
                    case 10:
                        job = "学生";
                        break;
                    default:
                        job = "无";
                        break;
                }
                user.setJob(job);
                RequestParams params = new RequestParams();
                params.addBodyParameter(Config.ACTION_CHANGE_FLUSER_INFO, NetHelper.ObjectToJson(user));
                NetHelper.putDataToServer(params, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.d(LOG, "更改职业成功");
                        tv_user_job.setText(job);
                        try {
//                            将信息保存到本地
                            utils.update(user, WhereBuilder.b("account", "=", user.getAccount()));
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Toast.makeText(getApplicationContext(), "联网失败", Toast.LENGTH_SHORT).show();
                    }
                });
                pd.show();
            }
        });
        builder.show();
    }

    /**
     * 更改签名
     */
    private void change_qianming(String qianming) {
        // TODO Auto-generated method stub
        final EditText texta = new EditText(getApplicationContext());
        texta.setText(qianming);
        new Builder(getApplicationContext())
                .setTitle("请输入您的签名")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(texta)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        final String mood = texta.getEditableText().toString();
                        user.setMood(mood);
                        RequestParams params = new RequestParams();
                        params.addBodyParameter(Config.ACTION_CHANGE_FLUSER_INFO, NetHelper.ObjectToJson(user));
                        NetHelper.putDataToServer(params, new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                Log.d(LOG, "更改心情成功");
                                tv_user_qianming.setText(mood);
                                try {
//                            将信息保存到本地
                                    utils.update(user, WhereBuilder.b("account", "=", user.getAccount()));
                                } catch (DbException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(HttpException e, String s) {
                                Toast.makeText(getApplicationContext(), "联网失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                        pd.show();
                    }
                }).setNegativeButton("取消", null).show();
    }

    /**
     * 更改年龄
     */
    public void change_age(String age) {

        final EditText texta = new EditText(getApplicationContext());
        texta.setText(age);
        //设置EditText输入类型
        texta.setKeyListener(new NumberKeyListener() {
            public int getInputType() {
                return InputType.TYPE_CLASS_PHONE;
            }

            @Override
            protected char[] getAcceptedChars() {
                // TODO Auto-generated method stub
                char[] numbers = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
                return numbers;
            }
        });
        new Builder(getApplicationContext())
                .setTitle("请输入您的年龄")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(texta)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        final String age = texta.getEditableText().toString();
                        user.setAge(age);
                        RequestParams params = new RequestParams();

                        params.addBodyParameter(Config.ACTION_CHANGE_FLUSER_INFO, NetHelper.ObjectToJson(user));
                        NetHelper.putDataToServer(params, new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                Log.d(LOG, "更改年龄成功");
                                tv_user_nianling.setText(age);
                                try {
//                            将信息保存到本地
                                    utils.update(user, WhereBuilder.b("account", "=", user.getAccount()));
                                } catch (DbException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(HttpException e, String s) {
                                Toast.makeText(getApplicationContext(), "联网失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                        pd.show();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    /**
     * 更改昵称
     */
    public void change_nickname(String nickname) {

        final EditText texta = new EditText(getApplicationContext());
        texta.setText(nickname);
        new Builder(getApplicationContext())
                .setTitle("请输入您的昵称")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(texta)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        final String name = texta.getEditableText().toString();
                        user.setName(name);
                        RequestParams params = new RequestParams();
                        params.addBodyParameter(Config.ACTION_CHANGE_FLUSER_INFO, NetHelper.ObjectToJson(user));
                        new HttpUtils().send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                Log.d(LOG, "更改昵称成功");
                                tv_user_nicheng.setText(name);
                                try {
//                            将信息保存到本地
                                    utils.update(user, WhereBuilder.b("account", "=", user.getAccount()));
                                } catch (DbException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(HttpException e, String s) {
                                Toast.makeText(getApplicationContext(), "联网失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                        pd.show();
                        dialog.dismiss();
                        //设置你的操作事项  
                    }
                })
                .setNegativeButton("取消", null)
                .show();
        //return true; 
    }

    /**
     * 更改性别
     */
    public void change_sex() {

        Builder builder = new Builder(getApplicationContext());
        String[] strarr = {"男", "女"};
        builder.setItems(strarr, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                String sex = "2";
                // TODO 自动生成的方法存根 
                if (arg1 == 0) {//男
                    sex = "1";
                    user.setGender("男");
                } else {//女
                    sex = "2";
                    user.setGender("女");
                }
                RequestParams params = new RequestParams();
                params.addBodyParameter(Config.ACTION_CHANGE_FLUSER_INFO, NetHelper.ObjectToJson(user));
                new HttpUtils().send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.d(LOG, "更改性别成功");
                        tv_user_nicheng.setText(user.getGender());
                        try {
//                            将信息保存到本地
                            utils.update(user, WhereBuilder.b("account", "=", user.getAccount()));
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Toast.makeText(getApplicationContext(), "联网失败", Toast.LENGTH_SHORT).show();
                    }
                });
                pd.show();
            }
        });
        builder.show();
    }

    /**
     * 更改头像
     */
    public void change_headurl() {
        /*//如果挂在SDcard
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			//在SDcard文件TestSyncListView下创建文件
			IMAGE_FILE_LOCATION = Environment.getExternalStorageDirectory()+Constant.CACHE_DIR_IMAGE+"/temp.jpg";
			imageUri = Uri.parse(IMAGE_FILE_LOCATION);
			//File cutFile = new File(Constant.CACHE_DIR_IMAGE,"temp.jpg");
			//cutFile.getParentFile().mkdirs();
			File dir = new File(IMAGE_FILE_LOCATION);  
	        if (!dir.exists()) {  
	              try {  
	                  //在指定的文件夹中创建文件  
	                  dir.createNewFile();  
	            } catch (Exception e) {  
	            	//println(e);
	            }  
	        }  
	        
		}else{
			Toast.makeText(getActivity(), "SD卡不存在，不能更改头像", 0).show();
			return;
		}*/
        /*创建缓存图片文件-要用于照相和本地图片选择缓存*/
        if (!CommonUtils.isExitsSdcard()) {
            Toast.makeText(getApplicationContext(), "SD卡不存在，不能更改头像", Toast.LENGTH_SHORT).show();
            return;
        }
        cameraFile = new File(PathUtil.getInstance().getImagePath(), DemoApplication.getInstance().getUserName()
                + System.currentTimeMillis() + ".jpg");
        if (cameraFile == null && !cameraFile.exists()) {//如果文件存在就不在创建
            cameraFile.getParentFile().mkdirs();
        }

        Builder builder = new Builder(getApplicationContext());
        String[] strarr = {"选择拍照", "选择本地相册"};
        builder.setItems(strarr, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                if (arg1 == 0) {//选择拍照
                    selectPicFromCamera();
                } else {//选择本地相册
                    selectPicFromLocal();
                }
            }
        });
        builder.show();
    }

    /**
     * 从图库获取图片
     */
    public void selectPicFromLocal() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            //startActivityForResult(intent, USERPIC_REQUEST_CODE_LOCAL_19);
        }
        startActivityForResult(intent, USERPIC_REQUEST_CODE_LOCAL);
    }

    /**
     * 照相获取图片
     */
    public void selectPicFromCamera() {

//		cameraFile = new File(PathUtil.getInstance().getImagePath(), DemoApplication.getInstance().getUserName()

        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile)),
                USERPIC_REQUEST_CODE_CAMERA);
    }

    /**
     * onActivityResult
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == USERPIC_REQUEST_CODE_CAMERA) { // 获取照片
            if (cameraFile != null && cameraFile.exists()) {
                Log.d(LOG, "cameraFile" + cameraFile.getAbsolutePath());
                //改成返回到指定的uri imageUri = Uri.fromFile(cameraFile); 
                cropImageUri(Uri.fromFile(cameraFile), 300, 300, USERPIC_REQUEST_CODE_CUT);

            }
        } else if (requestCode == USERPIC_REQUEST_CODE_LOCAL) { // 获取本地图片 
            if (data != null) {
                Uri selectedImage = data.getData();
                if (selectedImage != null) {
                    cropImageUri(selectedImage, 300, 300, USERPIC_REQUEST_CODE_CUT);
                    //Log.d("log","selectedImage"+selectedImage);

                }
            }
        } else if (requestCode == USERPIC_REQUEST_CODE_CUT) {//裁剪图片
            // 从剪切图片返回的数据  
            if (data != null) {
                Bitmap bitmap = data.getParcelableExtra("data");
                iv_user_photo.setImageBitmap(bitmap);

                File file = saveJPGE_After(bitmap, cameraFile); //获取截取图片后的数据
                UserPic = file.getAbsolutePath();
                RequestParams params = new RequestParams();
                if (file.exists()) {
                    try {
                        user.setPhotourl(file.getAbsolutePath());
                        params.addBodyParameter(Config.ACTION_CHANGE_FLUSER_INFO, NetHelper.ObjectToJson(user));
                        params.addBodyParameter(user.getAccount() + "avatar", file);
                        new HttpUtils().send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                Log.d(LOG, "更改照片成功");
                                ImageLoader.getInstance().displayImage(UserPic, iv_user_photo, ImageOptions.getOptions());
                                try {
//                            将信息保存到本地
                                    utils.update(user, WhereBuilder.b("account", "=", user.getAccount()));
                                } catch (DbException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(HttpException e, String s) {
                                Toast.makeText(getApplicationContext(), "联网失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                        pd.show();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "无法获取图片，请检查SD卡是否存在", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        }
    }

    //uri 转bitmap
    private Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }


    /**
     * 根据图库图片uri获取图片
     *
     * @param selectedImage
     */
    private void sendPicByUri(Uri selectedImage) {
        // String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(selectedImage, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex("_data");
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            cursor = null;

            if (picturePath == null || picturePath.equals("null")) {
                Toast toast = Toast.makeText(getApplicationContext(), "找不到图片", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            //copyFile(picturePath,imageUri.getPath());
            cropImageUri(selectedImage, 200, 200, USERPIC_REQUEST_CODE_CUT);
            //sendPicture(picturePath);
        } else {
            File file = new File(selectedImage.getPath());
            if (!file.exists()) {
                Toast toast = Toast.makeText(getApplicationContext(), "找不到图片", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;

            }
            //copyFile(selectedImage.getPath(),imageUri.getPath()); 
            cropImageUri(selectedImage, 200, 200, USERPIC_REQUEST_CODE_CUT);
            //sendPicture(file.getAbsolutePath());
        }

    }

    /**
     * 根据图库图片uri获取图片
     *
     * @param selectedImage
     */
    private String getpathfromUri(Uri selectedImage) {
        // String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(selectedImage, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex("_data");
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            cursor = null;

            if (picturePath == null || picturePath.equals("null")) {
                Toast toast = Toast.makeText(getApplicationContext(), "找不到图片", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return null;
            }
            return picturePath;
            //sendPicture(picturePath);
        } else {
            File file = new File(selectedImage.getPath());
            if (!file.exists()) {
                Toast toast = Toast.makeText(getApplicationContext(), "找不到图片", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return null;

            }
            return file.getAbsolutePath();
        }

    }

    /**
     * 裁剪图片
     *
     * @param uri         图片uri
     * @param outputX     传出需要的宽
     * @param outputY     传出需要的高
     * @param requestCode 用于在activity间标识传输
     */
    private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode) {


        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, requestCode);
    }

    /**
     * 保存Bitmap为文件
     *
     * @param baseBitmap
     */
    public void save(Bitmap baseBitmap) {
        try { 
              /*			  BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(cutFile));
			   baseBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
	           bos.flush();
	           bos.close();*/
            OutputStream stream = new FileOutputStream(CommonUtils.Uritofile(getApplicationContext(), imageUri));
            baseBitmap.compress(CompressFormat.JPEG, 100, stream);
            stream.close();
		  /*
		   // 模拟一个广播，通知系统sdcard被挂载
		   Intent intent = new Intent();
		   intent.setAction(Intent.ACTION_MEDIA_MOUNTED);
		   intent.setData(Uri.fromFile(Environment
		     .getExternalStorageDirectory()));
		   sendBroadcast(intent);*/
            Toast.makeText(getApplicationContext(), "保存图片成功", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "保存图片失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /**
     * bitmap 转 file
     * 保存图片为JPEG
     *
     * @param bitmap
     * @param cameraFile2
     */
    public File saveJPGE_After(Bitmap bitmap, File cameraFile2) {
        //File file = new File(cameraFile2);  
        try {
            FileOutputStream out = new FileOutputStream(cameraFile2);
            if (bitmap.compress(CompressFormat.JPEG, 100, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cameraFile2;
    }


}

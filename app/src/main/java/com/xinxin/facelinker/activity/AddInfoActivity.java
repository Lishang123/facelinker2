package com.xinxin.facelinker.activity;

import android.animation.AnimatorSet;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.easemob.chatuidemo.R;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinxin.facelinker.Config;
import com.xinxin.facelinker.domain.FLUser;

import org.jivesoftware.smackx.packet.StreamInitiation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class AddInfoActivity extends Activity {

    private EditText etEmail;
    private EditText etNickname;
    private EditText etGender;
    private EditText etBirthday;
    private Button btnOk;
    private ImageView ivPhoto;
    private String photo;
    private String phone_num;
    private EditText etJob;
    HttpUtils httpUtils = new HttpUtils();
    final int LIST_DIALOG=1;
    private static int CAMERA_REQUEST_CODE = 1;
    private static int GALLERY_REQUEST_CODE = 2;
    private static int CROP_REQUEST_CODE = 3;
    private String[] array=new String[]{"摄像头", "图库"};
    private int selectNum=0;
    private String ImageName=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etNickname = (EditText) findViewById(R.id.etNickname);
        etGender = (EditText) findViewById(R.id.etGender);
        etBirthday = (EditText) findViewById(R.id.etBirthday);
        btnOk = (Button) findViewById(R.id.btnOk);
        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        phone_num = getIntent().getStringExtra(Config.KEY_PHONE_NUM);
        etJob = (EditText) findViewById(R.id.etJob);
        ivPhoto.setImageResource(R.drawable.photo);

        //头像设置
        ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(LIST_DIALOG);
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (etBirthday.getText().toString().isEmpty() == true || etGender.getText().toString().isEmpty() == true || etEmail.getText().toString().isEmpty() == true || etNickname.getText().toString().isEmpty() == true) {
                    Toast.makeText(AddInfoActivity.this, R.string.information_is_not_enough, Toast.LENGTH_SHORT).show();
                    return;
                }

                final ProgressDialog pd = ProgressDialog.show(AddInfoActivity.this, getResources().getString(R.string.information_connecting), getResources().getString(R.string.information_connecting_to_server));
                RequestParams params = new RequestParams();
                params.addBodyParameter(Config.ACTION, Config.ACTION_ADD_INFO);
                params.addBodyParameter(Config.KEY_PHONE_NUM, phone_num);
                params.addBodyParameter(Config.KEY_GENDER, etGender.getText().toString());
                params.addBodyParameter(Config.KEY_EMAIL, etEmail.getText().toString());
                params.addBodyParameter(Config.KEY_BIRTHDAY, etBirthday.getText().toString());
                params.addBodyParameter(Config.KEY_NICKNAME, etNickname.getText().toString());
                params.addBodyParameter(Config.KEY_PHOTO, photo);
                params.addBodyParameter(Config.KEY_JOB, etJob.getText().toString());
                httpUtils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        pd.dismiss();

                        try {
                            //创建数据库，保存用户数据
                            DbUtils db= DbUtils.create(AddInfoActivity.this);
                            FLUser flUser=new FLUser();
                            flUser.setEmail(etEmail.getText().toString());
                            flUser.setGender(etGender.getText().toString());
                            flUser.setJob(etJob.getText().toString());
                            flUser.setName(etNickname.getText().toString());
                            db.saveBindingId(flUser);
                        } catch (DbException e) {
                            e.printStackTrace();
                        }

                        Intent i = new Intent(AddInfoActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        pd.dismiss();
                        Toast.makeText(AddInfoActivity.this, R.string.information_fail, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    //转变uri为file类型
    private Uri convertUri(Uri uri){
        InputStream is=null;
        try {
            is=getContentResolver().openInputStream(uri);
            Bitmap bitmap= BitmapFactory.decodeStream(is);
            is.close();
            System.out.println("convertUri");
            return saveBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //将Bitmap转变为file类型Uri
    private Uri saveBitmap(Bitmap bitmap){
        File tmpDir=new File(Environment.getExternalStorageDirectory()+"/com.xinxin.faceliner");
        if(!tmpDir.exists()) {
            tmpDir.mkdir();
        }
        File img=new File(tmpDir.getAbsolutePath()+"userpicture.png");
        try {
            FileOutputStream fos=new FileOutputStream(img);
            bitmap.compress(Bitmap.CompressFormat.PNG, 85, fos);
            fos.flush();
            fos.close();
            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //对图像进行裁剪
    private void startImageZoom(Uri uri){
        Intent intent=new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent,CROP_REQUEST_CODE);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (data == null) {
                File picture = new File(Environment.getExternalStorageDirectory()+
                        File.separator+ImageName);
                Uri uri = Uri.fromFile(picture);
                startImageZoom(uri);
                return;

            } else {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap bm = extras.getParcelable("data");
                    Uri uri=saveBitmap(bm);

                    //向数据库中保存图片地址
                    //startImageZoom(uri);
                }
            }
        }else if(requestCode==GALLERY_REQUEST_CODE){

            if(data==null)
                return;
            else {
                Uri uri;
                uri = data.getData();
                Uri fileUri = convertUri(uri);

                //向数据库中保存图片地址
                startImageZoom(fileUri);
            }

        }else if(requestCode==CROP_REQUEST_CODE){
            if(data==null)
                return;
            Bundle extras=data.getExtras();
            Bitmap bm=extras.getParcelable("data");
            ivPhoto.setImageBitmap(bm);
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog=null;
        switch (id) {
            case LIST_DIALOG:
                AlertDialog.Builder b = new AlertDialog.Builder(this);
                b.setTitle(R.string.select_uploda_picture_method);
                b.setSingleChoiceItems(array, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selectNum=i;
                    }
                });
                b.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (array[selectNum].equals("摄像头")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            ImageName = System.currentTimeMillis()+".jpg";
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                                    Environment.getExternalStorageDirectory()
                                    , ImageName)));
                            startActivityForResult(intent, CAMERA_REQUEST_CODE);

                        } else {
                            Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("image/*");
                            startActivityForResult(intent, GALLERY_REQUEST_CODE);
                        }
                    }
                });
                dialog = b.create();
                break;
            default:
                break;
        }
        return dialog;
    }
}


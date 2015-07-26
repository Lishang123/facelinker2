package com.xinxin.facelinker.test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.test.AndroidTestCase;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

/**
 * Created by xinxin on 2015/7/24.
 */
public class ImageTest extends AndroidTestCase {



    public void testImage() throws Exception {
        File file = new File(Environment.getExternalStorageDirectory(), "a.jpeg");
        FileOutputStream fileOutputStream = new FileOutputStream(file);

        URL url = new URL("http://img5q.duitang.com/uploads/item/201506/05/20150605140709_VjSv8.jpeg");
        Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());
        BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
    }
public void testhttputils(){

    HttpUtils http = new HttpUtils();

    HttpHandler handler = http.download("http://img5q.duitang.com/uploads/item/201506/05/20150605140709_VjSv8.jpeg", "/sdcard/a.jpeg", true, true, new RequestCallBack<File>() {
    @Override
    public void onSuccess(ResponseInfo<File> responseInfo) {
        System.out.println("downloaded:" + responseInfo.result.getPath());
    }

    @Override
    public void onFailure(HttpException e, String s) {
        System.out.println(s);
    }
});}



    }


package com.xinxin.facelinker.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.xinxin.facelinker.domain.Version;

/**
 * Created by xinxin on 2015/7/24.
 */
public class TestActivity extends Activity {
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void testXutilsDb(){
        DbUtils utils = DbUtils.create(this, Environment.getExternalStorageState(),"tesss.db");
        Version version = new Version(2,"najksdfj","www.baidu.con","mianshiuh助手");
        try {
            utils.saveOrUpdate(version);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

}

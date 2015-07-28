package com.xinxin.facelinker.utils;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xinxin.facelinker.Config;

/**
 * Created by xinxin on 2015/7/20.
 */
public class NetHelper {
    /**
     * 从服务器获取数据
     */
    public static <T> void getDataFromServer(RequestParams params,RequestCallBack<T> requestCallBack) {
        HttpUtils utils = new HttpUtils();
        // 使用xutils发送请求
        utils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params,
                requestCallBack);
    }

    /**将对象装换为json字符串，用于网络传输
     * @param object
     * @return
     */
    public static String ObjectToJson(Object object){
        Gson gson = new Gson();
        return gson.toJson(object);
    }
    /**
     * 解析网络数据
     *
     * @param result
     */
    public static Object parseJsonData(String result, Class clazz) {
        Gson gson = new Gson();
        Object o = null;
        try {
            o = gson.fromJson(result, clazz.getClassLoader().loadClass(clazz.getName()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("解析结果" + o);
        return o;
    }
    /**
     * 向服务器发送数据并获取返回值
     */
    public static <T> void putDataToServer(RequestParams params,RequestCallBack<T> requestCallBack) {
        HttpUtils utils = new HttpUtils();
        // 使用xutils发送请求
        utils.send(HttpRequest.HttpMethod.POST, Config.CATEGORIES_URL, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {

                    }
                });
    }
//    环信服务器端REST平台
//    http://www.easemob.com/docs/rest/
}

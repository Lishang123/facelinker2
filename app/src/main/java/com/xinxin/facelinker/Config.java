package com.xinxin.facelinker;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by xinxin on 2015/7/20.
 */
public class Config {

    public static final String SERVER_URL = "http://192.168.1.105:8080";
    public static final String CATEGORIES_URL = SERVER_URL + "/facelinker/service/ConnectTest";// 获取分类信息的接口
    public static final String UPDATESOFTADDRESS = SERVER_URL + "/trywebservise/chatdemo-ui.apk";// 版本更新的地址

    public static final String ACTION = "action";
    public static final String CONNECT_VERSION_UPDATE_FAIL = "fail to connenct versionupdating";

    public static String CONTENT = "content";
    public static final String ACTION_CHANGE_FLUSER_INFO = "change_fluser_info";

    public static final String ACTION_LOGON = "logon";
    public static final String ACTION_LOGIN = "login";
    public static final String ACTION_ADD_INFO = "add_info";
    public static final String ACTION_SHOW_PALS = "show_pals";
    public static final String ACTION_GET_PAL_INFO = "get_pal_info";
    public static final String ACTION_ADD_BLACKLIST = "add_blacklist";
    public static final String ACTION_SHOW_BLACKLIST = "add_blacklist";
    public static final String ACTION_SEARCH_USER = "search_user";
    public static final String ACTION_DELETE_BLACKLIST = "delete_blacklist";
    public static final String ACTION_DELETE_MY_MOTION = "delete_my_motion";
    public static final String ACTION_SHOW_PALS_MOTION = "show_pals_motion";
    public static final String ACTION_SHOW_MOTION = "show_motion";
    public static final String ACTION_SHOW_MY_MOTION = "show_my_motion";
    public static final String ACTION_ADD_MY_MOTION = "add_my_motion";
    public static final String ACTION_ADD_COMMENT = "add_comment";
    public static final String ACTION_ADD_NEW_PAL = "add_new_pal";
    public static final String ACTION_SHOW_NEAR_USER_MOTION = "show_near_user_motion";
    public static final String ACTION_RECEIVE_NEW_PALS = "receive_new_pals";
    public static final String ACTION_RECEIVE_NEW_PAL_INFO = "receive_new_pal_info";
    public static final String ACTION_ACCEPT_ADD_NEW_PAL = "accept_add_new_pal";
    public static final String ACTION_REFUSE_ADD_NEW_PAL = "refuse_add_new_pal";
    public static final String ACTION_SEND_NEW_PAL_MESSAGE = "send_new_pal_message";
    public static final String ACTION_GET_LOGON_VERIFICATION_CODE = "get_logon_verification_code";
    public static final String ACTION_SHOW_MALE_TOPLIST = "show_male_toplist";
    public static final String ACTION_SHOW_FEMALE_TOPLIST = "show_female_toplist";


    public static final String APP_ID = "com.xinxin.facelinker";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_ACTION = "action";
    public static final String KEY_PHONE_NUM = "phone_num";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_STATUS = "status";
    public static final String KEY_LOGIN_NUM = "login_num";
    public static final String KEY_MY_ACCOUNT_NUM = "my_account_num";
    public static final String KEY_OTHER_ACCOUNT_NUM = "other_account_num";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_BIRTHDAY = "birthday";
    public static final String KEY_NICKNAME = "nickname";
    public static final String KEY_PHOTO = "photo";
    public static final String KEY_MOOD = "mood";
    public static final String KEY_HONOUR = "honour";
    public static final String KEY_SEARCH_USER_ACCOUNT_NUM = "search_user_account_num";
    public static final String KEY_MOTION = "motion";
    public static final String KEY_PALS_MOTION_ITEM = "pals_motion_item";
    public static final String KEY_MY_PALS_ITEMS = "my_pals_items";
    public static final String KEY_BLACKLIST_ITEMS = "black_items";
    public static final String KEY_MOTION_ID = "motion_id";
    public static final String KEY_MY_MOTION_ITEMS = "my_motion_items";
    public static final String KEY_MY_MOTION = "my_motion";
    public static final String KEY_COMMENT = "comment";
    public static final String KEY_LONGTITUDE = "longitude";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_NEAR_USER_MOTION_ITEMS = "near_user_motion_items";
    public static final String KEY_ADD_NEW_PAL_MESSAGE = "add_new_pal_message";
    public static final String KEY_RECEIVE_NEW_PAL_MESSAGE = "receive_new_pal_message";
    public static final String KEY_RECEIVE_NEW_PALS = "receive_new_pals";
    public static final String KEY_SEND_NEW_PAL_MESSAGE = "send_new_pal_message";
    public static final String KEY_JOB = "job";

    public static final int RESULT_STATUS_SUCCESS = 1;
    public static final int RESULT_STATUS_FAIL = 0;
    public static final int RESULT_STATUS_INVALID_TOKEN = 2;

    public static String getCachedToken(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_APPEND).getString(KEY_TOKEN, null);
    }

    public static void cacheToken(Context context, String token) {
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(KEY_TOKEN, token);
        e.commit();
    }

    public static String getCachedAccountNum(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_APPEND).getString(KEY_MY_ACCOUNT_NUM, null);
    }

    public static void cacheAccountNum(Context context, String account_num) {
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(KEY_MY_ACCOUNT_NUM, account_num);
        e.commit();
    }

    public static final String ACTION_CHANGE_FLUSER_PASSWORD = "change_fluser_password";

}

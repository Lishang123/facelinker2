package com.xinxin.facelinker.test;

import android.os.Environment;
import android.test.AndroidTestCase;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.xinxin.facelinker.domain.Version;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xinxin on 2015/7/24.
 */
public class Database2Test extends AndroidTestCase {
    DbUtils utils;
    Version heihei;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        utils = DbUtils.create(getContext(), Environment.getExternalStorageDirectory() + "/", "Mypals.db");
        heihei = new Version();
        heihei.setId(2);
    }
    public  void  testSave(){

    }

    public void testXutilsDb() {

        Version version = new Version(2, "najkfdfj", "www.sdfsu.con", "miafghdfiuh助");
        Version version2 = new Version(5, "najvfdfj", "www.sdfsu.con", "miafghdfiuh助");

        try {
            utils.save(version);
            utils.save(version2);

        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void testInsert() {
        Version stu = null;
        for (int i = 0; i < 20; i++) {
            stu = new Version();
            stu.setVersion(10 + i);
            stu.setUrl("www.hahah." + i);
            stu.setDescribe("jack" + i);
            List<Version> mList = new ArrayList<Version>();
            mList.add(stu);
            try {
                utils.save(stu);
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
    }

    public void testFind() {
        try {
//            List<Version> stus = utils.findAll(Selector.from(Version.class));
//            for (Version v : stus) {
//                System.out.println(v.toString());
//            }
            Version v = utils.findFirst(Version.class);
            System.out.println(v.toString());
            System.out.println("***********************");
            Version v2 = utils.findById(Version.class, heihei.getId());
            System.out.println(v2.toString());
            System.out.println("***********************");
            Version v3 = utils.findFirst(Selector.from(Version.class).where("id", ">", "5").and("versionName", "=", null));
            System.out.println(v3.toString());
            System.out.println("***********************");
            List<Version> mList = new ArrayList<Version>();
            mList = utils.findAll(Selector.from(Version.class).where("id", ">", "5").and("versionName", "=", null));
            for (Version version : mList) {
                System.out.println(version.toString());
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void testDelete() {
        try {
            List<Version> stus = utils.findAll(Selector.from(Version.class));
//            utils.delete(stus.get(0));
//            utils.deleteAll(stus);
//            utils.deleteById(Version.class, WhereBuilder.b("id", "==", "10"));
//            utils.dropTable(Student.class);
//            utils.dropDb();
            utils.delete(Version.class, WhereBuilder.b("id", "==", "10"));
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void testUpdate() {
        try {
//            List<Version> stus = utils.findAll(Selector.from(Version.class));
//            Version stu = stus.get(0);
            Version v = utils.findFirst(Selector.from(Version.class).where("describe","=","jack8"));
            v.setDescribe("lalalal");
            utils.update(v);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}

//{
//    private DatabaseHelper databaseHelper;
//    private SQLiteDatabase db;
//
//    @Override
//    protected void setUp() throws Exception {
//        super.setUp();
//        databaseHelper = new DatabaseHelper(getContext(), "person.db", null, 1);
//        db = databaseHelper.getWritableDatabase();
//    }
//
//    @Override
//    protected void tearDown() throws Exception {
//        super.tearDown();
//        db.close();
//    }
//
//    //   public void test(){
////       DatabaseHelper databaseHelper = new DatabaseHelper(getContext(),"person.db",null,1);
////       SQLiteDatabase db = databaseHelper.getWritableDatabase();
////    }
//    public void testinsert() {
//
//        db.execSQL("insert into person (name,money,phone) values (?,?,?)", new Object[]{"小", "13000.0", "13419547601"});
//        db.execSQL("insert into person (name,money,phone) values (?,?,?)", new Object[]{"小新", "16000.0", "1341901"});
//        db.execSQL("insert into person (name,money,phone) values (?,?,?)", new Object[]{"小新", 13000.0, 13419});
//
//    }
//
//    public void testinser2t() {
//        ContentValues values = new ContentValues();
//        values.put("name", "小笨蛋");
//        values.put("money", "111111");
//        values.put("phone", "131541");
//        db.insert("person", null, values);
//
//    }
//
//    public void testTransaction() {
//        try{
//            db.beginTransaction();
//            ContentValues values = new ContentValues();
//            values.put("money",13000);
//            db.update("person", values, "name=?", new String[]{"小"});
//            values.clear();
//            values.put("money", -13000);
//            db.update("person",values,"name=?",new String[]{"小笨蛋"});
//            db.setTransactionSuccessful();
//        }finally {
//            db.endTransaction();
//        }
//
//
//    }
//}

package com.xinxin.facelinker.db;

/**
 * Created by xinxin on 2015/7/24.
 */
import java.io.Serializable;

public interface Dao<T> {
    void add(T t);
    void update(T t);
    void del(Serializable pk);
    T findOne(Serializable pk);
}


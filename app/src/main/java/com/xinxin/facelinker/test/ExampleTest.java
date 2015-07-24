package com.xinxin.facelinker.test;

import android.test.InstrumentationTestCase;

/**
 * Created by xinxin on 2015/7/21.
 */
public class ExampleTest extends InstrumentationTestCase {
public void test() throws Exception{
    int expect,real;
    expect = 1;
    real = 2;

    assertEquals(expect,real);

}

}

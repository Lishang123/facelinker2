package com.xinxin.facelinker.test;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.Button;

import com.easemob.chatuidemo.R;
import com.xinxin.facelinker.activity.LoginActivity;

/**
 * Created by xinxin on 2015/7/21.
 */
public class LoginPresenterTest extends ActivityUnitTestCase<LoginActivity> {
    Intent mLoginIntent;

    public LoginPresenterTest() {
        super(LoginActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
       mLoginIntent = new Intent(getInstrumentation().getTargetContext(), LoginActivity.class);
    }
    @MediumTest
    public void testLoginButton_labelText()  {
        startActivity(mLoginIntent, null, null);
        final Button loginButton = (Button) getActivity().findViewById(R.id.btn_login);
        final String buttontext = "111";
        assertEquals("Unexpected button ladel text", buttontext, loginButton.getText());

    }
    public void testLoginActivityMoveToIndex(){
        startActivity(mLoginIntent, null, null);
        final Button loginButton = (Button) getActivity().findViewById(R.id.btn_login);
        loginButton.performClick();
        final Intent loginIntent = getStartedActivityIntent();
        assertNotNull("Intent was null", loginIntent);

        //这一句是判断你在跳转后有没调finish()
        assertTrue(isFinishCalled());
    }
}

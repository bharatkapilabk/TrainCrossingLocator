package com.example.traincrossinglocator;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.action.ViewActions.click;
import android.view.View;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

public class LoginOrCreateTest {

    @Rule
    public ActivityTestRule<LoginOrCreate> activityTestRule=new ActivityTestRule<LoginOrCreate>(LoginOrCreate.class);
    private LoginOrCreate loginOrCreate=null;
    Instrumentation.ActivityMonitor monitor= getInstrumentation().addMonitor(LogIn.class.getName(), null,false);
    Instrumentation.ActivityMonitor monitor1=getInstrumentation().addMonitor(SignUp.class.getName(),null , false);


    @Rule
    public ActivityTestRule<LogIn> activityTestRule1=new ActivityTestRule<LogIn>(LogIn.class);
    private LogIn login=null;



    @Before
    public void setUp() throws Exception {
        loginOrCreate=activityTestRule.getActivity();
        login=activityTestRule1.getActivity();
    }

    @Test
    public void testLaunch(){
        View view=loginOrCreate.findViewById(R.id.btn_login);
        assertNotNull(view);
    }
    @Test
    public void testLaunch1(){
        View view=loginOrCreate.findViewById(R.id.btn_signup);
        assertNotNull(view);
    }
    @Test
    public void testLaunchofSecondActivityonButtonClick(){
        assertNotNull(loginOrCreate.findViewById(R.id.btn_login));
        assertNotNull(loginOrCreate.findViewById(R.id.btn_signup));
        Espresso.onView(withText(R.id.btn_login)).perform(click());
      //  Espresso.onView(withId(R.id.btn_signup)).perform(click());

        Activity login= getInstrumentation().waitForMonitorWithTimeout(monitor,10000);
        assertNotNull(login);
        login.finish();




        Activity signup= getInstrumentation().waitForMonitorWithTimeout(monitor1,10000);
        assertNotNull(signup);
        signup.finish();
    }



    @After
    public void tearDown() throws Exception {
        loginOrCreate=null;
    }
}
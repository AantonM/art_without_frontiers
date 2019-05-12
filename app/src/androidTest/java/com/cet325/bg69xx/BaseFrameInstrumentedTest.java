package com.cet325.bg69xx;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;
import android.widget.TextView;

public class BaseFrameInstrumentedTest  extends ActivityInstrumentationTestCase2<BaseFrameActivity> {

    private Activity baseFrameActivity;

    public BaseFrameInstrumentedTest() {
        super(BaseFrameActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        // Get the activity instance
        baseFrameActivity = getActivity();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testNumberOfElementsInTheNavigationDrawerMenu(){
        int expected = 5;
        int actual;

        ListView lv = (ListView) baseFrameActivity.getParent().findViewById(R.id.navList);
        actual = lv.getAdapter().getCount();

        setActivity(null);
        assertEquals(expected,actual);
    }
}

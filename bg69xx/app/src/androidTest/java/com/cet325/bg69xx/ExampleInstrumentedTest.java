package com.cet325.bg69xx;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

public class ExampleInstrumentedTest extends ActivityInstrumentationTestCase2<HomeActivity> {

    private Activity mHomeActivity;
    private TextView textView;

    public ExampleInstrumentedTest() {
        super(HomeActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        // Get the activity instance
        mHomeActivity = getActivity();
        // Get instance of the editText box
        textView = (TextView)mHomeActivity.findViewById(R.id.txtHomeTitle);

    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
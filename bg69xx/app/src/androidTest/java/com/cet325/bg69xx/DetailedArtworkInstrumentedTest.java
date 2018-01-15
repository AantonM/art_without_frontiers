package com.cet325.bg69xx;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

public class DetailedArtworkInstrumentedTest extends ActivityInstrumentationTestCase2<DetailedArtworkActivity> {

    private Activity detailedArtworkActivity;
    private TextView txtViewTitle;

    public DetailedArtworkInstrumentedTest() {
        super(DetailedArtworkActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        // Get the activity instance
        detailedArtworkActivity = getActivity();
        // Get instance of the editText box
        txtViewTitle = (TextView)detailedArtworkActivity.findViewById(R.id.txtViewTitle);

    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testPersistentDetailedArtworkData(){
        txtViewTitle = (TextView)detailedArtworkActivity.findViewById(R.id.txtViewTitle);
        final String title = "Test Title Artwork";

        detailedArtworkActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txtViewTitle.setText(title);
            }
        });

        detailedArtworkActivity.finish();
        setActivity(null);

        detailedArtworkActivity = getActivity();
        String returnedTitle = txtViewTitle.getText().toString();

        assertEquals(title, returnedTitle);


    }

}
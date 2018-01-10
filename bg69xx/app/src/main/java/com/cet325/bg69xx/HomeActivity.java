package com.cet325.bg69xx;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.UUID;


public class HomeActivity extends BaseFrameActivity {

    //params used for the left Navigation Drawer
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private SharedPreferences prefs;

    /***
     * Method called when this activity is created that set up the layout.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreateDrawer(R.layout.activity_home);
        getSupportActionBar().setTitle("Reina Sofia");
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        
        //make a test database connection
        databaseInitialisation();

    }

    /***
     * This method makes a test database connection.
     * If the database doesn't exit it will create a new one and populate it with the stock artwork values.
     * If the database exist it will close the connection without doing anything.
     */
    private void databaseInitialisation() {
        MySqlLiteHelper db = new MySqlLiteHelper(this);
        db.getWritableDatabase();
        db.close();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
            prefs.edit().putBoolean("firstrun", false).commit();

            //set default curency only the first time the application is runned
            prefs.edit().putString("pref_currency", getString(R.string.default_currency)).commit();

            //Create UUID for this user
            String uniqueID = UUID.randomUUID().toString();
            prefs.edit().putString("uuid", uniqueID).commit();
        }
    }

}

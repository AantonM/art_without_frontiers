package com.cet325.bg69xx;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class HomeActivity extends BaseFrameActivity {

    //params used for the left Navigation Drawer
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

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

        //set default curency
        setDefaultCurrency();
        
        //make a test database connection
        databaseInitialisation();
    }

    private void setDefaultCurrency() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("currency", getString(R.string.default_currency));
        editor.apply();
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

}

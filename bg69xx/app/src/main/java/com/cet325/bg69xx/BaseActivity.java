package com.cet325.bg69xx;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class BaseActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    //params used for the left Navigation Drawer
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    protected FrameLayout content;

    protected void onCreateDrawer(final int layoutResID) {
        setContentView(R.layout.drawer_layout);
        content = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(layoutResID, content, true);

        setupActionBar();

        mDrawerLayout = (DrawerLayout)findViewById(R.id.navigation_drawer_layout);
        String[] osArray = { "View paintings", "Exhibitions", "Gallery map", "Open hours", "Location"};
        mDrawerList = (ListView) findViewById(R.id.navList);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    Intent artworksListIntent = new Intent(view.getContext(), MasterArtworksListActivity.class);
                    startActivity(artworksListIntent);
                }
            }
        });


        setupDrawer();

    }

    private void setupActionBar() {

        mActivityTitle = getTitle().toString();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }


    /***
     * Handles the Navigation Drawer's behaviour when opening and closing.
     */
    protected void setupDrawer() {
        Toast.makeText(this, "setupDrawer", Toast.LENGTH_SHORT).show();

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when the Navagation drawe is opened. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Toast.makeText(BaseActivity.this, "onDrawerOpened", Toast.LENGTH_SHORT).show();
                getSupportActionBar().setTitle("Navigation");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when the Navagation drawe is closed. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                Toast.makeText(BaseActivity.this, "onDrawerClosed", Toast.LENGTH_SHORT).show();
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    /***
     * Listener for option selected from both the Navigation Drawer and the menu.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //check if the navigation menu is selected
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            Toast.makeText(BaseActivity.this, "Navigation", Toast.LENGTH_SHORT).show();
        }

        //check if the contacts option is selected
        if(id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
        }

        //check if the tickets button is selected
        if(id == R.id.tickets) {
            Intent ticketsIntent = new Intent(this, TicketsActivity.class);
            startActivity(ticketsIntent);
        }

        return true;
    }

    /***
     * Synchronize the state of the drawer indicator.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    /***
     * Change the state of the Drawer.
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /***
     * Creates the right menu.
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }
}

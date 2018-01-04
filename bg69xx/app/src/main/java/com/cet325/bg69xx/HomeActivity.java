package com.cet325.bg69xx;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HomeActivity extends AppCompatActivity {

    //params used for the left Navigation Drawer
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;

    /***
     * Method called when this activity is created that set up the layout.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setTitle("Reina Sofia");


        mDrawerLayout = (DrawerLayout)findViewById(R.id.navigation_drawer_layout);
        mActivityTitle = getTitle().toString();
        mDrawerList = (ListView)findViewById(R.id.navList);

        createNavigationMenuOptions();
        setupDrawer();
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    /***
     * Creates the elements of the Navigation Drawer.
     */
    private void createNavigationMenuOptions() {
        String[] osArray = { "View paintings", "Exhibitions", "Gallery map", "Open hours", "Location"};
        Toast.makeText(HomeActivity.this, "ND created", Toast.LENGTH_SHORT).show();
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    Toast.makeText(HomeActivity.this, "View Paintings", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /***
     * Handles the Navigation Drawer's behaviour when opening and closing.
     */
    protected void setupDrawer() {
        Toast.makeText(HomeActivity.this, "kon", Toast.LENGTH_SHORT).show();

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when the Navagation drawe is opened. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Toast.makeText(HomeActivity.this, "onDrawerOpened", Toast.LENGTH_SHORT).show();
                getSupportActionBar().setTitle("Navigation");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when the Navagation drawe is closed. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                Toast.makeText(HomeActivity.this, "onDrawerClosed", Toast.LENGTH_SHORT).show();
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
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
            Toast.makeText(HomeActivity.this, "Navigation", Toast.LENGTH_SHORT).show();
        }

        //TODO:Remove this listener and integrate the code within onCreate! This is just temp.
        if(id == R.id.action_contacts) {
            MySqlLiteHelper db = new MySqlLiteHelper(this);
            db.addArtwork(new ExhibitsDbMapper("a","b","c","d",null,"1000",5));
            db.close();
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
        Toast.makeText(HomeActivity.this, "onPostCreate", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(HomeActivity.this, "onConfigurationChanged", Toast.LENGTH_SHORT).show();
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

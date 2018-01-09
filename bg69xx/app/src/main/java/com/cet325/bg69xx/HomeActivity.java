package com.cet325.bg69xx;

import android.os.Bundle;
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

        //make a test database connection
        databaseInitialisation();

        //create the right navigation drawer
//        createNavigationDrawer();
    }

 //   protected void createNavigationDrawer() {
        //creation of the right navigation bar
//        mDrawerLayout = (DrawerLayout)findViewById(R.id.navigation_drawer_layout);

//        mDrawerList = (ListView) mDrawerLayout.findViewById(R.id.navList);

//        createNavigationMenuOptions();
//        setupDrawer();
//        mDrawerToggle.setDrawerIndicatorEnabled(true);
//        mDrawerLayout.setDrawerListener(mDrawerToggle);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//      }

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


//    /***
//     * Creates the elements of the Navigation Drawer.
//     */
//    private void createNavigationMenuOptions() {
//        String[] osArray = { "View paintings", "Exhibitions", "Gallery map", "Open hours", "Location"};
//        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
//        mDrawerList.setAdapter(mAdapter);
//
//        //listener for option selected from the drawer meny
//        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if(position == 0){
//                    Intent artworksListIntent = new Intent(view.getContext(), MasterArtworksListActivity.class);
//                    startActivity(artworksListIntent);
//                }
//            }
//        });
//    }

//    /***
//     * Handles the Navigation Drawer's behaviour when opening and closing.
//     */
//    protected void setupDrawer() {
//        Toast.makeText(HomeActivity.this, "setupDrawer", Toast.LENGTH_SHORT).show();
//
//        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
//                R.string.drawer_open, R.string.drawer_close) {
//
//            /** Called when the Navagation drawe is opened. */
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//                Toast.makeText(HomeActivity.this, "onDrawerOpened", Toast.LENGTH_SHORT).show();
//                getSupportActionBar().setTitle("Navigation");
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//            }
//
//            /** Called when the Navagation drawe is closed. */
//            public void onDrawerClosed(View view) {
//                super.onDrawerClosed(view);
//                Toast.makeText(HomeActivity.this, "onDrawerClosed", Toast.LENGTH_SHORT).show();
//                getSupportActionBar().setTitle(mActivityTitle);
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//            }
//        };
//    }

//    /***
//     * Listener for option selected from both the Navigation Drawer and the menu.
//     *
//     * @param item
//     * @return
//     */
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//
//        //check if the navigation menu is selected
//        if (mDrawerToggle.onOptionsItemSelected(item)) {
//            Toast.makeText(HomeActivity.this, "Navigation", Toast.LENGTH_SHORT).show();
//        }
//
//        //check if the contacts option is selected
//        if(id == R.id.action_settings) {
//            Intent settingsIntent = new Intent(this, SettingsActivity.class);
//            startActivity(settingsIntent);
//        }
//
//        //check if the tickets button is selected
//        if(id == R.id.tickets) {
//            Intent ticketsIntent = new Intent(this, TicketsActivity.class);
//            startActivity(ticketsIntent);
//        }
//
//        return true;
//    }

//    /***
//     * Synchronize the state of the drawer indicator.
//     *
//     * @param savedInstanceState
//     */
//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        mDrawerToggle.syncState();
//    }
//
//    /***
//     * Change the state of the Drawer.
//     *
//     * @param newConfig
//     */
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        mDrawerToggle.onConfigurationChanged(newConfig);
//    }
//
//    /***
//     * Creates the right menu.
//     *
//     * @param menu
//     * @return
//     */
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_home, menu);
//        return true;
//    }

}

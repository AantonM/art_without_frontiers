package com.cet325.bg69xx;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MasterArtworksListActivity extends HomeActivity {

    List<ArtworksDbMapper> artworksList = null;
    ListView listView;

    /***
     *  Method that sets up the Master Artwork list.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_artworks_list);
        getSupportActionBar().setTitle("Hidden Artworks");

        //create the sort spinner
        createSortSpinner();
        //create floating action button - add
        createFloatingAddBtn();
        //get all artworks from the database
        loadAllArtworks();
        //display the list into a listview
        displayCustomMasterList();
    }

    /***
     * Creates the Sort spinner that can arrange the displayed list of artworks
     */
    private void createSortSpinner() {
        Spinner spnSort = (Spinner) findViewById(R.id.spnSort);
        //the spinner options
        String[] spnOptions = new String[] {"Sort by:","Title asc", "Title desc", "Artist asc", "Artis desc", "Year asc", "Year desc"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spnOptions);
        //change the stock layout of the spinner
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        spnSort.setAdapter(adapter);
        //set initial selection of the spinner
        spnSort.setSelection(0);

        spnSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //avoid the first option in the spinner which acts like a title "Select option:"
                if(position != 0) {
                    sortList(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    /***
     * Sort the list depending on the spinner selection
     * @param selectedOptionId
     */
    private void sortList(int selectedOptionId) {

        switch(selectedOptionId){
            //Order by title asc
            case 1:
                Collections.sort(artworksList, new Comparator<ArtworksDbMapper>() {
                    @Override
                    public int compare(ArtworksDbMapper object1, ArtworksDbMapper object2) {
                        return object1.getTitle().compareTo(object2.getTitle());
                    }
                });
                break;
            //Order by title desc
            case 2:
                Collections.sort(artworksList, new Comparator<ArtworksDbMapper>() {
                    @Override
                    public int compare(ArtworksDbMapper object1, ArtworksDbMapper object2) {
                        return object2.getTitle().compareTo(object1.getTitle());
                    }
                });
                break;
            //Order by artist asc
            case 3:
                Collections.sort(artworksList, new Comparator<ArtworksDbMapper>() {
                    @Override
                    public int compare(ArtworksDbMapper object1, ArtworksDbMapper object2) {
                        return object1.getArtist().compareTo(object2.getArtist());
                    }
                });
                break;
            //Order by artist desc
            case 4:
                Collections.sort(artworksList, new Comparator<ArtworksDbMapper>() {
                    @Override
                    public int compare(ArtworksDbMapper object1, ArtworksDbMapper object2) {
                        return object2.getArtist().compareTo(object1.getArtist());
                    }
                });
                break;
            //Order by year asc
            case 5:
                Collections.sort(artworksList, new Comparator<ArtworksDbMapper>() {
                    @Override
                    public int compare(ArtworksDbMapper object1, ArtworksDbMapper object2) {
                        return object1.getYear().compareTo(object2.getYear());
                    }
                });
                break;
            //Order by year desc
            case 6:
                Collections.sort(artworksList, new Comparator<ArtworksDbMapper>() {
                    @Override
                    public int compare(ArtworksDbMapper object1, ArtworksDbMapper object2) {
                        return object2.getYear().compareTo(object1.getYear());
                    }
                });
                break;
        }

        //Update the List view
        displayCustomMasterList();
    }

    /***
     * Create onClick listener on the floating add btn
     */
    private void createFloatingAddBtn() {
        FloatingActionButton floatingAddBtn = (FloatingActionButton) findViewById(R.id.floatingMasterAdd);
        floatingAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addArtworkIntent = new Intent(v.getContext(), AddArtworkActivity.class);
                startActivity(addArtworkIntent);
            }
        });
    }

    /***
     * Get all artworks from the db
     */
    private void loadAllArtworks() {
        MySqlLiteHelper db = new MySqlLiteHelper(this);
        artworksList = db.getAllArtworks();
        db.close();
    }

    /***
     * Display the artworks into a ListView
     */
    private void displayCustomMasterList () {

        //The data that is going to be displayed
        List<String> lstArtist = new ArrayList<>();
        List<String> lstTitles = new ArrayList<>();
        List<Bitmap> lstBitmapImages = new ArrayList<>();
        List<String> lstYear = new ArrayList<>();
        List<Integer> lstRank = new ArrayList<>();

        //extract the data from the Master list into sublists
        for(int a = 0; a < artworksList.size(); a++){
            lstArtist.add(artworksList.get(a).getArtist());
            lstTitles.add(artworksList.get(a).getTitle());
            lstBitmapImages.add(ByteArrayToBitmapImgList(artworksList.get(a).getImage()));
            lstYear.add(artworksList.get(a).getYear());
            lstRank.add(artworksList.get(a).getRank());
        }

        //send the data to a custom array list
        MasterArtworksListCustomLayoutHandler customMasterList = new MasterArtworksListCustomLayoutHandler(this, lstArtist, lstTitles,lstBitmapImages, lstYear, lstRank);
        listView = (ListView) findViewById(R.id.lstArtworks);
        listView.setAdapter(customMasterList);
        //listens which element of the list is selected
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO: Open the specific detailed view
                Toast.makeText(MasterArtworksListActivity.this, "You Clicked item number: " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /***
     *  Convert Byte array to Bitmap image
     * @param imgByte
     * @return
     */
    private static Bitmap ByteArrayToBitmapImgList(byte[] imgByte) {
        if(imgByte != null) {
            return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        } else {
            return null;
        }
    }
}

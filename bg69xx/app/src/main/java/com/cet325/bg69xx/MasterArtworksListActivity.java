package com.cet325.bg69xx;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
                        return object1.title.compareTo(object2.title);
                    }
                });
                break;
            //Order by title desc
            case 2:
                Collections.sort(artworksList, new Comparator<ArtworksDbMapper>() {
                    @Override
                    public int compare(ArtworksDbMapper object1, ArtworksDbMapper object2) {
                        return object2.title.compareTo(object1.title);
                    }
                });
                break;
            //Order by artist asc
            case 3:
                Collections.sort(artworksList, new Comparator<ArtworksDbMapper>() {
                    @Override
                    public int compare(ArtworksDbMapper object1, ArtworksDbMapper object2) {
                        return object1.artist.compareTo(object2.artist);
                    }
                });
                break;
            //Order by artist desc
            case 4:
                Collections.sort(artworksList, new Comparator<ArtworksDbMapper>() {
                    @Override
                    public int compare(ArtworksDbMapper object1, ArtworksDbMapper object2) {
                        return object2.artist.compareTo(object1.artist);
                    }
                });
                break;
            //Order by year asc
            case 5:
                Collections.sort(artworksList, new Comparator<ArtworksDbMapper>() {
                    @Override
                    public int compare(ArtworksDbMapper object1, ArtworksDbMapper object2) {
                        return object1.year.compareTo(object2.year);
                    }
                });
                break;
            //Order by year desc
            case 6:
                Collections.sort(artworksList, new Comparator<ArtworksDbMapper>() {
                    @Override
                    public int compare(ArtworksDbMapper object1, ArtworksDbMapper object2) {
                        return object2.year.compareTo(object1.year);
                    }
                });
                break;
        }

        //Update the List view
        displayCustomMasterList();
    }

    /***
     * Get all artworks from the db
     */
    private void loadAllArtworks() {
        MySqlLiteHelper db = new MySqlLiteHelper(this);
        artworksList = db.getAllArtworks();

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
            lstArtist.add(artworksList.get(a).artist);
            lstTitles.add(artworksList.get(a).title);
            lstBitmapImages.add(ByteArrayToBitmapImgList(artworksList.get(a).image));
            lstYear.add(artworksList.get(a).year);
            lstRank.add(artworksList.get(a).rank);
        }

        //send the data to a custom array list
        MasterArtworksListCustomLayoutHandler customMasterList = new MasterArtworksListCustomLayoutHandler(this, lstArtist, lstTitles,lstBitmapImages, lstYear, lstRank);
        listView = (ListView) findViewById(R.id.lstArtworks);
        listView.setAdapter(customMasterList);
        //listens which element of the list is selected
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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

package com.cet325.bg69xx;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MasterArtworksListActivity extends HomeActivity {

    List<ArtworksDbMapper> artworksList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_artworks_list);
        getSupportActionBar().setTitle("Hidden Artworks");

        loadAllArtworks();
        populateListView();
    }

    private void loadAllArtworks() {
        MySqlLiteHelper db = new MySqlLiteHelper(this);
        artworksList = db.getAllArtworks();
    }

    private void populateListView() {
        ListView lstArtworks = (ListView) findViewById(R.id.lstArtworks);
        ArrayAdapter<ArtworksDbMapper> adapter = new ArrayAdapter<ArtworksDbMapper>(this, android.R.layout.simple_list_item_1, artworksList);
        lstArtworks.setAdapter(adapter);
    }

}

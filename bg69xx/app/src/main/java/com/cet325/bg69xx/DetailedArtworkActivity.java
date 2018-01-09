package com.cet325.bg69xx;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class DetailedArtworkActivity extends HomeActivity {

    private int artwork_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreateDrawer(R.layout.activity_detailed_artwork);

        displayArtwork();
    }

    /***
     * Display artwork details from extras given from MasterArtworksListActivity
     *
     */
    private void displayArtwork() {
        final Bundle extras = getIntent().getExtras();

        artwork_id = extras.getInt("EXTRA_ID");

        ImageView image = (ImageView) findViewById(R.id.imgViewPicture);
        image.setImageBitmap(getImage(artwork_id));

        TextView title = (TextView) findViewById(R.id.txtViewTitle);
        title.setText(extras.getString("EXTRA_TITLE"));

        TextView artist = (TextView) findViewById(R.id.txtViewArtist);
        artist.setText(extras.getString("EXTRA_ARTIST"));

        TextView room = (TextView) findViewById(R.id.txtViewRoom);
        room.setText(extras.getString("EXTRA_ROOM"));

        TextView description = (TextView) findViewById(R.id.txtViewDescription);
        description.setText(extras.getString("EXTRA_DESCRIPTION"));

        TextView year = (TextView) findViewById(R.id.txtViewYear);
        year.setText(extras.getString("EXTRA_YEAR"));

        RatingBar rating = (RatingBar) findViewById(R.id.ratingViewRate);
        rating.setRating(extras.getInt("EXTRA_RANK"));

        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                updateRating(rating, artwork_id);
                ratingBar.setRating(rating);
                Toast.makeText(DetailedArtworkActivity.this,"Rating has been updated for " + extras.getString("EXTRA_TITLE") ,Toast.LENGTH_SHORT).show();
            }
        });
    }

    /***
     * Update artwork rating when the rating bar value is changed.
     *
     * @param rating
     * @param artworkId
     */
    private void updateRating(float rating, int artworkId) {
        MySqlLiteHelper db = new MySqlLiteHelper(this);
        db.updateArtworkRating(rating,artworkId);
        db.close();
    }

    /***
     * Get image from the database for the artwork that is displayed in the detailed view.
     *
     * @param extra_id
     * @return
     */
    private Bitmap getImage(int extra_id) {
        MySqlLiteHelper db = new MySqlLiteHelper(this);
        return db.getImageById(extra_id);
    }
}

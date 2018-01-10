package com.cet325.bg69xx;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class EditArtworkActivity extends BaseFrameActivity {

    private static final int PERMISSION_REQUEST_CAMERA = 0;
    int TAKE_PHOTO_CODE = 0;

    //image view to where the photo will be displayed
    private ImageView imgImportImage;

    //the base layout
    private View mLayout;
    private ArtworksDbMapper artworkMapper;

    private int artwork_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreateDrawer(R.layout.activity_edit_artwork);
        mLayout = findViewById(R.id.editArtworkLayout);
        getSupportActionBar().setTitle("View/Edit artwork");

        displayArtwork();

        //take a photo to upload when the image is selected
        takePictureListener();

        //read input data when submit button is selected
        editDataListener();

    }

    private void displayArtwork() {
        final Bundle extras = getIntent().getExtras();

        artwork_id = extras.getInt("EXTRA_ID");

        ImageView image = (ImageView) findViewById(R.id.imgEditPicture);
        image.setImageBitmap(getImage(artwork_id));

        EditText title = (EditText) findViewById(R.id.txtEditTitle);
        title.setText(extras.getString("EXTRA_TITLE"));

        EditText artist = (EditText) findViewById(R.id.txtEditArtist);
        artist.setText(extras.getString("EXTRA_ARTIST"));

        EditText room = (EditText) findViewById(R.id.txtEditRoom);
        room.setText(extras.getString("EXTRA_ROOM"));

        EditText description = (EditText) findViewById(R.id.txtEditDescription);
        description.setText(extras.getString("EXTRA_DESCRIPTION"));

        EditText year = (EditText) findViewById(R.id.txtEditYear);
        year.setText(extras.getString("EXTRA_YEAR"));

        RatingBar rating = (RatingBar) findViewById(R.id.ratingEditRate);
        rating.setRating(extras.getInt("EXTRA_RANK"));

        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                updateRating(rating, artwork_id);
                ratingBar.setRating(rating);
                Toast.makeText(EditArtworkActivity.this,"Rating has been updated for " + extras.getString("EXTRA_TITLE") ,Toast.LENGTH_SHORT).show();
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

    /***
     * Opens camera when the picture ImageView is clicked
     */
    private void takePictureListener() {
        imgImportImage = (ImageView) findViewById(R.id.imgEditPicture);
        imgImportImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
    }

    /***
     * Check permissions and if everything is ok request to start Camera activity, otherwise request permissions.
     */
    private void takePhoto() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // Permission is already available, start camera preview
            startCamera();
        } else {
            // Permission is missing and must be requested.
            requestCameraPermission();
        }
    }

    /***
     * Start camera activity.
     */
    private void startCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
    }

    /**
     * Requests the {@link android.Manifest.permission#CAMERA} permission.
     * If an additional rationale should be displayed, the user has to launch the request from
     * a SnackBar that includes additional information.
     */
    private void requestCameraPermission() {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with a button to request the missing permission.
            Snackbar.make(mLayout, "Camera access is required to display the camera preview.",
                    Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Request the permission
                    ActivityCompat.requestPermissions(EditArtworkActivity.this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
                }
            }).show();

        } else {
            Snackbar.make(mLayout, "Permission is not available. Requesting camera permission.", Snackbar.LENGTH_SHORT).show();
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
        }
    }

    /***
     * Requesting a permission for camera usage.
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            // Request for camera permission.
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Start camera preview Activity.
                startCamera();
            } else {
                // Permission request was denied.
                Toast.makeText(this, "Permission request was denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /***
     * Called after the CAMERA activity is closed. This method displayed the photo taken into the ImageView.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgImportImage.setImageBitmap(imageBitmap);
        }
    }

    private void editDataListener() {
        Button btnSubmit = (Button) findViewById(R.id.btnEditArtwork);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readData();
                updateData();
                returnToMasterArtworksList(v);
            }
        });
    }

    private void readData() {

        ImageView imageView = (ImageView) findViewById(R.id.imgEditPicture);
        Bitmap imageBitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        byte[] image = imgBitmapToByteArray(imageBitmap);

        EditText titleEditView = (EditText) findViewById(R.id.txtEditTitle);
        String title = titleEditView.getText().toString();

        EditText artistEditView = (EditText) findViewById(R.id.txtEditArtist);
        String artist = artistEditView.getText().toString();

        EditText roomEditView = (EditText) findViewById(R.id.txtEditRoom);
        String room = roomEditView.getText().toString();

        EditText descriptionEditView = (EditText) findViewById(R.id.txtEditDescription);
        String description = descriptionEditView.getText().toString();

        EditText yearEditView = (EditText) findViewById(R.id.txtEditYear);
        String year = yearEditView.getText().toString();

        RatingBar ratingEditView = (RatingBar) findViewById(R.id.ratingEditRate);
        int rank = Math.round(ratingEditView.getRating());


        artworkMapper = new ArtworksDbMapper(artwork_id, artist, title, room, description, image, year, rank);
    }

    /***
     * Convert bitmap to array of bytes which can be saved into the database as BLOB
     * @param bitmap
     * @return an image as array of bytes.
     */
    private static byte[] imgBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    /***
     * Upload the inputed data to the database
     */
    private void updateData() {
        MySqlLiteHelper db = new MySqlLiteHelper(this);
        db.updateArtwork(artworkMapper);
        db.close();

        Toast.makeText(this,artworkMapper.getTitle() + " has been updated.",Toast.LENGTH_SHORT).show();
    }

    /***
     * Return to the Master Artworks list and close this activity.
     * @param v
     */
    private void returnToMasterArtworksList(View v) {
        Intent viewAllArtworksIntent = new Intent(v.getContext(), MasterArtworksListActivity.class);
        startActivity(viewAllArtworksIntent);
        finish();
    }

}

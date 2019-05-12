package com.cet325.bg69xx;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class AddArtworkActivity extends BaseFrameActivity {

    private static final int PERMISSION_REQUEST_CAMERA = 0;
    int TAKE_PHOTO_CODE = 0;

    //image view to where the photo will be displayed
    private ImageView imgImportImage;
    //the base layout
    private View mLayout;
    private ArtworksDbMapper artworkMapper;

    SharedPreferences prefs;
    private String user_uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreateDrawer(R.layout.activity_add_artwork);
        mLayout = findViewById(R.id.addArtworkLayout);
        getSupportActionBar().setTitle("Add artwork");

        //get UUID created the first time the application was started
        getUserUUID();

        //take a photo to upload when the image is selected
        takePictureListener();

        //read input data when submit button is selected
        uploadDataListener();

    }

    private void getUserUUID(){
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        user_uuid = prefs.getString("uuid", "");
    }

    /***
     * Opens camera when the picture ImageView is clicked
     */
    private void takePictureListener() {
        imgImportImage = (ImageView) findViewById(R.id.imgAddPicture);
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
                    ActivityCompat.requestPermissions(AddArtworkActivity.this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
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


    private void uploadDataListener() {
        Button btnSubmit = (Button) findViewById(R.id.btnSumbitArtwork);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readData(v);
            }
        });
    }

    private void readData(View view) {

        ImageView imageView = (ImageView) findViewById(R.id.imgAddPicture);
        Bitmap imageBitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        byte[] image = imgBitmapToByteArray(imageBitmap);

        TextView titleAddView = (TextView) findViewById(R.id.txtAddTitle);
        String title = titleAddView.getText().toString();

        TextView artistAddView = (TextView) findViewById(R.id.txtAddArtist);
        String artist = artistAddView.getText().toString();

        TextView roomAddView = (TextView) findViewById(R.id.txtAddRoom);
        String room = roomAddView.getText().toString();

        TextView descriptionAddView = (TextView) findViewById(R.id.txtAddDescription);
        String description = descriptionAddView.getText().toString();

        TextView yearAddView = (TextView) findViewById(R.id.txtAddYear);
        String year = yearAddView.getText().toString();

        RatingBar ratingAddView = (RatingBar) findViewById(R.id.ratingAddRate);
        int rank = Math.round(ratingAddView.getRating());

        //check if mandatory fields are empty
        if(title.isEmpty() || artist.isEmpty() || year.isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please fill all mandatory fields: Title, Artist, Year.")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        //check if year is real
        else if(Integer.valueOf(year) > Calendar.getInstance().get(Calendar.YEAR) || Integer.valueOf(year) < 500){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Year should be between 0500 and " + Calendar.getInstance().get(Calendar.YEAR))
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        else{
            Button btnSubmit = (Button) findViewById(R.id.btnSumbitArtwork);
            btnSubmit.setEnabled(false);
            artworkMapper = new ArtworksDbMapper(artist, title, room, description, image, year, rank,user_uuid);
            uploadData(view);
        }


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
    private void uploadData(View view) {
        MySqlLiteHelper db = new MySqlLiteHelper(this);
            db.addArtwork(artworkMapper);
        db.close();

        Toast.makeText(this,"New artworks has been saved to the database.",Toast.LENGTH_SHORT).show();
        returnToMasterArtworksList(view);
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

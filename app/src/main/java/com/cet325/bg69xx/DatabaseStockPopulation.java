package com.cet325.bg69xx;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/***
 * Class that creates stock records for 13 artworks.
 */
public class DatabaseStockPopulation extends HomeActivity {

    private static List<List<String>> artworksList;

    public List<List<String>> getArtworksList() {
        return artworksList;
    }

    public DatabaseStockPopulation(Context currentContext, SQLiteDatabase db, String tableArtworks) {
        readStockArtworks(currentContext);
        insertStockArtworks(currentContext, tableArtworks, db);
    }


    /***
     * Creates a list of all artworks from artworks.xml
     *
     * @param current
     */
    private void readStockArtworks(Context current) {

        artworksList = new ArrayList<>();
        artworksList.add(Arrays.asList(current.getResources().getStringArray(R.array.item_1)));
        artworksList.add(Arrays.asList(current.getResources().getStringArray(R.array.item_2)));
        artworksList.add(Arrays.asList(current.getResources().getStringArray(R.array.item_3)));
        artworksList.add(Arrays.asList(current.getResources().getStringArray(R.array.item_4)));
        artworksList.add(Arrays.asList(current.getResources().getStringArray(R.array.item_5)));
        artworksList.add(Arrays.asList(current.getResources().getStringArray(R.array.item_6)));
        artworksList.add(Arrays.asList(current.getResources().getStringArray(R.array.item_7)));
        artworksList.add(Arrays.asList(current.getResources().getStringArray(R.array.item_8)));
        artworksList.add(Arrays.asList(current.getResources().getStringArray(R.array.item_9)));
        artworksList.add(Arrays.asList(current.getResources().getStringArray(R.array.item_10)));
        artworksList.add(Arrays.asList(current.getResources().getStringArray(R.array.item_11)));
        artworksList.add(Arrays.asList(current.getResources().getStringArray(R.array.item_12)));
        artworksList.add(Arrays.asList(current.getResources().getStringArray(R.array.item_13)));

        //Console printing.
        Log.d("list:", String.valueOf(artworksList.get(1)));
        Log.d("artist:", String.valueOf(artworksList.get(1).get(1)));
    }

    /***
     * Method that inserts 13 stock artworks to the database.
     *
     * @param current
     * @param tableArtworks
     * @param db
     */
    private void insertStockArtworks(Context current, String tableArtworks, SQLiteDatabase db) {

        //iterate trough all artworks
        for (int i = 0; i < artworksList.size(); i++) {
            //get the full path of the artwork artwork_image
            String imageURI = "drawable/artwork_" + artworksList.get(i).get(4);
            //get the id of the artwork_image
            int imageResource = current.getResources().getIdentifier(imageURI, null, current.getPackageName());
            //create a bitmap of the artwork_image
            Bitmap imageBitmap = BitmapFactory.decodeResource(current.getResources(), imageResource);

            byte[] imgBytes = new byte[0];
            //convert the artwork_image to array of bytes
            if (imageBitmap != null)
            {
                imgBytes = imgBitmapToByteArray(imageBitmap);
            }

            //insert the artwork to the database
            db.execSQL("INSERT INTO " + tableArtworks + "(artist, title, room, description, image, year, rank, uuid) VALUES (?,?,?,?,?,?,?,?)",
                    new Object[]{artworksList.get(i).get(0),
                            artworksList.get(i).get(1),
                            artworksList.get(i).get(2),
                            artworksList.get(i).get(3),
                            imgBytes,
                            artworksList.get(i).get(5),
                            Integer.valueOf(artworksList.get(i).get(6)),
                            "default"});

            Log.d("db status: ", "Add - " + artworksList.get(i).get(1));
        }
        Log.d("db status: ", "DONE creating records.");
    }

    /***
     * Convert bitmap to array of bytes which can be saved into the database as BLOB
     * @param bitmap
     * @return an artwork_image as array of bytes.
     */
    private static byte[] imgBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
}

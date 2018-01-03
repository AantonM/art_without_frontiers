package com.cet325.bg69xx;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseInitialisation extends HomeActivity {

    private static List<List<String>> artworksList;

    public DatabaseInitialisation(Context currentContext){
        createStockExhibitions(currentContext);
        saveArtworks(currentContext);
    }

    public void createStockExhibitions(Context current){

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

    private void saveArtworks(Context current) {
        MySqlLiteHelper db = new MySqlLiteHelper(current);

        for(int i =0; i < artworksList.size(); i++) {
            String imageURI = "drawable/artwork_" + artworksList.get(i).get(4);
            int imageResource = current.getResources().getIdentifier(imageURI, null, current.getPackageName());
            Bitmap imageBitmap = BitmapFactory.decodeResource(current.getResources(), imageResource);

            byte[] imgBytes = imgBitmapToByteArray(imageBitmap);

            ExhibitsDbMapper exhibitsDbMapper = new ExhibitsDbMapper(artworksList.get(i).get(0),
                    artworksList.get(i).get(1),
                    artworksList.get(i).get(2),
                    artworksList.get(i).get(3),
                    imgBytes,
                    artworksList.get(i).get(5),
                    Integer.valueOf(artworksList.get(i).get(6)));

            db.addArtwork(exhibitsDbMapper);
        }
        db.close();
        Log.d("db status: ", "DONE creating records.");
    }

    // convert from bitmap to byte array
    public static byte[] imgBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
}

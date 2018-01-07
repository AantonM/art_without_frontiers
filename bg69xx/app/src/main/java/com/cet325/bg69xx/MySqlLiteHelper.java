package com.cet325.bg69xx;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/***
 * Class used to handle the SqlLite Database - CRUD
 */
public class MySqlLiteHelper extends SQLiteOpenHelper {

    //Database details
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ReinaSofiaDB";
    private static final String TABLE_ARTWORKS = "allArtworks";

    //Definition of the "allArtworks" attributes
    private static final String KEY_ID = "id";
    private static final String KEY_ARTIST = "artist";
    private static final String KEY_TITLE = "title";
    private static final String KEY_ROOM = "room";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_YEAR = "year";
    private static final String KEY_RANK = "rank";

    private static Context currentContext;


    public MySqlLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        currentContext = context;
    }

    /***
     * Method that creates the "allArtworks" table and insert stock records.
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createAllArtworksQuery = "CREATE TABLE " + TABLE_ARTWORKS + " ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "artist TEXT, " +
                "title TEXT, " +
                "room TEXT, " +
                "description TEXT, " +
                "image BLOB, " +
                "year TEXT, " +
                "rank INTEGER )";

        db.execSQL(createAllArtworksQuery);

        //insert numerous stock records of artworks.
        DatabaseStockPopulation di = new DatabaseStockPopulation(currentContext, db, TABLE_ARTWORKS);
    }

    /***
     *  Method that deletes the "allArtworks" table and creates a clean new one by calling onCreate.
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTWORKS);
        this.onCreate(db);
    }

    /***
     * Method that inserts new record into "allArtworks" table.
     *
     * @param artwork
     */
    public void addArtwork(ArtworksDbMapper artwork){
        Log.d("addArtwork-1: ", artwork.toString());
        SQLiteDatabase db = this.getWritableDatabase();

        String path = db.getPath();
        Log.d("addArtwork-2: ", path);

        ContentValues values = new ContentValues();
        values.put(KEY_ARTIST, artwork.getArtist());
        values.put(KEY_TITLE, artwork.getTitle());
        values.put(KEY_ROOM, artwork.getRoom());
        values.put(KEY_DESCRIPTION, artwork.getDescription());
        values.put(KEY_IMAGE, artwork.getImage());
        values.put(KEY_YEAR, artwork.getYear());
        values.put(KEY_RANK, artwork.getRank());

        db.insert(TABLE_ARTWORKS, null, values);
        db.close();
    }

    /***
     * Method that gets all Artworks from the database.
     *
     * @param artwork
     */
    public List<ArtworksDbMapper> getAllArtworks(){
        List<ArtworksDbMapper> artworksList = new LinkedList<ArtworksDbMapper>();

        String query = "SELECT * FROM " + TABLE_ARTWORKS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = Integer.valueOf(cursor.getString(0));
                String artist = cursor.getString(1);
                String title = cursor.getString(2);
                String room = cursor.getString(3);
                String description = cursor.getString(4);
                byte[] image = cursor.getBlob(5);
                String year = cursor.getString(6);
                int rank = Integer.parseInt(cursor.getString(7));


                //add to list
                artworksList.add(new ArtworksDbMapper(id, artist, title, room, description, image, year, rank));
            }while (cursor.moveToNext());
        }

        cursor.close();
        Log.d("getAllArtworks: ", "All artworks loaded");
        db.close();

        return artworksList;
    }

    private static Bitmap ByteArrayToBitmapImg(byte[] imgByte) {
        return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
    }
}

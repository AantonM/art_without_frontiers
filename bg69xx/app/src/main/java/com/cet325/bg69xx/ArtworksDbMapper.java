package com.cet325.bg69xx;

import android.graphics.Bitmap;

/***
 * Class that maps the Artworks database.
 * It contains fields for the pictures Artist, Title, Room, Description, Image, Year and Rank.
 *
 */
public class ArtworksDbMapper {
    //“Artist”, “Title”, “Room” “Description”, “Image”, “Year” and “Rank”.
    public int id;
    public String artist;
    public String title;
    public String room;
    public String description;
    public byte[] image;
    public String year;
    public int rank;

    public ArtworksDbMapper(){}

    public ArtworksDbMapper(String artist, String title, String room, String description, byte[] image, String year, Integer rank){
        this.artist = artist;
        this.title = title;
        this.room = room;
        this.description = description;
        this.image = image;
        this.year = year;
        this.rank = rank;
    }

    @Override
    public String toString(){
        return "Artwork [id=" + id + ", title=" + title + ", room=" + room + ", description=" + description + ", image=" + image + ", year=" + year + ", rank=" + rank;
    }
}

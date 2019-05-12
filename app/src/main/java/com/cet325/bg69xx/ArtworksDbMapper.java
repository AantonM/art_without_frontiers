package com.cet325.bg69xx;

/***
 * Class that maps the Artworks database.
 * It contains fields for the pictures Artist, Title, Room, Description, Image, Year and Rank.
 *
 */
public class ArtworksDbMapper {
    //"ID", “Artist”, “Title”, “Room” “Description”, “Image”, “Year” and “Rank”.
    private int id;
    private String artist;
    private String title;
    private String room;
    private String description;
    private byte[] image;
    private String year;
    private int rank;
    private String uuid;

    public ArtworksDbMapper(){}

    public ArtworksDbMapper(String artist, String title, String room, String description, byte[] image, String year, Integer rank, String uuid){
        this.artist = artist;
        this.title = title;
        this.room = room;
        this.description = description;
        this.image = image;
        this.year = year;
        this.rank = rank;
        this.uuid = uuid;
    }

    public ArtworksDbMapper(int id, String artist, String title, String room, String description, byte[] image, String year, Integer rank){
        this.id = id;
        this.artist = artist;
        this.title = title;
        this.room = room;
        this.description = description;
        this.image = image;
        this.year = year;
        this.rank = rank;
    }


    public ArtworksDbMapper(int id, String artist, String title, String room, String description, byte[] image, String year, Integer rank, String uuid){
        this.id = id;
        this.artist = artist;
        this.title = title;
        this.room = room;
        this.description = description;
        this.image = image;
        this.year = year;
        this.rank = rank;
        this.uuid = uuid;
    }

    @Override
    public String toString(){
        return "Artwork [id=" + getId() + ", title=" + getTitle() + ", room=" + getRoom() + ", description=" + getDescription() + ", image=" + getImage() + ", year=" + getYear() + ", rank=" + getRank();
    }

    public int getId() {
        return id;
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

    public String getRoom() {
        return room;
    }

    public String getDescription() {
        return description;
    }

    public byte[] getImage() {
        return image;
    }

    public String getYear() {
        return year;
    }

    public int getRank() {
        return rank;
    }

    public String getUuid() {
        return uuid;
    }

}

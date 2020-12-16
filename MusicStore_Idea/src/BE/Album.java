package BE;

import java.util.Date;

public class Album {
    private int album_id;
    private int picture_id;
    private String title;
    private String genre;
    private String release_date;

    public Album(int album_id, int picture_id, String title, String genre, String release_date) {
        this.album_id = album_id;
        this.picture_id = picture_id;
        this.title = title;
        this.genre = genre;
        this.release_date = release_date;
    }

    public int getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
    }

    public int getPicture_id() {
        return picture_id;
    }

    public void setPicture_id(int picture_id) {
        this.picture_id = picture_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
}

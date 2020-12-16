package BE;

public class Song {
    private int id;
    private int album_id;
    private int author_id;
    private int song_lenght;
    private String title;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    private Author author;

    public Song(int id, int album_id, int author_id, int song_lenght, String title) {
        this.id = id;
        this.album_id = album_id;
        this.author_id = author_id;
        this.song_lenght = song_lenght;
        this.title = title;
        author = new Author(0,"","","");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public int getSong_lenght() {
        return song_lenght;
    }

    public void setSong_lenght(int song_lenght) {
        this.song_lenght = song_lenght;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;

    }
}

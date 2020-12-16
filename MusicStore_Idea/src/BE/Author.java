package BE;

public class Author {
    private int author_id;
    private String author_name;
    private String author_surname;
    private String nationality;

    public Author(int author_id, String author_name, String author_surname, String nationality) {
        this.author_id = author_id;
        this.author_name = author_name;
        this.author_surname = author_surname;
        this.nationality = nationality;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getAuthor_surname() {
        return author_surname;
    }

    public void setAuthor_surname(String author_surname) {
        this.author_surname = author_surname;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}

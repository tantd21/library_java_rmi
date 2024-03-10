import java.io.Serializable;

public class BookCopy implements Serializable {
    private static final long serialVersionUID =  8271963769266110398L;
    private int id;
    private int year_published;
    private int book_id;
    private int published_id;

    private String book_name;
    private String published_name;
    public BookCopy() {
    }

    public BookCopy(Object[] data) {
        this.id = (int) data[0];
        this.book_name = (String) data[1];
        this.year_published = (int) data[2];
        this.published_name = (String) data[3];
    }

    public BookCopy(int id, int year_published, int book_id, int published_id) {
        this.id = id;
        this.year_published = year_published;
        this.book_id = book_id;
        this.published_id = published_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear_published() {
        return year_published;
    }

    public void setYear_published(int year_published) {
        this.year_published = year_published;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getPublished_id() {
        return published_id;
    }

    public void setPublished_id(int published_id) {
        this.published_id = published_id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getPublished_name() {
        return published_name;
    }

    public void setPublished_name(String published_name) {
        this.published_name = published_name;
    }

    @Override
    public String toString() {
        return this.book_name;
    }
}

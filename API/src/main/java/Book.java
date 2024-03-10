import java.io.Serializable;

public class Book implements Serializable {
    private static final long serialVersionUID =  8271963769266110398L;
    private int id;
    private String title;
    private int category_id;

    public Book() {
    }
    public Book(Object[] data) {
        this.id = (int) data[0];
        this.title = (String) data[1];
    }
    public Book(int id, String title, int category_id) {
        this.id = id;
        this.title = title;
        this.category_id = category_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
    public String toString() {
        return this.title;
    }
}

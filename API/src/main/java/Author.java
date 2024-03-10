import java.io.Serializable;

public class Author implements Serializable {
    private static final long serialVersionUID =  8271963769266110398L;
    private int id;
    private String name;

    public Author() {
    }

    public Author(Object[] data) {
        this.id = (int) data[0];
        this.name = (String) data[1];
    }

    public Author(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
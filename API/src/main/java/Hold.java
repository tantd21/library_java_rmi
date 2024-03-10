import java.io.Serializable;
import java.sql.Timestamp;

public class Hold implements Serializable {
    private static final long serialVersionUID =  8271963769266110398L;
    private int id;
    private Timestamp start_time;
    private Timestamp end_time;
    private int patron_id;
    private int book_copy_id;

    public Hold() {
    }

    public Hold(int id, Timestamp start_time, Timestamp end_time, int patron_id, int book_copy_id) {
        this.id = id;
        this.start_time = start_time;
        this.end_time = end_time;
        this.patron_id = patron_id;
        this.book_copy_id = book_copy_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getStart_time() {
        return start_time;
    }

    public void setStart_time(Timestamp start_time) {
        this.start_time = start_time;
    }

    public Timestamp getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Timestamp end_time) {
        this.end_time = end_time;
    }

    public int getPatron_id() {
        return patron_id;
    }

    public void setPatron_id(int patron_id) {
        this.patron_id = patron_id;
    }

    public int getBook_copy_id() {
        return book_copy_id;
    }

    public void setBook_copy_id(int book_copy_id) {
        this.book_copy_id = book_copy_id;
    }
}


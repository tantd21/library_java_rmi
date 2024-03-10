import java.io.Serializable;
import java.sql.Timestamp;

public class Notification implements Serializable {
    private static final long serialVersionUID =  8271963769266110398L;
    private int id;
    private Timestamp send_at;
    private String message;
    private int patron_id;

    public Notification() {
    }

    public Notification(int id, Timestamp send_at, String message, int patron_id) {
        this.id = id;
        this.send_at = send_at;
        this.message = message;
        this.patron_id = patron_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getSend_at() {
        return send_at;
    }

    public void setSend_at(Timestamp send_at) {
        this.send_at = send_at;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPatron_id() {
        return patron_id;
    }

    public void setPatron_id(int patron_id) {
        this.patron_id = patron_id;
    }
}

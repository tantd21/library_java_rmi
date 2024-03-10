import java.io.Serializable;

public class Patron implements Serializable {
    private static final long serialVersionUID =  8271963769266110398L;
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private boolean status;

    public Patron(Object[] data) {
        this.id = (int) data[0];
        this.first_name = (String) data[1];
        this.last_name = (String) data[2];
        this.email = (String) data[3];
        this.status = (boolean) data[4].toString().equals("Available");
    }

    public Patron(int id, String first_name, String last_name, String email, String password, boolean status) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.status = status;
    }

    public Patron() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.email;
    }
}

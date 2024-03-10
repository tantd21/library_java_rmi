import java.io.Serializable;
import java.sql.Timestamp;

public class Log implements Serializable {
    private static final long serialVersionUID =  8271963769266110398L;
    private int id;
    private String ip;
    private String username;
    private String table_name;
    private int col_id;
    private Timestamp time_start;
    private Timestamp time_end;

    public Log() {
    }

    public Log(String ip, String username, String table_name, int col_id, Timestamp time_start) {
        this.ip = ip;
        this.username = username;
        this.table_name = table_name;
        this.col_id = col_id;
        this.time_start = time_start;
    }

    public Log(int id, Timestamp time_end) {
        this.id = id;
        this.time_end = time_end;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public int getCol_id() {
        return col_id;
    }

    public void setCol_id(int col_id) {
        this.col_id = col_id;
    }

    public Timestamp getTime_start() {
        return time_start;
    }

    public void setTime_start(Timestamp time_start) {
        this.time_start = time_start;
    }

    public Timestamp getTime_end() {
        return time_end;
    }

    public void setTime_end(Timestamp time_end) {
        this.time_end = time_end;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", username='" + username + '\'' +
                ", table_name='" + table_name + '\'' +
                ", col_id=" + col_id +
                ", time_start=" + time_start +
                ", time_end=" + time_end +
                '}';
    }
}

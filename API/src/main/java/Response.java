import java.io.Serializable;

public class Response implements Serializable {
    private static final long serialVersionUID =  8271963769266110398L;
    /*
    * status = 200 => success
    * status = 100 => error
    * */
    private int status;
    private Object data;

    public Response() {
    }

    public Response(int status, Object data) {
        this.status = status;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

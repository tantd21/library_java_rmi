import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.sql.SQLException;

import static java.rmi.registry.LocateRegistry.getRegistry;

public class RMIServer {
    public static void main(String[] args) {
        try {
            System.setProperty("java.rmi.server.hostname", Config.IP_SERVER);
            LibraryImpl server = new LibraryImpl();
            LocateRegistry.createRegistry(Config.PORT_SERVER);

            // Đăng ký đối tượng này với rmiregistry
            Naming.rebind("rmi://" + Config.IP_SERVER + ":" + Config.PORT_SERVER + "/api", server);


            System.out.println(">>>>> INFO: RMI Server started !");
            System.out.println("rmi://" + Config.IP_SERVER + ":" + Config.PORT_SERVER + "/api");


        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}

import javax.swing.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientController {
    LibraryRemote libraryRemote;
    ClientInterface callbackObj;
    public ClientController() {
        try {
            System.setProperty("java.rmi.server.hostname", Config.IP_SERVER);
            libraryRemote = (LibraryRemote) Naming.lookup("rmi://" + Config.IP_SERVER + ":" + Config.PORT_SERVER + "/api");

            // register for callback
        } catch (NotBoundException e) {
            JOptionPane.showMessageDialog(null, "Error to Connect with Server", "Error", JOptionPane.WARNING_MESSAGE);
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(null, "Error to Connect with Server", "Error", JOptionPane.WARNING_MESSAGE);
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            JOptionPane.showMessageDialog(null, "Error to Connect with Server", "Error", JOptionPane.WARNING_MESSAGE);
            throw new RuntimeException(e);
        }
    }
    public ClientController(ClientInterface client) {
        try {
            System.setProperty("java.rmi.server.hostname", Config.IP_SERVER);
            libraryRemote = (LibraryRemote) Naming.lookup("rmi://" + Config.IP_SERVER + ":" + Config.PORT_SERVER + "/api");

            // register for callback
            callbackObj = client;
            libraryRemote.registerForCallback(callbackObj);
            System.out.println("Registered for callback.");
        } catch (NotBoundException e) {
            JOptionPane.showMessageDialog(null, "Error to Connect with Server", "Error", JOptionPane.WARNING_MESSAGE);
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(null, "Error to Connect with Server", "Error", JOptionPane.WARNING_MESSAGE);
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            JOptionPane.showMessageDialog(null, "Error to Connect with Server", "Error", JOptionPane.WARNING_MESSAGE);
            throw new RuntimeException(e);
        }
    }

    // Login - Register
    public Response loginClient(Patron patron) throws RemoteException {
        return libraryRemote.loginClient(patron);
    }
    public Response getNotificationByPatronId(int patron_id) throws RemoteException {
        return libraryRemote.getNotification(patron_id);
    }
    public Response getBookForSearchController() throws RemoteException {
        Response response = libraryRemote.getBooksForSearch();
        return response;
    }
    public Response getCheckoutsClient(int patron_id) throws RemoteException {
        Response response = libraryRemote.getCheckoutsClient(patron_id);
        return response;
    }
    public Response clientBorrowBookCopy(Checkout checkout) throws RemoteException {
        Response response = libraryRemote.createCheckout(checkout, false);
        return response;
    }

    public void exit() throws RemoteException {
        libraryRemote.unregisterForCallback(callbackObj);
    }

    public Response clientReturnBookCopy(int checkout_id) throws RemoteException {
        Response response = libraryRemote.deleteCheckout(checkout_id,false);
        return response;
    }
}

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ManagerController {
    LibraryRemote libraryRemote;
    ClientInterface callbackObj;

    public ManagerController() {
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

    public ManagerController(ClientInterface client) {
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

    // CRUD - Book
    public Response getBooksController() throws RemoteException {
        Response response = libraryRemote.getBooks();
        return response;

    }
    public Response createBookController(Book book, int author_id) throws RemoteException, SQLException {
        Response response = libraryRemote.createBook(book, author_id,false);
        return response;
    }

    public Response deleteBookController(int book_id) throws RemoteException {
        Response res = libraryRemote.deleteBook(book_id,false);
        return res;
    }

    public Response updateBookController(Book book, int authorId) throws RemoteException {
        Response response = libraryRemote.updateBook(book, authorId,false);
        return response;
    }

    //

    // CRUD - Author
    public Response getAuthorsController() throws RemoteException {
        return libraryRemote.getAuthors();
    }

    public Response createAuthorController(Author author) throws RemoteException {
        return libraryRemote.createAuthor(author,false);
    }

    public Response updateAuthorController(Author author) throws RemoteException {
        return libraryRemote.updateAuthor(author,false);
    }

    public Response deleteAuthorController(int authorId) throws RemoteException {
        return libraryRemote.deleteAuthor(authorId,false);
    }

    //

    // CRUD - Category
    public Response getCategoriesController() throws RemoteException {
        return libraryRemote.getCategories();
    }

    public Response createCategoryController(Category category) throws RemoteException {
        return libraryRemote.createCategory(category,false);
    }

    public Response updateCategoryController(Category category) throws RemoteException {
        return libraryRemote.updateCategory(category,false);
    }

    public Response deleteCategoryController(int categoryId) throws RemoteException {
        return libraryRemote.deleteCategory(categoryId,false);
    }

    // CRUD - Published
    public Response getPublishedController() throws RemoteException {
        return libraryRemote.getPublished();
    }

    // CRUD - Book Copy
    public Response getBooksCopyController() throws RemoteException {
        return libraryRemote.getBooksCopy();
    }
    public Response createBookCopyController(BookCopy bookCopy) throws RemoteException {
        return libraryRemote.createBookCopy(bookCopy,false);
    }

    // CRUD - Hold
    public Response createHoldController(Hold hold) throws RemoteException {
        return libraryRemote.createHold(hold, false);
    }

    public Response getHoldsController() throws RemoteException {
        return libraryRemote.getHolds();
    }
    public Response updateHoldController(Hold hold) throws RemoteException {
        return libraryRemote.updateHold(hold,false);
    }

    public Response deleteHoldController(int holdId) throws RemoteException {
        return libraryRemote.deleteHold(holdId,false);
    }
    // CRUD - Checkout
    public Response getCheckoutsController() throws RemoteException {
        return libraryRemote.getCheckouts();
    }
    public Response updateCheckoutController(Checkout checkout) throws RemoteException{
        return libraryRemote.updateCheckout(checkout,false);
    }

    public Response createCheckoutController(Checkout checkout) throws RemoteException {
        return  libraryRemote.createCheckout(checkout, false);
    }
    public Response deleteCheckoutController(int checkoutId) throws RemoteException {
        return libraryRemote.deleteCheckout(checkoutId, false);
    }



    // CRUD - Notification
    public Response getNotificationsController() throws RemoteException {
        return libraryRemote.getNotifications();
    }
    public Response createNotificationController(Notification notification) throws RemoteException {
        return libraryRemote.createNotification(notification,false);
    }

    public Response updateNotificationController(Notification notification) throws RemoteException {
        return libraryRemote.updateNotification(notification,false);
    }

    public Response deleteNotificationController(int notificationId) throws RemoteException {
        return libraryRemote.deleteNotification(notificationId,false);
    }


    // CRUD - Patron Account
    public Response getPatronsController() throws RemoteException {
        return libraryRemote.getPatrons();
    }
    public Response updatePatronController(Patron patron) throws RemoteException {
        return libraryRemote.updatePatron(patron, false);
    }
    public Response createPatronController(Patron patron) throws RemoteException {
        return libraryRemote.createPatron(patron, false);
    }
    public Response deleteControllerPatron(int patronId) throws RemoteException {
        return libraryRemote.deletePatron(patronId,false);
    }
    // CRUD - History
    public Response getHistoryController() throws RemoteException {
        return libraryRemote.getHistory();
    }

    // Log
    public int createLog(Log log) throws RemoteException {
        return libraryRemote.createLog(log,false);
    }

    public void updateLog(Log log) throws RemoteException {
        libraryRemote.updateLog(log,false);
    }

    public void deleteLog(int id) throws RemoteException {
        libraryRemote.deleteLog(id,false);
    }

    public void exit() throws RemoteException {
        libraryRemote.unregisterForCallback(callbackObj);
    }

    public boolean checkLog(String table_name, int col_id) throws RemoteException {
        return libraryRemote.checkLog(table_name, col_id);
    }

    // Common
    private static List<Category> getTableDataCategory(DefaultTableModel model) {
        List<Category> dataList = new ArrayList<>();

        int rowCount = model.getRowCount();
        int columnCount = model.getColumnCount();

        for (int row = 0; row < rowCount; row++) {
            Object[] rowData = new Object[columnCount];
            for (int column = 0; column < columnCount; column++) {
                rowData[column] = model.getValueAt(row, column);
            }
            Category category = new Category(rowData);
            dataList.add(category);
        }

        return dataList;
    }
    private static List<Published> getTableDataPublished(DefaultTableModel model) {
        List<Published> dataList = new ArrayList<>();

        int rowCount = model.getRowCount();
        int columnCount = model.getColumnCount();

        for (int row = 0; row < rowCount; row++) {
            Object[] rowData = new Object[columnCount];
            for (int column = 0; column < columnCount; column++) {
                rowData[column] = model.getValueAt(row, column);
            }
            Published published = new Published(rowData);
            dataList.add(published);
        }

        return dataList;
    }
    private static List<Author> getTableDataAuthor(DefaultTableModel model) {
        List<Author> dataList = new ArrayList<>();

        int rowCount = model.getRowCount();
        int columnCount = model.getColumnCount();

        for (int row = 0; row < rowCount; row++) {
            Object[] rowData = new Object[columnCount];
            for (int column = 0; column < columnCount; column++) {
                rowData[column] = model.getValueAt(row, column);
            }
            Author author = new Author(rowData);
            dataList.add(author);
        }

        return dataList;
    }
    private static List<BookCopy> getTableDataBookCopy(DefaultTableModel model) {
        List<BookCopy> dataList = new ArrayList<>();

        int rowCount = model.getRowCount();
        int columnCount = model.getColumnCount();

        for (int row = 0; row < rowCount; row++) {
            Object[] rowData = new Object[columnCount];
            for (int column = 0; column < columnCount; column++) {
                rowData[column] = model.getValueAt(row, column);
            }
            BookCopy bookcopy = new BookCopy(rowData);
            dataList.add(bookcopy);
        }

        return dataList;
    }
    private static List<Book> getTableDataBooks(DefaultTableModel model) {
        List<Book> dataList = new ArrayList<>();

        int rowCount = model.getRowCount();
        int columnCount = model.getColumnCount();

        for (int row = 0; row < rowCount; row++) {
            Object[] rowData = new Object[columnCount];
            for (int column = 0; column < columnCount; column++) {
                rowData[column] = model.getValueAt(row, column);
            }
            Book book = new Book(rowData);
            dataList.add(book);
        }

        return dataList;
    }
    private static List<Patron> getTableDataPatron(DefaultTableModel model) {
        List<Patron> dataList = new ArrayList<>();

        int rowCount = model.getRowCount();
        int columnCount = model.getColumnCount();

        for (int row = 0; row < rowCount; row++) {
            Object[] rowData = new Object[columnCount];
            for (int column = 0; column < columnCount; column++) {
                rowData[column] = model.getValueAt(row, column);
            }
            Patron patron = new Patron(rowData);
            dataList.add(patron);
        }

        return dataList;
    }

    public Response getDataComboBoxCategories() throws RemoteException {
        Response response = libraryRemote.getCategories();
        if (response.getStatus() == 100) {
            return response;
        }

        DefaultTableModel defaultTableModel = (DefaultTableModel) response.getData();
        List<Category> categoryList = getTableDataCategory(defaultTableModel);

        return new Response(200, categoryList);

    }
    public Response getDataComboBoxPublished() throws RemoteException {
        Response response = libraryRemote.getPublished();
        if (response.getStatus() == 100) {
            return response;
        }

        DefaultTableModel defaultTableModel = (DefaultTableModel) response.getData();
        List<Published> publishedList = getTableDataPublished(defaultTableModel);

        return new Response(200, publishedList);

    }
    public Response getDataComboBoxAuthors() throws RemoteException {
        Response response = libraryRemote.getAuthors();
        if (response.getStatus() == 100) {
            return response;
        }

        DefaultTableModel defaultTableModel = (DefaultTableModel) response.getData();
        List<Author> authorsList = getTableDataAuthor(defaultTableModel);

        return new Response(200, authorsList);

    }
    public Response getDataComboBoxBooks() throws RemoteException {
        Response response = libraryRemote.getBooks();
        if (response.getStatus() == 100) {
            return response;
        }

        DefaultTableModel defaultTableModel = (DefaultTableModel) response.getData();
        List<Book> booksList = getTableDataBooks(defaultTableModel);

        return new Response(200, booksList);

    }
    public Response getDataComboBoxBookCopies() throws RemoteException {
        Response response = libraryRemote.getBooksCopy();
        if (response.getStatus() == 100) {
            return response;
        }

        DefaultTableModel defaultTableModel = (DefaultTableModel) response.getData();
        List<BookCopy> bookCopiesList = getTableDataBookCopy(defaultTableModel);

        return new Response(200, bookCopiesList);

    }
    public Response getDataComboBoxPatrons() throws RemoteException {
        Response response = libraryRemote.getPatrons();
        if (response.getStatus() == 100) {
            return response;
        }

        DefaultTableModel defaultTableModel = (DefaultTableModel) response.getData();
        List<Patron> patronsList = getTableDataPatron(defaultTableModel);

        return new Response(200, patronsList);

    }



}

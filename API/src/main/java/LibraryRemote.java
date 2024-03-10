import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LibraryRemote extends Remote {

    public void registerForCallback(
            ClientInterface callbackClientObject
    ) throws java.rmi.RemoteException;

    public void unregisterForCallback(
            ClientInterface callbackClientObject)
            throws java.rmi.RemoteException;

    // Manage
    // Return DefaultTableModel
    public Response getBooks() throws RemoteException;
    public Response getBooksForSearch() throws RemoteException;
    public Response getCheckoutsClient(int patron_id) throws RemoteException;
    public Response getAuthors() throws RemoteException;

    public Response getBooksCopy() throws RemoteException;

    public Response getCategories() throws RemoteException;

    public Response getNotifications() throws RemoteException;

    public Response getPublished() throws RemoteException;

    public Response getHolds() throws RemoteException;

    public Response getCheckouts() throws RemoteException;

    public Response getPatrons() throws RemoteException;

    public Response getHistory() throws RemoteException;
    //

    // CRUD Book
    public Response createBook(Book book, int author_id, boolean isCallFromSever) throws RemoteException;

    public Response getBook(int id) throws RemoteException;

    public Response updateBook(Book book, int author_id, boolean isCallFromSever) throws RemoteException;

    public Response deleteBook(int id, boolean isCallFromSever) throws RemoteException;

    //
    // CRUD Author
    public Response createAuthor(Author author, boolean isCallFromSever) throws RemoteException;

    public Response getAuthor(int id) throws RemoteException;

    public Response updateAuthor(Author author, boolean isCallFromSever) throws RemoteException;

    public Response deleteAuthor(int id, boolean isCallFromSever) throws RemoteException;

    //
    // CRUD Category
    public Response createCategory(Category category, boolean isCallFromSever) throws RemoteException;

    public Response getCategory(int id) throws RemoteException;

    public Response updateCategory(Category category, boolean isCallFromSever) throws RemoteException;

    public Response deleteCategory(int id, boolean isCallFromSever) throws RemoteException;

    //
    // CRUD Published
    public Response createPublished(Published published, boolean isCallFromSever) throws RemoteException;

    public Response getPublished(int id) throws RemoteException;

    public Response updatePublished(Published published, boolean isCallFromSever) throws RemoteException;

    public Response deletePublished(int id, boolean isCallFromSever) throws RemoteException;

    //
    // CRUD BookCopy
    public Response createBookCopy(BookCopy bookCopy, boolean isCallFromSever) throws RemoteException;

    public Response getBookCopy(int id) throws RemoteException;

    public Response updateBookCopy(BookCopy bookCopy, boolean isCallFromSever) throws RemoteException;

    public Response deleteBookCopy(int id, boolean isCallFromSever) throws RemoteException;

    //
    // CRUD Patron
    public Response createPatron(Patron patron, boolean isCallFromSever) throws RemoteException;

    public Response getPatron(int id) throws RemoteException;

    public Response updatePatron(Patron patron, boolean isCallFromSever) throws RemoteException;

    public Response deletePatron(int id, boolean isCallFromSever) throws RemoteException;

    //
    // CRUD Hold
    public Response createHold(Hold hold, boolean isCallFromSever) throws RemoteException;

    public Response getHold(int id) throws RemoteException;

    public Response updateHold(Hold hold, boolean isCallFromSever) throws RemoteException;

    public Response deleteHold(int id, boolean isCallFromSever) throws RemoteException;

    //
    // CRUD Checkout
    public Response createCheckout(Checkout checkout, boolean isCallFromSever) throws RemoteException;

    public Response getCheckout(int id) throws RemoteException;

    public Response updateCheckout(Checkout checkout, boolean isCallFromSever) throws RemoteException;

    public Response deleteCheckout(int id, boolean isCallFromSever) throws RemoteException;

    //
    // CRUD Notification
    public Response createNotification(Notification notification, boolean isCallFromSever) throws RemoteException;

    public Response getNotification(int id) throws RemoteException;

    public Response updateNotification(Notification notification, boolean isCallFromSever) throws RemoteException;

    public Response deleteNotification(int id, boolean isCallFromSever) throws RemoteException;

    //
    // Log - Block
    public int createLog(Log log, boolean isCallFromSever) throws RemoteException;
    public void updateLog(Log log, boolean isCallFromSever) throws RemoteException;
    public void deleteLog(int id, boolean isCallFromSever) throws RemoteException;
    public void deleteAllLog(boolean isCallFromSever) throws RemoteException;
    public boolean checkLog(String table_name, int col_id) throws RemoteException;

    // login - register
    public Response loginClient(Patron patron) throws RemoteException;
    public Response registerClient(Patron patron) throws RemoteException;



}

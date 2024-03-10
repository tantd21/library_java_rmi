import javax.swing.table.DefaultTableModel;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;

public class LibraryImpl extends UnicastRemoteObject implements LibraryRemote {
    InetAddress localhost = InetAddress.getLocalHost();
    String ipAddress = localhost.getHostAddress();
    Connection conn = new Connect().getConnect();
    Statement stm = conn.createStatement();
    PreparedStatement pst;
    ResultSet rst;
    ResultSetMetaData rstmeta;
    Vector vTitle = new Vector();
    Vector vData = new Vector();
    Vector clientList;


    public LibraryImpl() throws SQLException, RemoteException, UnknownHostException {
        super();
        clientList = new Vector();
    }

    public synchronized void registerForCallback(
            ClientInterface callbackClientObject)
            throws java.rmi.RemoteException {
        // store the callback object into the vector
        if (!(clientList.contains(callbackClientObject))) {
            clientList.addElement(callbackClientObject);
            System.out.println("Registered new client ");
        } // end if
    }

    // This remote method allows an object client to
// cancel its registration for callback
// @param id is an ID for the client; to be used by
// the server to uniquely identify the registered client.
    public synchronized void unregisterForCallback(
            ClientInterface callbackClientObject)
            throws java.rmi.RemoteException {
        if (clientList.removeElement(callbackClientObject)) {
            System.out.println("Unregistered client ");
        } else {
            System.out.println(
                    "unregister: client wasn't registered.");
        }
    }

    private synchronized void doCallbacks(NOTIFY notify) throws java.rmi.RemoteException {
        // make callback to each registered client
        System.out.println(
                "**************************************\n"
                        + "Callbacks initiated ---");
        deleteAllLog(true);
        for (int i = 0; i < clientList.size(); i++) {
            System.out.println("doing " + (i + 1) + "-th callback\n");
            // convert the vector object to a callback object
            ClientInterface nextClient =
                    (ClientInterface) clientList.elementAt(i);
            // invoke the callback method
            nextClient.notify(notify);
            System.out.println("SERVER send CLIENT: " + notify.toString());
        }// end for
        System.out.println("********************************\n" +
                "Server completed callbacks ---");
    } // doCallbacks

    // Manage
    @Override
    public Response getBooks() throws RemoteException {

        try {
            vTitle.clear();
            vData.clear();

            String query = "SELECT book.id, book.title, category.name AS category, author.name AS author " +
                    "FROM book " +
                    "INNER JOIN book_author ON book.id = book_author.book_id " +
                    "INNER JOIN author ON book_author.author_id = author.id " +
                    "INNER JOIN category ON book.category_id = category.id order by book.id";

            rst = stm.executeQuery(query);

            String[] title = new String[]{
                    "Book ID", "Book Title", "Category", "Author"
            };
            Collections.addAll(vTitle, title);
            while (rst.next()) {
                Vector row = new Vector();

                row.add(rst.getInt("id"));
                row.add(rst.getString("title"));
                row.add(rst.getString("category"));
                row.add(rst.getString("author"));
                vData.add(row);
            }
            rst.close();
            return new Response(200, new DefaultTableModel(vData, vTitle));
        } catch (SQLException e) {
            System.out.println("!!!---Error: " + e);
            return new Response(100, null);

        }
    }

    @Override
    public Response getBooksForSearch() throws RemoteException {
        try {
            vTitle.clear();
            vData.clear();

            String query = "SELECT\n" +
                    "book_copy.id,\n " +
                    "  book.title,\n" +
                    "  category.name as 'category',\n" +
                    "  author.name as 'author',\n" +
                    "  published.name as 'published',\n" +
                    "  book_copy.year_published\n" +
                    "FROM\n" +
                    "  book\n" +
                    "  INNER JOIN book_author ON book.id = book_author.book_id\n" +
                    "  INNER JOIN author ON book_author.author_id = author.id\n" +
                    "  INNER JOIN category ON book.category_id = category.id\n" +
                    "  INNER JOIN book_copy ON book.id = book_copy.book_id\n" +
                    "  INNER JOIN published ON book_copy.published_id = published.id;\n";

            rst = stm.executeQuery(query);

            String[] title = new String[]{
                    "ID", "Book Title", "Category", "Author","Published","Year"
            };
            Collections.addAll(vTitle, title);
            while (rst.next()) {
                Vector row = new Vector();

                row.add(rst.getInt("id"));
                row.add(rst.getString("title"));
                row.add(rst.getString("category"));
                row.add(rst.getString("author"));
                row.add(rst.getString("published"));
                row.add(rst.getString("year_published"));
                vData.add(row);
            }
            rst.close();
            return new Response(200, new DefaultTableModel(vData, vTitle));
        } catch (SQLException e) {
            System.out.println("!!!---Error: " + e);
            return new Response(100, null);

        }
    }

    @Override
    public Response getCheckoutsClient(int patron_id) throws RemoteException {
        try {
            vTitle.clear();
            vData.clear();

            String query = "SELECT " +
                    "c.id, " +
                    "b.title AS book_title, " +
                    "c.start_time AS borrowed_date, " +
                    "c.end_time AS returned_date, " +
                    "c.is_returned AS borrow_status " +
                    "FROM checkout c " +
                    "INNER JOIN book_copy bc ON c.book_copy_id = bc.id " +
                    "INNER JOIN book b ON bc.book_id = b.id " +
                    "WHERE c.patron_id = ?";
            pst =  conn.prepareStatement(query);
            pst.setInt(1,patron_id);
            rst = pst.executeQuery();

            String[] title = new String[]{
                    "ID", "Book Title", "Borrowed Date", "Returned Date","Status"
            };
            Collections.addAll(vTitle, title);
            while (rst.next()) {
                Vector row = new Vector();

                row.add(rst.getInt("id"));
                row.add(rst.getString("book_title"));
                row.add(rst.getString("borrowed_date"));
                row.add(rst.getString("returned_date"));
                row.add(rst.getBoolean("borrow_status") ? "Yes" : "No");
                vData.add(row);
            }
            rst.close();
            return new Response(200, new DefaultTableModel(vData, vTitle));
        } catch (SQLException e) {
            System.out.println("!!!---Error: " + e);
            return new Response(100, null);

        }
    }

    @Override
    public Response getAuthors() throws RemoteException {
        try {
            vTitle.clear();
            vData.clear();

            String query = "SELECT a.id, a.name " +
                    "FROM author a ";

            rst = stm.executeQuery(query);

            String[] title = new String[]{
                    "Author ID", "Author Name"
            };
            Collections.addAll(vTitle, title);
            while (rst.next()) {
                Vector row = new Vector();

                row.add(rst.getInt("id"));
                row.add(rst.getString("name"));
                vData.add(row);
            }
            rst.close();
            return new Response(200, new DefaultTableModel(vData, vTitle));
        } catch (SQLException e) {
            System.out.println("!!!---Error: " + e);
            return new Response(100, null);

        }
    }

    @Override
    public Response getCategories() throws RemoteException {
        try {
            vTitle.clear();
            vData.clear();

            String query = "SELECT c.id, c.name " +
                    "FROM category c ";

            rst = stm.executeQuery(query);

            String[] title = new String[]{
                    "Category ID", "Category Name"
            };
            Collections.addAll(vTitle, title);
            while (rst.next()) {
                Vector row = new Vector();
                row.add(rst.getInt("id"));
                row.add(rst.getString("name"));
                vData.add(row);
            }
            rst.close();
            return new Response(200, new DefaultTableModel(vData, vTitle));
        } catch (SQLException e) {
            System.out.println("!!!---Error: " + e);
            return new Response(100, null);

        }
    }

    @Override
    public Response getPublished() throws RemoteException {
        try {
            vTitle.clear();
            vData.clear();

            String query = "SELECT p.id, p.name " +
                    "FROM published p ";

            rst = stm.executeQuery(query);

            String[] title = new String[]{
                    "Published ID", "Published Name"
            };
            Collections.addAll(vTitle, title);
            while (rst.next()) {
                Vector row = new Vector();

                row.add(rst.getInt("id"));
                row.add(rst.getString("name"));
                vData.add(row);
            }
            rst.close();
            return new Response(200, new DefaultTableModel(vData, vTitle));
        } catch (SQLException e) {
            System.out.println("!!!---Error: " + e);
            return new Response(100, null);

        }
    }

    @Override
    public Response getBooksCopy() throws RemoteException {
        try {
            vTitle.clear();
            vData.clear();

            String query = "SELECT bc.id, b.title, bc.year_published, p.name " +
                    "FROM book_copy bc " +
                    "INNER JOIN book b ON bc.book_id = b.id " +
                    "INNER JOIN published p ON bc.published_id = p.id ";

            rst = stm.executeQuery(query);

            String[] title = new String[]{
                    "Book Copy ID", "Book Title", "Year Public", "Published Name"
            };
            Collections.addAll(vTitle, title);
            while (rst.next()) {
                Vector row = new Vector();

                row.add(rst.getInt("id"));
                row.add(rst.getString("title"));
                row.add(rst.getInt("year_published"));
                row.add(rst.getString("name"));
                vData.add(row);
            }
            rst.close();
            return new Response(200, new DefaultTableModel(vData, vTitle));
        } catch (SQLException e) {
            System.out.println("!!!---Error: " + e);
            return new Response(100, e.toString());

        }
    }

    @Override
    public Response getHolds() throws RemoteException {
        try {
            vTitle.clear();
            vData.clear();

            String query = "SELECT h.id, pa.email, CONCAT(pa.first_name, ' ', pa.last_name) as fullname, b.title , h.start_time, h.end_time " +
                    "FROM hold as h " +
                    "INNER JOIN patron_account as pa ON pa.id = h.patron_id " +
                    "INNER JOIN book_copy as bc ON bc.id = h.book_copy_id " +
                    "INNER JOIN book as b ON b.id = bc.book_id " +
                    "ORDER BY h.id DESC ";

            rst = stm.executeQuery(query);

            String[] title = new String[]{
                    "Hold ID", "Patron Email", "Patron Name", "Book Title", "Time start", "Time end"
            };
            Collections.addAll(vTitle, title);
            while (rst.next()) {
                Vector row = new Vector();

                row.add(rst.getInt("id"));
                row.add(rst.getString("email"));
                row.add(rst.getString("fullname"));
                row.add(rst.getString("title"));
                row.add(rst.getTimestamp("start_time"));
                row.add(rst.getTimestamp("end_time"));
                vData.add(row);
            }
            rst.close();
            return new Response(200, new DefaultTableModel(vData, vTitle));
        } catch (SQLException e) {
            System.out.println("!!!---Error: " + e);
            return new Response(100, null);

        }
    }

    @Override
    public Response getCheckouts() throws RemoteException {
        try {
            vTitle.clear();
            vData.clear();

            String query = "SELECT c.id, pa.email, CONCAT(pa.first_name, ' ', pa.last_name) as fullname, b.title , c.start_time, c.end_time ,c.is_returned " +
                    "FROM checkout as c " +
                    "INNER JOIN patron_account as pa ON pa.id = c.patron_id " +
                    "INNER JOIN book_copy as bc ON bc.id = c.book_copy_id " +
                    "INNER JOIN book as b ON b.id = bc.book_id " +
                    "ORDER BY c.id DESC ";

            rst = stm.executeQuery(query);

            String[] title = new String[]{
                    "ID", "Patron Email", "Patron Name", "Book Title", "Time start", "Time end", "Approved"
            };
            Collections.addAll(vTitle, title);
            while (rst.next()) {
                Vector row = new Vector();

                row.add(rst.getInt("id"));
                row.add(rst.getString("email"));
                row.add(rst.getString("fullname"));
                row.add(rst.getString("title"));
                row.add(rst.getTimestamp("start_time"));
                row.add(rst.getTimestamp("end_time"));
                row.add(rst.getBoolean("is_returned") ? "Yes" : "No");

                vData.add(row);
            }
            rst.close();
            return new Response(200, new DefaultTableModel(vData, vTitle));
        } catch (SQLException e) {
            System.out.println("!!!---Error: " + e);
            return new Response(100, null);

        }
    }

    @Override
    public Response getNotifications() throws RemoteException {
        try {
            vTitle.clear();
            vData.clear();

            String query = "SELECT n.id, n.sent_at, n.message, pa.email " +
                    "FROM notification as n " +
                    "INNER JOIN patron_account as pa ON pa.id = n.patron_id ORDER BY id DESC ";

            rst = stm.executeQuery(query);

            String[] title = new String[]{
                    "Notification ID", "Sent At", "Message", "Patron Email"
            };
            Collections.addAll(vTitle, title);
            while (rst.next()) {
                Vector row = new Vector();

                row.add(rst.getInt("id"));
                row.add(rst.getTimestamp("sent_at"));
                row.add(rst.getString("message"));
                row.add(rst.getString("email"));
                vData.add(row);
            }
            rst.close();
            return new Response(200, new DefaultTableModel(vData, vTitle));
        } catch (SQLException e) {
            System.out.println("!!!---Error: " + e);
            return new Response(100, null);

        }
    }

    @Override
    public Response getPatrons() throws RemoteException {
        try {
            vTitle.clear();
            vData.clear();

            String query = "SELECT * " +
                    "FROM patron_account p ";

            rst = stm.executeQuery(query);

            String[] title = new String[]{
                    "Patron ID", "First Name", "Last Name", "Email", "Status"
            };
            Collections.addAll(vTitle, title);
            while (rst.next()) {
                Vector row = new Vector();

                row.add(rst.getInt("id"));
                row.add(rst.getString("first_name"));
                row.add(rst.getString("last_name"));
                row.add(rst.getString("email"));
                row.add(rst.getBoolean("status") ? "Available" : "Unavailable");
                vData.add(row);
            }
            rst.close();
            return new Response(200, new DefaultTableModel(vData, vTitle));
        } catch (SQLException e) {
            System.out.println("!!!---Error: " + e);
            return new Response(100, null);

        }
    }

    @Override
    public Response getHistory() throws RemoteException {
        try {
            vTitle.clear();
            vData.clear();

            String query = "SELECT * " +
                    "FROM db_log ORDER BY id DESC ";

            rst = stm.executeQuery(query);

            String[] title = new String[]{
                    "Table Name", "Action", "Time"
            };
            Collections.addAll(vTitle, title);
            while (rst.next()) {
                Vector row = new Vector();

                row.add(rst.getString("table_name"));
                row.add(rst.getString("action"));
                row.add(rst.getTime("timestamp"));
                vData.add(row);
            }
            rst.close();
            return new Response(200, new DefaultTableModel(vData, vTitle));
        } catch (SQLException e) {
            System.out.println("!!!---Error: " + e);
            return new Response(100, null);

        }
    }

    // CRUD - Book
    @Override
    public Response createBook(Book book, int author_id, boolean isCallFromSever) throws RemoteException {
        try {
            if (!isCallFromSever && !(server2() == null)) {
                server2().createBook(book, author_id, true);
            }

            // Thêm sách mới vào bảng book
            String insertBookQuery = "INSERT INTO book (title, category_id) VALUES (?, ?)";
            PreparedStatement insertBookStatement = conn.prepareStatement(insertBookQuery, Statement.RETURN_GENERATED_KEYS);
            insertBookStatement.setString(1, book.getTitle());
            insertBookStatement.setInt(2, book.getCategory_id());
            insertBookStatement.executeUpdate();

            // Lấy giá trị id của sách vừa được thêm vào
            ResultSet generatedKeys = insertBookStatement.getGeneratedKeys();
            int bookId;
            if (generatedKeys.next()) {
                bookId = generatedKeys.getInt(1);
            } else {
                return new Response(100, "Failed to get the generated book ID.");
            }

            // Thêm thông tin tác giả vào bảng book_author
            String insertBookAuthorQuery = "INSERT INTO book_author (book_id, author_id) VALUES (?, ?)";
            PreparedStatement insertBookAuthorStatement = conn.prepareStatement(insertBookAuthorQuery);
            insertBookAuthorStatement.setInt(1, bookId);
            insertBookAuthorStatement.setInt(2, author_id);
            insertBookAuthorStatement.executeUpdate();

            doCallbacks(NOTIFY.UPDATE_BOOK);

            return new Response(200, "Created new book successfully!");
        } catch (Exception e) {
            return new Response(100, e.getMessage());
        }
    }

    @Override
    public Response getBook(int id) throws RemoteException {
        return null;
    }

    @Override
    public Response updateBook(Book book, int author_id, boolean isCallFromSever) throws RemoteException {
        try {
            if (!isCallFromSever && !(server2() == null)) {
                server2().updateBook(book, author_id, true);
            }

            // Cập nhật sách theo id
            String insertBookQuery = "UPDATE book SET title = ?, category_id = ? WHERE id = ?";
            PreparedStatement insertBookStatement = conn.prepareStatement(insertBookQuery, Statement.RETURN_GENERATED_KEYS);
            insertBookStatement.setString(1, book.getTitle());
            insertBookStatement.setInt(2, book.getCategory_id());
            insertBookStatement.setInt(3, book.getId());
            insertBookStatement.executeUpdate();


            String insertBookAuthorQuery = "UPDATE book_author SET author_id = ? WHERE book_id = ?";
            PreparedStatement insertBookAuthorStatement = conn.prepareStatement(insertBookAuthorQuery);
            insertBookAuthorStatement.setInt(1, author_id);
            insertBookAuthorStatement.setInt(2, book.getId());
            insertBookAuthorStatement.executeUpdate();

            doCallbacks(NOTIFY.UPDATE_BOOK);

            return new Response(200, "Updated book successfully!");
        } catch (Exception e) {
            return new Response(100, e.getMessage());
        }
    }

    @Override
    public Response deleteBook(int id, boolean isCallFromSever) throws RemoteException {
        try {
            if (!isCallFromSever && !(server2() == null)) {
                server2().deleteBook(id, true);
            }

            // Xóa các bản sao sách từ bảng book_copy
            String deleteBookCopyQuery = "DELETE FROM book_copy WHERE book_id = ?";
            PreparedStatement deleteBookCopyStatement = conn.prepareStatement(deleteBookCopyQuery);
            deleteBookCopyStatement.setInt(1, id);
            deleteBookCopyStatement.executeUpdate();
            // Xóa dữ liệu tương ứng trong bảng book_author
            String deleteBookAuthorQuery = "DELETE FROM book_author WHERE book_id = ?";
            PreparedStatement deleteBookAuthorStatement = conn.prepareStatement(deleteBookAuthorQuery);
            deleteBookAuthorStatement.setInt(1, id);
            deleteBookAuthorStatement.executeUpdate();

            // Xóa sách từ bảng book
            String deleteBookQuery = "DELETE FROM book WHERE id = ?";
            PreparedStatement deleteBookStatement = conn.prepareStatement(deleteBookQuery);
            deleteBookStatement.setInt(1, id);
            int rowsAffected = deleteBookStatement.executeUpdate();

            if (rowsAffected > 0) {
                doCallbacks(NOTIFY.UPDATE_BOOK);
                return new Response(200, "Deleted Book Successfully!");

            } else {
                return new Response(100, "Not find book_id " + id);

            }


        } catch (SQLException e) {
            return new Response(100, e.getMessage());
        }
    }

    // CRUD Author
    @Override
    public Response createAuthor(Author author, boolean isCallFromSever) throws RemoteException {
        try {
            if (!isCallFromSever && !(server2() == null)) {
                server2().createAuthor(author, true);
            }

            String insertAuthorQuery = "INSERT INTO author (name) VALUES (?)";
            PreparedStatement insertAuthorStatement = conn.prepareStatement(insertAuthorQuery, Statement.RETURN_GENERATED_KEYS);
            insertAuthorStatement.setString(1, author.getName());
            insertAuthorStatement.executeUpdate();
            doCallbacks(NOTIFY.UPDATE_AUTHOR);
            return new Response(200, "Created new author successfully!");
        } catch (Exception e) {
            return new Response(100, e.getMessage());
        }
    }

    @Override
    public Response getAuthor(int id) throws RemoteException {
        return null;
    }

    @Override
    public Response updateAuthor(Author author, boolean isCallFromSever) throws RemoteException {
        try {
            if (!isCallFromSever && !(server2() == null)) {
                server2().updateAuthor(author, true);
            }

            String updateAuthorQuery = "UPDATE author SET name = ? WHERE id = ?";
            PreparedStatement updateAuthorStatement = conn.prepareStatement(updateAuthorQuery);
            updateAuthorStatement.setString(1, author.getName());
            updateAuthorStatement.setInt(2, author.getId());
            updateAuthorStatement.executeUpdate();
            doCallbacks(NOTIFY.UPDATE_AUTHOR);
            return new Response(200, "Updated author successfully!");
        } catch (Exception e) {
            return new Response(100, e.getMessage());
        }
    }

    @Override
    public Response deleteAuthor(int id, boolean isCallFromSever) throws RemoteException {
        try {
            if (!isCallFromSever && !(server2() == null)) {
                server2().deleteAuthor(id, true);
            }

            String deleteAuthorQuery = "DELETE FROM author WHERE id = ?";
            PreparedStatement deleteAuthorStatement = conn.prepareStatement(deleteAuthorQuery);
            deleteAuthorStatement.setInt(1, id);
            int rowsAffected = deleteAuthorStatement.executeUpdate();

            if (rowsAffected > 0) {
                doCallbacks(NOTIFY.UPDATE_AUTHOR);
                return new Response(200, "Deleted author Successfully!");

            } else {
                return new Response(100, "Not find author_id " + id);

            }


        } catch (SQLException e) {
            return new Response(100, e.getMessage());
        }
    }

    // CRUD Category
    @Override
    public Response createCategory(Category category, boolean isCallFromSever) throws RemoteException {
        try {
            if (!isCallFromSever && !(server2() == null)) {
                server2().createCategory(category, true);
            }

            String createCategoryQuery = "INSERT INTO category(name) VALUE (?)";
            PreparedStatement createCategoryStatement = conn.prepareStatement(createCategoryQuery);
            createCategoryStatement.setString(1, category.getName());
            createCategoryStatement.executeUpdate();
            doCallbacks(NOTIFY.UPDATE_CATEGORY);
            return new Response(200, "Created new Category successfully!");
        } catch (Exception e) {
            return new Response(100, e.getMessage());
        }
    }

    @Override
    public Response getCategory(int id) throws RemoteException {
        return null;
    }

    @Override
    public Response updateCategory(Category category, boolean isCallFromSever) throws RemoteException {
        try {
            if (!isCallFromSever && !(server2() == null)) {
                server2().updateCategory(category, true);
            }

            String updateCategoryQuery = "UPDATE category SET name = ? WHERE id = ?";
            PreparedStatement updateCategoryStatement = conn.prepareStatement(updateCategoryQuery);
            updateCategoryStatement.setString(1, category.getName());
            updateCategoryStatement.setInt(2, category.getId());
            updateCategoryStatement.executeUpdate();
            doCallbacks(NOTIFY.UPDATE_CATEGORY);
            return new Response(200, "Updated Category successfully!");
        } catch (Exception e) {
            return new Response(100, e.getMessage());
        }
    }

    @Override
    public Response deleteCategory(int id, boolean isCallFromSever) throws RemoteException {
        try {
            if (!isCallFromSever && !(server2() == null)) {
                server2().deleteCategory(id, true);
            }

            String deleteCategoryQuery = "DELETE FROM category WHERE id = ?";
            PreparedStatement deleteCategoryStatement = conn.prepareStatement(deleteCategoryQuery);
            deleteCategoryStatement.setInt(1, id);
            int rowsAffected = deleteCategoryStatement.executeUpdate();

            if (rowsAffected > 0) {
                doCallbacks(NOTIFY.UPDATE_CATEGORY);
                return new Response(200, "Deleted category Successfully!");

            } else {
                return new Response(100, "Not find author_id " + id);

            }


        } catch (SQLException e) {
            return new Response(100, e.getMessage());
        }
    }

    // CRUD Published
    @Override
    public Response createPublished(Published published, boolean isCallFromSever) throws RemoteException {
        return null;
    }

    @Override
    public Response getPublished(int id) throws RemoteException {
        return null;
    }

    @Override
    public Response updatePublished(Published published, boolean isCallFromSever) throws RemoteException {
        return null;
    }

    @Override
    public Response deletePublished(int id, boolean isCallFromSever) throws RemoteException {
        return null;
    }

    // CRUD Patron
    @Override
    public Response createPatron(Patron patron, boolean isCallFromSever) throws RemoteException {
        try {
            String query = "INSERT INTO patron_account (first_name, last_name, email, password, status) VALUES (?, ?, ?, ?, ?)";
            pst = conn.prepareStatement(query);

            pst.setString(1, patron.getFirstName());
            pst.setString(2, patron.getLastName());
            pst.setString(3, patron.getEmail());
            pst.setString(4, patron.getPassword());
            pst.setBoolean(5, patron.isStatus());

            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                return new Response(200, "Create new patron account successfully");
            } else {
                return new Response(100, "Some error when create new patron account");
            }

        } catch (SQLException e) {
            return new Response(100, e.getMessage());
        }
    }

    @Override
    public Response getPatron(int id) throws RemoteException {
        return null;
    }

    @Override
    public Response updatePatron(Patron patron, boolean isCallFromSever) throws RemoteException {
        try  {
            if (!isCallFromSever && !(server2() == null)) {
                server2().updatePatron(patron, true);
            }
            String query = "UPDATE patron_account SET first_name = ?, last_name = ?, email = ? WHERE id = ?";
            pst = conn.prepareStatement(query);

            pst.setString(1, patron.getFirstName());
            pst.setString(2, patron.getLastName());
            pst.setString(3, patron.getEmail());
            pst.setInt(4, patron.getId());

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                return new Response(200, "Update patron account successfully");
            } else {
                return new Response(100, "Some error when update patron account");
            }

        } catch (SQLException e) {
            return new Response(100, e.getMessage());
        }
    }

    @Override
    public Response deletePatron(int id, boolean isCallFromSever) throws RemoteException {
        try {
            if (!isCallFromSever && !(server2() == null)) {
                server2().deletePatron(id, true);
            }
            String query = "DELETE FROM patron_account WHERE id = ?";
            pst = conn.prepareStatement(query);

            pst.setInt(1, id);

            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                return new Response(200, "Successfully.");
            } else {
                return new Response(100, "No patron found with the given ID.");
            }
        } catch (SQLException e) {
            return new Response(100, e.getMessage());
        }
    }



    // CRUD BookCopy
    @Override
    public Response createBookCopy(BookCopy bookCopy, boolean isCallFromSever) throws RemoteException {
        try {
            String query = "INSERT INTO book_copy (year_published, book_id, published_id) VALUES (?, ?, ?)";
            pst = conn.prepareStatement(query);

            pst.setInt(1, bookCopy.getYear_published());
            pst.setInt(2, bookCopy.getPublished_id());
            pst.setInt(3, bookCopy.getBook_id());

            pst.executeUpdate();

            return new Response(200, "Create book copy successfully");
        } catch (SQLException e) {
            return new Response(100, e.getMessage());
        }
    }

    @Override
    public Response getBookCopy(int id) throws RemoteException {
        return null;
    }

    @Override
    public Response updateBookCopy(BookCopy bookCopy, boolean isCallFromSever) throws RemoteException {
        return null;
    }

    @Override
    public Response deleteBookCopy(int id, boolean isCallFromSever) throws RemoteException {
        return null;
    }

    // CRUD Hold
    @Override
    public Response createHold(Hold hold, boolean isCallFromSever) throws RemoteException {

        try {
            String query = "INSERT INTO hold (start_time, end_time, patron_id, book_copy_id) VALUES (?, ?, ?, ?)";
            pst = conn.prepareStatement(query);

            pst.setTimestamp(1, hold.getStart_time());
            pst.setTimestamp(2, hold.getEnd_time());
            pst.setInt(3, hold.getPatron_id());
            pst.setInt(4, hold.getBook_copy_id());

            pst.executeUpdate();

            return new Response(200, "Create hold successfully");
        } catch (SQLException e) {
            return new Response(100, e.getMessage());
        }
    }

    @Override
    public Response getHold(int id) throws RemoteException {
        return null;
    }

    @Override
    public Response updateHold(Hold hold, boolean isCallFromSever) throws RemoteException {
        try  {
            String query = "UPDATE hold SET start_time = ?, end_time = ?, patron_id = ?, book_copy_id = ? WHERE id = ?";
            pst = conn.prepareStatement(query);

            pst.setTimestamp(1, hold.getStart_time());
            pst.setTimestamp(2, hold.getEnd_time());
            pst.setInt(3, hold.getPatron_id());
            pst.setInt(4, hold.getBook_copy_id());
            pst.setInt(5, hold.getId());

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                return new Response(200, "Hold updated successfully.");
            } else {
                return new Response(100, "No hold found with the given ID.");
            }
        } catch (SQLException e) {
            return new Response(100, e.getMessage());
        }
    }

    @Override
    public Response deleteHold(int id, boolean isCallFromSever) throws RemoteException {
        try {
            String query = "DELETE FROM hold WHERE id = ?";
            pst = conn.prepareStatement(query);

            pst.setInt(1, id);

            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                return new Response(200, "Hold deleted successfully.");
            } else {
                return new Response(100, "No hold found with the given ID.");
            }
        } catch (SQLException e) {
            return new Response(100, e.getMessage());
        }
    }

    // CRUD Checkout
    @Override
    public Response createCheckout(Checkout checkout, boolean isCallFromSever) throws RemoteException {
        try {
            if (!isCallFromSever && !(server2() == null)) {
                server2().createCheckout(checkout, true);
            }
            String query = "INSERT INTO checkout (start_time, end_time, is_returned, patron_id, book_copy_id) VALUES (?, ?, ?, ?, ?)";
            pst = conn.prepareStatement(query);

            pst.setTimestamp(1, checkout.getStart_time());
            pst.setTimestamp(2, checkout.getEnd_time());
            pst.setBoolean(3, checkout.isIs_returned());
            pst.setInt(4, checkout.getPatron_id());
            pst.setInt(5, checkout.getBook_copy_id());

            pst.executeUpdate();

            doCallbacks(NOTIFY.CLIENT_UPDATE_CHECKOUT);
            doCallbacks(NOTIFY.UPDATE_CHECKOUT);

            return new Response(200, "Successfully");
        } catch (SQLException e) {
            return new Response(100, e.getMessage());
        }
    }

    @Override
    public Response getCheckout(int id) throws RemoteException {
        return null;
    }

    @Override
    public Response updateCheckout(Checkout checkout, boolean isCallFromSever) throws RemoteException {
        try  {
            if (!isCallFromSever && !(server2() == null)) {
                server2().updateCheckout(checkout, true);
            }
            String query = "UPDATE checkout SET start_time = ?, end_time = ?, is_returned = ?, patron_id = ?, book_copy_id = ? WHERE id = ?";
            pst = conn.prepareStatement(query);

            pst.setTimestamp(1, checkout.getStart_time());
            pst.setTimestamp(2, checkout.getEnd_time());
            pst.setBoolean(3, checkout.isIs_returned());
            pst.setInt(4, checkout.getPatron_id());
            pst.setInt(5, checkout.getBook_copy_id());
            pst.setInt(6, checkout.getId());

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                if (checkout.isIs_returned()) {
                    Notification notification = new Notification();

                    java.util.Date date = new Date();
                    Timestamp time_now = new Timestamp(date.getTime());

                    notification.setMessage("ID " + checkout.getId() + ": Thủ thư cho phép bạn mượn sách!");
                    notification.setPatron_id(checkout.getPatron_id());
                    notification.setSend_at(time_now);

                    String insertNotificationQuery = "INSERT INTO notification (sent_at, message, patron_id) VALUES (?, ?, ?)";
                    PreparedStatement insertNotificationStatement = conn.prepareStatement(insertNotificationQuery, Statement.RETURN_GENERATED_KEYS);
                    insertNotificationStatement.setTimestamp(1, notification.getSend_at());
                    insertNotificationStatement.setString(2, notification.getMessage());
                    insertNotificationStatement.setInt(3, notification.getPatron_id());
                    insertNotificationStatement.executeUpdate();

                    doCallbacks(NOTIFY.UPDATE_NOTIFICATION);
                    doCallbacks(NOTIFY.CLIENT_UPDATE_NOTIFICATION);
                    doCallbacks(NOTIFY.CLIENT_UPDATE_CHECKOUT);

                }
                return new Response(200, "Updated successfully.");
            } else {
                return new Response(100, "No record found with the given ID.");
            }
        } catch (SQLException e) {
            return new Response(100, e.getMessage());
        }
    }

    @Override
    public Response deleteCheckout(int id, boolean isCallFromSever) throws RemoteException {
        try {
            if (!isCallFromSever && !(server2() == null)) {
                server2().deleteCheckout(id, true);
            }
            String query = "DELETE FROM checkout WHERE id = ?";
            pst = conn.prepareStatement(query);

            pst.setInt(1, id);

            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                doCallbacks(NOTIFY.CLIENT_UPDATE_CHECKOUT);
                doCallbacks(NOTIFY.UPDATE_CHECKOUT);
                return new Response(200, "Successfully.");
            } else {
                return new Response(100, "No checkout found with the given ID.");
            }
        } catch (SQLException e) {
            return new Response(100, e.getMessage());
        }
    }

    // CRUD Notification
    @Override
    public Response createNotification(Notification notification, boolean isCallFromSever) throws RemoteException {
        try {
            if (!isCallFromSever && !(server2() == null)) {
                server2().createNotification(notification, true);
            }

            String insertNotificationQuery = "INSERT INTO notification (sent_at, message, patron_id) VALUES (?, ?, ?)";
            PreparedStatement insertNotificationStatement = conn.prepareStatement(insertNotificationQuery, Statement.RETURN_GENERATED_KEYS);
            insertNotificationStatement.setTimestamp(1, notification.getSend_at());
            insertNotificationStatement.setString(2, notification.getMessage());
            insertNotificationStatement.setInt(3, notification.getPatron_id());
            insertNotificationStatement.executeUpdate();

            doCallbacks(NOTIFY.UPDATE_NOTIFICATION);
            doCallbacks(NOTIFY.CLIENT_UPDATE_NOTIFICATION);


            return new Response(200, "Created new notification successfully!");
        } catch (Exception e) {
            return new Response(100, e.getMessage());
        }
    }

    // Get by patron_id
    @Override
    public Response getNotification(int id) throws RemoteException {
        try {
            vTitle.clear();
            vData.clear();

            String query = "SELECT sent_at, message " +
                    "FROM notification WHERE patron_id = ? ORDER BY id DESC ";

            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rst = pst.executeQuery();

            String[] title = new String[]{
                    "Time", "Message"
            };
            Collections.addAll(vTitle, title);
            while (rst.next()) {
                Vector row = new Vector();
                System.out.println(row);
                row.add(rst.getTimestamp("sent_at"));
                row.add(rst.getString("message"));
                vData.add(row);
            }
            rst.close();
            return new Response(200, new DefaultTableModel(vData, vTitle));
        } catch (SQLException e) {
            System.out.println("!!!---Error: " + e);
            return new Response(100, null);

        }
    }

    @Override
    public Response updateNotification(Notification notification, boolean isCallFromSever) throws RemoteException {
        try {
            if (!isCallFromSever && !(server2() == null)) {
                server2().updateNotification(notification, true);
            }

            String updateNotificationQuery = "UPDATE notification SET sent_at = ?, message = ? , patron_id = ? WHERE id = ?";
            PreparedStatement updateNotificationStatement = conn.prepareStatement(updateNotificationQuery);
            updateNotificationStatement.setTimestamp(1, notification.getSend_at());
            updateNotificationStatement.setString(2, notification.getMessage());
            updateNotificationStatement.setInt(3, notification.getPatron_id());
            updateNotificationStatement.setInt(4, notification.getId());
            updateNotificationStatement.executeUpdate();
            doCallbacks(NOTIFY.UPDATE_NOTIFICATION);
            return new Response(200, "Updated notification successfully!");
        } catch (Exception e) {
            return new Response(100, e.getMessage());
        }
    }

    @Override
    public Response deleteNotification(int id, boolean isCallFromSever) throws RemoteException {
        try {
            if (!isCallFromSever && !(server2() == null)) {
                server2().deleteNotification(id, true);
            }

            String deleteNotificationQuery = "DELETE FROM notification WHERE id = ?";
            PreparedStatement deleteNotificationStatement = conn.prepareStatement(deleteNotificationQuery);
            deleteNotificationStatement.setInt(1, id);
            int rowsAffected = deleteNotificationStatement.executeUpdate();

            if (rowsAffected > 0) {
                doCallbacks(NOTIFY.UPDATE_AUTHOR);
                return new Response(200, "Deleted author Successfully!");

            } else {
                return new Response(100, "Not find author_id " + id);

            }


        } catch (SQLException e) {
            return new Response(100, e.getMessage());
        }
    }

    // CRUD - Log
    @Override
    public int createLog(Log log, boolean isCallFromSever) throws RemoteException {
        try {
            if (!isCallFromSever && !(server2() == null)) {
                server2().createLog(log, true);
            }

            String createLogQuery = "INSERT INTO log (ip, username, table_name, col_id, time_start) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement createLogStatement = conn.prepareStatement(createLogQuery, Statement.RETURN_GENERATED_KEYS);
            createLogStatement.setString(1, log.getIp());
            createLogStatement.setString(2, log.getUsername());
            createLogStatement.setString(3, log.getTable_name());
            createLogStatement.setInt(4, log.getCol_id());
            createLogStatement.setTimestamp(5, log.getTime_start());
            createLogStatement.executeUpdate();

            ResultSet generatedKeys = createLogStatement.getGeneratedKeys();
            int log_id;
            if (generatedKeys.next()) {
                log_id = generatedKeys.getInt(1);
                System.out.println(log_id);
                return log_id;
            }
            return -1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    @Override
    public void updateLog(Log log, boolean isCallFromSever) throws RemoteException {
        try {
            if (!isCallFromSever && !(server2() == null)) {
                server2().updateLog(log, true);
            }

            String updateLogQuery = "UPDATE log SET ip = ? , username = ?, table_name = ? , col_id = ?, time_start = ?  WHERE id = ?";
            PreparedStatement updateLogStatement = conn.prepareStatement(updateLogQuery);
            updateLogStatement.setString(1, log.getIp());
            updateLogStatement.setString(2, log.getUsername());
            updateLogStatement.setString(3, log.getTable_name());
            updateLogStatement.setInt(4, log.getCol_id());
            updateLogStatement.setTimestamp(5, log.getTime_start());
            updateLogStatement.setInt(6, log.getId());
            updateLogStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteLog(int id, boolean isCallFromSever) throws RemoteException {
        try {
            if (!isCallFromSever && !(server2() == null)) {
                server2().deleteLog(id, true);
            }

            // Xóa các bản sao sách từ bảng book_copy
            String deleteLogQuery = "DELETE FROM log WHERE id = ?";
            PreparedStatement deleteLogStatement = conn.prepareStatement(deleteLogQuery);
            deleteLogStatement.setInt(1, id);
            deleteLogStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteAllLog(boolean isCallFromSever) throws RemoteException {
        try {
            if (!isCallFromSever && !(server2() == null)) {
                server2().deleteAllLog(true);
            }

            // Xóa các bản sao sách từ bảng book_copy
            String deleteLogQuery = "DELETE FROM log";
            PreparedStatement deleteLogStatement = conn.prepareStatement(deleteLogQuery);
            deleteLogStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean checkLog(String table_name, int col_id) throws RemoteException {
        try {
            String checkLogQuery = "SELECT COUNT(*) FROM log WHERE table_name = ? AND col_id = ?";
            PreparedStatement checkLogStatement = conn.prepareStatement(checkLogQuery);
            checkLogStatement.setString(1, table_name);
            checkLogStatement.setInt(2, col_id);
            ResultSet resultSet = checkLogStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }

            return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Response loginClient(Patron patron) throws RemoteException {
        String email = patron.getEmail();
        String password = patron.getPassword();

        try {
            String query = "SELECT * FROM patron_account WHERE email = ?";
            pst = conn.prepareStatement(query);
            pst.setString(1, email);
            
            rst = pst.executeQuery();
            if (rst.next()) {
                String password_db= rst.getString("password");
                
                // Compare the stored password hash with the provided password
                boolean passwordMatch = password_db.equals(password);

                if (passwordMatch) {
                    if (!rst.getBoolean("status")) {
                        return new Response(100, "Account had banned! Contact with manage of VKU Library");
                    }
                    patron.setId(rst.getInt("id"));
                    patron.setFirstName(rst.getString("first_name"));
                    patron.setLastName(rst.getString("last_name"));
                    patron.setStatus(rst.getBoolean("status"));
                    return new Response(200, patron);
                } else {
                    return new Response(100, "Login failed! Please try again!");
                }
            } else {
                return new Response(100, "User not found!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response registerClient(Patron patron) throws RemoteException {
        return null;
    }


    // synchronized server - server
    LibraryRemote server2;

    public LibraryRemote server2() throws RemoteException {
        try {
            if (Config.IP_SERVER_2.equals("localhost")) {
                System.out.println("SERVER: server2 don't running");
                return null;
            }
            if (server2 == null) {
                server2 = (LibraryRemote) Naming.lookup("rmi://" + Config.IP_SERVER_2 + ":" + Config.PORT_SERVER_2 + "/api");
            }
            return server2;
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

}

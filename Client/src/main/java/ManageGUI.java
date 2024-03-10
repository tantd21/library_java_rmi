/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author nguye
 */
public class ManageGUI extends javax.swing.JFrame {
    class ClientImpl extends UnicastRemoteObject implements ClientInterface {

        public ClientImpl() throws RemoteException {
        }

        @Override
        public void notify(NOTIFY notify) throws RemoteException {
            log = null;
            if (notify == NOTIFY.UPDATE_BOOK) showTableBook();
            if (notify == NOTIFY.UPDATE_AUTHOR) showTableAuthor();
            if (notify == NOTIFY.UPDATE_CATEGORY) showTableCategory();
            if (notify == NOTIFY.UPDATE_NOTIFICATION) showTableNotification();

        }
    }

    ManagerController controller;
    TableRowSorter sorter;
    Log log;
    InetAddress ipAddress;

    {
        try {
            ipAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    String ip = ipAddress.getHostAddress().isEmpty() ? "Unknown" : ipAddress.getHostAddress();
    String username = "manage";
    String table_name;
    String col_id;

    /**
     * Creates new form LibraryGUI
     */
    public ManageGUI() {
        initComponents();
        try {
            controller = new ManagerController(new ClientImpl());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        // Bắt sự kiện đóng cửa sổ để xóa log
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                try {
                    controller.exit();
                    if (log != null)
                        controller.deleteLog(log.getId());
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        // load data
        showTableBook();
        showTableAuthor();
        showTableCategory();
        showTablePublished();
        showTableBookCopy();
        showTableHold();
        showTableCheckout();
        showTableNotification();
        showTablePatrons();
        showTableHistory();

        showDataComboBoxCategory();
        showDataComboBoxPublished();
        showDataComboBoxAuthor();
        showDataComboBoxBooks();
        showDataComboBoxBookCopy();
        showDataComboBoxPatron();
        //
    }


    public synchronized void showTableBook() {
        try {
            Response response = controller.getBooksController();
            if (response.getStatus() == 100) {
                JOptionPane.showMessageDialog(this, response.getStatus());
            }
            sorter = new TableRowSorter<>((DefaultTableModel) response.getData());
            tbl_Book.setRowSorter(sorter);
            tf_search_Book.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    search(tf_search_Book.getText());
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    search(tf_search_Book.getText());
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    search(tf_search_Book.getText());
                }

                public void search(String str) {
                    if (str.length() == 0) {
                        sorter.setRowFilter(null);
                    } else {
                        sorter.setRowFilter(RowFilter.regexFilter(str));
                    }
                }
            });

            tbl_Book.setModel((DefaultTableModel) response.getData());
            tbl_Book.getTableHeader().setDefaultRenderer(new CustomHeaderRenderer());
            tbl_Book.setRowHeight(30);
            tbl_Book.setDefaultEditor(Object.class, null);
            TableColumn indexColumn = tbl_Book.getColumnModel().getColumn(0);
            indexColumn.setCellRenderer(new CenteredTableCellRenderer());
            indexColumn.setMaxWidth(80);
            sp_Book.setViewportView(tbl_Book);
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }

    public synchronized void showTableAuthor() {
        try {
            Response response = controller.getAuthorsController();
            if (response.getStatus() == 100) {
                JOptionPane.showMessageDialog(this, response.getStatus());
            }
            sorter = new TableRowSorter<>((DefaultTableModel) response.getData());
            tbl_Author.setRowSorter(sorter);
            tf_search_Author.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    search(tf_search_Author.getText());
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    search(tf_search_Author.getText());
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    search(tf_search_Author.getText());
                }

                public void search(String str) {
                    if (str.length() == 0) {
                        sorter.setRowFilter(null);
                    } else {
                        sorter.setRowFilter(RowFilter.regexFilter(str));
                    }
                }
            });

            tbl_Author.setModel((DefaultTableModel) response.getData());
            tbl_Author.getTableHeader().setDefaultRenderer(new CustomHeaderRenderer());
            tbl_Author.setRowHeight(30);
            tbl_Author.setDefaultEditor(Object.class, null);
            TableColumn indexColumn = tbl_Author.getColumnModel().getColumn(0);
            indexColumn.setCellRenderer(new CenteredTableCellRenderer());
            indexColumn.setMaxWidth(80);
            sp_Author.setViewportView(tbl_Author);
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }

    public synchronized void showTableCategory() {
        try {
            Response response = controller.getCategoriesController();
            if (response.getStatus() == 100) {
                JOptionPane.showMessageDialog(this, response.getStatus());
            }
            sorter = new TableRowSorter<>((DefaultTableModel) response.getData());
            tbl_Category.setRowSorter(sorter);
            tf_search_Category.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    search(tf_search_Category.getText());
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    search(tf_search_Category.getText());
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    search(tf_search_Category.getText());
                }

                public void search(String str) {
                    if (str.length() == 0) {
                        sorter.setRowFilter(null);
                    } else {
                        sorter.setRowFilter(RowFilter.regexFilter(str));
                    }
                }
            });

            tbl_Category.setModel((DefaultTableModel) response.getData());
            tbl_Category.getTableHeader().setDefaultRenderer(new CustomHeaderRenderer());
            tbl_Category.setRowHeight(30);
            tbl_Category.setDefaultEditor(Object.class, null);
            TableColumn indexColumn = tbl_Category.getColumnModel().getColumn(0);
            indexColumn.setCellRenderer(new CenteredTableCellRenderer());
            indexColumn.setMaxWidth(80);
            sp_Category.setViewportView(tbl_Category);
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }

    public synchronized void showTablePublished() {
        try {
            Response response = controller.getPublishedController();
            if (response.getStatus() == 100) {
                JOptionPane.showMessageDialog(this, response.getStatus());
            }
            sorter = new TableRowSorter<>((DefaultTableModel) response.getData());
            tbl_Published.setRowSorter(sorter);
            tf_search_Published.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    search(tf_search_Published.getText());
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    search(tf_search_Published.getText());
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    search(tf_search_Published.getText());
                }

                public void search(String str) {
                    if (str.length() == 0) {
                        sorter.setRowFilter(null);
                    } else {
                        sorter.setRowFilter(RowFilter.regexFilter(str));
                    }
                }
            });

            tbl_Published.setModel((DefaultTableModel) response.getData());
            tbl_Published.getTableHeader().setDefaultRenderer(new CustomHeaderRenderer());
            tbl_Published.setRowHeight(30);
            tbl_Published.setDefaultEditor(Object.class, null);
            TableColumn indexColumn = tbl_Published.getColumnModel().getColumn(0);
            indexColumn.setCellRenderer(new CenteredTableCellRenderer());
            indexColumn.setMaxWidth(80);
            sp_Published.setViewportView(tbl_Published);
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }

    public synchronized void showTableBookCopy() {
        try {
            Response response = controller.getBooksCopyController();
            if (response.getStatus() == 100) {
                JOptionPane.showMessageDialog(this, response.getStatus());
            }
            sorter = new TableRowSorter<>((DefaultTableModel) response.getData());
            tbl_BookCopy.setRowSorter(sorter);
            tf_search_BookCopy.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    search(tf_search_BookCopy.getText());
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    search(tf_search_BookCopy.getText());
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    search(tf_search_BookCopy.getText());
                }

                public void search(String str) {
                    if (str.length() == 0) {
                        sorter.setRowFilter(null);
                    } else {
                        sorter.setRowFilter(RowFilter.regexFilter(str));
                    }
                }
            });

            tbl_BookCopy.setModel((DefaultTableModel) response.getData());
            tbl_BookCopy.getTableHeader().setDefaultRenderer(new CustomHeaderRenderer());
            tbl_BookCopy.setRowHeight(30);
            tbl_BookCopy.setDefaultEditor(Object.class, null);
            TableColumn indexColumn = tbl_BookCopy.getColumnModel().getColumn(0);
            indexColumn.setCellRenderer(new CenteredTableCellRenderer());
            indexColumn.setMaxWidth(80);
            sp_BookCopy.setViewportView(tbl_BookCopy);
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }
    public synchronized void showTableHold() {
        try {
            Response response = controller.getHoldsController();
            if (response.getStatus() == 100) {
                JOptionPane.showMessageDialog(this, response.getData());
            }
            sorter = new TableRowSorter<>((DefaultTableModel) response.getData());
            tbl_Hold.setRowSorter(sorter);
            tf_search_Hold.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    search(tf_search_Hold.getText());
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    search(tf_search_Hold.getText());
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    search(tf_search_Hold.getText());
                }

                public void search(String str) {
                    if (str.length() == 0) {
                        sorter.setRowFilter(null);
                    } else {
                        sorter.setRowFilter(RowFilter.regexFilter(str));
                    }
                }
            });

            tbl_Hold.setModel((DefaultTableModel) response.getData());
            tbl_Hold.getTableHeader().setDefaultRenderer(new CustomHeaderRenderer());
            tbl_Hold.setRowHeight(30);
            tbl_Hold.setDefaultEditor(Object.class, null);
            TableColumn indexColumn = tbl_Hold.getColumnModel().getColumn(0);
            indexColumn.setCellRenderer(new CenteredTableCellRenderer());
            indexColumn.setMaxWidth(80);
            sp_Hold.setViewportView(tbl_Hold);
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }

    public synchronized void showTableCheckout() {
        try {
            Response response = controller.getCheckoutsController();
            if (response.getStatus() == 100) {
                JOptionPane.showMessageDialog(this, response.getData());
            }
            sorter = new TableRowSorter<>((DefaultTableModel) response.getData());
            tbl_Checkout.setRowSorter(sorter);
            tf_search_Checkout.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    search(tf_search_Checkout.getText());
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    search(tf_search_Checkout.getText());
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    search(tf_search_Checkout.getText());
                }

                public void search(String str) {
                    if (str.length() == 0) {
                        sorter.setRowFilter(null);
                    } else {
                        sorter.setRowFilter(RowFilter.regexFilter(str));
                    }
                }
            });

            tbl_Checkout.setModel((DefaultTableModel) response.getData());
            tbl_Checkout.getTableHeader().setDefaultRenderer(new CustomHeaderRenderer());
            tbl_Checkout.setRowHeight(30);
            tbl_Checkout.setDefaultEditor(Object.class, null);
            TableColumn indexColumn = tbl_Checkout.getColumnModel().getColumn(0);
            indexColumn.setCellRenderer(new CenteredTableCellRenderer());
            indexColumn.setMaxWidth(80);
            sp_Checkout.setViewportView(tbl_Checkout);
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }

    public synchronized void showTableNotification() {
        try {
            Response response = controller.getNotificationsController();
            if (response.getStatus() == 100) {
                JOptionPane.showMessageDialog(this, response.getData());
            }
            sorter = new TableRowSorter<>((DefaultTableModel) response.getData());
            tbl_Notification.setRowSorter(sorter);
            tf_search_Notification.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    search(tf_search_Notification.getText());
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    search(tf_search_Notification.getText());
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    search(tf_search_Notification.getText());
                }

                public void search(String str) {
                    if (str.length() == 0) {
                        sorter.setRowFilter(null);
                    } else {
                        sorter.setRowFilter(RowFilter.regexFilter(str));
                    }
                }
            });

            tbl_Notification.setModel((DefaultTableModel) response.getData());
            tbl_Notification.getTableHeader().setDefaultRenderer(new CustomHeaderRenderer());
            tbl_Notification.setRowHeight(30);
            tbl_Notification.setDefaultEditor(Object.class, null);
            TableColumn indexColumn = tbl_Notification.getColumnModel().getColumn(0);
            indexColumn.setCellRenderer(new CenteredTableCellRenderer());
            indexColumn.setMaxWidth(80);
            sp_Notification.setViewportView(tbl_Notification);
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }

    public synchronized void showTablePatrons() {
        try {
            Response response = controller.getPatronsController();
            if (response.getStatus() == 100) {
                JOptionPane.showMessageDialog(this, response.getData());
            }
            sorter = new TableRowSorter<>((DefaultTableModel) response.getData());
            tbl_Patron.setRowSorter(sorter);
            tf_search_Patron.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    search(tf_search_Patron.getText());
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    search(tf_search_Patron.getText());
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    search(tf_search_Patron.getText());
                }

                public void search(String str) {
                    if (str.length() == 0) {
                        sorter.setRowFilter(null);
                    } else {
                        sorter.setRowFilter(RowFilter.regexFilter(str));
                    }
                }
            });

            tbl_Patron.setModel((DefaultTableModel) response.getData());
            tbl_Patron.getTableHeader().setDefaultRenderer(new CustomHeaderRenderer());
            tbl_Patron.setRowHeight(30);
            tbl_Patron.setDefaultEditor(Object.class, null);
            TableColumn indexColumn = tbl_Patron.getColumnModel().getColumn(0);
            indexColumn.setCellRenderer(new CenteredTableCellRenderer());
            indexColumn.setMaxWidth(80);
            sp_Patron.setViewportView(tbl_Patron);
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }

    public synchronized void showTableHistory() {
        try {
            Response response = controller.getHistoryController();
            if (response.getStatus() == 100) {
                JOptionPane.showMessageDialog(this, response.getData());
            }
            sorter = new TableRowSorter<>((DefaultTableModel) response.getData());
            tbl_History.setRowSorter(sorter);
            tf_search_History.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    search(tf_search_History.getText());
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    search(tf_search_History.getText());
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    search(tf_search_History.getText());
                }

                public void search(String str) {
                    if (str.length() == 0) {
                        sorter.setRowFilter(null);
                    } else {
                        sorter.setRowFilter(RowFilter.regexFilter(str));
                    }
                }
            });

            tbl_History.setModel((DefaultTableModel) response.getData());
            tbl_History.getTableHeader().setDefaultRenderer(new CustomHeaderRenderer());
            tbl_History.setRowHeight(30);
            tbl_History.setDefaultEditor(Object.class, null);
            TableColumn indexColumn = tbl_History.getColumnModel().getColumn(0);
            indexColumn.setCellRenderer(new CenteredTableCellRenderer());
            indexColumn.setMaxWidth(80);
            sp_History.setViewportView(tbl_History);
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }

    // ------------------------------------------------------------------
    public synchronized void showDataComboBoxCategory() {
        try {
            Response response = controller.getDataComboBoxCategories();
            if (response.getStatus() == 100) {
                JOptionPane.showMessageDialog(this, response.getData());
            }
            cb_category_Book.removeAllItems();
            List<Category> categoryList = (List<Category>) response.getData();
            for (Category i :
                    categoryList) {
                cb_category_Book.addItem(i);
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public synchronized void showDataComboBoxPublished() {
        try {
            Response response = controller.getDataComboBoxPublished();
            if (response.getStatus() == 100) {
                JOptionPane.showMessageDialog(this, response.getData());
            }
            cb_category_Book.removeAllItems();
            List<Published> publishedList = (List<Published>) response.getData();
            for (Published i :
                    publishedList) {
                cb_published_BookCopy.addItem(i);
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public synchronized void showDataComboBoxAuthor() {
        try {
            Response response = controller.getDataComboBoxAuthors();
            if (response.getStatus() == 100) {
                JOptionPane.showMessageDialog(this, response.getData());
            }
            cb_author_Book.removeAllItems();
            List<Author> authorList = (List<Author>) response.getData();
            for (Author i : authorList) {
                cb_author_Book.addItem(i);
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public synchronized void showDataComboBoxBookCopy() {
        try {
            Response response = controller.getDataComboBoxBookCopies();
            if (response.getStatus() == 100) {
                JOptionPane.showMessageDialog(this, response.getData());
            }
            cb_book_Hold.removeAllItems();
            cb_book_Checkout.removeAllItems();
            List<BookCopy> bookCopyList = (List<BookCopy>) response.getData();
            for (BookCopy i : bookCopyList) {
                cb_book_Hold.addItem(i);
                cb_book_Checkout.addItem(i);
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public synchronized void showDataComboBoxPatron() {
        try {
            Response response = controller.getDataComboBoxPatrons();
            if (response.getStatus() == 100) {
                JOptionPane.showMessageDialog(this, response.getData());
            }
            cb_patron_Hold.removeAllItems();
            cb_patron_Checkout.removeAllItems();
            List<Patron> patronsList = (List<Patron>) response.getData();
            for (Patron i : patronsList) {
                cb_patron_Hold.addItem(i);
                cb_patron_Checkout.addItem(i);
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    public synchronized void showDataComboBoxBooks() {
        try {
            Response response = controller.getDataComboBoxBooks();
            if (response.getStatus() == 100) {
                JOptionPane.showMessageDialog(this, response.getData());
            }
            cb_book_BookCopy.removeAllItems();

            List<Book> booksList = (List<Book>) response.getData();
            for (Book i : booksList) {
                cb_book_BookCopy.addItem(i);

            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    public void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        main_panel = new javax.swing.JTabbedPane();
        panel_BookCopy = new javax.swing.JPanel();
        sp_BookCopy = new javax.swing.JScrollPane();
        tbl_BookCopy = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        tf_search_BookCopy = new javax.swing.JTextField();
        btn_create_BookCopy = new javax.swing.JButton();
        btn_update_BookCopy = new javax.swing.JButton();
        btn_delete_BookCopy = new javax.swing.JButton();
        btn_refresh_BookCopy = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        tf_ID_BookCopy = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        tf_year_BookCopy = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        cb_book_BookCopy = new javax.swing.JComboBox();
        jLabel25 = new javax.swing.JLabel();
        cb_published_BookCopy = new javax.swing.JComboBox();
        panel_Patron = new javax.swing.JPanel();
        sp_Patron = new javax.swing.JScrollPane();
        tbl_Patron = new javax.swing.JTable();
        jLabel38 = new javax.swing.JLabel();
        tf_search_Patron = new javax.swing.JTextField();
        btn_create_Patron = new javax.swing.JButton();
        btn_update_Patron = new javax.swing.JButton();
        btn_delete_Patron = new javax.swing.JButton();
        btn_refresh_Patron = new javax.swing.JButton();
        jLabel39 = new javax.swing.JLabel();
        tf_ID_Patron = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        tf_email_Patron = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        tf_lname_Patron = new javax.swing.JTextField();
        tf_fname_Patron = new javax.swing.JTextField();
        tf_pass_Patron = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        checkBox_Patron = new javax.swing.JCheckBox();
        btn_sendAll = new javax.swing.JButton();
        btn_send = new javax.swing.JButton();
        panel_Hold = new javax.swing.JPanel();
        sp_Hold = new javax.swing.JScrollPane();
        tbl_Hold = new javax.swing.JTable();
        jLabel26 = new javax.swing.JLabel();
        tf_search_Hold = new javax.swing.JTextField();
        btn_create_Hold = new javax.swing.JButton();
        btn_update_Hold = new javax.swing.JButton();
        btn_delete_Hold = new javax.swing.JButton();
        btn_refresh_Hold = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        tf_ID_Hold = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        tf_start_Hold = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        cb_book_Hold = new javax.swing.JComboBox();
        cb_patron_Hold = new javax.swing.JComboBox();
        jLabel31 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        tf_end_Hold = new javax.swing.JTextField();
        panel_Checkout = new javax.swing.JPanel();
        sp_Checkout = new javax.swing.JScrollPane();
        tbl_Checkout = new javax.swing.JTable();
        jLabel32 = new javax.swing.JLabel();
        tf_search_Checkout = new javax.swing.JTextField();
        btn_create_Checkout = new javax.swing.JButton();
        btn_update_Checkout = new javax.swing.JButton();
        btn_delete_Checkout = new javax.swing.JButton();
        btn_refresh_Checkout = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        cb_book_Checkout = new javax.swing.JComboBox();
        cb_patron_Checkout = new javax.swing.JComboBox();
        jLabel47 = new javax.swing.JLabel();
        tf_end_Checkout = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        tf_ID_Checkout = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        tf_start_Checkout = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        checkBox_Checkout = new javax.swing.JCheckBox();
        jLabel52 = new javax.swing.JLabel();
        panel_Book = new javax.swing.JPanel();
        sp_Book = new javax.swing.JScrollPane();
        tbl_Book = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        tf_search_Book = new javax.swing.JTextField();
        btn_create_Book = new javax.swing.JButton();
        btn_update_Book = new javax.swing.JButton();
        btn_delete_Book = new javax.swing.JButton();
        btn_refresh_Book = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        tf_ID_Book = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tf_title_Book = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cb_category_Book = new javax.swing.JComboBox();
        cb_author_Book = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        panel_Author = new javax.swing.JPanel();
        sp_Author = new javax.swing.JScrollPane();
        tbl_Author = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        tf_search_Author = new javax.swing.JTextField();
        btn_create_Author = new javax.swing.JButton();
        btn_update_Author = new javax.swing.JButton();
        btn_delete_Author = new javax.swing.JButton();
        btn_refresh_Author = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        tf_ID_Author = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        tf_name_Author = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        panel_Category = new javax.swing.JPanel();
        sp_Category = new javax.swing.JScrollPane();
        tbl_Category = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        tf_search_Category = new javax.swing.JTextField();
        btn_create_Category = new javax.swing.JButton();
        btn_update_Category = new javax.swing.JButton();
        btn_delete_Category = new javax.swing.JButton();
        btn_refresh_Category = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        tf_ID_Category = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        tf_name_Category = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        panel_Published = new javax.swing.JPanel();
        sp_Published = new javax.swing.JScrollPane();
        tbl_Published = new javax.swing.JTable();
        jLabel20 = new javax.swing.JLabel();
        tf_search_Published = new javax.swing.JTextField();
        btn_create_Published = new javax.swing.JButton();
        btn_update_Published = new javax.swing.JButton();
        btn_delete_Published = new javax.swing.JButton();
        btn_refresh_Published = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        tf_ID_Published = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        tf_name_Published = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        panel_Notification = new javax.swing.JPanel();
        sp_Notification = new javax.swing.JScrollPane();
        tbl_Notification = new javax.swing.JTable();
        jLabel53 = new javax.swing.JLabel();
        tf_search_Notification = new javax.swing.JTextField();
        btn_refresh_Notification = new javax.swing.JButton();
        panel_History = new javax.swing.JPanel();
        sp_History = new javax.swing.JScrollPane();
        tbl_History = new javax.swing.JTable();
        jLabel55 = new javax.swing.JLabel();
        tf_search_History = new javax.swing.JTextField();
        btn_refresh_History = new javax.swing.JButton();
        panel_Statistics = new javax.swing.JPanel();
        panel_Setting = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("VKU Library");
        setResizable(false);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/banner.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        tbl_BookCopy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbl_BookCopyMousePressed(evt);
            }
        });
        sp_BookCopy.setViewportView(tbl_BookCopy);

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search_32.png"))); // NOI18N
        jLabel13.setText("Search");

        btn_create_BookCopy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btn_create_BookCopy.setText("Create");
        btn_create_BookCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_create_BookCopyActionPerformed(evt);
            }
        });

        btn_update_BookCopy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refresh.png"))); // NOI18N
        btn_update_BookCopy.setText("Update");
        btn_update_BookCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_update_BookCopyActionPerformed(evt);
            }
        });

        btn_delete_BookCopy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bin.png"))); // NOI18N
        btn_delete_BookCopy.setText("Delete");
        btn_delete_BookCopy.setMaximumSize(new java.awt.Dimension(89, 23));
        btn_delete_BookCopy.setMinimumSize(new java.awt.Dimension(89, 23));
        btn_delete_BookCopy.setPreferredSize(new java.awt.Dimension(89, 23));
        btn_delete_BookCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delete_BookCopyActionPerformed(evt);
            }
        });

        btn_refresh_BookCopy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/changes.png"))); // NOI18N
        btn_refresh_BookCopy.setText("Refresh");
        btn_refresh_BookCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refresh_BookCopyActionPerformed(evt);
            }
        });

        jLabel14.setText("ID");

        jLabel15.setText("Year Published");

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        jLabel19.setText("Information");

        jLabel24.setText("Book Name");

        jLabel25.setText("Published Name");

        javax.swing.GroupLayout panel_BookCopyLayout = new javax.swing.GroupLayout(panel_BookCopy);
        panel_BookCopy.setLayout(panel_BookCopyLayout);
        panel_BookCopyLayout.setHorizontalGroup(
                panel_BookCopyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel_BookCopyLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(panel_BookCopyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panel_BookCopyLayout.createSequentialGroup()
                                                .addComponent(jLabel13)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(tf_search_BookCopy))
                                        .addComponent(sp_BookCopy, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE))
                                .addGap(21, 21, 21)
                                .addGroup(panel_BookCopyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(panel_BookCopyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(panel_BookCopyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_BookCopyLayout.createSequentialGroup()
                                                                .addGroup(panel_BookCopyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel15)
                                                                        .addComponent(jLabel14))
                                                                .addGap(22, 22, 22)
                                                                .addGroup(panel_BookCopyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(tf_year_BookCopy, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(tf_ID_BookCopy, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addGroup(panel_BookCopyLayout.createSequentialGroup()
                                                                .addGroup(panel_BookCopyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel24)
                                                                        .addComponent(jLabel25))
                                                                .addGap(13, 13, 13)
                                                                .addGroup(panel_BookCopyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(cb_published_BookCopy, 0, 188, Short.MAX_VALUE)
                                                                        .addComponent(cb_book_BookCopy, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                                        .addGroup(panel_BookCopyLayout.createSequentialGroup()
                                                .addGroup(panel_BookCopyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(btn_create_BookCopy, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                                                        .addComponent(btn_delete_BookCopy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(22, 22, 22)
                                                .addGroup(panel_BookCopyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(btn_update_BookCopy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btn_refresh_BookCopy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(17, 17, 17))
        );
        panel_BookCopyLayout.setVerticalGroup(
                panel_BookCopyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel_BookCopyLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel_BookCopyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel13)
                                        .addComponent(tf_search_BookCopy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_BookCopyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(panel_BookCopyLayout.createSequentialGroup()
                                                .addGroup(panel_BookCopyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel14)
                                                        .addComponent(tf_ID_BookCopy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_BookCopyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel15)
                                                        .addComponent(tf_year_BookCopy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_BookCopyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel24)
                                                        .addComponent(cb_book_BookCopy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_BookCopyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel25)
                                                        .addComponent(cb_published_BookCopy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(24, 24, 24)
                                                .addGroup(panel_BookCopyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(btn_create_BookCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btn_update_BookCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_BookCopyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(btn_delete_BookCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btn_refresh_BookCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(sp_BookCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        main_panel.addTab("Book Copy", new javax.swing.ImageIcon(getClass().getResource("/images/stack-of-books.png")), panel_BookCopy); // NOI18N

        tbl_Patron.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbl_PatronMousePressed(evt);
            }
        });
        sp_Patron.setViewportView(tbl_Patron);

        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search_32.png"))); // NOI18N
        jLabel38.setText("Search");

        btn_create_Patron.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btn_create_Patron.setText("Create");
        btn_create_Patron.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_create_PatronActionPerformed(evt);
            }
        });

        btn_update_Patron.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refresh.png"))); // NOI18N
        btn_update_Patron.setText("Update");
        btn_update_Patron.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_update_PatronActionPerformed(evt);
            }
        });

        btn_delete_Patron.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bin.png"))); // NOI18N
        btn_delete_Patron.setText("Delete");
        btn_delete_Patron.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delete_PatronActionPerformed(evt);
            }
        });

        btn_refresh_Patron.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/changes.png"))); // NOI18N
        btn_refresh_Patron.setText("Refresh");
        btn_refresh_Patron.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refresh_PatronActionPerformed(evt);
            }
        });

        jLabel39.setText("ID");

        jLabel40.setText("First name");

        jLabel41.setText("Last Name");

        jLabel42.setText("Email");

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        jLabel43.setText("Information");

        jLabel44.setText("Password");

        jLabel45.setText("Status");

        checkBox_Patron.setText("Active");

        btn_sendAll.setText("Send Notification");
        btn_sendAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sendAllActionPerformed(evt);
            }
        });

        btn_send.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/notification.png"))); // NOI18N
        btn_send.setText("Send Message");
        btn_send.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_sendMouseClicked(evt);
            }
        });
        javax.swing.GroupLayout panel_PatronLayout = new javax.swing.GroupLayout(panel_Patron);
        panel_Patron.setLayout(panel_PatronLayout);
        panel_PatronLayout.setHorizontalGroup(
                panel_PatronLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel_PatronLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(panel_PatronLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panel_PatronLayout.createSequentialGroup()
                                                .addComponent(jLabel38)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(tf_search_Patron)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btn_sendAll, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(sp_Patron, javax.swing.GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(panel_PatronLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(panel_PatronLayout.createSequentialGroup()
                                                .addGroup(panel_PatronLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(btn_delete_Patron, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                                                        .addComponent(btn_create_Patron, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_PatronLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(btn_update_Patron, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btn_refresh_Patron, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_PatronLayout.createSequentialGroup()
                                                .addGroup(panel_PatronLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel_PatronLayout.createSequentialGroup()
                                                                .addGroup(panel_PatronLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel41)
                                                                        .addComponent(jLabel42)
                                                                        .addComponent(jLabel40)
                                                                        .addComponent(jLabel39)
                                                                        .addComponent(jLabel44)
                                                                        .addComponent(jLabel45))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(panel_PatronLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(tf_ID_Patron, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                                                                        .addComponent(tf_email_Patron)
                                                                        .addComponent(tf_lname_Patron)
                                                                        .addComponent(tf_pass_Patron)
                                                                        .addComponent(tf_fname_Patron)
                                                                        .addComponent(checkBox_Patron, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                        .addComponent(jLabel43, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(btn_send, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(17, 17, 17))
        );
        panel_PatronLayout.setVerticalGroup(
                panel_PatronLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel_PatronLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel_PatronLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel38)
                                        .addComponent(tf_search_Patron, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btn_sendAll, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_PatronLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panel_PatronLayout.createSequentialGroup()
                                                .addGroup(panel_PatronLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel39)
                                                        .addComponent(tf_ID_Patron, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_PatronLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel40)
                                                        .addComponent(tf_fname_Patron, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_PatronLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel41)
                                                        .addComponent(tf_lname_Patron, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_PatronLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel42)
                                                        .addComponent(tf_email_Patron, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_PatronLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel44)
                                                        .addComponent(tf_pass_Patron, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_PatronLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel45)
                                                        .addComponent(checkBox_Patron))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btn_send, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(panel_PatronLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(btn_create_Patron, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btn_update_Patron, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_PatronLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(btn_delete_Patron, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btn_refresh_Patron, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(sp_Patron, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(32, Short.MAX_VALUE))
        );

        main_panel.addTab("Patron Account", new javax.swing.ImageIcon(getClass().getResource("/images/user (1).png")), panel_Patron); // NOI18N

        tbl_Hold.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbl_HoldMousePressed(evt);
            }
        });
        sp_Hold.setViewportView(tbl_Hold);

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search_32.png"))); // NOI18N
        jLabel26.setText("Search");

        btn_create_Hold.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btn_create_Hold.setText("Create");
        btn_create_Hold.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_create_HoldActionPerformed(evt);
            }
        });

        btn_update_Hold.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refresh.png"))); // NOI18N
        btn_update_Hold.setText("Update");
        btn_update_Hold.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_update_HoldActionPerformed(evt);
            }
        });

        btn_delete_Hold.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bin.png"))); // NOI18N
        btn_delete_Hold.setText("Delete");
        btn_delete_Hold.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delete_HoldActionPerformed(evt);
            }
        });

        btn_refresh_Hold.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/changes.png"))); // NOI18N
        btn_refresh_Hold.setText("Refresh");
        btn_refresh_Hold.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refresh_HoldActionPerformed(evt);
            }
        });

        jLabel27.setText("ID");

        jLabel28.setText("Time Start");

        jLabel29.setText("Book Name");

        jLabel30.setText("Patron");

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        jLabel31.setText("Information");

        jLabel46.setText("Time End");

        javax.swing.GroupLayout panel_HoldLayout = new javax.swing.GroupLayout(panel_Hold);
        panel_Hold.setLayout(panel_HoldLayout);
        panel_HoldLayout.setHorizontalGroup(
                panel_HoldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel_HoldLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(panel_HoldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panel_HoldLayout.createSequentialGroup()
                                                .addComponent(jLabel26)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(tf_search_Hold))
                                        .addComponent(sp_Hold, javax.swing.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(panel_HoldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panel_HoldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addGroup(panel_HoldLayout.createSequentialGroup()
                                                        .addGroup(panel_HoldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(jLabel29)
                                                                .addComponent(jLabel30)
                                                                .addComponent(jLabel28)
                                                                .addComponent(jLabel27)
                                                                .addComponent(jLabel46))
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addGroup(panel_HoldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(tf_ID_Hold)
                                                                .addComponent(tf_start_Hold)
                                                                .addComponent(cb_book_Hold, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(cb_patron_Hold, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(tf_end_Hold)))
                                                .addGroup(panel_HoldLayout.createSequentialGroup()
                                                        .addGroup(panel_HoldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(btn_delete_Hold, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                                                                .addComponent(btn_create_Hold, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
                                                        .addGap(18, 18, 18)
                                                        .addGroup(panel_HoldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(btn_update_Hold, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(btn_refresh_Hold, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(17, 17, 17))
        );
        panel_HoldLayout.setVerticalGroup(
                panel_HoldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel_HoldLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel_HoldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel26)
                                        .addComponent(tf_search_Hold, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_HoldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(panel_HoldLayout.createSequentialGroup()
                                                .addGroup(panel_HoldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel27)
                                                        .addComponent(tf_ID_Hold, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_HoldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel28)
                                                        .addComponent(tf_start_Hold, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_HoldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel46)
                                                        .addComponent(tf_end_Hold, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_HoldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel29)
                                                        .addComponent(cb_book_Hold, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_HoldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel30)
                                                        .addComponent(cb_patron_Hold, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(99, 99, 99)
                                                .addGroup(panel_HoldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(btn_create_Hold, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btn_update_Hold, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_HoldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(btn_delete_Hold, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btn_refresh_Hold, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(sp_Hold, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        main_panel.addTab("Hold", new javax.swing.ImageIcon(getClass().getResource("/images/upload.png")), panel_Hold); // NOI18N

        tbl_Checkout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbl_CheckoutMousePressed(evt);
            }
        });
        sp_Checkout.setViewportView(tbl_Checkout);

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search_32.png"))); // NOI18N
        jLabel32.setText("Search");

        btn_create_Checkout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btn_create_Checkout.setText("Create");
        btn_create_Checkout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_create_CheckoutActionPerformed(evt);
            }
        });

        btn_update_Checkout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refresh.png"))); // NOI18N
        btn_update_Checkout.setText("Update");
        btn_update_Checkout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_update_CheckoutActionPerformed(evt);
            }
        });

        btn_delete_Checkout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bin.png"))); // NOI18N
        btn_delete_Checkout.setText("Delete");
        btn_delete_Checkout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delete_CheckoutActionPerformed(evt);
            }
        });

        btn_refresh_Checkout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/changes.png"))); // NOI18N
        btn_refresh_Checkout.setText("Refresh");
        btn_refresh_Checkout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refresh_CheckoutActionPerformed(evt);
            }
        });

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        jLabel37.setText("Information");

        jLabel47.setText("Time End");

        jLabel48.setText("ID");

        jLabel49.setText("Time Start");

        jLabel50.setText("Book Name");

        jLabel51.setText("Patron");

        checkBox_Checkout.setText("Approved");

        jLabel52.setText("Status");

        javax.swing.GroupLayout panel_CheckoutLayout = new javax.swing.GroupLayout(panel_Checkout);
        panel_Checkout.setLayout(panel_CheckoutLayout);
        panel_CheckoutLayout.setHorizontalGroup(
                panel_CheckoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel_CheckoutLayout.createSequentialGroup()
                                .addGroup(panel_CheckoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panel_CheckoutLayout.createSequentialGroup()
                                                .addGap(14, 14, 14)
                                                .addGroup(panel_CheckoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(panel_CheckoutLayout.createSequentialGroup()
                                                                .addComponent(jLabel32)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(tf_search_Checkout))
                                                        .addComponent(sp_Checkout, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE))
                                                .addGap(27, 27, 27)
                                                .addGroup(panel_CheckoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(panel_CheckoutLayout.createSequentialGroup()
                                                                .addGroup(panel_CheckoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(btn_delete_Checkout, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                                                                        .addComponent(btn_create_Checkout, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(panel_CheckoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(btn_update_Checkout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(btn_refresh_Checkout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_CheckoutLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(panel_CheckoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(panel_CheckoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(jLabel50)
                                                                .addComponent(jLabel51)
                                                                .addComponent(jLabel49)
                                                                .addComponent(jLabel48)
                                                                .addComponent(jLabel52))
                                                        .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(panel_CheckoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(tf_ID_Checkout)
                                                        .addComponent(tf_start_Checkout)
                                                        .addComponent(cb_book_Checkout, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(cb_patron_Checkout, 0, 195, Short.MAX_VALUE)
                                                        .addComponent(tf_end_Checkout, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                                                        .addComponent(checkBox_Checkout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(18, 18, 18))
        );
        panel_CheckoutLayout.setVerticalGroup(
                panel_CheckoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel_CheckoutLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel_CheckoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel32)
                                        .addComponent(tf_search_Checkout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_CheckoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panel_CheckoutLayout.createSequentialGroup()
                                                .addGroup(panel_CheckoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel48)
                                                        .addComponent(tf_ID_Checkout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_CheckoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel49)
                                                        .addComponent(tf_start_Checkout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_CheckoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel47)
                                                        .addComponent(tf_end_Checkout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_CheckoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel50)
                                                        .addComponent(cb_book_Checkout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_CheckoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel51)
                                                        .addComponent(cb_patron_Checkout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_CheckoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel52)
                                                        .addComponent(checkBox_Checkout))
                                                .addGap(59, 59, 59)
                                                .addGroup(panel_CheckoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(btn_create_Checkout, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btn_update_Checkout, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_CheckoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(btn_delete_Checkout, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btn_refresh_Checkout, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(sp_Checkout, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        main_panel.addTab("Checkout", new javax.swing.ImageIcon(getClass().getResource("/images/download (1).png")), panel_Checkout); // NOI18N

        tbl_Book.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbl_BookMousePressed(evt);
            }
        });
        sp_Book.setViewportView(tbl_Book);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search_32.png"))); // NOI18N
        jLabel2.setText("Search");

        btn_create_Book.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btn_create_Book.setText("Create");
        btn_create_Book.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_create_BookActionPerformed(evt);
            }
        });

        btn_update_Book.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refresh.png"))); // NOI18N
        btn_update_Book.setText("Update");
        btn_update_Book.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_update_BookActionPerformed(evt);
            }
        });

        btn_delete_Book.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bin.png"))); // NOI18N
        btn_delete_Book.setText("Delete");
        btn_delete_Book.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delete_BookActionPerformed(evt);
            }
        });

        btn_refresh_Book.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/changes.png"))); // NOI18N
        btn_refresh_Book.setText("Refresh");
        btn_refresh_Book.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refresh_BookActionPerformed(evt);
            }
        });

        jLabel3.setText("ID");

        jLabel4.setText("Title");

        jLabel5.setText("Category");

        jLabel6.setText("Author");

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        jLabel16.setText("Information");

        javax.swing.GroupLayout panel_BookLayout = new javax.swing.GroupLayout(panel_Book);
        panel_Book.setLayout(panel_BookLayout);
        panel_BookLayout.setHorizontalGroup(
                panel_BookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel_BookLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(panel_BookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panel_BookLayout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(tf_search_Book))
                                        .addComponent(sp_Book, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(panel_BookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(panel_BookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(panel_BookLayout.createSequentialGroup()
                                                        .addGroup(panel_BookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(jLabel5)
                                                                .addComponent(jLabel6)
                                                                .addComponent(jLabel4)
                                                                .addComponent(jLabel3))
                                                        .addGap(18, 18, 18)
                                                        .addGroup(panel_BookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(tf_ID_Book)
                                                                .addComponent(tf_title_Book)
                                                                .addComponent(cb_category_Book, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(cb_author_Book, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(panel_BookLayout.createSequentialGroup()
                                                .addGroup(panel_BookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(btn_delete_Book, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                                                        .addComponent(btn_create_Book, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_BookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(btn_update_Book, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btn_refresh_Book, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(17, 17, 17))
        );
        panel_BookLayout.setVerticalGroup(
                panel_BookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel_BookLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel_BookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(tf_search_Book, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_BookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panel_BookLayout.createSequentialGroup()
                                                .addGroup(panel_BookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel3)
                                                        .addComponent(tf_ID_Book, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_BookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel4)
                                                        .addComponent(tf_title_Book, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_BookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel5)
                                                        .addComponent(cb_category_Book, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_BookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel6)
                                                        .addComponent(cb_author_Book, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(19, 19, 19)
                                                .addGroup(panel_BookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(btn_create_Book, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btn_update_Book, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_BookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(btn_delete_Book, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btn_refresh_Book, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(sp_Book, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(29, Short.MAX_VALUE))
        );

        main_panel.addTab("Book", new javax.swing.ImageIcon(getClass().getResource("/images/book.png")), panel_Book); // NOI18N

        tbl_Author.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbl_AuthorMousePressed(evt);
            }
        });
        sp_Author.setViewportView(tbl_Author);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search_32.png"))); // NOI18N
        jLabel7.setText("Search");

        btn_create_Author.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btn_create_Author.setText("Create");
        btn_create_Author.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_create_AuthorActionPerformed(evt);
            }
        });

        btn_update_Author.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refresh.png"))); // NOI18N
        btn_update_Author.setText("Update");
        btn_update_Author.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_update_AuthorActionPerformed(evt);
            }
        });

        btn_delete_Author.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bin.png"))); // NOI18N
        btn_delete_Author.setText("Delete");
        btn_delete_Author.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delete_AuthorActionPerformed(evt);
            }
        });

        btn_refresh_Author.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/changes.png"))); // NOI18N
        btn_refresh_Author.setText("Refresh");
        btn_refresh_Author.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refresh_AuthorActionPerformed(evt);
            }
        });

        jLabel8.setText("ID");

        jLabel9.setText("Name");

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        jLabel17.setText("Information");

        javax.swing.GroupLayout panel_AuthorLayout = new javax.swing.GroupLayout(panel_Author);
        panel_Author.setLayout(panel_AuthorLayout);
        panel_AuthorLayout.setHorizontalGroup(
                panel_AuthorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel_AuthorLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(panel_AuthorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panel_AuthorLayout.createSequentialGroup()
                                                .addComponent(jLabel7)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(tf_search_Author))
                                        .addComponent(sp_Author, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(panel_AuthorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(panel_AuthorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(panel_AuthorLayout.createSequentialGroup()
                                                        .addGroup(panel_AuthorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(jLabel9)
                                                                .addComponent(jLabel8))
                                                        .addGap(34, 34, 34)
                                                        .addGroup(panel_AuthorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(tf_ID_Author, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                                                                .addComponent(tf_name_Author)))
                                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(panel_AuthorLayout.createSequentialGroup()
                                                .addGroup(panel_AuthorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(btn_delete_Author, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                                                        .addComponent(btn_create_Author, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_AuthorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(btn_update_Author, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btn_refresh_Author, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(17, 17, 17))
        );
        panel_AuthorLayout.setVerticalGroup(
                panel_AuthorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel_AuthorLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel_AuthorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel7)
                                        .addComponent(tf_search_Author, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_AuthorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panel_AuthorLayout.createSequentialGroup()
                                                .addGroup(panel_AuthorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel8)
                                                        .addComponent(tf_ID_Author, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_AuthorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel9)
                                                        .addComponent(tf_name_Author, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(21, 21, 21)
                                                .addGroup(panel_AuthorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(btn_create_Author, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btn_update_Author, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_AuthorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(btn_delete_Author, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btn_refresh_Author, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(sp_Author, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(34, Short.MAX_VALUE))
        );

        main_panel.addTab("Author", new javax.swing.ImageIcon(getClass().getResource("/images/writer.png")), panel_Author); // NOI18N

        tbl_Category.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbl_CategoryMousePressed(evt);
            }
        });
        sp_Category.setViewportView(tbl_Category);

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search_32.png"))); // NOI18N
        jLabel10.setText("Search");

        btn_create_Category.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btn_create_Category.setText("Create");
        btn_create_Category.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_create_CategoryActionPerformed(evt);
            }
        });

        btn_update_Category.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refresh.png"))); // NOI18N
        btn_update_Category.setText("Update");
        btn_update_Category.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_update_CategoryActionPerformed(evt);
            }
        });

        btn_delete_Category.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bin.png"))); // NOI18N
        btn_delete_Category.setText("Delete");
        btn_delete_Category.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delete_CategoryActionPerformed(evt);
            }
        });

        btn_refresh_Category.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/changes.png"))); // NOI18N
        btn_refresh_Category.setText("Refresh");
        btn_refresh_Category.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refresh_CategoryActionPerformed(evt);
            }
        });

        jLabel11.setText("ID");

        jLabel12.setText("Name");

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        jLabel18.setText("Information");

        javax.swing.GroupLayout panel_CategoryLayout = new javax.swing.GroupLayout(panel_Category);
        panel_Category.setLayout(panel_CategoryLayout);
        panel_CategoryLayout.setHorizontalGroup(
                panel_CategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel_CategoryLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(panel_CategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panel_CategoryLayout.createSequentialGroup()
                                                .addComponent(jLabel10)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(tf_search_Category))
                                        .addComponent(sp_Category, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(panel_CategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(panel_CategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(panel_CategoryLayout.createSequentialGroup()
                                                        .addGroup(panel_CategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(jLabel12)
                                                                .addComponent(jLabel11))
                                                        .addGap(34, 34, 34)
                                                        .addGroup(panel_CategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(tf_ID_Category, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                                                                .addComponent(tf_name_Category)))
                                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(panel_CategoryLayout.createSequentialGroup()
                                                .addGroup(panel_CategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(btn_delete_Category, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                                                        .addComponent(btn_create_Category, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_CategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(btn_update_Category, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btn_refresh_Category, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(17, 17, 17))
        );
        panel_CategoryLayout.setVerticalGroup(
                panel_CategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel_CategoryLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel_CategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel10)
                                        .addComponent(tf_search_Category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_CategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panel_CategoryLayout.createSequentialGroup()
                                                .addGroup(panel_CategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel11)
                                                        .addComponent(tf_ID_Category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_CategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel12)
                                                        .addComponent(tf_name_Category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_CategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(btn_create_Category, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btn_update_Category, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_CategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(btn_delete_Category, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btn_refresh_Category, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(sp_Category, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(34, Short.MAX_VALUE))
        );

        main_panel.addTab("Category", new javax.swing.ImageIcon(getClass().getResource("/images/tag.png")), panel_Category); // NOI18N

        tbl_Published.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbl_PublishedMousePressed(evt);
            }
        });
        sp_Published.setViewportView(tbl_Published);

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search_32.png"))); // NOI18N
        jLabel20.setText("Search");

        btn_create_Published.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btn_create_Published.setText("Create");
        btn_create_Published.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_create_PublishedActionPerformed(evt);
            }
        });

        btn_update_Published.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refresh.png"))); // NOI18N
        btn_update_Published.setText("Update");
        btn_update_Published.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_update_PublishedActionPerformed(evt);
            }
        });

        btn_delete_Published.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bin.png"))); // NOI18N
        btn_delete_Published.setText("Delete");
        btn_delete_Published.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delete_PublishedActionPerformed(evt);
            }
        });

        btn_refresh_Published.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/changes.png"))); // NOI18N
        btn_refresh_Published.setText("Refresh");
        btn_refresh_Published.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refresh_PublishedActionPerformed(evt);
            }
        });

        jLabel21.setText("ID");

        jLabel22.setText("Name");

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        jLabel23.setText("Information");

        javax.swing.GroupLayout panel_PublishedLayout = new javax.swing.GroupLayout(panel_Published);
        panel_Published.setLayout(panel_PublishedLayout);
        panel_PublishedLayout.setHorizontalGroup(
                panel_PublishedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel_PublishedLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(panel_PublishedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panel_PublishedLayout.createSequentialGroup()
                                                .addComponent(jLabel20)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(tf_search_Published))
                                        .addComponent(sp_Published, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(panel_PublishedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(panel_PublishedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(panel_PublishedLayout.createSequentialGroup()
                                                        .addGroup(panel_PublishedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(jLabel22)
                                                                .addComponent(jLabel21))
                                                        .addGap(34, 34, 34)
                                                        .addGroup(panel_PublishedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(tf_ID_Published, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                                                                .addComponent(tf_name_Published)))
                                                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(panel_PublishedLayout.createSequentialGroup()
                                                .addGroup(panel_PublishedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(btn_delete_Published, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                                                        .addComponent(btn_create_Published, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_PublishedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(btn_update_Published, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btn_refresh_Published, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(17, 17, 17))
        );
        panel_PublishedLayout.setVerticalGroup(
                panel_PublishedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel_PublishedLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel_PublishedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel20)
                                        .addComponent(tf_search_Published, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_PublishedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panel_PublishedLayout.createSequentialGroup()
                                                .addGroup(panel_PublishedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel21)
                                                        .addComponent(tf_ID_Published, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_PublishedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel22)
                                                        .addComponent(tf_name_Published, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_PublishedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(btn_create_Published, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btn_update_Published, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(panel_PublishedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(btn_delete_Published, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btn_refresh_Published, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(sp_Published, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(27, Short.MAX_VALUE))
        );

        main_panel.addTab("Published", new javax.swing.ImageIcon(getClass().getResource("/images/growth.png")), panel_Published); // NOI18N

        sp_Notification.setViewportView(tbl_Notification);

        jLabel53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search_32.png"))); // NOI18N
        jLabel53.setText("Search");

        btn_refresh_Notification.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/changes.png"))); // NOI18N
        btn_refresh_Notification.setText("Refresh");
        btn_refresh_Notification.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refresh_NotificationActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_NotificationLayout = new javax.swing.GroupLayout(panel_Notification);
        panel_Notification.setLayout(panel_NotificationLayout);
        panel_NotificationLayout.setHorizontalGroup(
                panel_NotificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel_NotificationLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(panel_NotificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(panel_NotificationLayout.createSequentialGroup()
                                                .addComponent(jLabel53)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(tf_search_Notification))
                                        .addComponent(sp_Notification, javax.swing.GroupLayout.PREFERRED_SIZE, 871, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btn_refresh_Notification, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(15, Short.MAX_VALUE))
        );
        panel_NotificationLayout.setVerticalGroup(
                panel_NotificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel_NotificationLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel_NotificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel53)
                                        .addComponent(tf_search_Notification, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sp_Notification, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_refresh_Notification, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        main_panel.addTab("Notification", new javax.swing.ImageIcon(getClass().getResource("/images/notification (1).png")), panel_Notification); // NOI18N

        sp_History.setViewportView(tbl_History);

        jLabel55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search_32.png"))); // NOI18N
        jLabel55.setText("Search");

        btn_refresh_History.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/changes.png"))); // NOI18N
        btn_refresh_History.setText("Refresh");
        btn_refresh_History.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refresh_HistoryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_HistoryLayout = new javax.swing.GroupLayout(panel_History);
        panel_History.setLayout(panel_HistoryLayout);
        panel_HistoryLayout.setHorizontalGroup(
                panel_HistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel_HistoryLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(panel_HistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(panel_HistoryLayout.createSequentialGroup()
                                                .addComponent(jLabel55)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(tf_search_History))
                                        .addComponent(sp_History, javax.swing.GroupLayout.PREFERRED_SIZE, 871, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btn_refresh_History, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(15, Short.MAX_VALUE))
        );
        panel_HistoryLayout.setVerticalGroup(
                panel_HistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel_HistoryLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel_HistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel55)
                                        .addComponent(tf_search_History, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sp_History, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_refresh_History, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        main_panel.addTab("History", new javax.swing.ImageIcon(getClass().getResource("/images/history.png")), panel_History); // NOI18N

        javax.swing.GroupLayout panel_StatisticsLayout = new javax.swing.GroupLayout(panel_Statistics);
        panel_Statistics.setLayout(panel_StatisticsLayout);
        panel_StatisticsLayout.setHorizontalGroup(
                panel_StatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 900, Short.MAX_VALUE)
        );
        panel_StatisticsLayout.setVerticalGroup(
                panel_StatisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 475, Short.MAX_VALUE)
        );

        main_panel.addTab("Statistics", new javax.swing.ImageIcon(getClass().getResource("/images/pie-chart.png")), panel_Statistics); // NOI18N

        javax.swing.GroupLayout panel_SettingLayout = new javax.swing.GroupLayout(panel_Setting);
        panel_Setting.setLayout(panel_SettingLayout);
        panel_SettingLayout.setHorizontalGroup(
                panel_SettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 900, Short.MAX_VALUE)
        );
        panel_SettingLayout.setVerticalGroup(
                panel_SettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 475, Short.MAX_VALUE)
        );

        main_panel.addTab("Setting", new javax.swing.ImageIcon(getClass().getResource("/images/setting.png")), panel_Setting); // NOI18N

        getContentPane().add(main_panel, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>         


    // Send Notification
    public void btn_sendAllActionPerformed(ActionEvent evt) {
    }

    public void btn_sendMouseClicked(java.awt.event.MouseEvent evt) {
        int selectedRow = tbl_Patron.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "You need to choose who to send to!");
            return;
        }
        String m = JOptionPane.showInputDialog(this, "What notification do you want to send?",
                "Send Notification", JOptionPane.INFORMATION_MESSAGE);
        if (m == null) {
            return;
        }
        try {
            Notification notification = new Notification();

            Date date = new Date();
            Timestamp time_now = new Timestamp(date.getTime());

            notification.setMessage(m);
            notification.setPatron_id(Integer.parseInt(tf_ID_Patron.getText()));
            notification.setSend_at(time_now);

            Response response = controller.createNotificationController(notification);
            if (response.getStatus() == 100) {
                JOptionPane.showMessageDialog(this, response.getData());
            } else {
                JOptionPane.showMessageDialog(this, "Send Notify to Patron Successfully!");
            }

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    // Block

    public boolean checkBlock(String table_name, int col_id) {
        try {
            return controller.checkLog(table_name, col_id);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    // Book - done

    public void tbl_BookMousePressed(java.awt.event.MouseEvent evt) {
        int selectedRow = tbl_Book.getSelectedRow();
        tf_ID_Book.setEditable(false);
        if (selectedRow != -1) {
            table_name = "book";
            // Lấy thông tin từ hàng dữ liệu được chọn
            int bookId = (int) tbl_Book.getValueAt(selectedRow, 0);
            String title = (String) tbl_Book.getValueAt(selectedRow, 1);
            String category = (String) tbl_Book.getValueAt(selectedRow, 2);
            String author = (String) tbl_Book.getValueAt(selectedRow, 3);


            // check block
            if (checkBlock(table_name, bookId)) {
                JOptionPane.showMessageDialog(this, "Bạn không thể thao tác với bản ghi này! Có người dùng khác đang sử dụng bản ghi này!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Set vào tf
            tf_ID_Book.setText(String.valueOf(bookId));
            tf_title_Book.setText(title);

            ComboBoxModel<Category> cb_model_category = cb_category_Book.getModel();
            for (int i = 0; i < cb_model_category.getSize(); i++) {
                if (cb_model_category.getElementAt(i).toString().equals(category)) {
                    cb_model_category.setSelectedItem(cb_model_category.getElementAt(i));
                    break;
                }
            }
            ComboBoxModel<Author> cb_model_author = cb_author_Book.getModel();
            for (int i = 0; i < cb_model_author.getSize(); i++) {
                if (cb_model_author.getElementAt(i).toString().equals(author)) {
                    cb_model_author.setSelectedItem(cb_model_author.getElementAt(i));
                    break;
                }
            }
            Date date = new Date();
            Timestamp time_now = new Timestamp(date.getTime());
            if (log == null) {
                log = new Log(ip, username, table_name, bookId, time_now);
                try {
                    log.setId(controller.createLog(log));
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            } else
                // TH: Click vào bảng khác
                if (log.getTable_name() != table_name) {

                    try {
                        controller.deleteLog(log.getId());
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                    // Lấy thời gian hiện tại

                    log = new Log(ip, username, table_name, bookId, time_now);
                    try {
                        log.setId(controller.createLog(log));
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }

                } else
                    // TH: Click vào cùng bảng
                    if (log.getTable_name().equals(table_name)) {
                        log.setCol_id(bookId);
                        try {
                            controller.updateLog(log);
                        } catch (RemoteException ex) {
                            throw new RuntimeException(ex);
                        }

                    }
            System.out.println(" LOG: " + log.toString());

        }
    }

    public void btn_refresh_BookActionPerformed(java.awt.event.ActionEvent evt) {
        tf_ID_Book.setEditable(true);

        tf_ID_Book.setText("");
        tf_title_Book.setText("");
        showTableBook();
        showDataComboBoxAuthor();
        showDataComboBoxCategory();
    }

    public void btn_delete_BookActionPerformed(java.awt.event.ActionEvent evt) {

        int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa sách này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {


            int book_id = Integer.parseInt(tf_ID_Book.getText());

            try {
                Response res = controller.deleteBookController(book_id);
                if (res.getStatus() == 100) {
                    JOptionPane.showMessageDialog(this, res.getData());
                } else {
                    showTableBook();
                    JOptionPane.showMessageDialog(this, res.getData());
                }
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
        btn_refresh_BookActionPerformed(null);
    }

    public void btn_update_BookActionPerformed(java.awt.event.ActionEvent evt) {
        Book book = new Book();
        int book_id = Integer.parseInt(tf_ID_Book.getText());
        String book_title = tf_title_Book.getText();
        Category category = (Category) cb_category_Book.getSelectedItem();
        int category_id = category.getId();
        Author author = (Author) cb_author_Book.getSelectedItem();
        int author_id = author.getId();

        book.setId(book_id);
        book.setTitle(book_title);
        book.setCategory_id(category_id);

        try {
            Response response = controller.updateBookController(book, author_id);
            if (response.getStatus() == 100) {
                JOptionPane.showMessageDialog(this, response.getData());
            } else {
                showTableBook();
                JOptionPane.showMessageDialog(this, response.getData());
            }

        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
        btn_refresh_BookActionPerformed(null);
    }

    public void btn_create_BookActionPerformed(java.awt.event.ActionEvent evt) {
        Book book = new Book();
        String book_title = tf_title_Book.getText();
        Category category = (Category) cb_category_Book.getSelectedItem();
        int category_id = category.getId();
        Author author = (Author) cb_author_Book.getSelectedItem();
        int author_id = author.getId();

        book.setTitle(book_title);
        book.setCategory_id(category_id);

        try {
            Response response = controller.createBookController(book, author_id);
            if (response.getStatus() == 100) {
                JOptionPane.showMessageDialog(this, response.getData());
            } else {
                showTableBook();
                JOptionPane.showMessageDialog(this, response.getData());
            }

        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        btn_refresh_BookActionPerformed(null);
    }
    // Author

    public void tbl_AuthorMousePressed(java.awt.event.MouseEvent evt) {
        int selectedRow = tbl_Author.getSelectedRow();
        tf_ID_Author.setEditable(false);
        if (selectedRow != -1) {
            table_name = "author";
            // Lấy thông tin từ hàng dữ liệu được chọn
            int author_id = (int) tbl_Author.getValueAt(selectedRow, 0);
            String name = (String) tbl_Author.getValueAt(selectedRow, 1);


            // check block
            if (checkBlock(table_name, author_id)) {
                JOptionPane.showMessageDialog(this, "Bạn không thể thao tác với bản ghi này! Có người dùng khác đang sử dụng bản ghi này!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Set vào tf
            tf_ID_Author.setText(String.valueOf(author_id));
            tf_name_Author.setText(name);

            Date date = new Date();
            Timestamp time_now = new Timestamp(date.getTime());
            if (log == null) {
                log = new Log(ip, username, table_name, author_id, time_now);
                try {
                    log.setId(controller.createLog(log));
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            } else
                // TH: Click vào bảng khác
                if (log.getTable_name() != table_name) {

                    try {
                        controller.deleteLog(log.getId());
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                    // Lấy thời gian hiện tại

                    log = new Log(ip, username, table_name, author_id, time_now);
                    try {
                        log.setId(controller.createLog(log));
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }

                } else
                    // TH: Click vào cùng bảng
                    if (log.getTable_name().equals(table_name)) {
                        log.setCol_id(author_id);
                        try {
                            controller.updateLog(log);
                        } catch (RemoteException ex) {
                            throw new RuntimeException(ex);
                        }

                    }
            System.out.println(" LOG: " + log.toString());

        }
    }

    public void btn_refresh_AuthorActionPerformed(java.awt.event.ActionEvent evt) {
        tf_ID_Author.setEditable(true);

        tf_ID_Author.setText("");
        tf_name_Author.setText("");
        showTableAuthor();
    }

    public void btn_delete_AuthorActionPerformed(java.awt.event.ActionEvent evt) {
        int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {


            int author_id = Integer.parseInt(tf_ID_Author.getText());

            try {
                Response res = controller.deleteAuthorController(author_id);
                if (res.getStatus() == 100) {
                    JOptionPane.showMessageDialog(this, res.getData());
                } else {
                    showTableBook();
                    JOptionPane.showMessageDialog(this, res.getData());
                }
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
        btn_refresh_BookActionPerformed(null);
    }

    public void btn_update_AuthorActionPerformed(java.awt.event.ActionEvent evt) {
        Author author = new Author();
        if (tf_ID_Author.getText() != null) {
            int author_id = Integer.parseInt(tf_ID_Author.getText());
            author.setId(author_id);
        }
        String author_name = tf_name_Author.getText();
        author.setName(author_name);

        try {
            Response response = controller.updateAuthorController(author);
            if (response.getStatus() == 100) {
                JOptionPane.showMessageDialog(this, response.getData());
            } else {
                showTableAuthor();
                JOptionPane.showMessageDialog(this, response.getData());
            }

        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
        btn_refresh_BookActionPerformed(null);
    }

    public void btn_create_AuthorActionPerformed(java.awt.event.ActionEvent evt) {
        Author author = new Author();
        if (!tf_ID_Author.getText().isEmpty()) {
            int author_id = Integer.parseInt(tf_ID_Author.getText());
            author.setId(author_id);
        }
        String name = tf_name_Author.getText();
        author.setName(name);

        try {
            Response response = controller.createAuthorController(author);
            if (response.getStatus() == 100) {
                JOptionPane.showMessageDialog(this, response.getData());
            } else {
                showTableAuthor();
                JOptionPane.showMessageDialog(this, response.getData());
            }

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        btn_refresh_AuthorActionPerformed(null);
    }
    // Category

    public void tbl_CategoryMousePressed(java.awt.event.MouseEvent evt) {
        int selectedRow = tbl_Category.getSelectedRow();
        tf_ID_Category.setEditable(false);
        if (selectedRow != -1) {
            table_name = "category";
            // Lấy thông tin từ hàng dữ liệu được chọn
            int category_id = (int) tbl_Category.getValueAt(selectedRow, 0);
            String name = (String) tbl_Category.getValueAt(selectedRow, 1);


            // check block
            if (checkBlock(table_name, category_id)) {
                JOptionPane.showMessageDialog(this, "Bạn không thể thao tác với bản ghi này! Có người dùng khác đang sử dụng bản ghi này!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Set vào tf
            tf_ID_Category.setText(String.valueOf(category_id));
            tf_name_Category.setText(name);

            Date date = new Date();
            Timestamp time_now = new Timestamp(date.getTime());
            if (log == null) {
                log = new Log(ip, username, table_name, category_id, time_now);
                try {
                    log.setId(controller.createLog(log));
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            } else
                // TH: Click vào bảng khác
                if (log.getTable_name() != table_name) {

                    try {
                        controller.deleteLog(log.getId());
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                    // Lấy thời gian hiện tại

                    log = new Log(ip, username, table_name, category_id, time_now);
                    try {
                        log.setId(controller.createLog(log));
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }

                } else
                    // TH: Click vào cùng bảng
                    if (log.getTable_name().equals(table_name)) {
                        log.setCol_id(category_id);
                        try {
                            controller.updateLog(log);
                        } catch (RemoteException ex) {
                            throw new RuntimeException(ex);
                        }

                    }
            System.out.println(" LOG: " + log.toString());

        }
    }

    public void btn_refresh_CategoryActionPerformed(java.awt.event.ActionEvent evt) {
        tf_ID_Category.setEditable(true);

        tf_ID_Category.setText("");
        tf_name_Category.setText("");
        showTableCategory();
    }

    public void btn_delete_CategoryActionPerformed(java.awt.event.ActionEvent evt) {
        int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {


            int category_id = Integer.parseInt(tf_ID_Category.getText());

            try {
                Response res = controller.deleteCategoryController(category_id);
                if (res.getStatus() == 100) {
                    JOptionPane.showMessageDialog(this, res.getData());
                } else {
                    showTableCategory();
                    JOptionPane.showMessageDialog(this, res.getData());
                }
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
        btn_refresh_CategoryActionPerformed(null);
    }

    public void btn_update_CategoryActionPerformed(java.awt.event.ActionEvent evt) {
        Category category = new Category();
        if (tf_ID_Category.getText() != null) {
            int category_id = Integer.parseInt(tf_ID_Category.getText());
            category.setId(category_id);
        }
        String category_name = tf_name_Category.getText();
        category.setName(category_name);

        try {
            Response response = controller.updateCategoryController(category);
            if (response.getStatus() == 100) {
                JOptionPane.showMessageDialog(this, response.getData());
            } else {
                showTableCategory();
                JOptionPane.showMessageDialog(this, response.getData());
            }

        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
        btn_refresh_BookActionPerformed(null);
    }

    public void btn_create_CategoryActionPerformed(java.awt.event.ActionEvent evt) {
        Category category = new Category();
        if (!tf_ID_Category.getText().isEmpty()) {
            int category_id = Integer.parseInt(tf_ID_Category.getText());
            category.setId(category_id);
        }
        String name = tf_name_Category.getText();
        category.setName(name);

        try {
            Response response = controller.createCategoryController(category);
            if (response.getStatus() == 100) {
                JOptionPane.showMessageDialog(this, response.getData());
            } else {
                showTableCategory();
                JOptionPane.showMessageDialog(this, response.getData());
            }

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        btn_refresh_CategoryActionPerformed(null);
    }

    // Patron Account
    public void tbl_PatronMousePressed(java.awt.event.MouseEvent evt) {
        int selectedRow = tbl_Patron.getSelectedRow();
        tf_ID_Patron.setEditable(false);
        if (selectedRow != -1) {
            table_name = "patron_account";
            // Lấy thông tin từ hàng dữ liệu được chọn
            int author_id = (int) tbl_Patron.getValueAt(selectedRow, 0);
            String fname = (String) tbl_Patron.getValueAt(selectedRow, 1);
            String lname = (String) tbl_Patron.getValueAt(selectedRow, 2);
            String email = (String) tbl_Patron.getValueAt(selectedRow, 3);
            String status = (String) tbl_Patron.getValueAt(selectedRow, 4);


            // check block
            if (checkBlock(table_name, author_id)) {
                JOptionPane.showMessageDialog(this, "Bạn không thể thao tác với bản ghi này! Có người dùng khác đang sử dụng bản ghi này!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Set vào tf
            tf_ID_Patron.setText(String.valueOf(author_id));
            tf_fname_Patron.setText(fname);
            tf_lname_Patron.setText(lname);
            tf_email_Patron.setText(email);
            if (status.equals("Available")) {
                checkBox_Patron.setEnabled(true);
            } else {
                checkBox_Patron.setEnabled(false);
            }

            Date date = new Date();
            Timestamp time_now = new Timestamp(date.getTime());
            if (log == null) {
                log = new Log(ip, username, table_name, author_id, time_now);
                try {
                    log.setId(controller.createLog(log));
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            } else
                // TH: Click vào bảng khác
                if (log.getTable_name() != table_name) {

                    try {
                        controller.deleteLog(log.getId());
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                    // Lấy thời gian hiện tại

                    log = new Log(ip, username, table_name, author_id, time_now);
                    try {
                        log.setId(controller.createLog(log));
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }

                } else
                    // TH: Click vào cùng bảng
                    if (log.getTable_name().equals(table_name)) {
                        log.setCol_id(author_id);
                        try {
                            controller.updateLog(log);
                        } catch (RemoteException ex) {
                            throw new RuntimeException(ex);
                        }

                    }
            System.out.println(" LOG: " + log.toString());

        }
    }

    public void btn_refresh_PatronActionPerformed(java.awt.event.ActionEvent evt) {
        tf_ID_Patron.setEditable(true);

        tf_ID_Patron.setText("");
        tf_fname_Patron.setText("");
        tf_lname_Patron.setText("");
        tf_email_Patron.setText("");
        tf_pass_Patron.setText("");

        showTableNotification();
    }

    public void btn_delete_PatronActionPerformed(java.awt.event.ActionEvent evt) {
        int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {


            int patron_id = Integer.parseInt(tf_ID_Patron.getText());

            try {
                Response res = controller.deleteControllerPatron(patron_id);
                if (res.getStatus() == 100) {
                    JOptionPane.showMessageDialog(this, res.getData());
                } else {
                    showTablePatrons();
                    JOptionPane.showMessageDialog(this, res.getData());
                }
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
        btn_refresh_PatronActionPerformed(null);
    }


    public void btn_update_PatronActionPerformed(java.awt.event.ActionEvent evt) {
        Patron patron = new Patron();
        int patron_id = Integer.parseInt(tf_ID_Patron.getText());
        String patron_fname = tf_fname_Patron.getText();
        String patron_lname =  tf_lname_Patron.getText();
        String patron_email = tf_email_Patron.getText();




        patron.setId(patron_id);
        patron.setFirstName(patron_fname);
        patron.setLastName(patron_lname);
        patron.setEmail(patron_email);




        try {
                Response response = controller.updatePatronController(patron);
                if (response.getStatus() == 100) {
                    JOptionPane.showMessageDialog(this, response.getData());
                } else {
                    showTablePatrons();
                    JOptionPane.showMessageDialog(this, response.getData());
                }

            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
            btn_refresh_PatronActionPerformed(null);


    }

    public void btn_create_PatronActionPerformed(java.awt.event.ActionEvent evt) {
        Patron patron = new Patron();
        if (!tf_ID_Patron.getText().isEmpty()) {
            int patron_id = Integer.parseInt(tf_ID_Patron.getText());
            patron.setId(patron_id);
        }
        String patron_fname = tf_fname_Patron.getText();
        String patron_lname =  tf_lname_Patron.getText();
        String patron_email = tf_email_Patron.getText();
        String patron_pass  = tf_pass_Patron.getText();


        patron.setFirstName(patron_fname);
        patron.setLastName(patron_lname);
        patron.setEmail(patron_email);
        patron.setPassword(patron_pass);

        try {
            Response response = controller.createPatronController(patron);
            if (response.getStatus() == 100) {
                JOptionPane.showMessageDialog(this, response.getData());
            } else {
                showTablePatrons();
                JOptionPane.showMessageDialog(this, response.getData());
            }

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        btn_refresh_PatronActionPerformed(null);
    }


    // Hold
    public void tbl_HoldMousePressed(java.awt.event.MouseEvent evt) {
        int selectedRow = tbl_Hold.getSelectedRow();
        tf_ID_Hold.setEditable(false);
        if (selectedRow != -1) {
            table_name = "hold";
            // Lấy thông tin từ hàng dữ liệu được chọn
            int hold_id = (int) tbl_Hold.getValueAt(selectedRow, 0);
            String patron = (String) tbl_Hold.getValueAt(selectedRow, 1);
            String book_copy = (String) tbl_Hold.getValueAt(selectedRow, 3);
            Timestamp time_start = (Timestamp) tbl_Hold.getValueAt(selectedRow, 4);
            Timestamp time_end = (Timestamp) tbl_Hold.getValueAt(selectedRow, 5);

            // check block
            if (checkBlock(table_name, hold_id)) {
                JOptionPane.showMessageDialog(this, "Bạn không thể thao tác với bản ghi này! Có người dùng khác đang sử dụng bản ghi này!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Set vào tf
            tf_ID_Hold.setText(String.valueOf(hold_id));
            tf_start_Hold.setText(String.valueOf(time_start));
            tf_end_Hold.setText(String.valueOf(time_end));

            ComboBoxModel<BookCopy> cb_model_bookcopy = cb_book_Hold.getModel();
            for (int i = 0; i < cb_model_bookcopy.getSize(); i++) {
                if (cb_model_bookcopy.getElementAt(i).toString().equals(book_copy)) {
                    cb_model_bookcopy.setSelectedItem(cb_model_bookcopy.getElementAt(i));
                    break;
                }
            }
            ComboBoxModel<Patron> cb_model_patron = cb_patron_Hold.getModel();
            for (int i = 0; i < cb_model_patron.getSize(); i++) {
                if (cb_model_patron.getElementAt(i).toString().equals(patron)) {
                    cb_model_patron.setSelectedItem(cb_model_patron.getElementAt(i));
                    break;
                }
            }

            Date date = new Date();
            Timestamp time_now = new Timestamp(date.getTime());
            if (log == null) {
                log = new Log(ip, username, table_name, hold_id, time_now);
                try {
                    log.setId(controller.createLog(log));
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            } else
                // TH: Click vào bảng khác
                if (log.getTable_name() != table_name) {

                    try {
                        controller.deleteLog(log.getId());
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                    // Lấy thời gian hiện tại

                    log = new Log(ip, username, table_name, hold_id, time_now);
                    try {
                        log.setId(controller.createLog(log));
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }

                } else
                    // TH: Click vào cùng bảng
                    if (log.getTable_name().equals(table_name)) {
                        log.setCol_id(hold_id);
                        try {
                            controller.updateLog(log);
                        } catch (RemoteException ex) {
                            throw new RuntimeException(ex);
                        }

                    }
            System.out.println(" LOG: " + log.toString());

        }
    }

    public void btn_create_HoldActionPerformed(java.awt.event.ActionEvent evt) {
        Hold hold = new Hold();
        String start = tf_start_Hold.getText();
        Timestamp timestamp_start = Timestamp.valueOf(start);
        String end = tf_end_Hold.getText();
        Timestamp timestamp_end = Timestamp.valueOf(end);
        BookCopy bookCopy = (BookCopy) cb_book_Hold.getSelectedItem();
        int bookCopyId = bookCopy.getId();
        Patron patron = (Patron) cb_patron_Hold.getSelectedItem();
        int patronId = patron.getId();

        hold.setStart_time(timestamp_start);
        hold.setEnd_time(timestamp_end);
        hold.setBook_copy_id(bookCopyId);
        hold.setPatron_id(patronId);

        try {
            Response response = controller.createHoldController(hold);
            if (response.getStatus() == 100) {
                JOptionPane.showMessageDialog(this, response.getData());
            } else {
                showTableBook();
                JOptionPane.showMessageDialog(this, response.getData());
            }

        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
        btn_refresh_HoldActionPerformed(null);
    }

    public void btn_refresh_HoldActionPerformed(java.awt.event.ActionEvent evt) {
        tf_ID_Hold.setEditable(true);
        long currentTimeMillis = System.currentTimeMillis();
        Timestamp now = new Timestamp(currentTimeMillis);
        tf_ID_Hold.setText("");
        tf_start_Hold.setText(now.toString());
        tf_end_Hold.setText(now.toString());
        showTableHold();
    }

    public void btn_delete_HoldActionPerformed(java.awt.event.ActionEvent evt) {
        int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa phiếu mượn này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {


            int holdId = Integer.parseInt(tf_ID_Hold.getText());

            try {
                Response res = controller.deleteHoldController(holdId);
                if (res.getStatus() == 100) {
                    JOptionPane.showMessageDialog(this, res.getData());
                } else {
                    showTableBook();
                    JOptionPane.showMessageDialog(this, res.getData());
                }
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
        btn_refresh_HoldActionPerformed(null);
    }

    public void btn_update_HoldActionPerformed(java.awt.event.ActionEvent evt) {
        Hold hold = new Hold();
        int holdId = Integer.parseInt(tf_ID_Hold.getText());
        hold.setId(holdId);
        String start = tf_start_Hold.getText();
        Timestamp timestamp_start = Timestamp.valueOf(start);
        String end = tf_end_Hold.getText();
        Timestamp timestamp_end = Timestamp.valueOf(end);
        BookCopy bookCopy = (BookCopy) cb_book_Hold.getSelectedItem();
        int bookCopyId = bookCopy.getId();
        Patron patron = (Patron) cb_patron_Hold.getSelectedItem();
        int patronId = patron.getId();

        hold.setStart_time(timestamp_start);
        hold.setEnd_time(timestamp_end);
        hold.setBook_copy_id(bookCopyId);
        hold.setPatron_id(patronId);

        try {
            Response response = controller.updateHoldController(hold);
            if (response.getStatus() == 100) {
                JOptionPane.showMessageDialog(this, response.getData());
            } else {
                showTableBook();
                JOptionPane.showMessageDialog(this, response.getData());
            }

        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
        btn_refresh_HoldActionPerformed(null);
    }


    // Published
    public void tbl_PublishedMousePressed(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:

    }

    public void btn_refresh_PublishedActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    public void btn_delete_PublishedActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    public void btn_update_PublishedActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    public void btn_create_PublishedActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    // Checkout

    public void tbl_CheckoutMousePressed(java.awt.event.MouseEvent evt) {
        int selectedRow = tbl_Checkout.getSelectedRow();
        tf_ID_Checkout.setEditable(false);
        if (selectedRow != -1) {
            table_name = "checkout";
            // Lấy thông tin từ hàng dữ liệu được chọn
            int checkout_id = (int) tbl_Checkout.getValueAt(selectedRow, 0);
            String patron = (String) tbl_Checkout.getValueAt(selectedRow, 1);
            String book_copy = (String) tbl_Checkout.getValueAt(selectedRow, 3);
            Timestamp time_start = (Timestamp) tbl_Checkout.getValueAt(selectedRow, 4);
            Timestamp time_end = (Timestamp) tbl_Checkout.getValueAt(selectedRow, 5);
            String status = (String) tbl_Checkout.getValueAt(selectedRow, 6);

            if (status.equals("Yes")) {
                checkBox_Checkout.setSelected(true);
            }
            else {
                checkBox_Checkout.setSelected(false);
            }

            // check block
            if (checkBlock(table_name, checkout_id)) {
                JOptionPane.showMessageDialog(this, "Bạn không thể thao tác với bản ghi này! Có người dùng khác đang sử dụng bản ghi này!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Set vào tf
            tf_ID_Checkout.setText(String.valueOf(checkout_id));
            tf_start_Checkout.setText(String.valueOf(time_start));
            tf_end_Checkout.setText(String.valueOf(time_end));

            ComboBoxModel<BookCopy> cb_model_bookcopy = cb_book_Checkout.getModel();
            for (int i = 0; i < cb_model_bookcopy.getSize(); i++) {
                if (cb_model_bookcopy.getElementAt(i).toString().equals(book_copy)) {
                    cb_model_bookcopy.setSelectedItem(cb_model_bookcopy.getElementAt(i));
                    break;
                }
            }
            ComboBoxModel<Patron> cb_model_patron = cb_patron_Checkout.getModel();
            for (int i = 0; i < cb_model_patron.getSize(); i++) {
                if (cb_model_patron.getElementAt(i).toString().equals(patron)) {
                    cb_model_patron.setSelectedItem(cb_model_patron.getElementAt(i));
                    break;
                }
            }

            Date date = new Date();
            Timestamp time_now = new Timestamp(date.getTime());
            if (log == null) {
                log = new Log(ip, username, table_name, checkout_id, time_now);
                try {
                    log.setId(controller.createLog(log));
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            } else
                // TH: Click vào bảng khác
                if (log.getTable_name() != table_name) {

                    try {
                        controller.deleteLog(log.getId());
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                    // Lấy thời gian hiện tại

                    log = new Log(ip, username, table_name, checkout_id, time_now);
                    try {
                        log.setId(controller.createLog(log));
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }

                } else
                    // TH: Click vào cùng bảng
                    if (log.getTable_name().equals(table_name)) {
                        log.setCol_id(checkout_id);
                        try {
                            controller.updateLog(log);
                        } catch (RemoteException ex) {
                            throw new RuntimeException(ex);
                        }

                    }
            System.out.println(" LOG: " + log.toString());

        }
    }

    public void btn_create_CheckoutActionPerformed(java.awt.event.ActionEvent evt) {
        Checkout checkout = new Checkout();
        String start = tf_start_Checkout.getText();
        Timestamp timestamp_start = Timestamp.valueOf(start);
        String end = tf_end_Checkout.getText();
        Timestamp timestamp_end = Timestamp.valueOf(end);
        BookCopy bookCopy = (BookCopy) cb_book_Checkout.getSelectedItem();
        int bookCopyId = bookCopy.getId();
        Patron patron = (Patron) cb_patron_Checkout.getSelectedItem();
        int patronId = patron.getId();

        checkout.setStart_time(timestamp_start);
        checkout.setEnd_time(timestamp_end);
        checkout.setBook_copy_id(bookCopyId);
        checkout.setPatron_id(patronId);

        try {
            Response response = controller.createCheckoutController(checkout);
            if (response.getStatus() == 100) {
                JOptionPane.showMessageDialog(this, response.getData());
            } else {
                showTableCheckout();
                JOptionPane.showMessageDialog(this, response.getData());
            }

        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
        btn_refresh_CheckoutActionPerformed(null);
    }

    public void btn_update_CheckoutActionPerformed(java.awt.event.ActionEvent evt) {
        Checkout checkout = new Checkout();
        int checkoutId = Integer.parseInt(tf_ID_Checkout.getText());
        checkout.setId(checkoutId);
        String start = tf_start_Checkout.getText();
        Timestamp timestamp_start = Timestamp.valueOf(start);
        String end = tf_end_Checkout.getText();
        Timestamp timestamp_end = Timestamp.valueOf(end);
        BookCopy bookCopy = (BookCopy) cb_book_Checkout.getSelectedItem();
        int bookCopyId = bookCopy.getId();
        Patron patron = (Patron) cb_patron_Checkout.getSelectedItem();
        int patronId = patron.getId();
        Boolean status = checkBox_Checkout.isSelected();


        checkout.setStart_time(timestamp_start);
        checkout.setEnd_time(timestamp_end);
        checkout.setBook_copy_id(bookCopyId);
        checkout.setPatron_id(patronId);
        checkout.setIs_returned(status);

        try {
            Response response = controller.updateCheckoutController(checkout);
            if (response.getStatus() == 100) {
                JOptionPane.showMessageDialog(this, response.getData());
            } else {
                showTableCheckout();
                JOptionPane.showMessageDialog(this, response.getData());
            }

        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
        btn_refresh_CheckoutActionPerformed(null);
    }

    public void btn_delete_CheckoutActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa sách này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {


            int checkoutId = Integer.parseInt(tf_ID_Checkout.getText());

            try {
                Response res = controller.deleteCheckoutController(checkoutId);
                if (res.getStatus() == 100) {
                    JOptionPane.showMessageDialog(this, res.getData());
                } else {
                    showTableCheckout();
                    JOptionPane.showMessageDialog(this, res.getData());
                }
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
        btn_refresh_CheckoutActionPerformed(null);
    }

    public void btn_refresh_CheckoutActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        tf_ID_Checkout.setEditable(true);
        long currentTimeMillis = System.currentTimeMillis();
        Timestamp now = new Timestamp(currentTimeMillis);
        tf_ID_Checkout.setText("");
        tf_start_Checkout.setText(now.toString());
        tf_end_Checkout.setText(now.toString());
        showTableCheckout();
    }

    // Book Copy
    public void tbl_BookCopyMousePressed(java.awt.event.MouseEvent evt) {
        int selectedRow = tbl_BookCopy.getSelectedRow();
        tf_ID_BookCopy.setEditable(false);
        if (selectedRow != -1) {
            table_name = "book_copy";
            // Lấy thông tin từ hàng dữ liệu được chọn
            int book_copy_id = (int) tbl_BookCopy.getValueAt(selectedRow, 0);
            String book = (String) tbl_BookCopy.getValueAt(selectedRow, 1);
            int year_publish = (int) tbl_BookCopy.getValueAt(selectedRow, 2);
            String published = (String) tbl_BookCopy.getValueAt(selectedRow, 3);

            // check block
            if (checkBlock(table_name, book_copy_id)) {
                JOptionPane.showMessageDialog(this, "Bạn không thể thao tác với bản ghi này! Có người dùng khác đang sử dụng bản ghi này!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Set vào tf
            tf_ID_BookCopy.setText(String.valueOf(book_copy_id));
            tf_year_BookCopy.setText(String.valueOf(year_publish));

            ComboBoxModel<Book> cb_model_book = cb_book_BookCopy.getModel();
            for (int i = 0; i < cb_model_book.getSize(); i++) {
                if (cb_model_book.getElementAt(i).toString().equals(book)) {
                    cb_model_book.setSelectedItem(cb_model_book.getElementAt(i));
                    break;
                }
            }
            ComboBoxModel<Published> cb_model_published = cb_published_BookCopy.getModel();
            for (int i = 0; i < cb_model_published.getSize(); i++) {
                if (cb_model_published.getElementAt(i).toString().equals(published)) {
                    cb_model_published.setSelectedItem(cb_model_published.getElementAt(i));
                    break;
                }
            }

            Date date = new Date();
            Timestamp time_now = new Timestamp(date.getTime());
            if (log == null) {
                log = new Log(ip, username, table_name, book_copy_id, time_now);
                try {
                    log.setId(controller.createLog(log));
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            } else
                // TH: Click vào bảng khác
                if (log.getTable_name() != table_name) {

                    try {
                        controller.deleteLog(log.getId());
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                    // Lấy thời gian hiện tại

                    log = new Log(ip, username, table_name, book_copy_id, time_now);
                    try {
                        log.setId(controller.createLog(log));
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }

                } else
                    // TH: Click vào cùng bảng
                    if (log.getTable_name().equals(table_name)) {
                        log.setCol_id(book_copy_id);
                        try {
                            controller.updateLog(log);
                        } catch (RemoteException ex) {
                            throw new RuntimeException(ex);
                        }

                    }
            System.out.println(" LOG: " + log.toString());

        }
    }

    public void btn_create_BookCopyActionPerformed(java.awt.event.ActionEvent evt) {
        Book book = (Book) cb_book_BookCopy.getSelectedItem();
        int book_id = book.getId();
        Published published = (Published) cb_published_BookCopy.getSelectedItem();
        int published_id = published.getId();
        int year = Integer.parseInt(tf_year_BookCopy.getText());
        BookCopy bookCopy = new BookCopy();
        bookCopy.setPublished_id(published_id);
        bookCopy.setBook_id(book_id);
        bookCopy.setYear_published(year);
        try {
            Response response = controller.createBookCopyController(bookCopy);
            if (response.getStatus() == 100) {
                JOptionPane.showMessageDialog(this, response.getData());
            } else {
                showTableBookCopy();
                JOptionPane.showMessageDialog(this, response.getData());
            }

        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
        btn_refresh_BookActionPerformed(null);
    }

    public void btn_delete_BookCopyActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    public void btn_update_BookCopyActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    public void btn_refresh_BookCopyActionPerformed(java.awt.event.ActionEvent evt) {
        tf_ID_BookCopy.setEditable(true);
        tf_ID_BookCopy.setText("");
        tf_year_BookCopy.setText("");
        showTableBookCopy();
    }


    //
    public void btn_refresh_HistoryActionPerformed(ActionEvent evt) {
        showTableHistory();
    }

    public void btn_refresh_NotificationActionPerformed(ActionEvent evt) {
        showTableNotification();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ManageGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManageGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManageGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManageGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManageGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JButton btn_create_Author;
    private javax.swing.JButton btn_create_Book;
    private javax.swing.JButton btn_create_BookCopy;
    private javax.swing.JButton btn_create_Category;
    private javax.swing.JButton btn_create_Checkout;
    private javax.swing.JButton btn_create_Hold;
    private javax.swing.JButton btn_create_Patron;
    private javax.swing.JButton btn_create_Published;
    private javax.swing.JButton btn_delete_Author;
    private javax.swing.JButton btn_delete_Book;
    private javax.swing.JButton btn_delete_BookCopy;
    private javax.swing.JButton btn_delete_Category;
    private javax.swing.JButton btn_delete_Checkout;
    private javax.swing.JButton btn_delete_Hold;
    private javax.swing.JButton btn_delete_Patron;
    private javax.swing.JButton btn_delete_Published;
    private javax.swing.JButton btn_refresh_Author;
    private javax.swing.JButton btn_refresh_Book;
    private javax.swing.JButton btn_refresh_BookCopy;
    private javax.swing.JButton btn_refresh_Category;
    private javax.swing.JButton btn_refresh_Checkout;
    private javax.swing.JButton btn_refresh_History;
    private javax.swing.JButton btn_refresh_Hold;
    private javax.swing.JButton btn_refresh_Notification;
    private javax.swing.JButton btn_refresh_Patron;
    private javax.swing.JButton btn_refresh_Published;
    private javax.swing.JButton btn_send;
    private javax.swing.JButton btn_sendAll;
    private javax.swing.JButton btn_update_Author;
    private javax.swing.JButton btn_update_Book;
    private javax.swing.JButton btn_update_BookCopy;
    private javax.swing.JButton btn_update_Category;
    private javax.swing.JButton btn_update_Checkout;
    private javax.swing.JButton btn_update_Hold;
    private javax.swing.JButton btn_update_Patron;
    private javax.swing.JButton btn_update_Published;
    private javax.swing.JComboBox cb_author_Book;
    private javax.swing.JComboBox cb_book_BookCopy;
    private javax.swing.JComboBox cb_book_Checkout;
    private javax.swing.JComboBox cb_book_Hold;
    private javax.swing.JComboBox cb_category_Book;
    private javax.swing.JComboBox cb_patron_Checkout;
    private javax.swing.JComboBox cb_patron_Hold;
    private javax.swing.JComboBox cb_published_BookCopy;
    private javax.swing.JCheckBox checkBox_Checkout;
    private javax.swing.JCheckBox checkBox_Patron;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTabbedPane main_panel;
    private javax.swing.JPanel panel_Author;
    private javax.swing.JPanel panel_Book;
    private javax.swing.JPanel panel_BookCopy;
    private javax.swing.JPanel panel_Category;
    private javax.swing.JPanel panel_Checkout;
    private javax.swing.JPanel panel_History;
    private javax.swing.JPanel panel_Hold;
    private javax.swing.JPanel panel_Notification;
    private javax.swing.JPanel panel_Patron;
    private javax.swing.JPanel panel_Published;
    private javax.swing.JPanel panel_Setting;
    private javax.swing.JPanel panel_Statistics;
    private javax.swing.JScrollPane sp_Author;
    private javax.swing.JScrollPane sp_Book;
    private javax.swing.JScrollPane sp_BookCopy;
    private javax.swing.JScrollPane sp_Category;
    private javax.swing.JScrollPane sp_Checkout;
    private javax.swing.JScrollPane sp_History;
    private javax.swing.JScrollPane sp_Hold;
    private javax.swing.JScrollPane sp_Notification;
    private javax.swing.JScrollPane sp_Patron;
    private javax.swing.JScrollPane sp_Published;
    private javax.swing.JTable tbl_Author;
    private javax.swing.JTable tbl_Book;
    private javax.swing.JTable tbl_BookCopy;
    private javax.swing.JTable tbl_Category;
    private javax.swing.JTable tbl_Checkout;
    private javax.swing.JTable tbl_History;
    private javax.swing.JTable tbl_Hold;
    private javax.swing.JTable tbl_Notification;
    private javax.swing.JTable tbl_Patron;
    private javax.swing.JTable tbl_Published;
    private javax.swing.JTextField tf_ID_Author;
    private javax.swing.JTextField tf_ID_Book;
    private javax.swing.JTextField tf_ID_BookCopy;
    private javax.swing.JTextField tf_ID_Category;
    private javax.swing.JTextField tf_ID_Checkout;
    private javax.swing.JTextField tf_ID_Hold;
    private javax.swing.JTextField tf_ID_Patron;
    private javax.swing.JTextField tf_ID_Published;
    private javax.swing.JTextField tf_email_Patron;
    private javax.swing.JTextField tf_end_Checkout;
    private javax.swing.JTextField tf_end_Hold;
    private javax.swing.JTextField tf_fname_Patron;
    private javax.swing.JTextField tf_lname_Patron;
    private javax.swing.JTextField tf_name_Author;
    private javax.swing.JTextField tf_name_Category;
    private javax.swing.JTextField tf_name_Published;
    private javax.swing.JTextField tf_pass_Patron;
    private javax.swing.JTextField tf_search_Author;
    private javax.swing.JTextField tf_search_Book;
    private javax.swing.JTextField tf_search_BookCopy;
    private javax.swing.JTextField tf_search_Category;
    private javax.swing.JTextField tf_search_Checkout;
    private javax.swing.JTextField tf_search_History;
    private javax.swing.JTextField tf_search_Hold;
    private javax.swing.JTextField tf_search_Notification;
    private javax.swing.JTextField tf_search_Patron;
    private javax.swing.JTextField tf_search_Published;
    private javax.swing.JTextField tf_start_Checkout;
    private javax.swing.JTextField tf_start_Hold;
    private javax.swing.JTextField tf_title_Book;
    private javax.swing.JTextField tf_year_BookCopy;


    // End of variables declaration                   
}

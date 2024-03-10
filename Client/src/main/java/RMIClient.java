import javax.swing.*;

public class RMIClient {
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
//        new RegisterGUI().setVisible(true);
//        new LoginGUI().setVisible(true);
        new ManageGUI().setVisible(true);

    }


}

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class CenteredTableCellRenderer extends DefaultTableCellRenderer {
    public CenteredTableCellRenderer() {
        setHorizontalAlignment(JLabel.CENTER);
    }
}
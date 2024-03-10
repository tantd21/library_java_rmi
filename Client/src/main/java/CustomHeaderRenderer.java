import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class CustomHeaderRenderer extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        label.setBackground(Color.BLUE); // Đặt màu nền là màu xanh
        label.setForeground(Color.WHITE); // Đặt màu chữ là màu trắng
        label.setFont(label.getFont().deriveFont(Font.BOLD)); // Đặt chữ in đậm
        label.setHorizontalAlignment(JLabel.CENTER); // Căn giữa tiêu đề
        return label;
    }
}

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class MachineTableModel extends AbstractTableModel {
    private static final String[] COLUMN_NAMES = new String[] {
            "ID", "Type", "Host", "Core", "Memory", "Time", "Action"
    };
    private ArrayList<ArrayList<Object>> data = new ArrayList<>();

    public void addRow(ArrayList<Object> row) {
        data.add(row);
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public Class<?> getColumnClass(int column) {
        if(column == 6) {
            return JButton.class;
        }
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(columnIndex == 6) {
            return true;
        }
        return true;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex).get(columnIndex);
    }
}

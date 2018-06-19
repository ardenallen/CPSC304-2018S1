package layout.classGUI.manager;

import layout.MainFrame;
import model.Employee;
import model.Manager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Date;
import java.util.List;

public class ManagerEmployeeStatForm {
    private JButton minButton;
    private JButton maxButton;
    private JButton backButton;
    private JPanel mainPanel;
    private JTable employeeTable;
    private JTextField pleaseEnterTheDateTextField;

    private MainFrame mainFrame;

    public ManagerEmployeeStatForm(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        /*
         * Table setup
         */
        Object[] headers = {"Rank", "EID", "Name"};

        employeeTable = new JTable(new DefaultTableModel(headers, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }});
        DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();

        // Getting date from text box??
        Date inputDate = Date.valueOf(pleaseEnterTheDateTextField.getText().trim());

        /*
         * Min button setup
         */
        minButton = new JButton("Minimum sale");
        minButton.addActionListener(e -> {
            model.setRowCount(0);
            List<Employee> minEmployeeList = Manager.getLeastMostSalesEmployee("min", inputDate);

            int count = 1;

            for (Employee employee : minEmployeeList) {
                model.addRow(new Object[] {count, employee.getUserId(), employee.getName()});
                count++;
            }

            model.fireTableDataChanged();
        });

        /*
         * Max button setup
         */
        maxButton = new JButton("Maximum sale");
        maxButton.addActionListener(e -> {
            model.setRowCount(0);

            List<Employee> maxEmployeeList = Manager.getLeastMostSalesEmployee("max", inputDate);

            int count = 1;

            for (Employee employee : maxEmployeeList) {
                model.addRow(new Object[] {count, employee.getUserId(), employee.getName()});
                count++;
            }

            model.fireTableDataChanged();
        });

        /*
         * Back button setup
         */
        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            mainFrame.backToManagerMainForm();
        });
    }
}
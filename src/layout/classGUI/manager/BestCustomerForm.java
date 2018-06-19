package layout.classGUI.manager;

import layout.MainFrame;
import model.Customer;
import model.Manager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class BestCustomerForm {
    private JTable bestCustomerTable;
    private JPanel mainPanel;
    private JButton backButton;
    private MainFrame mainFrame;

    public BestCustomerForm(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        /*
         * Table setup
         */
        Object[] headers = {"CID", "Name"};

        bestCustomerTable = new JTable(new DefaultTableModel(headers, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }});
        DefaultTableModel model = (DefaultTableModel) bestCustomerTable.getModel();

        List<Customer> bestCustomers = Manager.getBestCustomer();

        for (Customer customer : bestCustomers) {
            model.addRow(new Object[] {customer.getUserId(), customer.getName()});
        }

        /*
         * Back button setup
         */
        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            mainFrame.backToManagerMainForm();
        });
    }
}

package layout.classGUI.manager;

import layout.MainFrame;
import layout.dialog.RemoveEmployeeDialog;
import model.Employee;
import model.Manager;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ManageEmployeeForm {
    private static final String HTML_START = "<html>";
    private static final String HTML_PARA_START = "<p>";
    private static final String HTML_PARA_END = "</p>";
    private static final String HTML_END = "</html>";

    private JPanel employeesPanel;
    private JPanel mainPanel;
    private JPanel buttonsPanel;
    private JButton addButton;
    private JButton removeButton;
    private JButton backButton;

    private MainFrame mainFrame;

    public ManageEmployeeForm(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        List<Employee> employeeList = Manager.getAllEmployee();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        for (Employee employee : employeeList) {
            JButton employeeButton = new JButton();
            employeeButton.setText(employeeInfoHtmlParser(employee));
            employeeButton.addActionListener(e -> {
                mainFrame.changeToUpdateEmployeeForm(employee);
            });
            employeesPanel.add(employeeButton, gbc);
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private String employeeInfoHtmlParser(Employee employee) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(HTML_START);

        stringBuilder.append(HTML_PARA_START);
        stringBuilder.append("<b>ID: </b>");
        stringBuilder.append(employee.getUserId());
        stringBuilder.append(HTML_PARA_END);

        stringBuilder.append(HTML_PARA_START);
        stringBuilder.append("<b>Name: </b>");
        stringBuilder.append(employee.getName());
        stringBuilder.append(HTML_PARA_END);

        stringBuilder.append(HTML_END);

        return stringBuilder.toString();
    }

    private void createUIComponents() {
        /*
         * Add button handler
         */
        addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            mainFrame.changeToAddEmployeeForm();
        });

        /*
         * Remove button handler
         */
        removeButton = new JButton("Remove");
        removeButton.addActionListener(e -> {
            RemoveEmployeeDialog removeEmployeeDialog = new RemoveEmployeeDialog(mainFrame);
            removeEmployeeDialog.pack();
            removeEmployeeDialog.setLocationRelativeTo(mainPanel);
            removeEmployeeDialog.setVisible(true);
        });

        /*
         * Back button handler
         */
        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            mainFrame.backToManagerMainForm();
        });
    }
}

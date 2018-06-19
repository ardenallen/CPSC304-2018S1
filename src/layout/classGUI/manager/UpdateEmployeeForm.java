package layout.classGUI.manager;

import layout.MainFrame;
import layout.dialog.NoUpdateErrorDialog;
import layout.dialog.OperationFailureDialog;
import layout.dialog.OperationSuccessfulDialog;
import model.AuthClient;
import model.Employee;
import model.Manager;
import model.User;

import javax.swing.*;

public class UpdateEmployeeForm {
    private JPanel mainPanel;
    private JTextField nameField;
    private JTextField sinField;
    private JTextField phoneNumField;
    private JComboBox positionsBox;
    private JButton updateButton;
    private JButton backButton;

    private int eId;
    private String initialName;
    private int sin;
    private String initialPhoneNum;
    private String initialPosition;

    private MainFrame mainFrame;

    public UpdateEmployeeForm(Employee employee, MainFrame mainFrame) {
        eId = employee.getUserId();
        initialName = employee.getName();
        sin = employee.getSIN();
        initialPhoneNum = employee.getPhone();
        this.mainFrame = mainFrame;

        if (AuthClient.tryAuth(new User("manager", employee.getUserId()))) {
            initialPosition = "manager";
        } else {
            initialPosition = "employee";
        }

        if (initialPosition.equals("employee")) {
            positionsBox.setSelectedIndex(0);
        } else {
            positionsBox.setSelectedIndex(1);
        }

        nameField.setText(initialName);

        sinField.setText(String.valueOf(sin));
        sinField.setEditable(false);

        phoneNumField.setText(initialPhoneNum);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        /*
         * Name field setup
         */
        nameField = new JTextField();

        /*
         * SIN field setup
         *
         * SIN is unique to people thus should not be editable
         */
        sinField = new JTextField();
        sinField.setEditable(false);

        /*
         * Name field setup
         */
        phoneNumField = new JTextField();

        /*
         * Position box setup
         */
        String[] positionOptions = {"employee", "manager"};
        positionsBox = new JComboBox<>(positionOptions);

        /*
         * Update button setup
         */
        updateButton = new JButton("Update");
        updateButton.addActionListener(e -> {
            String updatedName = nameField.getText();
            String updatePhoneNum = phoneNumField.getText();
            String updatedPosition = positionsBox.getSelectedItem().toString();

            boolean isNotUpdated = updatedName.equals(initialName)
                    && updatePhoneNum.equals(initialPhoneNum) && updatedPosition.equals(initialPosition);

            if (isNotUpdated) {
                NoUpdateErrorDialog noUpdateErrorDialog = new NoUpdateErrorDialog();
                noUpdateErrorDialog.pack();
                noUpdateErrorDialog.setLocationRelativeTo(mainPanel);
                noUpdateErrorDialog.setVisible(true);
            } else {
                boolean isSuccessful1 = true;

                if (!updatedPosition.equals(initialPosition) && updatedPosition.equals("employee")) {
                    isSuccessful1 = Manager.demoteManagerToEmployee(eId);
                } else if (!updatedPosition.equals(initialPosition) && updatedPosition.equals("manager")) {
                    isSuccessful1 = Manager.promoteEmployeeToManager(eId);
                }

                boolean isSuccessful2 = Manager.updateEmployee(eId, updatedName, updatePhoneNum);

                if (isSuccessful1 && isSuccessful2) {
                    OperationSuccessfulDialog operationSuccessfulDialog = new OperationSuccessfulDialog();
                    operationSuccessfulDialog.pack();
                    operationSuccessfulDialog.setLocationRelativeTo(mainPanel);
                    operationSuccessfulDialog.setVisible(true);

                    mainFrame.backAndRefreshManageEmployeeForm();
                } else {
                    OperationFailureDialog operationFailureDialog = new OperationFailureDialog();
                    operationFailureDialog.pack();
                    operationFailureDialog.setLocationRelativeTo(mainPanel);
                    operationFailureDialog.setVisible(true);
                }
            }
        });

        /*
         * Back button setup
         */
        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            mainFrame.backToPreviousForm();
        });
    }
}

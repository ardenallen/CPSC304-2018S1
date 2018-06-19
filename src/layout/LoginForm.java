package layout;

import layout.dialog.CustomerSignUpDialog;
import layout.dialog.FailedLoginDialog;
import layout.dialog.InvalidIdDialog;
import model.AuthClient;
import model.Customer;
import model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm {
    private JPanel mainPanel;
    private JComboBox<String> userClassOptionBox;
    private JButton loginButton;
    private JTextField idField;
    private JButton signUpButton;

    private MainFrame mainFrame;

    public LoginForm(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        String[] userClasses = { "customer", "employee", "manager"};
        userClassOptionBox = new JComboBox<>(userClasses);
        userClassOptionBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userClass = (String) userClassOptionBox.getSelectedItem();
                if (userClass != "customer"){
                    signUpButton.setVisible(false);
                }
                else{
                    signUpButton.setVisible(true);
                }
            }
        });

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userClass = (String) userClassOptionBox.getSelectedItem();
                Integer userId = null;

                try {
                    userId = Integer.parseInt(idField.getText());
                } catch (NumberFormatException e1) {
                    InvalidIdDialog invalidIdDialog = new InvalidIdDialog();
                    invalidIdDialog.pack();
                    invalidIdDialog.setLocationRelativeTo(mainPanel);
                    invalidIdDialog.setVisible(true);
                    return;
                }

                User userToLogin = new User(userClass, userId);

                if (AuthClient.tryAuth(userToLogin)) {
                    mainFrame.switchClassPanel(userToLogin);
                } else {
                    FailedLoginDialog failedLoginDialog = new FailedLoginDialog();
                    failedLoginDialog.pack();
                    failedLoginDialog.setLocationRelativeTo(mainPanel);
                    failedLoginDialog.setVisible(true);
                }
            }
        });
        signUpButton = new JButton("Sign Up New Customer");
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cID = Customer.getMaxID() + 1;
                CustomerSignUpDialog signUpDialog = new CustomerSignUpDialog(cID, mainFrame);
                signUpDialog.pack();
                signUpDialog.setLocationRelativeTo(mainPanel);
                signUpDialog.setVisible(true);
            }
        });
    }
}

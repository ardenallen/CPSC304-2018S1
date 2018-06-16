package layout;

import layout.dialog.FailedLoginDialog;
import layout.dialog.InvalidIdDialog;
import model.AuthClient;
import model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm {
    private JPanel mainPanel;
    private JComboBox<String> userClassOptionBox;
    private JButton loginButton;
    private JTextField idField;

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
    }
}

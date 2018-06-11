package layout.classGUI.customer;

import layout.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerMainForm {
    private JButton bookButton;
    private JButton loyaltySignupButton;
    private JButton historyButton;
    private JButton logoutButton;
    private JPanel mainPanel;

    private MainFrame mainFrame;

    public CustomerMainForm(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.logout();
            }
        });
    }
}

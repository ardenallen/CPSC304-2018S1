package layout.classGUI.manager;

import layout.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerMainForm {
    private JPanel mainPanel;
    private JButton sellButton;
    private JButton moviesButton;
    private JButton bookingsButton;
    private JButton statsButton;
    private JButton logoutButton;
    private JButton employeeButton;

    private MainFrame mainFrame;

    public ManagerMainForm(MainFrame mainFrame) {
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

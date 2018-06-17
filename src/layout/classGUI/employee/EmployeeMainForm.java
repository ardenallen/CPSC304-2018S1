package layout.classGUI.employee;

import layout.MainFrame;
import model.Employee;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeMainForm {
    private static final String NAME_BASE = "Welcome, ";

    private JButton sellButton;
    private JButton logoutButton;
    private JPanel mainPanel;
    private JButton refundButton;
    private JButton movieStatsButton;
    private JLabel nameLabel;

    private MainFrame mainFrame;

    public EmployeeMainForm(MainFrame mainFrame, Employee employee) {
        this.mainFrame = mainFrame;

        nameLabel.setText(NAME_BASE + employee.getName());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        /*
         * Name label handler
         */
        nameLabel = new JLabel();

        /*
         * Sell ticket button handler
         */
        sellButton = new JButton("Sell tickets");
        sellButton.addActionListener(e -> {
            mainFrame.changeToMovieSelectForm();
        });

        /*
         * Refund button handler
         */
        refundButton = new JButton("Refund");

        /*
         * Movie stat button handler
         */
        movieStatsButton = new JButton("Get movie stats");

        /*
         * Logout button handler
         */
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.logout();
            }
        });
    }
}

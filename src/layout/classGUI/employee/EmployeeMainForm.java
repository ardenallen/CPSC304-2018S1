package layout.classGUI.employee;

import layout.MainFrame;
import layout.dialog.TransactionNumInputDialog;
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
    private JButton checkPointButton;

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
        refundButton.addActionListener(e -> {
            TransactionNumInputDialog transactionNumInputDialog = new TransactionNumInputDialog(mainFrame);
            transactionNumInputDialog.pack();
            transactionNumInputDialog.setLocationRelativeTo(mainPanel);
            transactionNumInputDialog.setVisible(true);
        });

        /*
         * Check point button handler
         */
        checkPointButton = new JButton("Check loyalty point");
        checkPointButton.addActionListener(e -> {
            CheckLoyaltyPointDialog checkLoyaltyPointDialog = new CheckLoyaltyPointDialog(mainFrame);
            checkLoyaltyPointDialog.pack();
            checkLoyaltyPointDialog.setLocationRelativeTo(mainPanel);
            checkLoyaltyPointDialog.setVisible(true);
        });

        /*
         * Movie stat button handler
         */
        movieStatsButton = new JButton("Get movie stats");
        movieStatsButton.addActionListener(e -> {
            mainFrame.changeToEmployeeViewMovieStatForm();
        });

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

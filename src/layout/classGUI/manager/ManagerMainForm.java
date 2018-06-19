package layout.classGUI.manager;

import layout.MainFrame;
import layout.dialog.CheckLoyaltyPointDialog;
import layout.dialog.TransactionNumInputDialog;
import model.Manager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerMainForm {
    private static final String NAME_BASE = "Welcome, ";

    private JPanel mainPanel;
    private JButton sellButton;
    private JButton movieStatsButton;
    private JButton refundButton;
    private JButton checkPointButton;
    private JButton manageEmployeesButton;
    private JButton employeeStatsButton;
    private JButton manageMoviesButton;
    private JButton logoutButton;
    private JLabel nameLabel;
    private JButton bestCustomerButton;

    private Manager manager;
    private MainFrame mainFrame;

    public ManagerMainForm(MainFrame mainFrame, Manager manager) {
        this.mainFrame = mainFrame;
        this.manager = manager;

        nameLabel.setText(NAME_BASE + manager.getName());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        /*
         * Name label setup
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
         * Employee stat button handler
         */
        employeeStatsButton = new JButton("Get employee stats");
        employeeStatsButton.addActionListener(e -> {
            mainFrame.changeToManagerViewEmployeeStatForm();
        });

        /*
         * Manage employees button handler
         */
        manageEmployeesButton = new JButton("Manage employees");
        manageEmployeesButton.addActionListener(e -> {
            mainFrame.changeToManageEmployeeForm();
        });

        /*
         * Manage movies button handler
         */
        manageMoviesButton = new JButton("Manage movies");
        manageMoviesButton.addActionListener(e -> {
            mainFrame.changeToManageMovieForm();
        });

        /*
         * Best customer button handler
         */
        bestCustomerButton = new JButton("Get best customer");
        bestCustomerButton.addActionListener(e -> {
            mainFrame.changeToBestCustomerForm();
        });

        /*
         * Logout button setup
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

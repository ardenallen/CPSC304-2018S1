package layout.classGUI.customer;

import layout.MainFrame;
import layout.dialog.LoyaltySignupDialog;
import model.Customer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerMainForm {
    private static final String STATUS_BASE = "Status: ";

    private JButton bookButton;
    private JButton loyaltySignupButton;
    private JButton historyButton;
    private JButton logoutButton;
    private JPanel mainPanel;
    private JLabel statusLabel;

    private MainFrame mainFrame;

    private Customer customer;

    public CustomerMainForm(MainFrame mainFrame, Customer customer) {
        this.mainFrame = mainFrame;
        this.customer = customer;

        if(customer.isLoyaltyMember()){
            statusLabel.setText(STATUS_BASE + "Loyalty Member");
        } else {
            statusLabel.setText(STATUS_BASE + "Regular Member");
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        statusLabel = new JLabel();

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

        /*
         * Loyalty member sign-up button handler
         */
        loyaltySignupButton = new JButton("Signup for loyalty membership");

        loyaltySignupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoyaltySignupDialog loyaltySignupDialog = new LoyaltySignupDialog(customer, mainFrame);
                loyaltySignupDialog.pack();
                loyaltySignupDialog.setLocationRelativeTo(mainPanel);
                loyaltySignupDialog.setVisible(true);
            }
        });

        if (customer.isLoyaltyMember()) {
            loyaltySignupButton.setEnabled(false);
        }
    }
}

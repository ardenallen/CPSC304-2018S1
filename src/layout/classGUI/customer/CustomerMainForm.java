package layout.classGUI.customer;

import layout.MainFrame;
import layout.dialog.LoyaltyPointDialog;
import layout.dialog.LoyaltySignupDialog;
import model.Customer;

import javax.swing.*;
import java.awt.event.ActionListener;

public class CustomerMainForm {
    private static final String STATUS_BASE = "Status: ";

    private JButton bookButton;
    private JButton loyaltySignupButton;
    private JButton historyButton;
    private JButton logoutButton;
    private JPanel mainPanel;
    private JLabel statusLabel;
    private ActionListener signupListner;
    private ActionListener pointCheckListener;

    private MainFrame mainFrame;

    private Customer customer;

    public CustomerMainForm(MainFrame mainFrame, Customer customer) {
        this.mainFrame = mainFrame;
        this.customer = customer;

        if(customer.isLoyaltyMember()){
            statusLabel.setText(STATUS_BASE + "Loyalty Member");
            switchLoyalMembershipButton();
        } else {
            statusLabel.setText(STATUS_BASE + "Regular Member");
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void switchLoyalMembershipButton() {
        loyaltySignupButton.setText("Check membership points");
        loyaltySignupButton.removeActionListener(signupListner);

        pointCheckListener = e -> {
            LoyaltyPointDialog loyaltyPointDialog = new LoyaltyPointDialog(customer);
            loyaltyPointDialog.pack();
            loyaltyPointDialog.setLocationRelativeTo(mainPanel);
            loyaltyPointDialog.setVisible(true);
        };

        loyaltySignupButton.addActionListener(pointCheckListener);

        loyaltySignupButton.revalidate();
    }

    private void createUIComponents() {
        /*
         * Status label handler
         */
        statusLabel = new JLabel();

        /*
         * Logout button handler
         */
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> mainFrame.logout());

        /*
         * Loyalty member sign-up/point check button handler
         */
        signupListner = e -> {
            LoyaltySignupDialog loyaltySignupDialog = new LoyaltySignupDialog(customer, mainFrame);
            loyaltySignupDialog.pack();
            loyaltySignupDialog.setLocationRelativeTo(mainPanel);
            loyaltySignupDialog.setVisible(true);
        };
        loyaltySignupButton = new JButton("Signup for loyalty membership");
        loyaltySignupButton.addActionListener(signupListner);

        /*
         * Booking button handler
         */
        bookButton = new JButton("Book tickets");
        bookButton.addActionListener(e -> {
            mainFrame.changeToMovieSelectPanel();
        });

        /*
         * History button handler
         */
        historyButton = new JButton("View bookings");
        historyButton.addActionListener(e -> {
            mainFrame.changeToCustomerHistoryPanel(customer);
        });
    }
}

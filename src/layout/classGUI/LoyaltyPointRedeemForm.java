package layout.classGUI;

import layout.MainFrame;
import layout.dialog.FailedLoginDialog;
import layout.dialog.SuccessfulPurchaseDialog;
import layout.dialog.ZeroTicketNumDialog;
import model.Customer;
import model.Movie;
import model.Showtime;

import javax.swing.*;

public class LoyaltyPointRedeemForm {
    private JPanel mainPanel;
    private JSpinner ticketNumSpinner;
    private JButton confirmButton;
    private JButton backButton;
    private JLabel pointBalanceLabel;

    private Customer customer;
    private Movie movie;
    private Showtime showtime;
    private MainFrame mainFrame;

    public LoyaltyPointRedeemForm(Customer customer, Movie movie, Showtime showtime, MainFrame mainFrame) {
        this.customer = customer;
        this.movie = movie;
        this.showtime = showtime;
        this.mainFrame = mainFrame;

        pointBalanceLabel.setText(String.valueOf(customer.getPointBalance()));
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        /*
         * Loyalty point balance label setup;
         */
        pointBalanceLabel = new JLabel();

        /*
         * Ticket num spinner setup
         */
        Integer value = 0;
        Integer min = 0;
        Integer max = (customer.getPointBalance() / 1000);
        Integer step = 1;
        SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, step);
        ticketNumSpinner = new JSpinner(model);

        /*
         * Confirm button setup
         */
        confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> {
            Integer ticketNum = (Integer) ticketNumSpinner.getValue();

            // If ticket number is zero (error)
            if (ticketNum == 0) {
                ZeroTicketNumDialog zeroTicketNumDialog = new ZeroTicketNumDialog();
                zeroTicketNumDialog.pack();
                zeroTicketNumDialog.setLocationRelativeTo(mainPanel);
                zeroTicketNumDialog.setVisible(true);
                return;
            }

            if (mainFrame.getCustomer().buyTickets(movie, showtime, ticketNum, "Redeem")) {
                SuccessfulPurchaseDialog successfulPurchaseDialog = new SuccessfulPurchaseDialog();
                successfulPurchaseDialog.pack();
                successfulPurchaseDialog.setLocationRelativeTo(mainPanel);
                successfulPurchaseDialog.setVisible(true);
            } else {
                FailedLoginDialog failedLoginDialog = new FailedLoginDialog();
                failedLoginDialog.pack();
                failedLoginDialog.setLocationRelativeTo(mainPanel);
                failedLoginDialog.setVisible(true);
                return;
            }

            mainFrame.backToCustomerMainForm();
        });

        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            mainFrame.backToCustomerBookingForm();
        });
    }
}

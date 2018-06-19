package layout.classGUI;

import layout.MainFrame;
import layout.dialog.FailedPurchaseDialog;
import layout.dialog.SuccessfulPurchaseDialog;
import layout.dialog.ZeroTicketNumDialog;
import model.*;

import javax.swing.*;

public class LoyaltyPointRedeemForm {
    private JPanel mainPanel;
    private JSpinner ticketNumSpinner;
    private JButton confirmButton;
    private JButton backButton;
    private JLabel pointBalanceLabel;

    private Customer customer;
    private Employee employee;
    private Movie movie;
    private Showtime showtime;
    private MainFrame mainFrame;

    public LoyaltyPointRedeemForm(Customer customer, Employee employee, Movie movie, Showtime showtime, MainFrame mainFrame) {
        this.customer = customer;
        this.employee = employee;
        this.movie = movie;
        this.showtime = showtime;
        this.mainFrame = mainFrame;

        pointBalanceLabel.setText(String.valueOf(User.getLoyaltyPoints(customer.getUserId())));
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
        Integer max = (User.getLoyaltyPoints(customer.getUserId()) / 1000);
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

            // If this purchase is being done by a customer
            if (employee == null) {
                if (mainFrame.getCustomer().buyTickets(movie, showtime, ticketNum, "Redeem")) {
                    SuccessfulPurchaseDialog successfulPurchaseDialog = new SuccessfulPurchaseDialog();
                    successfulPurchaseDialog.pack();
                    successfulPurchaseDialog.setLocationRelativeTo(mainPanel);
                    successfulPurchaseDialog.setVisible(true);
                } else {
                    FailedPurchaseDialog failedPurchaseDialog = new FailedPurchaseDialog();
                    failedPurchaseDialog.pack();
                    failedPurchaseDialog.setLocationRelativeTo(mainPanel);
                    failedPurchaseDialog.setVisible(true);
                    return;
                }

                mainFrame.backToCustomerMainForm();
            } else {
                // If this purchase is being done by an employee or manager
                if (mainFrame.getEmployee().sellTickets(movie, showtime, ticketNum, "Redeem", customer)) {
                    SuccessfulPurchaseDialog successfulPurchaseDialog = new SuccessfulPurchaseDialog();
                    successfulPurchaseDialog.pack();
                    successfulPurchaseDialog.setLocationRelativeTo(mainPanel);
                    successfulPurchaseDialog.setVisible(true);
                } else {
                    FailedPurchaseDialog failedPurchaseDialog = new FailedPurchaseDialog();
                    failedPurchaseDialog.pack();
                    failedPurchaseDialog.setLocationRelativeTo(mainPanel);
                    failedPurchaseDialog.setVisible(true);
                }

                mainFrame.backToEmployeeMainForm();
            }
        });

        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            mainFrame.backToPreviousForm();
        });
    }
}

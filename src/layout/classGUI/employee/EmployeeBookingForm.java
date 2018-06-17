package layout.classGUI.employee;

import layout.MainFrame;
import layout.dialog.*;
import model.*;

import javax.swing.*;

public class EmployeeBookingForm {
    private JPanel mainPanel;
    private JTextField customerIdField;
    private JButton redeemButton;
    private JSpinner ticketNumSpinner;
    private JComboBox paymentOptionBox;
    private JTextField cardNumberField;
    private JLabel cardNumberLabel;
    private JButton confirmButton;
    private JButton backButton;

    private MainFrame mainFrame;

    private Movie movie;
    private Showtime showtime;

    public EmployeeBookingForm(Movie movie, Showtime showtime, MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.movie = movie;
        this.showtime = showtime;

        cardNumberLabel.setText("Card number: ");
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        /*
         * Card fields are invisible by default
         */
        cardNumberField = new JTextField();
        cardNumberLabel = new JLabel();
        cardNumberLabel.setVisible(false);
        cardNumberField.setVisible(false);

        /*
         * Customer ID field setup
         */
        customerIdField = new JTextField();

        /*
         * Spinner setup
         */
        Integer value = 0;
        Integer min = 0;
        Integer max =
                Auditorium.getAuditoriumCapacity(showtime.getaId()) - Ticket.getTicketsOfShowtime(showtime).size();
        Integer step = 1;
        SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, step);
        ticketNumSpinner = new JSpinner(model);

        /*
         * Payment option combo box setup
         */
        String[] paymentOptions = {"Cash", "Credit", "Debit"};
        paymentOptionBox = new JComboBox<>(paymentOptions);
        paymentOptionBox.addActionListener(e -> {
            JComboBox jcb = (JComboBox) e.getSource();
            String selectionOption = (String) jcb.getSelectedItem();

            assert selectionOption != null;

            if (!selectionOption.equals("Cash")) {
                cardNumberLabel.setVisible(true);
                cardNumberField.setVisible(true);
            } else {
                cardNumberLabel.setVisible(false);
                cardNumberField.setVisible(false);
            }
        });

        /*
         * Sell button setup
         */
        confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> {
            Integer ticketNum = (Integer) ticketNumSpinner.getValue();
            StringBuilder paymentStrBuilder = new StringBuilder();

            String selectedOption = (String) paymentOptionBox.getSelectedItem();
            assert selectedOption != null;

            switch (selectedOption) {
                case ("Cash"):
                    paymentStrBuilder.append("Cash");
                    break;

                case ("Debit"):
                    paymentStrBuilder.append("D");
                    paymentStrBuilder.append(cardNumberField.getText());
                    break;
                case ("Credit"):
                    paymentStrBuilder.append("C");
                    paymentStrBuilder.append(cardNumberField.getText());
                    break;
            }

            // If ticket number is zero (error)
            if (ticketNum == 0) {
                ZeroTicketNumDialog zeroTicketNumDialog = new ZeroTicketNumDialog();
                zeroTicketNumDialog.pack();
                zeroTicketNumDialog.setLocationRelativeTo(mainPanel);
                zeroTicketNumDialog.setVisible(true);
                return;
            }

            int customerId;

            // If some other invalid value was entered for Customer ID
            try {
                customerId = Integer.parseInt(customerIdField.getText());
            } catch (NumberFormatException e1) {
                InvalidCustomerIdDialog invalidCustomerIdDialog = new InvalidCustomerIdDialog();
                invalidCustomerIdDialog.pack();
                invalidCustomerIdDialog.setLocationRelativeTo(mainPanel);
                invalidCustomerIdDialog.setVisible(true);
                return;
            }

            // See if customer exists
            if (!AuthClient.tryAuth(new User("customer", customerId))) {
                CustomerDoesNotExistDialog customerDoesNotExistDialog = new CustomerDoesNotExistDialog();
                customerDoesNotExistDialog.pack();
                customerDoesNotExistDialog.setLocationRelativeTo(mainPanel);
                customerDoesNotExistDialog.setVisible(true);
                return;
            }

            Customer targetCustomer = new Customer(customerId);

            // Try selling
            String paymentInfo = paymentStrBuilder.toString();
            if (mainFrame.getEmployee().sellTickets(movie, showtime, ticketNum, paymentInfo, targetCustomer)) {
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

            mainFrame.backToEmployeeMainForm();
        });

        /*
         * Redeem button setup
         */
        redeemButton = new JButton("Redeem");
        redeemButton.addActionListener(e -> {
            int customerId;

            // If some other invalid value was entered for Customer ID
            try {
                customerId = Integer.parseInt(customerIdField.getText());
            } catch (NumberFormatException e1) {
                InvalidCustomerIdDialog invalidCustomerIdDialog = new InvalidCustomerIdDialog();
                invalidCustomerIdDialog.pack();
                invalidCustomerIdDialog.setLocationRelativeTo(mainPanel);
                invalidCustomerIdDialog.setVisible(true);
                return;
            }

            // See if customer exists
            if (!AuthClient.tryAuth(new User("customer", customerId))) {
                CustomerDoesNotExistDialog customerDoesNotExistDialog = new CustomerDoesNotExistDialog();
                customerDoesNotExistDialog.pack();
                customerDoesNotExistDialog.setLocationRelativeTo(mainPanel);
                customerDoesNotExistDialog.setVisible(true);
                return;
            }

            Customer targetCustomer = new Customer(customerId);

            if (!targetCustomer.isLoyaltyMember()) {
                NotLoyaltyMemberDialog notLoyaltyMemberDialog = new NotLoyaltyMemberDialog();
                notLoyaltyMemberDialog.pack();
                notLoyaltyMemberDialog.setLocationRelativeTo(mainPanel);
                notLoyaltyMemberDialog.setVisible(true);
                return;
            }

            if (!targetCustomer.canRedeem(1)) {
                NotEnoughPointsDialog notEnoughPointsDialog = new NotEnoughPointsDialog();
                notEnoughPointsDialog.pack();
                notEnoughPointsDialog.setLocationRelativeTo(mainPanel);
                notEnoughPointsDialog.setVisible(true);
                return;
            }

            mainFrame.changeToLoyaltyPointRedeemForm(targetCustomer, mainFrame.getEmployee(), movie, showtime);
        });

        /*
         * Back button setup
         */
        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            mainFrame.backToPreviousForm();
        });
    }
}

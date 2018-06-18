package layout.classGUI.customer;

import layout.MainFrame;
import layout.dialog.FailedPurchaseDialog;
import layout.dialog.SuccessfulPurchaseDialog;
import layout.dialog.ZeroTicketNumDialog;
import model.*;

import javax.swing.*;

public class CustomerBookingForm {
    private JSpinner ticketNumSpinner;
    private JComboBox paymentOptionBox;
    private JButton confirmButton;
    private JButton backButton;
    private JTextField cardNumberField;
    private JLabel cardNumberLabel;
    private JPanel mainPanel;
    private JLabel pointTitleLabel;
    private JLabel loyaltyPointLabel;
    private JButton redeemButton;

    private MainFrame mainFrame;

    private Movie movie;
    private Showtime showtime;
    private boolean isLoyaltyMember;
    private int loyaltyPointBalance;

    public CustomerBookingForm(Movie movie, Showtime showtime, MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.movie = movie;
        this.showtime = showtime;

        cardNumberLabel.setText("Card number: ");
        pointTitleLabel.setText("Loyalty point balance: ");
        loyaltyPointLabel.setText(String.valueOf(loyaltyPointBalance));
    }

    private void createUIComponents() {
        /*
         * Loyalty point labels setup
         */
        pointTitleLabel = new JLabel();
        loyaltyPointLabel = new JLabel();
        isLoyaltyMember = mainFrame.getCustomer().isLoyaltyMember();

        if (isLoyaltyMember) {
            pointTitleLabel.setVisible(true);
            loyaltyPointLabel.setVisible(true);
            loyaltyPointBalance = User.getLoyaltyPoints(mainFrame.getCustomer().getUserId());
        } else {
            pointTitleLabel.setVisible(false);
            loyaltyPointLabel.setVisible(false);
            loyaltyPointBalance = -1;
        }

        /*
         * Redeem button setup
         */
        redeemButton = new JButton("Redeem");
        redeemButton.addActionListener(e -> {
            mainFrame.changeToLoyaltyPointRedeemForm(mainFrame.getCustomer(), null, movie, showtime);
        });

        if (!isLoyaltyMember) {
            redeemButton.setVisible(false);
        }

        if (loyaltyPointBalance < 1000) {
            redeemButton.setEnabled(false);
        }

        /*
         * Card fields are invisible by default
         */
        cardNumberField = new JTextField();
        cardNumberLabel = new JLabel();
        cardNumberLabel.setVisible(false);
        cardNumberField.setVisible(false);

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

            // Try purchasing
            String paymentInfo = paymentStrBuilder.toString();
            if (mainFrame.getCustomer().buyTickets(movie, showtime, ticketNum, paymentInfo)) {
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
        });

        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            mainFrame.backToPreviousForm();
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}

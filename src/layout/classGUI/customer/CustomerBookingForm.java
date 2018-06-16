package layout.classGUI.customer;

import layout.MainFrame;
import model.Movie;
import model.Showtime;

import javax.swing.*;

public class CustomerBookingForm {
    private JSpinner ticketNumSpinner;
    private JComboBox paymentOptionBox;
    private JButton confirmButton;
    private JButton backButton;
    private JTextField cardNumberField;
    private JLabel cardNumberLabel;

    private MainFrame mainFrame;

    private Movie movie;
    private Showtime showtime;

    public CustomerBookingForm(Movie movie, Showtime showtime, MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.movie = movie;
        this.showtime = showtime;

        cardNumberLabel.setText("Card number: ");
    }

    private void createUIComponents() {
        cardNumberField = new JTextField();
        cardNumberLabel = new JLabel();

        // TODO: Set max value as available booking #
        Integer value = 0;
        Integer min = 0;
        Integer max = 100;
        Integer step = 1;
        SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, step);
        ticketNumSpinner = new JSpinner(model);

        String[] paymentOptions = {"Cash", "Credit", "Debit"};
        paymentOptionBox = new JComboBox<>(paymentOptions);
        paymentOptionBox.addActionListener(e -> {
            JComboBox jcb = (JComboBox) e.getSource();
            String selectionOption = (String) jcb.getSelectedItem();

            assert selectionOption != null;

            if (selectionOption.equals("Cash")) {
                cardNumberLabel.setVisible(false);
                cardNumberField.setVisible(false);
            }
        });

        confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> {
            /*
             * TODO: Add booking into DB
             * TODO: Give customer points, if he/she is a loyalty member
             */
        });

        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            mainFrame.backToShowtimeSelectionForm();
        });
    }
}

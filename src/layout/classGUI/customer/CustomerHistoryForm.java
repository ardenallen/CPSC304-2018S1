package layout.classGUI.customer;

import layout.MainFrame;
import model.Booking;
import model.Customer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerHistoryForm {
    private JPanel mainPanel;

    private List<Booking> bookingList;

    public CustomerHistoryForm(Customer customer, MainFrame mainFrame) {
        /*
         * TODO: Get booking list of customer from DB
         *
         * Below is a placeholder
         */
        bookingList = new ArrayList<>();

        for (Booking booking : bookingList) {
            JButton bookingButton = new JButton();
            bookingButton.setText("Transaction #: " + booking.getTransactionNum());
            bookingButton.addActionListener(e -> {
                /*
                 * TODO: Create a page to view associated tickets
                 */
            });
        }

        /*
         * Back button handler
         */
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            mainFrame.backToCustomerMainForm();
        });
        mainPanel.add(backButton);
    }
}

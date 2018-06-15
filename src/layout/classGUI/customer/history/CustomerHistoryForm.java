package layout.classGUI.customer.history;

import layout.MainFrame;
import model.Booking;
import model.Customer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerHistoryForm {
    private JPanel mainPanel;

    private List<Booking> bookingList;

    public CustomerHistoryForm(MainFrame mainFrame, Customer customer) {
        /*
         * History button generator
         *
         * TODO: Get booking list of customer from DB
         *
         * Below is a placeholder
         */
        bookingList = new ArrayList<>();

        for (Booking booking : bookingList) {
            JButton bookingButton = new JButton();
            bookingButton.setText("Transaction #: " + booking.getTransactionNum());
            bookingButton.addActionListener(e -> {
                mainFrame.changeToCustomerTicketsPanel(booking);
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

    public JPanel getMainPanel() {
        return mainPanel;
    }
}

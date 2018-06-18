package layout.classGUI.customer.history;

import layout.MainFrame;
import model.Booking;
import model.Customer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CustomerHistoryForm {
    private JPanel mainPanel;
    private JPanel bookingsPanel;

    public CustomerHistoryForm(MainFrame mainFrame, Customer customer) {
        List<Booking> bookingList = customer.getAllBookings();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        for (Booking booking : bookingList) {
            JButton bookingButton = new JButton();
            bookingButton.setText("Transaction #: " + booking.getTransactionNum());
            bookingButton.addActionListener(e -> {
                mainFrame.changeToCustomerTicketsForm(booking);
            });
            bookingsPanel.add(bookingButton, gbc);
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

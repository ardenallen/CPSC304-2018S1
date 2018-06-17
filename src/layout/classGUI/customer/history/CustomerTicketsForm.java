package layout.classGUI.customer.history;

import layout.MainFrame;
import model.Booking;
import model.Ticket;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CustomerTicketsForm {
    private JPanel mainPanel;
    private JTable ticketsTable;
    private JButton backButton;

    private MainFrame mainFrame;
    private Booking booking;

    public CustomerTicketsForm(MainFrame mainFrame, Booking booking) {
        this.mainFrame = mainFrame;
        this.booking = booking;
    }

    private void createUIComponents() {
        Object[] headers = {"Ticket #", "Title", "Start time", "Auditorium #"};

        ticketsTable = new JTable(new DefaultTableModel(headers, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        DefaultTableModel model = (DefaultTableModel) ticketsTable.getModel();

        for (Ticket ticket : booking.getTickets()) {
            model.addRow(
                    new Object[] {ticket.getTicketNum(), ticket.getTitle(), ticket.getStartTime(), ticket.getaId()});
        }

        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            mainFrame.backToPreviousForm();
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}

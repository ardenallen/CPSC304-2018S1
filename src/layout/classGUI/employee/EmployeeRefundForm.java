package layout.classGUI.employee;

import layout.MainFrame;
import layout.dialog.OperationFailureDialog;
import layout.dialog.OperationSuccessfulDialog;
import layout.dialog.RefundConfirmDialog;
import layout.dialog.WrongCardNumDialog;
import model.Booking;
import model.Employee;
import model.Ticket;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRefundForm {
    private static final String HTML_START = "<html>";
    private static final String HTML_PARA_START = "<p>";
    private static final String HTML_PARA_END = "</p>";
    private static final String HTML_END = "</html>";

    private JPanel mainPanel;
    private JButton refundButton;
    private JLabel paymentMethodLabel;
    private JScrollPane ticketsPane;
    private JPanel ticketsPanel;
    private JButton backButton;
    private JTextField cardNumField;
    private JLabel cardNumLabel;
    private List<JToggleButton> buttonList;

    private Booking booking;
    private MainFrame mainFrame;

    public EmployeeRefundForm(Booking booking, MainFrame mainFrame) {
        this.booking = booking;
        this.mainFrame = mainFrame;

        /*
         * Payment label setup
         */
        paymentMethodLabel.setText("Payment method: " + booking.getPaymentMethod());

        /*
         * Ticket button setup
         */
        buttonList = new ArrayList<>();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        for (Ticket ticket : booking.getTickets()) {
            JToggleButton ticketButton = new JToggleButton();
            ticketButton.setName(String.valueOf(ticket.getTicketNum()));
            ticketButton.setText(ticketInfoHtmlParser(ticket));
            ticketsPanel.add(ticketButton, gbc);
            buttonList.add(ticketButton);
        }

        cardNumLabel.setText("Card number: ");

        if (booking.getPaymentMethod().equals("Cash")) {
            cardNumLabel.setVisible(true);
            cardNumField.setVisible(true);
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        /*
         * Payment method label setup
         */
        paymentMethodLabel = new JLabel();

        /*
         * Refund button setup
         */
        refundButton = new JButton("Refund");
        refundButton.addActionListener(e -> {
            List<Integer> ticketNumList = new ArrayList<>();

            for (JToggleButton ticketButton : buttonList) {
                if (ticketButton.isSelected()) {
                    ticketNumList.add(Integer.valueOf(ticketButton.getName()));
                }
            }

            RefundConfirmDialog refundConfirmDialog;

            if (booking.getPaymentMethod().trim().equals("Cash")) {
                refundConfirmDialog =
                        new RefundConfirmDialog("Cash",  null, ticketNumList, mainFrame);
            } else {
                if (!cardNumField.getText().equals(booking.getCardInfo())) {
                    WrongCardNumDialog wrongCardNumDialog = new WrongCardNumDialog();
                    wrongCardNumDialog.pack();
                    wrongCardNumDialog.setLocationRelativeTo(mainPanel);
                    wrongCardNumDialog.setVisible(true);
                    return;
                }
                refundConfirmDialog =
                        new RefundConfirmDialog("Cash", booking.getCardInfo(), ticketNumList, mainFrame);
            }

            refundConfirmDialog.pack();
            refundConfirmDialog.setLocationRelativeTo(mainPanel);
            refundConfirmDialog.setVisible(true);
        });

        /*
         * Card fields are invisible by default
         */
        cardNumField = new JTextField();
        cardNumLabel = new JLabel();
        cardNumField.setVisible(false);
        cardNumLabel.setVisible(false);

        /*
         * Back button setup
         */
        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            mainFrame.backToEmployeeMainForm();
        });
    }

    private String ticketInfoHtmlParser(Ticket ticket) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(HTML_START);

        stringBuilder.append(HTML_PARA_START);
        stringBuilder.append("<b>Ticket #: </b>");
        stringBuilder.append(ticket.getTicketNum());
        stringBuilder.append(HTML_PARA_END);

        stringBuilder.append(HTML_PARA_START);
        stringBuilder.append("<b>Title: </b>");
        stringBuilder.append(ticket.getTitle());
        stringBuilder.append(HTML_PARA_END);

        stringBuilder.append(HTML_PARA_START);
        stringBuilder.append("<b>Start time: </b>");
        stringBuilder.append(ticket.getStartTime());
        stringBuilder.append(HTML_PARA_END);

        stringBuilder.append(HTML_PARA_START);
        stringBuilder.append("<b>Auditorium #: </b>");
        stringBuilder.append(ticket.getaId());
        stringBuilder.append(HTML_PARA_END);

        stringBuilder.append(HTML_END);

        return stringBuilder.toString();
    }
}

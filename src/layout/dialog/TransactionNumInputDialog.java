package layout.dialog;

import layout.MainFrame;
import model.Booking;

import javax.swing.*;
import java.awt.event.*;

public class TransactionNumInputDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField transactionNumField;

    private MainFrame mainFrame;

    public TransactionNumInputDialog(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        String transactionNum = transactionNumField.getText();

        Booking booking = Booking.getBooking(transactionNum);

        if (booking == null) {
            BookingDoesNotExistDialog bookingDoesNotExistDialog = new BookingDoesNotExistDialog();
            bookingDoesNotExistDialog.pack();
            bookingDoesNotExistDialog.setLocationRelativeTo(mainFrame.getMainFrame());
            bookingDoesNotExistDialog.setVisible(true);

            return;
        }

        mainFrame.changeToEmployeeViewBookingForm(booking);
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    private void createUIComponents() {
        /*
         * Customer ID field setup
         */
        transactionNumField = new JTextField();
    }
}

package layout.dialog;

import layout.MainFrame;
import model.Employee;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class RefundConfirmDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel priceLabel;
    private MainFrame mainFrame;

    private String paymentMethod;
    private String cardInfo;
    private List<Integer> ticketNumList;

    public RefundConfirmDialog(String paymentMethod, String cardInfo, List<Integer> ticketNumList, MainFrame mainFrame) {
        this.paymentMethod = paymentMethod;
        this.cardInfo = cardInfo;
        this.ticketNumList = ticketNumList;
        this.mainFrame = mainFrame;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        double amountForRefund = ticketNumList.size() * 13.25;
        priceLabel.setText("$" + String.valueOf(amountForRefund));

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
        boolean isSuccessful;

        if (paymentMethod.equals("Cash")) {
            isSuccessful = Employee.refund("", ticketNumList);
        } else {
            isSuccessful = Employee.refund(cardInfo, ticketNumList);
        }

        if (isSuccessful) {
            OperationSuccessfulDialog operationSuccessfulDialog = new OperationSuccessfulDialog();
            operationSuccessfulDialog.pack();
            operationSuccessfulDialog.setLocationRelativeTo(mainFrame.getMainFrame());
            operationSuccessfulDialog.setVisible(true);

            mainFrame.backToEmployeeMainForm();
        } else {
            OperationFailureDialog operationFailureDialog = new OperationFailureDialog();
            operationFailureDialog.pack();
            operationFailureDialog.setLocationRelativeTo(mainFrame.getMainFrame());
            operationFailureDialog.setVisible(true);
        }

        dispose();
    }

    private void onCancel() {
        dispose();
    }

    private void createUIComponents() {
        priceLabel = new JLabel();
    }
}

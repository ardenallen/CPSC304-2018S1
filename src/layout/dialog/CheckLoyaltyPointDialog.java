package layout.dialog;

import layout.MainFrame;
import model.AuthClient;
import model.Customer;
import model.User;

import javax.swing.*;
import java.awt.event.*;

public class CheckLoyaltyPointDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField customerIdField;

    private MainFrame mainFrame;

    public CheckLoyaltyPointDialog(MainFrame mainFrame) {
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
        int customerId = Integer.parseInt(customerIdField.getText());

        if (!AuthClient.tryAuth(new User("customer", customerId))) {
            CustomerDoesNotExistDialog customerDoesNotExistDialog = new CustomerDoesNotExistDialog();
            customerDoesNotExistDialog.pack();
            customerDoesNotExistDialog.setLocationRelativeTo(mainFrame.getMainFrame());
            customerDoesNotExistDialog.setVisible(true);
            return;
        }

        Customer targetCustomer = new Customer(customerId);

        if (!targetCustomer.isLoyaltyMember()) {
            NotLoyaltyMemberDialog notLoyaltyMemberDialog = new NotLoyaltyMemberDialog();
            notLoyaltyMemberDialog.pack();
            notLoyaltyMemberDialog.setLocationRelativeTo(mainFrame.getMainFrame());
            notLoyaltyMemberDialog.setVisible(true);
            return;
        }

        LoyaltyPointDialog loyaltyPointDialog = new LoyaltyPointDialog(targetCustomer);
        loyaltyPointDialog.pack();
        loyaltyPointDialog.setLocationRelativeTo(mainFrame.getMainFrame());
        loyaltyPointDialog.setVisible(true);

        dispose();
    }

    private void onCancel() {
        dispose();
    }
}

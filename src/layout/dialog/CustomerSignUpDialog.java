package layout.dialog;

import model.Customer;

import javax.swing.*;
import java.awt.event.*;

public class CustomerSignUpDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField Name;
    private int cID;

    public CustomerSignUpDialog(int cID) {
        this.cID = cID;
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

        }

    private void onOK() {
        String name = Name.getText();
        Customer.addNewCustomer(cID, name);
        CustomerIdDialog idDialog = new CustomerIdDialog(cID);
        idDialog.pack();
//        idDialog.setLocationRelativeTo(this);
        idDialog.setVisible(true);
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}

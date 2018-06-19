package layout.dialog;

import layout.MainFrame;
import model.Customer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerSignUpDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField Name;
    private int cID;
    private MainFrame mainFrame;

    public CustomerSignUpDialog(int cID, MainFrame mainFrame) {
        this.mainFrame = mainFrame;

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
        CustomerIdDialog idDialog = new CustomerIdDialog(cID, mainFrame);
        idDialog.pack();
        idDialog.setLocationRelativeTo(mainFrame.getMainFrame());
        idDialog.setVisible(true);

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}

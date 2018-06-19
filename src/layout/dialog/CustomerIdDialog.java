package layout.dialog;

import model.Customer;

import javax.swing.*;
import java.awt.event.*;

public class CustomerIdDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel cIDLabel;

    public CustomerIdDialog(int cID) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        cIDLabel.setText("Your cID: " + cID);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void createUIComponents() {
        cIDLabel = new JLabel();
    }
}

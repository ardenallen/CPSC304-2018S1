package layout.dialog;

import model.Customer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoyaltyPointDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel pointLabel;

    public LoyaltyPointDialog(Customer customer) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        pointLabel.setText("Current point: " + customer.getPointBalance());

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
        pointLabel = new JLabel();
    }
}

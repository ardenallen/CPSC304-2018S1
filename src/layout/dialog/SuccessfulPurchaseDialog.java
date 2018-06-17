package layout.dialog;

import javax.swing.*;
import java.awt.event.*;

public class SuccessfulPurchaseDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;

    public SuccessfulPurchaseDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
    }

    private void onOK() {
        dispose();
    }
}

package layout.dialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NotLoyaltyMemberDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;

    public NotLoyaltyMemberDialog() {
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
        // add your code here
        dispose();
    }
}

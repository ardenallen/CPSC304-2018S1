package layout.dialog;

import javax.swing.*;
import java.awt.event.*;

public class NotEnoughPointsDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;

    public NotEnoughPointsDialog() {
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

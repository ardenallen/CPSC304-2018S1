package layout.dialog;

import layout.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerIdDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel idLabel;

    private MainFrame mainFrame;

    public CustomerIdDialog(int cID, MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        idLabel.setText("Your cID: " + cID);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
    }

    private void onOK() {
        dispose();
    }

    private void createUIComponents() {
        idLabel = new JLabel();
    }
}

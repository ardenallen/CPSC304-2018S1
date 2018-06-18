package layout.dialog;

import layout.MainFrame;
import model.Manager;

import javax.swing.*;
import java.awt.event.*;

public class RemoveMovieDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField titleField;

    private MainFrame mainFrame;

    public RemoveMovieDialog(MainFrame mainFrame) {
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
        if (Manager.removeMovie(titleField.getText())) {
            OperationSuccessfulDialog operationSuccessfulDialog = new OperationSuccessfulDialog();
            operationSuccessfulDialog.pack();
            operationSuccessfulDialog.setLocationRelativeTo(mainFrame.getMainFrame());
            operationSuccessfulDialog.setVisible(true);

            mainFrame.backAndRefreshManageMovieForm();
        } else {
            OperationFailureDialog operationFailureDialog = new OperationFailureDialog();
            operationFailureDialog.pack();
            operationFailureDialog.setLocationRelativeTo(mainFrame.getMainFrame());
            operationFailureDialog.setVisible(true);
        }

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void createUIComponents() {
        /*
         * Title field setup
         */
        titleField = new JTextField();
    }
}

package layout.dialog;

import layout.MainFrame;
import model.Manager;

import javax.swing.*;
import java.awt.event.*;

public class RemoveEmployeeDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField idField;

    private MainFrame mainFrame;

    public RemoveEmployeeDialog(MainFrame mainFrame) {
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
        int idToRemove = Integer.parseInt(idField.getText());

        if (idToRemove == mainFrame.getManager().getUserId()) {
            CantDeleteSelfErrorDialog cantDeleteSelfErrorDialog = new CantDeleteSelfErrorDialog();
            cantDeleteSelfErrorDialog.pack();
            cantDeleteSelfErrorDialog.setLocationRelativeTo(mainFrame.getMainFrame());
            cantDeleteSelfErrorDialog.setVisible(true);

            return;
        }

        if (Manager.removeEmployee(idToRemove)) {
            OperationSuccessfulDialog operationSuccessfulDialog = new OperationSuccessfulDialog();
            operationSuccessfulDialog.pack();
            operationSuccessfulDialog.setLocationRelativeTo(mainFrame.getMainFrame());
            operationSuccessfulDialog.setVisible(true);

            mainFrame.backAndRefreshManageEmployeeForm();
        } else {
            OperationFailureDialog operationFailureDialog = new OperationFailureDialog();
            operationFailureDialog.pack();
            operationFailureDialog.setLocationRelativeTo(mainFrame.getMainFrame());
            operationFailureDialog.setVisible(true);

            return;
        }

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void createUIComponents() {
        /*
         * ID field setup
         */
        idField = new JTextField();
    }
}

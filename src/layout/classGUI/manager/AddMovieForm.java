package layout.classGUI.manager;

import layout.MainFrame;
import layout.dialog.FieldCantBeBlankDialog;
import layout.dialog.OperationFailureDialog;
import layout.dialog.OperationSuccessfulDialog;
import model.Manager;

import javax.swing.*;

public class AddMovieForm {
    private JPanel mainPanel;
    private JTextField titleField;
    private JTextField genreField;
    private JTextField durationField;
    private JTextField censorField;
    private JButton addButton;
    private JButton backButton;

    private MainFrame mainFrame;

    public AddMovieForm(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        /*
         * Title field setup
         */
        titleField = new JTextField();

        /*
         * Genre field setup
         */
        genreField = new JTextField();

        /*
         * Duration field setup
         */
        durationField = new JTextField();

        /*
         * Age restriction field setup
         */
        censorField = new JTextField();

        /*
         * Add field setup
         */
        addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            // Blank check
            if(titleField.getText().isEmpty() || genreField.getText().isEmpty() ||
                    durationField.getText().isEmpty() || censorField.getText().isEmpty()) {
                FieldCantBeBlankDialog fieldCantBeBlankDialog = new FieldCantBeBlankDialog();
                fieldCantBeBlankDialog.pack();
                fieldCantBeBlankDialog.setLocationRelativeTo(mainPanel);
                fieldCantBeBlankDialog.setVisible(true);
                return;
            }

            String title = titleField.getText();
            String genre = genreField.getText();
            String censor = censorField.getText();
            int duration;

            // Type check
            try {
                duration = Integer.parseInt(durationField.getText());
            } catch (NumberFormatException e1) {
                OperationFailureDialog operationFailureDialog = new OperationFailureDialog();
                operationFailureDialog.pack();
                operationFailureDialog.setLocationRelativeTo(mainPanel);
                operationFailureDialog.setVisible(true);
                return;
            }

            if (Manager.addMovie(title, duration, genre, censor)) {
                OperationSuccessfulDialog operationSuccessfulDialog = new OperationSuccessfulDialog();
                operationSuccessfulDialog.pack();
                operationSuccessfulDialog.setLocationRelativeTo(mainPanel);
                operationSuccessfulDialog.setVisible(true);

                mainFrame.backAndRefreshManageMovieForm();
            } else {
                OperationFailureDialog operationFailureDialog = new OperationFailureDialog();
                operationFailureDialog.pack();
                operationFailureDialog.setLocationRelativeTo(mainPanel);
                operationFailureDialog.setVisible(true);
            }
        });

        /*
         * Back button setup
         */
        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            mainFrame.backToPreviousForm();
        });
    }
}

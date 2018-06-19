package layout.classGUI.manager;

import layout.MainFrame;
import layout.dialog.FieldCantBeBlankDialog;
import layout.dialog.OperationFailureDialog;
import layout.dialog.OperationSuccessfulDialog;
import model.Manager;
import model.Movie;

import javax.swing.*;
import java.sql.Timestamp;

public class AddShowtimeForm {
    private JSpinner dateSpinner;
    private JButton addButton;
    private JButton backButton;
    private JComboBox ccBox;
    private JTextField aIdField;
    private JPanel mainPanel;

    private Movie movie;
    private MainFrame mainFrame;

    public AddShowtimeForm(Movie movie, MainFrame mainFrame) {
        this.movie = movie;
        this.mainFrame = mainFrame;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        /*
         * Date spinner setup
         */
        SpinnerDateModel spinnerDateModel = new SpinnerDateModel();
        dateSpinner = new JSpinner(spinnerDateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd hh:mm:ss.SSSSSS");
        dateSpinner.setEditor(dateEditor);

        /*
         * CC box setup
         */
        String[] ccOptions = {"Supported", "Not supported"};
        ccBox = new JComboBox<>(ccOptions);

        /*
         * aID field setup
         */
        aIdField = new JTextField();

        /*
         * Add button setup
         */
        addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            Timestamp timestamp = null;
            String title = null;
            boolean cc = false;
            int aId = -1;

            // Blank check;
            if(aIdField.getText().isEmpty()) {
                FieldCantBeBlankDialog fieldCantBeBlankDialog = new FieldCantBeBlankDialog();
                fieldCantBeBlankDialog.pack();
                fieldCantBeBlankDialog.setLocationRelativeTo(mainPanel);
                fieldCantBeBlankDialog.setVisible(true);
                return;
            }

            // Type check;
            try {
                timestamp = new Timestamp(spinnerDateModel.getDate().toInstant().toEpochMilli());
                title = movie.getTitle();
                cc = ccBox.getSelectedItem().toString().equals("Supported");
                aId = Integer.parseInt(aIdField.getText());
            } catch (Exception e1) {
                OperationFailureDialog operationFailureDialog = new OperationFailureDialog();
                operationFailureDialog.pack();
                operationFailureDialog.setLocationRelativeTo(mainPanel);
                operationFailureDialog.setVisible(true);

                return;
            }

            if (Manager.addShowtime(timestamp, title, cc, aId)) {
                OperationSuccessfulDialog operationSuccessfulDialog = new OperationSuccessfulDialog();
                operationSuccessfulDialog.pack();
                operationSuccessfulDialog.setLocationRelativeTo(mainPanel);
                operationSuccessfulDialog.setVisible(true);

                mainFrame.backAndRefreshManageShowtimeForm(movie);
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

package layout.classGUI.manager;

import layout.MainFrame;
import layout.dialog.OperationFailureDialog;
import layout.dialog.OperationSuccessfulDialog;
import model.Manager;
import model.Movie;
import model.Showtime;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RemoveShowtimeForm {
    private static final String HTML_START = "<html>";
    private static final String HTML_PARA_START = "<p>";
    private static final String HTML_PARA_END = "</p>";
    private static final String HTML_END = "</html>";
    private static final String SEPARATOR = ";";

    private JPanel mainPanel;
    private JButton removeButton;
    private JButton backButton;
    private JPanel showtimesPanel;
    private JPanel buttonsPanel;

    private MainFrame mainFrame;
    private Movie movie;
    private List<JToggleButton> buttonList;

    public RemoveShowtimeForm(Movie movie, List<Showtime> showtimeList, MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.movie = movie;

        /*
         * Ticket button setup
         */
        buttonList = new ArrayList<>();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        for (Showtime showtime : showtimeList) {
            JToggleButton showtimeButton = new JToggleButton();
            showtimeButton.setName(movie.getTitle() + SEPARATOR + showtime.getStartTime().toString());
            showtimeButton.setText(showtimeInfoHtmlParser(showtime));
            showtimesPanel.add(showtimeButton, gbc);
            buttonList.add(showtimeButton);
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private String showtimeInfoHtmlParser(Showtime showtime) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(HTML_START);

        stringBuilder.append(HTML_PARA_START);
        stringBuilder.append("<b>Title: </b>");
        stringBuilder.append(showtime.getMovieTitle());
        stringBuilder.append(HTML_PARA_END);

        stringBuilder.append(HTML_PARA_START);
        stringBuilder.append("<b>Start time: </b>");
        stringBuilder.append(showtime.getStartTime());
        stringBuilder.append(HTML_PARA_END);

        stringBuilder.append(HTML_END);

        return stringBuilder.toString();
    }

    private void createUIComponents() {
        /*
         * Remove button setup
         */
        removeButton = new JButton("Remove");
        removeButton.addActionListener(e -> {
            List<String> showtimesToDelete = new ArrayList<>();

            for (JToggleButton showtimeButton : buttonList) {
                if (showtimeButton.isSelected()) {
                    showtimesToDelete.add(showtimeButton.getName());
                }
            }

            boolean isAllOperationSuccessful = true;

            for (String showtime : showtimesToDelete) {
                String[] components = showtime.split(SEPARATOR);

                String titleString = components[0];
                String timestampString = components[1];

                Timestamp timestamp = Timestamp.valueOf(timestampString);

                boolean isCurrentOperationSuccessful = Manager.removeShowtime(timestamp, titleString);

                isAllOperationSuccessful = isAllOperationSuccessful && isCurrentOperationSuccessful;
            }

            if (isAllOperationSuccessful) {
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
            mainFrame.backAndRefreshManageShowtimeForm(movie);
        });
    }
}

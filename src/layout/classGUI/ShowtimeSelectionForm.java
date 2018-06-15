package layout.classGUI;

import layout.MainFrame;
import model.Movie;
import model.Showtime;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ShowtimeSelectionForm {
    private static final String HTML_START = "<html>";
    private static final String HTML_PARA_START = "<p>";
    private static final String HTML_PARA_END = "</p>";
    private static final String HTML_END = "</html>";

    private JPanel mainPanel;
    private List<Showtime> showtimeList;

    public ShowtimeSelectionForm(Movie movie, MainFrame mainFrame) {
        /*
         * TODO: Get list of showtime for a movie from DB
         *
         * Below is just placeholder for now
         */
        showtimeList = new ArrayList<>();

        for (Showtime showtime : showtimeList) {
            JButton showTimeButton = new JButton();
            showTimeButton.setText(showtimeInfoHtmlParser(showtime));

            /*
             * TODO: Add and connect booking page as an action listener
             */

            mainPanel.add(showTimeButton);
        }

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            mainFrame.backToMovieSelectionForm();
        });
        mainPanel.add(backButton);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private String showtimeInfoHtmlParser(Showtime showtime) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(HTML_START);

        stringBuilder.append(HTML_PARA_START);
        stringBuilder.append("Start time: ");
        stringBuilder.append(showtime.getStartTime());
        stringBuilder.append(HTML_PARA_END);

        stringBuilder.append(HTML_PARA_START);
        stringBuilder.append("CC: ");
        if (showtime.isCc()) {
            stringBuilder.append("Supported");
        } else {
            stringBuilder.append("Not supported");
        }
        stringBuilder.append(HTML_PARA_END);

        stringBuilder.append(HTML_PARA_START);
        stringBuilder.append("Auditorium #: ");
        stringBuilder.append(showtime.getaId());
        stringBuilder.append(HTML_PARA_END);

        stringBuilder.append(HTML_END);

        return stringBuilder.toString();
    }
}

package layout.classGUI.customer;

import layout.MainFrame;
import model.Movie;
import model.MovieInfo;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerBookingForm {
    private static final String HTML_START = "<html>";
    private static final String HTML_PARA_START = "<p>";
    private static final String HTML_PARA_END = "</p>";
    private static final String HTML_END = "</html>";

    private JPanel mainPanel;
    private List<MovieInfo> movieInfoList;

    public CustomerBookingForm(MainFrame mainFrame) {
        /*
         * TODO: Get list of movie from DB
         */
        movieInfoList = new ArrayList<>();
        movieInfoList.add(new MovieInfo("Incredibles 2", 125, "Family", "PG"));

        for (MovieInfo movieInfo : movieInfoList) {
            JButton movieButton = new JButton();
            movieButton.setText(movieInfoHtmlParser(movieInfo));
            mainPanel.add(movieButton);

            /*
             * TODO: Add a new action listener to booking page for movie
             */
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private String movieInfoHtmlParser(MovieInfo movieInfo) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(HTML_START);

        stringBuilder.append(HTML_PARA_START);
        stringBuilder.append("Title: ");
        stringBuilder.append(movieInfo.getTitle().trim());
        stringBuilder.append(HTML_PARA_END);

        stringBuilder.append(HTML_PARA_START);
        stringBuilder.append("Genre: ");
        stringBuilder.append(movieInfo.getGenre().trim());
        stringBuilder.append(HTML_PARA_END);

        stringBuilder.append(HTML_PARA_START);
        stringBuilder.append("Duration: ");
        stringBuilder.append(movieInfo.getDuration());
        stringBuilder.append("mins");
        stringBuilder.append(HTML_PARA_END);

        stringBuilder.append(HTML_PARA_START);
        stringBuilder.append("Age Rest.: ");
        stringBuilder.append(movieInfo.getCensor().trim());
        stringBuilder.append(HTML_PARA_END);

        stringBuilder.append(HTML_END);

        return stringBuilder.toString();
    }
}

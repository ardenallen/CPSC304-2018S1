package layout.classGUI.customer;

import layout.MainFrame;
import model.Movie;

import javax.swing.*;
import java.util.List;

public class CustomerBookingForm {
    private static final String HTML_START = "<html>";
    private static final String HTML_PARA_START = "<p>";
    private static final String HTML_PARA_END = "</p>";
    private static final String HTML_END = "</html>";

    private JPanel mainPanel;
    private List<Movie> movieList;

    public CustomerBookingForm(MainFrame mainFrame) {
        /*
         * TODO: Get list of movie from DB
         */

        for (Movie movie : movieList) {
            JButton movieButton = new JButton();
            movieButton.setText(movieInfoHtmlParser(movie));
            mainPanel.add(movieButton);

            /*
             * TODO: Add a new action listener to booking page for movie
             */
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private String movieInfoHtmlParser(Movie movie) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(HTML_START);

        stringBuilder.append(HTML_PARA_START);
        stringBuilder.append("Title: ");
        stringBuilder.append(movie.getTitle());
        stringBuilder.append(HTML_PARA_END);

        stringBuilder.append(HTML_PARA_START);
        stringBuilder.append("Genre: ");
        stringBuilder.append(movie.getGenre());
        stringBuilder.append(HTML_PARA_END);

        stringBuilder.append(HTML_PARA_START);
        stringBuilder.append("Duration: ");
        stringBuilder.append(movie.getDuration());
        stringBuilder.append("mins");
        stringBuilder.append(HTML_PARA_END);

        stringBuilder.append(HTML_PARA_START);
        stringBuilder.append("Age Rest.: ");
        stringBuilder.append(movie.getCensor());
        stringBuilder.append(HTML_PARA_END);

        stringBuilder.append(HTML_END);

        return stringBuilder.toString();
    }
}

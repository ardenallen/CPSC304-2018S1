package layout.classGUI;

import layout.MainFrame;
import model.Movie;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MovieSelectionForm {
    private static final String HTML_START = "<html>";
    private static final String HTML_PARA_START = "<p>";
    private static final String HTML_PARA_END = "</p>";
    private static final String HTML_END = "</html>";

    private JPanel mainPanel;
    private List<Movie> movieList;

    public MovieSelectionForm(MainFrame mainFrame) {
        /*
         * TODO: Get list of movie from DB
         *
         * Below is just placeholder for now
         */
        movieList = new ArrayList<>();
        movieList.add(new Movie("Incredibles 2", 125, "Family", "PG"));

        for (Movie movie : movieList) {
            JButton movieButton = new JButton();
            movieButton.setText(movieInfoHtmlParser(movie));
            mainPanel.add(movieButton);

            /*
             * TODO: Add a new action listener to showtime selection screen
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
        stringBuilder.append(movie.getTitle().trim());
        stringBuilder.append(HTML_PARA_END);

        stringBuilder.append(HTML_PARA_START);
        stringBuilder.append("Genre: ");
        stringBuilder.append(movie.getGenre().trim());
        stringBuilder.append(HTML_PARA_END);

        stringBuilder.append(HTML_PARA_START);
        stringBuilder.append("Duration: ");
        stringBuilder.append(movie.getDuration());
        stringBuilder.append("mins");
        stringBuilder.append(HTML_PARA_END);

        stringBuilder.append(HTML_PARA_START);
        stringBuilder.append("Age Rest.: ");
        stringBuilder.append(movie.getCensor().trim());
        stringBuilder.append(HTML_PARA_END);

        stringBuilder.append(HTML_END);

        return stringBuilder.toString();
    }
}

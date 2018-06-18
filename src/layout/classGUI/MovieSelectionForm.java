package layout.classGUI;

import layout.MainFrame;
import model.Movie;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MovieSelectionForm {
    private static final String HTML_START = "<html>";
    private static final String HTML_PARA_START = "<p>";
    private static final String HTML_PARA_END = "</p>";
    private static final String HTML_END = "</html>";

    private JPanel mainPanel;
    private JPanel moviesPanel;
    private List<Movie> movieList;

    public MovieSelectionForm(MainFrame mainFrame) {
        /*
         * Movie button generator
         */
        movieList = Movie.getAllMovie();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        for (Movie movie : movieList) {
            JButton movieButton = new JButton();
            movieButton.setText(movieInfoHtmlParser(movie));
            movieButton.setHorizontalAlignment(SwingConstants.LEFT);
            movieButton.addActionListener(e -> {
                mainFrame.changeToShowtimeSelectForm(movie);
            });
            moviesPanel.add(movieButton, gbc);
        }

        /*
         * Back button handler
         */
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            if (mainFrame.getCurrentUserClass().equals("customer")) {
                mainFrame.backToCustomerMainForm();
            } else if (mainFrame.getCurrentUserClass().equals("employee")) {
                mainFrame.backToEmployeeMainForm();
            } else {
                mainFrame.backToManagerMainForm();
            }
        });
        mainPanel.add(backButton);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private String movieInfoHtmlParser(Movie movie) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(HTML_START);

        stringBuilder.append(HTML_PARA_START);
        stringBuilder.append("<b>Title: </b>");
        stringBuilder.append(movie.getTitle().trim());
        stringBuilder.append(HTML_PARA_END);

        stringBuilder.append(HTML_PARA_START);
        stringBuilder.append("<b>Genre: </b>");
        stringBuilder.append(movie.getGenre().trim());
        stringBuilder.append(HTML_PARA_END);

        stringBuilder.append(HTML_PARA_START);
        stringBuilder.append("<b>Duration: </b>");
        stringBuilder.append(movie.getDuration());
        stringBuilder.append("mins");
        stringBuilder.append(HTML_PARA_END);

        stringBuilder.append(HTML_PARA_START);
        stringBuilder.append("<b>Age Rest.: </b>");
        stringBuilder.append(movie.getCensor().trim());
        stringBuilder.append(HTML_PARA_END);

        stringBuilder.append(HTML_END);

        return stringBuilder.toString();
    }
}

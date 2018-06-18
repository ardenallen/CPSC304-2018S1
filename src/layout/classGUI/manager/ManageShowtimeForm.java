package layout.classGUI.manager;

import layout.MainFrame;
import model.Movie;
import model.Showtime;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ManageShowtimeForm {
    private static final String HTML_START = "<html>";
    private static final String HTML_PARA_START = "<p>";
    private static final String HTML_PARA_END = "</p>";
    private static final String HTML_END = "</html>";

    private JPanel mainPanel;
    private JPanel showtimesPanel;
    private JButton addButton;
    private JButton backButton;
    private JButton removeButton;

    private Movie movie;
    private List<Showtime> showtimeList;
    private MainFrame mainFrame;

    public ManageShowtimeForm(Movie movie, MainFrame mainFrame) {
        this.movie = movie;
        this.mainFrame = mainFrame;
        showtimeList = Showtime.getAllShowtimes(movie.getTitle());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        for (Showtime showtime : showtimeList) {
            JButton showTimeButton = new JButton();
            showTimeButton.setText(showtimeInfoHtmlParser(showtime));
            showTimeButton.setEnabled(false);
            showtimesPanel.add(showTimeButton, gbc);
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
         * Add button handler
         */
        addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            mainFrame.changeToAddShowtimeForm(movie);
        });

        /*
         * Remove button handler
         */
        removeButton = new JButton("Remove");
        removeButton.addActionListener(e -> {
            mainFrame.changeToRemoveShowtimeForm(movie, showtimeList);
        });

        /*
         * Back button handler
         */
        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            mainFrame.backAndRefreshManageMovieForm();
        });
    }
}

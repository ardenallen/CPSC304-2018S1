package layout.classGUI.customer;

import layout.MainFrame;
import layout.dialog.MovieNoLongerOfferedDialog;
import model.Customer;
import model.Movie;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CustomerRecommForm {
    private JPanel mainPanel;

    public CustomerRecommForm(MainFrame mainFrame) {
        List<String> recommendations = Customer.getRecommendations();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int count = 1;

        for (String recommendation : recommendations) {
            JButton recommendationButton = new JButton();
            recommendationButton.setText(recommendation);

            Movie movie = Movie.findMovieByTitle(count + ". " + recommendation);

            recommendationButton.addActionListener(e -> {
                if (movie == null) {
                    MovieNoLongerOfferedDialog movieNoLongerOfferedDialog = new MovieNoLongerOfferedDialog();
                    movieNoLongerOfferedDialog.pack();
                    movieNoLongerOfferedDialog.setLocationRelativeTo(mainPanel);
                    movieNoLongerOfferedDialog.setVisible(true);
                } else {
                    mainFrame.changeToShowtimeSelectForm(movie);
                }
            });

            mainPanel.add(recommendationButton, gbc);

            count++;
        }

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            mainFrame.backToCustomerMainForm();
        });

        mainPanel.add(backButton);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}

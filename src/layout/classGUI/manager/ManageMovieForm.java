package layout.classGUI.manager;

import layout.MainFrame;
import layout.dialog.RemoveMovieDialog;
import model.Movie;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ManageMovieForm {
    private JPanel mainPanel;
    private JPanel buttonsPanel;
    private JPanel moviesPanel;
    private JButton addButton;
    private JButton removeButton;
    private JButton backButton;

    private MainFrame mainFrame;

    public ManageMovieForm(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        List<Movie> movieList = Movie.getAllMovie();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        for (Movie movie : movieList) {
            JButton movieButton = new JButton();
            movieButton.setText(movie.getTitle());
            movieButton.addActionListener(e -> {
                mainFrame.changeToManageShowtimeForm(movie);
            });
            moviesPanel.add(movieButton, gbc);
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        /*
         * Add button handler
         */
        addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            mainFrame.changeToAddMovieForm();
        });

        /*
         * Remove button handler
         */
        removeButton = new JButton("Remove");
        removeButton.addActionListener(e -> {
            RemoveMovieDialog removeMovieDialog = new RemoveMovieDialog(mainFrame);
            removeMovieDialog.pack();
            removeMovieDialog.setLocationRelativeTo(mainPanel);
            removeMovieDialog.setVisible(true);
        });

        /*
         * Back button handler
         */
        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            mainFrame.backToManagerMainForm();
        });
    }
}

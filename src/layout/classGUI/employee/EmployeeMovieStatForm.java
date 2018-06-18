package layout.classGUI.employee;

import layout.MainFrame;
import model.Employee;
import model.Movie;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class EmployeeMovieStatForm {
    private JButton minButton;
    private JButton maxButton;
    private JTable movieTable;
    private JButton backButton;
    private JPanel mainPanel;

    private MainFrame mainFrame;

    public EmployeeMovieStatForm(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        /*
         * Table setup
         */
        Object[] headers = {"Rank", "Title", "Genre"};

        movieTable = new JTable(new DefaultTableModel(headers, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        DefaultTableModel model = (DefaultTableModel) movieTable.getModel();

        /*
         * Min button setup
         */
        minButton = new JButton("Minimum sale");
        minButton.addActionListener(e -> {
            model.setRowCount(0);

            List<Movie> minMovieList = Employee.getLeastMostPopularMovie("min");

            int count = 1;

            for (Movie movie : minMovieList) {
                model.addRow(new Object[] {count, movie.getTitle(), movie.getGenre()});
                count++;
            }

            model.fireTableDataChanged();
        });

        /*
         * Max button setup
         */
        maxButton = new JButton("Maximum sale");
        maxButton.addActionListener(e -> {
            model.setRowCount(0);

            List<Movie> maxMovieList = Employee.getLeastMostPopularMovie("max");

            int count = 1;

            for (Movie movie : maxMovieList) {
                model.addRow(new Object[] {count, movie.getTitle(), movie.getGenre()});
                count++;
            }

            model.fireTableDataChanged();
        });

        /*
         * Back button setup
         */
        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            mainFrame.backToEmployeeMainForm();
            // TODO: Add back to manager form
        });
    }
}

package layout.classGUI.employee;

import layout.MainFrame;
import model.Employee;
import model.MovieStat;

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
        Object[] headers = {"Rank", "Title", "# of tickets sold"};

        movieTable = new JTable(new DefaultTableModel(headers, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        DefaultTableModel model = (DefaultTableModel) movieTable.getModel();

        List<MovieStat> allMovies = Employee.getAllMovieStats();

        for (MovieStat movie : allMovies) {
            model.addRow(new Object[] {"N/A", movie.title, movie.count});
        }

        /*
         * Min button setup
         */
        minButton = new JButton("Minimum sale");
        minButton.addActionListener(e -> {
            model.setRowCount(0);

            List<MovieStat> minMovieList = Employee.getLeastMostPopularMovie("min");

            int count = 1;

            for (MovieStat movie : minMovieList) {
                model.addRow(new Object[] {count, movie.title, movie.count});
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

            List<MovieStat> maxMovieList = Employee.getLeastMostPopularMovie("max");

            int count = 1;

            for (MovieStat movie : maxMovieList) {
                model.addRow(new Object[] {count, movie.title, movie.count});
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

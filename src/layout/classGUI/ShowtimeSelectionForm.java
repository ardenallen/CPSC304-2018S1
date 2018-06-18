package layout.classGUI;

import layout.MainFrame;
import model.Auditorium;
import model.Movie;
import model.Showtime;
import model.Ticket;
import sun.applet.Main;
import utils.OracleConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class ShowtimeSelectionForm {
    private static final String HTML_START = "<html>";
    private static final String HTML_PARA_START = "<p>";
    private static final String HTML_PARA_END = "</p>";
    private static final String HTML_END = "</html>";

    private JPanel mainPanel;
    private JPanel showtimesPanel;
    private MainFrame mainFrame;

    private List<Showtime> showtimeList;

    public ShowtimeSelectionForm(Movie movie, MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        /*
         * Showtime button generator
         */
        showtimeList = Showtime.getAllShowtimes(movie);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        for (Showtime showtime : showtimeList) {
            JButton showTimeButton = new JButton();
            int bookedTicketNum = Ticket.getTicketsOfShowtime(showtime).size();
            int auditoriumCapacity = Auditorium.getAuditoriumCapacity(showtime.getaId());
            showTimeButton.setText(showtimeInfoHtmlParser(showtime, bookedTicketNum, auditoriumCapacity));
            showTimeButton.addActionListener(e -> {
                if (mainFrame.getCurrentUserClass().equals("customer")) {
                    mainFrame.changeToCustomerBookingForm(movie, showtime);
                } else {
                    // Current class is either employee or manager
                    mainFrame.changeToEmployeeSellingForm(movie, showtime);
                }
            });

            if (bookedTicketNum == auditoriumCapacity) {
                showTimeButton.setEnabled(false);
            }

            showtimesPanel.add(showTimeButton, gbc);
        }

        /*
         * Back button handler
         */
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            mainFrame.backToPreviousForm();
        });
        mainPanel.add(backButton);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private String showtimeInfoHtmlParser(Showtime showtime, int bookedTicketNum, int auditoriumCapacity) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(HTML_START);

        stringBuilder.append(HTML_PARA_START);
        stringBuilder.append("<b>Start time: </b>");
        stringBuilder.append(showtime.getStartTime());
        stringBuilder.append(HTML_PARA_END);

        stringBuilder.append(HTML_PARA_START);
        stringBuilder.append("<b>CC: </b>");
        if (showtime.isCc()) {
            stringBuilder.append("Supported");
        } else {
            stringBuilder.append("Not supported");
        }
        stringBuilder.append(HTML_PARA_END);

        stringBuilder.append(HTML_PARA_START);
        stringBuilder.append("<b>Auditorium #: </b>");
        stringBuilder.append(showtime.getaId());
        stringBuilder.append(HTML_PARA_END);

        if (!mainFrame.getCurrentUserClass().equals("customer")) {
            stringBuilder.append(HTML_PARA_START);
            stringBuilder.append("<b>Availability: </b>");
            stringBuilder.append(bookedTicketNum);
            stringBuilder.append("/");
            stringBuilder.append(auditoriumCapacity);
            stringBuilder.append(HTML_PARA_END);
        }

        stringBuilder.append(HTML_END);

        return stringBuilder.toString();
    }
}

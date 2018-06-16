package layout;

import layout.classGUI.MovieSelectionForm;
import layout.classGUI.ShowtimeSelectionForm;
import layout.classGUI.customer.history.CustomerHistoryForm;
import layout.classGUI.customer.CustomerMainForm;
import layout.classGUI.customer.history.CustomerTicketsForm;
import layout.classGUI.employee.EmployeeMainForm;
import layout.classGUI.manager.ManagerMainForm;
import model.Booking;
import model.Customer;
import model.Movie;
import model.User;

import javax.swing.*;

/**
 * Main frame to handle GUI
 */
public class MainFrame {
    private static final int WINDOW_WIDTH = 1280;
    private static final int WINDOW_HEIGHT = 720;

    private JFrame mainFrame;

    private LoginForm loginForm;
    private MovieSelectionForm movieSelectionForm;
    private ShowtimeSelectionForm showtimeSelectionForm;

    private CustomerMainForm customerMainForm;
    private CustomerHistoryForm customerHistoryForm;
    private CustomerTicketsForm customerTicketsForm;

    private EmployeeMainForm employeeMainForm;
    private ManagerMainForm managerMainForm;

    private MainFrame() {
        mainFrame = new JFrame("Theatre Management Software");
        mainFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        loginForm = new LoginForm(this);
        mainFrame.setContentPane(loginForm.getMainPanel());

        mainFrame.setVisible(true);
    }

    public static void main(String args[]) {
        MainFrame mainFrame = new MainFrame();
    }

    /**
     * General UI handler
     */

    public void switchClassPanel(User user) {
        switch(user.getUserClass()) {
            case "customer":
                Customer customer = new Customer(user.getUserId(), "");
                customerMainForm = new CustomerMainForm(this, customer);
                removeContent();
                changeContent(customerMainForm.getMainPanel());
                break;

            case "employee":
                employeeMainForm = new EmployeeMainForm(this);
                removeContent();
                changeContent(employeeMainForm.getMainPanel());
                break;

            case "manager":
                managerMainForm = new ManagerMainForm(this);
                removeContent();
                changeContent(managerMainForm.getMainPanel());
                break;
        }
    }

    public void logout() {
        removeContent();
        loginForm = new LoginForm(this);
        changeContent(loginForm.getMainPanel());
    }

    public void changeToMovieSelectPanel() {
        movieSelectionForm = new MovieSelectionForm(this);
        changeContent(movieSelectionForm.getMainPanel());
    }

    public void changeToShowtimeSelectPanel(Movie movie) {
        showtimeSelectionForm = new ShowtimeSelectionForm(movie, this);
        changeContent(showtimeSelectionForm.getMainPanel());
    }

    public void backToMovieSelectionForm() {
        removeContent();
        changeContent(movieSelectionForm.getMainPanel());
    }

    public void backToShowtimeSelectionForm() {
        removeContent();
        changeContent(showtimeSelectionForm.getMainPanel());
    }

    /**
     * Customer specific UI handler
     */
    public void refreshCustomerFrame(Customer customer) {
        customerMainForm = new CustomerMainForm(this, customer);
        removeContent();
        changeContent(customerMainForm.getMainPanel());
    }

    public void backToCustomerMainForm() {
        removeContent();
        changeContent(customerMainForm.getMainPanel());
    }

    public void changeToCustomerHistoryPanel(Customer customer) {
        customerHistoryForm = new CustomerHistoryForm(this, customer);
        changeContent(customerHistoryForm.getMainPanel());
    }

    public void changeToCustomerTicketsPanel(Booking booking) {
        customerTicketsForm = new CustomerTicketsForm(this, booking);
        changeContent(customerTicketsForm.getMainPanel());
    }

    public void backToCustomerHistorySelectionForm() {
        removeContent();
        changeContent(customerHistoryForm.getMainPanel());
    }

    /**
     * UI helper methods
     */
    private void removeContent() {
        mainFrame.getContentPane().removeAll();
        repaintFrame();
    }

    private void changeContent(JPanel newPanel) {
        mainFrame.setContentPane(newPanel);
        repaintFrame();
    }

    private void repaintFrame() {
        mainFrame.validate();
        mainFrame.repaint();
    }
}

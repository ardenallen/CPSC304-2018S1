package layout;

import layout.classGUI.LoyaltyPointRedeemForm;
import layout.classGUI.MovieSelectionForm;
import layout.classGUI.ShowtimeSelectionForm;
import layout.classGUI.customer.CustomerBookingForm;
import layout.classGUI.customer.CustomerRecommForm;
import layout.classGUI.customer.history.CustomerHistoryForm;
import layout.classGUI.customer.CustomerMainForm;
import layout.classGUI.customer.history.CustomerTicketsForm;
import layout.classGUI.employee.EmployeeMovieStatForm;
import layout.classGUI.employee.EmployeeSellingForm;
import layout.classGUI.employee.EmployeeMainForm;
import layout.classGUI.employee.EmployeeRefundForm;
import layout.classGUI.manager.*;
import model.*;

import javax.swing.*;
import java.util.List;
import java.awt.*;

/**
 * Main frame to handle GUI
 */
public class MainFrame {
    private static final int WINDOW_WIDTH = 1280;
    private static final int WINDOW_HEIGHT = 720;

    private JFrame mainFrame;
    private Container previousContainer;

    private String currentUserClass;
    private LoginForm loginForm;
    private MovieSelectionForm movieSelectionForm;
    private ShowtimeSelectionForm showtimeSelectionForm;
    private LoyaltyPointRedeemForm loyaltyPointRedeemForm;

    private Customer customer;
    private CustomerMainForm customerMainForm;
    private CustomerBookingForm customerBookingForm;
    private CustomerHistoryForm customerHistoryForm;
    private CustomerTicketsForm customerTicketsForm;
    private CustomerRecommForm customerRecommForm;

    private Employee employee;
    private EmployeeMainForm employeeMainForm;
    private EmployeeSellingForm employeeSellingForm;
    private EmployeeRefundForm employeeRefundForm;
    private EmployeeMovieStatForm employeeMovieStatForm;

    private Manager manager;
    private ManageEmployeeForm manageEmployeeForm;
    private UpdateEmployeeForm updateEmployeeForm;
    private AddEmployeeForm addEmployeeForm;
    private ManageMovieForm manageMovieForm;
    private AddMovieForm addMovieForm;
    private ManageShowtimeForm manageShowtimeForm;
    private AddShowtimeForm addShowtimeForm;
    private RemoveShowtimeForm removeShowtimeForm;
    private ManagerEmployeeStatForm managerEmployeeStatForm;
    private BestCustomerForm bestCustomerForm;
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
        customer = null;
        employee = null;
        manager = null;

        switch(user.getUserClass()) {
            case "customer":
                currentUserClass = "customer";
                customer = new Customer(user.getUserId());
                customerMainForm = new CustomerMainForm(this, customer);
                removeContent();
                changeContent(customerMainForm.getMainPanel());
                break;

            case "employee":
                currentUserClass = "employee";
                employee = new Employee(user.getUserId());
                employeeMainForm = new EmployeeMainForm(this, employee);
                removeContent();
                changeContent(employeeMainForm.getMainPanel());
                break;

            case "manager":
                currentUserClass = "manager";
                manager = new Manager(user.getUserId());

                // To make sure the UI doesn't get confused (manager is also employee)
                employee = manager;

                managerMainForm = new ManagerMainForm(this, manager);
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

    public void changeToMovieSelectForm() {
        movieSelectionForm = new MovieSelectionForm(this);
        changeContent(movieSelectionForm.getMainPanel());
    }

    public void changeToShowtimeSelectForm(Movie movie) {
        showtimeSelectionForm = new ShowtimeSelectionForm(movie, this);
        changeContent(showtimeSelectionForm.getMainPanel());
    }

    public void changeToLoyaltyPointRedeemForm(Customer customer, Employee employee, Movie movie, Showtime showtime) {
        loyaltyPointRedeemForm = new LoyaltyPointRedeemForm(customer, employee, movie, showtime, this);
        changeContent(loyaltyPointRedeemForm.getMainPanel());
    }

    public void backToPreviousForm() {
        removeContent();
        changeContent(previousContainer);
    }

    public String getCurrentUserClass() {
        return currentUserClass;
    }

    public JFrame getMainFrame() {
        return mainFrame;
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

    public void changeToCustomerBookingForm(Movie movie, Showtime showtime) {
        customerBookingForm = new CustomerBookingForm(movie, showtime, this);
        changeContent(customerBookingForm.getMainPanel());
    }

    public void changeToCustomerHistoryForm(Customer customer) {
        customerHistoryForm = new CustomerHistoryForm(this, customer);
        changeContent(customerHistoryForm.getMainPanel());
    }

    public void changeToCustomerTicketsForm(Booking booking) {
        customerTicketsForm = new CustomerTicketsForm(this, booking);
        changeContent(customerTicketsForm.getMainPanel());
    }

    public void changeToCustomerRecommForm() {
        customerRecommForm = new CustomerRecommForm(this);
        changeContent(customerRecommForm.getMainPanel());
    }

    public Customer getCustomer() {
        return customer;
    }

    /**
     * Employee specific UI handler
     */
    public void changeToEmployeeSellingForm(Movie movie, Showtime showtime) {
        employeeSellingForm = new EmployeeSellingForm(movie, showtime, this);
        changeContent(employeeSellingForm.getMainPanel());
    }

    public void changeToEmployeeViewBookingForm(Booking booking) {
        employeeRefundForm = new EmployeeRefundForm(booking, this);
        changeContent(employeeRefundForm.getMainPanel());
    }

    public void changeToEmployeeViewMovieStatForm() {
        employeeMovieStatForm = new EmployeeMovieStatForm(this);
        changeContent(employeeMovieStatForm.getMainPanel());
    }

    public void backToEmployeeMainForm() {
        removeContent();
        changeContent(employeeMainForm.getMainPanel());
    }

    public Employee getEmployee() {
        return employee;
    }

    /**
     * Manager specific UI handler
     */
    public void changeToManageEmployeeForm() {
        manageEmployeeForm = new ManageEmployeeForm(this);
        changeContent(manageEmployeeForm.getMainPanel());
    }

    public void changeToUpdateEmployeeForm(Employee employee) {
        updateEmployeeForm = new UpdateEmployeeForm(employee, this);
        changeContent(updateEmployeeForm.getMainPanel());
    }

    public void changeToAddEmployeeForm() {
        addEmployeeForm = new AddEmployeeForm(this);
        changeContent(addEmployeeForm.getMainPanel());
    }

    public void changeToManageMovieForm() {
        manageMovieForm = new ManageMovieForm(this);
        changeContent(manageMovieForm.getMainPanel());
    }

    public void changeToAddMovieForm() {
        addMovieForm = new AddMovieForm(this);
        changeContent(addMovieForm.getMainPanel());
    }

    public void changeToManageShowtimeForm(Movie movie) {
        manageShowtimeForm = new ManageShowtimeForm(movie, this);
        changeContent(manageShowtimeForm.getMainPanel());
    }

    public void changeToAddShowtimeForm(Movie movie) {
        addShowtimeForm = new AddShowtimeForm(movie, this);
        changeContent(addShowtimeForm.getMainPanel());
    }

    public void changeToRemoveShowtimeForm(Movie movie, List<Showtime> showtimeList) {
        removeShowtimeForm = new RemoveShowtimeForm(movie, showtimeList, this);
        changeContent(removeShowtimeForm.getMainPanel());
    }

    public void backAndRefreshManageShowtimeForm(Movie movie) {
        removeContent();
        manageShowtimeForm = new ManageShowtimeForm(movie, this);
        changeContent(manageShowtimeForm.getMainPanel());
    }

    public void backAndRefreshManageMovieForm() {
        removeContent();
        manageMovieForm = new ManageMovieForm(this);
        changeContent(manageMovieForm.getMainPanel());
    }

    public void backAndRefreshManageEmployeeForm() {
        removeContent();
        manageEmployeeForm = new ManageEmployeeForm(this);
        changeContent(manageEmployeeForm.getMainPanel());
    }

    public void backToManagerMainForm() {
        removeContent();
        changeContent(managerMainForm.getMainPanel());
    }

    public void changeToManagerViewEmployeeStatForm() {
        managerEmployeeStatForm = new ManagerEmployeeStatForm(this);
        changeContent(managerEmployeeStatForm.getMainPanel());
    }

    public void changeToBestCustomerForm() {
        bestCustomerForm = new BestCustomerForm(this);
        changeContent(bestCustomerForm.getMainPanel());
    }

    public Manager getManager() {
        return manager;
    }

    /**
     * UI helper methods
     */
    private void removeContent() {
        mainFrame.getContentPane().removeAll();
        repaintFrame();
    }

    private void changeContent(Container container) {
        previousContainer = mainFrame.getContentPane();
        mainFrame.setContentPane(container);
        repaintFrame();
    }

    private void changeContent(JPanel newPanel) {
        previousContainer = mainFrame.getContentPane();
        mainFrame.setContentPane(newPanel);
        repaintFrame();
    }

    private void repaintFrame() {
        mainFrame.validate();
        mainFrame.repaint();
    }
}

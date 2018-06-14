package layout;

import layout.classGUI.customer.CustomerBookingForm;
import layout.classGUI.customer.CustomerMainForm;
import layout.classGUI.employee.EmployeeMainForm;
import layout.classGUI.manager.ManagerMainForm;
import model.Customer;
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
    private CustomerMainForm customerMainForm;
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

    public void switchClassPanel(User user) {
        switch(user.getUserClass()) {
            case "customer":
                Customer customer = new Customer(user.getUserId());
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

    public void refreshCustomerFrame(Customer customer) {
        customerMainForm = new CustomerMainForm(this, customer);
        removeContent();
        changeContent(customerMainForm.getMainPanel());
    }

    public void changeToCustomerBookingPanel() {
        CustomerBookingForm customerBookingForm = new CustomerBookingForm(this);
        changeContent(customerBookingForm.getMainPanel());
    }

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

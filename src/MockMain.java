import layout.LoginForm;

import javax.swing.*;

/**
 * Mock class to test GUI
 */
public class MockMain {
    private static final int WINDOW_WIDTH = 1280;
    private static final int WINDOW_HEIGHT = 720;

    private JFrame mainFrame;
    private LoginForm loginForm;

    public MockMain() {
        mainFrame = new JFrame("Theatre Management Software");
        mainFrame.setVisible(true);
        mainFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        loginForm = new LoginForm();
        mainFrame.setContentPane(loginForm.getMainPanel());
    }

    public static void main(String args[]) {
        MockMain mockMain = new MockMain();
    }
}

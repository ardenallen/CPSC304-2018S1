import javax.swing.*;

/**
 * Mock class to test GUI
 */
public class MockMain {
    private static final int WINDOW_WIDTH = 1280;
    private static final int WINDOW_HEIGHT = 720;

    private JFrame mainFrame;

    public MockMain() {
        mainFrame = new JFrame();
        mainFrame.setVisible(true);
        mainFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    public static void main(String args[]) {
        MockMain mockMain = new MockMain();
    }
}

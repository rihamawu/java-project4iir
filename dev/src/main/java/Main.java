import view.LoginPage;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Use SwingUtilities.invokeLater to ensure thread safety
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginPage(); // Create and show the LoginPage UI
            }
        });
    }
}
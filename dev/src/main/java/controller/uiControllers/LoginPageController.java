package controller.uiControllers;




import view.pages.AdminDashboard.AdminDashboard;
import view.pages.AuditorDashboard.AuditorDashboard;
import view.pages.LoginPage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPageController {
    private LoginPage view;

    public LoginPageController(LoginPage view) {
        this.view = view;
        AddLoginPageEvents();


    }

    void AddLoginPageEvents() {
        view.GetLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = view.GetEmailField().getText();
                String password = new String(view.GetPasswordField().getPassword());

                // Handle login logic here
                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Email and password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (email.equals("a") && password.equals("a")) {
                    view.dispose(); // Close the LoginPage
                    new AdminDashboard(); // Open the Admin Dashboard
                } else if (email.equals("b") && password.equals("b")) {
                    view.dispose(); // Close the LoginPage
                    new AuditorDashboard(); // Open the Auditor Dashboard
                } else {
                    JOptionPane.showMessageDialog(view, "Incorrect Email/password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
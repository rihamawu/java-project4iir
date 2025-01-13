package controller;

import view.DashboardAdmin;
import view.DashboardAuditor;
import view.LoginPage;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private LoginPage view;

    public LoginController(LoginPage view) {
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
                } else if (email.equals("Admin@gmail.com") && password.equals("admin")) {
                    view.dispose(); // Close the LoginPage
                    new DashboardAdmin(); // Open the Admin Dashboard
                } else if (email.equals("Auditor@gmail.com") && password.equals("auditor")) {
                    view.dispose(); // Close the LoginPage
                    new DashboardAuditor(); // Open the Auditor Dashboard
                } else {
                    JOptionPane.showMessageDialog(view, "Incorrect Email/password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
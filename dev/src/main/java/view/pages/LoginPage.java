package view.pages;


import controller.uiControllers.LoginPageController;

import javax.swing.*;
import java.awt.*;

public class LoginPage extends JFrame {
    private JLabel title;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginPage() {
        SetupUi();
        LoginPageController loginController = new LoginPageController(this);

    }
    public JLabel GetTitle() {
        return title;
    }

    public JButton GetLoginButton() {
        return loginButton;
    }

    public JTextField GetEmailField() {
        return emailField;
    }

    public JPasswordField GetPasswordField() {
        return passwordField;
    }

    public void SetupUi() {
        setTitle("Login Page");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        title = new JLabel("Login", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(title);

        emailField = new JTextField();
        emailField.setBorder(BorderFactory.createTitledBorder("Email"));
        panel.add(emailField);

        passwordField = new JPasswordField();
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));
        panel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 16));
        loginButton.setBackground(new Color(0, 120, 215));
        loginButton.setForeground(Color.WHITE);
        panel.add(loginButton);

        add(panel);
        setVisible(true);
    }
    public static void main(String[] args) {
        // Add the LoginPage panel to the frame
        LoginPage loginPage = new LoginPage();
        loginPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginPage.setSize(500, 400);
        loginPage.setLocationRelativeTo(null); // Center the frame
        loginPage.setVisible(true);
    }



}
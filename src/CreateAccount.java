import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateAccount extends JFrame {
    private JPanel panel;
    private JPanel menuPanel;
    private JLabel title;
    private JLabel usernameLabel;
    private JTextField usernameTextField;
    private JLabel passwordLabel;
    private JButton cancelButton;
    private JButton createButton;
    private JTextField emailTextField;
    private JLabel newPasswordLabel;
    private JPasswordField passwordTextField;
    private JLabel icon;
    private JPasswordField confirmPasswordTextField;
    private JTextField nameTextField;
    public void addUserToDb() {
        System.out.println("HEre");
    }
    public CreateAccount() {
        ImageIcon iconImg = new ImageIcon("images/controller_small.png");
        icon.setIcon(iconImg);

        setTitle("Create a New Account");
        setContentPane(panel);
        pack();
        setSize(1100, 700);
        setVisible(true);




        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginMenu menu = new LoginMenu();
                menu.setVisible(true);
            }
        });

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name, username, email;
                String password;
                String confirmPassword;
                GameArenaSystem system = GameArenaSystem.load();

                name = nameTextField.getText();
                username = usernameTextField.getText();
                email = emailTextField.getText();
                password = String.valueOf(passwordTextField.getPassword());
                confirmPassword = String.valueOf(confirmPasswordTextField.getPassword());

                if ( username.length() == 0 || email.length() == 0 || name.length() == 0 || password.length() == 0 || confirmPassword.length() == 0 )
                    JOptionPane.showMessageDialog(null, "Please fill out all fields.", "Credentials Missing", JOptionPane.ERROR_MESSAGE);

                else if (!password.equals(confirmPassword))
                    JOptionPane.showMessageDialog(null, "Passwords do not match. Please try again.", "Password Mismatch", JOptionPane.ERROR_MESSAGE);

                else if (system.emailExists(email))
                    JOptionPane.showMessageDialog(null, "An account with the email '" + email + "' already exists. Please log in or use another email account.", "Account Exists", JOptionPane.ERROR_MESSAGE);

                else if (system.usernameExists(username))
                    JOptionPane.showMessageDialog(null, "An account with the username '" + username + "' already exists. Please choose another username.", "Username Taken", JOptionPane.ERROR_MESSAGE);

                else {
                    User user = new User(username, email, name, password);
                    system.registerUser(user);
                    JOptionPane.showMessageDialog(null, "Account created successfully!", "Account Created", JOptionPane.INFORMATION_MESSAGE);

                    dispose();
                    UserAccount userAccount = new UserAccount(user);
                    userAccount.setVisible(true);
                }
            }
        });
    }
}

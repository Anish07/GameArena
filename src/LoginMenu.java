import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginMenu extends JFrame {
    private JPanel panel, menuPanel;
    private JLabel icon, title,usernameLabel, passwordLabel, createButtonLabel;
    private JTextField usernameTextField;
    private JPasswordField passwordField1;
    private JButton loginButton, forgotButton, createAccountButton;
    private JSeparator sep;


    public LoginMenu() {
        ImageIcon iconImg = new ImageIcon("images/controller_small.png");
        icon.setIcon(iconImg);

        setTitle("Game Arena Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);
        pack();
        setSize(1100, 600);
        setVisible(true);


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameArenaSystem system;
                User user;

                String username = usernameTextField.getText();
                String password = String.valueOf(passwordField1.getPassword());

                if (username.length() == 0 || password.length() == 0) {
                    JOptionPane.showMessageDialog(null, "Please enter your username and password.", "Credentials Missing", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                system = GameArenaSystem.load();
                user = system.login(username, password);

                if (user == null) {
                    if (system.usernameExists(username))
                      JOptionPane.showMessageDialog(null,"Password is invalid, please try again.","Login Error", JOptionPane.ERROR_MESSAGE);
                    else
                      JOptionPane.showMessageDialog(null,"Invalid username provided, please try again.","Login Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    dispose();
                    user.setOnline();
                    UserAccount userAccount = new UserAccount(user);
                    userAccount.setVisible(true);
                }
            }
        });

        forgotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ForgotPassword f = new ForgotPassword();
            }
        });


        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                CreateAccount c = new CreateAccount();
            }
        });
    }

    public static void main(String[] args) {
        LoginMenu menu = new LoginMenu();
    }
}

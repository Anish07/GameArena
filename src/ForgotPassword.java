import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ForgotPassword extends JFrame {
    private JPanel panel;
    private JPanel menuPanel;
    private JLabel icon;
    private JLabel title;
    private JLabel usernameLabel;
    private JTextField usernameTextField;
    private JLabel passwordLabel;
    private JButton cancelButton;
    private JButton resetButton;
    private JTextField emailTextField;
    private JLabel newPasswordLabel;
    private JPasswordField newPasswordTextField;

    public ForgotPassword() {
        ImageIcon iconImg = new ImageIcon("images/controller_small.png");
        icon.setIcon(iconImg);

        setTitle("Reset Password");
        setContentPane(panel);
        pack();
        setSize(1100, 600);
        setVisible(true);

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField.getText();
                String email = emailTextField.getText();
                String password = String.valueOf(newPasswordTextField.getPassword());
                GameArenaSystem system = GameArenaSystem.load();

                if (username.length() == 0 || email.length() == 0 )
                    JOptionPane.showMessageDialog(null, "Please enter the username and email associated with your account.", "Credentials Missing", JOptionPane.ERROR_MESSAGE);

                else if (password.length() == 0 )
                    JOptionPane.showMessageDialog(null, "Please enter a new password", "Password Missing", JOptionPane.ERROR_MESSAGE);

                else if (!system.accountExists(username, email))
                    JOptionPane.showMessageDialog(null, "No account exists with the provided username and email. Please try again.", "Invalid Credentials", JOptionPane.ERROR_MESSAGE);

                else {
                    system.getUser(username).setPassword(password);

                    system.updatePassword(username,email,password);
                    system.save();

                    JOptionPane.showMessageDialog(null, "Password successfully updated.", "Password Updated", JOptionPane.INFORMATION_MESSAGE);
                    dispose();

                    LoginMenu menu = new LoginMenu();
                    menu.setVisible(true);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginMenu menu = new LoginMenu();
                menu.setVisible(true);
            }
        });
    }

}

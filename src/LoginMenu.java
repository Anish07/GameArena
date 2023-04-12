import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LoginMenu extends JFrame {

    private final String PG_url = "jdbc:postgresql://localhost:5433/postgres";
    private final String PG_user = "postgres";
    private final String PG_password = "docker";


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

    public Connection init(){
        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

             //statement.executeUpdate("drop table if exists users");
             statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (\n" +
                    "  id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "  username TEXT NOT NULL,\n" +
                     " email TEXT NOT NULL,\n" +
                    "  password TEXT NOT NULL,\n" +
                    "  name TEXT NOT NULL \n" +
                    ")");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS friends (\n" +
                    "  id INTEGER PRIMARY KEY,\n" +
                    "  user_id INTEGER NOT NULL,\n" +
                    "  friend_id INTEGER NOT NULL,\n" +
                    "  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" +
                    "  FOREIGN KEY (user_id) REFERENCES users(id),\n" +
                    "  FOREIGN KEY (friend_id) REFERENCES users(id)\n" +
                    ");");
           // statement.executeUpdate("INSERT INTO users (username, password) VALUES ('jane_doi', 'passiooi')");

            ResultSet rs = statement.executeQuery("select * from users");
            while(rs.next())
            {
                // read the result set
                System.out.println("name = " + rs.getString("username"));
                System.out.println("password = " + rs.getString("password"));
                System.out.println("id = " + rs.getInt("id"));
            }
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return connection;
    }






    public static void main(String[] args) {
        LoginMenu menu = new LoginMenu();
        menu.init();
    }
}

import javax.swing.*;

public class FriendsList extends JFrame {
    private JTextArea friendsField;
    private JPanel panel1;

    public FriendsList(User user) {
        setContentPane(panel1);
        setSize(400, 500);
        setVisible(true);

        friendsField.setText("\n\n");
        for (User u: user.getFriendsList())
            friendsField.append("Username: " + u.getUsername() + "\tStatus: " + u.getStatus() + "\n");
    }
}

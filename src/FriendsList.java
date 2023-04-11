import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FriendsList extends JFrame {
    private JTextArea friendsField;
    private JPanel panel1;
    private JButton removeAFriendbtn;
    private User curuser;

    public FriendsList(User user) {
        this.curuser = user;
        setContentPane(panel1);
        setSize(400, 500);
        setVisible(true);
        removeAFriendbtn.setText("Remove a friend");
        friendsField.setText("\n\n");
        GameArenaSystem system = GameArenaSystem.load();
        for (User u: curuser.getFriendsList())
            friendsField.append("Username: " + u.getUsername() + "\tStatus: " + u.getStatus() + "\n");

        removeAFriendbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel();
                JLabel label = new JLabel("Enter username:");
                JTextField textField = new JTextField(10);
                panel.add(label);
                panel.add(textField);

                int result = JOptionPane.showOptionDialog(null, panel, "Remove Friend", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (result == JOptionPane.OK_OPTION) {
                    String friend = textField.getText();
                    if(curuser.getFriendsList().contains(system.getUser(friend))) {
                        curuser.removeFriend(system.getUser(friend));
                        JOptionPane.showMessageDialog(null, "Friend '" + friend + "' deleted.");
                        for (User u: curuser.getFriendsList())
                            friendsField.append("Username: " + u.getUsername() + "\tStatus: " + u.getStatus() + "\n");
                    } else {
                        System.out.println(curuser.print());
                        JOptionPane.showMessageDialog(null, friend + " is not in your friends list", "Friend not found", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });


    }
}

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
public class UserInterface extends JFrame implements ActionListener {
    private String username;
    private JTextField searchField;
    private JTextArea resultsArea;
    private ArrayList<User> users;
    private User curuser;

    public UserInterface(User user, ArrayList<User> users) {
        super("Find a friend");
        this.curuser = curuser;
        this.users = users;

        JLabel searchLabel = new JLabel("Search:");
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        resultsArea = new JTextArea(10, 30);
        resultsArea.setEditable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(resultsArea), BorderLayout.CENTER);
        setContentPane(mainPanel);

        searchButton.addActionListener(this);
        pack();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String searchText = searchField.getText();
        resultsArea.setText("");
        GameArenaSystem system = GameArenaSystem.load();

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem inviteMenuItem = new JMenuItem("Invite to Tournament Lobby");
        popupMenu.add(inviteMenuItem);

        if (system.usernameExists(searchText)) {
            resultsArea.append("Username: " + searchText + " Status: " + system.getUser(searchText).getStatus() + "\n");
            resultsArea.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    resultsArea.setToolTipText(searchText);
                }

                public void mouseExited(MouseEvent e) {
                    resultsArea.setToolTipText(null);
                }

                public void mousePressed(MouseEvent e) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        popupMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            });

            inviteMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    TournamentGUI lobby = new TournamentGUI(username, searchText);
                }
            });
        }
        else {
            JOptionPane.showMessageDialog(null, "Invalid username provided, please try again.", "User not found", JOptionPane.ERROR_MESSAGE);
        }
    }
}

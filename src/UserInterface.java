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

    private User curuser;

    public UserInterface(User user, ArrayList<User> users) {
        super("Find a friend");
        this.username = user.getUsername();

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

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem inviteMenuItem = new JMenuItem("Invite to Tournament Lobby");
        popupMenu.add(inviteMenuItem);

        try {
            File file = new File("database.txt");
            Scanner scanner = new Scanner(file);
            Boolean usernamefound = false;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String targetusername = "";
                if (line.length() >= 10) {
                    if (line.substring(0, 9).equals("Username:")) {
                        targetusername = line.substring(10);
                    }
                }
                if (searchText.equals(targetusername)) {
                    usernamefound = true;
                    if (searchText.equals(targetusername)) {
                        //String userInformation = user.print();
                        resultsArea.append(searchText + "\n");

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
                }
            }
            if (usernamefound == false) {
                JOptionPane.showMessageDialog(null, "Invalid username provided, please try again.", "User not found", JOptionPane.ERROR_MESSAGE);
            }
            scanner.close();
        } catch (FileNotFoundException f) {
            System.out.println("File not found.");
        }

    }

   // public static void main(String[] args) {
   //     UserInterface gui = new UserInterface("Bob");
   // }

}

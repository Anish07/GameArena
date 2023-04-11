import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UserAccount extends JFrame {
    private JPanel panel;
    private JLabel greeting;
    private JButton selectAGameButton;
    private JButton addAFriendButton;
    private JButton viewAccountStatisticsButton;
    private JTextArea stats;
    private JButton logOffButton;
    private String username;

    public UserAccount(String username) {
        this.username = username;
        greeting.setText("Welcome " + username + "!");

        // Code below is for reading from txt file and printing it.
        // this is used to count number of lines each user has in terms of game data
        int count = 0;

        String fileName = "database.txt"; // Update with the correct file name

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean found = false;
            while ((line = br.readLine()) != null) {

                //right now every user has 24 lines, but this number can be changed if more games are added or data information is expanded
                if (count == 24){
                    break;
                } else if (line.contains("Password") && line.contains(username)) {
                    found = true;
                    stats.append("Player Data: \n");
                } else if (found) {
                    stats.append(line + "\n");
                    count += 1;
                }
            }

            if (!found) {
                System.out.println("Player data not found for username: " + username);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //////////////////////////////////////////////////////////////////////////////

        stats.setVisible(false);

        setContentPane(panel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(size.width/2, size.height/4);

        selectAGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameForm gameForm = new GameForm();
                gameForm.setVisible(true);
            }
        });

        logOffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginMenu m = new LoginMenu();
                m.setVisible(true);
            }
        });

        viewAccountStatisticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (stats.isVisible()) {
                    stats.setVisible(false);
                    viewAccountStatisticsButton.setText("View Account Statistics");
                }
                else {
                    stats.setVisible(true);
                    viewAccountStatisticsButton.setText("Hide Account Statistics");
                }
                greeting.grabFocus();
            }
        });

        addAFriendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();
                UserInterface gui = new UserInterface(username);
            }
        });

    }

//    public static void main(String[] args) {
//        JFrame frame = new JFrame("UserAccount");
//        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
//
//        frame.setContentPane(new UserAccount("testUser").panel);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setSize(size.width/2, size.height/4);
//        frame.setVisible(true);
//    }
}

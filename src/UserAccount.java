import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class UserAccount extends JFrame {
    private JPanel panel;
    private JLabel greeting;
    private JButton selectAGameButton;
    private JButton addAFriendButton;
    private JButton viewAccountStatisticsButton;
    private JTextArea stats;
    private JButton logOffButton;
    private JScrollPane statsPane;
    private User user;

    public UserAccount(User user) {
        this.user = user;
        greeting.setText("Welcome " + user.getUsername() + "!");

        updateStats();
        statsPane.setVisible(false);

        setContentPane(panel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(1100, 600);

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
                if (statsPane.isVisible()) {
                    statsPane.setVisible(false);
                    viewAccountStatisticsButton.setText("View Account Statistics");
                } else {
                    statsPane.setVisible(true);
                    viewAccountStatisticsButton.setText("Hide Account Statistics");
                }
                greeting.grabFocus();
            }
        });

        addAFriendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserInterface(user, GameArenaSystem.load().getUsers());
            }
        });

    }

    public void updateStats() {
        // Display current user game stats
        // Should be called to update the UI after a game finishes
        HashMap<String, HashMap<String, Integer>> gameStats = user.getStats();
        stats.setText("Player Data\n\n");

        for (String game : gameStats.keySet()) {
            stats.append(game + "\n");
            stats.append("Wins: " + gameStats.get(game).get("Wins") + "\n");
            stats.append("Losses: " + gameStats.get(game).get("Losses") + "\n");
            stats.append("Ties: " + gameStats.get(game).get("Ties") + "\n\n");
        }
        // Scroll to the top
        stats.setCaretPosition(0);
    }
}

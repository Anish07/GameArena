import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class UserAccount extends JFrame {
    private JPanel panel, menuPanel;
    private JLabel greeting, title, icon;
    private JButton selectAGameButton, addAFriendButton, viewAccountStatisticsButton, logOffButton;
    private JScrollPane statsPane;
    private JTextArea stats;
    private JPanel statsPanel;
    private User user;

    public UserAccount(User user) {
        ImageIcon iconImg = new ImageIcon("images/controller_small.png");
        icon.setIcon(iconImg);

        this.user = user;
        greeting.setText("Welcome " + user.getUsername() + "!");

        updateStats();
        statsPanel.setVisible(false);

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
                if (statsPanel.isVisible()) {
                    statsPanel.setVisible(false);
                    viewAccountStatisticsButton.setText("View Account Statistics");
                    setSize(1100, 600);
                } else {
                    statsPanel.setVisible(true);
                    viewAccountStatisticsButton.setText("Hide Account Statistics");
                    setSize(1100, 800);
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
        stats.setText("");

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

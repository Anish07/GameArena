import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameForm extends JFrame {
    private JPanel panel;
    private JComboBox gameList;
    private JLabel gameLabel;
    private JButton playButton;
    private JButton backButton;

    public GameForm() {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(size.width/2, size.height/4);

        for (String game: GameArenaSystem.load().getGames()) {
            gameList.addItem(game);
        }

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameList.getSelectedItem() == "TournamentLobby") {
                    String player1 = "Kyle";
                    String player2 = "Tom";
                    TournamentGUI gui = new TournamentGUI(player1, player2);
                } else if (gameList.getSelectedItem() == "BlackJack") {
                    Tester.start(); //we open the menu by a static method.
                } else if (gameList.getSelectedItem() == "TicTacToe") {
                    TicTacToe t = new TicTacToe();
                    t.setVisible(true);
                } else {
                    dispose();
                }
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });


    }
}

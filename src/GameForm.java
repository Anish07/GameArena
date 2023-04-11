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
        setSize(size.width/2, size.height/4);

        for (String game: GameArenaSystem.load().getGames()) {
            gameList.addItem(game);
        }

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                if (gameList.getSelectedItem().equals("TournamentLobby")) {
                    String player1 = "Kyle";
                    String player2 = "Tom";
                    TournamentGUI gui = new TournamentGUI(player1, player2);
                } else if (gameList.getSelectedItem().equals("BlackJack")) {
                    System.out.println("BlackJack selected");
                    Tester.start(); //we open the menu by a static method.
                } else if (gameList.getSelectedItem().equals("TicTacToe")) {
                    TicTacToe t = new TicTacToe();
                    t.setVisible(true);
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

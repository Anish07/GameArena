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

    public GameForm(User user) {
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
                String selectedGame = (String) gameList.getSelectedItem();
                if (selectedGame.equals("TournamentLobby")) {
                    String player1 = "Kyle";
                    String player2 = "Tom";
                    TournamentGUI gui = new TournamentGUI(player1, player2);
                } else if (selectedGame.equals("BlackJack")) {
                    user.playGame("Black Jack");
                    Tester.start(user); //we open the menu by a static method.
                } else if (selectedGame.equals("TicTacToe")) {
                    user.playGame("Tic Tac Toe");
                    TicTacToe t = new TicTacToe();
                    t.setVisible(true);
                } else {
                    System.out.println(selectedGame);
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

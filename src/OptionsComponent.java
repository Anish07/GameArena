import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class OptionsComponent extends JComponent implements ActionListener{

  private JButton btnPlay = new JButton("PLAY");
  private JButton btnExit = new JButton("EXIT");
  private JButton btnHelp = new JButton("HELP");
  private JButton btnInfo = new JButton("INFO");
  private static BufferedImage backgroundImage;

  public OptionsComponent(User user) {
    btnPlay.addActionListener(this);
    btnExit.addActionListener(this);
    btnHelp.addActionListener(this);
    btnInfo.addActionListener(this);
  }

  public void paintComponent(Graphics g) {

    Graphics2D g2 = (Graphics2D) g;

    try {
      backgroundImage = ImageIO.read(new File("images/background-2.jpeg"));
    }
    catch(IOException e) {}

    g2.drawImage(backgroundImage, 0, 0, null);

    g2.setFont(new Font("Palatino", Font.BOLD, 100));
    g2.setColor(Color.WHITE);
    g2.drawString("Welcome", 380, 100);
    g2.drawString("to", 530, 180);
    g2.drawString("BLACKJACK!", 290, 260);


    btnPlay.setBounds(500, 300, 150, 80);
    btnExit.setBounds(500, 400, 150, 80);
    btnHelp.setBounds(80, 75, 150, 80);
    btnInfo.setBounds(900, 75, 150, 80);

    btnPlay.setFont(new Font("Palatino", Font.BOLD, 40));
    btnExit.setFont(new Font("Palatino", Font.BOLD, 40));
    btnHelp.setFont(new Font("Palatino", Font.BOLD, 40));
    btnInfo.setFont(new Font("Palatino", Font.BOLD, 40));

    super.add(btnPlay);
    super.add(btnExit);
    super.add(btnHelp);
    super.add(btnInfo);
  }

  public void actionPerformed(ActionEvent e) {
    JButton selectedButton = (JButton)e.getSource();

    if(selectedButton == btnExit) {
      Tester.menuFrame.dispose();

    }
    else if(selectedButton == btnPlay) {
      Tester.currentState = Tester.STATE.GAME;
      Tester.menuFrame.dispose();
      Tester.gameRefreshThread.start();
      Tester.gameCheckThread.start();
    }
    else if(selectedButton == btnHelp) {
      JOptionPane.showMessageDialog(this, "The goal of blackjack is to beat the dealer's hand without going over 21." +
                      "\nFace cards are worth 10. Aces are worth 1 or 11, whichever makes a better hand." +
                      "\nEach player starts with two cards, one of the dealer's cards is hidden until the end." +
                      "\nClick on 'Hit' to ask for another card. Click on 'Stand' to hold your hand and end your turn." +
                      "\nIf you go over 21 you lose, and the dealer wins regardless of the dealer's hand." +
                      "\nIf you are dealt 21 (Ace & 10), you got a blackjack!", "How to play BlackJack",
              JOptionPane.INFORMATION_MESSAGE);
    }
    else if(selectedButton == btnInfo) {
      JOptionPane.showMessageDialog(this, "GameArena Copyright 2023 for CPS406 Software Engineering Project", "About", JOptionPane.INFORMATION_MESSAGE);
    }
  }
}
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Font;

public class Game {

  ArrayList<Card> dealerHand;
  ArrayList<Card> playerHand;

  public boolean faceDown;
  public boolean dealerWon;
  public volatile boolean roundOver;


  JFrame frame;
  Deck deck;
  GameComponent atmosphereComponent;
  GameComponent cardComponent;

  JButton btnHit;
  JButton btnStand;
  JButton btnDouble;
  JButton btnExit;

  public Game(JFrame f) {
    deck = new Deck();
    deck.shuffleDeck();
    dealerHand = new ArrayList<Card>();
    playerHand = new ArrayList<Card>();
    atmosphereComponent = new GameComponent(dealerHand, playerHand);
    frame = f;
    faceDown = true;
    dealerWon = true;
    roundOver = false;
  }

  public void formGame() {

    System.out.println("GAME STARTED");
    frame.setTitle("BLACKJACK!");
    frame.setSize(1130, 665);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    frame.setResizable(false);

    btnHit = new JButton("HIT");
    btnHit.setBounds(390, 550, 100, 50);
    btnHit.setFont(new Font("Palatino", Font.BOLD, 16));
    btnStand = new JButton("STAND");
    btnStand.setBounds(520, 550, 100, 50);
    btnStand.setFont(new Font("Palatino", Font.BOLD, 16));
    btnDouble = new JButton("DOUBLE");
    btnDouble.setBounds(650, 550, 100, 50);
    btnDouble.setFont(new Font("Palatino", Font.BOLD, 16));
    btnExit = new JButton("EXIT CASINO");
    btnExit.setBounds(930, 240, 190, 50);
    btnExit.setFont(new Font("Palatino", Font.BOLD, 16));

    frame.add(btnHit);
    frame.add(btnStand);
    frame.add(btnDouble);
    frame.add(btnExit);

    btnExit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(frame,  "You have left the casino with " +  Tester.currentBalance + ".");
        Tester.currentState = Tester.STATE.MENU;
        frame.dispose();

      }
    });

    atmosphereComponent = new GameComponent(dealerHand, playerHand);
    atmosphereComponent.setBounds(0, 0, 1130, 665);
    frame.add(atmosphereComponent);
    frame.setVisible(true);
  }

  public void startGame() {

    for(int i = 0; i<2; i++) {
      dealerHand.add(deck.getCard(i));
    }
    for(int i = 2; i<4; i++) {
      playerHand.add(deck.getCard(i));
    }
    for (int i = 0; i < 4; i++) {
      deck.removeCard(0);
    }

    cardComponent = new GameComponent(dealerHand, playerHand);
    cardComponent.setBounds(0, 0, 1130, 665);
    frame.add(cardComponent);
    frame.setVisible(true);

    checkHand(dealerHand);
    checkHand(playerHand);

    btnHit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addCard(playerHand);
        checkHand(playerHand);
        if (getSumOfHand(playerHand)<17 && getSumOfHand(dealerHand)<17){
          addCard(dealerHand);
          checkHand(dealerHand);
        }
      }
    });

    btnDouble.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addCard(playerHand);
        addCard(playerHand);
        checkHand(playerHand);
        if (getSumOfHand(playerHand)<17 && getSumOfHand(dealerHand)<17){
          addCard(dealerHand);
          checkHand(dealerHand);
        }
      }
    });

    btnStand.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        while (getSumOfHand(dealerHand)<17) {
          addCard(dealerHand);
          checkHand(dealerHand);
        }
        if ((getSumOfHand(dealerHand)<21) && getSumOfHand(playerHand)<21) {
          if(getSumOfHand(playerHand) > getSumOfHand(dealerHand)) {
            faceDown = false;
            dealerWon = false;
            JOptionPane.showMessageDialog(frame, "You Win!");
            rest();
            roundOver = true;
          }
          else {
            faceDown = false;
            JOptionPane.showMessageDialog(frame, "Dealer Wins!");
            rest();
            roundOver = true;
          }
        }
      }
    });
  }

  public void checkHand (ArrayList<Card> hand) {
    if (hand.equals(playerHand)) {
      if(getSumOfHand(hand) == 21){
        faceDown = false;
        dealerWon = false;
        JOptionPane.showMessageDialog(frame, "You Win!");
        rest();
        roundOver = true;
      }
      else if (getSumOfHand(hand) > 21) {
        faceDown = false; JOptionPane.showMessageDialog(frame, "Dealer Wins!");
        rest();
        roundOver = true;
      }
    }
    else {
      if(getSumOfHand(hand) == 21) {
        faceDown = false;
        JOptionPane.showMessageDialog(frame, "Dealer Wins!");
        rest();
        roundOver = true;
      }
      else if (getSumOfHand(hand) > 21) {
        faceDown = false;
        dealerWon = false;
        JOptionPane.showMessageDialog(frame, "You Win!");
        rest();
        roundOver = true;
      }
    }
  }

  public void addCard(ArrayList<Card> hand) {
    hand.add(deck.getCard(0));
    deck.removeCard(0);
    faceDown = true;
  }

  public boolean hasAceInHand(ArrayList<Card> hand) {
    for (int i = 0; i < hand.size(); i++){
      if(hand.get(i).getValue() == 11) {
        return true;
      }
    }
    return false;
  }

  public int aceCountInHand(ArrayList<Card> hand){
    int aceCount = 0;
    for (int i = 0; i < hand.size(); i++) {
      if(hand.get(i).getValue() == 11) {
        aceCount++;
      }
    }
    return aceCount;
  }

  public int getSumWithHighAce(ArrayList<Card> hand) {
    int handSum = 0;
    for (int i = 0; i < hand.size(); i++){
      handSum = handSum + hand.get(i).getValue();
    }
    return handSum;
  }

  public int getSumOfHand (ArrayList<Card> hand) {
    if(hasAceInHand(hand)) {
      if(getSumWithHighAce(hand) <= 21) {
        return getSumWithHighAce(hand);
      }
      else{
        for (int i = 0; i < aceCountInHand(hand); i++) {
          int sumOfHand = getSumWithHighAce(hand)-(i+1)*10;
          if(sumOfHand <= 21) {
            return sumOfHand;
          }
        }
      }
    }
    else {
      int sumOfHand = 0;
      for (int i = 0; i < hand.size(); i++) {
        sumOfHand = sumOfHand + hand.get(i).getValue();
      }
      return sumOfHand;
    }
    return 22;
  }

  public static void rest() {
    try {
      Thread.sleep(500);
    }
    catch (InterruptedException e) {}
  }
}
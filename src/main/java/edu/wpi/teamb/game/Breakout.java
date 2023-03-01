package edu.wpi.teamb.game;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Breakout extends JFrame {

  public Breakout() {

      initializeUI();
  }

  private void initializeUI() {

    add(new Board());
    setTitle("Badger Breaker");

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setResizable(false);
    pack();
  }

  public static void startGame(){

    EventQueue.invokeLater(
        () -> {
          var game = new Breakout();
          game.setLocationRelativeTo(null);
          game.setVisible(true);
        });
  }
}

package edu.wpi.teamb.game;

import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Paddle extends Sprite {

  private int dx;

  public Paddle() {

    initPaddle();
  }

  private void initPaddle() {

    loadImageRight();
    getImageDimensions();

    resetState();
  }

  private void loadImageRight() {

    var ii = new ImageIcon("src/main/resources/media/paddleRight.png");
    image = ii.getImage();
  }

  private void loadImageLeft() {

    var ii = new ImageIcon("src/main/resources/media/paddleLeft.png");
    image = ii.getImage();
  }

  void move() {

    x += dx;

    if (x <= 0) {

      x = 0;
    }

    if (x >= Commons.WIDTH - imageWidth) {

      x = Commons.WIDTH - imageWidth;
    }
  }

  void keyPressed(KeyEvent e) {

    int key = e.getKeyCode();

    if (key == KeyEvent.VK_LEFT) {
      loadImageLeft();
      dx = -3;
    }

    if (key == KeyEvent.VK_RIGHT) {
      loadImageRight();
      dx = 3;
    }
  }

  void keyReleased(KeyEvent e) {

    int key = e.getKeyCode();

    if (key == KeyEvent.VK_LEFT) {

      dx = 0;
    }

    if (key == KeyEvent.VK_RIGHT) {

      dx = 0;
    }
  }

  private void resetState() {

    x = Commons.INIT_PADDLE_X;
    y = Commons.INIT_PADDLE_Y;
  }
}

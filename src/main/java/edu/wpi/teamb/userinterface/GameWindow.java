package edu.wpi.teamb.userinterface;
import javax.swing.JFrame;

public class GameWindow extends JFrame{
    public static final int SCREEN_WIDTH = 600;

    private GameScreen gameScreen;

    public GameWindow(){

        super("Google Chrome offline game");
        setSize(SCREEN_WIDTH, 175);

        setLocation(400, 200);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(false);

        gameScreen = new GameScreen();

        addKeyListener(gameScreen);

        add(gameScreen);
    }

    public void startgame(){
        setVisible(true);
        gameScreen.startGame();
    }

    public static void main(String args[]){
        (new GameWindow()).startgame();
    }
}

package SnakeGame;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class SnakePlayer extends JFrame {

    public SnakePlayer() {

        initUI();
    }

    private void initUI() {

        add(new Game());
        StartPanel startPanel = new StartPanel(this);
        add(startPanel);


        setResizable(false);
        pack();

        setTitle("Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    public void startGame() {
        getContentPane().removeAll();
        Game game = new Game();
        add(game);
        game.setFocusable(true);
        game.requestFocusInWindow();
        pack();
        setVisible(true);
        MusicPlayer.startBackgroundMusic();

    }


    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            JFrame ex = new SnakePlayer();
            ex.setVisible(true);
        });
    }

}
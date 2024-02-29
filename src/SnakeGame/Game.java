package SnakeGame;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;


public class Game extends JPanel implements ActionListener {

    private final int GameWidth = 700;
    private final int GameHeight = 700;
    private final int BodySize = 15;
    private final int Apples = 250;
    private final int PoisonousApples = 1;

    private final int RAND_POS = 29;
    private final int DELAY = 140;
    private final int xThird[] = new int[PoisonousApples];
    private final int yThird[] = new int[PoisonousApples];
    private int PoisonousApples_x;
    private int PoisonousApples_y;
    private final int x[] = new int[Apples];
    private final int y[] = new int[Apples];
    private int dots;
    private int apple_x;
    private int apple_y;
    private int specialApple_x;
    private int specialApple_y;

    private boolean inGame = true;
    private boolean specialAppleVisible = false;
    private boolean PoisonousApplesVisible = false;

    private Timer timer;
    private Timer specialAppleTimer;
    private Timer PoisonousApplesTimer;

    private Image youLoseImage;
    private Image youWinImage;
    private Image ball;
    private Image apple;
    private Image thirdApple;
    private Image head;
    private Image specialApple;
    private Image backgroundImage;
    private int finalScore;
    private boolean youWin = true;

    private Features features;
    private JButton resetButton;
    private Controller controller;


    public Game() {
        initBoard();
        features = new Features();
        createResetButton();
        controller = new Controller();
        setupSpecialAppleTimer();
        setupPoisonousApplesTimer();

    }

    private void initBoard() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                controller.keyPressed(e);
            }
        });
        setBackground(Color.black);
        setFocusable(true);
        setPreferredSize(new Dimension(GameWidth, GameHeight));
        loadImages();
        initGame();
    }

    private void loadImages() {
        ImageIcon body = new ImageIcon("src/SnakeGame/resources/img/body.png");
        ball = body.getImage();

        ImageIcon Apple = new ImageIcon("src/SnakeGame/resources/img/apple.png");
        apple = Apple.getImage();
        ImageIcon thirdAppleIcon = new ImageIcon("src/SnakeGame/resources/img/apple2.png");
        thirdApple = thirdAppleIcon.getImage();

        ImageIcon headSnake = new ImageIcon("src/SnakeGame/resources/img/head.png");
        head = headSnake.getImage();

        ImageIcon specialAppleIcon = new ImageIcon("src/SnakeGame/resources/img/apple3.png");
        specialApple = specialAppleIcon.getImage();

        ImageIcon youLoseIcon = new ImageIcon("src/SnakeGame/resources/img/you_lose.png");
        youLoseImage = youLoseIcon.getImage();

        ImageIcon youWinIcon = new ImageIcon("src/SnakeGame/resources/img/you_win.png");
        youWinImage = youWinIcon.getImage();

        ImageIcon backgroundIcon = new ImageIcon("src/SnakeGame/resources/img/back.png");
        backgroundImage = backgroundIcon.getImage();
    }

    private void initGame() {
        dots = 3;
        for (int z = 0; z < dots; z++) {
            x[z] = 75 - z * BodySize;
            y[z] = 75;
        }
        locateApple();
        locateThirdApple();
        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void setupSpecialAppleTimer() {
        specialAppleTimer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!specialAppleVisible) {
                    locateSpecialApple();
                    specialAppleVisible = true;
                } else {
                    specialAppleVisible = false;
                }
            }
        });
        specialAppleTimer.start();
    }
    private void setupPoisonousApplesTimer() {
        PoisonousApplesTimer = new Timer(7000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!PoisonousApplesVisible) {
                    locateSpecialApple();
                    PoisonousApplesVisible = true;
                } else {
                    PoisonousApplesVisible = false;
                }
            }
        });
        PoisonousApplesTimer.start();
    }

    private void locateApple() {
        int r = (int) (Math.random() * RAND_POS);
        apple_x = ((r * BodySize));

        r = (int) (Math.random() * RAND_POS);
        apple_y = ((r * BodySize));
    }
    private void locateThirdApple() {
        int r = (int) (Math.random() * RAND_POS);
        PoisonousApples_x = ((r * BodySize));

        r = (int) (Math.random() * RAND_POS);
        PoisonousApples_y = ((r * BodySize));
    }


    private void locateSpecialApple() {
        int r = (int) (Math.random() * RAND_POS);
        specialApple_x = ((r * BodySize));

        r = (int) (Math.random() * RAND_POS);
        specialApple_y = ((r * BodySize));
    }



    private void drawScore(Graphics g) {
        g.setColor(Color.white);
        g.drawString("Score: " + features.getScore(), 10, 20);
    }

    private void drawTime(Graphics g) {
        g.setColor(Color.white);
        g.drawString("Time: " + features.getTimeElapsed() + "s", 10, 40);
    }

    private void doDrawing(Graphics g) {
        if (inGame) {
            if (specialAppleVisible)
                g.drawImage(specialApple, specialApple_x, specialApple_y, this);

            g.drawImage(apple, apple_x, apple_y, this);
            if (PoisonousApplesVisible)
                g.drawImage(thirdApple, PoisonousApples_x, PoisonousApples_y, this);
            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                } else {
                    g.drawImage(ball, x[z], y[z], this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        } else {
            gameFinished(g);
        }
    }

    private void gameFinished(Graphics g) {
        int x1 = (GameWidth - youLoseImage.getWidth(this)) / 2;
        int y1 = (GameHeight - youLoseImage.getHeight(this)) / 2;
        int x2 = (GameWidth - youWinImage.getWidth(this)) / 2;
        int y2 = (GameHeight - youWinImage.getHeight(this)) / 2;

        if (youWin){
            g.drawImage(youWinImage, x2, y2, this);
            MusicPlayer.playYouWinSound();
            MusicPlayer.stopBackgroundMusic();
            resetButton.setBounds(GameWidth / 2 - 90, y1 + youLoseImage.getHeight(this) + 40, 190, 50);
            add(resetButton);

        } else{
            MusicPlayer.playGameOverSound();
            g.drawImage(youLoseImage, x1, y1, this);
            Font font = new Font("Arial", Font.BOLD, 20);
            g.setFont(font);
            FontMetrics fm = g.getFontMetrics(font);
            int textWidth = fm.stringWidth("Final Score: " + features.getScore());
            g.setColor(Color.white);
            g.drawString("Final Score: " + features.getScore(), (GameWidth - textWidth) / 2, y1 + youLoseImage.getHeight(this) + 20);
            resetButton.setBounds(GameWidth / 2 - 90, y1 + youLoseImage.getHeight(this) + 40, 190, 50);
            add(resetButton);
            MusicPlayer.stopBackgroundMusic();
        }
    }
    private void checkApple() {
        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            dots++;
            locateApple();
            MusicPlayer.appleEatMusic();
            features.increaseScore();
        }
    }

    private void checkSpecialApple() {
        if ((x[0] == specialApple_x) && (y[0] == specialApple_y)) {
            locateSpecialApple();
            MusicPlayer.apple2EatMusic();
            features.decreaseScore();
            features.increaseSpecialApplesEaten();
        }
    }
    private void checkThirdApple() {
        if ((x[0] == PoisonousApples_x) && (y[0] == PoisonousApples_y)) {
            locateThirdApple();
            MusicPlayer.apple3EatMusic();
            features.decreaseScore(3);
            dots--;
        }
    }


    private void move() {
        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }
        if (controller.isLeftDirection()) {
            x[0] -= BodySize;
        }
        if (controller.isRightDirection()) {
            x[0] += BodySize;
        }
        if (controller.isUpDirection()) {
            y[0] -= BodySize;
        }
        if (controller.isDownDirection()) {
            y[0] += BodySize;
        }
    }

    private void checkCollision() {
        for (int z = dots; z > 0; z--) {
            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
                youWin = false;
            }
        }

        if (y[0] >= GameHeight || y[0] < 0 || x[0] >= GameWidth || x[0] < 0) {
            inGame = false;
            youWin = false;
        }

        if ((x[0] == specialApple_x) && (y[0] == specialApple_y)) {
            inGame = false;
            youWin = false;
        }

        if (!inGame) {
            timer.stop();
            specialAppleTimer.stop();
            PoisonousApplesTimer.stop();
        }
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, GameWidth, GameHeight, this);
        doDrawing(g);
        drawScore(g);
        drawTime(g);

    }
    private void createResetButton() {
        resetButton = new JButton("Reset Game");

        resetButton.setUI(new GradientButtonUI(new Color(239, 71, 101), new Color(255, 154, 90)));
        resetButton.setFont(new Font("Arial", Font.BOLD, 18));
        resetButton.setForeground(Color.WHITE);



        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
    }


    private void resetGame() {
        dots = 3;
        features = new Features();
        initGame();
        inGame = true;
        youWin = true;
        controller.setInitialState();
        timer.start();
        specialAppleTimer.start();
        finalScore = features.getScore();
        remove(resetButton);
        MusicPlayer.startBackgroundMusic();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkSpecialApple();
            checkThirdApple();
            checkCollision();
            move();
        }
        repaint();
    }
    private static class GradientButtonUI extends BasicButtonUI {
        private final Color startColor;
        private final Color endColor;

        GradientButtonUI(Color startColor, Color endColor) {
            this.startColor = startColor;
            this.endColor = endColor;
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            Graphics2D g2d = (Graphics2D) g;
            int w = c.getWidth();
            int h = c.getHeight();
            GradientPaint gradientPaint = new GradientPaint(0, 0, startColor, 0, h, endColor);
            g2d.setPaint(gradientPaint);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.fill(new RoundRectangle2D.Double(0, 0, w, h, 0, 0));
            super.paint(g, c);
        }
    }
}

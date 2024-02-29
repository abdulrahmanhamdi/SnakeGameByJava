package SnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartPanel extends JPanel {

    private final SnakePlayer snake;
    public StartPanel(SnakePlayer snake) {
        this.snake = snake;

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        addBackgroundPanel();
        setPreferredSize(new Dimension(512, 512));
    }

    private void addBackgroundPanel() {
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("src/SnakeGame/resources/img/snake.png");
                Image img = backgroundImage.getImage();

                // Calculate the scaled dimensions
                int width = getWidth();
                int height = getHeight();
                int imgWidth = img.getWidth(this);
                int imgHeight = img.getHeight(this);
                int newWidth, newHeight;

                // Scale the image while maintaining its aspect ratio
                if (width > height) {
                    newWidth = width;
                    newHeight = (int) (imgHeight * ((double) width / imgWidth));
                } else {
                    newWidth = (int) (imgWidth * ((double) height / imgHeight));
                    newHeight = height;
                }

                g.drawImage(img, (width - newWidth) / 2, (height - newHeight) / 2, newWidth, newHeight, this);
            }
        };
        backgroundPanel.setLayout(null);

        addStartGameButton(backgroundPanel);
        addGameDescriptionButton(backgroundPanel); // Add game description button

        add(backgroundPanel, BorderLayout.CENTER);
    }

    /*GradientButton descriptionButton = new GradientButton("Game Description",
                    new Color(252, 137, 77),
                    new Color(243, 64, 121));*/
    private void addGameDescriptionButton(JPanel panel) {

        Color normalColor = new Color(255, 255, 255);
        Color hoverColor = new Color(255, 194, 41);
        Color pressedColor = new Color(255, 147, 20);
        StyledButton descriptionButton = new StyledButton("About", normalColor, hoverColor, pressedColor);



        descriptionButton.setForeground(Color.BLACK);
        descriptionButton.setFont(new Font("Arial", Font.BOLD, 12));


        descriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGameDescriptionDialog();
            }
        });
        descriptionButton.setBounds(20, 20, 130, 25);
        panel.add(descriptionButton);
    }
    private void showGameDescriptionDialog() {
        String description = "<html>"
                + "<h1 style='color: #007bff;'>Game Description</h1>"
                + "<p style='font-size: 12px; color: #333; font-family: Garamond, serif;'>"
                + "In Snake Game, players control a snake that grows in length as it eats apples while avoiding collision with its own body and the walls."
                + "<br>"
                + "The objective is to survive as long as possible and achieve the highest score by consuming apples. Additionally, special apples may appear, offering unique challenges or rewards."
                + "<br>"
                + "Navigate the snake using arrow keys or on-screen controls. Enjoy this classic game and test your reflexes!"
                + "</p>"
                + "</html>";
        JOptionPane.showMessageDialog(this, description, "Game Description", JOptionPane.INFORMATION_MESSAGE);
    }




    private StyledButton createStartGameButton() {
        Color normalColor = new Color(255, 255, 255);
        Color hoverColor = new Color(255, 194, 41);
        Color pressedColor = new Color(255, 147, 20);
        StyledButton startGameButton = new StyledButton("Start Game", normalColor, hoverColor, pressedColor);
        startGameButton.setFont(new Font("Arial", Font.BOLD, 12));

        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                snake.startGame();
            }
        });

        return startGameButton;
    }

    private void addStartGameButton(JPanel panel) {
        int startGameButtonX = 20;
        int startGameButtonY = 60;
        int startGameButtonWidth = 130;
        int startGameButtonHeight = 25;

        JButton startGameButton = createStartGameButton();
        startGameButton.setBounds(startGameButtonX, startGameButtonY, startGameButtonWidth, startGameButtonHeight);

        panel.add(startGameButton);
    }
}
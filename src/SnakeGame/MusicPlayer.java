package SnakeGame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class MusicPlayer {
    private static final String BACKGROUND_MUSIC = "src/SnakeGame/resources/Sounds/background.wav";
    private static final String APPLE_MUSIC = "src/SnakeGame/resources/Sounds/apple.wav";
    private Clip clip;
    private static Clip backgroundMusicClip;


    public static void startBackgroundMusic() {
        try {
            File audioFile = new File(BACKGROUND_MUSIC);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(audioStream);

            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopBackgroundMusic() {
        if (backgroundMusicClip != null && backgroundMusicClip.isRunning()) {
            backgroundMusicClip.stop();
        }
    }

    public MusicPlayer(String filePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
        }
    }

    // Method to play game over sound
    public static void playGameOverSound() {
        playSound("src/SnakeGame/resources/Sounds/gameOver.wav");
    }
    public static void playYouWinSound() {
        playSound("src/SnakeGame/resources/Sounds/youWin.wav");
    }
    public static void appleEatMusic() {
        playSound(APPLE_MUSIC);
    }
    public static void apple2EatMusic() {
        playSound("src/SnakeGame/resources/Sounds/apple2.wav");
    }

    public static void apple3EatMusic() {
        playSound("src/SnakeGame/resources/Sounds/apple3.wav");
    }




    // Helper method to play a sound from a file
    private static void playSound(String filePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
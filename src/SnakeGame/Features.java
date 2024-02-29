package SnakeGame;

public class Features {
    private int score;
    private int specialApplesEaten; // New field to track the number of special apples eaten
    private long startTime;
    private int applesEaten;


    public Features() {
        score = 0;
        specialApplesEaten = 0; // Initialize special apples eaten
        startTime = System.currentTimeMillis();
    }

    public int getScore() {
        return score;
    }

    public void increaseScore() {
        score += 5;
    }

    public void decreaseScore() {
        score -= 1; // Subtract 1 from the score
    }
    public void decreaseScore(int x) {
        score-=x;
    }

    public long getTimeElapsed() {
        return (System.currentTimeMillis() - startTime) / 1000; // Time elapsed in seconds
    }

    public int getSpecialApplesEaten() {
        return specialApplesEaten;
    }
    public int getApplesEaten() {
        return applesEaten;
    }
    public void increaseApplesEaten() {
        applesEaten++;
    }

    public void increaseSpecialApplesEaten() {
        specialApplesEaten++;
    }

}
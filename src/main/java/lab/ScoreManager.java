package lab;

public class ScoreManager {
    private int score;

    public ScoreManager() {
        this.score = 0;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore(int points) {
        score += points;
    }

    public void resetScore() {
        score = 0;
    }
}

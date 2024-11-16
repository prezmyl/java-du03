package lab;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ScoreManager {
    private int score;
    private GraphicsContext gc;

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
    public void update() {
        if (gc != null) {
            gc.setFill(Color.BLACK);
            gc.fillText("Score: " + score, 10, 40); // Zobrazení skóre
        }
    }
}

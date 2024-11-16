package lab;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class HealthDisplay implements DrawAble {
    private final Player.Health health;

    public HealthDisplay(Player.Health health) {
        this.health = health;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillText("Lives: " + health.getLives(), 10, 20);
    }
}

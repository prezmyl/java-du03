package lab;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Enemy extends GameObject implements DrawableSimulable{
    private static final double ENEMY_WIDTH = 30;
    private static final double ENEMY_HEIGHT = 20;

    public Enemy(double x, double y) {
        super(x, y);
        this.speedY = 0.3;
    }

    @Override
    public void simulate() {
        if (position.getY() + speedY + ENEMY_HEIGHT < Constant.GAME_HEIGHT) {
            position = new Point2D(position.getX(), position.getY() + speedY);
        }
        else {
            this.speedY = 0;
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillRect(position.getX(), position.getY(), ENEMY_WIDTH, ENEMY_HEIGHT);
    }


    public double getWidth() {
        return ENEMY_WIDTH;
    }

    public double getHeight() {
        return ENEMY_HEIGHT;
    }
}

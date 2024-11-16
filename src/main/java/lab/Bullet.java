package lab;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet extends GameObject implements DrawableSimulable {
    protected static final double BULLET_WIDTH = 5;
    protected static final double BULLET_HEIGHT = 10;

    public Bullet(double x, double y) {
        super(x, y);
        this.speedY = 3;
    }

    @Override
    public void simulate() {
        position = new Point2D(position.getX(), position.getY() - speedY);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(position.getX(), position.getY(), BULLET_WIDTH, BULLET_HEIGHT);
    }

    public double getWidth() {
        return BULLET_WIDTH;
    }

    public double getHeight() {
        return BULLET_HEIGHT;
    }
}

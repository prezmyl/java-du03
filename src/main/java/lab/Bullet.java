package lab;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet extends GameObject implements DrawableSimulable {
    protected static final double WIDTH = 5;
    protected static final double HEIGHT = 10;

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
        gc.fillRect(position.getX(), position.getY(), WIDTH, HEIGHT);
    }

    public double getWidth() {
        return WIDTH;
    }

    public double getHeight() {
        return HEIGHT;
    }
}

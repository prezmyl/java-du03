package lab;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Enemy extends GameObject implements DrawableSimulable{
    private static final double WIDTH = 30;
    private static final double HEIGHT = 20;

    public Enemy(double x, double y) {
        super(x, y);
        this.speedY = 0.3;
    }

    @Override
    public void simulate() {
        if (position.getY() + speedY + HEIGHT < 400) {
            position = new Point2D(position.getX(), position.getY() + speedY);
        }
        else {
            this.speedY = 0;
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillRect(position.getX(), position.getY(), WIDTH, HEIGHT);
    }


    public double getWidth() {
        return WIDTH;
    }

    public double getHeight() {
        return HEIGHT;
    }
}

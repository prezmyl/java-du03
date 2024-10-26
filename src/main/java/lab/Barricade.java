package lab;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Barricade extends StaticObject implements DrawAble{

    private static final double WIDTH = 50;
    private static final double HEIGHT = 15;

    public Barricade(double x, double y) {
        super(x, y);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.GRAY);
        gc.fillRect(position.getX(), position.getY(), WIDTH, HEIGHT);
    }

    public double getWidth() {
        return WIDTH;
    }

    public double getHeight() {
        return HEIGHT;
    }
}

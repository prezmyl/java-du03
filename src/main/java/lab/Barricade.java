package lab;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Barricade extends GameObject implements DrawAble{

    private static final double BARRICADE_WIDTH = 50;
    private static final double BARRICADE_HEIGHT = 15;

    public Barricade(double x, double y) {
        super(x, y);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.GRAY);
        gc.fillRect(position.getX(), position.getY(), BARRICADE_WIDTH, BARRICADE_HEIGHT);
    }

    public double getWidth() {
        return BARRICADE_WIDTH;
    }

    public double getHeight() {
        return BARRICADE_HEIGHT;
    }
}

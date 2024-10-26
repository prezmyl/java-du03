package lab;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public abstract class StaticObject {

    protected Point2D position;

    public StaticObject(double x, double y) {
        this.position = new Point2D(x, y);
    }

     public abstract void draw(GraphicsContext gc);

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }
}

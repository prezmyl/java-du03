package lab;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Barricade extends GameObject implements DrawAble, Collisionable{

    private static final double BARRICADE_WIDTH = 50;
    private static final double BARRICADE_HEIGHT = 15;
    private boolean active = true;

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

    @Override
    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(position.getX(), position.getY(), BARRICADE_WIDTH, BARRICADE_HEIGHT);
    }

    @Override
    public boolean intersect(Rectangle2D another) {
        return getBoundingBox().intersects(another);
    }

    @Override
    public void hitBy(Collisionable another) {
        if (another instanceof Bullet) {
            System.out.println("Barricade hit by bullet.");
            setActive(false); // Deaktivace bariéry při zásahu střelou
        }
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }


}

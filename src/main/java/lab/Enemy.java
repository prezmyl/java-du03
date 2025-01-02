package lab;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Enemy extends GameObject implements DrawableSimulable, Collisionable{
    private static final double ENEMY_WIDTH = 30;
    private static final double ENEMY_HEIGHT = 20;
    private final double initialX;
    private final double initialY;

    private boolean active = true;

    public Enemy(double x, double y) {
        super(x, y);
        this.initialX = x;
        this.initialY = y;
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

        if (position.getY() >= Constant.GAME_HEIGHT - 50) {
            position = new Point2D(initialX, initialY);
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillRect(position.getX(), position.getY(), ENEMY_WIDTH, ENEMY_HEIGHT);
    }

    @Override
    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(position.getX(), position.getY(), ENEMY_WIDTH, ENEMY_HEIGHT);
    }

    @Override
    public boolean intersect(Rectangle2D another) {
        return getBoundingBox().intersects(another);
    }

    @Override
    public void hitBy(Collisionable another) {
        if (another instanceof Bullet) {
            System.out.println("Enemy hit by bullet.");
            setActive(false);
        }
        if (another instanceof Player) {
            System.out.println("Enemy hit by player.");
            setActive(false);

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

    public double getWidth() {
        return ENEMY_WIDTH;
    }

    public double getHeight() {
        return ENEMY_HEIGHT;
    }
}

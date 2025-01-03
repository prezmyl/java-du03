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
    private final GameSession gameSession;

    private boolean active = true;

    public Enemy(double x, double y, GameSession gameSession) {
        super(x, y);
        this.initialX = x;
        this.initialY = y;
        this.speedY = 100;
        this.gameSession = gameSession;
    }

    @Override
    public void simulate(double deltaT) {
        System.out.println("Simulating enemy at Y: " + position.getY());
        position = new Point2D(position.getX(), position.getY() + speedY * deltaT);

        if (position.getY() >= Constant.GAME_HEIGHT - 50) {
            System.out.println("Reset enemy to top");
            position = new Point2D(initialX, 0); // Reset Y nahoru
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillRect(position.getX(), position.getY(), ENEMY_WIDTH, ENEMY_HEIGHT);
    }

    private void shoot() {
        Bullet bullet = new Bullet(position.getX() + getWidth() / 2, position.getY() + getHeight(), Bullet.Type.ENEMY);
        gameSession.addBullet(bullet);
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
        if (another instanceof Bullet bullet) {


            if (bullet.getType() == Bullet.Type.PLAYER){
                System.out.println("Enemy hit by player bullet.");
                gameSession.getScoreManager().increaseScore(100);
                setActive(false);
            }
        }
        if (another instanceof Player) {
            System.out.println("Enemy hit by player ship.");
            gameSession.getScoreManager().increaseScore(50);
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

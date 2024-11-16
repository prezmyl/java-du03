package lab;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Player extends GameObject implements DrawableSimulable {

    private static final double PLAYER_WIDTH = 40;
    private static final double PLAYER_HEIGHT = 20;
    private Health health;

    public Player(double x, double y) {
        super(x, y);
        this.speedX = 5;
        this.health = new Health(Constant.MAX_HEALTH);
    }

    @Override
    public void simulate(){
       /* if (position.getX() + speedX < Constant.GAME_WIDTH - PLAYER_WIDTH && position.getX() + speedX > 0) {
            position = new Point2D(position.getX() + speedX, position.getY());
        }
        else{
            this.speedX = -1 * speedX;
        }*/

    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.fillRect(position.getX(), position.getY(), PLAYER_WIDTH, PLAYER_HEIGHT);

    }

    public void moveLeft(){
        position = new Point2D(position.getX() - speedX, position.getY());
    }


    public void moveRight(){
        position = new Point2D(position.getX() + speedX, position.getY());
    }

    /*public void shoot(){
        Bullet bullet = new Bullet(position.getX() + getWidth() / 2, position.getY() - Bullet.BULLET_HEIGHT);
    }*/
    public void shoot(DrawingThread drawingThread) {
        Bullet bullet = new Bullet(
                position.getX() + getWidth() / 2 - Bullet.BULLET_WIDTH / 2,
                position.getY() - Bullet.BULLET_HEIGHT
        );
        drawingThread.addBullet(bullet); // Předání střely do DrawingThread
    }
    public Health getHealth(){
        return health;
    }

    public double getWidth() {
        return PLAYER_WIDTH;
    }

    public double getHeight() {
        return PLAYER_HEIGHT;
    }

    //Vnorena trida (zavislost Health na vnejsi tride Player)
    public class Health{
        private int lives;

        public Health(int lives){
            this.lives = lives;
        }

        public int getLives(){
            return lives;
        }

        public void decreaseLives() {
            if (lives > 0){
                lives--;
            }
        }

        public void increaseLives() {
            lives++;
        }
    }

}

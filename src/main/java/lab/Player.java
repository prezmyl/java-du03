package lab;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Player extends GameObject implements DrawableSimulable {

    private static final double WIDTH = 40;
    private static final double HEIGHT = 20;

    public Player(double x, double y) {
        super(x, y);
        this.speedX = 0.5;
    }

    @Override
    public void simulate(){
        if (position.getX() + speedX < 800 - WIDTH && position.getX() + speedX > 0) {
            position = new Point2D(position.getX() + speedX, position.getY());
        }
        else{
            this.speedX = -1 * speedX;
        }

    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.fillRect(position.getX(), position.getY(), WIDTH, HEIGHT);
    }

    public double getWidth() {
        return WIDTH;
    }

    public double getHeight() {
        return HEIGHT;
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

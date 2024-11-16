package lab;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;


public class DrawingThread extends AnimationTimer {
	private final GraphicsContext gc;
	private final ArrayList<GameObject> gameObject = new ArrayList<>();
	private final Player player;
	private final ScoreManager scoreManager;
	private final HealthDisplay healthDisplay;
	private long lastBulletTime = 0;
	private static final long BULLET_INTERVAL = 1000000000;


	public DrawingThread(Canvas canvas) {
		this.gc = canvas.getGraphicsContext2D();
		this.player = new Player(100, 350);
		gameObject.add(player);

		this.scoreManager = new ScoreManager();
		this.healthDisplay = new HealthDisplay(player.new Health(3));

		for (int i = 0; i < 11; i++) {
			gameObject.add(new Enemy(100 + i * 60, 50));
		}

		for (int i = 0; i < 5; i++) {
			gameObject.add(new Barricade(200 + i * 100, 300));
		}

	}

	@Override
	public void handle(long now) {
		gc.clearRect(0, 0, Constant.GAME_WIDTH, Constant.GAME_HEIGHT);

		// pridani strely
		if (now - lastBulletTime > BULLET_INTERVAL) {
				addBullet(player.getPosition().getX() + player.getWidth() / 2 - Bullet.BULLET_WIDTH / 2, player.getPosition().getY() - Bullet.BULLET_HEIGHT);
			lastBulletTime = now;
		}


		// aktualiza a vykresleni hernich obj.
		for (GameObject object : gameObject){
			if (object instanceof DrawableSimulable simulable){
				simulable.draw(gc);
				simulable.simulate();
			} else if (object instanceof DrawAble drawable) {
				drawable.draw(gc);
			}
		}

		/*
		gameObject.forEach(obj -> {
			if (obj instanceof DrawableSimulable simulable) {
				simulable.draw(gc);
				simulable.simulate();
			} else if (obj instanceof DrawAble drawable) {
				drawable.draw(gc);
			}
		}); */

		gc.fillText("Score: " + scoreManager.getScore(), 10, 40);
		healthDisplay.draw(gc);
	}

	// Pridani nove strely
	public void addBullet(double x, double y) {
		gameObject.add(new Bullet(x, y));
	}
}

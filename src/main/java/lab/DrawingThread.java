package lab;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.Iterator;

public class DrawingThread extends AnimationTimer {
	private final GraphicsContext gc;
	private final ArrayList<GameObject> gameObject = new ArrayList<>();
	private final ArrayList<DrawAble> drawAble = new ArrayList<>();
	private final Player player;
	private long lastBulletTime = 0;
	private static final long BULLET_INTERVAL = 1000000000;
	private final double bulletWIDTH = 5;

	public DrawingThread(Canvas canvas) {
		this.gc = canvas.getGraphicsContext2D();
		this.player = new Player(100, 350); // Vytvoření hráče
		gameObject.add(player);

		// Přidání nepřátel
		for (int i = 0; i < 11; i++) {
			gameObject.add(new Enemy(100 + i * 60, 50));
		}

		// Přidání barikád
		for (int i = 0; i < 5; i++) {
			drawAble.add(new Barricade(200 + i * 100, 300));
		}

	}

	@Override
	public void handle(long now) {
		gc.clearRect(0, 0, 800, 400);

		// pridani strely
		if (now - lastBulletTime > BULLET_INTERVAL) {
				addBullet(player.getPosition().getX() + player.getWidth() / 2 - Bullet.WIDTH / 2, player.getPosition().getY() - Bullet.HEIGHT);
			lastBulletTime = now;
		}

		// aktualiza a vykresleni hernich obj.
		for (GameObject obj : gameObject) {
			if (obj instanceof DrawableSimulable simulable) {
                simulable.draw(gc);
				simulable.simulate();
			}
			else if (obj instanceof DrawAble drawable) {
				drawable.draw(gc);
			}

		}

	}

	// Pridani nove strely
	public void addBullet(double x, double y) {
		gameObject.add(new Bullet(x, y));
	}
}

package lab;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;


public class DrawingThread extends AnimationTimer {
	private final GraphicsContext gc;
	private final ArrayList<GameObject> gameObject = new ArrayList<>();
	private final Player player;
	//private final GameStateObserver gameStateObserver;
	private final ScoreManager scoreManager;
	private final HealthDisplay healthDisplay;
	private long lastBulletTime = 0;
	private static final long BULLET_INTERVAL = 1000000000;


	public DrawingThread(Canvas canvas, GameSession gameSession ) {
		this.gc = canvas.getGraphicsContext2D();
		this.player = gameSession.getPlayer();
		this.scoreManager = gameSession.getScoreManager();
		//this.gameStateObserver = gameSession.getGameStateObserver();
		this.healthDisplay = new HealthDisplay(player.new Health(3));


		gameObject.add(player);

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

		checkCollisions();

		// aktualiza a vykresleni hernich obj.
		gameObject.removeIf(obj -> obj instanceof Collisionable && !((Collisionable) obj).isActive());

		gameObject.stream()
				.filter(obj -> obj instanceof DrawableSimulable)
				.map(obj -> (DrawableSimulable) obj)
				.forEach(simulable -> {
					simulable.simulate();
					simulable.draw(gc);
				});


		gameObject.stream()
				.filter(obj -> obj instanceof DrawAble && !(obj instanceof DrawableSimulable))
				.map(obj -> (DrawAble) obj)
				.forEach(drawable -> drawable.draw(gc));


		//gameStateObserver.onScoreUpdate(scoreManager.getScore());
		//gameStateObserver.onLivesUpdate(player.getHealth().getLives());
		healthDisplay.update(); // Aktualizuje zobrazení životů
		scoreManager.update(); // Aktualizuje zobrazení skóre

	/*	if (player.getHealth().getLives() <= 0) {
			gameStateObserver.onGameOver();
		}*/


	}

	private void checkCollisions() {
		for (int i = 0; i < gameObject.size(); i++) {
			GameObject obj1 = gameObject.get(i);
			if (!(obj1 instanceof Collisionable)) {
				continue;
			}
			for (int j = i + 1; j < gameObject.size(); j++) {
				GameObject obj2 = gameObject.get(j);
				if (!(obj2 instanceof Collisionable)) {
					continue;
				}

				Collisionable col1 = (Collisionable) obj1;
				Collisionable col2 = (Collisionable) obj2;

				if (col1.intersect(col2.getBoundingBox())) {
					col1.hitBy(col2);
					col2.hitBy(col1);
				}
			}
		}
	}

	// Pridani nove strely
		public void addBullet(Bullet bullet) {
			gameObject.add(bullet);
		}
}

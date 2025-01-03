package lab;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class DrawingThread extends AnimationTimer {
	private final GraphicsContext gc;
	private final ArrayList<GameObject> gameObject = new ArrayList<>();
	private final Player player;
	private final GameStateObserver gameStateObserver;
	private final ScoreManager scoreManager;
	private final HealthDisplay healthDisplay;
	private long lastBulletTime = 0;
	private long lastTime;
	private final GameSession gameSession;
	private double deltaT = 0.016;
	private long currentNow;

	private long lastSecond = 0;
	private int frameCount = 0;

	public DrawingThread(Canvas canvas, GameSession gameSession, GameStateObserver gameStateObserver ) {
		this.gc = canvas.getGraphicsContext2D();
		this.player = gameSession.getPlayer();
		this.scoreManager = gameSession.getScoreManager();
		this.gameStateObserver = gameStateObserver;
		this.healthDisplay = new HealthDisplay(player.new Health(3));
		this.gameSession = gameSession;
	}

	@Override
	public void start() {
		lastTime = System.nanoTime(); // Inicializace času při startu
		super.start(); // Spuštění herní smyčky AnimationTimer
	}

	@Override
	public void handle(long now) {
		currentNow = now;
		deltaT = (now - lastTime) / 1e9;
		lastTime = now;

		// Počet snímků za sekundu
		long currentSecond = now / 1_000_000_000;
		if (lastSecond == currentSecond) {
			frameCount++;
		} else {
			System.out.println("FPS: " + frameCount);
			frameCount = 0;
			lastSecond = currentSecond;
		}

		gc.clearRect(0, 0, Constant.GAME_WIDTH, Constant.GAME_HEIGHT);

		gameSession.removeInactiveObjects();
		checkCollisions();

		// Vykreslení a simulace DrawableSimulable
		gameSession.getDrawableSimulables().forEach(obj -> {
			obj.simulate(deltaT);
			obj.draw(gc);
		});

		// Vykreslení DrawAble
		gameSession.getDrawables().forEach(obj -> obj.draw(gc));

		gameStateObserver.onScoreUpdate(scoreManager.getScore());
		gameStateObserver.onLivesUpdate(player.getHealth().getLives());

		if (player.getHealth().getLives() <= 0) {
			stop();
			gameStateObserver.onGameOver();
		}

		/* aktualiza a vykresleni hernich obj.
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

		gameStateObserver.onScoreUpdate(scoreManager.getScore());
		gameStateObserver.onLivesUpdate(player.getHealth().getLives());


		if (player.getHealth().getLives() <= 0) {
			stop();
			gameStateObserver.onGameOver();
		}
*/

	}

	private void checkCollisions() {
		List<Collisionable> activeObjects = new ArrayList<>();
		activeObjects.addAll(gameSession.getBullets());
		activeObjects.addAll(gameSession.getEnemies());
		activeObjects.add(player);
		activeObjects.addAll(gameSession.getBarricades());

		for (int i = 0; i < activeObjects.size(); i++) {
			for (int j = i + 1; j < activeObjects.size(); j++) {
				Collisionable col1 = activeObjects.get(i);
				Collisionable col2 = activeObjects.get(j);

				col1.handleCollision(col2);
			}
		}
	}

/*
	private  void checkCollisions(){
			List<Collisionable> activeObjects = gameObject.stream()
					.filter(obj -> obj instanceof Collisionable)
					.map(obj -> (Collisionable) obj)
					.filter(Collisionable::isActive)
					.toList();

			for (int i = 0; i < activeObjects.size(); i++) {
				for (int j = i + 1; j < activeObjects.size(); j++) {
					Collisionable col1 = activeObjects.get(i);
					Collisionable col2 = activeObjects.get(j);

					col1.handleCollision(col2);
				}
			}
	}*/


	public void addBullet(Bullet bullet) {
		gameObject.add(bullet);
	}

	public double getDeltaT(){
		return deltaT;
	}

	public long getCurrentNow() {
		return currentNow;
	}
}

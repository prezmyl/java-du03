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
	private static final long BULLET_INTERVAL = 1000000000;


	public DrawingThread(Canvas canvas, GameSession gameSession, GameStateObserver gameStateObserver ) {
		this.gc = canvas.getGraphicsContext2D();
		this.player = gameSession.getPlayer();
		this.scoreManager = gameSession.getScoreManager();
		this.gameStateObserver = gameStateObserver;
		this.healthDisplay = new HealthDisplay(player.new Health(3));


		gameObject.add(player);

		for (int i = 0; i < 11; i++) {
			gameObject.add(new Enemy(100 + i * 60, 50));
		}

		for (int i = 0; i < 5; i++) {
			gameObject.add(new Barricade(200 + i * 100, 300));
		}

		/*if (player.getHealth().getLives() <= 0){
			onGameOver();
		}*/



	}

	/*private void onGameOver() {
		stop();
		System.out.println("onGameOver, stopping simulation!");
		Platform.runLater(() -> gameStateObserver.onGameOver());

	}*/

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


	/*	gameObject.stream()
					.filter(obj -> obj instanceof Collisionable)
					.map(obj -> (Collisionable) obj)
					.forEach(col1 -> gameObject.stream()
						.filter(obj -> obj instanceof Collisionable)
						.map(obj -> (Collisionable) obj)
						.filter(col2 -> col1 != col2)
						.filter(col2 -> col1.isActive() && col2.isActive())
						.filter(col2 -> col1.intersect(col2.getBoundingBox()))
						.forEach(col2 -> col1.handleCollision(col2)));
*/

		gameStateObserver.onScoreUpdate(scoreManager.getScore());
		gameStateObserver.onLivesUpdate(player.getHealth().getLives());


		if (player.getHealth().getLives() <= 0) {
			stop();
			gameStateObserver.onGameOver();
		}


	}

/*
private void checkCollisions() {
    List<Collisionable> activeObjects = gameObject.stream()
            .filter(obj -> obj instanceof Collisionable)
            .map(obj -> (Collisionable) obj)
            .filter(Collisionable::isActive)
            .toList();

    // Set pro sledování zpracovaných kolizí
    Set<Pair<Collisionable, Collisionable>> processedCollisions = new HashSet<>();

    for (int i = 0; i < activeObjects.size(); i++) {
        for (int j = i + 1; j < activeObjects.size(); j++) {
            Collisionable col1 = activeObjects.get(i);
            Collisionable col2 = activeObjects.get(j);

            Pair<Collisionable, Collisionable> collisionPair = new Pair<>(col1, col2);

            // Pokud již byla tato kolize zpracována, přeskočíme
            if (processedCollisions.contains(collisionPair)) {
                continue;
            }

            col1.handleCollision(col2);
            processedCollisions.add(collisionPair);
        }
    }
}

*/	private  void checkCollisions(){
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
	}
/*
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

				// Kontrola, zda jsou oba objekty aktivni
				if (!col1.isActive() || !col2.isActive()) {
					continue;
				}

				if (col1.intersect(col2.getBoundingBox())) {
					col1.hitBy(col2);
					col2.hitBy(col1);

					// Zpracujeme kolizi pouze jednou
					if (obj1 instanceof Enemy && obj2 instanceof Player) {
						handlePlayerEnemyCollision((Player) obj2, (Enemy) obj1);
					} else if (obj2 instanceof Enemy && obj1 instanceof Player) {
						handlePlayerEnemyCollision((Player) obj1, (Enemy) obj2);
					}

					if (obj1 instanceof Bullet && obj2 instanceof Enemy) {
						handleBulletEnemyCollision((Bullet) obj1, (Enemy) obj2);
					} else if (obj2 instanceof Bullet && obj1 instanceof Enemy) {
						handleBulletEnemyCollision((Bullet) obj2, (Enemy) obj1);
					}
				}
			}
		}
	}*/


	private void handlePlayerEnemyCollision(Player player, Enemy enemy) {
		if (!enemy.isActive()) {
			return; // Zabrání opakovanému zpracování stejné kolize
		}

		player.getHealth().decreaseLives();
		System.out.println("Player lives decreased. Current lives: " + player.getHealth().getLives()); // Debug výpis
		enemy.setActive(false);


	}

	private void handleBulletEnemyCollision(Bullet bullet, Enemy enemy) {
		if (enemy.isActive()) {
			System.out.println("Handling collision: Bullet -> Enemy");
			enemy.setActive(false);
			bullet.setActive(false);

			if (scoreManager != null) {
				scoreManager.increaseScore(100);
				System.out.println("Score increased. Current score: " + scoreManager.getScore());
			} else {
				System.out.println("ScoreManager is null!");
			}
		}
	}

	// Pridani nove strely
		public void addBullet(Bullet bullet) {
			gameObject.add(bullet);
		}
}

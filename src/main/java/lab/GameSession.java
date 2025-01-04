package lab;

import javafx.geometry.Point2D;
import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class GameSession {
    private final Player player;
    private final ScoreManager scoreManager;
    private final List<Enemy> enemies;
    private final List<Barricade> barricades;
    private final List<Bullet> bullets;
    private final Scene scene;


    public GameSession(Scene scene) {
        this.scene = scene;
        this.player = new Player(Constant.PLAYER_START.getX(), Constant.PLAYER_START.getY());
        this.enemies = new ArrayList<>();
        this.barricades = new ArrayList<>();
        this.bullets = new ArrayList<>();
        this.scoreManager = new ScoreManager();

        initializeEnemies();
        initializeBarricades();
    }
    public void moveEnemiesDown() {
        for (Enemy enemy : enemies) {
            enemy.setPosition(new Point2D(enemy.getPosition().getX(), enemy.getPosition().getY() + enemy.getMOVE_STEP()));
        }
    }

    private Direction enemyDirection = Direction.RIGHT;

    public Direction getEnemyDirection() {
        return enemyDirection;
    }

    public void updateEnemyDirection() {
        boolean atEdge = enemies.stream().anyMatch(enemy ->
                enemy.getPosition().getX() <= 0 ||
                        enemy.getPosition().getX() + enemy.getWidth() >= Constant.GAME_WIDTH
        );

        if (atEdge && shouldMoveEnemiesDown()) { // ❗ Nová podmínka pro posun dolů
            enemyDirection = (enemyDirection == Direction.RIGHT) ? Direction.LEFT : Direction.RIGHT;
            moveEnemiesDown();
            updateLastMoveDownTime(); // ❗ Aktualizace posledního posunu dolů
            System.out.println("Enemies moved down after reaching edge.");
        }
    }


    private double gameTime = 0; // Celkový herní čas
    private double lastMoveDownTime = 0; // Čas posledního posunu dolů
    private final double MOVE_DOWN_INTERVAL = 0.5; // Interval mezi posuny dolů (v sekundách)

    public double getGameTime() {
        return gameTime;
    }

    public void updateGameTime(double deltaT) {
        gameTime += deltaT;
    }

    public boolean shouldMoveEnemiesDown() {
        return gameTime - lastMoveDownTime >= MOVE_DOWN_INTERVAL;
    }

    public void updateLastMoveDownTime() {
        lastMoveDownTime = gameTime;
    }


    public void removeInactiveObjects() {
        bullets.removeIf(bullet -> !bullet.isActive());
        enemies.removeIf(enemy -> !enemy.isActive());
        barricades.removeIf(barricade -> !barricade.isActive());

    }


    public Player getPlayer() {
        return player;
    }

    public ScoreManager getScoreManager() {
        return scoreManager;
    }

    public Scene getScene() {
        return scene;
    }

    private void initializeEnemies() {
        for (int i = 0; i < 11; i++) {
            enemies.add(new Enemy(100 + i * 60, 50, this)); // Předání GameSession
        }
    }

    private void initializeBarricades() {
        for (int i = 0; i < 5; i++) {
            barricades.add(new Barricade(200 + i * 100, 300));
        }
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }


    public List<Bullet> getBullets() {
        return bullets;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Barricade> getBarricades() {
        return barricades;
    }


    public Stream<DrawableSimulable> getDrawableSimulables() {
        return Stream.concat(
                Stream.concat(
                        bullets.stream(),
                        enemies.stream()
                ),
                Stream.of(player)
        );
    }

    public Stream<DrawAble> getDrawables() {
        return Stream.concat(
                barricades.stream(),
                Stream.of(player)
        );
    }

    public Stream<Enemy> streamEnemies() {
        return enemies.stream();
    }


    public Stream<Barricade> streamBarricades() {
        return barricades.stream();
    }


}

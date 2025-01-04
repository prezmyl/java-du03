package lab;

import javafx.geometry.Point2D;
import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameSession {
    private final Player player;
    private final ScoreManager scoreManager;
    private final List<Enemy> enemies;
    private final List<Barricade> barricades;
    private final List<Bullet> bullets;
    private final Scene scene;
    private final Ground ground;

    private double gameTime = 0; // Celkový herní čas, nevyuzito


    public GameSession(Scene scene) {
        this.scene = scene;
        this.player = new Player(Constant.PLAYER_START.getX(), Constant.PLAYER_START.getY());
        this.enemies = new ArrayList<>();
        this.barricades = new ArrayList<>();
        this.bullets = new ArrayList<>();
        this.scoreManager = new ScoreManager();
        this.ground = new Ground();

        initializeEnemies();
        initializeBarricades();
    }



    public void removeInactiveObjects() {
        bullets.removeIf(bullet -> !bullet.isActive());
        enemies.removeIf(enemy -> !enemy.isActive());
        barricades.removeIf(barricade -> !barricade.isActive());

    }

    //endGame condition check
    public boolean checkEnemyReachedGround() {
        return enemies.stream().anyMatch(enemy -> enemy.getBoundingBox().intersects(ground.getBoundingBox()));
    }

    private long lastEnemyShotTime = 0;
    private final double SHOOT_PROBABILITY = 0.3; // 30% šance na střelbu

    public void enemyShoot(long now) {
        if (now - lastEnemyShotTime < Constant.BULLET_INTERVAL) {
            return; // Zabráníme příliš časté střelbě
        }

        List<Enemy> shootingCandidates = enemies.stream()
                .filter(enemy -> Math.random() < SHOOT_PROBABILITY)
                .toList();

        if (!shootingCandidates.isEmpty()) {
            Enemy shooter = shootingCandidates.get(new Random().nextInt(shootingCandidates.size()));
            shooter.shoot(now);
            lastEnemyShotTime = now;
        }
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

    public Ground getGround() {
        return ground;
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
                Stream.concat(
                        barricades.stream(),
                        Stream.of(player)
                ),
                Stream.of(ground)
        );
    }

    public Stream<Enemy> streamEnemies() {
        return enemies.stream();
    }


    public Stream<Barricade> streamBarricades() {
        return barricades.stream();
    }

    public double getGameTime() {
        return gameTime;
    }

    public void updateGameTime(double deltaT) {
        gameTime += deltaT;
    }

}

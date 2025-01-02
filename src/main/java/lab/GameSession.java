package lab;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class GameSession {
    private final Player player;
    private final ScoreManager scoreManager;
    private final List<Enemy> enemies;
    private final List<Barricade> barricades;
    private final List<Bullet> bullets;


    public GameSession() {
        this.player = new Player(Constant.PLAYER_START.getX(), Constant.PLAYER_START.getY());
        this.enemies = new ArrayList<>();
        this.barricades = new ArrayList<>();
        this.bullets = new ArrayList<>();
        this.scoreManager = new ScoreManager();

        initializeEnemies();
        initializeBarricades();
    }

    public void removeInactiveObjects() {
        bullets.removeIf(bullet -> !bullet.isActive());
        enemies.removeIf(enemy -> !enemy.isActive());
       //  barricades.removeIf(barricade -> barricade instanceof Collisionable && !((Collisionable) barricade).isActive());
    }


    public Player getPlayer() {
        return player;
    }

    public ScoreManager getScoreManager() {
        return scoreManager;
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

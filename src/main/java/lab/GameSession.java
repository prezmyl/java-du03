package lab;

public class GameSession {
    private final Player player;
    private final ScoreManager scoreManager;
    private final GameStateObserver gameStateObserver;

    public GameSession(GameStateObserver gameStateObserver) {
        this.player = new Player(Constant.PLAYER_START.getX(), Constant.PLAYER_START.getY());
        this.scoreManager = new ScoreManager();
        this.gameStateObserver = gameStateObserver;
    }

    public Player getPlayer() {
        return player;
    }

    public ScoreManager getScoreManager() {
        return scoreManager;
    }

    public GameStateObserver getGameStateObserver() {
        return gameStateObserver;
    }
}

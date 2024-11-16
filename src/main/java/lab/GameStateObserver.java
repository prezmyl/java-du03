package lab;

public interface GameStateObserver {
    void onScoreUpdate(int newScore);
    void onLivesUpdate(int remainingLives);
    void onGameOver();
}

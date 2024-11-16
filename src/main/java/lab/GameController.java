package lab;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javafx.fxml.FXML;

import java.util.HashMap;
import java.util.Map;

public class GameController implements GameStateObserver {

    @FXML
    private Label scoreLabel;
    @FXML
    private Label livesLabel;

    private GameSession gameSession;
    private final Map<KeyCode, Runnable> keyAction = new HashMap<>();

    public GameController(){
        // Bezparametrický konstruktor pro FXML
    }

    public void setGameSession(GameSession gameSession, DrawingThread drawingThread) {
        this.gameSession = gameSession;

        //init valid input mapping using lambda
        Player player = gameSession.getPlayer();
        keyAction.put(KeyCode.LEFT, () -> player.moveLeft());
        keyAction.put(KeyCode.RIGHT, () -> player.moveRight());
        keyAction.put(KeyCode.SPACE, () -> player.shoot(drawingThread));
    }



    //Lambda
    public void initialize(){
        System.out.println("GameController initialized.");
    }

    @FXML
    protected void handleKeyPress(KeyEvent keyEvent) {
        System.out.println("Key pressed: " + keyEvent.getCode()); // Výpis klávesy
        Runnable action = keyAction.get(keyEvent.getCode());
        if(action != null){
            System.out.println("Action triggered for: " + keyEvent.getCode());
            action.run();
        }
        else  System.out.println("No action mapped for: " + keyEvent.getCode());

    }

    @Override
    public void onScoreUpdate(int newScore) {
        scoreLabel.setText("Score: " + newScore);
    }

    @Override
    public void onLivesUpdate(int remainingLives) {
        livesLabel.setText("Lives: " + remainingLives);

    }

    @Override
    public void onGameOver() {
        System.out.println("Game Over!");
        //+ logika pro ukonceni hry
    }



}

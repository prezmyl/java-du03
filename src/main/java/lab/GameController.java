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
    }

    public void setGameSession(GameSession gameSession) {
        this.gameSession = gameSession;

        //init valid input mapping using lambda
        Player player = gameSession.getPlayer();
        keyAction.put(KeyCode.LEFT, () -> player.moveLeft());
        keyAction.put(KeyCode.RIGHT, () -> player.moveRight());
        keyAction.put(KeyCode.SPACE, () -> player.shoot());
    }

    //Lambda
    public void initialize(){
    }

    @FXML
    protected void handleKeyPress(KeyEvent keyEvent) {
        Runnable action = keyAction.get(keyEvent.getCode());
        if(action != null){
            action.run();
        }

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

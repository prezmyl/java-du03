package lab;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameController implements GameStateObserver {

    @FXML
    private Label scoreLabel;
    @FXML
    private Label livesLabel;

    private GameSession gameSession;
    private final Map<KeyCode, Runnable> keyAction = new HashMap<>();

    public GameController() {
        // Bezparametrický konstruktor pro FXML
    }

    public void setGameSession(GameSession gameSession, DrawingThread drawingThread) {
        this.gameSession = gameSession;

        //init valid input mapping using lambda
        Player player = gameSession.getPlayer();
        keyAction.put(KeyCode.LEFT, () -> player.moveLeft());
        keyAction.put(KeyCode.RIGHT, () -> player.moveRight());
        keyAction.put(KeyCode.SPACE, () -> player.shoot(drawingThread));
        keyAction.put(KeyCode.H, this::displayHighScores);
        keyAction.put(KeyCode.J, this::saveCurrentScore);
    }

    public void initialize() {
        System.out.println("GameController initialized.");
    }

    @FXML
    protected void handleKeyPress(KeyEvent keyEvent) {
        System.out.println("Key pressed: " + keyEvent.getCode()); // Výpis klávesy
        Runnable action = keyAction.get(keyEvent.getCode());
        if (action != null) {
            System.out.println("Action triggered for: " + keyEvent.getCode());
            action.run();
        } else {
            System.out.println("No action mapped for: " + keyEvent.getCode());
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
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText("invasion succeed");
            alert.setContentText("You lost all your lives and the planet Earth is lost");
            alert.showAndWait();
            saveCurrentScore();
        });

    }

    @FXML
    private void displayHighScores() {
        List<Integer> highScores = gameSession.getScoreManager().getHighScores();
        if (highScores.isEmpty()) {
            showAlert("No high scores available.");
        } else {
            StringBuilder sb = new StringBuilder("Top 5 High Scores:\n");
            for (int i = 0; i < highScores.size(); i++) {
                sb.append(i + 1).append(". ").append(highScores.get(i)).append("\n");
            }
            showAlert(sb.toString());
        }
    }

    @FXML
    public void handleHighScoresButton() {
        displayHighScores();
        System.out.println("High Scores button clicked");
        requestFocusToCanvas();


    }

    @FXML
    public void handleSaveScoreButton() {
        saveCurrentScore();
        System.out.println("Save Score button clicked");
        requestFocusToCanvas();
    }

    private void requestFocusToCanvas() {
        if (scoreLabel.getScene() != null) {
            scoreLabel.getScene().getRoot().requestFocus();
        }
    }

    private void saveCurrentScore() {
        gameSession.getScoreManager().saveCurrentScore();
        showAlert("Score saved successfully!");
    }

    private void showAlert(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("High Scores");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}

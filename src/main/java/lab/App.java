package lab;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {

	private DrawingThread drawingThread;
	private GameSession gameSession;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/lab/gameWindow.fxml"));
			Pane root = loader.load();

			gameSession = new GameSession();

			GameController gameController = loader.getController();

			Canvas canvas = new Canvas(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
			root.getChildren().add(0,canvas);
			canvas.setFocusTraversable(false);

			drawingThread = new DrawingThread(canvas, gameSession);
			gameController.setGameSession(gameSession, drawingThread);

			Scene scene = new Scene(root, Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Space Invaders");
			primaryStage.show();

			scene.setOnKeyPressed(gameController::handleKeyPress);
			canvas.requestFocus();

			drawingThread.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop() throws Exception {
		if (gameSession != null) {
			gameSession.getScoreManager().saveCurrentScore();
		}
		if (drawingThread != null) {
			drawingThread.stop();
		}
		super.stop();
	}
}

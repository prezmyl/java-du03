package lab;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {

	private DrawingThread drawingThread;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/lab/gameWindow.fxml"));
			Pane root = loader.load();

			GameSession gameSession = new GameSession();
			//Player player = new Player(Constant.PLAYER_START.getX(), Constant.PLAYER_START.getY());

			GameController gameController = loader.getController();



			//Group root = new Group();
			Canvas canvas = new Canvas(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
			root.getChildren().add(canvas);
			drawingThread = new DrawingThread(canvas, gameSession);
			gameController.setGameSession(gameSession, drawingThread);
			Scene scene = new Scene(root, Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Space Invaders");
			primaryStage.show();

			scene.setOnKeyPressed(gameController::handleKeyPress);
			canvas.requestFocus();


			// Spusteni herni smycky

			drawingThread.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop() throws Exception {
		drawingThread.stop();
		super.stop();
	}
}

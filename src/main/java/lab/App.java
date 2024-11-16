package lab;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public class App extends Application {

	private DrawingThread drawingThread;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			Group root = new Group();
			Canvas canvas = new Canvas(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
			root.getChildren().add(canvas);
			Scene scene = new Scene(root, Constant.GAME_WIDTH, Constant.GAME_HEIGHT);

			primaryStage.setScene(scene);
			primaryStage.setTitle("Space Invaders");
			primaryStage.show();

			// Spusteni herni smycky
			drawingThread = new DrawingThread(canvas);
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

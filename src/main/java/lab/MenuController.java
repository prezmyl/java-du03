package lab;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class MenuController {

    private App app; // Odkaz na hlavni tridu

    public void setApp(App app) {
        this.app = app;
    }

    @FXML
    private void startGame(ActionEvent event) {
        Stage primaryStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        app.startGame(primaryStage); // Prepnuti na herni scenu
    }

    @FXML
    private void exitGame(ActionEvent event) {
        // Ukonceni aplikace
        System.exit(0);
    }
}

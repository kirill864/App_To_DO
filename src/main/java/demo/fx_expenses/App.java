package demo.fx_expenses;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App extends Application {
    public static String loggedInUsername;
    private Stage stage;
    private Connection conn;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("samp.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 822.0, 479.0);
            stage.setTitle("Log in!");
            stage.setScene(scene);
            stage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void setConnection(Connection conn) {
        this.conn = conn;
    }

    public Stage getStage() {
        return stage;
    }
}
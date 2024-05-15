package demo.fx_expenses;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App extends Application {
    public static String loggedInUsername; // Переменная для хранения имени вошедшего пользователя
    private Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("samp.fxml")); // Загружаем FXML файл
            Scene scene = new Scene(fxmlLoader.load(), 822.0, 479.0); // Создаем сцену с размерами
            stage.setTitle("Log in!"); // Устанавливаем заголовок окна
            stage.setScene(scene); // Устанавливаем сцену на Stage
            stage.show(); // Показываем окно
        } catch (Exception e) {
            e.printStackTrace(); // Печатаем стек ошибки в случае исключения
        }
    }

    public static void main(String[] args) {
        launch(args); // Запуск
    }

}

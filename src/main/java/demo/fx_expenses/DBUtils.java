package demo.fx_expenses;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;

import static java.sql.DriverManager.getConnection;

public class DBUtils {
    private static Connection connection;


    // Метод для смены сцены
    public static <T> T changeScene(ActionEvent event, String fxmlFile, String title, String username) {
        Parent root = null;
        T controller = null;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DBUtils.class.getResource(fxmlFile)); // Загрузка FXML файла
            root = fxmlLoader.load();
            controller = fxmlLoader.getController(); // Получаем контроллер сцены

            if (controller instanceof AddTaskController) {
                ((AddTaskController) controller).setConnection(connection); // Устанавливаем соединение
            }

            if (username != null) {
                LoggedInController loggedInController = fxmlLoader.getController();
                loggedInController.setUserInformation(username); // Устанавливаем имя пользователя
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title); // Устанавливаем заголовок сцены
        stage.setScene(new Scene(root)); // Устанавливаем новую сцену
        stage.show(); // Показываем сцену

        return controller; // Возвращаем контроллер
    }

    // Метод для регистрации пользователя
    public static void signUpUser(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection("jdbc:mysql://localhost:3306/app_myexpenses", "root", ""); // Подключение к базе данных
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("Такой пользователь уже есть");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO users (username, password) VALUES(?, ?)");
                psInsert.setString(1, username);
                psInsert.setString(2, password);
                psInsert.executeUpdate();

                changeScene(event, "logged-in.fxml", "Мои расходы", username); // Смена сцены после успешной регистрации
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("bad ");
        } finally {
            // Закрытие ресурсов
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCheckUserExists != null) {
                try {
                    psCheckUserExists.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Метод для входа в систему
    public static void logInUser(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection("jdbc:mysql://localhost:3306/app_myexpenses", "root", ""); // Подключение к базе данных
            preparedStatement = connection.prepareStatement("select password from users where username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Нету такого пользователя");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");
                    if (retrievedPassword.equals(password)) {
                        App.loggedInUsername = username; // Сохраняем имя пользователя
                        changeScene(event, "logged-in.fxml", "2DO", username); // Смена сцены после успешного входа
                    } else {
                        System.out.println("Неправильный пароль");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.show();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Закрытие соединения с базой
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

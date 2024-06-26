package demo.fx_expenses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoggedInController implements Initializable{
    @FXML
    private Button button_logout;

    @FXML
    private Label label_welcome; 

    @FXML
    private TableView<Task> taskTable; 

    @FXML
    private TableColumn<Task, Date> dateCol; 

    @FXML
    private TableColumn<Task, String> importanceCol; 

    @FXML
    private TableColumn<Task, String> descriptionCol; 

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Task task = null;

    ObservableList<Task> TaskList = FXCollections.observableArrayList(); // Список задач

    String username = null; // Имя вошедшего пользователя

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/app_myexpenses", "root", ""); // Подключение к базе данных
            loadDate(); // Загрузка данных в таблицу
            refreshTable(null); // Обновление таблицы при загрузке приложения

            // Получаем контроллер AddTaskController
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addTask.fxml"));
            Parent parent = loader.load();
            AddTaskController addTaskController = loader.getController();



        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        // Обработчик события нажатия на кнопку выхода
        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "samp.fxml", "TO DO", null); // Смена сцены на страницу входа
            }
        });

        // Устанавливаем информацию о пользователе, если он вошел
        if (App.loggedInUsername != null) {
            setUserInformation(App.loggedInUsername);
        }
    }

    public void setUserInformation(String username) {
        label_welcome.setText(username); // Устанавливаем имя пользователя на метке приветствия
    }
    

    @FXML
    void addNewTask(MouseEvent event) throws IOException {
        // Открытие окна для добавления новой задачи
        Parent parent = FXMLLoader.load(getClass().getResource("addTask.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

    @FXML
    void deleteTask(MouseEvent event) {
        // Обработчик для удаления задачи
    }

    @FXML
    void editTask(MouseEvent event) {
        // Обработчик для редактирования задачи
    }

    @FXML
    void refreshTable(MouseEvent event) {
        // Обновление данных в таблице
        try {
            TaskList.clear();

            query = "SELECT * FROM do_list WHERE user_name = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, App.loggedInUsername);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                TaskList.add(new Task(
                        resultSet.getString("description"),
                        resultSet.getDate("date_start"),
                        resultSet.getString("impotance")));
            }

            taskTable.setItems(TaskList); // Устанавливаем обновленные данные в таблицу
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void loadDate() throws SQLException {
        // Загрузка данных в колонки таблицы
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date_start"));
        importanceCol.setCellValueFactory(new PropertyValueFactory<>("impotance"));
    }

    public void setConnection(Connection conn) {
        this.connection = conn; // Установка соединения с базой данных
    }
}

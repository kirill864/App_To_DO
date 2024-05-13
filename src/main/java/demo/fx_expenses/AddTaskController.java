package demo.fx_expenses;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static java.sql.DriverManager.getConnection;

public class AddTaskController implements Initializable {

    @FXML
    private TextField description_tf;

    @FXML
    private DatePicker end_datePicker;

    @FXML
    private TextField impotance_tf;

    private Connection connection;

    String query = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Task task = null;
    private LoggedInController loggedInController;

    public void setLoggedInController(LoggedInController loggedInController) {
        this.loggedInController = loggedInController;
    }

    @FXML
    void close(MouseEvent event) {

    }

    @FXML
    void save(MouseEvent event) throws SQLException {
        String description = description_tf.getText();
        String importance = impotance_tf.getText();
        LocalDate date = end_datePicker.getValue();


        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/app_myexpenses", "root", "");



        if (description.isEmpty() || importance.isEmpty() || date == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Введите все данные");
            alert.showAndWait();
        } else {
            try {
                String query = "INSERT INTO do_list (description, date_start, impotance, user_name) VALUES (?, ?, ?, ?)";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, description);
                preparedStatement.setDate(2, Date.valueOf(date));
                preparedStatement.setString(3, importance);
                preparedStatement.setString(4, App.loggedInUsername);

                int rowsAffected = preparedStatement.executeUpdate();



            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}

package demo.fx_expenses;

import demo.fx_expenses.DBUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private Button button_LoginScene;

    @FXML
    private Button button_register;

    @FXML
    private TextField tf_login;

    @FXML
    private TextField tf_password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        button_register.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!tf_login.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty() ){
                    DBUtils.signUpUser(event,tf_login.getText(),tf_password.getText());
                }else {
                    System.out.println("Введите всю информацию");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.show();
                }


            }
        });

        button_LoginScene.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event,"samp.fxml", "2DO", null);
            }
        });
    }
}



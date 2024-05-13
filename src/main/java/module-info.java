module demo.fx_expenses {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens demo.fx_expenses to javafx.fxml;
    exports demo.fx_expenses;
}
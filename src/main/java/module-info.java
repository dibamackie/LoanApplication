module com.example.loanapplication {
    requires javafx.controls;
    requires javafx.fxml;
    exports com.example.loanapplication;
    exports com.example.loanapplication.controllers;
    opens com.example.loanapplication to javafx.fxml;
    opens com.example.loanapplication.controllers to javafx.fxml;
}
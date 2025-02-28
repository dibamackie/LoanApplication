package com.example.loanapplication.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class LoginController {
    @FXML private TextField userNameText;
    @FXML private TextField passwordText;

    private final String[][] users = {{"user1", "pass123"}, {"user2", "pass456"}};

    @FXML
    private void signIn() throws Exception {
        String username = userNameText.getText().trim();
        String password = passwordText.getText().trim();

        boolean isValid = false;
        for (String[] user : users) {
            if (user[0].equals(username) && user[1].equals(password)) {
                isValid = true;
                break;
            }
        }

        if (isValid) {
            Stage stage = (Stage) userNameText.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/loanapplication/autoLoanApplication.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 579, 630);
            stage.setScene(scene);
            stage.setTitle("Auto Loan Application");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid username or password!");
            alert.showAndWait();
            userNameText.clear();
            passwordText.clear();
            userNameText.requestFocus();
        }
    }
}
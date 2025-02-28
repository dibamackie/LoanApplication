package com.example.loanapplication.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.List;

public class SavedRatesController {
    @FXML private ListView<LoanData> savedRatesListView;
    @FXML private Button loadButton, removeButton;

    private List<LoanData> savedRates;
    private AutoLoanController parentController;

    public void setSavedRates(List<LoanData> savedRates, AutoLoanController parentController) {
        this.savedRates = savedRates;
        this.parentController = parentController;
        savedRatesListView.getItems().addAll(savedRates);

        savedRatesListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                loadSelectedRate();
            }
        });
    }

    @FXML
    private void loadSelectedRate() {
        LoanData selected = savedRatesListView.getSelectionModel().getSelectedItem();
        if (selected != null && parentController != null) {
            parentController.loadSavedRate(selected);
            Stage stage = (Stage) savedRatesListView.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void remove() {
        LoanData selected = savedRatesListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            savedRates.remove(selected);
            savedRatesListView.getItems().remove(selected);
        }
    }
}
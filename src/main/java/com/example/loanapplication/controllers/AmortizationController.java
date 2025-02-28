package com.example.loanapplication.controllers;

import com.example.loanapplication.LoanCalculation;
import com.example.loanapplication.Payment;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AmortizationController {
    @FXML private TableView<Payment> amortizationScheduleTable;
    @FXML private TableColumn<Payment, Integer> paymentNoColumn;
    @FXML private TableColumn<Payment, String> paymentDateColumn;
    @FXML private TableColumn<Payment, Double> paymentAmountColumn, interestPaymentColumn, principlePaymentColumn, remainingBlanaceColumn;

    public void setLoan(LoanCalculation loan) {
        paymentNoColumn.setCellValueFactory(new PropertyValueFactory<>("paymentNo"));
        paymentDateColumn.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        paymentAmountColumn.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));
        interestPaymentColumn.setCellValueFactory(new PropertyValueFactory<>("interestPayment"));
        principlePaymentColumn.setCellValueFactory(new PropertyValueFactory<>("principalPayment"));
        remainingBlanaceColumn.setCellValueFactory(new PropertyValueFactory<>("remainingBalance"));

        amortizationScheduleTable.getItems().addAll(loan.generateAmortizationSchedule());
    }
}
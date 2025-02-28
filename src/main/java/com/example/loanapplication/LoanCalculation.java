package com.example.loanapplication;

import java.util.List;

public interface LoanCalculation {
    double calculatePayment();
    List<Payment> generateAmortizationSchedule();
}
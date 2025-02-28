package com.example.loanapplication;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FixedRateLoan implements LoanCalculation {
    private double principal;
    private double annualInterestRate;
    private int loanTermMonths;
    private String paymentFrequency;

    public FixedRateLoan(double principal, double annualInterestRate, int loanTermMonths, String paymentFrequency) {
        this.principal = principal;
        this.annualInterestRate = annualInterestRate / 100;
        this.loanTermMonths = loanTermMonths;
        this.paymentFrequency = paymentFrequency;
    }

    @Override
    public double calculatePayment() {
        int paymentsPerYear = switch (paymentFrequency) {
            case "Weekly" -> 52;
            case "Bi-Weekly" -> 26;
            case "Monthly" -> 12;
            default -> 12;
        };
        double monthlyRate = annualInterestRate / paymentsPerYear;
        int totalPayments = (int) (loanTermMonths * paymentsPerYear / 12.0);
        return (principal * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -totalPayments));
    }

    @Override
    public List<Payment> generateAmortizationSchedule() {
        List<Payment> schedule = new ArrayList<>();
        double payment = calculatePayment();
        double balance = principal;
        int paymentsPerYear = switch (paymentFrequency) {
            case "Weekly" -> 52;
            case "Bi-Weekly" -> 26;
            case "Monthly" -> 12;
            default -> 12;
        };
        double monthlyRate = annualInterestRate / paymentsPerYear;
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        for (int i = 1; balance > 0; i++) {
            double interest = balance * monthlyRate;
            double principalPayment = payment - interest;
            balance -= principalPayment;
            if (balance < 0) balance = 0;

            schedule.add(new Payment(i, date.format(formatter), payment, interest, principalPayment, balance));

            date = switch (paymentFrequency) {
                case "Weekly" -> date.plusWeeks(1);
                case "Bi-Weekly" -> date.plusWeeks(2);
                case "Monthly" -> date.plusMonths(1);
                default -> date.plusMonths(1);
            };
        }
        return schedule;
    }
}
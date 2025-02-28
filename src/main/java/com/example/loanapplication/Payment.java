package com.example.loanapplication;

public class Payment {
    private int paymentNo;
    private String paymentDate;
    private double paymentAmount, interestPayment, principalPayment, remainingBalance;

    public Payment(int paymentNo, String paymentDate, double paymentAmount, double interestPayment,
                   double principalPayment, double remainingBalance) {
        this.paymentNo = paymentNo;
        this.paymentDate = paymentDate;
        this.paymentAmount = paymentAmount;
        this.interestPayment = interestPayment;
        this.principalPayment = principalPayment;
        this.remainingBalance = remainingBalance;
    }

    public int getPaymentNo() { return paymentNo; }
    public String getPaymentDate() { return paymentDate; }
    public double getPaymentAmount() { return paymentAmount; }
    public double getInterestPayment() { return interestPayment; }
    public double getPrincipalPayment() { return principalPayment; }
    public double getRemainingBalance() { return remainingBalance; }
}
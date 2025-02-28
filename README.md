# Auto Loan Application

Auto Loan Application is a JavaFX-based desktop application for calculating and managing auto loans. Developed as part of a workshop, it allows users to input customer information, vehicle details, and loan terms, then calculates payments and generates amortization schedules.

## Features
- **Login System**: Authenticate users with hard-coded credentials.
- **Customer Information**: Capture full name, phone, city, and province with real-time validation.
- **Vehicle Information**: Select vehicle type (Car, Truck, Family Van), age (New, Used), and price.
- **Loan Information**: Input down payment, select interest rate (0.99%, 1.99%, 2.99%, or custom), loan duration (12–96 months), and payment frequency (Weekly, Bi-Weekly, Monthly).
- **Calculate Payments**: Compute fixed-rate loan payments with formatted currency output.
- **Save and Load Rates**: Save loan data in-memory and load it back into the form.
- **Amortization Schedule**: Display a detailed payment breakdown over the loan term.

## Project Structure
- **Java Controllers**: `LoginController`, `AutoLoanController`, `AmortizationController`, `SavedRatesController`
- **FXML Files**: `logIn.fxml`, `autoLoanApplication.fxml`, `amortizationSchedule.fxml`, `savedRates.fxml`
- **Business Logic**: `LoanCalculation` interface, `FixedRateLoan` implementation, `Payment` class

## Prerequisites
- Java 17 or later
- JavaFX SDK (or Maven dependency for JavaFX)
- Maven (if using `pom.xml`)

## How to Run
1. Clone the repository:

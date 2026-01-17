# Auto Loan Application

The **Auto Loan Application** is a **JavaFX-based desktop application** designed to calculate and manage auto loans through an intuitive graphical interface. Developed as part of a workshop, the application allows users to enter customer information, vehicle details, and loan terms, then computes loan payments and generates a detailed amortization schedule.

This project demonstrates practical financial calculations, clean UI design, and structured application architecture using Java and JavaFX.

---

## Features

### Authentication
- Login system with hard-coded credentials
- Ensures controlled access to the application

### Customer Information
- Capture customer details:
  - Full name
  - Phone number
  - City
  - Province (dropdown selection)
- Real-time input validation

### Vehicle Information
- Select vehicle type:
  - Car
  - Truck
  - Family Van
- Select vehicle condition:
  - New
  - Used
- Enter vehicle price

### Loan Information
- Enter down payment amount
- Select interest rate:
  - 0.99%
  - 1.99%
  - 2.99%
  - Custom rate
- Choose loan duration using a slider (12–96 months)
- Select payment frequency:
  - Weekly
  - Bi-weekly
  - Monthly

### Loan Calculation
- Calculate fixed-rate loan payments
- Display estimated payment with formatted currency output

### Saved Rates
- Save loan rate configurations in memory
- View saved rates in a separate window
- Load or remove saved rate entries

### Amortization Schedule
- Generate a detailed amortization table including:
  - Payment number
  - Payment date
  - Payment amount
  - Interest payment
  - Principal payment
  - Remaining balance

---

## Project Structure

### Controllers
- `LoginController`
- `AutoLoanController`
- `AmortizationController`
- `SavedRatesController`

### FXML Views
- `logIn.fxml`
- `autoLoanApplication.fxml`
- `amortizationSchedule.fxml`
- `savedRates.fxml`

### Business Logic
- `LoanCalculation` interface
- `FixedRateLoan` implementation
- `Payment` model class

---

## Tech Stack

- **Language:** Java  
- **UI Framework:** JavaFX  
- **Concepts & Practices:**
  - Object-Oriented Programming (OOP)
  - MVC-style separation
  - Event-driven programming
  - Financial computation
  - GUI-based application design

---

## Application Workflow

1. User logs in with valid credentials
2. Customer and vehicle information is entered
3. Loan terms are selected
4. Payment is calculated and displayed
5. User may:
   - Save loan rate details
   - View saved rates
   - Generate a full amortization schedule

---

## Author

Developed by **Diba Makki**

---

## License

This project is for educational and portfolio purposes.

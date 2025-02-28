package com.example.loanapplication.controllers;

import com.example.loanapplication.FixedRateLoan;
import com.example.loanapplication.LoanCalculation;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for the Auto Loan Application form.
 * Handles user input, validation, loan calculations, and saving/loading of form data.
 */
public class AutoLoanController {
    // Customer Information Fields
    @FXML private TextField nameText, phoneText, cityText;
    @FXML private ChoiceBox<String> provinceChoice;

    // Vehicle Information Fields
    @FXML private RadioButton typeCarRadio, typeTruckRadio, typeFamilyVanRadio;
    @FXML private RadioButton ageNewRadio, ageUsedRadio;
    @FXML private TextField vehiclePriceText;

    // Loan Information Fields
    @FXML private TextField downPaymentText, interestRateOtherText;
    @FXML private RadioButton interestRate099Radio, interestRate199Radio, interestRate299Radio, interestRateOtherRadio;
    @FXML private RadioButton paymentFrequencyWeeklyRadio, paymentFrequencyBiWeeklyRadio, paymentFrequencyMonthlyRadio;
    @FXML private Slider loanDurationSlider;

    // Toggle Groups for Radio Buttons
    @FXML private ToggleGroup vehicleType;
    @FXML private ToggleGroup vehicleAge;
    @FXML private ToggleGroup interestRate;
    @FXML private ToggleGroup paymentFrequency;

    // Action Buttons
    @FXML private Button calculateButton, saveRateButton, savedRatesButton, amortizationButton, clearButton;

    // List to store saved loan data
    private List<LoanData> savedRates = new ArrayList<>();
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

    /**
     * Initializes the controller after the FXML file has been loaded.
     */
    @FXML
    private void initialize() {
        // Populate province dropdown
        provinceChoice.getItems().addAll("ON", "QC", "BC", "AB", "MB", "SK", "NS", "NB", "NL", "PE");

        // Configure loan duration slider
        loanDurationSlider.setMin(12);
        loanDurationSlider.setMax(96);
        loanDurationSlider.setMajorTickUnit(12);
        loanDurationSlider.setShowTickMarks(true);
        loanDurationSlider.setShowTickLabels(true);
        loanDurationSlider.setSnapToTicks(true);

        // Bind visibility of custom interest rate text field to "Other" radio button
        interestRateOtherText.visibleProperty().bind(interestRateOtherRadio.selectedProperty());
        interestRateOtherText.managedProperty().bind(interestRateOtherRadio.selectedProperty());
    }

    /**
     * Clears all form fields and resets selections.
     */
    @FXML
    private void clearForm() {
        nameText.clear();
        phoneText.clear();
        cityText.clear();
        provinceChoice.getSelectionModel().clearSelection();
        vehicleType.selectToggle(null);
        vehicleAge.selectToggle(null);
        vehiclePriceText.clear();
        downPaymentText.clear();
        interestRate.selectToggle(null);
        interestRateOtherText.clear();
        loanDurationSlider.setValue(12);
        paymentFrequency.selectToggle(null);
    }

    /**
     * Calculates the loan payment based on user input and displays the result.
     */
    @FXML
    private void calculate() {
        if (!validateFields()) return;

        double price = Double.parseDouble(vehiclePriceText.getText());
        double downPayment = Double.parseDouble(downPaymentText.getText());
        double interestRateValue = getInterestRate();
        int months = (int) loanDurationSlider.getValue();
        String frequency = getPaymentFrequency();

        LoanCalculation loan = new FixedRateLoan(price - downPayment, interestRateValue, months, frequency);
        double payment = loan.calculatePayment();

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Estimated Payment: " + currencyFormat.format(payment));
        alert.showAndWait();
    }

    /**
     * Saves all form data into a LoanData object and adds it to the savedRates list.
     */
    @FXML
    private void saveRate() {
        if (!validateFields()) return;

        String name = nameText.getText();
        String phone = phoneText.getText();
        String city = cityText.getText();
        String province = provinceChoice.getValue();
        String vehicleTypeSelected = ((RadioButton) vehicleType.getSelectedToggle()).getText();
        String vehicleAgeSelected = ((RadioButton) vehicleAge.getSelectedToggle()).getText();
        double vehiclePrice = Double.parseDouble(vehiclePriceText.getText());
        double downPayment = Double.parseDouble(downPaymentText.getText());
        double interestRateValue = getInterestRate();
        double loanDuration = loanDurationSlider.getValue();
        String paymentFrequencySelected = ((RadioButton) paymentFrequency.getSelectedToggle()).getText();

        LoanData data = new LoanData(name, phone, city, province, vehicleTypeSelected, vehicleAgeSelected,
                vehiclePrice, downPayment, interestRateValue, loanDuration,
                paymentFrequencySelected);
        savedRates.add(data);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Rate saved successfully!");
        alert.showAndWait();
    }

    /**
     * Opens a new window to display saved rates.
     */
    @FXML
    private void savedRates() throws Exception {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/loanapplication/savedRates.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 298, 400);
        SavedRatesController controller = fxmlLoader.getController();
        controller.setSavedRates(savedRates, this);
        stage.setScene(scene);
        stage.setTitle("Saved Rates");
        stage.show();
    }

    /**
     * Opens a new window to display the amortization schedule based on current form data.
     */
    @FXML
    private void amortizationSchedule() throws Exception {
        if (!validateFields()) return;

        double price = Double.parseDouble(vehiclePriceText.getText());
        double downPayment = Double.parseDouble(downPaymentText.getText());
        double interestRateValue = getInterestRate();
        int months = (int) loanDurationSlider.getValue();
        String frequency = getPaymentFrequency();

        LoanCalculation loan = new FixedRateLoan(price - downPayment, interestRateValue, months, frequency);
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/loanapplication/amortizationSchedule.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 724, 400);
        AmortizationController controller = fxmlLoader.getController();
        controller.setLoan(loan);
        stage.setScene(scene);
        stage.setTitle("Amortization Schedule");
        stage.show();
    }

    /**
     * Validates all required fields and returns true if valid, false otherwise.
     * Displays an error dialog with specific messages if validation fails.
     */
    private boolean validateFields() {
        List<String> errors = new ArrayList<>();

        // Validate Customer Information
        if (nameText.getText().isEmpty()) errors.add("Name is required.");
        if (phoneText.getText().isEmpty()) errors.add("Phone is required.");
        if (cityText.getText().isEmpty()) errors.add("City is required.");
        if (provinceChoice.getSelectionModel().isEmpty()) errors.add("Province is required.");

        // Validate Vehicle Information
        if (vehicleType.getSelectedToggle() == null) errors.add("Vehicle type is required.");
        if (vehicleAge.getSelectedToggle() == null) errors.add("Vehicle age is required.");
        if (vehiclePriceText.getText().isEmpty() || !isNumeric(vehiclePriceText.getText())) errors.add("Valid vehicle price is required.");

        // Validate Loan Information
        if (downPaymentText.getText().isEmpty() || !isNumeric(downPaymentText.getText())) errors.add("Valid down payment is required.");
        if (interestRate.getSelectedToggle() == null) errors.add("Interest rate is required.");
        if (interestRateOtherRadio.isSelected() && (interestRateOtherText.getText().isEmpty() || !isNumeric(interestRateOtherText.getText()))) {
            errors.add("Valid custom interest rate is required.");
        }
        if (paymentFrequency.getSelectedToggle() == null) errors.add("Payment frequency is required.");

        if (!errors.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, String.join("\n", errors));
            alert.showAndWait();
            return false;
        }
        return true;
    }

    /**
     * Retrieves the selected interest rate value.
     */
    private double getInterestRate() {
        if (interestRate099Radio.isSelected()) return 0.99;
        if (interestRate199Radio.isSelected()) return 1.99;
        if (interestRate299Radio.isSelected()) return 2.99;
        if (interestRateOtherRadio.isSelected()) return Double.parseDouble(interestRateOtherText.getText());
        throw new IllegalStateException("No interest rate selected"); // Should not occur due to validation
    }

    /**
     * Retrieves the selected payment frequency.
     */
    private String getPaymentFrequency() {
        if (paymentFrequencyWeeklyRadio.isSelected()) return "Weekly";
        if (paymentFrequencyBiWeeklyRadio.isSelected()) return "Bi-Weekly";
        if (paymentFrequencyMonthlyRadio.isSelected()) return "Monthly";
        throw new IllegalStateException("No payment frequency selected"); // Should not occur due to validation
    }

    /**
     * Checks if a string is a valid numeric value.
     */
    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Loads saved loan data into the form fields.
     * @param data The LoanData object containing saved values.
     */
    public void loadSavedRate(LoanData data) {
        nameText.setText(data.getName());
        phoneText.setText(data.getPhone());
        cityText.setText(data.getCity());
        provinceChoice.setValue(data.getProvince());

        if (data.getVehicleType().equals("Car")) typeCarRadio.setSelected(true);
        else if (data.getVehicleType().equals("Truck")) typeTruckRadio.setSelected(true);
        else if (data.getVehicleType().equals("Family Van")) typeFamilyVanRadio.setSelected(true);

        if (data.getVehicleAge().equals("New")) ageNewRadio.setSelected(true);
        else if (data.getVehicleAge().equals("Used")) ageUsedRadio.setSelected(true);

        vehiclePriceText.setText(String.valueOf(data.getVehiclePrice()));
        downPaymentText.setText(String.valueOf(data.getDownPayment()));

        double rate = data.getInterestRate();
        if (rate == 0.99) interestRate099Radio.setSelected(true);
        else if (rate == 1.99) interestRate199Radio.setSelected(true);
        else if (rate == 2.99) interestRate299Radio.setSelected(true);
        else {
            interestRateOtherRadio.setSelected(true);
            interestRateOtherText.setText(String.valueOf(rate));
        }

        loanDurationSlider.setValue(data.getLoanDuration());

        if (data.getPaymentFrequency().equals("Weekly")) paymentFrequencyWeeklyRadio.setSelected(true);
        else if (data.getPaymentFrequency().equals("Bi-Weekly")) paymentFrequencyBiWeeklyRadio.setSelected(true);
        else if (data.getPaymentFrequency().equals("Monthly")) paymentFrequencyMonthlyRadio.setSelected(true);
    }
}

/**
 * Data class to store all loan application information.
 */
class LoanData {
    private String name, phone, city, province, vehicleType, vehicleAge, paymentFrequency;
    private double vehiclePrice, downPayment, interestRate, loanDuration;

    public LoanData(String name, String phone, String city, String province, String vehicleType,
                    String vehicleAge, double vehiclePrice, double downPayment, double interestRate,
                    double loanDuration, String paymentFrequency) {
        this.name = name;
        this.phone = phone;
        this.city = city;
        this.province = province;
        this.vehicleType = vehicleType;
        this.vehicleAge = vehicleAge;
        this.vehiclePrice = vehiclePrice;
        this.downPayment = downPayment;
        this.interestRate = interestRate;
        this.loanDuration = loanDuration;
        this.paymentFrequency = paymentFrequency;
    }

    @Override
    public String toString() {
        return name + " | " + vehicleType + " | " + interestRate + "%";
    }

    // Getter methods
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getCity() { return city; }
    public String getProvince() { return province; }
    public String getVehicleType() { return vehicleType; }
    public String getVehicleAge() { return vehicleAge; }
    public double getVehiclePrice() { return vehiclePrice; }
    public double getDownPayment() { return downPayment; }
    public double getInterestRate() { return interestRate; }
    public double getLoanDuration() { return loanDuration; }
    public String getPaymentFrequency() { return paymentFrequency; }
}
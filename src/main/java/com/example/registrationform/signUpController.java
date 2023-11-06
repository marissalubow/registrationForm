package com.example.registrationform;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.util.function.Predicate;


/**
 * sign up controller class allows functionalities to be set for the sign up page
 */
public class signUpController {

    @FXML
    private TextField birthdateField;

    @FXML
    private TextField zipCodeField;
    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField confirmPasswordField;

    @FXML
    private Button signUp;
    @FXML
    private Label Label;
    @FXML
    private Label firstNameCheck;
    @FXML
    private Label lastNameCheck;
    @FXML
    private Label emailCheck;
    @FXML
    private Label dobCheck;
    @FXML
    private Label zipCodeCheck;
    @FXML
    private Label phoneNumberCheck;



    private final BooleanProperty firstNameValid = new SimpleBooleanProperty(false);
    private final BooleanProperty lastNameValid = new SimpleBooleanProperty(false);
    private final BooleanProperty emailValid = new SimpleBooleanProperty(false);
    private final BooleanProperty dobValid = new SimpleBooleanProperty(false);
    private final BooleanProperty zipCodeValid = new SimpleBooleanProperty(false);
    private final BooleanProperty phoneNumberValid = new SimpleBooleanProperty(false);

    /**
     * initalizing different fields with the Regex
     */
    public void initialize() {
        addValidationListener(firstNameField, firstNameValid, "^[A-Za-z]{2,25}$");
        addValidationListener(lastNameField, lastNameValid, "^[A-Za-z]{2,25}$");
        addValidationListener(emailField, emailValid, this::isValidEmail);
        addValidationListener(birthdateField, dobValid, "^(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/(19|20)\\d{2}$");
        addValidationListener(zipCodeField, zipCodeValid, "^\\d{5}$");
        addValidationListener(phoneNumberField, phoneNumberValid, "^\\d{3}-\\d{3}-\\d{4}$");

        enableAddButtonIfValid();
    }

    private void addValidationListener(TextField field, BooleanProperty property, String regex) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean isValid = newValue.matches(regex);
            setFieldStyle(field, isValid);
            property.set(isValid);
            enableAddButtonIfValid();
        });
    }

    private void addValidationListener(TextField field, BooleanProperty property, Predicate<String> validationFunction) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean isValid = validationFunction.test(newValue);
            setFieldStyle(field, isValid);
            property.set(isValid);
            enableAddButtonIfValid();
        });
    }

    private boolean isValidEmail(String email) {
        return email != null && email.toLowerCase().endsWith("@farmingdale.edu");
    }

    private void setFieldStyle(TextField field, boolean isValid) {
        if (isValid) {
            setFieldBorderColor(field, Color.LIGHTBLUE);

        } else {
            setFieldBorderColor(field, Color.RED);

        }
    }

    private void setFieldBorderColor(TextField field, Color color) {
        field.setStyle("-fx-border-color: " + toHexColor(color) + ";");
    }


    private String toHexColor(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    private void enableAddButtonIfValid() {
        signUp.disableProperty().bind(
                Bindings.not(
                        firstNameValid.and(lastNameValid).and(emailValid).and(dobValid).and(zipCodeValid).and(phoneNumberValid)
                )
        );
    }

    private void clearFieldsWithDelay() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(5), event -> clearFields())
        );
        timeline.play();
    }

    @FXML
    private void handleAddButtonClick() {
        if (allFieldsAreValid()) {
            Label.setText("Data Saved");
            Label.setVisible(true);
            clearFieldsWithDelay();
        }
    }

    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        birthdateField.clear();
        zipCodeField.clear();
        phoneNumberField.clear();
        Label.setVisible(false);
        setFieldBorderColor(firstNameField, Color.TRANSPARENT);
        setFieldBorderColor(lastNameField, Color.TRANSPARENT);
        setFieldBorderColor(emailField, Color.TRANSPARENT);
        setFieldBorderColor(birthdateField, Color.TRANSPARENT);
        setFieldBorderColor(zipCodeField, Color.TRANSPARENT);
        setFieldBorderColor(phoneNumberField, Color.TRANSPARENT);


    }

    private boolean allFieldsAreValid() {
        return firstNameValid.get() && lastNameValid.get() && emailValid.get() && dobValid.get() && zipCodeValid.get() && phoneNumberValid.get();
    }





}



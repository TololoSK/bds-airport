package org.but.feec.airport.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.but.feec.airport.api.PersonCreateView;
import org.but.feec.airport.data.PersonRepository;
import org.but.feec.airport.service.PersonService;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class PersonCreateController {
    private static final Logger logger = LoggerFactory.getLogger(PersonCreateController.class);

    @FXML
    public Button createButton;
    @FXML
    private TextField first_nameTextField;
    @FXML
    private TextField surnameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private ComboBox positionComboBox;
    
    @FXML
    private TextField salaryTextField;
    
    @FXML
    private TextField primary_account_numberTextField;

    private PersonService personService;
    private PersonRepository personRepository;
    private ValidationSupport validation;

    @FXML
    public void initialize() {
        personRepository = new PersonRepository();
        personService = new PersonService(personRepository);

        validation = new ValidationSupport();
        validation.registerValidator(first_nameTextField, Validator.createEmptyValidator("Cell first name must not be empty."));
        validation.registerValidator(surnameTextField, Validator.createEmptyValidator("Cell surname must not be empty."));
        validation.registerValidator(emailTextField, Validator.createEmptyValidator("Cell email must not be empty."));
        validation.registerValidator(passwordTextField, Validator.createEmptyValidator("Cell password must not be empty."));
        validation.registerValidator(positionComboBox, Validator.createEmptyValidator("Position must be selected."));
   
        createButton.disableProperty().bind(validation.invalidProperty());

        logger.info("PersonCreateController initialized");
    }

    @FXML
    void handleCreateNewPerson(ActionEvent event) {
        String password = passwordTextField.getText();
        String first_name = first_nameTextField.getText();
        String surname = surnameTextField.getText();
        String email = emailTextField.getText();
        String position = positionComboBox.getValue().toString();
        String salary = salaryTextField.getText();
        String primary_account_number = primary_account_numberTextField.getText();


        PersonCreateView personCreateView = new PersonCreateView();
        personCreateView.setPassword(password);
        personCreateView.setFirst_name(first_name);
        personCreateView.setSurname(surname);
        personCreateView.setEmail(email);
        personCreateView.setPosition(position);
        personCreateView.setSalary(salary);
        personCreateView.setPrimary_account_number(primary_account_number);

        personService.createPerson(personCreateView);

        personCreatedConfirmationDialog();
    }

    private void personCreatedConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Person Created Confirmation");
        alert.setHeaderText("Your person was successfully created.");

        Timeline idlestage = new Timeline(new KeyFrame(Duration.seconds(3), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                alert.setResult(ButtonType.CANCEL);
                alert.hide();
            }
        }));
        idlestage.setCycleCount(1);
        idlestage.play();
        Optional<ButtonType> result = alert.showAndWait();
    }

}


package org.but.feec.airport.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    private TextField newUsername;
    @FXML
    private TextField newName;

    @FXML
    private TextField newSurname;

    @FXML
    private TextField newCheck;

    @FXML
    private TextField newPassword;

    private PersonService personService;
    private PersonRepository personRepository;
    private ValidationSupport validation;

    @FXML
    public void initialize() {
        personRepository = new PersonRepository();
        personService = new PersonService(personRepository);

        validation = new ValidationSupport();
        validation.registerValidator(newUsername, Validator.createEmptyValidator("Cell username must not be empty."));
        validation.registerValidator(newName, Validator.createEmptyValidator("Cell name must not be empty."));
        validation.registerValidator(newSurname, Validator.createEmptyValidator("Cell surname must not be empty."));
        validation.registerValidator(newCheck, Validator.createEmptyValidator("Cell security check results must not be empty."));
        validation.registerValidator(newPassword, Validator.createEmptyValidator("Cell password must not be empty."));

        createButton.disableProperty().bind(validation.invalidProperty());

        logger.info("PersonCreateController initialized");
    }

    @FXML
    void handleCreateNewPerson(ActionEvent event) {
        String password = newPassword.getText();
        String first_name = newName.getText();
        String last_name = newSurname.getText();
        String security_check = newCheck.getText();
        String username = newUsername.getText();


        PersonCreateView personCreateView = new PersonCreateView();
        personCreateView.setPassword(password.toCharArray());
        personCreateView.setFirst_name(first_name);
        personCreateView.setLast_name(last_name);
        personCreateView.setSecurity_check(security_check);
        personCreateView.setUsername(username);


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


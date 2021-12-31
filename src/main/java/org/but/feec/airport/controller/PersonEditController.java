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
import javafx.stage.Stage;
import javafx.util.Duration;
import org.but.feec.airport.api.PersonBasicView;
import org.but.feec.airport.api.PersonEditView;
import org.but.feec.airport.data.PersonRepository;
import org.but.feec.airport.service.PersonService;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;

public class PersonEditController {
    private static final Logger logger = LoggerFactory.getLogger(PersonEditController.class);

    @FXML
    public Button editButton;
    @FXML
    public TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private ComboBox<String> positionComboBox; //toto
    @FXML
    private TextField idTextField;

    private PersonService personService;
    private PersonRepository personRepository;
    private ValidationSupport validation;
    public Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        personRepository = new PersonRepository();
        personService = new PersonService(personRepository);

        validation = new ValidationSupport();
        validation.registerValidator(nameTextField, Validator.createEmptyValidator("Cell name must not be empty."));
        validation.registerValidator(surnameTextField, Validator.createEmptyValidator("Cell surname must not be empty."));
        validation.registerValidator(emailTextField, Validator.createEmptyValidator("Cell email must not be empty."));
        validation.registerValidator(positionComboBox, Validator.createEmptyValidator("Cell position must not be empty.")); //toto

        editButton.disableProperty().bind(validation.invalidProperty());

        loadPersonsData();

        logger.info("PersonsEditController initialized");
    }

    /**
     * Load passed data from Persons controller. Check this tutorial explaining how to pass the data between controllers: https://dev.to/devtony101/javafx-3-ways-of-passing-information-between-scenes-1bm8
     */
    private void loadPersonsData() {
        Stage stage = this.stage;
        if (stage.getUserData() instanceof PersonBasicView) {
            PersonBasicView personBasicView = (PersonBasicView) stage.getUserData();
            emailTextField.setText(personBasicView.getEmail());
            idTextField.setText(String.valueOf(personBasicView.getId_employee()));
            surnameTextField.setText(personBasicView.getSurname());
            nameTextField.setText(personBasicView.getFirst_name());
            positionComboBox.setValue(personBasicView.getPosition());
            System.out.println(personBasicView.getPosition());
        }
    }

    @FXML
    public void handleRemovePersonButton(ActionEvent event) {
    	Long id = Long.valueOf(idTextField.getText());
    	PersonEditView personEditView = new PersonEditView();
        personEditView.setId(id);
        
        personService.removePerson(personEditView);
        
        personEditedConfirmationDialog();
    }
    
    
    @FXML
    public void handleEditPersonButton(ActionEvent event) {
        String email = emailTextField.getText();
        String surname = surnameTextField.getText();
        String first_name = nameTextField.getText();
        String position = positionComboBox.getValue().toString(); //toto
        Long id = Long.valueOf(idTextField.getText());

        PersonEditView personEditView = new PersonEditView();
        personEditView.setId(id);
        personEditView.setEmail(email);
        personEditView.setFirst_name(first_name);
        personEditView.setSurname(surname);
        personEditView.setPosition(position);	//toto

        personService.editPerson(personEditView);

        personEditedConfirmationDialog();
    }

    private void personEditedConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Person Edited Confirmation");
        alert.setHeaderText("Your person was successfully edited.");

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

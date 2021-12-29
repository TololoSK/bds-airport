package org.but.feec.airport.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.but.feec.airport.api.PersonDetailView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonDetailController {
    private static final Logger logger = LoggerFactory.getLogger(PersonDetailController.class);

    @FXML
    private TextField idTextField;

    @FXML
    private TextField first_nameTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField positionTextField;

    @FXML
    private TextField salaryTextField;

    @FXML
    private TextField primary_account_numberTextField;

    // used to reference the stage and to get passed data through it
    public Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        idTextField.setEditable(false);
        first_nameTextField.setEditable(false);
        surnameTextField.setEditable(false);
        emailTextField.setEditable(false);
        positionTextField.setEditable(false);
        salaryTextField.setEditable(false);
        primary_account_numberTextField.setEditable(false);


        loadPersonsData();

        logger.info("PersonsDetailController initialized");
    }

    private void loadPersonsData() {
        Stage stage = this.stage;
        if (stage.getUserData() instanceof PersonDetailView) {
            PersonDetailView personBasicView = (PersonDetailView) stage.getUserData();
            idTextField.setText(String.valueOf(personBasicView.getId_employee()));
            first_nameTextField.setText(personBasicView.getFirst_name());
            surnameTextField.setText(personBasicView.getSurname());
            emailTextField.setText(personBasicView.getEmail());
            positionTextField.setText(personBasicView.getPosition());
            salaryTextField.setText(personBasicView.getSalary());
            primary_account_numberTextField.setText(personBasicView.getPrimary_account_number());
        }
    }
}

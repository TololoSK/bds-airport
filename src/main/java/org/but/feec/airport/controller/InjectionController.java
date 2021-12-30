package org.but.feec.airport.controller;

import java.io.IOException;

import org.but.feec.airport.App;
import org.but.feec.airport.api.InjectionView;
import org.but.feec.airport.data.PersonRepository;
import org.but.feec.airport.exceptions.ExceptionHandler;
import org.but.feec.airport.service.PersonService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InjectionController {
	@FXML
    private Button backButton;
    @FXML
    private TextField injectionTextField;
    
    private PersonService personService;
    private PersonRepository personRepository;
    
	public InjectionController() {
    }

    @FXML
    private void initialize() {
    	personRepository = new PersonRepository();
        personService = new PersonService(personRepository);
       
    }
    
    public void handleInsertButton(ActionEvent event) {
    	String text = injectionTextField.getText();
    	
    	InjectionView injectionView = new InjectionView();
    	injectionView.setText(text);

    	personService.injection(injectionView);
    }
    
    public void handleBackButton(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("fxml/Persons.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1050, 600);
            Stage stage = new Stage();
            stage.setTitle("Airport Management App");
            stage.setScene(scene);

            Stage stageOld = (Stage) backButton.getScene().getWindow();
            stageOld.close();

            stage.show();
        } catch (IOException ex) {
           ExceptionHandler.handleException(ex);
        }
    }
}

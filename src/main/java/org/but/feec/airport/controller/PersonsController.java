package org.but.feec.airport.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.but.feec.airport.App;
import org.but.feec.airport.api.PersonBasicView;
import org.but.feec.airport.api.PersonDetailView;
import org.but.feec.airport.api.PersonEditView;
import org.but.feec.airport.data.PersonRepository;
import org.but.feec.airport.exceptions.ExceptionHandler;
import org.but.feec.airport.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class PersonsController
{ private static final Logger logger = LoggerFactory.getLogger(PersonsController.class);

	@FXML
	public TextField searchTextField;
    @FXML
    public Button addPassengerButton;
    @FXML
    public Button refreshButton;
    @FXML
    public Button showInjectionButton;
    @FXML
    private TableColumn<PersonBasicView, Long> id_employee;
    @FXML
    private TableColumn<PersonBasicView, String> first_name;
    @FXML
    private TableColumn<PersonBasicView, String> surname;
    @FXML
    private TableColumn<PersonBasicView, String> email;
    @FXML
    private TableView<PersonBasicView> systemPassengerTableView;

    private PersonService personService;
    private PersonRepository personRepository;

    public PersonsController() {
    }

    @FXML
    private void initialize() {
        personRepository = new PersonRepository();
        personService = new PersonService(personRepository);
        id_employee.setCellValueFactory(new PropertyValueFactory<PersonBasicView, Long>("id_employee"));
        first_name.setCellValueFactory(new PropertyValueFactory<PersonBasicView, String>("first_name"));
        surname.setCellValueFactory(new PropertyValueFactory<PersonBasicView, String>("surname"));
        email.setCellValueFactory(new PropertyValueFactory<PersonBasicView, String>("email"));


        ObservableList<PersonBasicView> observablePersonsList = initializePersonsData();

        systemPassengerTableView.setItems(observablePersonsList);

        systemPassengerTableView.getSortOrder().add(id_employee);

        initializeTableViewSelection();

        logger.info("PersonsController initialized");
    }

    private void initializeTableViewSelection() {
        MenuItem edit = new MenuItem("Edit employee");
        MenuItem detailedView = new MenuItem(" Employee detailed view");
        edit.setOnAction((ActionEvent event) -> {
            PersonBasicView personView = systemPassengerTableView.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("fxml/PersonEdit.fxml"));
                Stage stage = new Stage();
                stage.setUserData(personView);
                stage.setTitle("Edit Employee");

                PersonEditController controller = new PersonEditController();
                controller.setStage(stage);
                fxmlLoader.setController(controller);

                Scene scene = new Scene(fxmlLoader.load(), 600, 500);

                stage.setScene(scene);

                stage.show();
            } catch (IOException ex) {
                ExceptionHandler.handleException(ex);
            }
        });

        detailedView.setOnAction((ActionEvent event) -> {
            PersonBasicView personView = systemPassengerTableView.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("fxml/PersonDetail.fxml"));
                Stage stage = new Stage();

                Long personId = personView.getId_employee();
                PersonDetailView personDetailView = personService.getPersonDetailView(personId);

                stage.setUserData(personDetailView);
                stage.setTitle("Employee Detailed View");

                PersonDetailController controller = new PersonDetailController();
                controller.setStage(stage);
                fxmlLoader.setController(controller);

                Scene scene = new Scene(fxmlLoader.load(), 600, 500);

                stage.setScene(scene);

                stage.show();
            } catch (IOException ex) {
                ExceptionHandler.handleException(ex);
            }
        });


        ContextMenu menu = new ContextMenu();
        menu.getItems().add(edit);
        menu.getItems().addAll(detailedView);
        systemPassengerTableView.setContextMenu(menu);
    }

    private ObservableList<PersonBasicView> initializePersonsData() {
        List<PersonBasicView> persons = personService.getPersonBasicView("");
        return FXCollections.observableArrayList(persons);
    }


    public void handleExitMenuItem(ActionEvent event) {
        System.exit(0);
    }
    
    public void handleSearchButton(ActionEvent event) {
    	String searchString = searchTextField.getText();

        List<PersonBasicView> persons = personService.getPersonBasicView(searchString);
        systemPassengerTableView.setItems(FXCollections.observableArrayList(persons));
        systemPassengerTableView.refresh();
        systemPassengerTableView.sort();
        
    }

    public void handleAddPassenger(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("fxml/PersonCreate.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 500);
            Stage stage = new Stage();
            stage.setTitle("New Employee");
            stage.setScene(scene);



            stage.show();
        } catch (IOException ex) {
            ExceptionHandler.handleException(ex);
        }
    }
    
    public void handleShowInjectionButton() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("fxml/Injection.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1050, 600);
            Stage stage = new Stage();
            stage.setTitle("Airport Management App");
            stage.setScene(scene);

            Stage stageOld = (Stage) showInjectionButton.getScene().getWindow();
            stageOld.close();

            stage.show();
        } catch (IOException ex) {
           ExceptionHandler.handleException(ex);
        }
    }

    public void handleRefreshButton(ActionEvent actionEvent) {
        ObservableList<PersonBasicView> observablePersonsList = initializePersonsData();
        systemPassengerTableView.setItems(observablePersonsList);
        systemPassengerTableView.refresh();
        systemPassengerTableView.sort();
    }
}

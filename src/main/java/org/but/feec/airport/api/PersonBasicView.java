package org.but.feec.airport.api;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PersonBasicView {
    private LongProperty id_employee = new SimpleLongProperty();
    StringProperty first_name= new SimpleStringProperty();
    StringProperty surname = new SimpleStringProperty();
    StringProperty email = new SimpleStringProperty();
    StringProperty position = new SimpleStringProperty();

    public String getPosition() {
		return positionProperty().get();
	}

	public void setPosition(String position) {
		this.positionProperty().setValue(position);
	}

	public Long getId_employee() {
        return id_employeeProperty().get();
    }

    public void setId_employee(Long id) {
        this.id_employeeProperty().setValue(id);
    }
    public String getFirst_name() {
        return first_nameProperty().get();
    }
    public void setFirst_name(String first_name) {
        this.first_nameProperty().setValue(first_name);
    }
    public String getSurname() {
        return surnameProperty().get();
    }
    public void setSurname(String surname) {
        this.surnameProperty().setValue(surname);
    }
    public String getEmail() {
        return emailProperty().get();
    }
    public void setEmail(String email) {
        this.emailProperty().setValue(email);
    }
    
    public LongProperty id_employeeProperty() {
        return id_employee;
    }
    public StringProperty first_nameProperty() {
        return first_name;
    }
    public StringProperty surnameProperty() {
        return surname;
    }
    public StringProperty emailProperty() {
        return email;
    }
    public StringProperty positionProperty() {
        return position;
    }
}
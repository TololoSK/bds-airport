package org.but.feec.airport.api;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PersonDetailView {
    private LongProperty id_employee = new SimpleLongProperty();
    private StringProperty first_name = new SimpleStringProperty();
    private StringProperty surname = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();
    private StringProperty position = new SimpleStringProperty();
    private StringProperty salary = new SimpleStringProperty();
    private StringProperty primary_account_number = new SimpleStringProperty();

    public Long getId_employee() {
        return id_employeeProperty().get();
    }

    public void setId_employee(Long id_employee) {
        this.id_employeeProperty().setValue(id_employee);
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

    public void setSurname(String last_name) {
        this.surnameProperty().setValue(last_name);
    }

    public String getEmail(){ 
    	return emailProperty().get();
    }
    
    public void setEmail(String email) {
        this.emailProperty().setValue(email);
    }

    public String getPosition() {
        return positionProperty().get();
    }

    public void setPosition(String position) {
        this.positionProperty().set(position);
    }

    public String getSalary() {
        return salaryProperty().get();
    }

    public void setSalary(String salary) {
    	System.out.println(salary);
        this.salaryProperty().setValue(salary);
    }

    public String getPrimary_account_number() {
        return primary_account_numberProperty().get();
    }

    public void setPrimary_account_number(String primary_account_number) {
    	System.out.println(primary_account_number);
        this.primary_account_numberProperty().setValue(primary_account_number);
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

    public StringProperty salaryProperty() {
        return salary;
    }

    public StringProperty primary_account_numberProperty() {
        return primary_account_number;
    }




}

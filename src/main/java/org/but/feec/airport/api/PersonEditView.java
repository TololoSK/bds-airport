package org.but.feec.airport.api;



public class PersonEditView {
    private Long id;
    private String first_name;
    private String surname;
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getFirst_name() {
        return first_name;
    }
    public void setFirst_name(String first_name) {this.first_name=first_name;}
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {this.surname=surname;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email=email;}
    
    @Override
    public String toString() {
        return "PersonCreateView{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + surname + '\'' +
                ", email='" + email + '\'' +
                 '\''+
                '}';
    }
}

package org.but.feec.airport.api;

public class PersonAuthView {
    private String username;
    private String password;
    public String getUsername (){ return username;}
    public void setUsername (String username) { this.username=username;}
    public String getPassword(){ return password;}
    public void setPassword (String password) { this.password=password;}
    @Override
    public String toString() {
        return "PersonAuthView{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
}}

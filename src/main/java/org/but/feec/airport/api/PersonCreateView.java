package org.but.feec.airport.api;
public class PersonCreateView {

    private String first_name;
    private String surname;
    private String email;
    private String position;
    private String salary;
    private String primary_account_number;
    private String password;
    
    public String getFirst_name() {
        return first_name;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname){
    	this.surname=surname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
    	this.email=email;
	}
    public String getPosition() {
    	return position;
    }
    public void setPosition(String position) {
    	this.position = position;
    }
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getPrimary_account_number() {
		return primary_account_number;
	}
	public void setPrimary_account_number(String primary_account_number) {
		this.primary_account_number = primary_account_number;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	 @Override
	    public String toString() {
	        return "PersonCreateView{" +
	                ", first_name='" + first_name + '\'' +
	                ", last_name='" + surname + '\'' +
	                ", check_results='" + email + '\'' +
	                ", password=" + password + '\'' +
	                ", position=" + position + '\'' +
	                ", salary=" + salary + '\'' +
	                ", primary_account_number=" + primary_account_number +
	                '}';
	    }
}

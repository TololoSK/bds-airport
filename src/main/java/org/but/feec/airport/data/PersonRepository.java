package org.but.feec.airport.data;

import org.but.feec.airport.api.*;
import org.but.feec.airport.config.DataSourceConfig;
import org.but.feec.airport.exceptions.DataAccessException;

import java.sql.PreparedStatement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonRepository {
    public PersonAuthView findPersonByEmail(String email) {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT email, password" +
                             " FROM employee" +
                             " WHERE email = ?")
        ) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToPersonAuth(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find person by ID with addresses failed.", e);
        }
        return null;
    }
    private PersonAuthView mapToPersonAuth(ResultSet rs) throws SQLException {
       PersonAuthView person = new PersonAuthView();
       person.setUsername(rs.getString("email"));
       person.setPassword(rs.getString("password"));
       return person;
    }
    public PersonDetailView findPersonDetailedView(Long personId) {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT employee.id_employee, employee.first_name, employee.surname, employee.email, employee_type.position, salary.salary, salary.primary_account_number"
                     + " FROM employee"
                     + " LEFT JOIN employee_type ON employee.id_employee_type = employee_type.id_employee_type"
                     + " LEFT JOIN salary ON employee.id_employee = salary.id_employee"
                     + " WHERE employee.id_employee = ?")
        ) {
            preparedStatement.setLong(1, personId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToPersonDetailView(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find person by ID with addresses failed.", e);
        }
        return null;
    }

    public List<PersonBasicView> getPersonBasicView(String search) {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id_employee, first_name, surname, email" + 
            		 								" FROM employee WHERE surname LIKE ?")) {
        	preparedStatement.setString(1, "%" + search + "%");
        	ResultSet resultSet = preparedStatement.executeQuery();
            List<PersonBasicView> personBasicViews = new ArrayList<>();
            while (resultSet.next()) {
                personBasicViews.add(mapToPersonBasicView(resultSet));
            }
            System.out.println(personBasicViews.size());
            return personBasicViews;
        } catch (SQLException e) {
            throw new DataAccessException("Persons basic view could not be loaded.", e);
        }
    }
    
    public void createPerson(PersonCreateView personCreateView) {
        String insertEmployeeSQL = "INSERT INTO employee (first_name, surname, email, password, id_employee_type) VALUES (?,?,?,?, (SELECT id_employee_type FROM employee_type WHERE position = ?))";
        String insertSalarySQL = "INSERT INTO salary (salary, paid_at, primary_account_number, id_employee) VALUES (?,CURRENT_DATE,?,?)";
        try (Connection connection = DataSourceConfig.getConnection();
             // would be beneficial if I will return the created entity back
             PreparedStatement preparedStatement = connection.prepareStatement(insertEmployeeSQL, Statement.RETURN_GENERATED_KEYS)) {
            // set prepared statement variables
            preparedStatement.setString(1, personCreateView.getFirst_name());
            preparedStatement.setString(2, personCreateView.getSurname());
            preparedStatement.setString(3, personCreateView.getEmail());
            preparedStatement.setString(4, personCreateView.getPassword());
            preparedStatement.setString(5, personCreateView.getPosition());

            System.out.println(personCreateView.getPassword());
            System.out.println(personCreateView.getPosition());

            int affectedRows = preparedStatement.executeUpdate();
            long insertedID = 0;
           
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    insertedID = resultSet.getLong("id_employee");
                }
            }
            
            if (affectedRows == 0) {
                throw new DataAccessException("Creating person failed, no rows affected.");
            }

            try (PreparedStatement ps = connection.prepareStatement(insertSalarySQL, Statement.RETURN_GENERATED_KEYS)) {
            	System.out.println(personCreateView.getSalary());
                ps.setDouble(1, Double.parseDouble(personCreateView.getSalary()));
                ps.setString(2, personCreateView.getPrimary_account_number());
                ps.setLong(3, insertedID);
                
                affectedRows = ps.executeUpdate();

                if (affectedRows == 0) {
                    throw new DataAccessException("Creating salary failed, no rows affected.");
                }
            } catch (SQLException e) {
            	e.printStackTrace();
                throw new DataAccessException("This salary for edit does not exist.");
            }  
            
        } catch (SQLException e) {
        	e.printStackTrace();
            throw new DataAccessException("Creating person failed operation on the database failed.");
        }
    }
        
    public void removePerson(PersonEditView personEditView) {
    	String removePersonSQL = "DELETE FROM employee WHERE id_employee=?";
    	
    	try (Connection connection = DataSourceConfig.getConnection();
               PreparedStatement preparedStatement = connection.prepareStatement(removePersonSQL, Statement.RETURN_GENERATED_KEYS)) {
               // set prepared statement variables
               preparedStatement.setLong(1, personEditView.getId());
               
               int affectedRows = preparedStatement.executeUpdate();

               if (affectedRows == 0) {
                   throw new DataAccessException("Creating salary failed, no rows affected.");
               }
           } catch (SQLException e) {
        	   e.printStackTrace();
               throw new DataAccessException("Remove person failed operation on the database failed.");
           }
    }

    public void editPerson(PersonEditView personEditView) {
        String insertPersonSQL = "UPDATE employee SET first_name = ?, surname = ?, email = ? WHERE id_employee= ?";
        String checkIfExists = "SELECT id_employee FROM employee WHERE id_employee = ?";
        try (Connection connection = DataSourceConfig.getConnection();
             // would be beneficial if I will return the created entity back
             PreparedStatement preparedStatement = connection.prepareStatement(insertPersonSQL, Statement.RETURN_GENERATED_KEYS)) {
            // set prepared statement variables
            preparedStatement.setString(1, personEditView.getFirst_name());
            preparedStatement.setString(2, personEditView.getSurname());
            preparedStatement.setString(3, personEditView.getEmail());
            preparedStatement.setLong(4, personEditView.getId());

            try {
            connection.setAutoCommit(false);
                try (PreparedStatement ps = connection.prepareStatement(checkIfExists, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setLong(1, personEditView.getId());
                    ps.execute();
                } catch (SQLException e) {
                	e.printStackTrace();
                    throw new DataAccessException("This person for edit does not exist.");
                }

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows == 0) {
                    throw new DataAccessException("Creating person failed, no rows affected.");
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            } finally {
            connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Creating person failed operation on the database failed.");
        }
    }


    private PersonBasicView mapToPersonBasicView(ResultSet rs) throws SQLException {
        PersonBasicView personBasicView = new PersonBasicView();
        personBasicView.setId_employee(rs.getLong("id_employee"));
        personBasicView.setFirst_name(rs.getString("first_name"));
        personBasicView.setSurname(rs.getString("surname"));
        personBasicView.setEmail(rs.getString("email"));
        //personBasicView.setCountry_of_residence(rs.getString("country_of_residence"));
        return personBasicView;
    }

    private PersonDetailView mapToPersonDetailView(ResultSet rs) throws SQLException {
        PersonDetailView personDetailView = new PersonDetailView();
        personDetailView.setId_employee(rs.getLong("id_employee"));
        personDetailView.setFirst_name(rs.getString("first_name"));
        personDetailView.setSurname(rs.getString("surname"));
        personDetailView.setEmail(rs.getString("email"));
        personDetailView.setPosition(rs.getString("position"));
        personDetailView.setSalary(rs.getString("salary"));
        personDetailView.setPrimary_account_number(rs.getString("primary_account_number"));
        return personDetailView;
    }

}



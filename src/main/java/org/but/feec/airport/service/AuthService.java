package org.but.feec.airport.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.but.feec.airport.api.PersonAuthView;
import org.but.feec.airport.data.PersonRepository;
import org.but.feec.airport.exceptions.ResourceNotFoundException;

public class AuthService {
    private PersonRepository personRepository;
    
    public AuthService(PersonRepository personRepository ){ 
    	this.personRepository=personRepository;
    }
    
    private PersonAuthView findPersonByEmail(String email) {
        return personRepository.findPersonByEmail(email);
    }
    
    public boolean authenticate(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }
        PersonAuthView personAuthView = findPersonByEmail(username);
        if (personAuthView == null) {
            throw new ResourceNotFoundException("Username not found!");
        }

        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), personAuthView.getPassword());
        
        
        return result.verified;
}}

package org.but.feec.airport.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.but.feec.airport.api.PersonBasicView;
import org.but.feec.airport.api.PersonCreateView;
import org.but.feec.airport.api.PersonDetailView;
import org.but.feec.airport.api.PersonEditView;
import org.but.feec.airport.data.PersonRepository;

import java.util.List;

public class PersonService {
    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public PersonDetailView getPersonDetailView(Long id) {
        return personRepository.findPersonDetailedView(id);
    }

    public List<PersonBasicView> getPersonBasicView() {
        return personRepository.getPersonBasicView();
    }


        public void createPerson(PersonCreateView personCreateView) {
            char[] originalPassword = personCreateView.getPassword();
            char[] hashedPassword = hashPassword(originalPassword);
            personCreateView.setPassword(hashedPassword);

            personRepository.createPerson(personCreateView);
        }
        public void editPerson(PersonEditView personEditView) {
            personRepository.editPerson(personEditView);
        }
        private char[] hashPassword(char[] password) {
            return BCrypt.withDefaults().hashToChar(12, password);
        }

    }



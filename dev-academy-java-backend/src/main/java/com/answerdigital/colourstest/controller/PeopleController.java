package com.answerdigital.colourstest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.answerdigital.colourstest.dto.PersonUpdateDTO;
import com.answerdigital.colourstest.model.Person;
import com.answerdigital.colourstest.repository.PeopleRepository;

@RestController
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PeopleRepository peopleRepository;

    @GetMapping
    public ResponseEntity<List<Person>> getPeople() {
        // TODO STEP 1
        //
        // Implement a JSON endpoint that returns the full list
        // of people from the PeopleRepository. If there are zero
        // people returned from PeopleRepository then an empty
        // JSON array should be returned.
    	
        return new ResponseEntity<List<Person>>(peopleRepository.findAll(),  HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable("id") long id) {
        // TODO: Step 2
        //
        // Implement a JSON endpoint that returns a single person
        // from the PeopleRepository based on the id parameter.
        // If null is returned from the PeopleRepository with
        // the supplied id then a NotFound should be returned.
    	
    	Optional<Person> person = peopleRepository.findById(id);
    	
    	if (person.isPresent()) {
    		return new ResponseEntity(person, HttpStatus.OK);		
    	}
    	return new ResponseEntity("[]", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable("id") Long id, @RequestBody PersonUpdateDTO personUpdate) {
        // TODO STEP 3
        //
        // Implement an endpoint that recieves a JSON object to
        // update a person using the PeopleRepository based on
        // the id parameter. Once the person has been sucessfullly
        // updated, the person should be returned from the endpoint.
        // If null is returned from the PeopleRepository then a
        // NotFound should be returned.
    	
        Optional<Person> person = peopleRepository.findById(id);
        
        if (person.isPresent()) {
        	if (!person.equals(personUpdate)) {
        		person.ifPresent(x -> x.setAuthorised(personUpdate.isAuthorised()));
        		person.ifPresent(x -> x.setEnabled(personUpdate.isEnabled()));
        		person.ifPresent(x -> x.setColours(personUpdate.getColours()));
        		person.ifPresent(x -> peopleRepository.save(x));
        		
        		return new ResponseEntity(person, HttpStatus.OK);
        	}else {
        		return new ResponseEntity(person, HttpStatus.OK);
        	}
        }
        
        return new ResponseEntity("[]", HttpStatus.NOT_FOUND);
    }
    
    @PostMapping
    public ResponseEntity<Person> setPerson(@RequestBody Person person) {
    	
    	if(person != null) {
    		Person newPerson = peopleRepository.save(person);
    		return new ResponseEntity(newPerson, HttpStatus.OK);
    	}
    	
    	return new ResponseEntity("[]", HttpStatus.BAD_REQUEST);
    }
}

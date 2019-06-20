package com.answerdigital.colourstest.controller;

import com.answerdigital.colourstest.model.Colour;
import com.answerdigital.colourstest.repository.ColoursRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/colours")
public class ColoursController {

    @Autowired
    private ColoursRepository coloursRepository;

    @GetMapping
    public ResponseEntity<List<Colour>> getColours() {
        return new ResponseEntity(coloursRepository.findAll(), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Colour> getColour(@PathVariable("id") long id) {
    	
    	Optional<Colour> colour = coloursRepository.findById(id);
    	
    	if (colour.isPresent()) {
    		return new ResponseEntity(colour, HttpStatus.OK);		
    	}
    	return new ResponseEntity("[]", HttpStatus.NOT_FOUND);
    }
    
    @PostMapping
    public ResponseEntity<Colour> setColour(@RequestBody Colour colour) {
    	
    	if(colour != null) {
        	Colour newColour = coloursRepository.save(colour);
        	return new ResponseEntity(newColour, HttpStatus.OK);
        }
        
    	return new ResponseEntity("[]", HttpStatus.BAD_REQUEST);
       
    }
    
}

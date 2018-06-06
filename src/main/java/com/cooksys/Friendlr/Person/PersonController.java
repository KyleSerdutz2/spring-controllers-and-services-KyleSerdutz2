package com.cooksys.Friendlr.Person;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.custom.Exception.IdNotAvailableException;

@RestController
@RequestMapping("person")
public class PersonController {
	private PersonService personService;
	private PersonMapper personMapper;
	
	public PersonController(PersonService personService, PersonMapper personMapper) {
		this.personService = personService;
		this.personMapper = personMapper;
	}
	
	//-----------------------
	//=======================
	
	//[GET][x]
	//.../person
	@GetMapping
	public List<PersonDto> getEveryone() {
		//Logic is small enough it can be in controller instead of service
		return personService.getPeople().stream().map(personMapper::toDto).collect(Collectors.toList());
	}
	
	//[GET][x]
	//-----------------------
	//Client << Server's Body
	//-----------------------
	//.../person/{id}
	@GetMapping("{id}")
	public PersonDto getPerson(@PathVariable Long id) {
		//Logic is small enough it can be in controller instead of service
		for(Person person : personService.getPeople()) {
			if(person.getId() == id)
				return personMapper.toDto(person);
		}
		return null;
	}
	
	//[GET][x]
	//-----------------------
	//Client << Server's Body
	//-----------------------
	//.../person/{id}/friends
	@GetMapping("{id}/friends")
	public List<PersonDto> getPersonsFriends(@PathVariable Long id) {
		//Logic is small enough it can be in controller instead of service
		for(Person person : personService.getPeople()) {
			if(person.getId() == id) {
				return person.getFriends().stream().map(personMapper::toDto).collect(Collectors.toList());
			}
		}
		return null;
	}
	
	//[POST][X]
	//-----------------------
	//Client's Body >> Server
	//-----------------------
	//.../person
	@PostMapping
	public ResponseEntity<PersonDto> addPerson(@RequestBody PersonDto personDto) throws IdNotAvailableException {
		if(personService.addPerson(personDto, personMapper))
			return new ResponseEntity<>(HttpStatus.CREATED); //201
		else
			throw new IdNotAvailableException(personDto.getId()); //404
	}
	
	//[PUT][X]
	//-----------------------
	//Client's Body >> Server
	//-----------------------
	//.../person/{id}
	@PutMapping("{id}")
	public ResponseEntity<PersonDto> updatePerson(@PathVariable Long id, @RequestBody PersonDto personDto) throws IdNotAvailableException {
		if(personService.updatePerson(id, personDto, personMapper))
			return new ResponseEntity<>(HttpStatus.OK); //200
		else
			throw new IdNotAvailableException(personDto.getId()); //404
	}
	
	//[DELETE][X]
	//.../person/{id}
	@DeleteMapping("{id}")
	public ResponseEntity<Object> removePerson(@PathVariable Long id) throws IdNotAvailableException {
		if(personService.removePerson(id))
			return new ResponseEntity<>(HttpStatus.CREATED); //201
		
		return null; //404
	}
}
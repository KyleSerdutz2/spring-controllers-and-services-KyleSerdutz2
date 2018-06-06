package com.cooksys.Friendlr.Person;

import org.springframework.stereotype.Service;

import com.cooksys.custom.Exception.IdNotAvailableException;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {
	private List<Person> people = new ArrayList<Person>();

	public PersonService() {
		people.add(new Person("Jim", "Boe"));
		people.add(new Person("Tim", "Sho"));
		people.add(new Person("Lim", "Glo"));
		people.add(new Person("Kim", "Moe"));
		
		//Jim friends with: (Tim, Lim, Kim)
		people.get(0).getFriends().add(people.get(1));
		people.get(0).getFriends().add(people.get(2));
		people.get(0).getFriends().add(people.get(3));
		//Tim friends with: (Jim, Lim, Kim)
		people.get(1).getFriends().add(people.get(0));
		people.get(1).getFriends().add(people.get(2));
		people.get(1).getFriends().add(people.get(3));
		//Lim friends with: (Kim)
		people.get(2).getFriends().add(people.get(3));
		//Kim friends with: (Jim)
		people.get(3).getFriends().add(people.get(0));
	}
	
	public List<Person> getPeople() {
		return people;
	}

	public void setPeople(List<Person> people) {
		this.people = people;
	}
	
	public boolean addPerson(PersonDto personDto, PersonMapper personMapper) throws IdNotAvailableException {
		if(personDto.getId() == null) {
			Person person = personMapper.fromDto(personDto);
			person.setId(); 
			people.add(person);//Auto-increments
		} else if (personDto.getId() > 0) {
			for(Person ourPerson: people) {
				if(personDto.getId() == ourPerson.getId()) {
					throw new IdNotAvailableException(personDto.getId());
				}
			}
			Person person = personMapper.fromDto(personDto);
			person.setId(); 
			people.add(person);
		} else
			throw new IdNotAvailableException(personDto.getId());
		return true;
	}

	public boolean updatePerson(Long id, PersonDto personDto, PersonMapper personMapper) throws IdNotAvailableException {
		if(id == null  || id == 0 || id < 0) {
			throw new IdNotAvailableException(id);
		} else if (id > 0) {
			boolean findMatch = false;
			for(Person ourPerson: people) {
				if(id == ourPerson.getId()) {
					Person person = personMapper.fromDto(personDto);
					
					//Save stuff so it doesn't overwrite 100%
					//(Won't override friends for example)
					if(person.getId() != null) {
						boolean idConflict = false; //You can change id if it doesn't conflict or below 0
						for(Person ourPerson2: people) {
							if(ourPerson2.getId() == person.getId())
								idConflict = true;
						}
						if(!idConflict && id != null && id > 0)
							ourPerson.setId(person.getId());
					}
					if(person.getFirstName() != null)
						ourPerson.setFirstName(person.getFirstName());
					if(person.getLastName() != null)
						ourPerson.setLastName(person.getLastName());
					
					findMatch = true;
				}
			}

			if(!findMatch)
				throw new IdNotAvailableException(personDto.getId());
		}
		return true;
	}
	
	public boolean removePerson(Long id) throws IdNotAvailableException {
		if(id == null  || id == 0 || id < 0) {
			throw new IdNotAvailableException(id);
		} else if (id > 0) {
			boolean findMatch = false;
			for(Person ourPerson: people) {
				if(id == ourPerson.getId()) {
					findMatch = true;
				}
			}

			if(!findMatch)
				throw new IdNotAvailableException(id);
			else
				people.remove(id);
		}
		return true;
	}
}
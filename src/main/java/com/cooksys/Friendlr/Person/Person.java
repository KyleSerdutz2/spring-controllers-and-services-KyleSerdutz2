package com.cooksys.Friendlr.Person;

import java.util.ArrayList;
import java.util.List;

public class Person {
	public Person(){
		//Does not increment id!!!
		//Bad idea because can't construct Person temporarily!
	}
	public Person(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		
		idInc++;
		id = idInc;
	}
	public Person(String firstName, String lastName, List<Person> friends) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.friends = friends;
		
		idInc++;
		id = idInc;
	}

	private static long idInc=0;
	
	private Long id = null;
	private String firstName;
	private String lastName;
	private List<Person> friends;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setId() {
		idInc++;
		id = idInc;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public List<Person> getFriends() {
		if(friends == null)
			friends = new ArrayList<Person>();
		return friends;
	}
	public void setFriends(List<Person> friends) {
		friends = friends;
	}
}

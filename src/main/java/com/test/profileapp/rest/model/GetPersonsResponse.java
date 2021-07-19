package com.test.profileapp.rest.model;

import java.util.List;

public class GetPersonsResponse {

	private List<Person> persons ;
	private Long count;
	
	public GetPersonsResponse(List<Person> persons, Long count) {
		super();
		this.persons = persons;
		this.count = count;
	}
	public List<Person> getPersons() {
		return persons;
	}
	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	
	
	
}

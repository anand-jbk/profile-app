package com.test.profileapp.rest.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.profileapp.constants.ValidationConstants;
import com.test.profileapp.exception.ResourceNotFoundException;
import com.test.profileapp.rest.model.GetPersonsResponse;
import com.test.profileapp.rest.model.Person;
import com.test.profileapp.service.PersonService;

@RestController
@RequestMapping("${basepath}/api/persons")
public class PersonController {

	@Autowired
	private PersonService personService;

	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public GetPersonsResponse getPersons(@RequestParam(defaultValue = "0") Integer pageNumber,
			@RequestParam(defaultValue = "10") Integer recordsPerPage) throws Exception {

		return personService.getPersons(pageNumber, recordsPerPage);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public Person getPerson(@PathVariable Integer id) throws Exception {
		Optional<Person> result = personService.getPerson(id);
		if (result.isPresent()) {
			return result.get();
		} else {
			throw new ResourceNotFoundException(ValidationConstants.PERSON_NOT_FOUND, null);
		}

	}

	@RequestMapping(method = RequestMethod.POST)
	public Person addPerson(@Valid @RequestBody Person person) throws Exception {
		return personService.addPerson(person);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	public Person updatePerson(@PathVariable Integer id, @Valid @RequestBody Person person) throws Exception {
		Optional<Person> result = personService.updatePerson(id, person);

		if (result.isPresent()) {
			return result.get();}
		else {
			throw new ResourceNotFoundException(ValidationConstants.PERSON_NOT_FOUND, null);
		}
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public void deletePerson(@PathVariable Integer id) throws Exception {
		personService.deletePerson(id);
	}
}
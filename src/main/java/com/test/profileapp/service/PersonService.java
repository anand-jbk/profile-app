package com.test.profileapp.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.test.profileapp.exception.WebServiceException;
import com.test.profileapp.repository.PersonRepository;
import com.test.profileapp.rest.model.GetPersonsResponse;
import com.test.profileapp.rest.model.Person;

@Service
public class PersonService {

	Logger logger = LoggerFactory.getLogger(PersonService.class);
	
	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private ModelMapper modelMapper;

	public GetPersonsResponse getPersons(Integer pageNumber, Integer recordsPerPage) throws WebServiceException {
		try {
			Pageable recordsRange = PageRequest.of(pageNumber, recordsPerPage);
			Page<com.test.profileapp.entity.Person> findAllResult = personRepository.findAll(recordsRange);

			return new GetPersonsResponse(modelMapper.map(findAllResult.getContent(), List.class),
					findAllResult.getTotalElements());
		} catch (Exception e) {
			logger.error("Exception occured in getPersons",e);
			throw new WebServiceException("Data store exception", "");
		}
	}

	public Optional<Person> getPerson(Integer id) throws WebServiceException {
		try {
			Optional<com.test.profileapp.entity.Person> findById = personRepository.findById(id);
			if (findById.isPresent()) {
				Person result = modelMapper.map(findById.get(), Person.class);
				return Optional.ofNullable(result);
			} else {
				return Optional.empty();
			}
		} catch (Exception e) {
			logger.error("Exception occured in getPerson",e);
			throw new WebServiceException("Data store exception", "");
		}
	}

	public Person addPerson(Person person) throws WebServiceException {
		try {
			com.test.profileapp.entity.Person saveResult = personRepository
					.saveAndFlush(modelMapper.map(person, com.test.profileapp.entity.Person.class));
			return modelMapper.map(saveResult, Person.class);
		} catch (Exception e) {
			logger.error("Exception occured in addPerson",e);
			throw new WebServiceException("Data store exception", "");
		}
	}

	@Transactional
	public Optional<Person> updatePerson(Integer id, Person person) throws WebServiceException {
		try {
			int updateCount = personRepository.updatePersonById(person.getFirstName(), person.getLastName(), id);

			if (updateCount != 0) {
				person.setId(id);
				return Optional.ofNullable(person);
			}
			return Optional.empty();
		} catch (Exception e) {
			logger.error("Exception occured in updatePerson",e);
			throw new WebServiceException("Data store exception", "");
		}
	}

	public void deletePerson(Integer id) throws WebServiceException {
		try {
			personRepository.deleteById(id);
		} catch (Exception e) {
			logger.error("Exception occured in deletePerson",e);
			throw new WebServiceException("Data store exception", "");
		}
	}
}

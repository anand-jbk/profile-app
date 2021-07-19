package com.test.profileapp.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.test.profileapp.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {

	@Modifying()
	@Query("update Person set first_name = ?1, last_name = ?2 where id = ?3")
	int updatePersonById(String firstname, String lastname, Integer id);
}

package com.test.profileapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.test.profileapp.entity.Address;


public interface AddressRepository extends JpaRepository<Address,Integer> {

	
	List<Address> findAllByPersonId(int personId);
	
	@Modifying()
	@Query("update Address set street = ?1, city = ?2, state = ?3, postal_code = ?4, person_id = ?5 where id = ?6")
	int updateAddressById(String street,String city,String state,String postalCode,Integer personId, Integer id);
}

package com.test.profileapp.rest.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.test.profileapp.constants.ValidationConstants;
import com.test.profileapp.exception.ResourceNotFoundException;
import com.test.profileapp.rest.model.Address;
import com.test.profileapp.service.AddressService;

@RestController
@RequestMapping("${basepath}/api/persons/{personId}/addresses")
public class AddressController {

	@Autowired
	private AddressService addressService;

	@RequestMapping(method = RequestMethod.GET)
	public List<Address> getAddress(@PathVariable Integer personId) throws Exception {
		return addressService.getPersonAddresses(personId);
	}

	@RequestMapping(method = RequestMethod.POST)
	public List<Address> addPersonAddresses(@PathVariable Integer personId, @Valid @RequestBody List<Address> addresses)
			throws Exception {
		return addressService.addPersonAddresses(personId, addresses);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	public Address updateAddress(@PathVariable Integer id, @PathVariable Integer personId, @Valid @RequestBody Address address) throws Exception {
		Optional<Address> result = addressService.updateAddress(id, personId, address);
		if (result.isPresent()) {
			return result.get();
		} else {
			throw new ResourceNotFoundException(ValidationConstants.ADDRESS_NOT_FOUND, null);
		}
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public void deletePerson(@PathVariable Integer id, @PathVariable Integer personId) throws Exception {
		addressService.deleteAddress(id);
	}

}
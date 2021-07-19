package com.test.profileapp.service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.test.profileapp.exception.WebServiceException;
import com.test.profileapp.repository.AddressRepository;
import com.test.profileapp.rest.model.Address;

@Service
public class AddressService {

	Logger logger = LoggerFactory.getLogger(AddressService.class);
	
	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private ModelMapper modelMapper;

	public List<Address> getPersonAddresses(Integer personId) throws WebServiceException {
		try {
			Type listTypeForModel = new TypeToken<List<Address>>() {}.getType();
			return modelMapper.map(addressRepository.findAllByPersonId(personId),listTypeForModel);
		} catch (Exception e) {
			logger.error("Exception occured in getPersonAddresses", e);
			throw new WebServiceException("Data store exception", "");
		}
	}

	public  List<Address> addPersonAddresses(Integer personId, List<Address> addresses) throws WebServiceException {
		try {
			Type listTypeForEntity = new TypeToken<List<com.test.profileapp.entity.Address>>() {}.getType();
			Iterable<com.test.profileapp.entity.Address> addressesToSave = modelMapper.map(addresses, listTypeForEntity);
			addressesToSave.forEach(address -> address.setPersonId(personId));
			List<com.test.profileapp.entity.Address> saveAll = addressRepository.saveAllAndFlush(addressesToSave);
			
			Type listTypeForModel = new TypeToken<List<Address>>() {}.getType();
			return modelMapper.map(saveAll, listTypeForModel);
		} catch (DataIntegrityViolationException e) {
			logger.error("Exception occured in addPersonAddresses", e);
			throw new WebServiceException("Data Integrity error", "");
		}catch (Exception e) {
			logger.error("Exception occured in addPersonAddresses", e);
			throw new WebServiceException("Data store exception", "");
		}
	}

	@Transactional
	public Optional<Address> updateAddress(Integer id, Integer personId, Address address) throws WebServiceException {
		try {
			int updateCount = addressRepository.updateAddressById(address.getStreet(), address.getCity(), address.getState(), address.getPostalCode(),personId, id) ;

			if (updateCount != 0) {
				address.setId(id);
				return Optional.ofNullable(address);
			}
			return Optional.empty();
		} catch (Exception e) {
			logger.error("Exception occured in updateAddress", e);
			throw new WebServiceException("Data store exception", "");
		}
	}
	
	public void deleteAddress(Integer id) throws WebServiceException {
		try {
			addressRepository.deleteById(id);
		} catch (Exception e) {
			logger.error("Exception occured in deleteAddress", e);
			throw new WebServiceException("Data store exception", "");
		}
	}

}

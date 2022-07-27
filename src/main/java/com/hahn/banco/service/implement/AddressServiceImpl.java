package com.hahn.banco.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hahn.banco.dto.address.AddressDTO;
import com.hahn.banco.entity.Address;
import com.hahn.banco.repository.AddressRepository;


@Service
public class AddressServiceImpl {

  @Autowired
  private AddressRepository addressRepository;

  public AddressServiceImpl(AddressRepository addressRepository) {
    this.addressRepository = addressRepository;
  }

  public AddressDTO getById(Long id) {
		return this.toDTO(addressRepository.findById(id).get());
  }

  public Address save(Address newAddress) {
      return addressRepository.save(newAddress);
  }

  // public void delete(Long id) {
  //   addressRepository.deleteById(id);
  // }

  // public Iterable<Address> getAll() {
  //   return addressRepository.findAll();
  // }

  private AddressDTO toDTO(Address address) {
      return new AddressDTO(address.getId(), address.getCity(), address.getStreet(), address.getDirection(), address.getState());
  }

}

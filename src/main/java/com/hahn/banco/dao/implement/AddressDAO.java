package com.hahn.banco.dao.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hahn.banco.dao.IAddressDAO;
import com.hahn.banco.dto.address.AddressDTO;
import com.hahn.banco.dto.address.AddressPostDTO;
import com.hahn.banco.entity.Address;
import com.hahn.banco.repository.AddressRepository;

@Component
public class AddressDAO implements IAddressDAO {

    @Autowired
    private AddressRepository addressRepository;
	

    @Override
    public Address create(Address newAddress) {
        return addressRepository.save(newAddress);
    }

    @Override
    public void delete(Long id) {
        addressRepository.deleteById(id);   
    }

    @Override
    public List<Address> list(String name) {
        return addressRepository.findByName(name);
    }

    @Override
    public Optional<Address> read(Long id) {
        return addressRepository.findById(id);
    }

    @Override
    public void update(Address newAddress, Long id) {
        this.read(id).map(
                address -> {
                    address.setDirection(address.getDirection());
                    addressRepository.save(address);
                    return true;
                }
        );
    }

    @Override
    public AddressDTO toDTO(Address address) {
        return new AddressDTO(address.getId(), null, address.getStreet(), address.getDirection(), address.getState());
    }

    @Override
    public Address toEntity(AddressPostDTO newAddress) {
        return new Address(null, newAddress.getStreet(), newAddress.getDirection());
    }

    @Override
    public Address toEntity(AddressDTO newAddress) {
        return new Address(newAddress.getId(), null, newAddress.getStreet(), newAddress.getDirection()); 
    }
    
}

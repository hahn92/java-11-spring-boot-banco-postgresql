package com.hahn.banco.dao;

import java.util.List;
import java.util.Optional;

import com.hahn.banco.dto.address.AddressDTO;
import com.hahn.banco.dto.address.AddressPostDTO;
import com.hahn.banco.entity.Address;


public interface IAddressDAO {

    Optional<Address> read(Long id);

    List<Address> list(String name);

    Address create(Address newAddress);
    
    void update(Address newAddress, Long id);
    
    void delete(Long id);

    AddressDTO toDTO(Address address);

    Address toEntity(AddressPostDTO newAddress);

    Address toEntity(AddressDTO newAddress);

}

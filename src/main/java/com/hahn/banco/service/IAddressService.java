package com.hahn.banco.service;

import java.util.Optional;

import com.hahn.banco.dto.address.AddressDTO;
import com.hahn.banco.dto.address.AddressPostDTO;;

public interface IAddressService {

    Optional<AddressDTO> getById(Long id);

    AddressDTO save(AddressPostDTO newAddress, long id_city);
    
}

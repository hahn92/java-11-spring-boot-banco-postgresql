package com.hahn.banco.service.implement;

import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hahn.banco.dto.address.AddressDTO;
import com.hahn.banco.dto.address.AddressPostDTO;
import com.hahn.banco.entity.Address;
import com.hahn.banco.repository.AddressRepository;
import com.hahn.banco.service.IAddressService;


@Service
public class AddressServiceImpl implements IAddressService {
	
  private static final Log LOGGER = LogFactory.getLog(AddressServiceImpl.class);

  @Autowired
  private AddressRepository addressRepository;
  @Autowired
  private ModelMapper modelMapper;


  public AddressServiceImpl(AddressRepository addressRepository, ModelMapper modelMapper) {
    this.addressRepository = addressRepository;
    this.modelMapper = modelMapper;
  }
  

  @Override   
  public Optional<AddressDTO> getById(Long id) {
        // TODO Auto-generated method stub
        Address address = addressRepository.findById(id).get();
        if(address.getId() != null) {
            LOGGER.debug("+++ AddressServiceImpl:getById: "+address.toString());
            return Optional.of(this.toDTO(address));
        }
        LOGGER.debug("--- AddressServiceImpl:getById: No existe la direccion con id: "+id);
        return null;
  }

  @Override
  public AddressDTO save(AddressPostDTO addressDTO, Long id_city) {
      // TODO Auto-generated method stub
      Address address = this.toEntity(addressDTO);
      LOGGER.debug("+++ AddressServiceImpl:save: "+address.toString());
      return this.toDTO(addressRepository.save(address));
  }
  

  public AddressDTO toDTO(Address address) {
      LOGGER.debug("+++ AddressServiceImpl:toDTO: "+address.toString());
      return modelMapper.map(address, AddressDTO.class);
  }

  public Address toEntity(AddressPostDTO addressDTO) {
      LOGGER.debug("+++ AddressServiceImpl:toEntity: "+addressDTO.toString());
      return modelMapper.map(addressDTO, Address.class); 
  }

  public Address toEntity(AddressDTO addressDTO) {
    LOGGER.debug("+++ AddressServiceImpl:toEntity: "+addressDTO.toString());
    return modelMapper.map(addressDTO, Address.class);  
  }

}

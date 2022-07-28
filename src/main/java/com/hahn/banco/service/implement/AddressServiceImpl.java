package com.hahn.banco.service.implement;

import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hahn.banco.dto.address.AddressDTO;
import com.hahn.banco.dto.address.AddressPostDTO;
import com.hahn.banco.dto.city.CityDTO;
import com.hahn.banco.entity.Address;
import com.hahn.banco.entity.City;
import com.hahn.banco.repository.AddressRepository;
import com.hahn.banco.service.IAddressService;


@Service
public class AddressServiceImpl implements IAddressService {
	
  private static final Log LOGGER = LogFactory.getLog(AddressServiceImpl.class);

  @Autowired
  private AddressRepository addressRepository;
  @Autowired
  private CityServiceImpl cityServiceiImpl;

  public AddressServiceImpl(AddressRepository addressRepository, CityServiceImpl cityServiceiImpl) {
    this.addressRepository = addressRepository;
    this.cityServiceiImpl = cityServiceiImpl;
  }

  @Override   
  public Optional<AddressDTO> getById(Long id) {
        // TODO Auto-generated method stub
        Address address = addressRepository.findById(id).get();
        if(address.getId() != null) {
            LOGGER.debug("+++ AddressServiceImpl:getById: "+address.toString());
            return Optional.of(this.toDTO(address, address.getCity().getId()));
        }
        LOGGER.debug("--- AddressServiceImpl:getById: No existe la direccion con id: "+id);
        return null;
  }

  @Override
  public AddressDTO save(AddressPostDTO addressDTO, Long id_city) {
      // TODO Auto-generated method stub
      Address address = this.toEntity(addressDTO, id_city);
      LOGGER.debug("+++ AddressServiceImpl:save: "+address.toString());
      return this.toDTO(addressRepository.save(address), id_city);
  }

  public AddressDTO toDTO(Address address, Long id_city) {
      LOGGER.debug("+++ AddressServiceImpl:toDTO: "+address.toString());
      return new AddressDTO(address.getId(), cityServiceiImpl.getById(id_city).get(), address.getStreet(), address.getDirection(), address.getState());
  }

  public Address toEntity(AddressPostDTO addressDTO, Long id_city) {
      LOGGER.debug("+++ AddressServiceImpl:toEntity: "+addressDTO.toString());
      CityDTO cityDTO = cityServiceiImpl.getById(id_city).get();
      City city = cityServiceiImpl.toEntity(cityDTO, cityDTO.getDepartment().getId());
      LOGGER.debug("+++ AddressServiceImpl:toEntity: "+city.toString());
      return new Address(city, addressDTO.getStreet(), addressDTO.getDirection());   
  }

  public Address toEntity(AddressDTO addressDTO, Long id_city) {
      LOGGER.debug("+++ AddressServiceImpl:toEntity: "+addressDTO.toString());
      CityDTO cityDept = cityServiceiImpl.getById(id_city).get();
      City city = cityServiceiImpl.toEntity(cityDept, cityDept.getDepartment().getId());
      LOGGER.debug("+++ AddressServiceImpl:toEntity: "+city.toString());
      return new Address(addressDTO.getId(), city, addressDTO.getStreet(), addressDTO.getDirection());   
  }

}

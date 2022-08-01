package com.hahn.banco.service.implement;

import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hahn.banco.dao.IAddressDAO;
import com.hahn.banco.dao.ICityDAO;
import com.hahn.banco.dto.address.AddressDTO;
import com.hahn.banco.dto.address.AddressPostDTO;
import com.hahn.banco.entity.Address;
import com.hahn.banco.service.IAddressService;


@Service
public class AddressServiceImpl implements IAddressService {
	
    private static final Log LOGGER = LogFactory.getLog(AddressServiceImpl.class);

    @Autowired
    private IAddressDAO iAddressDAO;
    @Autowired
    private ICityDAO iCityDAO;


    public AddressServiceImpl(IAddressDAO iAddressDAO, ICityDAO iCityDAO) {
        this.iAddressDAO = iAddressDAO;
        this.iCityDAO = iCityDAO;
    }

    
    @Override   
    public Optional<AddressDTO> getById(Long id) {
            Address address = iAddressDAO.read(id).get();
            if(address.getId() != null) {
                LOGGER.debug("+++ AddressServiceImpl:getById: "+address.toString());
                AddressDTO AddressDTO = iAddressDAO.toDTO(address);
                iCityDAO.read(address.getCity().getId()).ifPresent(city -> AddressDTO.setCity(iCityDAO.toDTO(city)));
                return Optional.of(AddressDTO);
            }
            LOGGER.debug("--- AddressServiceImpl:getById: No existe la direccion con id: "+id);
            return null;
    }

    @Override
    public AddressDTO save(AddressPostDTO addressDTO, Long id_city) {
        Address address = iAddressDAO.toEntity(addressDTO);
        LOGGER.debug("+++ AddressServiceImpl:save: "+address.toString());
        iCityDAO.read(id_city).ifPresent(city -> address.setCity(city));
        return iAddressDAO.toDTO(iAddressDAO.create(address));
    }

}

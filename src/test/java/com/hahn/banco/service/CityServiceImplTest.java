// package com.hahn.banco.service;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;

// import com.hahn.banco.dto.city.CityDTO;
// import com.hahn.banco.dto.city.CityPostDTO;
// import com.hahn.banco.entity.City;
// import com.hahn.banco.entity.Department;
// import com.hahn.banco.repository.CityRepository;
// import com.hahn.banco.repository.DepartmentRepository;
// import com.hahn.banco.service.implement.CityServiceImpl;
// import com.hahn.banco.service.implement.DepartmentServiceImpl;

// import static org.junit.jupiter.api.Assertions.*;

// import java.util.Optional;

// public class CityServiceImplTest {

//     private static CityServiceImpl cityServiceImpl;
//     private static DepartmentServiceImpl departmentServiceImpl;


//     @BeforeEach
//     public void setUp() throws Exception {

//         DepartmentRepository departmentRepository = Mockito.mock(DepartmentRepository.class);

//         Mockito.when(departmentRepository.findById(1L)).thenReturn(
//                 Optional.of(new Department(1L, "Department 1"))
//         );
//         Mockito.when(departmentRepository.save(
//                 Mockito.any(Department.class)
//         )).thenReturn(new Department(2L, "Department 2"));
        
//         departmentServiceImpl = new DepartmentServiceImpl(departmentRepository);

//         CityRepository cityRepository = Mockito.mock(CityRepository.class);

//         Mockito.when(cityRepository.findById(1L)).thenReturn(
//                 Optional.of(new City(1L, new Department(1L, "Department 1"), "City 1"))
//         );
//         Mockito.when(cityRepository.save(
//                 Mockito.any(City.class)
//         )).thenReturn(new City(2L, new Department(2L, "Department 2"), "City 2"));
        

//         cityServiceImpl = new CityServiceImpl(cityRepository, departmentServiceImpl);
//     }


//     @Test
//     public void return_city_by_id() {

//         CityDTO city = cityServiceImpl.getById(1L).get();
//         assertEquals(1L, city.getId());

//     }
    

//     @Test
//     public void return_city_save() {

//         CityDTO city = cityServiceImpl.save(new CityPostDTO("City 3"), 1L);
//         assertEquals("City 2", city.getName());

//     }
    
// }

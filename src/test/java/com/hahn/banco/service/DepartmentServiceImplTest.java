// package com.hahn.banco.service;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;
// import org.modelmapper.ModelMapper;

// import com.hahn.banco.dto.department.DepartmentDTO;
// import com.hahn.banco.dto.department.DepartmentPostDTO;
// import com.hahn.banco.entity.Department;
// import com.hahn.banco.repository.DepartmentRepository;
// import com.hahn.banco.service.implement.DepartmentServiceImpl;

// import static org.junit.jupiter.api.Assertions.*;

// import java.util.Optional;

// public class DepartmentServiceImplTest {

//     private static DepartmentServiceImpl departmentServiceImpl;
//     private static ModelMapper modelMapper;

//     @BeforeEach
//     public void setUp() throws Exception {
//         DepartmentRepository departmentRepository = Mockito.mock(DepartmentRepository.class);

//         Mockito.when(departmentRepository.findById(1L)).thenReturn(
//                 Optional.of(new Department(1L, "Department 1"))
//         );
//         Mockito.when(departmentRepository.save(
//                 Mockito.any(Department.class)
//         )).thenReturn(new Department(2L, "Department 2"));
        
//         modelMapper = Mockito.mock(ModelMapper.class);
//         departmentServiceImpl = new DepartmentServiceImpl(departmentRepository, modelMapper);
//     }


//     @Test
//     public void return_deparment_by_id() {

//         DepartmentDTO departament = departmentServiceImpl.getById(1L).get();
//         assertEquals(1L, departament.getId());

//     }
    

//     @Test
//     public void return_department_save() {

//         DepartmentDTO departament = departmentServiceImpl.save(new DepartmentPostDTO("Department 2"));
//         assertEquals("Department 2", departament.getName());

//     }
    
// }
